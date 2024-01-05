package com.zj.auction.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
@EnableScheduling
public class CommonThreadPool {

    @Bean("customThreadPool")
    public Executor customAsyncThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //最大线程数
        executor.setMaxPoolSize(8);
        //核心线程数
        executor.setCorePoolSize(4);
        //任务队列的大小
        executor.setQueueCapacity(0);
        //线程池名的前缀
        executor.setThreadNamePrefix("通用线程");
        //允许线程的空闲时间30秒
        executor.setKeepAliveSeconds(60);
        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程初始化
        executor.initialize();
        return executor;
    }
}
