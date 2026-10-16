/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Writer;

public class NullWriter
extends Writer {
    final public static NullWriter NULL_WRITER = new NullWriter();

    @Override
    public Writer append(char c) {
        return this;
    }

    @Override
    public Writer append(CharSequence csq, int start, int end) {
        return this;
    }

    @Override
    public Writer append(CharSequence csq) {
        return this;
    }

    @Override
    public void write(int idx) {
    }

    @Override
    public void write(char[] chr) {
    }

    @Override
    public void write(char[] chr, int st, int end) {
    }

    @Override
    public void write(String str) {
    }

    @Override
    public void write(String str, int st, int end) {
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
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

