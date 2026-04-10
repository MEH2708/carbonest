package com.ecopulse.majorprojectecopulse.repository;

import com.ecopulse.majorprojectecopulse.model.UserBadge;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserBadgeRepository extends MongoRepository<UserBadge, String> {
    List<UserBadge> findByUserId(String userId);
}
