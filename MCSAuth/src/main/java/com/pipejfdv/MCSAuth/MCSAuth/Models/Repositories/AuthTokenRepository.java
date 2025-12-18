package com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    /*
    search for token for its complete removal from the user
     */
    List<AuthToken> findByUserIdFM(UUID userId);

    /*
    * search token specify
    */
    AuthToken findByToken(String token);

    /*
    Update credentials token when user try to inside sensible information
    and is authorized
     */
    @Transactional
    @Modifying
    @Query("UPDATE AuthToken a SET a.token = :token WHERE a.userIdFM = :userIdFM")
    void updateToken(@Param("token") String token, @Param("userIdFM") UUID userIdFM);
}
