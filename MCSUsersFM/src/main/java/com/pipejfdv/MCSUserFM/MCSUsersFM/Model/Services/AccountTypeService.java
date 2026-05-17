package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.AccountTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.AccountTypeContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
* Service for managing Account Type entities
* Handles CRUD operations and lookup by ID or name
*/
@Service
public class AccountTypeService implements AccountTypeContract.AccountTypeModel {
    private final AccountTypeRepository accountTypeRepository;
    /*CONSTRUCTOR*/
    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }
    /*CRUD*/
    /*
    * Retrieves an account type by ID or name
    * @Params id The UUID of the account type (optional)
    * @Params account The name of the account type (optional)
    * @Return AccountType The found account type
    * @Throw IdNotFoundException if searched by ID and not found
    * @Throw NameNotFoundException if searched by name and not found
    */
    public AccountType readyAccountType(UUID id, String account) throws IdNotFoundException, NameNotFoundException {
        if(id == null){
            return accountTypeRepository.findAccountTypeByName(account)
                    .orElseThrow(() -> new NameNotFoundException(account));
        }
        if (account == null || account.isBlank()){
            return accountTypeRepository.findById(id)
                    .orElseThrow(()-> new IdNotFoundException(id));
        }
        return null;
    }

    /*
    * Retrieves all account types
    * @Return List of all account types
    */
    public List<AccountType> listAccountTypes() {
        return accountTypeRepository.findAll();
    }

    /*
    * Deletes an account type by its ID
    * @Params idAccount The UUID of the account type to delete
    */
    public void deleted(UUID idAccount) {
        AccountType accountType = accountTypeRepository.findById(idAccount)
                .orElseThrow(()-> new IdNotFoundException(idAccount));
        accountTypeRepository.delete(accountType);
    }

    /*
    * Creates a new account type
    * @Params accountType The account type entity to create
    * @Return AccountType The saved account type
    * @Throw DuplicateElementException if name already exists
    */
    public AccountType created(AccountType accountType) throws DuplicateElementException {
        if (accountTypeRepository.existsByName(accountType.getName())) {
            throw new DuplicateElementException(accountType.getName());
        }
        accountType.setId(UUID.randomUUID());
        accountTypeRepository.save(accountType);
        return accountTypeRepository.findAccountTypeByName(accountType.getName())
                .orElseThrow(() -> new NameNotFoundException(accountType.getName()));
    }

    /*
    * Updates an existing account type name
    * @Params idAccount The UUID of the account type to update
    * @Params accountType The account type entity with the new name
    * @Return AccountType The updated account type
    */
    public AccountType updated(UUID idAccount, AccountType accountType) {
        AccountType existingAccountType = accountTypeRepository.findById(idAccount)
                .orElseThrow(()-> new IdNotFoundException(idAccount));
        existingAccountType.setName(accountType.getName());
        accountTypeRepository.save(existingAccountType);
        return existingAccountType;
    }
}
