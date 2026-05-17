package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*
* Repository for managing AccountType entity database operations
*/
@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, UUID> {

    Optional<AccountType> findById(UUID id);

    /*
    * Finds an account type by its name
    * @Param name the account type name to search for
    * @Return an Optional containing the AccountType if found, or empty if not
    */
    Optional<AccountType> findAccountTypeByName(String name);

    boolean existsByName(String name);
}
