package com.ecopulse.majorprojectecopulse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

/**
 * Mirrors Kaggle dataset columns:
 *  - transport_mode    → activityType = "TRANSPORT" | "CAR" | "BIKE" | "BUS"
 *  - electricity_kwh   → activityType = "ELECTRICITY", value = kWh
 *  - meals_per_day     → activityType = "MEAL", value = count
 *  - waste_kg          → activityType = "WASTE", value = kg
 *  - co2_emissions_kg  → co2e
 */

@Document(collection = "user_activities")
public class UserActivity {

    @Id
    private String id;

    @Indexed
    private String userId;

    private String activityType;
    private double value;
    private double activityValue;
    private double co2e;
    private double sustainabilityScore;
    private String department;
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserActivity() {}

    public String getId()                           { return id; }
    public void   setId(String id)                  { this.id = id; }
    public String getUserId()                       { return userId; }
    public void   setUserId(String userId)          { this.userId = userId; }
    public String getActivityType()                 { return activityType; }
    public void   setActivityType(String t)         { this.activityType = t; }
    public double getValue()                        { return value; }
    public void   setValue(double value)            { this.value = value; }
    public double getActivityValue()                { return activityValue; }
    public void   setActivityValue(double v)        { this.activityValue = v; }
    public double getCo2e()                         { return co2e; }
    public void   setCo2e(double co2e)              { this.co2e = co2e; }
    public double getSustainabilityScore()          { return sustainabilityScore; }
    public void   setSustainabilityScore(double s)  { this.sustainabilityScore = s; }
    public String getDepartment()                   { return department; }
    public void   setDepartment(String d)           { this.department = d; }
    public LocalDateTime getCreatedAt()             { return createdAt; }
    public void   setCreatedAt(LocalDateTime t)     { this.createdAt = t; }
}
