package com.cash.springBoot.app.eureka.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *eureka server
 *author cash
 *create 2017/7/28-18:05
**/

@EnableEurekaServer
@SpringBootApplication
public class ApplicationBoot extends SpringBootServletInitializer implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());



    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(ApplicationBoot.class).run(args);
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}