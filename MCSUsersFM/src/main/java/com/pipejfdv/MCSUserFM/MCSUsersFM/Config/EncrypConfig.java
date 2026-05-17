package com.pipejfdv.MCSUserFM.MCSUsersFM.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
* Configuration class for password encryption using BCrypt
*/
@Configuration
@RequiredArgsConstructor
public class EncrypConfig {
    /*
    * Creates a BCrypt password encoder bean
    * @Return PasswordEncoder instance for hashing passwords
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
