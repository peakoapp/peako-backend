package com.peakoapp.core.exception;

/**
 * The {@code BadParameterException} class is thrown when invalid user-passed parameters are
 * detected.
 *
 * @version 0.1.0
 */
public class BadParameterException extends RuntimeException {
    private static final long serialVersionUID = -2985786577270977499L;

    public BadParameterException(String msg) {
        super(msg);
    }
}
