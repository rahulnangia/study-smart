package com.ss.common.exceptions;

/**
 * Created by rahul on 12/2/15.
 */
public class SSRuntimeException extends RuntimeException {

    public SSRuntimeException(String message) {
        super(message);
    }

    public SSRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
