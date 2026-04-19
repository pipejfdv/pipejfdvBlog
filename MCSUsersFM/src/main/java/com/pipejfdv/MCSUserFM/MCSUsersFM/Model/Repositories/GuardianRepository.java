package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface GuardianRepository extends JpaRepository<Guardian, UUID> {
    boolean existsByName(String name);
    boolean existsByLastname(String lastName);
    @Transactional
    @Query("SELECT g FROM Guardian g WHERE g.user = :userId")
    Optional<Guardian> findGuardianForUser(@Param("userId") User userId);
}
