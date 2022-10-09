package com.peakoapp.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * The {@code AccountDisabledException} class is thrown when a user attempts to access an account
 * that has been disabled for some reasons.
 *
 * @version 0.1.0
 */
public class AccountDisabledException extends AuthenticationException {
    private static final long serialVersionUID = -8156789017239293255L;

    public AccountDisabledException(String msg) {
        super(msg);
    }
}
