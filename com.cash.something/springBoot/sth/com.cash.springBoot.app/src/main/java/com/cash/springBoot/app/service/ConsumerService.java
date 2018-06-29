package com.cash.springBoot.app.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

/**
 * author cash
 * create 2017-07-28-15:17
 **/
@Component
public class ConsumerService {

    @HystrixCommand(fallbackMethod = "fallback")
    public String consumer() throws InterruptedException {
        Thread.sleep(5000L);
        return "ok";
    }
    public String fallback() {
        return "fallback";
    }
}
