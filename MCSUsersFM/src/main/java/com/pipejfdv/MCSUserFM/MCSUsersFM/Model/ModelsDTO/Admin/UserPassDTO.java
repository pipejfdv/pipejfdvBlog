package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin;

import java.util.UUID;

/*
* Admin DTO for transferring user data including password
*/
public class UserPassDTO {
    private UUID idUser;
    private String username;
    private String password;
    private String typeOfAccount;

    /*
    * Default constructor
    */
    public UserPassDTO() {
    }

    /*
    * Creates a UserPassDTO with all fields
    * @Param idUser the user UUID
    * @Param username the username
    * @Param password the user password
    * @Param typeOfAccount the account type name
    */
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
