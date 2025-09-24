package com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
}
