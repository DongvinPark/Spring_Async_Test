package com.async_test;

import java.util.concurrent.Executor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncTestApplication {

    public static void main(String[] args) {
        // close the application context to shut down the custom ExecutorService
        SpringApplication.run(AsyncTestApplication.class, args);
    }

}
