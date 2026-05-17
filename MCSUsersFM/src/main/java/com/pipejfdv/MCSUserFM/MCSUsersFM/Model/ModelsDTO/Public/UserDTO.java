package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;

import java.util.UUID;

/*
* Public DTO for transferring user data without sensitive fields
*/
public class UserDTO {
    private UUID idUser;
    private String email;
    private String username;
    private AccountType accountType;

    /*
    * Creates a UserDTO with all fields including ID
    * @Param idUser the user UUID
    * @Param username the username
    * @Param email the user email
    */
    public UserDTO(UUID idUser, String username, String email) {
        this.idUser = idUser;
        this.username = username;
        this.email = email;
    }

    /*
    * Creates a UserDTO for new users without an ID yet
    * @Param username the username
    * @Param email the user email
    * @Param accountType the account type
    */
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
