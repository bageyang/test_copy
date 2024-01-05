package com.zj.auction.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.mapper.UserBillMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.model.UserBill;
import com.zj.auction.common.util.HttpUtils;
import com.zj.auction.common.enums.UserBillStatAndTypeEnum;
import com.zj.auction.payment.config.KuDianPayClientConfig;
import com.zj.auction.payment.service.impl.UserBillService;
import com.zj.auction.payment.util.SignUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zj.auction.common.util.UidGenerator;
import com.zj.auction.common.vo.ResponseData;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Api(value="酷点支付接口",tags={"酷点支付接口"})
@RestController
@RequestMapping(value="/app/kudianPay")
@Slf4j
public class KuDianPayController {

    @Resource
    UserBillService userBillService;
    @Autowired
    private KuDianPayClientConfig kuDianPayClientConfig;

    @ApiOperation("h5收银台支付")
    @PostMapping("/kudianPay")
    public ResponseData kuDianPay(@RequestBody Map<String, String> bodys) throws UnsupportedEncodingException {
        //需要传递的东西 1.type(支付订单类类型) 2. 支付商品（商品订单id）,余额充值（userid）,预约竞拍保证金(预约记录id),转拍手续费支付单(转拍订单编号)
        String s = userBillService.tradeCreate(bodys);
        return ResponseData.ok(s);
    }


    /**
     * @Description 支付宝回调方法
     * @Title aliPayCallBackForPay
     * @Author Mao Qi
     * @Date 2019/10/24 19:26
     * @param
     * @return	java.lang.String
     */
    @ApiOperation("h5收银台支付回调")
    @PostMapping(value = "/kudianPayCallBackForPay")
    public String kuDianPayCallBackForPay(@RequestBody Map<String, String> params) throws AlipayApiException, UnsupportedEncodingException {
        log.info("支付通知正在执行");
        log.info("通知参数 ===> {}", params);
        String result = "failure";
        try {
            //验签
            HashMap map = new HashMap<String,String>();
            map.put("code",params.get("code"));
            map.put("msg", params.get("msg"));
            map.put("result",params.get("result"));
            map.put("sign_type",params.get("sign_type"));
            String signLocal = SignUtils.md5Sign(map, kuDianPayClientConfig.getSecretKey());
            String signRemote = params.get("sign");

            boolean flag = signLocal.equals(signRemote);
            //解析content
            String content = params.get("result");
            Map contentMap = JSON.parseObject(content,Map.class);

            log.info("回调校验："+flag+"状态码："+contentMap.get("status"));
            if(flag&&"100".equals(contentMap.get("status").toString())){
                //商户订单号
                String transactionId = (String) contentMap.get("out_trade_no");
                log.info("----------商户订单号--------"+transactionId);
                //支付宝交易号
                String tradeNo = (String) contentMap.get("pay_no");
                String total = contentMap.get("fee").toString();
                log.info("----------回调金额--------"+total);
                BigDecimal totalMoney = new BigDecimal(total);
                totalMoney = totalMoney.setScale(2,BigDecimal.ROUND_HALF_UP);
                //附加数据
//            String passbackParams = (String) contentMap.get("body");
                String passbackParams = URLDecoder.decode(contentMap.get("body").toString(),"GBK");
                String payment = (String) contentMap.get("payment");
                Integer payType = payment.equals("ALIPAY") ?  2 : 1;
                log.info("----------passbackParams--------"+passbackParams);
                //处理业务 修改订单状态 记录支付日志
                userBillService.processOrder(contentMap);
                //todo mq处理
//            try{
//                if("购买线上商品".equals(passbackParams)) {
//                    appOrderService.paymentCallback(transactionId, totalMoney, tradeNo,payType);
//                }
//                if("余额充值".equals(passbackParams)) {
//                    appOrderService.payRechargeCallback(transactionId, totalMoney, tradeNo,payType);
//                }
//                if("预约竞拍保证金".equals(passbackParams)) {//预约保证金
//                    appOrderService.appointmentCallback(transactionId, totalMoney, tradeNo,payType);
//                }
//                if("转拍手续费".equals(passbackParams)) {//转拍回调
//                    appOrderService.turnOnCallback(transactionId, totalMoney, tradeNo,payType);
//                }
//            } catch (Exception e) {
//                log.error("--->支付回调异常", e);
//            }
                result = "SUCCESS";
            }
            else {
                log.error("支付未成功");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
