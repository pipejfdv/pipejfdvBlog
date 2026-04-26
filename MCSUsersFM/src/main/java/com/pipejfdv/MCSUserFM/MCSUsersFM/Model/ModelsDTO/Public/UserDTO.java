package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;

import java.util.UUID;

public class UserDTO {
    private UUID idUser;
    private String email;
    private String username;
    private AccountType accountType;

    public UserDTO(UUID idUser, String username, String email) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
    }

    public UserDTO(String username, String email, AccountType accountType) {
        this.username = username;
        this.email = email;
        this.accountType = accountType;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
