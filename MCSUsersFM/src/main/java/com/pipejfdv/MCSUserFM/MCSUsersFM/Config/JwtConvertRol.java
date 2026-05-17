package com.pipejfdv.MCSUserFM.MCSUsersFM.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import java.util.List;

/*
* Converts JWT tokens into Spring Security authentication tokens
* Extracts the accountType claim and maps it to a role authority
*/
@Component
public class JwtConvertRol implements Converter<Jwt, AbstractAuthenticationToken> {
    /*
    * Converts a JWT into an authentication token with role authority
    * @Params source The JWT token to convert
    * @Return JwtAuthenticationToken with granted authorities
    * @Throw JwtException if accountType claim is missing
    */
    @Override public AbstractAuthenticationToken convert(Jwt source) {
        String accountType = source.getClaimAsString("accountType");
        if (accountType == null || accountType.isBlank()) {
            throw new JwtException("Missing 'accountType' claim");
        }
        String role = "ROLE_" + accountType.replace(" ", "_");
        var authorities = List.of(new SimpleGrantedAuthority(role));
        return new JwtAuthenticationToken(source, authorities);
    }
}
