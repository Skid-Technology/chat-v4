/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.timeout;

import io.netty.handler.timeout.TimeoutException;

public final class WriteTimeoutException
extends TimeoutException {
    private final static long serialVersionUID = -144786655770296065L;
    final public static WriteTimeoutException INSTANCE = new WriteTimeoutException();

    private WriteTimeoutException() {
    }
}

