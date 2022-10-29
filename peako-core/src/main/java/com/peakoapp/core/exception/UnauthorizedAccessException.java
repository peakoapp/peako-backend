package com.peakoapp.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * The {@code UnauthorizedAccessException} class is thrown when authentication is required but not
 * provided.
 *
 * @version 0.1.0
 */
public class UnauthorizedAccessException extends AuthenticationException {
    private static final long serialVersionUID = 3081549938017462533L;

    public UnauthorizedAccessException(String msg) {
        super(msg);
    }
}
