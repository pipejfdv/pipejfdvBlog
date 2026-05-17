package com.pipejfdv.MCSAuth.MCSAuth.Models.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
* DTO representing the authentication response sent to the client after successful login or token refresh.
* Contains the access token, refresh token, username, account type, and expiration timestamp.
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String refreshToken;
    private String username;
    private String typeOfAccount;
    private LocalDateTime expirationToken;
}
