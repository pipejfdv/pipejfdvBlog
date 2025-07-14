package com.pipejfdv.MCSUserFM.MCSUsersFM.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.ModelsDTO.AccountTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Repositories.AccountTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    /*CONSTRUCTOR*/
    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }
    /*CRUD*/
    public List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
    }

    public void deleteAccountType(UUID idAccount) {
        AccountType accountType = accountTypeRepository.findById(idAccount)
                .orElseThrow(()-> new IllegalArgumentException("Account type not found"));
        accountTypeRepository.delete(accountType);
    }

    public AccountTypeDTO createAccountType(AccountType accountType) {
        if (accountTypeRepository.existsByName(accountType.getName())) {
            throw new IllegalArgumentException("Account type already exists");
        }
        accountTypeRepository.save(accountType);
        return new AccountTypeDTO(accountType.getName());
    }

    public AccountTypeDTO updateAccountType(UUID idAccount, AccountType accountType) {
        AccountType existingAccountType = accountTypeRepository.findById(idAccount)
                .orElseThrow(()-> new IllegalArgumentException("Account type not found"));
        existingAccountType.setName(accountType.getName());
        accountTypeRepository.save(existingAccountType);
        return new AccountTypeDTO(existingAccountType.getName());
    }
}
