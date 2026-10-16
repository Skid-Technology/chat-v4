/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import java.nio.charset.Charset;

abstract class AbstractCompositeHashFunction
extends AbstractStreamingHashFunction {
    final HashFunction[] functions;
    private final static long serialVersionUID = 0L;

    AbstractCompositeHashFunction(HashFunction ... functions) {
        for (HashFunction function : functions) {
            Preconditions.checkNotNull(function);
        }
        this.functions = functions;
    }

    abstract HashCode makeHash(Hasher[] var1);

    @Override
    public Hasher newHasher() {
        final Hasher[] hashers = new Hasher[this.functions.length];
        for (int i = 0; i < hashers.length; ++i) {
            hashers[i] = this.functions[i].newHasher();
        }
        return new Hasher(){

            @Override
            public Hasher putByte(byte b) {
                for (Hasher hasher : hashers) {
                    hasher.putByte(b);
                }
                return this;
            }

            @Override
            public Hasher putBytes(byte[] bytes) {
                for (Hasher hasher : hashers) {
                    hasher.putBytes(bytes);
                }
                return this;
            }

            @Override
            public Hasher putBytes(byte[] bytes, int off, int len) {
                for (Hasher hasher : hashers) {
                    hasher.putBytes(bytes, off, len);
                }
                return this;
            }

            @Override
            public Hasher putShort(short s) {
                for (Hasher hasher : hashers) {
                    hasher.putShort(s);
                }
                return this;
            }

            @Override
            public Hasher putInt(int i) {
                for (Hasher hasher : hashers) {
                    hasher.putInt(i);
                }
                return this;
            }

            @Override
            public Hasher putLong(long l) {
                for (Hasher hasher : hashers) {
                    hasher.putLong(l);
                }
                return this;
            }

            @Override
            public Hasher putFloat(float f) {
                for (Hasher hasher : hashers) {
                    hasher.putFloat(f);
                }
                return this;
            }

            @Override
            public Hasher putDouble(double d) {
                for (Hasher hasher : hashers) {
                    hasher.putDouble(d);
                }
                return this;
            }

            @Override
            public Hasher putBoolean(boolean b) {
                for (Hasher hasher : hashers) {
                    hasher.putBoolean(b);
                }
                return this;
            }

            @Override
            public Hasher putChar(char c) {
                for (Hasher hasher : hashers) {
                    hasher.putChar(c);
                }
                return this;
            }

            @Override
            public Hasher putUnencodedChars(CharSequence chars) {
                for (Hasher hasher : hashers) {
                    hasher.putUnencodedChars(chars);
                }
                return this;
            }

            @Override
            public Hasher putString(CharSequence chars, Charset charset) {
                for (Hasher hasher : hashers) {
                    hasher.putString(chars, charset);
                }
                return this;
            }

            @Override
            public <T> Hasher putObject(T instance, Funnel<? super T> funnel) {
                for (Hasher hasher : hashers) {
                    hasher.putObject(instance, funnel);
                }
                return this;
            }

            @Override
            public HashCode hash() {
                return AbstractCompositeHashFunction.this.makeHash(hashers);
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
            public PrimitiveSink putChar(char x0) {
                return this.putChar(x0);
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

            @Override
            public PrimitiveSink putLong(long x0) {
                return this.putLong(x0);
            }

            @Override
            public PrimitiveSink putInt(int x0) {
                return this.putInt(x0);
            }

            @Override
            public PrimitiveSink putShort(short x0) {
                return this.putShort(x0);
            }

            @Override
            public PrimitiveSink putBytes(byte[] x0, int x1, int x2) {
                return this.putBytes(x0, x1, x2);
            }

            @Override
            public PrimitiveSink putBytes(byte[] x0) {
                return this.putBytes(x0);
            }

            @Override
            public PrimitiveSink putByte(byte x0) {
                return this.putByte(x0);
            }
        };
    }
}

