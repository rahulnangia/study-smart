package com.ss.common.exceptions.impl;

import com.ss.common.constants.ErrorMessages;
import com.ss.common.exceptions.SSRuntimeException;

/**
 * Created by rahul on 12/3/15.
 */
public class SSDatabaseException extends SSRuntimeException {
    public SSDatabaseException(Throwable cause) {
        super(ErrorMessages.DATABASE_OPERATION, cause);
    }
}
