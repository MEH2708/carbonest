package com.ecopulse.majorprojectecopulse.dto;

public class LeaderboardEntry {
    private String userId;
    private String name;
    private String department;
    private double carbonCredits;
    private double totalCO2e;
    private double sustainabilityScore;
    private String level;
    private int    rewardPoints;

    public LeaderboardEntry(String userId, String name, String department,
                            double carbonCredits, double totalCO2e,
                            double sustainabilityScore, String level, int rewardPoints) {
        this.userId             = userId;
        this.name               = name;
        this.department         = department;
        this.carbonCredits      = carbonCredits;
        this.totalCO2e          = totalCO2e;
        this.sustainabilityScore = sustainabilityScore;
        this.level              = level;
        this.rewardPoints       = rewardPoints;
    }

    public String getUserId()              { return userId; }
    public String getName()               { return name; }
    public String getDepartment()         { return department; }
    public double getCarbonCredits()      { return carbonCredits; }
    public double getTotalCO2e()          { return totalCO2e; }
    public double getSustainabilityScore(){ return sustainabilityScore; }
    public String getLevel()             { return level; }
    public int    getRewardPoints()       { return rewardPoints; }
}
