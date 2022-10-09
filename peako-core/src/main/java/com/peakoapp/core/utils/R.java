package com.peakoapp.core.utils;

import com.peakoapp.core.constant.enums.ReCode;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * The {@code R} class is a wrapper class that unifies the response body data structure.
 *
 * @version 0.1.0
 */
public class R<T> implements Serializable {
    private int code;
    private String message;
    private T body;
    private Date timestamp;

    private R(int code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
        this.timestamp = Date.from(Instant.now());
    }

    public R(ReCode code, T body) {
        this(code.value(), code.message(), body);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public static <V> R<V> ok() {
        return new R<>(ReCode.SUCCESS, null);
    }

    public static <V> R<V> ok(V body) {
        return new R<>(ReCode.SUCCESS, body);
    }

    public static <V> R<V> bad() {
        return new R<>(ReCode.BAD_PARAMS, null);
    }

    public static <V> R<V> notFound() {
        return new R<>(ReCode.NOT_FOUND, null);
    }

    public static <V> R<V> error() {
        return new R<>(ReCode.ERROR, null);
    }
}
