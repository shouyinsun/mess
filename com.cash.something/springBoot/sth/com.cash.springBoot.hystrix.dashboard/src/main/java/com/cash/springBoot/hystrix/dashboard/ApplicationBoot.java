package com.cash.springBoot.hystrix.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 *hystrix dashboard
 *author cash
 *create 2017/7/28-18:05
**/

@EnableHystrixDashboard
@SpringCloudApplication
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