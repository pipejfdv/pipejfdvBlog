package com.pipejfdv.MCSAuth.MCSAuth.Components;

import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
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
    * Builds a JWT token with user data and signs it using HS256 algorithm
    * @Param user UserPassDTO the user data for token claims
    * @Param expiration long the token expiration time in milliseconds
    * @Return String the signed JWT token
    */
    public String buildToken(UserPassDTO user, long expiration) {
        return Jwts.builder()
                .id(user.getIdUser().toString())
                .subject(user.getUsername())
                .claim("accountType", user.getTypeOfAccount())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }
    /*
    * Generates an access token for the given user
    * @Param user UserPassDTO the user data
    * @Return String the generated JWT access token
    */
    public String generateToken(UserPassDTO user) {
        return buildToken(user, jwt.getExpiration());
    }
    /*
    * Generates a refresh token for the given user with longer expiration
    * @Param user UserPassDTO the user data
    * @Return String the generated JWT refresh token
    */
    public String generateRefreshToken(UserPassDTO user) {
        return buildToken(user, jwt.getRefreshExpiration().getExpiration());
    }
    /*
    * Creates the HMAC-SHA secret key from the base64-encoded secret
    * @Return SecretKey the key used for signing and verifying tokens
    */
    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwt.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
    * Extracts the username from the JWT token
    * @Param token String the JWT token
    * @Return String the username stored in the token subject
    */
    public String extractUsernameForToken(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    /*
    * Extracts the expiration date from the JWT token
    * @Param token String the JWT token
    * @Return Date the expiration date of the token
    */
    public Date extractExpiration(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    /*
    * Checks if the token is valid for the given user and not expired
    * @Param token String the JWT token
    * @Param user UserPassDTO the user to validate against
    * @Return boolean true if valid, false if invalid or expired
    */
    public boolean isTokenValid(final String token, final UserPassDTO user) {
        final String username = extractUsernameForToken(token);
        return  (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    /*
    * Checks if the token has expired
    * @Param token String the JWT token
    * @Return boolean true if expired, false if still valid
    */
    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }
}
