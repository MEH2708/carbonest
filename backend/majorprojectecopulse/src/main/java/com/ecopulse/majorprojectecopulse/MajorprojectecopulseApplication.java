package com.ecopulse.majorprojectecopulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MajorprojectecopulseApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MajorprojectecopulseApplication.class)
                .properties("server.port=${PORT:10000}")
                .run(args);
    }
}