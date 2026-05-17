package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.AccountTypeService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.AccountTypeContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
/*
* Presenter for Account Type operations
* Acts as intermediary between controller and AccountTypeService
*/
@Component
public class AccountTypePresenter implements AccountTypeContract.Presenter {

    private final AccountTypeService service;

    public AccountTypePresenter(AccountTypeService service) {
        this.service = service;
    }

    /*
    * Retrieves an account type by ID or name
    * @Params id The UUID of the account type (optional)
    * @Params account The name of the account type (optional)
    * @Return AccountType The found account type
    */
    @Override
    public AccountType getAccountType(UUID id, String account) {
        return service.readyAccountType(id, account);
    }

    /*
    * Retrieves all account types
    * @Return List of all account types
    */
    @Override
    public List<AccountType> getListAccountTypes() {
        return service.listAccountTypes();
    }

    /*
    * Deletes an account type by ID
    * @Params idAccount The UUID of the account type to delete
    * @Throw IdNotFoundException if account type not found
    */
    @Override
    public void deleteAccountType(UUID idAccount) throws IdNotFoundException {
        service.deleted(idAccount);
    }

    /*
    * Creates a new account type
    * @Params accountType The account type entity to create
    * @Return AccountType The created account type
    */
    @Override
    public AccountType createAccountType(AccountType accountType) {
        return service.created(accountType);
    }

    /*
    * Updates an existing account type
    * @Params idAccount The UUID of the account type to update
    * @Params accountType The account type entity with updated data
    * @Return AccountType The updated account type
    */
    @Override
    public AccountType updateAccountType(UUID idAccount, AccountType accountType) {
        return service.updated(idAccount, accountType);
    }
}
