package com.peakoapp.core.exception;

/**
 * The {@code CoreException} class is thrown when an unknown exception is captured.
 *
 * @version 0.1.0
 */
public class CoreException extends RuntimeException {
    private static final long serialVersionUID = 4159488941474091909L;

    public CoreException(String msg) {
        super(msg);
    }
}
