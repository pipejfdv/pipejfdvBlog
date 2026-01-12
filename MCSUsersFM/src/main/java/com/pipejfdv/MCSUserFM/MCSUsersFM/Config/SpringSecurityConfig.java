package com.pipejfdv.MCSUserFM.MCSUsersFM.Config;

import io.jsonwebtoken.io.Decoders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final JwtConvertRol jwtConvertRol;

    public SpringSecurityConfig(JwtConvertRol jwtConvertRol){
        this.jwtConvertRol = jwtConvertRol;
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        byte[] KeyBytes = Decoders.BASE64.decode("dX3kRg9UznX3R8fCgekVYTNDpmk7w34tuepf5E/ZjT6Mtms+=8vSbwSX=e+/E1");
        SecretKey secretKey = new SecretKeySpec(KeyBytes,"HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/funnyMind/**").permitAll()
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(auth -> auth
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtConvertRol)
                        )
                )
                .build();
    }
}
