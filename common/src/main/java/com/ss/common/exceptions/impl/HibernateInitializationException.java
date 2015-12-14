package com.ss.common.exceptions.impl;

import com.ss.common.exceptions.SSRuntimeException;

/**
 * Created by rahul on 12/2/15.
 */
public class HibernateInitializationException extends SSRuntimeException {

    public HibernateInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
