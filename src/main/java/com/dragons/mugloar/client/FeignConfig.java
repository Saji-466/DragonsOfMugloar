package com.dragons.mugloar.client;

import com.dragons.mugloar.client.config.FeignLogger;
import feign.*;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import feign.okhttp.OkHttpClient;

@Configuration
public class FeignConfig {

    private final okhttp3.OkHttpClient okHttpClient;

    public FeignConfig(okhttp3.OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    @Bean
    public ErrorDecoder errorDecoder() {
        return new com.dragons.mugloar.client.config.ErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new com.dragons.mugloar.client.config.Retryer();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Logger feignLogger() {
        return new FeignLogger();
    }


    @Bean
    @Primary
    public Client feignClient() {
        return new OkHttpClient(okHttpClient);
    }
}