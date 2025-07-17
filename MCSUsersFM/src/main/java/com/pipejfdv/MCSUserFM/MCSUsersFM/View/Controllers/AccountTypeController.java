package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.AccountTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.AccountTypePresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.AccountTypeContract;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class AccountTypeController implements AccountTypeContract.view{
    private final AccountTypePresenter presenter;

    public AccountTypeController(AccountTypePresenter presenter) {
        this.presenter = presenter;
    }

    @GetMapping("/AcTypes/List")
    @Override
    public List<AccountType> showAccountTypes() {
        return presenter.getListAccountTypes();
    }

    @DeleteMapping("/AcTypes/{id}")
    @Override
    public void showDeleteAccountType(@PathVariable UUID id) {
        presenter.deleteAccountType(id);
    }

    @PostMapping("/AcTypes/create")
    @Override
    public AccountTypeDTO showCreateAccountType(@RequestBody AccountType accountType) {
        return presenter.createAccountType(accountType);
    }

    @PutMapping("/AcTypes/update/{id}")
    @Override
    public AccountTypeDTO showUpdateAccountType(@PathVariable UUID id, @RequestBody AccountType accountType) {
        return presenter.updateAccountType(id, accountType);
    }
}
