package com.pipejfdv.MCSAuth.MCSAuth.Models.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* DTO representing the login credentials received from the client in the login request body.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentials {
    private String username;
    private String password;
}
