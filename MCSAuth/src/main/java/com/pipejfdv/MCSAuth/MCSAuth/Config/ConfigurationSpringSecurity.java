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

/*
* Configures Spring Security filters and endpoint access rules for the auth microservice.
*/
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class ConfigurationSpringSecurity {
    private final AuthTokenRepository authTokenRepository;
    private final IdentifyUserAuthenticationJWT identifyUserAuthentication;

    /*
    * Configures HTTP security rules and session management for all routes
    * @Param http HttpSecurity the security builder
    * @Return SecurityFilterChain the configured filter chain
    * @Throw Exception if configuration fails
    */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/auth/deleted/**").hasAnyRole("FMAdmin")
                        .anyRequest().denyAll()//deny other type of request
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //without HTTP session
                .build();
    }

}
