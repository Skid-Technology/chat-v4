/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.internal;

import java.io.IOException;
import java.util.Arrays;

public final class AppendableCharSequence
implements CharSequence,
Appendable {
    private char[] chars;
    private int pos;

    public AppendableCharSequence(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length: " + length + " (length: >= 1)");
        }
        this.chars = new char[length];
    }

    private AppendableCharSequence(char[] chars) {
        this.chars = chars;
        this.pos = chars.length;
    }

    @Override
    public int length() {
        return this.pos;
    }

    @Override
    public char charAt(int index) {
        if (index > this.pos) {
            throw new IndexOutOfBoundsException();
        }
        return this.chars[index];
    }

    @Override
    public AppendableCharSequence subSequence(int start, int end) {
        return new AppendableCharSequence(Arrays.copyOfRange(this.chars, start, end));
    }

    @Override
    public AppendableCharSequence append(char c) {
        if (this.pos == this.chars.length) {
            char[] old = this.chars;
            int len = old.length << 1;
            if (len < 0) {
                throw new IllegalStateException();
            }
            this.chars = new char[len];
            System.arraycopy(old, 0, this.chars, 0, old.length);
        }
        this.chars[this.pos++] = c;
        return this;
    }

    @Override
    public AppendableCharSequence append(CharSequence csq) {
        return this.append(csq, 0, csq.length());
    }

    @Override
    public AppendableCharSequence append(CharSequence csq, int start, int end) {
        if (csq.length() < end) {
            throw new IndexOutOfBoundsException();
        }
        int length = end - start;
        if (length > this.chars.length - this.pos) {
            this.chars = AppendableCharSequence.expand(this.chars, this.pos + length, this.pos);
        }
        if (csq instanceof AppendableCharSequence) {
            AppendableCharSequence seq = (AppendableCharSequence)csq;
            char[] src = seq.chars;
            System.arraycopy(src, start, this.chars, this.pos, length);
            this.pos += length;
            return this;
        }
        for (int i = start; i < end; ++i) {
            this.chars[this.pos++] = csq.charAt(i);
        }
        return this;
    }

    public void reset() {
        this.pos = 0;
    }

    @Override
    public String toString() {
        return new String(this.chars, 0, this.pos);
    }

    public String substring(int start, int end) {
        int length = end - start;
        if (start > this.pos || length > this.pos) {
            throw new IndexOutOfBoundsException();
        }
        return new String(this.chars, start, length);
    }

    private static char[] expand(char[] array, int neededSpace, int size) {
        int newCapacity = array.length;
        do {
            if ((newCapacity <<= 1) >= 0) continue;
            throw new IllegalStateException();
        } while (neededSpace > newCapacity);
        char[] newArray = new char[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public CharSequence subSequence(int x0, int x1) {
        return this.subSequence(x0, x1);
    }

    @Override
    public Appendable append(char x0) throws IOException {
        return this.append(x0);
    }

    @Override
    public Appendable append(CharSequence x0, int x1, int x2) throws IOException {
        return this.append(x0, x1, x2);
    }

    @Override
    public Appendable append(CharSequence x0) throws IOException {
        return this.append(x0);
    }
}

