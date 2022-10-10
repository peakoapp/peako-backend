package com.peakoapp.core.factory.jwt;

import com.peakoapp.core.exception.InvalidJwtTokenException;
import com.peakoapp.core.model.dto.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Collections;

/**
 * The {@code AccessTokenParser} class extends {@link AbstractJwtTokenParser} to parse a user
 * provided JWT token that was created by {@link AccessTokenFactory}.
 *
 * @version 0.1.0
 */
public class AccessTokenParser extends AbstractJwtTokenParser {
    public AccessTokenParser(String token) {
        super(token);
    }

    @Override
    public JwtToken parse() throws ExpiredJwtException, InvalidJwtTokenException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .requireIssuer(AccessTokenFactory.ISSUER)
                    .requireAudience(AccessTokenFactory.AUDIENCE)
                    .setSigningKey(AccessTokenFactory.SECRET_KEY)
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
