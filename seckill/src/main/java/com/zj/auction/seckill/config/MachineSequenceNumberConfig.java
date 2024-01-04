package com.zj.auction.seckill.config;

import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.util.SnowFlake;
import com.zj.auction.seckill.service.RedisService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Configurable
public class MachineSequenceNumberConfig implements SmartInitializingSingleton {
    @Autowired
    private RedisService redisService;
    @Autowired
    private Redisson redisson;
    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void afterSingletonsInstantiated() {
        Integer machineNumber = null;
        RLock lock = redisson.getLock(RedisConstant.MACHINE_SEQUENCE_LOCK_KEY);
        try {
            if (lock.tryLock()) {
                Set<Object> snObjSet = redisService.sMembers(RedisConstant.MACHINE_SEQUENCE_KEY);
                Set<Integer> snSet = snObjSet.stream().map(e -> Integer.parseInt(String.valueOf(e))).collect(Collectors.toSet());
                int i = snSet.size()+1;
                for (int j = 0; j <= i; j++) {
                    if(!snSet.contains(j)){
                        machineNumber = j;
                        System.out.println(machineNumber);
                        break;
                    }
                }
            }
        }finally {
            if(lock.isLocked()&&lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
       if(Objects.isNull(machineNumber)){
           // todo log err info
           // exit start
           int exitCode = SpringApplication.exit(context, () -> 0);
           System.exit(exitCode);
       }else {
           SnowFlake.init(0,machineNumber);
       }
    }
}
