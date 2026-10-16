/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.tools.rmi;

public class ObjectNotFoundException
extends Exception {
    private final static long serialVersionUID = 1L;

    public ObjectNotFoundException(String name) {
        super(name + " is not exported");
    }

    public ObjectNotFoundException(String name, Exception e) {
        super(name + " because of " + e.toString());
    }
}

