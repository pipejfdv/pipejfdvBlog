package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(@NotBlank(message = "the email is void") String email);
    /*
    This method use Optional so that it can return values null or not null values
     */
    Optional<User> findByUsername(@NotBlank(message = "the username is void") String username);
}
