package com.dragons.mugloar.client;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OkHttpClientConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .cache(null)
                .addInterceptor(chain -> chain.proceed(
                        chain.request().newBuilder()
                                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                                .header("Pragma", "no-cache")
                                .header("Expires", "0")
                                .build()
                ))
                .build();
    }
}