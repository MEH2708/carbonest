package com.ecopulse.majorprojectecopulse.repository;

import com.ecopulse.majorprojectecopulse.model.CarbonWallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
public interface WalletRepository extends MongoRepository<CarbonWallet, String> {
    Optional<CarbonWallet> findByUserId(String userId);
}
