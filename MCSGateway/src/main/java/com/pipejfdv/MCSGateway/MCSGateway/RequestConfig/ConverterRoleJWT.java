package com.pipejfdv.MCSGateway.MCSGateway.RequestConfig;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/*
* Converts a JWT token into a Spring Security authentication token.
* Extracts the role from the "accountType" claim and maps it to granted authorities.
*/
@Component
public class ConverterRoleJWT implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    /*
    * Reads the "accountType" claim from the JWT and builds an authentication token with the proper role.
    * @Param source the decoded JWT token
    * @Return Mono<AbstractAuthenticationToken> authentication token with granted authorities
    * @Throw JwtException if the "accountType" claim is missing or empty
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
