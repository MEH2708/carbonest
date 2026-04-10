package com.ecopulse.majorprojectecopulse.service;

import com.ecopulse.majorprojectecopulse.model.Badge;
import com.ecopulse.majorprojectecopulse.model.CarbonWallet;
import com.ecopulse.majorprojectecopulse.model.UserBadge;
import com.ecopulse.majorprojectecopulse.repository.BadgeRepository;
import com.ecopulse.majorprojectecopulse.repository.UserBadgeRepository;
import com.ecopulse.majorprojectecopulse.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    @Autowired private BadgeRepository      badgeRepo;
    @Autowired private UserBadgeRepository  userBadgeRepo;
    @Autowired private WalletRepository     walletRepo;

    public void checkAndAssignBadges(String userId, double credits) {
        List<Badge>     allBadges = badgeRepo.findAll();
        List<UserBadge> earned    = userBadgeRepo.findByUserId(userId);

        for (Badge badge : allBadges) {
            if (credits < badge.getMinCredits()) continue;

            boolean alreadyEarned = earned.stream()
                .anyMatch(b -> b.getBadgeName().equals(badge.getName()));

            if (!alreadyEarned) {
                UserBadge ub = new UserBadge();
                ub.setUserId(userId);
                ub.setBadgeName(badge.getName());
                userBadgeRepo.save(ub);

                walletRepo.findByUserId(userId).ifPresent(w -> {
                    w.setRewardPoints(w.getRewardPoints() + badge.getRewardPoints());
                    walletRepo.save(w);
                });
            }
        }
    }

    public List<UserBadge> getUserBadges(String userId) {
        return userBadgeRepo.findByUserId(userId);
    }
}
