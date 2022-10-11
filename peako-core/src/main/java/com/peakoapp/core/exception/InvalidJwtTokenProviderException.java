package com.peakoapp.core.exception;

/**
 * The {@code InvalidJwtTokenProviderException} class is thrown when an invalid token type is passed
 * to the {@link com.peakoapp.core.jwt.JwtTokenManager}.
 *
 * @version 0.1.0
 */
public class InvalidJwtTokenProviderException extends RuntimeException {
    private static final long serialVersionUID = 5679129783322953855L;

    public InvalidJwtTokenProviderException(String msg) {
        super(msg);
    }
}
