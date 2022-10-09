package com.peakoapp.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * The {@code AccountLockedException} class is thrown when a user attempts to access an account that
 * has been locked due to security reason(s).
 *
 * @version 0.1.0
 */
public class AccountLockedException extends AuthenticationException {
    private static final long serialVersionUID = 8850963795801568575L;

    public AccountLockedException(String msg) {
        super(msg);
    }
}
