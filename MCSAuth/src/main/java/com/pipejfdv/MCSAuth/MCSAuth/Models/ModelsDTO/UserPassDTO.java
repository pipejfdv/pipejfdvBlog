package com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/*
* DTO representing user credentials transferred from the MCSUsersFM microservice.
* Contains user ID, username, encrypted password, and account type.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPassDTO {
    private UUID idUser;
    private String username;
    private String password;
    private String typeOfAccount;
}