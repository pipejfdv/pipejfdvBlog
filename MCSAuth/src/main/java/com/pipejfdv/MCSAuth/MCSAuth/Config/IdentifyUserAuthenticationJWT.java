package com.pipejfdv.MCSAuth.MCSAuth.Config;

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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/*
* Configures how Spring Security identifies and authenticates users for JWT-based auth.
*/
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class IdentifyUserAuthenticationJWT {
    private final MCSUsersFMServices mcsUsersFMServices;
    private final AuthTokenRepository  authTokenRepository;

    /*
    * Builds a UserDetailsService that loads user credentials from MCSUsersFM
    * @Return UserDetailsService the user details service
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
    * Creates an AuthenticationProvider that compares user and password using DAO
    * @Return AuthenticationProvider the authentication provider
    */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /*
    * Provides a BCrypt password encoder for encrypting and verifying passwords
    * @Return PasswordEncoder the BCrypt password encoder
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*
    * Creates the AuthenticationManager used to process authentication requests
    * @Param config AuthenticationConfiguration the Spring authentication config
    * @Return AuthenticationManager the authentication manager
    * @Throw Exception if configuration fails
    */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return config.getAuthenticationManager();
    }
}
