package com.ss.common.exceptions;

/**
 * Created by rahul on 12/2/15.
 */
public class SSException extends Exception {

    public SSException(String message) {
        super(message);
    }

    public SSException(String message, Throwable cause) {
        super(message, cause);
    }
}
