package com.zj.auction.general;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zj.auction.common.mapper")
@EnableAsync
public class GeneralApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneralApplication.class,args);
    }
}
