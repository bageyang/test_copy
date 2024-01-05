package com.zj.auction.payment.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zj.auction.common.constant.Constant;
import com.zj.auction.common.dto.PayDto;
import com.zj.auction.common.enums.UserBillStatAndTypeEnum;
import com.zj.auction.common.mapper.UserBillMapper;
import com.zj.auction.common.model.UserBill;
import com.zj.auction.common.util.HttpUtils;
import com.zj.auction.common.util.UidGenerator;
import com.zj.auction.payment.config.KuDianPayClientConfig;
import com.zj.auction.payment.service.impl.UserBillService;
import com.zj.auction.payment.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
@Transactional
public class UserBillServiceImpl implements UserBillService {

    @Autowired
    private KuDianPayClientConfig kuDianPayClientConfig;

    @Resource
    private UserBillMapper userBillMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ReentrantLock lock = new ReentrantLock();

//    @Transactional(rollbackFor = Exception.class)
    @Override
    public String tradeCreate(Map bodys) {
        try {
            //1. 产生预支付流水记录
            UserBill userBill = new UserBill();
            String payXid = UidGenerator.createPayXid();
            userBill.setUserId(Long.parseLong((String) bodys.get("user_id")));
            //设置支付类型
            if ("购买商品支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.BUY_GOODS.getCode());
            }else if ("充值余额支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.RECHARGE.getCode());
            }else if ("预约保证金支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.APPOINTMENT.getCode());
            }else if ("转拍手续费支付".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.AUCTION.getCode());
            }else if ("pay".equals(bodys.get("type"))){
                userBill.setBillType(UserBillStatAndTypeEnum.PAY.getCode());
            }
            userBill.setExternalSn((String) bodys.get("origin_sn"));
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
            int payType = (userBill.getPayType());
            String payment = (payType == 1 ? "ALIPAY" : "WXPAY");


            //组装当前业务方法的content参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("fee",userBill.getAmount().intValue());
            bizContent.put("out_trade_no",userBill.getTranstionSn());
            String textByCode = UserBillStatAndTypeEnum.getTextByCode(userBill.getBillType());
            bizContent.put("body", URLEncoder.encode(textByCode,"UTF-8"));
            bizContent.put("client_ip","103.163.180.14");
            bizContent.put("notify_url",kuDianPayClientConfig.getNotifyUrl()+"/app/kudianPay/kudianPayCallBackForPay");
            bizContent.put("payment",payment);
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
                int code = (int) js.get("code");
                if (statusCode == 200 && code == 0){
                    log.info("调用成功，返回结果 ===> " + js);
                    return js.getJSONObject("result").toJSONString();
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
                if (!(UserBillStatAndTypeEnum.NO_PAY.getCode() == (userBill.getBillStatus().byteValue()))) {
                    return;
                }
                //更新订单状态
                userBill.setBillStatus(UserBillStatAndTypeEnum.SUCCESS_PAY.getCode());
                userBillMapper.updateById(userBill);
//                userBillMapper.updateBillStatusByTranstionSn(orderNo, UserBillStatAndTypeEnum.SUCCESS_PAY.getCode());
                //todo 记录支付日志

                //发送mq
                String billType = UserBillStatAndTypeEnum.getTextByCode(userBill.getBillType());
                String payType = UserBillStatAndTypeEnum.getPayTextByCode(userBill.getPayType());
                BigDecimal amount = userBill.getAmount();
                Long originSn = Long.parseLong(userBill.getExternalSn());
                String billStatus = UserBillStatAndTypeEnum.getStatusTextByCode(userBill.getBillStatus());

                PayDto payDto = buildPayMqMSg(billType,payType,amount,orderNo,originSn,billStatus);
                rabbitTemplate.convertAndSend(Constant.PAY_EXCHANGE_KEY, null, payDto);
            } finally {
                //要主动释放锁
                lock.unlock();
            }
        }

    }

    @Override
    public List<UserBill> getNoPayOrderByDuration(int minutes) {
        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        QueryWrapper<UserBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bill_status", UserBillStatAndTypeEnum.NO_PAY.getCode());
        queryWrapper.le("create_time", instant);

        List<UserBill> userBillList = userBillMapper.selectList(queryWrapper);

        return userBillList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkPayOrderStatus(String transtionSn) {
        log.info("根据订单号核实订单状态 ===> {}", transtionSn);
        String result = this.queryOrder(transtionSn);

        //订单未创建
        if(result == null){
            log.warn("核实订单未创建 ===> {}", transtionSn);
            //更新本地订单状态
            UserBill userBill = userBillMapper.selectUserBillByTranstionSn(transtionSn);

            userBill.setBillStatus(UserBillStatAndTypeEnum.CLOSE.getCode());
            userBillMapper.updateById(userBill);
//            userBillMapper.updateBillStatusByTranstionSn(transtionSn,UserBillStatAndTypeEnum.CLOSE.getCode());
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject res = jsonObject.getJSONObject("result");
        UserBill userBill = userBillMapper.selectUserBillByTranstionSn(transtionSn);
        //获取支付状态
        int status = res.getIntValue("status");
        // 100 支付成功  101待支付 102 用户支付中 104支付失败 105订单撤销或关闭
        if (status == 101) {
            log.info("核实订单未支付 ===> {}", transtionSn);
            //如果订单未支付，则调用关单接口关闭订单
            this.closeOrder(transtionSn);
            userBill.setBillStatus(UserBillStatAndTypeEnum.CLOSE.getCode());
            userBillMapper.updateById(userBill);
//            userBillMapper.updateBillStatusByTranstionSn(transtionSn,UserBillStatAndTypeEnum.CLOSE.getCode());
            //mq 发送订单超时
//            String billType = UserBillStatAndTypeEnum.getTextByCode(userBill.getBillType());
//            String payType = UserBillStatAndTypeEnum.getPayTextByCode(userBill.getPayType());
//            BigDecimal amount = userBill.getAmount();
//            Long originSn = Long.parseLong(userBill.getExternalSn());
//            PayDto payDto = buildPayMqMSg(billType,payType,amount,transtionSn,originSn);
//            //todo 记录支付日志
//            rabbitTemplate.convertAndSend(Constant.PAY_EXCHANGE_KEY, null, payDto);
        }else if (status == 100) {
            log.info("核实订单已支付 ===> {}", transtionSn);
            //修改订单状态
            userBill.setBillStatus(UserBillStatAndTypeEnum.SUCCESS_PAY.getCode());
            userBillMapper.updateById(userBill);
//            userBillMapper.updateBillStatusByTranstionSn(transtionSn,UserBillStatAndTypeEnum.SUCCESS_PAY.getCode());
            //mq 发送

            //发送mq
            String billType = UserBillStatAndTypeEnum.getTextByCode(userBill.getBillType());
            String payType = UserBillStatAndTypeEnum.getPayTextByCode(userBill.getPayType());
            BigDecimal amount = userBill.getAmount();
            Long originSn = Long.parseLong(userBill.getExternalSn());
            String billStatus = UserBillStatAndTypeEnum.getStatusTextByCode(userBill.getBillStatus());
            PayDto payDto = buildPayMqMSg(billType,payType,amount,transtionSn,originSn,billStatus);
            //todo 记录支付日志
            rabbitTemplate.convertAndSend(Constant.PAY_EXCHANGE_KEY, null, payDto);
        }
    }

    @Override
    public String queryOrder(String transtionSn) {
        //1.调用查询支付订单
        HashMap map = new HashMap<String,String>();
        map.put("mch_id",kuDianPayClientConfig.getMchId());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        map.put("nonce_str","abcderg");
        map.put("sign_type","MD5");


        //组装当前业务方法的content参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no",transtionSn);
        map.put("content", bizContent.toString());
        //签名
        String sign = SignUtils.md5Sign(map, kuDianPayClientConfig.getSecretKey());
        map.put("sign",sign);

        try {
            HttpResponse res = HttpUtils.doPostJson(kuDianPayClientConfig.getGatewayUrl(),"/openapi/pay/query", "POST", null, null, map);
            String s = EntityUtils.toString(res.getEntity(),"utf-8");
            JSONObject js = JSONObject.parseObject(s);

            int statusCode = res.getStatusLine().getStatusCode();
            int code = (int) js.get("code");
            if (statusCode == 200 && code == 0){
                log.info("调用查询成功，返回结果 ===> " + js);
                return js.getJSONObject("result").toJSONString();
            }else {
                log.info("调用查询失败，返回码 ===> " + statusCode + ", 返回描述 ===> " + js);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询交易失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void closeOrder(String transtionSn) {
        log.info("关单接口的调用，订单号 ===> {}", transtionSn);
        //1.调用查询支付订单
        HashMap map = new HashMap<String,String>();
        map.put("mch_id",kuDianPayClientConfig.getMchId());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        map.put("nonce_str","abcderg");
        map.put("sign_type","MD5");


        //组装当前业务方法的content参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no",transtionSn);
        map.put("content", bizContent.toString());
        //签名
        String sign = SignUtils.md5Sign(map, kuDianPayClientConfig.getSecretKey());
        map.put("sign",sign);

        try {
            HttpResponse res = HttpUtils.doPostJson(kuDianPayClientConfig.getGatewayUrl(),"/openapi/pay/close", "POST", null, null, map);
            String s = EntityUtils.toString(res.getEntity(),"utf-8");
            JSONObject js = JSONObject.parseObject(s);

            int statusCode = res.getStatusLine().getStatusCode();
            int code = (int) js.get("code");
            if (statusCode == 200 && code == 0){
                log.info("调用关闭支付订单成功，返回结果 ===> " + js);
            }else {
                log.info("调用关闭支付订单失败，返回码 ===> " + statusCode + ", 返回描述 ===> " + js);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用关闭订单失败");
        }

    }

    private PayDto buildPayMqMSg(String billType, String payType, BigDecimal amount, String transtionSn,Long originSn, String billStatus) {
        PayDto payDto = new PayDto();
        payDto.setBillType(billType);
        payDto.setPayType(payType);
        payDto.setBillStatus(billStatus);
        payDto.setTranstionSn(transtionSn);
        payDto.setOriginSn(originSn);
        payDto.setAmount(amount);
        payDto.setCreateTime(LocalDateTime.now());
        return payDto;
    }
}
