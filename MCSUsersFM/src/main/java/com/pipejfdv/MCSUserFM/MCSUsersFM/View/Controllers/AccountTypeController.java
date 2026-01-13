package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.AccountTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.AccountTypePresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.AccountTypeContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funnyMind")
public class AccountTypeController implements AccountTypeContract.view{
    private final AccountTypePresenter presenter;

    public AccountTypeController(AccountTypePresenter presenter) {
        this.presenter = presenter;
    }

    @GetMapping("/AcTypes/account")
    public ResponseEntity<ApiResponseOK<AccountTypeDTO>> showAccountType(
            @RequestParam (value = "id", required = false) UUID id,
            @RequestParam (value = "account", required = false) String nameAccountType)
    {
        AccountTypeDTO accountTypeDTO;
        if (id == null){
            AccountType accountType = presenter.getAccountType(null, nameAccountType);
            accountTypeDTO = new AccountTypeDTO(accountType.getName());
        }
        else if (nameAccountType == null || nameAccountType.isBlank()){
            AccountType accountType = presenter.getAccountType(id, null);
            accountTypeDTO = new AccountTypeDTO(accountType.getName());
        }
        else {
            throw new IllegalArgumentException("Wrong Parameters");
        }
        ApiResponseOK<AccountTypeDTO> response = new ApiResponseOK<>(
                "account ready",
                accountTypeDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/AcTypes/List")
    @Override
    public ResponseEntity<ApiResponseOK<List<AccountTypeDTO>>> showAccountTypes() {
        List<AccountTypeDTO> listDTO = presenter.getListAccountTypes().stream()
                .map(accountType -> new AccountTypeDTO(accountType.getName(), accountType.getId()))
                .collect(Collectors.toList());
        ApiResponseOK<List<AccountTypeDTO>> response = new ApiResponseOK<>(
                "List ready of account types",
                listDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/AcTypes")
    @Override
    public ResponseEntity<ApiResponseOK<AccountTypeDTO>> showDeleteAccountType(
            @RequestParam (value = "idAccountType", required = false) UUID id,
            @RequestParam (value = "account", required = false) String nameAccountType)
    {
        AccountType accountType;
        AccountTypeDTO accountTypeDTO;
        if(id == null){
            accountType = presenter.getAccountType(null, nameAccountType);
            accountTypeDTO = new AccountTypeDTO(accountType.getName(), accountType.getId());
            presenter.deleteAccountType(accountTypeDTO.getId());
        }
        else if (nameAccountType == null || nameAccountType.isBlank()) {
            accountType = presenter.getAccountType(id, null);
            accountTypeDTO = new AccountTypeDTO(accountType.getName(), accountType.getId());
            presenter.deleteAccountType(accountTypeDTO.getId());
        }
        else {
            throw new IllegalArgumentException("Wrong Parameters");
        }
        ApiResponseOK<AccountTypeDTO> response = new ApiResponseOK<>(
                "Account type deleted",
                accountTypeDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/AcTypes/create")
    @Override
    public ResponseEntity<ApiResponseOK<AccountTypeDTO>> showCreateAccountType(@RequestBody AccountType accountType) {
        AccountType account =presenter.createAccountType(accountType);
        AccountTypeDTO accountTypeDTO = new AccountTypeDTO(account.getName());

        ApiResponseOK<AccountTypeDTO> response = new ApiResponseOK<>(
                "create account type",
                accountTypeDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/AcTypes/update/{id}")
    @Override
    public ResponseEntity<ApiResponseOK<AccountTypeDTO>> showUpdateAccountType(@PathVariable UUID id, @RequestBody AccountType accountType) {
        AccountType accountType1 = presenter.updateAccountType(id, accountType);
        AccountTypeDTO accountTypeDTO = new AccountTypeDTO(accountType1.getName());
        ApiResponseOK<AccountTypeDTO> response = new ApiResponseOK<>(
                "update account type",
                accountTypeDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
