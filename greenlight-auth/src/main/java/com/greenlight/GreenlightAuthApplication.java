package com.greenlight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GreenlightAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenlightAuthApplication.class, args);
    }

}
