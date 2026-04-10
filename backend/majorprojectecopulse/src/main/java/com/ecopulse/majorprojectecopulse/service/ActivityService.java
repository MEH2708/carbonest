package com.ecopulse.majorprojectecopulse.service;

import com.ecopulse.majorprojectecopulse.model.CarbonWallet;
import com.ecopulse.majorprojectecopulse.model.UserActivity;
import com.ecopulse.majorprojectecopulse.repository.UserActivityRepository;
import com.ecopulse.majorprojectecopulse.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired private UserActivityRepository activityRepo;
    @Autowired private WalletRepository       walletRepo;
    @Autowired private BadgeService           badgeService;

    /**
     * CO₂ factors aligned with Kaggle campus-sustainability-dataset.csv
     * ELECTRICITY  : 0.82 kg/kWh  (India grid average)
     * CAR / TRANSPORT : 0.21 kg/km
     * BUS          : 0.089 kg/km
     * BIKE/WALKING : 0.0 kg/km  (zero emission)
     * MEAL         : 1.5 kg/meal (avg campus meal — Non-Veg=2.5, Vegan=0.8, Veg=1.5, Mixed=1.8)
     * WASTE        : 0.5 kg/kg
     * FLIGHT       : 0.255 kg/km
     * OTHER        : 0.1 per unit
     */
    private double co2Factor(String type) {
        return switch (type.toUpperCase()) {
            case "ELECTRICITY"            -> 0.82;
            case "CAR", "TRANSPORT"       -> 0.21;
            case "BUS"                    -> 0.089;
            case "BIKE"                   -> 0.0;
            case "MEAL"                   -> 1.5;
            case "WASTE"                  -> 0.5;
            case "FLIGHT"                 -> 0.255;
            default                       -> 0.1;
        };
    }

    private double sustainabilityScore(String type, double co2, double value) {
        return switch (type.toUpperCase()) {
            case "BIKE"        -> value * 2.0;
            case "ELECTRICITY" -> Math.max(0, 10 - co2);
            case "WASTE"       -> Math.max(0, 5  - co2);
            default            -> Math.max(0, 8  - co2);
        };
    }

    public UserActivity logActivity(UserActivity activity) {
        // Normalise value — accept either field name from frontend
        double val = activity.getValue() > 0 ? activity.getValue() : activity.getActivityValue();
        activity.setValue(val);
        activity.setActivityValue(val);

        String type = activity.getActivityType() != null
            ? activity.getActivityType().toUpperCase() : "OTHER";
        activity.setActivityType(type);

        double co2   = round3(val * co2Factor(type));
        double score = round3(sustainabilityScore(type, co2, val));
        activity.setCo2e(co2);
        activity.setSustainabilityScore(score);

        UserActivity saved = activityRepo.save(activity);

        // Get or create wallet
        CarbonWallet wallet = walletRepo.findByUserId(activity.getUserId())
            .orElseGet(() -> {
                CarbonWallet w = new CarbonWallet();
                w.setUserId(activity.getUserId());
                return w;
            });

        wallet.setTotalCO2e(round3(wallet.getTotalCO2e() + co2));
        wallet.setCarbonCredits(round3(wallet.getCarbonCredits() + co2 / 3.0));
        wallet.setSustainabilityScore(round3(wallet.getSustainabilityScore() + score));

        switch (type) {
            case "ELECTRICITY"          -> wallet.setElectricityKwh(round3(wallet.getElectricityKwh() + val));
            case "TRANSPORT","CAR","BUS","BIKE" -> wallet.setTransportKm(round3(wallet.getTransportKm() + val));
            case "MEAL"                 -> wallet.setMealsLogged(round3(wallet.getMealsLogged() + val));
            case "WASTE"                -> wallet.setWasteKg(round3(wallet.getWasteKg() + val));
        }

        double cr = wallet.getCarbonCredits();
        wallet.setLevel(cr >= 100 ? "Gold" : cr >= 50 ? "Silver" : "Bronze");

        walletRepo.save(wallet);
        badgeService.checkAndAssignBadges(activity.getUserId(), cr);

        return saved;
    }

    public List<UserActivity> getUserActivities(String userId) {
        return activityRepo.findByUserId(userId);
    }

    public List<UserActivity> getByDepartment(String dept) {
        return activityRepo.findByDepartment(dept);
    }

    private double round3(double v) { return Math.round(v * 1000.0) / 1000.0; }
}
