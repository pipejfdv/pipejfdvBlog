package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.AccountTypeDTO;

import java.util.List;
import java.util.UUID;

public interface AccountTypeContract {
    interface view{
        List<AccountType> showAccountTypes();
        void showDeleteAccountType(UUID idAccount);
        AccountTypeDTO showCreateAccountType(AccountType accountType);
        AccountTypeDTO showUpdateAccountType(UUID idAccount, AccountType accountType);
    }
    interface Presenter{
        List<AccountType> getListAccountTypes();
        void deleteAccountType(UUID idAccount) throws IdNotFoundException;
        AccountTypeDTO createAccountType(AccountType accountType);
        AccountTypeDTO updateAccountType(UUID idAccount, AccountType accountType);
    }
    interface AccountTypeModel{
        List<AccountType> listAccountTypes();
        void deleted (UUID idAccount) throws IdNotFoundException;
        AccountTypeDTO created(AccountType accountType);
        AccountTypeDTO updated(UUID idAccount, AccountType accountType);
    }

}
