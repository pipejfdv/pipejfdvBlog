package com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    /*
    search for token for its complete removal from the user
     */
    List<AuthToken> findByUserIdFM(UUID userId);
}
