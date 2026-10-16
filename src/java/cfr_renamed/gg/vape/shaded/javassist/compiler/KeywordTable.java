/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler;

import java.util.HashMap;

public final class KeywordTable
extends HashMap<String, Integer> {
    private final static long serialVersionUID = 1L;

    public int lookup(String name) {
        return this.containsKey(name) ? (Integer)this.get(name) : -1;
    }

    public void append(String name, int t) {
        this.put(name, t);
    }
}

