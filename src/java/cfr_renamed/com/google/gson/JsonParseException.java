/*
 * Decompiled with CFR 0.152.
 */
package com.google.gson;

public class JsonParseException
extends RuntimeException {
    final static long serialVersionUID = -4086729973971783390L;

    public JsonParseException(String msg) {
        super(msg);
    }

    public JsonParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }
}

