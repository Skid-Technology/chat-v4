/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.hash;

import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import java.nio.charset.Charset;

abstract class AbstractHasher
implements Hasher {
    AbstractHasher() {
    }

    @Override
    public final Hasher putBoolean(boolean b) {
        return this.putByte(b ? (byte)1 : 0);
    }

    @Override
    public final Hasher putDouble(double d) {
        return this.putLong(Double.doubleToRawLongBits(d));
    }

    @Override
    public final Hasher putFloat(float f) {
        return this.putInt(Float.floatToRawIntBits(f));
    }

    @Override
    public Hasher putUnencodedChars(CharSequence charSequence) {
        int len = charSequence.length();
        for (int i = 0; i < len; ++i) {
            this.putChar(charSequence.charAt(i));
        }
        return this;
    }

    @Override
    public Hasher putString(CharSequence charSequence, Charset charset) {
        return this.putBytes(charSequence.toString().getBytes(charset));
    }

    @Override
    public PrimitiveSink putString(CharSequence x0, Charset x1) {
        return this.putString(x0, x1);
    }

    @Override
    public PrimitiveSink putUnencodedChars(CharSequence x0) {
        return this.putUnencodedChars(x0);
    }

    @Override
    public PrimitiveSink putBoolean(boolean x0) {
        return this.putBoolean(x0);
    }

    @Override
    public PrimitiveSink putDouble(double x0) {
        return this.putDouble(x0);
    }

    @Override
    public PrimitiveSink putFloat(float x0) {
        return this.putFloat(x0);
    }
}

