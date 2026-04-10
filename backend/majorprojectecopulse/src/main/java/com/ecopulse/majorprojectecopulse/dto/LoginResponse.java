package com.ecopulse.majorprojectecopulse.dto;

public class LoginResponse {
    private String id;
    private String name;
    private String email;
    private String department;
    private String studentId;
    private String role;
    private String token;

    public LoginResponse(String id, String name, String email,
                         String department, String studentId,
                         String role, String token) {
        this.id         = id;
        this.name       = name;
        this.email      = email;
        this.department = department;
        this.studentId  = studentId;
        this.role       = role;
        this.token      = token;
    }

    public String getId()          { return id; }
    public String getName()        { return name; }
    public String getEmail()       { return email; }
    public String getDepartment()  { return department; }
    public String getStudentId()   { return studentId; }
    public String getRole()        { return role; }
    public String getToken()       { return token; }
}
