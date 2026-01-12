package com.pipejfdv.MCSUserFM.MCSUsersFM.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtUtils {
    /*
    * Is responsible for extracting the token ID when it is not sent as a parameter
    */
    public UUID extractUserId(Authentication authentication){
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return UUID.fromString(jwtAuth.getToken().getId());
    }
    /*
    *  Is responsible for extracting the token Rol
    */
    public String extractAccountType(Authentication authentication){
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return jwtAuth.getToken().getClaimAsString("accountType");
    }
}
