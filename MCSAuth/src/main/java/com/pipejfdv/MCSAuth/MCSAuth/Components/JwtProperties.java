package com.pipejfdv.MCSAuth.MCSAuth.Components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
* This component allows take values of MCS-AUTH.yml
*/
@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private long expiration;
    private RefreshToken refreshExpiration;

    // nested class, to take ownership of jwt
    @Getter
    @Setter
    public static class RefreshToken {
        private long expiration;
    }
}
