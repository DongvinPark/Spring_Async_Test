package com.async_test.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {

    private static final int numberOfExpectedMaxDbIoRequest = 3350;

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        int n = Runtime.getRuntime().availableProcessors();

        executor.setCorePoolSize(2*n);
        executor.setMaxPoolSize(2*n);
        executor.setQueueCapacity(numberOfExpectedMaxDbIoRequest*3);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

}
