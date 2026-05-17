package com.pipejfdv.Juegos.MCSJuegos.Config;

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
* Configures Spring Security with JWT-based authentication and role-based access control
* Defines public, mixed, PremiumUser, Medic, and FMAdmin endpoints
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
	* @Return JwtDecoder configured with the secret key
	*/
    @Bean
    public JwtDecoder jwtDecoder(){
        byte[] KeyBytes = Decoders.BASE64.decode("dX3kRg9UznX3R8fCgekVYTNDpmk7w34tuepf5E/ZjT6Mtms+=8vSbwSX=e+/E1");
        SecretKey secretKey = new SecretKeySpec(KeyBytes,"HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    /*
	* Configures HTTP security: disables CSRF, sets stateless sessions, defines role-based URL rules
	* @Param http HttpSecurity object to configure
	* @Return SecurityFilterChain built security filter chain
	* @Throw Exception on configuration error
	*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // public paths
                        .requestMatchers("/games/listGames", "/games/listCategories").permitAll()
                        // mix paths
                        .requestMatchers("/games/read/**", "/games/read/category/**").hasAnyRole("DemoUser", "PremiumUser", "FMAdmin", "Medic")
                        // path PremiumUser
                        .requestMatchers("/games/progress", "/games/createGameStat").hasAnyRole("PremiumUser", "FMAdmin")
                        // path Medic
                        .requestMatchers("/games/progress/**", "/games/read/**").hasAnyRole("Medic")
                        // path Admin
                        .requestMatchers("/games/**").hasAnyRole("FMAdmin")
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
