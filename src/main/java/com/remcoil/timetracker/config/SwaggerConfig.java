package com.remcoil.timetracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                version = "0.0.4",
                title = "Time tracker",
                description = "System for tracking the time of work on the project for RemCoil employees."
        ),
        servers = {
                @Server(url = "https://time-tracker-develop.herokuapp.com", description = "Dev server"),
                @Server(url = "http://localhost:8080", description = "Test server")
        }
)
public class SwaggerConfig {
}