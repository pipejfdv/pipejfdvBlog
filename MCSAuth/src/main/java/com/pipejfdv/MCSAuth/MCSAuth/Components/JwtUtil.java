package com.pipejfdv.MCSAuth.MCSAuth.Components;

import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;


/*
* This component is in charge to create token JWT.
*
* import configurations because the properties is on MCSConfig
*/
@Component
public class JwtUtil {
    private final JwtProperties jwt;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwt = jwtProperties;
    }
    /*
    * Creation token with data user and secret pass is Algorit HS256
    */
    public String generateToken(UserPassDTO user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("email", user.getEmail())
                .claim("accountType", user.getTypeOfAccount())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwt.getExpiration()))
                .signWith(Keys.hmacShaKeyFor(jwt.getSecret().getBytes()), Jwts.SIG.HS256)
                .compact();
    }
}
