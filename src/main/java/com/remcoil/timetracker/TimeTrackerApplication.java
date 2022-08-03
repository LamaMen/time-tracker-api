package com.remcoil.timetracker;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@SecurityScheme(name = "time-tracker", scheme = "bearer", bearerFormat = "JWT",type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class TimeTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimeTrackerApplication.class, args);
    }
}
