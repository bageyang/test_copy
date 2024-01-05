package com.zj.auction.payment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.zj.auction.common.mapper.OrderMapper;
import com.zj.auction.common.model.Order;
import com.zj.auction.common.util.HttpUtils;
import com.zj.auction.payment.config.KuDianPayClientConfig;
import com.zj.auction.payment.util.SignUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zj.auction.common.vo.ResponseData;
import javax.annotation.Resource;
import java.io.InputStreamReader;
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
    @Autowired
    private KuDianPayClientConfig kuDianPayClientConfig;
    @Resource
    private  OrderMapper orderMapper;
    @ApiOperation("h5收银台支付")
    @PostMapping("/trade/page/pay/{productId}")
    public ResponseData kuDianPay(@PathVariable Long productId) throws UnsupportedEncodingException {


        String gateway = kuDianPayClientConfig.getGatewayUrl();
        log.info("收银台支付");
        //1. 创建订单
        Order order = orderMapper.selectOrderByOrderNumber(15679L);
        if (order == null){
            order = new Order();
            order.setOrderSn(15679L);
            order.setOrderFee(new BigDecimal(10));
            order.setPayAmount(new BigDecimal(1.1));
        }
        //2.调用支付接口
        HashMap map = new HashMap<String,String>();
        map.put("mch_id",kuDianPayClientConfig.getMchId());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        map.put("nonce_str","abcderg");
        map.put("sign_type","MD5");


        //组装当前业务方法的content参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("fee",order.getPayAmount().intValue());
        bizContent.put("out_trade_no",order.getOrderSn());
        bizContent.put("body", URLEncoder.encode("余额充值","GBK"));
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
                return ResponseData.ok(js);
            }else {
                log.info("调用失败，返回码 ===> " + statusCode + ", 返回描述 ===> " + res.getEntity().toString());
                throw new RuntimeException("创建支付交易失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败");
        }
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
        }
        return "SUCCESS";
    }
}
