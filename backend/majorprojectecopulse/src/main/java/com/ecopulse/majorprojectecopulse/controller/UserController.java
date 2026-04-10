package com.ecopulse.majorprojectecopulse.controller;

import com.ecopulse.majorprojectecopulse.dto.LoginResponse;
import com.ecopulse.majorprojectecopulse.model.User;
import com.ecopulse.majorprojectecopulse.repository.UserRepository;
import com.ecopulse.majorprojectecopulse.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired private UserService userService;

    /** POST /api/users/register
     *  Body: { name, email, password, department, studentId }
     *  Returns: { id, name, email, department, studentId, role, token }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            LoginResponse resp = userService.register(user);
            return ResponseEntity.ok(resp);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** POST /api/users/login?email=x&password=y
     *  Returns: { id, name, email, department, studentId, role, token }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password) {
        try {
            LoginResponse resp = userService.login(email, password);
            return ResponseEntity.ok(resp);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    /** GET /api/users/{id} — returns user info (no password) */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return userService.findById(id)
            .map(u -> {
                u.setPassword(null);
                return ResponseEntity.ok(u);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
