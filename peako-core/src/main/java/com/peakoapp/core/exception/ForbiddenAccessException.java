package com.peakoapp.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * The {@code ForbiddenAccessException} class is thrown when a request doesn't have the privilege to
 * perform the action.
 *
 * @version 0.1.0
 */
public class ForbiddenAccessException extends AuthenticationException {
    private static final long serialVersionUID = 2482766800901043097L;

    public ForbiddenAccessException(String msg) {
        super(msg);
    }
}
