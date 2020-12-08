package com.github.gatoke.offers;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "30M")
@SpringBootApplication
public class OffersApplication {

    public static void main(final String[] args) {
        SpringApplication.run(OffersApplication.class, args);
    }

}
