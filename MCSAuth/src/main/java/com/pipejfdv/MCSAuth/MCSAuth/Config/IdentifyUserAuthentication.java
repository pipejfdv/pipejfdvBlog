package com.pipejfdv.MCSAuth.MCSAuth.Config;

import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.InvalidBearerTokenException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories.AuthTokenRepository;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Services.MCSUsersFMServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class IdentifyUserAuthentication {
    /*
    This document is in charge, how Spring security identify user
     */
    private final MCSUsersFMServices mcsUsersFMServices;
    private final AuthTokenRepository  authTokenRepository;

    /*
    config route of different paths
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuth jwtAuth) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login/**", "/auth/refresh/**", "/auth/prueba/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //without HTTP session
                .authenticationProvider(authenticationProvider())//How I identify user with this method authenticationProvider()
                .addFilterBefore(jwtAuth, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler((request, response, authentication) -> {
                                    final var authHeader = request.getHeader("Authorization");
                                    logout(authHeader);
                                })
                                .logoutSuccessHandler(((request, response, authentication) ->
                                        SecurityContextHolder.clearContext()))
                )
                .build();

    }
    /*
    * expired token after to close session
    */
    private void logout(String authHeader) {
        if (authHeader == null && authHeader.startsWith("Bearer ")) {
            throw new InvalidBearerTokenException("Invalid Bearer Token: " + authHeader);
        }
        final String jwtToken = authHeader.substring(7);
        final AuthToken foundToken = authTokenRepository.findByToken(jwtToken);
        foundToken.setExpired(true);
        foundToken.setRevoked(true);
        authTokenRepository.save(foundToken);
    }
    /*
    this is the way in which spring security validates if the user exists or how it checks it
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            final UserPassDTO user = mcsUsersFMServices.getCredentialsUser(username);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        };
    }
    /*
    this is the way to spring security compare the password and user
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /*
    this is the method it can encrypt the password and decrypt the same time
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*
    Configuration of AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return config.getAuthenticationManager();
    }
}
