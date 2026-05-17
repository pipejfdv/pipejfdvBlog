package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
/*
* Repository for managing User entity database operations
*/
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // validation methods for create users
    boolean existsByEmail(@NotBlank(message = "the email is void") String email);
    boolean existsByUsername(@NotBlank(message = "the username is void") String username);
    /*
    This method use Optional so that it can return values null or not null values
     */
    /*
    * Finds a user by their username
    * @Param username the username to search for
    * @Return an Optional containing the User if found, or empty if not
    */
    Optional<User> findByUsername(@NotBlank(message = "the username is void") String username);
}
