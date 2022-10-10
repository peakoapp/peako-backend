package com.peakoapp.core.model.dto;

import java.util.Date;
import java.util.Map;

/**
 * The {@code JwtToken} class is a wrapper class that contains the essential information of a valid
 * JWT token.
 *
 * @version 0.1.0
 */
public class JwtToken {
    private final long subject;
    private final String issuer;
    private final String audience;
    private final Date expiration;
    private final Map<String, Object> claims;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public JwtToken(long subject, String issuer, String audience, Date expiration,
                    Map<String, Object> claims) {
        this.subject = subject;
        this.issuer = issuer;
        this.audience = audience;
        this.expiration = expiration;
        this.claims = claims;
    }

    public long getSubject() {
        return subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAudience() {
        return audience;
    }

    public Date getExpiration() {
        return expiration;
    }

    public Object getClaim(String key) {
        return claims.get(key);
    }

    public Map<String, Object> getClaims() {
        return claims;
    }
}
