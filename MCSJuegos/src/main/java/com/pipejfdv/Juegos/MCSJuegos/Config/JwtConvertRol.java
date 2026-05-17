package com.pipejfdv.Juegos.MCSJuegos.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* Converts a JWT token into an authentication token with role information
* Extracts the accountType claim and maps it to a Spring Security role
*/
@Component
public class JwtConvertRol implements Converter<Jwt, AbstractAuthenticationToken> {

    /*
	* Extracts accountType from JWT claims and creates a JwtAuthenticationToken with the role
	* @Param source the JWT token to convert
	* @Return AbstractAuthenticationToken containing the role-based authorities
	* @Throw JwtException if accountType claim is missing or blank
	*/
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        String accountType = source.getClaimAsString("accountType");
        if(accountType == null || accountType.isBlank()){
            throw new JwtException("Missing 'accountType' claim");
        }
        String role = "ROLE_" + accountType.replace(" ", "_");
        var authorities = List.of(new SimpleGrantedAuthority(role));
        return new JwtAuthenticationToken(source, authorities);
    }
}
