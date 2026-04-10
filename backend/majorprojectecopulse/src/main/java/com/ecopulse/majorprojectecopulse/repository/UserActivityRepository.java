package com.ecopulse.majorprojectecopulse.repository;

import com.ecopulse.majorprojectecopulse.model.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserActivityRepository extends MongoRepository<UserActivity, String> {
    List<UserActivity> findByUserId(String userId);
    List<UserActivity> findByDepartment(String department);
}
