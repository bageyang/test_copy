package com.zj.auction.payment.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="APP支付宝支付接口",tags={"APP支付宝支付接口"})
@RestController
@RequestMapping(value="/app/aliPay")
@Slf4j
public class AliPayController {
}
