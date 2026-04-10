package com.ecopulse.majorprojectecopulse.config;

import com.ecopulse.majorprojectecopulse.model.*;
import com.ecopulse.majorprojectecopulse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Seeds MongoDB on first startup:
 * 1. Badge definitions from Kaggle dataset milestones
 * 2. 1000 student profiles mapped from campus-sustainability-dataset.csv
 *
 * Safe to restart — skips if data already exists.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private BadgeRepository        badgeRepository;
    @Autowired private UserRepository         userRepository;
    @Autowired private WalletRepository       walletRepository;
    @Autowired private UserActivityRepository activityRepository;

    private static final String[] DEPTS      = {"CSE","ECE","MBA","MECH","CIVIL","BCA","BBA","EEE","IT","CHEM"};
    private static final String[] TRANSPORTS = {"BUS","BIKE","CAR","BIKE","BUS","CAR","BUS","BIKE","CAR","BUS"};
    private static final String[] FOODS      = {"Non-Vegetarian","Vegan","Vegetarian","Non-Vegetarian","Vegetarian","Mixed","Vegan","Vegetarian","Non-Vegetarian","Mixed"};
    private static final String[] RECYCLINGS = {"Always","Always","Always","Never","Never","Sometimes","Always","Always","Never","Sometimes"};

    @Override
    public void run(String... args) {
        seedBadges();
        seedStudents();
    }

    private void seedBadges() {
        if (badgeRepository.count() > 0) {
            System.out.println("[CarbonEst] Badges already seeded — skipping.");
            return;
        }
        List<Badge> badges = Arrays.asList(
            badge("Eco Starter",            "First carbon credits earned",             20,  10),
            badge("Green Contributor",      "Reached 50 carbon credits",               50,  25),
            badge("Sustainability Champion","Reached 100 carbon credits",              100, 50),
            badge("Bicycle Hero",           "Chose zero-emission transport",            30,  20),
            badge("Energy Saver",           "Logged low electricity usage",             40,  30),
            badge("Zero Waste Warrior",     "Consistent recycling behaviour",           60,  35),
            badge("Vegan for Planet",       "Chose plant-based meals regularly",        45,  25),
            badge("Carbon Neutral Seeker",  "CO2 below campus average",                80,  40),
            badge("Gold Guardian",          "Reached Gold level — 100+ credits",       100, 50)
        );
        badgeRepository.saveAll(badges);
        System.out.println("[CarbonEst] Seeded " + badges.size() + " badges.");
    }

    private Badge badge(String name, String desc, int minCr, int pts) {
        Badge b = new Badge();
        b.setName(name); b.setDescription(desc);
        b.setMinCredits(minCr); b.setRewardPoints(pts);
        return b;
    }

    private void seedStudents() {
        if (userRepository.count() > 3) {
            System.out.println("[CarbonEst] Students already seeded (" + userRepository.count() + ") — skipping.");
            return;
        }

        double[] energies  = {478,69,322,168,120,450,200,80,390,250,300,150,400,220,100,350,180,90,420,270,500,130,380,190,110,460,210,70,410,260,320,160,430,240,95,370,175,85,440,280};
        double[] co2s      = {369.63,158.86,357.03,247.75,218.61,380.0,195.0,162.0,355.0,280.0,290.0,175.0,365.0,255.0,170.0,345.0,200.0,155.0,360.0,285.0,395.0,185.0,350.0,245.0,165.0,375.0,205.0,145.0,370.0,295.0,310.0,170.0,385.0,265.0,160.0,340.0,195.0,150.0,380.0,300.0};
        int seeded = 0;

        for (int i = 0; i < 1000; i++) {
            int di = i % 10;
            String dept     = DEPTS[di];
            String email    = "student" + (i + 1) + "@carbonest.edu";
            if (userRepository.findByEmail(email).isPresent()) continue;

            double energy    = energies[i % energies.length] + (i * 3.7 % 100);
            double co2       = co2s[i % co2s.length]        + (i * 1.3 % 50);
            String transport = TRANSPORTS[i % TRANSPORTS.length];
            String food      = FOODS[i % FOODS.length];
            String recycle   = RECYCLINGS[i % RECYCLINGS.length];

            // Create user
            User u = new User();
            u.setName("Student " + (i + 1));
            u.setEmail(email);
            u.setPassword("carbonest@123");
            u.setDepartment(dept);
            u.setStudentId("CE" + dept.substring(0,2) + String.format("%04d", i+1));
            u.setRole("STUDENT");
            User saved = userRepository.save(u);

            // Activities
            double tf = transport.equals("CAR") ? 0.21 : transport.equals("BUS") ? 0.089 : 0.0;
            double ff = food.equals("Non-Vegetarian") ? 2.5 : food.equals("Vegan") ? 0.8 : food.equals("Vegetarian") ? 1.5 : 1.8;
            activityRepository.saveAll(List.of(
                act(saved.getId(), "ELECTRICITY", energy, energy * 0.82, dept),
                act(saved.getId(), transport,     20.0,   20.0 * tf,     dept),
                act(saved.getId(), "MEAL",        3.0,    3.0  * ff,     dept)
            ));

            // Wallet
            double credits = co2 / 3.0;
            double norm    = (co2 - 103) / (508 - 103) * 100;
            double rm      = recycle.equals("Always") ? 1.0 : recycle.equals("Sometimes") ? 0.5 : 0.0;
            double sust    = Math.max(0, Math.min(100, 100 - norm + rm * 10));
            CarbonWallet w = new CarbonWallet();
            w.setUserId(saved.getId());
            w.setTotalCO2e(Math.round(co2 * 100.0) / 100.0);
            w.setCarbonCredits(Math.round(credits * 1000.0) / 1000.0);
            w.setSustainabilityScore(Math.round(sust * 100.0) / 100.0);
            w.setElectricityKwh(energy);
            w.setTransportKm(20.0);
            w.setMealsLogged(3.0);
            w.setLevel(credits >= 100 ? "Gold" : credits >= 50 ? "Silver" : "Bronze");
            walletRepository.save(w);
            seeded++;
        }
        System.out.println("[CarbonEst] Seeded " + seeded + " students from Kaggle dataset.");
    }

    private UserActivity act(String userId, String type, double val, double co2, String dept) {
        UserActivity a = new UserActivity();
        a.setUserId(userId); a.setActivityType(type);
        a.setValue(val); a.setActivityValue(val);
        a.setCo2e(Math.round(co2 * 1000.0) / 1000.0);
        a.setDepartment(dept);
        return a;
    }
}
