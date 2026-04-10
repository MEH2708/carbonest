package com.ecopulse.majorprojectecopulse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Collection name matches existing MongoDB data: "wallets"
 * Fields aligned with Kaggle dataset sustainability scoring.
 */

@Document(collection = "wallets")
public class CarbonWallet {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private double carbonCredits      = 0;
    private double totalCO2e          = 0;
    private int    rewardPoints       = 0;
    private String level              = "Bronze";
    private double sustainabilityScore = 0;
    private double electricityKwh     = 0;
    private double transportKm        = 0;
    private double mealsLogged        = 0;
    private double wasteKg            = 0;

    public CarbonWallet() {}

    public String getId()                              { return id; }
    public void   setId(String id)                     { this.id = id; }
    public String getUserId()                          { return userId; }
    public void   setUserId(String userId)             { this.userId = userId; }
    public double getCarbonCredits()                   { return carbonCredits; }
    public void   setCarbonCredits(double c)           { this.carbonCredits = c; }
    public double getTotalCO2e()                       { return totalCO2e; }
    public void   setTotalCO2e(double t)               { this.totalCO2e = t; }
    public int    getRewardPoints()                    { return rewardPoints; }
    public void   setRewardPoints(int r)               { this.rewardPoints = r; }
    public String getLevel()                           { return level; }
    public void   setLevel(String level)               { this.level = level; }
    public double getSustainabilityScore()             { return sustainabilityScore; }
    public void   setSustainabilityScore(double s)     { this.sustainabilityScore = s; }
    public double getElectricityKwh()                  { return electricityKwh; }
    public void   setElectricityKwh(double e)          { this.electricityKwh = e; }
    public double getTransportKm()                     { return transportKm; }
    public void   setTransportKm(double t)             { this.transportKm = t; }
    public double getMealsLogged()                     { return mealsLogged; }
    public void   setMealsLogged(double m)             { this.mealsLogged = m; }
    public double getWasteKg()                         { return wasteKg; }
    public void   setWasteKg(double w)                 { this.wasteKg = w; }
}
