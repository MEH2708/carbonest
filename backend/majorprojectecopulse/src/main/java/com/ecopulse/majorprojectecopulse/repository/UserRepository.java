package com.ecopulse.majorprojectecopulse.repository;

import com.ecopulse.majorprojectecopulse.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
