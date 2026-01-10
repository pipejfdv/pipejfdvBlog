package com.pipejfdv.MCSGateway.MCSGateway.RequestConfig;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ConverterRoleJWT implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    /*
    * This method is used for change the account type so that they can be interpreted for spring security
    */
    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        String accountType = source.getClaimAsString("accountType");
        if (accountType == null || accountType.isBlank())
        {
            return Mono.error(new JwtException("Missing 'accountType' claim"));
        }
        String role = "ROLE_" + accountType.replace(" ", "_");
        var authorities = List.of(new SimpleGrantedAuthority(role));
        var authToken = new JwtAuthenticationToken(source, authorities);
        return Mono.just(authToken);
    }
}
