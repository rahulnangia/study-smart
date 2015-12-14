package com.ss.database.core;

import org.hibernate.dialect.MySQL5Dialect;

import java.sql.Types;

/**
 * Created by rahul on 12/15/15.
 */
public class MySQL5BitBooleanDialect extends MySQL5Dialect {

    public MySQL5BitBooleanDialect() {
        super();
        registerColumnType(Types.BOOLEAN, "bit");
    }

}