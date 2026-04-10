package com.ecopulse.majorprojectecopulse.controller;

import com.ecopulse.majorprojectecopulse.model.UserActivity;
import com.ecopulse.majorprojectecopulse.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired private ActivityService activityService;

    /** POST /api/activities/log
     *  Body: { userId, activityType, value, department }
     */
    @PostMapping("/log")
    public UserActivity log(@RequestBody UserActivity activity) {
        return activityService.logActivity(activity);
    }

    /** GET /api/activities/{userId} */
    @GetMapping("/{userId}")
    public List<UserActivity> getUserActivities(@PathVariable String userId) {
        return activityService.getUserActivities(userId);
    }

    /** GET /api/activities/dept/{department} */
    @GetMapping("/dept/{department}")
    public List<UserActivity> getByDept(@PathVariable String department) {
        return activityService.getByDepartment(department);
    }
}
