package com.zj.auction.payment;

import com.zj.auction.common.constant.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SysConfTests {

    @Test
    public void testAlipayConfig(){

        String aliAppId = SystemConfig.getAliAppId();
        log.info(">>>>>",aliAppId);

    }
}
