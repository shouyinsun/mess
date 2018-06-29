package com.cash.springBoot.app.controller;

import com.cash.springBoot.app.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hystrix控制
 * author cash
 * create 2017-07-28-15:11
 **/

@RestController
public class HystrixController {

    @Autowired
    ConsumerService consumerService;

    @RequestMapping("/hystrixTst")
    public String getStr() throws InterruptedException {
        return consumerService.consumer();
    }

}
