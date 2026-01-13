package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameAccountNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.AccountTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.AccountTypeContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountTypeService implements AccountTypeContract.AccountTypeModel {
    private final AccountTypeRepository accountTypeRepository;
    /*CONSTRUCTOR*/
    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }
    /*CRUD*/
    public AccountType readyAccountType(UUID id, String account) throws IdNotFoundException, NameAccountNotFoundException {
        if(id == null){
            return accountTypeRepository.findAccountTypeByName(account)
                    .orElseThrow(() -> new NameAccountNotFoundException(account));
        }
        if (account == null || account.isBlank()){
            return accountTypeRepository.findById(id)
                    .orElseThrow(()-> new IdNotFoundException(id));
        }
        return null;
    }

    public List<AccountType> listAccountTypes() {
        return accountTypeRepository.findAll();
    }

    public void deleted(UUID idAccount) {
        AccountType accountType = accountTypeRepository.findById(idAccount)
                .orElseThrow(()-> new IdNotFoundException(idAccount));
        accountTypeRepository.delete(accountType);
    }

    public AccountType created(AccountType accountType) {
        if (accountTypeRepository.existsByName(accountType.getName())) {
            throw new DuplicateElementException(accountType.getName());
        }
        accountType.setId(UUID.randomUUID());
        accountTypeRepository.save(accountType);
        return accountTypeRepository.findAccountTypeByName(accountType.getName())
                .orElseThrow(() -> new NameAccountNotFoundException(accountType.getName()));
    }

    public AccountType updated(UUID idAccount, AccountType accountType) {
        AccountType existingAccountType = accountTypeRepository.findById(idAccount)
                .orElseThrow(()-> new IdNotFoundException(idAccount));
        existingAccountType.setName(accountType.getName());
        accountTypeRepository.save(existingAccountType);
        return existingAccountType;
    }
}
