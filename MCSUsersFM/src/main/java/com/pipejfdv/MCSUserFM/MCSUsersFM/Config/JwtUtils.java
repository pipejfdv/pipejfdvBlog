package com.pipejfdv.MCSUserFM.MCSUsersFM.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*
* Utility class for extracting information from JWT tokens
* Provides methods to get user ID and account type from authentication
*/
@Component
public class JwtUtils {
    /*
    * Extracts the user ID from the JWT token in the authentication object
    * @Params authentication Spring Security authentication object
    * @Return UUID of the authenticated user
    */
    public UUID extractUserId(Authentication authentication){
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return UUID.fromString(jwtAuth.getToken().getId());
    }
    /*
    * Extracts the account type from the JWT token
    * @Params authentication Spring Security authentication object
    * @Return String with the account type name
    */
    public String extractAccountType(Authentication authentication){
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return jwtAuth.getToken().getClaimAsString("accountType");
    }
}
