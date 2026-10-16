/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io;

import java.nio.charset.Charset;

public class Charsets {
    final public static Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    final public static Charset US_ASCII = Charset.forName("US-ASCII");
    final public static Charset UTF_16 = Charset.forName("UTF-16");
    final public static Charset UTF_16BE = Charset.forName("UTF-16BE");
    final public static Charset UTF_16LE = Charset.forName("UTF-16LE");
    final public static Charset UTF_8 = Charset.forName("UTF-8");

    public static Charset toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    public static Charset toCharset(String charset) {
        return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
    }
}

