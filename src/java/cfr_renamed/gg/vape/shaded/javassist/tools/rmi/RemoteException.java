/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.tools.rmi;

public class RemoteException
extends RuntimeException {
    private final static long serialVersionUID = 1L;

    public RemoteException(String msg) {
        super(msg);
    }

    public RemoteException(Exception e) {
        super("by " + e.toString());
    }
}

