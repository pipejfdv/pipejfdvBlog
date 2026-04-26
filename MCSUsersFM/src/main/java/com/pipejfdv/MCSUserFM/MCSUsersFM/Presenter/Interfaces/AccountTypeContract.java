package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.AccountTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AccountTypeContract {
    interface view{
        ResponseEntity<ApiResponseOK<AccountTypeDTO>> showAccountType(UUID id, String nameAccountType);
        ResponseEntity<ApiResponseOK<List<AccountTypeDTO>>> showAccountTypes();
        ResponseEntity<ApiResponseOK<AccountTypeDTO>> showDeleteAccountType(UUID idAccount, String nameAccountType);
        ResponseEntity<ApiResponseOK<AccountTypeDTO>> showCreateAccountType(AccountType accountType);
        ResponseEntity<ApiResponseOK<AccountTypeDTO>> showUpdateAccountType(UUID idAccount, AccountType accountType);
    }
    interface Presenter{
        AccountType getAccountType(UUID id, String account);
        List<AccountType> getListAccountTypes();
        void deleteAccountType(UUID idAccount) throws IdNotFoundException;
        AccountType createAccountType(AccountType accountType);
        AccountType updateAccountType(UUID idAccount, AccountType accountType);
    }
    interface AccountTypeModel{
        AccountType readyAccountType(UUID id, String account) throws NameNotFoundException, IdNotFoundException;
        List<AccountType> listAccountTypes();
        void deleted (UUID idAccount) throws IdNotFoundException;
        AccountType created(AccountType accountType) throws DuplicateElementException;
        AccountType updated(UUID idAccount, AccountType accountType);
    }

}
