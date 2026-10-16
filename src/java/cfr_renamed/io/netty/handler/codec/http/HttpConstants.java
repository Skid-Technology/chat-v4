/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.http;

import io.netty.util.CharsetUtil;
import java.nio.charset.Charset;

public final class HttpConstants {
    final public static byte SP = 32;
    final public static byte HT = 9;
    final public static byte CR = 13;
    final public static byte EQUALS = 61;
    final public static byte LF = 10;
    final public static byte COLON = 58;
    final public static byte SEMICOLON = 59;
    final public static byte COMMA = 44;
    final public static byte DOUBLE_QUOTE = 34;
    final public static Charset DEFAULT_CHARSET = CharsetUtil.UTF_8;

    private HttpConstants() {
    }
}

