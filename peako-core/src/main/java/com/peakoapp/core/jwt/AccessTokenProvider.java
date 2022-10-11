package com.peakoapp.core.jwt;

import com.peakoapp.core.constant.constnt.PeakoServerMetadata;
import com.peakoapp.core.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;

/**
 * The {@code AccessTokenProvider} class implements {@link JwtTokenProvider} to provide JWT access
 * token creation and parsing operations.
 *
 * @version 0.1.0
 */
public class AccessTokenProvider implements JwtTokenProvider {
    protected final long expiration = 1800000L;  // 30 minutes
    protected final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private static final String SECRET = "4neeUxOeKIGWvGlPtGR2AUF4idgbfyEs3RDQEuMk9ElP8a5tsAskYpVxn"
            + "XqZ2UyJrjK0YgjvVdangkzygSeDEAZBJeZ8r5mN5JkrYHqaY3pwhWq8Jjb2ffKcKI2Q6hb58elO52aluIjf6"
            + "RZfdVTwiEiwJC3dJJS4";  // TODO: get from AWS or properties.
    protected static final SecretKey SECRET_KEY
            = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    @Override
    public String create(Long id) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setIssuer(PeakoServerMetadata.DOMAIN)
                .setSubject(String.valueOf(id))
                .setAudience("ACCESS")
                .setExpiration(expiredAt)
                .setIssuedAt(issuedAt)
                .signWith(SECRET_KEY, algorithm)
                .compact();
    }

    @Override
    public JwtToken parse(String token) throws ExpiredJwtException, InvalidJwtTokenException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .requireIssuer(PeakoServerMetadata.DOMAIN)
                    .requireAudience("ACCESS")
                    .setSigningKey(SECRET_KEY)
                    .build().parseClaimsJws(token).getBody();
            return new JwtToken(Long.parseLong(claims.getSubject()), claims.getIssuer(),
                    claims.getAudience(), claims.getExpiration(), Collections.emptyMap());
        } catch (MissingClaimException | IncorrectClaimException | UnsupportedJwtException
                | MalformedJwtException | SignatureException | NumberFormatException e
        ) {
            throw new InvalidJwtTokenException("The JWT token cannot be correctly parsed.");
        }
    }
}
