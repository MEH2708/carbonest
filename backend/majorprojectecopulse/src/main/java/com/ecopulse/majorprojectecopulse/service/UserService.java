package com.ecopulse.majorprojectecopulse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecopulse.majorprojectecopulse.config.JwtUtil;
import com.ecopulse.majorprojectecopulse.dto.LoginResponse;
import com.ecopulse.majorprojectecopulse.model.User;
import com.ecopulse.majorprojectecopulse.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepo;
    @Autowired private JwtUtil        jwtUtil;

    public LoginResponse register(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        if (user.getRole() == null) user.setRole("STUDENT");
        User saved = userRepo.save(user);
        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        return new LoginResponse(
            saved.getId(), saved.getName(), saved.getEmail(),
            saved.getDepartment(), saved.getStudentId(), saved.getRole(), token
        );
    }

    public LoginResponse login(String email, String password) {
        User u = userRepo.findByEmail(email)
            .filter(user -> password.equals(user.getPassword()))
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String token = jwtUtil.generateToken(u.getId(), u.getEmail(), u.getRole());
        return new LoginResponse(
            u.getId(), u.getName(), u.getEmail(),
            u.getDepartment(), u.getStudentId(), u.getRole(), token
        );
    }

    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }
}
