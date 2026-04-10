package com.ecopulse.majorprojectecopulse.controller;

import com.ecopulse.majorprojectecopulse.model.CarbonWallet;
import com.ecopulse.majorprojectecopulse.model.UserActivity;
import com.ecopulse.majorprojectecopulse.repository.UserActivityRepository;
import com.ecopulse.majorprojectecopulse.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired private WalletRepository       walletRepo;
    @Autowired private UserActivityRepository activityRepo;

    /** GET /api/dashboard/{userId} */
    @GetMapping("/{userId}")
    public Map<String, Object> getDashboard(@PathVariable String userId) {
        CarbonWallet wallet = walletRepo.findByUserId(userId).orElse(null);
        List<UserActivity> acts = activityRepo.findByUserId(userId);

        double totalCO2 = acts.stream().mapToDouble(UserActivity::getCo2e).sum();

        Map<String, Double> co2ByType = new LinkedHashMap<>();
        acts.forEach(a -> co2ByType.merge(
            a.getActivityType(), a.getCo2e(), Double::sum));

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("credits",             wallet != null ? wallet.getCarbonCredits()       : 0);
        res.put("totalCO2",            Math.round(totalCO2 * 100.0) / 100.0);
        res.put("activityCount",       acts.size());
        res.put("rewardPoints",        wallet != null ? wallet.getRewardPoints()         : 0);
        res.put("level",               wallet != null ? wallet.getLevel()                : "Bronze");
        res.put("sustainabilityScore", wallet != null ? wallet.getSustainabilityScore()  : 0);
        res.put("electricityKwh",      wallet != null ? wallet.getElectricityKwh()       : 0);
        res.put("transportKm",         wallet != null ? wallet.getTransportKm()          : 0);
        res.put("mealsLogged",         wallet != null ? wallet.getMealsLogged()          : 0);
        res.put("wasteKg",             wallet != null ? wallet.getWasteKg()              : 0);
        res.put("co2ByType",           co2ByType);
        return res;
    }
}
