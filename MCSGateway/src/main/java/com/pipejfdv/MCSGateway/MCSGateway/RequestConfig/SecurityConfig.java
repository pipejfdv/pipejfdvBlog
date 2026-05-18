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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
* Configures HTTP security for the API Gateway.
* Defines public and secured route chains with JWT authentication.
*/
@EnableWebFluxSecurity
@Component
public class SecurityConfig {
    private final ConverterRoleJWT converterRoleJWT;
    public SecurityConfig(ConverterRoleJWT converterRoleJWT) {
        this.converterRoleJWT = converterRoleJWT;
    }

    /*
    * Global CORS configuration for the Gateway.
    * Allows all origins, methods, and headers for cross-origin requests from the frontend.
    */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /*
    * Creates a JWT decoder that validates tokens using a base64-encoded HMAC secret key.
    * @Return ReactiveJwtDecoder decoder that verifies JWT signatures
    */
    @Bean
    public ReactiveJwtDecoder jwtDecoder(){
        byte[] KeyBytes = Decoders.BASE64.decode("dX3kRg9UznX3R8fCgekVYTNDpmk7w34tuepf5E/ZjT6Mtms+=8vSbwSX=e+/E1");
        SecretKey secretKey = new SecretKeySpec(KeyBytes,"HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }

    /*
    * Defines the public security chain for unauthenticated endpoints (login, register, refresh).
    * @Param http ServerHttpSecurity builder to configure security rules
    * @Return SecurityWebFilterChain the public route filter chain
    */
    @Bean
    @Order(1)
    public SecurityWebFilterChain publicChain(ServerHttpSecurity http){
        return http
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers(
                        "/pipejfdv/api/v1/funnyMind/User/create/**",
                        "/pipejfdv/api/v1/auth/login",
                        "/pipejfdv/api/v1/auth/logout",
                        "/pipejfdv/api/v1/auth/refresh",
                        "/pipejfdv/api/v1/funnyMind/User/exists/**",
                        "/pipejfdv/api/v1/funnyMind/DT/List",
                        "/pipejfdv/api/v1/funnyMind/DT/Document",
                        "/pipejfdv/api/v1/funnyMind/tceClassification/list",
                        "/pipejfdv/api/v1/funnyMind/relationship/Public/list",
                        "/pipejfdv/api/v1/funnyMind/AcTypes/List"
                ))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth ->
                        auth.anyExchange().permitAll()
                )
                .build();
    }

    /*
    * Defines the secured security chain. Requires valid JWT and checks role-based access.
    * @Param http ServerHttpSecurity builder to configure security rules
    * @Return SecurityWebFilterChain the secured route filter chain
    */
    @Bean
    @Order(2)
    public SecurityWebFilterChain securedChain(ServerHttpSecurity http){
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(a -> a
                        .pathMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/pipejfdv/api/v1/funnyMind/**").hasAnyRole("PremiumUser", "DemoUser", "FMAdmin", "Medic")
                        .pathMatchers("/pipejfdv/api/v1/auth/deleted/**").hasAnyRole("FMAdmin")
                        .pathMatchers("/pipejfdv/api/v1/games/**").hasAnyRole("PremiumUser", "DemoUser", "FMAdmin", "Medic")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(auth -> auth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(converterRoleJWT))
                )
                .build();
    }
}
