/*
 * Decompiled with CFR 0.152.
 */
package com.google.gson.internal.sql;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.DefaultDateTypeAdapter;
import com.google.gson.internal.sql.SqlDateTypeAdapter;
import com.google.gson.internal.sql.SqlTimeTypeAdapter;
import com.google.gson.internal.sql.SqlTimestampTypeAdapter;
import java.sql.Timestamp;
import java.util.Date;

public final class SqlTypesSupport {
    final public static boolean SUPPORTS_SQL_TYPES;
    final public static DefaultDateTypeAdapter.DateType<? extends Date> DATE_DATE_TYPE;
    final public static DefaultDateTypeAdapter.DateType<? extends Date> TIMESTAMP_DATE_TYPE;
    final public static TypeAdapterFactory DATE_FACTORY;
    final public static TypeAdapterFactory TIME_FACTORY;
    final public static TypeAdapterFactory TIMESTAMP_FACTORY;

    private SqlTypesSupport() {
    }

    static {
        boolean sqlTypesSupport;
        try {
            Class.forName("java.sql.Date");
            sqlTypesSupport = true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            sqlTypesSupport = false;
        }
        SUPPORTS_SQL_TYPES = sqlTypesSupport;
        if (SUPPORTS_SQL_TYPES) {
            DATE_DATE_TYPE = new DefaultDateTypeAdapter.DateType<java.sql.Date>(java.sql.Date.class){

                @Override
                protected java.sql.Date deserialize(Date date) {
                    return new java.sql.Date(date.getTime());
                }

                @Override
                protected Date deserialize(Date date) {
                    return this.deserialize(date);
                }
            };
            TIMESTAMP_DATE_TYPE = new DefaultDateTypeAdapter.DateType<Timestamp>(Timestamp.class){

                @Override
                protected Timestamp deserialize(Date date) {
                    return new Timestamp(date.getTime());
                }

                @Override
                protected Date deserialize(Date date) {
                    return this.deserialize(date);
                }
            };
            DATE_FACTORY = SqlDateTypeAdapter.FACTORY;
            TIME_FACTORY = SqlTimeTypeAdapter.FACTORY;
            TIMESTAMP_FACTORY = SqlTimestampTypeAdapter.FACTORY;
        } else {
            DATE_DATE_TYPE = null;
            TIMESTAMP_DATE_TYPE = null;
            DATE_FACTORY = null;
            TIME_FACTORY = null;
            TIMESTAMP_FACTORY = null;
        }
    }
}

