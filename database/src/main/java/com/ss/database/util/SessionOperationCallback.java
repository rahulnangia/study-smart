package com.ss.database.util;

import org.hibernate.Session;

/**
 * Created by rahul on 12/2/15.
 */
public interface SessionOperationCallback<T> {

    /**
     * this method executes any operation in the session
     *
     * @param session
     * @param <T>
     */
    public <T> void execute(Session session);

    /**
     * fetching the result of the session operation
     *
     * @return
     */
    public T get();
}
