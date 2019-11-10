package com.github.gatoke.offers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class OffersApplication {

    public static void main(final String[] args) {
        SpringApplication.run(OffersApplication.class, args);
    }

}
