package com.ants.backgroundworkbench;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@SpringBootApplication
public class BackgroundWorkbenchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackgroundWorkbenchApplication.class, args);
    }

}
