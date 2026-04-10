package com.ecopulse.majorprojectecopulse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

/**
 * Mirrors:
 *  - MongoDB collection "users"
 *  - Supabase public.profiles table
 *  - Kaggle dataset: student_id, department, sustainability_score
 */

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String department;
    private String studentId;
    private String role = "STUDENT";
    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {}

    public String getId()                        { return id; }
    public void   setId(String id)               { this.id = id; }
    public String getName()                      { return name; }
    public void   setName(String name)           { this.name = name; }
    public String getEmail()                     { return email; }
    public void   setEmail(String email)         { this.email = email; }
    public String getPassword()                  { return password; }
    public void   setPassword(String password)   { this.password = password; }
    public String getDepartment()                { return department; }
    public void   setDepartment(String d)        { this.department = d; }
    public String getStudentId()                 { return studentId; }
    public void   setStudentId(String s)         { this.studentId = s; }
    public String getRole()                      { return role; }
    public void   setRole(String role)           { this.role = role; }
    public LocalDateTime getCreatedAt()          { return createdAt; }
    public void   setCreatedAt(LocalDateTime t)  { this.createdAt = t; }
}
