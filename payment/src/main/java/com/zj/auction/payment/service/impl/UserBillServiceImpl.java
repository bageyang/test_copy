package com.zj.auction.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zj.auction.common.enums.UserBillStatAndTypeEnum;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.mapper.UserBillMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.UserBill;
import com.zj.auction.common.util.HttpUtils;
import com.zj.auction.common.util.UidGenerator;
import com.zj.auction.payment.config.KuDianPayClientConfig;
import com.zj.auction.payment.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class UserBillServiceImpl implements UserBillService{

    @Autowired
    private KuDianPayClientConfig kuDianPayClientConfig;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserBillMapper userBillMapper;

    private final ReentrantLock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String tradeCreate(Map bodys) {
        try {
            //1. 产生预支付流水记录
            UserBill userBill = new UserBill();
            String payXid = UidGenerator.createPayXid();
            userBill.setUserId(Long.parseLong((String) bodys.get("userId")));
            //设置支付类型
            if ("购买商品支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.BUY_GOODS.getCode());
            }else if ("充值余额支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.RECHARGE.getCode());
            }else if ("预约保证金支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.APPOINTMENT.getCode());
            }else if ("转拍手续费支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.AUCTION.getCode());
            }
            //设置支付手段
            if ("ALIPAY".equals(bodys.get("pay_type"))){
                userBill.setPayType(UserBillStatAndTypeEnum.ALI_PAY.getCode());
            }else if ("WXPAY".equals(bodys.get("pay_type"))){
                userBill.setPayType(UserBillStatAndTypeEnum.WX_PAY.getCode());
            }
            //设置流水记录状态
            userBill.setBillStatus(UserBillStatAndTypeEnum.NO_PAY.getCode());
            userBill.setTranstionSn(payXid);
            BigDecimal amount = new BigDecimal((String)(bodys.get("amount")));
            userBill.setAmount(amount);
            int insert = userBillMapper.insert(userBill);
            if (insert <= 0) {
                throw new RuntimeException("创建支付订单失败");
            }

            //2.调用支付接口
            HashMap map = new HashMap<String,String>();
            map.put("mch_id",kuDianPayClientConfig.getMchId());
            map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
            map.put("nonce_str","abcderg");
            map.put("sign_type","MD5");
            String payment = userBill.getPayType().intValue() == 1 ? "ALIPAY" : "WXPAY";
            map.put("payment", payment);


            //组装当前业务方法的content参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("fee",userBill.getAmount().intValue());
            bizContent.put("out_trade_no",userBill.getTranstionSn());
            bizContent.put("body", URLEncoder.encode(UserBillStatAndTypeEnum.getTextByCode(userBill.getBillType()),"GBK"));
            bizContent.put("client_ip","103.163.180.14");
            bizContent.put("notify_url",kuDianPayClientConfig.getNotifyUrl()+"/app/kudianPay/kudianPayCallBackForPay");
            map.put("content", bizContent.toString());
            //签名
            String sign = SignUtils.md5Sign(map, kuDianPayClientConfig.getSecretKey());
            map.put("sign",sign);
            //发送调用起支付
            try {
                HttpResponse res = HttpUtils.doPostJson(kuDianPayClientConfig.getGatewayUrl(),"/openapi/pay/kdh5pay", "POST", null, null, map);
                String s = EntityUtils.toString(res.getEntity(),"utf-8");
                JSONObject js = JSONObject.parseObject(s);

                int statusCode = res.getStatusLine().getStatusCode();
                if (statusCode == 200){
                    log.info("调用成功，返回结果 ===> " + res.getEntity().toString());
                    return js.getString("data");
                }else {
                    log.info("调用失败，返回码 ===> " + statusCode + ", 返回描述 ===> " + res.getEntity().toString());
                    throw new RuntimeException("创建支付交易失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("创建支付交易失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败");
        }
    }

    /**
     * 处理订单
     * @param params
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(Map<String, String> params) {

        log.info("处理订单");

        //获取支付订单号
        String orderNo = params.get("out_trade_no");

        /*在对业务数据进行状态检查和处理之前，
        要采用数据锁进行并发控制，
        以避免函数重入造成的数据混乱*/
        //尝试获取锁：
        // 成功获取则立即返回true，获取失败则立即返回false。不必一直等待锁的释放
        if(lock.tryLock()) {
            try {

                //处理重复通知
                //接口调用的幂等性：无论接口被调用多少次，以下业务执行一次
                UserBill userBill = userBillMapper.selectUserBillByTranstionSn(orderNo);
                if (!(UserBillStatAndTypeEnum.NO_PAY.getCode() == (userBill.getBillStatus()))) {
                    return;
                }
                //更新订单状态
                userBillMapper.updateBillStatusByTranstion_sn(orderNo, UserBillStatAndTypeEnum.SUCCESS_PAY.getCode());

            } finally {
                //要主动释放锁
                lock.unlock();
            }
        }

    }
}
