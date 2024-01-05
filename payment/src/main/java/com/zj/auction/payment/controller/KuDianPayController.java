package com.zj.auction.payment.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonToken;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zj.auction.common.vo.ResponseData;
import javax.annotation.Resource;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;


@Api(value="酷点支付接口",tags={"酷点支付接口"})
@RestController
@RequestMapping(value="/pay/kudianPay")
@Slf4j
public class KuDianPayController {
    @Autowired
    private KuDianPayClientConfig kuDianPayClientConfig;
    @Resource
    private  OrderMapper orderMapper;
    @ApiOperation("统一收单下单并支付页面接口的调用")
    @PostMapping("/trade/page/pay/{productId}")
    public ResponseData kuDianPay(@PathVariable Long productId){


        String gateway = kuDianPayClientConfig.getGatewayUrl();
        log.info("收银台支付");
        //1. 创建订单
        Order order = orderMapper.selectOrderByOrderNumber(12346L);
        if (order == null){
            order = new Order();
            order.setOrderSn(12346L);
            order.setOrderFee(new BigDecimal(10));
            order.setPayAmount(new BigDecimal(10.1));
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
        bizContent.put("body","aaabbb");
        bizContent.put("client_ip","103.163.180.14");
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
}
