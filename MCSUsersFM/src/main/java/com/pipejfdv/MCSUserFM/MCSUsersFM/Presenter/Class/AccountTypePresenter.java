package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.AccountTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.AccountTypeService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.AccountTypeContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class AccountTypePresenter implements AccountTypeContract.Presenter {

    private final AccountTypeService service;

    public AccountTypePresenter(AccountTypeService service) {
        this.service = service;
    }

    @Override
    public List<AccountType> getListAccountTypes() {
        return service.listAccountTypes();
    }

    @Override
    public void deleteAccountType(UUID idAccount) throws IdNotFoundException {
        service.deleted(idAccount);
    }

    @Override
    public AccountTypeDTO createAccountType(AccountType accountType) {
        return service.created(accountType);
    }

    @Override
    public AccountTypeDTO updateAccountType(UUID idAccount, AccountType accountType) {
        return service.updated(idAccount, accountType);
    }
}
