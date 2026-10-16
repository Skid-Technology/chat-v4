/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

import java.io.IOException;

abstract class LinuxDeviceTask {
    final public static int INPROGRESS = 1;
    final public static int COMPLETED = 2;
    final public static int FAILED = 3;
    private Object result;
    private IOException exception;
    private int state = 1;

    LinuxDeviceTask() {
    }

    public final void doExecute() {
        try {
            this.result = this.execute();
            this.state = 2;
        }
        catch (IOException e) {
            this.exception = e;
            this.state = 3;
        }
    }

    public final IOException getException() {
        return this.exception;
    }

    public final Object getResult() {
        return this.result;
    }

    public final int getState() {
        return this.state;
    }

    protected abstract Object execute() throws IOException;
}

