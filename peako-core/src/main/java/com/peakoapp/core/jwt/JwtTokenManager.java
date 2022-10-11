package com.peakoapp.core.jwt;

import com.peakoapp.core.exception.InvalidJwtTokenProviderException;

/**
 * The {@code JwtTokenManager} class is a factory class that produces the corresponding token
 * provider.
 *
 * @version 0.1.0
 */
public class JwtTokenManager {
    /**
     * Gets the token provider that handles the given token type.
     *
     * @param type The type of token to be operated on.
     * @return A {@link JwtTokenProvider} instance.
     */
    public static JwtTokenProvider getProvider(TokenType type) {
        if (type == TokenType.ACCESS) {
            return new AccessTokenProvider();
        }
        throw new InvalidJwtTokenProviderException("Attempted to get invalid jwt token provider.");
    }
}
