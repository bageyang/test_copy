package com.zj.auction.seckill;

import com.zj.auction.seckill.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void inserRedisList(){
//        int num = 1000000;
//        for (int i = 0; i < num; i++) {
//            redisService.lPush("auction:stock","socket_"+i);
//        }
        RScript script = redissonClient.getScript();
        List<String> keys = new ArrayList<>();
        keys.add("auction:volume");
        keys.add("auction:stock");
        Object[] values = new Object[]{"auction01",0,1};
        Object value = script.evalSha(RScript.Mode.READ_WRITE, "a1b4d39b78f9ee7c73e6f6d9a681b0f8be081d74", RScript.ReturnType.STATUS, Collections.singletonList(keys), values);
        System.out.println(value);
    }
}
