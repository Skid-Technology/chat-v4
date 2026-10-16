/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils;

public class TimerUtil {
    protected long var_long_Q;
    private static String var_java_lang_String_o;

    public long long_B() {
        return this.var_long_Q;
    }

    public static void p(String string) {
        var_java_lang_String_o = string;
    }

    public boolean hasTimeElapsed(long l) {
        return this.getLastMS() >= l;
    }

    public void x(long l) {
        this.var_long_Q = System.currentTimeMillis() + l;
    }

    public TimerUtil() {
        this.reset();
    }

    public void reset() {
        this.var_long_Q = System.currentTimeMillis();
    }

    public static String p() {
        return var_java_lang_String_o;
    }

    public long getLastMS() {
        return System.currentTimeMillis() - this.var_long_Q;
    }

    static {
        if (TimerUtil.p() != null) {
            TimerUtil.p("s6ZeSb");
        }
    }
}

