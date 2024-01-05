package com.zj.auction.general.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

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
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程初始化
        executor.initialize();
        return executor;
    }
}
