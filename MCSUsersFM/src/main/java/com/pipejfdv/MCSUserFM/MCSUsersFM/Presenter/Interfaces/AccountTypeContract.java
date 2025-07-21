package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.AccountTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.Responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface AccountTypeContract {
    interface view{
        ResponseEntity<ApiResponse<AccountTypeDTO>> showAccountType(UUID id);
        ResponseEntity<ApiResponse<List<AccountTypeDTO>>> showAccountTypes();
        ResponseEntity<ApiResponse<AccountTypeDTO>> showDeleteAccountType(UUID idAccount);
        ResponseEntity<ApiResponse<AccountTypeDTO>> showCreateAccountType(AccountType accountType);
        ResponseEntity<ApiResponse<AccountTypeDTO>> showUpdateAccountType(UUID idAccount, AccountType accountType);
    }
    interface Presenter{
        AccountType getAccountType(UUID id);
        List<AccountType> getListAccountTypes();
        void deleteAccountType(UUID idAccount) throws IdNotFoundException;
        AccountType createAccountType(AccountType accountType);
        AccountType updateAccountType(UUID idAccount, AccountType accountType);
    }
    interface AccountTypeModel{
        AccountType readyAccountType(UUID id);
        List<AccountType> listAccountTypes();
        void deleted (UUID idAccount) throws IdNotFoundException;
        AccountType created(AccountType accountType) throws DuplicateElementException;
        AccountType updated(UUID idAccount, AccountType accountType);
    }

}
