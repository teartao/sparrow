package com.sparrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class RedisDistributedLockApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RedisDistributedLockApplication.class, args);
    }
}
