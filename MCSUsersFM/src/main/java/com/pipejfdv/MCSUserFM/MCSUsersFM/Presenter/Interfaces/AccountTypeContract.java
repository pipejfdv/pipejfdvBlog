package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;

import java.util.List;
import java.util.UUID;

public interface AccountTypeContract {
    interface view{
        List<AccountType> showAccountTypes();
        void showDeleteAccountType(UUID idAccount);
        AccountType showCreateAccountType(AccountType accountType);
        AccountType showUpdateAccountType(UUID idAccount, AccountType accountType);
    }
    interface Presenter{
        List<AccountType> getListAccountTypes();
        void deleteAccountType(UUID idAccount) throws IdNotFoundException;
        AccountType createAccountType(AccountType accountType);
        AccountType updateAccountType(UUID idAccount, AccountType accountType);
    }
    interface AccountTypeModel{
        List<AccountType> listAccountTypes();
        void deleted (UUID idAccount) throws IdNotFoundException;
        AccountType created(AccountType accountType);
        AccountType updated(UUID idAccount, AccountType accountType);
    }

}
