package com.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InnovateStay {

    public static void main(String[] args) {
        SpringApplication.run(InnovateStay.class, args);
    }

}
