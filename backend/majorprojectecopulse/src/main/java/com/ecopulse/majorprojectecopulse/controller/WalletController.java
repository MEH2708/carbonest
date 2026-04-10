package com.ecopulse.majorprojectecopulse.controller;

import com.ecopulse.majorprojectecopulse.model.CarbonWallet;
import com.ecopulse.majorprojectecopulse.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "*")
public class WalletController {

    @Autowired private WalletRepository walletRepo;

    /** GET /api/wallet/{userId} */
    @GetMapping("/{userId}")
    public CarbonWallet getWallet(@PathVariable String userId) {
        return walletRepo.findByUserId(userId).orElse(null);
    }
}
