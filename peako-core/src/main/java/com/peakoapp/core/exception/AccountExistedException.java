package com.peakoapp.core.exception;

/**
 * The {@code AccountExistedException} class is thrown when a user attempts to sign up with an
 * email address that has already existed.
 *
 * @version 0.1.0
 */
public class AccountExistedException extends RuntimeException {
    private static final long serialVersionUID = 8440468979746274844L;

    public AccountExistedException(String msg) {
        super(msg);
    }
}
