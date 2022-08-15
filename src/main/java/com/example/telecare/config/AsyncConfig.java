package com.example.telecare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor(){
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(2);
//        threadPoolTaskExecutor.setMaxPoolSize(10);
//        threadPoolTaskExecutor.setQueueCapacity(500);
//        threadPoolTaskExecutor.setThreadNamePrefix("mainThread-");
//        return threadPoolTaskExecutor;
        return new ConcurrentTaskExecutor(Executors.newCachedThreadPool());
    }
}
