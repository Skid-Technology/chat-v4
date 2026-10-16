/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.compression;

import io.netty.handler.codec.EncoderException;

public class CompressionException
extends EncoderException {
    private final static long serialVersionUID = 5603413481274811897L;

    public CompressionException() {
    }

    public CompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressionException(String message) {
        super(message);
    }

    public CompressionException(Throwable cause) {
        super(cause);
    }
}

