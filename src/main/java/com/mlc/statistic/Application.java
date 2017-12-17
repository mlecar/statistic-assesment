package com.mlc.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// @EnableAsync
@ImportResource("statistics-beans.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
