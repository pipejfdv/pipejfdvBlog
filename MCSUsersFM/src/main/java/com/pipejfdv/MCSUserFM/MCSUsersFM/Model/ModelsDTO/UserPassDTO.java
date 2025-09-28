package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO;

import java.util.UUID;

public class UserPassDTO {
    private UUID idUser;
    private String username;
    private String password;
    private String typeOfAccount;

    public UserPassDTO(UUID idUser, String username, String password, String typeOfAccount) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.typeOfAccount = typeOfAccount;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }
}
