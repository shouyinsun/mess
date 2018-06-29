package com.cash.springBoot.app.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 江苏联通拉取上行job
 * author cash
 * create 2017-05-04-20:14
 **/

@Component
public class CashScheduler {

    private final static Logger logger = LoggerFactory.getLogger(CashScheduler.class);



    @Scheduled(fixedDelayString = "10000")
    public void run(){
        logger.debug("--------------debug");
        logger.info("--------------info");
        logger.warn("--------------warn");
        logger.error("--------------error");
    }

}

