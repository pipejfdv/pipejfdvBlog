package com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
* JPA repository for managing AuthToken entities in the database.
*/
@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    /*
    * Finds all tokens belonging to a specific user
    * @Param userId UUID the user ID
    * @Return List AuthToken list of token records for the user
    */
    List<AuthToken> findByUserIdFM(UUID userId);

    /*
    * Finds a token record by its token string value
    * @Param token String the JWT token to search for
    * @Return AuthToken the matching token entity
    */
    AuthToken findByToken(String token);

    /*
    * Finds a single token record for a user using a custom JPA query
    * @Param userIdFM UUID the user ID
    * @Return Optional AuthToken the token record if found
    */
    @Transactional
    @Query("SELECT a FROM AuthToken a WHERE a.userIdFM = :userIdFM")
    Optional<AuthToken> findAnOnceUser(@Param("userIdFM")UUID userIdFM);

    /*
    * Updates the token value for all records belonging to a user
    * @Param token String the new token value
    * @Param userIdFM UUID the user ID
    */
    @Transactional
    @Modifying
    @Query("UPDATE AuthToken a SET a.token = :token WHERE a.userIdFM = :userIdFM")
    void updateToken(@Param("token") String token, @Param("userIdFM") UUID userIdFM);
}
