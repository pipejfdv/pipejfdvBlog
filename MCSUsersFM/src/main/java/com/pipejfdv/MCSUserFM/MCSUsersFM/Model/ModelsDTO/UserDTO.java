package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;

public class UserDTO {
    final private String username;
    final private String email;
    private AccountType accountType;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserDTO(String username, String email, AccountType accountType) {
        this.username = username;
        this.email = email;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
