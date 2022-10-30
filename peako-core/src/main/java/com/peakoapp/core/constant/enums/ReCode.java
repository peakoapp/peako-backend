package com.peakoapp.core.constant.enums;

/**
 * The {@code RCode} enumerates the custom status code used in the response body used by
 * {@link com.peakoapp.core.utils.R}.
 *
 * @version 0.1.0
 */
public enum ReCode {
    SUCCESS(2000, "OK"),

    NOT_FOUND(4000, "resource not found"),
    DUPLICATE(4001, "resource duplication"),
    BAD_PARAMS(4002, "bad parameters"),
    UNAUTHORIZED(4003, "no credentials provided"),
    FORBIDDEN(4004, "not enough privileges owned"),
    METHOD_NOT_ALLOWED(4005, "no such http method"),

    ACCOUNT_DELETED(4100, "account deleted"),
    ACCOUNT_DISABLED(4101, "account disabled"),
    ACCOUNT_LOCKED(4102, "account locked"),
    ACCOUNT_NOT_EXIST(4103, "account not found"),
    ACCOUNT_EXISTED(4104, "email address in use"),

    ALREADY_FRIENDS(4120, "already friends"),

    TOKEN_EXPIRED(4200, "expired token"),
    TOKEN_INVALID(4201, "invalid token"),

    ERROR(5000, "unknown server error"),
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
