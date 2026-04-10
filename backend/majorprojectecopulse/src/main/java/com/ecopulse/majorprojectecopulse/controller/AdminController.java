package com.ecopulse.majorprojectecopulse.controller;

import com.ecopulse.majorprojectecopulse.dto.LeaderboardEntry;
import com.ecopulse.majorprojectecopulse.model.CarbonWallet;
import com.ecopulse.majorprojectecopulse.model.User;
import com.ecopulse.majorprojectecopulse.model.UserActivity;
import com.ecopulse.majorprojectecopulse.repository.UserActivityRepository;
import com.ecopulse.majorprojectecopulse.repository.UserRepository;
import com.ecopulse.majorprojectecopulse.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired private WalletRepository       walletRepo;
    @Autowired private UserRepository         userRepo;
    @Autowired private UserActivityRepository activityRepo;

    /**
     * GET /api/admin/leaderboard
     * Returns top 50 students with REAL NAMES (not object IDs).
     * Fixes the gap: joins wallet → user to pull name + department.
     */
    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard() {
        List<CarbonWallet> wallets = walletRepo.findAll().stream()
            .sorted(Comparator.comparingDouble(CarbonWallet::getCarbonCredits).reversed())
            .limit(50)
            .collect(Collectors.toList());

        // Batch-fetch all user IDs in one query
        List<String> userIds = wallets.stream()
            .map(CarbonWallet::getUserId)
            .collect(Collectors.toList());

        Map<String, User> userMap = userRepo.findAllById(userIds).stream()
            .collect(Collectors.toMap(User::getId, u -> u));

        return wallets.stream().map(w -> {
            User u = userMap.get(w.getUserId());
            String name = u != null ? u.getName() : "Student";
            String dept = u != null && u.getDepartment() != null ? u.getDepartment() : "—";
            return new LeaderboardEntry(
                w.getUserId(), name, dept,
                w.getCarbonCredits(), w.getTotalCO2e(),
                w.getSustainabilityScore(), w.getLevel(), w.getRewardPoints()
            );
        }).collect(Collectors.toList());
    }

    /**
     * GET /api/admin/stats/departments
     * Average CO₂ per department from Kaggle-seeded data.
     */
    @GetMapping("/stats/departments")
    public Map<String, Object> getDeptStats() {
        List<UserActivity> all = activityRepo.findAll();
        Map<String, DoubleSummaryStatistics> stats = all.stream()
            .filter(a -> a.getDepartment() != null && !a.getDepartment().isBlank())
            .collect(Collectors.groupingBy(
                UserActivity::getDepartment,
                Collectors.summarizingDouble(UserActivity::getCo2e)
            ));

        Map<String, Object> result = new LinkedHashMap<>();
        stats.forEach((dept, s) -> result.put(dept, Map.of(
            "avgCO2",   Math.round(s.getAverage() * 100.0) / 100.0,
            "totalCO2", Math.round(s.getSum()     * 100.0) / 100.0,
            "count",    s.getCount()
        )));
        return result;
    }

    /**
     * GET /api/admin/stats/activities
     * Activity type CO₂ distribution + student count.
     */
    @GetMapping("/stats/activities")
    public Map<String, Object> getActivityStats() {
        List<UserActivity> all = activityRepo.findAll();
        Map<String, Long>   countByType = all.stream()
            .collect(Collectors.groupingBy(UserActivity::getActivityType, Collectors.counting()));
        Map<String, Double> co2ByType   = all.stream()
            .collect(Collectors.groupingBy(
                UserActivity::getActivityType,
                Collectors.summingDouble(UserActivity::getCo2e)
            ));
        long totalStudents = all.stream().map(UserActivity::getUserId).distinct().count();

        return Map.of(
            "countByType",   countByType,
            "co2ByType",     co2ByType,
            "totalStudents", totalStudents
        );
    }
}
