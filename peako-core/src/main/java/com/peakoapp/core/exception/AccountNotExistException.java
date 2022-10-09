package com.peakoapp.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * The {@code AccountNotExistException} class is thrown when a user attempts to sign in with an
 * email address that does not exist in the database.
 *
 * @version 0.1.0
 */
public class AccountNotExistException extends AuthenticationException {
    private static final long serialVersionUID = 243189778656627717L;

    public AccountNotExistException(String msg) {
        super(msg);
    }
}
