package com.pipejfdv.MCSUserFM.MCSUsersFM.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Models.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, UUID> {

    Optional<AccountType> findById(UUID id);

    AccountType findAccountTypeByName(String name);

    boolean existsByName(String name);
}
