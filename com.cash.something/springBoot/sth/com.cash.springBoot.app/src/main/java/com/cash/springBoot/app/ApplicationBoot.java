package com.cash.springBoot.app;

import com.cash.springBoot.app.model.Events;
import com.cash.springBoot.app.model.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cash on 2017/5/15.
 */
@RestController
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = {"com.cash.springBoot.app"})
@EnableDiscoveryClient
@EnableAsync
@EnableCircuitBreaker
@ComponentScan(basePackages = {"com.cash.springBoot.app"})
public class ApplicationBoot extends SpringBootServletInitializer implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 状态机
     */
    @Autowired
    private StateMachine<States, Events> stateMachine;

    @RequestMapping(value = "/logLevel", method = RequestMethod.GET)
    public String logLevel() {

        if(logger.isDebugEnabled()){
            return "debug";
        }else if(logger.isInfoEnabled()){
            return "info";
        }else if(logger.isErrorEnabled()){
            return "error";
        }

        return "";
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(ApplicationBoot.class).run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
    }
}