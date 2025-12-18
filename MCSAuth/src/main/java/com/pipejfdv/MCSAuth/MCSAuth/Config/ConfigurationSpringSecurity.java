package com.pipejfdv.MCSAuth.MCSAuth.Config;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class ConfigurationSpringSecurity {
    private final AuthTokenRepository authTokenRepository;
    private final IdentifyUserAuthenticationJWT identifyUserAuthentication;

    /*
    config route of different paths
    */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        //.requestMatchers("/auth/login/**", "/auth/refresh/**", "/auth/logout/**").permitAll()
                        .anyRequest().denyAll()//deny other type of request
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //without HTTP session
                .build();
    }

}
