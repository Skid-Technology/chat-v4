/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.value;

import gg.vape.value.Value;

public interface ValueChangeValidator<V extends Value<T, V>, T> {
    public boolean isValidChange(V var1, T var2, T var3);
}

