package com.ecopulse.majorprojectecopulse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "badges")
public class Badge {

    @Id private String id;
    private String name;
    private String description;
    private int    minCredits;
    private int    rewardPoints;

    public Badge() {}

    public String getId()                 { return id; }
    public void   setId(String id)        { this.id = id; }
    public String getName()               { return name; }
    public void   setName(String name)    { this.name = name; }
    public String getDescription()        { return description; }
    public void   setDescription(String d){ this.description = d; }
    public int    getMinCredits()         { return minCredits; }
    public void   setMinCredits(int m)    { this.minCredits = m; }
    public int    getRewardPoints()       { return rewardPoints; }
    public void   setRewardPoints(int r)  { this.rewardPoints = r; }
}
