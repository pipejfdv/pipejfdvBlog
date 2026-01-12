package com.pipejfdv.MCSGateway.MCSGateway.RequestConfig;

import io.jsonwebtoken.io.Decoders;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@EnableWebFluxSecurity
@Component
public class SecurityConfig {
    private final ConverterRoleJWT converterRoleJWT;
    public SecurityConfig(ConverterRoleJWT converterRoleJWT) {
        this.converterRoleJWT = converterRoleJWT;
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder(){
        byte[] KeyBytes = Decoders.BASE64.decode("dX3kRg9UznX3R8fCgekVYTNDpmk7w34tuepf5E/ZjT6Mtms+=8vSbwSX=e+/E1");
        SecretKey secretKey = new SecretKeySpec(KeyBytes,"HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    @Order(1)
    public SecurityWebFilterChain publicChain(ServerHttpSecurity http){
        return http
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers(
                        "/pipejfdv/api/v1/funnyMind/User/create/**",
                        "/pipejfdv/api/v1/auth/login",
                        "/pipejfdv/api/v1/auth/logout",
                        "/pipejfdv/api/v1/auth/refresh"
                ))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth ->
                        auth.anyExchange().permitAll()
                )
                .build();
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain securedChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(a -> a
                        .pathMatchers("/pipejfdv/api/v1/funnyMind/**").hasAnyRole("PremiumUser", "DemoUser", "Admin")
                        .pathMatchers("/pipejfdv/api/v1/auth/deleted/**").hasAnyRole("Admin")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(auth -> auth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(converterRoleJWT))
                )
                .build();
    }
}
