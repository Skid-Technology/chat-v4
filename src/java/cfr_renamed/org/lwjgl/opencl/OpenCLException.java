/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opencl;

public class OpenCLException
extends RuntimeException {
    private final static long serialVersionUID = 1L;

    public OpenCLException() {
    }

    public OpenCLException(String message) {
        super(message);
    }

    public OpenCLException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenCLException(Throwable cause) {
        super(cause);
    }
}

