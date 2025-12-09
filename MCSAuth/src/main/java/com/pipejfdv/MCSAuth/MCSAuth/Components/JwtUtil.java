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
    * Creation token with data user and secret pass is Algorithm HS256
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
    /*
    * Create secret key to token is same to hashing
    */
    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwt.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
    * Username extraction of token
    * @Param final String token
    * @Return String username
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
     *  Validity token
     * @Param final String token
     * @Return Date
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
    *  Token is valid to continue to credentials
    *  @Param final String token
    *  @Param final UserPassDTO
    *  @Return boolean True is correct / False is incorrect
    */
    public boolean isTokenValid(final String token, final UserPassDTO user) {
        final String username = extractUsernameForToken(token);
        return  (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    /*
    * Is token valid or Expired
    * @Param final String token
    * @Return boolean True is correct / False is incorrect
    */
    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }
}
