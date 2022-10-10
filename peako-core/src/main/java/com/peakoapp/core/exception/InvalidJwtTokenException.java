package com.peakoapp.core.exception;

/**
 * The {@code InvalidJwtTokenException} class is thrown when an access token or refresh token is
 * unrecognized.
 *
 * @version 0.1.0
 */
public class InvalidJwtTokenException extends RuntimeException {
    private static final long serialVersionUID = -2995893997115766491L;

    public InvalidJwtTokenException(String msg) {
        super(msg);
    }
}
