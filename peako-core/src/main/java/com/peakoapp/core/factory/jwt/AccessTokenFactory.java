package com.peakoapp.core.factory.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.util.Assert;

/**
 * The {@code AccessTokenFactory} class extends {@link AbstractJwtTokenFactory} to create a standard
 * access key for users.
 *
 * @version 0.1.0
 */
public class AccessTokenFactory extends AbstractJwtTokenFactory {
    protected static final String AUDIENCE = "ACCESS";

    protected final long expiration;
    protected final SignatureAlgorithm algorithm;
    protected Date issuedAt;
    protected Date expiredAt;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public AccessTokenFactory(long id, SignatureAlgorithm algorithm, long expiration) {
        super(id);
        Assert.notNull(algorithm, "The sign algorithm must not be null.");
        Assert.state(algorithm.equals(SignatureAlgorithm.HS256), "Now support only HS256.");
        Assert.state(expiration > 0, "The expiration must be greater than 0.");
        this.algorithm = algorithm;
        this.expiration = expiration;
    }

    public AccessTokenFactory(long id, SignatureAlgorithm algorithm) {
        this(id, algorithm, BASE_EXP);
    }

    public AccessTokenFactory(long id) {
        this(id, SignatureAlgorithm.HS256, BASE_EXP);
    }

    @Override
    public String getIssuer() {
        return ISSUER;
    }

    @Override
    public Date getIssuedAt() {
        return issuedAt;
    }

    @Override
    public Date getExpiredAt() {
        return expiredAt;
    }

    @Override
    public String create() {
        issuedAt = new Date();
        expiredAt = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(String.valueOf(id))
                .setAudience(AUDIENCE)
                .setExpiration(expiredAt)
                .setIssuedAt(issuedAt)
                .signWith(SECRET_KEY, algorithm)
                .compact();
    }
}
