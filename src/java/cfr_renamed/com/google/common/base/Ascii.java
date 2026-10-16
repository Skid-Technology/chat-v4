/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.CheckReturnValue
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import javax.annotation.CheckReturnValue;

@GwtCompatible
public final class Ascii {
    final public static byte NUL = 0;
    final public static byte SOH = 1;
    final public static byte STX = 2;
    final public static byte ETX = 3;
    final public static byte EOT = 4;
    final public static byte ENQ = 5;
    final public static byte ACK = 6;
    final public static byte BEL = 7;
    final public static byte BS = 8;
    final public static byte HT = 9;
    final public static byte LF = 10;
    final public static byte NL = 10;
    final public static byte VT = 11;
    final public static byte FF = 12;
    final public static byte CR = 13;
    final public static byte SO = 14;
    final public static byte SI = 15;
    final public static byte DLE = 16;
    final public static byte DC1 = 17;
    final public static byte XON = 17;
    final public static byte DC2 = 18;
    final public static byte DC3 = 19;
    final public static byte XOFF = 19;
    final public static byte DC4 = 20;
    final public static byte NAK = 21;
    final public static byte SYN = 22;
    final public static byte ETB = 23;
    final public static byte CAN = 24;
    final public static byte EM = 25;
    final public static byte SUB = 26;
    final public static byte ESC = 27;
    final public static byte FS = 28;
    final public static byte GS = 29;
    final public static byte RS = 30;
    final public static byte US = 31;
    final public static byte SP = 32;
    final public static byte SPACE = 32;
    final public static byte DEL = 127;
    final public static char MIN = '\u0000';
    final public static char MAX = '\u007f';

    private Ascii() {
    }

    public static String toLowerCase(String string) {
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            if (!Ascii.isUpperCase(string.charAt(i))) continue;
            char[] chars = string.toCharArray();
            while (i < length) {
                char c = chars[i];
                if (Ascii.isUpperCase(c)) {
                    chars[i] = (char)(c ^ 0x20);
                }
                ++i;
            }
            return String.valueOf(chars);
        }
        return string;
    }

    public static String toLowerCase(CharSequence chars) {
        if (chars instanceof String) {
            return Ascii.toLowerCase((String)chars);
        }
        int length = chars.length();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            builder.append(Ascii.toLowerCase(chars.charAt(i)));
        }
        return builder.toString();
    }

    public static char toLowerCase(char c) {
        return Ascii.isUpperCase(c) ? (char)(c ^ 0x20) : c;
    }

    public static String toUpperCase(String string) {
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            if (!Ascii.isLowerCase(string.charAt(i))) continue;
            char[] chars = string.toCharArray();
            while (i < length) {
                char c = chars[i];
                if (Ascii.isLowerCase(c)) {
                    chars[i] = (char)(c & 0x5F);
                }
                ++i;
            }
            return String.valueOf(chars);
        }
        return string;
    }

    public static String toUpperCase(CharSequence chars) {
        if (chars instanceof String) {
            return Ascii.toUpperCase((String)chars);
        }
        int length = chars.length();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            builder.append(Ascii.toUpperCase(chars.charAt(i)));
        }
        return builder.toString();
    }

    public static char toUpperCase(char c) {
        return Ascii.isLowerCase(c) ? (char)(c & 0x5F) : c;
    }

    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    @CheckReturnValue
    @Beta
    public static String truncate(CharSequence seq, int maxLength, String truncationIndicator) {
        Preconditions.checkNotNull(seq);
        int truncationLength = maxLength - truncationIndicator.length();
        Preconditions.checkArgument(truncationLength >= 0, "maxLength (%s) must be >= length of the truncation indicator (%s)", maxLength, truncationIndicator.length());
        if (seq.length() <= maxLength) {
            String string = seq.toString();
            if (string.length() <= maxLength) {
                return string;
            }
            seq = string;
        }
        return new StringBuilder(maxLength).append(seq, 0, truncationLength).append(truncationIndicator).toString();
    }

    @Beta
    public static boolean equalsIgnoreCase(CharSequence s1, CharSequence s2) {
        int length = s1.length();
        if (s1 == s2) {
            return true;
        }
        if (length != s2.length()) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            int alphaIndex;
            char c2;
            char c1 = s1.charAt(i);
            if (c1 == (c2 = s2.charAt(i)) || (alphaIndex = Ascii.getAlphaIndex(c1)) < 26 && alphaIndex == Ascii.getAlphaIndex(c2)) continue;
            return false;
        }
        return true;
    }

    private static int getAlphaIndex(char c) {
        return (char)((c | 0x20) - 97);
    }
}

