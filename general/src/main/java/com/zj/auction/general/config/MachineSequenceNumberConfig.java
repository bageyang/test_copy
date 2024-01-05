package com.zj.auction.general.config;

import com.zj.auction.common.constant.RedisConstant;
import com.zj.auction.common.dto.MachineSnDto;
import com.zj.auction.common.util.IPUtils;
import com.zj.auction.common.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Configuration
@EnableScheduling
@Slf4j
public class MachineSequenceNumberConfig implements ApplicationRunner {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private RedissonClient redisson;
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
                Set<String> snObjSet = redisTemplate.keys(RedisConstant.MACHINE_SEQUENCE_KEY+"*");
                if(Objects.isNull(snObjSet)){
                    machineNumber = 1;
                }else {
                    Set<Integer> snSet = snObjSet.stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toSet());
                    int i = snSet.size()+1;
                    for (int j = 0; j <= i; j++) {
                        if(!snSet.contains(j)){
                            machineNumber = j;
                            break;
                        }
                    }
                }
            }
            if(Objects.isNull(machineNumber)){
                // exit start
                log.error("全局ID机器码初始化失败");
                int exitCode = SpringApplication.exit(context, () -> 0);
                System.exit(exitCode);
            }else {
                MachineSnDto machineSnDto = getMachineSnDto();
                redisTemplate.opsForValue().set(RedisConstant.MACHINE_SEQUENCE_KEY+machineNumber,machineSnDto,MACHINE_SEQUENCE_EXPRESS_TIME);
                log.info("全局ID机器码已初始化,初始时间:{},机器码:{}",System.currentTimeMillis(),machineNumber);
                SnowFlake.init(0,machineNumber);
            }
        }finally {
            if(lock.isLocked()&&lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    private MachineSnDto getMachineSnDto() {
        MachineSnDto machineSnDto = new MachineSnDto();
        String localAddress = IPUtils.getLocalAddress();
        machineSnDto.setIp(localAddress);
        machineSnDto.setActiveTime(LocalDateTime.now());
        machineSnDto.setServerName(context.getApplicationName());
        return machineSnDto;
    }

    @Async("customThreadPool")
    @Scheduled(fixedDelay = 1,timeUnit = TimeUnit.MINUTES)
    public void configureTasks() {
        long initFLag = SnowFlake.getInitFLag();
        long machineId = SnowFlake.getMachineId();
        if(initFLag!=-1){
            long now = System.currentTimeMillis();
            if((now-initFLag)<(60*1000)){
                log.info("全局ID机器码已初始化,初始时间:{}",initFLag);
            }else {
                log.info("续存机器码:{},上次续存时间:{}",SnowFlake.getMachineId(),SnowFlake.getInitFLag());
                RLock lock = redisson.getLock(RedisConstant.MACHINE_SEQUENCE_LOCK_KEY);
                try {
                    if (lock.tryLock()) {
                        redisTemplate.expire(RedisConstant.MACHINE_SEQUENCE_KEY+machineId,MACHINE_SEQUENCE_EXPRESS_TIME, TimeUnit.SECONDS);
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
