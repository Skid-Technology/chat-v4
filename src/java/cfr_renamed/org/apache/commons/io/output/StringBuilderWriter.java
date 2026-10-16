/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class StringBuilderWriter
extends Writer
implements Serializable {
    private final StringBuilder builder;

    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }

    public StringBuilderWriter(int capacity) {
        this.builder = new StringBuilder(capacity);
    }

    public StringBuilderWriter(StringBuilder builder) {
        this.builder = builder != null ? builder : new StringBuilder();
    }

    @Override
    public Writer append(char value) {
        this.builder.append(value);
        return this;
    }

    @Override
    public Writer append(CharSequence value) {
        this.builder.append(value);
        return this;
    }

    @Override
    public Writer append(CharSequence value, int start, int end) {
        this.builder.append(value, start, end);
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    @Override
    public void write(String value) {
        if (value != null) {
            this.builder.append(value);
        }
    }

    @Override
    public void write(char[] value, int offset, int length) {
        if (value != null) {
            this.builder.append(value, offset, length);
        }
    }

    public StringBuilder getBuilder() {
        return this.builder;
    }

    public String toString() {
        return this.builder.toString();
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

