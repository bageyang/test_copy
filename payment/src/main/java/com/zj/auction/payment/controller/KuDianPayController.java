package com.zj.auction.payment.controller;

import com.zj.auction.payment.config.KuDianPayClientConfig;
import com.zj.auction.seckill.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zj.auction.common.vo.ResponseData;

@Api(value="酷点支付接口",tags={"酷点支付接口"})
@RestController
@RequestMapping(value="/pay/kudianPay")
@Slf4j
public class KuDianPayController {
    @Autowired
    private KuDianPayClientConfig kuDianPayClientConfig;
    @Autowired
    private OrderService orderService;

    @ApiOperation("统一收单下单并支付页面接口的调用")
    @PostMapping("/trade/page/pay/{productId}")
    public ResponseData kuDianPay(@PathVariable Long productId){


        String gateway = kuDianPayClientConfig.getGatewayUrl();
        log.info("收银台支付");
        //1. 创建订单


        return ResponseData.ok(gateway);
    }

}
