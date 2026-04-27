package com.pipejfdv.Juegos.MCSJuegos.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtConvertRol implements Converter<Jwt, AbstractAuthenticationToken> {

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
