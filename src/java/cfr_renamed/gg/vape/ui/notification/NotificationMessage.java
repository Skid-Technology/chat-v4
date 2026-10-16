/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.notification;

import gg.vape.ui.notification.NotificationType;
import java.awt.Color;

public class NotificationMessage {
    int var_int_Q;
    Color var_java_awt_Color_I;
    Color var_java_awt_Color_s;
    String var_java_lang_String_p;

    public Color t() {
        return this.var_java_awt_Color_I;
    }

    public void D(Color color) {
        this.var_java_awt_Color_I = color;
    }

    public void P(Color color) {
        this.var_java_awt_Color_s = color;
    }

    public void c(String string) {
        this.var_java_lang_String_p = string;
    }

    public void T(int n) {
        this.var_int_Q = n;
    }

    public String S() {
        return this.var_java_lang_String_p;
    }

    public Color Y() {
        return this.var_java_awt_Color_s;
    }

    public NotificationMessage(NotificationType yh_02, String string) {
        this(yh_02);
        this.c(string);
    }

    public NotificationMessage(String string, Color color, Color color2, int n) {
        this.var_java_lang_String_p = string;
        this.var_java_awt_Color_I = color;
        this.var_java_awt_Color_s = color2;
        this.var_int_Q = n;
    }

    public int c() {
        return this.var_int_Q;
    }

    public NotificationMessage(NotificationType yh_02) {
        this(yh_02.K(), yh_02.s(), yh_02.S(), yh_02.G());
    }
}

