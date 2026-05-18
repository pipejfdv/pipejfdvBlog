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

/*
* Configures Spring Security for the microservice
* Sets up JWT decoding, role-based authorization and stateless session management
*/
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    private final JwtConvertRol jwtConvertRol;

    public SpringSecurityConfig(JwtConvertRol jwtConvertRol){
        this.jwtConvertRol = jwtConvertRol;
    }

    /*
    * Creates a JWT decoder using HMAC-SHA256 secret key
    * @Return JwtDecoder instance for validating JWT tokens
    */
    @Bean
    public JwtDecoder jwtDecoder(){
        byte[] KeyBytes = Decoders.BASE64.decode("dX3kRg9UznX3R8fCgekVYTNDpmk7w34tuepf5E/ZjT6Mtms+=8vSbwSX=e+/E1");
        SecretKey secretKey = new SecretKeySpec(KeyBytes,"HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    /*
    * Configures HTTP security with role-based access for each endpoint
    * @Params http HttpSecurity object to configure
    * @Return SecurityFilterChain with all security rules applied
    * @Throw Exception if configuration fails
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // public paths
                        .requestMatchers("/funnyMind/User/create/**",
                                "/funnyMind/DT/List", "/funnyMind/DT/Document",
                                "/funnyMind/tceClassification/list",
                                "/funnyMind/relationship/Public/list",
                                "/funnyMind/AcTypes/List", "/funnyMind/User/exists/**",
                                //paths of MCS
                                "/funnyMind/User/Auth/**",
                                "/funnyMind/children/getPublic/games/**").permitAll()

                        // mix paths (DemoUser, PremiumUser, FMAdmin, Medic)
                        .requestMatchers(
                                "/funnyMind/User/userData/**",
                                "/funnyMind/User/delete/**",
                                "/funnyMind/User/update/**",
                                "/funnyMind/Guardian/public",
                                "/funnyMind/Guardian/delete",
                                "/funnyMind/Guardian/create/**",
                                "/funnyMind/Guardian/edit",
                                "/funnyMind/tceClassification/type/**",
                                "/funnyMind/children/getPublic/**",
                                "/funnyMind/children/public/list"
                        ).hasAnyRole("DemoUser", "PremiumUser", "FMAdmin", "Medic")
                        // protected paths DemoUser & PremiumUser
                        .requestMatchers(
                                "/funnyMind/guardianChildren/**",
                                "/funnyMind/children/create/**",
                                "/funnyMind/children/update",
                                "/funnyMind/children/deleted",
                                "/funnyMind/children/create/**",
                                "/funnyMind/guardianChildren/**",
                                "/funnyMind/relationship/create/**",
                                "/funnyMind/relationship/get",
                                "/funnyMind/AcTypes/account",
                                "/funnyMind/profiles/create/**",
                                "/funnyMind/profiles/get/**",
                                "/funnyMind/profiles/public/list/**",
                                "/funnyMind/profiles/update/**"
                        ).hasAnyRole("DemoUser", "PremiumUser")
                        // protected paths Medic
                        .requestMatchers("/funnyMind/tceClassification/updateByChildren/**").hasAnyRole("Medic")
                        // protected paths Medic & Admin (shared)
                        .requestMatchers("/funnyMind/children/Ad_Me/list").hasAnyRole("Medic", "FMAdmin")
                        // protected paths Admin
                        .requestMatchers(
                                "/funnyMind/User/list",
                                "/funnyMind/Guardian/admin",
                                "/funnyMind/Guardian/list",
                                "/funnyMind/DT/**",
                                "/funnyMind/guardianChildren/**",
                                "/funnyMind/tceClassification/**",
                                "/funnyMind/children/create/**",
                                "/funnyMind/children/getAdmin/**",
                                "/funnyMind/children/Ad_Me/list",
                                "/funnyMind/children/deleted",
                                "/funnyMind/children/create/**",
                                "/funnyMind/guardianChildren/**",
                                "/funnyMind/relationship/**",
                                "/funnyMind/AcTypes/**",
                                "/funnyMind/profiles/**"
                        ).hasAnyRole("FMAdmin")

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
