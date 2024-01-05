package com.zj.auction.seckill.config;

import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.util.SnowFlake;
import com.zj.auction.seckill.service.RedisService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Configuration
@EnableScheduling
public class MachineSequenceNumberConfig implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MachineSequenceNumberConfig.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private Redisson redisson;
    @Autowired
    private ConfigurableApplicationContext context;
    /**
     * 失效时间 秒
     */
    private static final long MACHINE_SEQUENCE_EXPRESS_TIME = 5*60;

    @Override
    public void run(ApplicationArguments args) {
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
            if(Objects.isNull(machineNumber)){
                // todo log err info
                // exit start
                LOGGER.error("全局ID机器码初始化失败");
                int exitCode = SpringApplication.exit(context, () -> 0);
                System.exit(exitCode);
            }else {
                redisService.sAddExpress(RedisConstant.MACHINE_SEQUENCE_KEY,MACHINE_SEQUENCE_EXPRESS_TIME,machineNumber);
                LOGGER.info("全局ID机器码已初始化,初始时间:{},机器码:{}",System.currentTimeMillis(),machineNumber);
                SnowFlake.init(0,machineNumber);
            }
        }finally {
            if(lock.isLocked()&&lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    @Async("customThreadPool")
    @Scheduled(fixedDelay = 1,timeUnit = TimeUnit.MINUTES)
    public void configureTasks() {
        long initFLag = SnowFlake.getInitFLag();
        if(initFLag!=-1){
            long now = System.currentTimeMillis();
            if((now-initFLag)<(60*1000)){
                LOGGER.info("全局ID机器码已初始化,初始时间:{}",initFLag);
            }else {
                LOGGER.info("续存机器码:{},上次续存时间:{}",SnowFlake.getMachineId(),SnowFlake.getInitFLag());
                RLock lock = redisson.getLock(RedisConstant.MACHINE_SEQUENCE_LOCK_KEY);
                try {
                    if (lock.tryLock()) {
                        redisService.sAddExpress(RedisConstant.MACHINE_SEQUENCE_KEY,MACHINE_SEQUENCE_EXPRESS_TIME,SnowFlake.getMachineId());
                        SnowFlake.setInitFLag(now);
                    }
                }finally {
                    if(lock.isLocked()&&lock.isHeldByCurrentThread()){
                        lock.unlock();
                    }
                }
            }
        }

    }

}
