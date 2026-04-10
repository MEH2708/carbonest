package com.ecopulse.majorprojectecopulse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection = "user_badges")
public class UserBadge {

    @Id private String id;
    private String userId;
    private String badgeName;
    private LocalDateTime earnedAt = LocalDateTime.now();

    public UserBadge() {}

    public String getId()                       { return id; }
    public void   setId(String id)              { this.id = id; }
    public String getUserId()                   { return userId; }
    public void   setUserId(String userId)      { this.userId = userId; }
    public String getBadgeName()                { return badgeName; }
    public void   setBadgeName(String b)        { this.badgeName = b; }
    public LocalDateTime getEarnedAt()          { return earnedAt; }
    public void   setEarnedAt(LocalDateTime t)  { this.earnedAt = t; }
}
