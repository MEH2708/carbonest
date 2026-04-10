package com.ecopulse.majorprojectecopulse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing // Optional: useful for @CreatedDate and @LastModifiedDate
public class MongoConfig {
    // Basic connection is now managed via application.properties
}
