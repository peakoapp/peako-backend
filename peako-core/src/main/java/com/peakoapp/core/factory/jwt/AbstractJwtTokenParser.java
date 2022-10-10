package com.peakoapp.core.factory.jwt;

/**
 * The {@code AbstractJwtTokenParser} abstract class extends {@link JwtTokenParser} and extracts
 * the most essential data required to parse a valid JWT token.
 *
 * @version 0.1.0
 */
public abstract class AbstractJwtTokenParser implements JwtTokenParser {
    protected final String token;

    protected AbstractJwtTokenParser(String token) {
        this. token = token;
    }
}
