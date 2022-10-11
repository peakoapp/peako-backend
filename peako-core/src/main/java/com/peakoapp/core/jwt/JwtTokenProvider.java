package com.peakoapp.core.jwt;

import com.peakoapp.core.exception.InvalidJwtTokenException;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * The {@code JwtTokenProvider} interface defines the {@code create()} and {@code pares()} methods
 * to be implemented by any concrete token provider.
 *
 * @version 0.1.0
 */
public interface JwtTokenProvider {
    /**
     * Creates a JWT token with the given id as the subject.
     *
     * @param id The user id.
     * @return A valid JWT token.
     */
    String create(Long id);

    /**
     * Parses the given JWT token into parts.
     *
     * @param token The given JWT token, usually from the authorization header.
     * @return A {@link JwtToken} instance that contains all the parts of a valid JWT token.
     * @throws ExpiredJwtException if the given JWT token has expired.
     * @throws InvalidJwtTokenException if the given JWT token is not recognized.
     */
    JwtToken parse(String token) throws ExpiredJwtException, InvalidJwtTokenException;
}
