package com.zj.auction.payment.config;

import com.alipay.api.*;
import com.zj.auction.common.constant.SystemConfig;
import lombok.Data;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.apache.http.client.HttpClient;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
@Data
//加载配置文件
@PropertySource("classpath:kudianpay.properties")
@ConfigurationProperties(prefix="kudianpay") //读取wxpay节点
public class KuDianPayClientConfig {
    //商户号
    private String mchId;
    //网关
    private String gatewayUrl;
    //secret-key
    private String secretKey;
    //notify-url
    private String notifyUrl;
}


