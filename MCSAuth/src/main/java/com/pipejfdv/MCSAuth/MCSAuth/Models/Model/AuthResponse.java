package com.pipejfdv.MCSAuth.MCSAuth.Models.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
*   This is response from token to send
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String typeOfAccount;
    private LocalDateTime expirationToken;
}
