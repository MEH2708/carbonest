package com.ecopulse.majorprojectecopulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MajorprojectecopulseApplication {

    public static void main(String[] args) {
    String port = System.getenv("PORT");
    if (port == null) {
        port = "10000"; // fallback
    }

    System.setProperty("server.port", port);

    SpringApplication.run(MajorprojectecopulseApplication.class, args);
}
}