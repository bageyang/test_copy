package com.zj.auction.payment.config;

import com.alipay.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.zj.auction.common.constant.SystemConfig;

import javax.annotation.Resource;

@Configuration
//加载配置文件
@PropertySource("classpath:alipay.properties")
public class AlipayClientConfig {

    @Resource
    private Environment config;

//    @Bean
//    public AlipayClient alipayClient() throws AlipayApiException {
//
//        AlipayConfig alipayConfig = new AlipayConfig();
//
//        //设置网关地址
//        alipayConfig.setServerUrl(config.getProperty("alipay.gateway-url"));
//        //设置应用Id
//        alipayConfig.setAppId(SystemConfig.getAliAppId());
//        //设置应用私钥
//        alipayConfig.setPrivateKey(SystemConfig.getAliMerchantPrivateKey());
//        //设置应用公钥
//        alipayConfig.setAppCertPath(config.getProperty("alipay.app-cert-path"));
//        //设置支付宝公钥
//        alipayConfig.setAlipayPublicCertPath(config.getProperty("alipay.ali-cert-path"));
//        //设置支付宝root公钥
//        alipayConfig.setRootCertPath(config.getProperty("alipay.ali-root-cert-path"));
//        //设置请求格式，固定值json
//        alipayConfig.setFormat(AlipayConstants.FORMAT_JSON);
//        //设置字符集
//        alipayConfig.setCharset(AlipayConstants.CHARSET_UTF8);
//        //设置签名类型
//        alipayConfig.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
//        //构造client
//        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
//
//        return alipayClient;
//    }
}
