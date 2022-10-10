package com.peakoapp.core.factory.jwt;

import com.peakoapp.core.constant.constnt.PeakoServerMetadata;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

/**
 * The {@code AbstractJwtTokenFactory} abstract class extends {@link JwtTokenFactory} and extracts
 * the most essential data required to create a valid JWT token.
 *
 * @version 0.1.0
 */
public abstract class AbstractJwtTokenFactory implements JwtTokenFactory {
    private static final String SECRET = "4neeUxOeKIGWvGlPtGR2AUF4idgbfyEs3RDQEuMk9ElP8a5tsAskYpVxn"
            + "XqZ2UyJrjK0YgjvVdangkzygSeDEAZBJeZ8r5mN5JkrYHqaY3pwhWq8Jjb2ffKcKI2Q6hb58elO52aluIjf6"
            + "RZfdVTwiEiwJC3dJJS4";  // TODO: get from AWS or properties.
    protected static final SecretKey SECRET_KEY
            = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    protected static final long BASE_EXP = 1800000;  // 30 minutes in millisecond
    protected static final String ISSUER = PeakoServerMetadata.DOMAIN;
    protected final long id;

    protected AbstractJwtTokenFactory(long id) {
        this.id = id;
    }

    public abstract String getIssuer();

    public abstract Date getIssuedAt();

    public abstract Date getExpiredAt();
}
