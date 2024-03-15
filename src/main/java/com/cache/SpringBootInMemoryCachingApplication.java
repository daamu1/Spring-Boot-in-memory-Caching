package com.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringBootInMemoryCachingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootInMemoryCachingApplication.class, args);
    }
}
