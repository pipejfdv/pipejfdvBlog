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
    * Creation token with data user and secret pass is Algorithm HS256
    */
    public String buildToken(UserPassDTO user, long expiration) {
        return Jwts.builder()
                .id(user.getIdUser().toString())
                .subject(user.getUsername())
                .claim("accountType", user.getTypeOfAccount())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(jwt.getSecret().getBytes()), Jwts.SIG.HS256)
                .compact();
    }
    /*
    generate token for user
     */
    public String generateToken(UserPassDTO user) {
        return buildToken(user, jwt.getExpiration());
    }
    /*
    generate refresh toke for user
     */
    public String generateRefreshToken(UserPassDTO user) {
        return buildToken(user, jwt.getRefreshExpiration().getExpiration());
    }
}
