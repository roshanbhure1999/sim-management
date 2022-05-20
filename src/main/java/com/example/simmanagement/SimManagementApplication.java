package com.example.simmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@ComponentScan(basePackages = "com.example.simmanagement")
public class SimManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimManagementApplication.class, args);

    }

}
