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
/*
* Repository for managing Guardian entity database operations
*/
@Repository
public interface GuardianRepository extends JpaRepository<Guardian, UUID> {
    boolean existsByName(String name);
    boolean existsByLastname(String lastName);

    @Query("SELECT g FROM Guardian g WHERE g.user = :userId")
    /*
    * Finds a guardian associated with a specific user
    * @Param userId the User entity to search for
    * @Return an Optional containing the Guardian if found, or empty if not
    */
    Optional<Guardian> findGuardianForUser(@Param("userId") User userId);
}
