package com.ecopulse.majorprojectecopulse.repository;

import com.ecopulse.majorprojectecopulse.model.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BadgeRepository extends MongoRepository<Badge, String> {}
