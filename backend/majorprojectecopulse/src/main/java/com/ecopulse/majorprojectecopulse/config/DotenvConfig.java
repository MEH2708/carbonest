package com.ecopulse.majorprojectecopulse.config;

import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DotenvConfig {

    static {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("PORT", dotenv.get("PORT"));
    }
}