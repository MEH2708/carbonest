package com.ecopulse.majorprojectecopulse.controller;

import com.ecopulse.majorprojectecopulse.model.UserBadge;
import com.ecopulse.majorprojectecopulse.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/badges")
@CrossOrigin(origins = "*")
public class BadgeController {

    @Autowired private BadgeService badgeService;

    /** GET /api/badges/{userId} */
    @GetMapping("/{userId}")
    public List<UserBadge> getUserBadges(@PathVariable String userId) {
        return badgeService.getUserBadges(userId);
    }
}
