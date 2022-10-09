package com.peakoapp.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * The {@code AccountDeletedException} class is thrown when a user attempts to access an account
 * that has previously been logically deleted by the user.
 *
 * @version 0.1.0
 */
public class AccountDeletedException extends AuthenticationException {
    private static final long serialVersionUID = 6299084805945342317L;

    public AccountDeletedException(String msg) {
        super(msg);
    }
}
