package com.project.hb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootApplication
public class HbApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbApplication.class, args);
    }
}
