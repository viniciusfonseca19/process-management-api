package com.process.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean(name = "simpleExecutor")
    public Executor simpleExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}