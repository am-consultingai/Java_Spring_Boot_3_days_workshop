package org.kahoot.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot main application class - enables auto-configuration and component scanning
@SpringBootApplication
public class Main {
    // Application entry point - starts embedded Tomcat server
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
