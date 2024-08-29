package com.dragons.mugloar;

import com.dragons.mugloar.service.TaskRunnerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class MultiThreadingConfig {

    @Value("${thread.pool.size}")
    private int threadPoolSize;

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(threadPoolSize);
    }

    @Bean
    public TaskRunnerService taskRunnerService(ExecutorService executorService) {
        return new TaskRunnerService(executorService);
    }
}