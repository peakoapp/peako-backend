package com.peakoapp.core.constant.enums;

/**
 * The RCode enumerates the custom status code used in the response body.
 *
 * @version 0.1.0
 */
public enum ReCode {
    SUCCESS(2000, "OK"),

    ALREADY_CONFIRMED(2100, "The email address has been verified."),

    NOT_FOUND(4000, "resource not found"),
    DUPLICATE(4001, "resource duplication"),
    BAD_PARAMS(4002, "bad parameters"),

    ACCOUNT_DELETED(4100, "The requested account has been deleted."),
    ACCOUNT_DISABLED(4101, "The requested account is not enabled."),
    ACCOUNT_LOCKED(4102, "The requested account is locked."),
    ACCOUNT_NOT_EXIST(4103, "The requested account does not exist."),
    ACCOUNT_EXISTED(4104, "The email address has been used."),

    TOKEN_EXPIRED(4200, "The provided token has expired."),
    TOKEN_INVALID(4201, "The provided token is not valid."),

    ERROR(5000, "The request encountered an unknown server error."),
    DB_OP_ERROR(5001, "database operation error")
    ;

    private final int code;
    private final String message;

    ReCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int value() {
        return code;
    }

    public String message() {
        return message;
    }
}
