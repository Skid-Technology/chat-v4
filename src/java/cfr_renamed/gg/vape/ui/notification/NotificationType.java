/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.notification;

import java.awt.Color;

public enum NotificationType {
    FRIENDINVITESENT("Friend invite sent", new Color(6, 161, 126), new Color(4, 106, 83)),
    SUCCESS("Success message", new Color(6, 161, 126), new Color(4, 106, 83)),
    WARNING("Warning message", new Color(236, 129, 44), new Color(156, 85, 29)),
    ERROR("Error Message", new Color(240, 100, 100), new Color(165, 33, 37));

    private static boolean var_boolean_F;
    private final static NotificationType[] var_gg_vape_ui_notification_NotificationType_arr_u;
    Color var_java_awt_Color_b;
    Color var_java_awt_Color_D;
    int var_int_M;
    String var_java_lang_String_p;

    public void p(String string) {
        this.var_java_lang_String_p = string;
    }

    public void f(Color color) {
        this.var_java_awt_Color_b = color;
    }

    public static boolean u() {
        return var_boolean_F;
    }

    private NotificationType(String string2, Color color, Color color2, int n2) {
        this.var_java_lang_String_p = string2;
        this.var_java_awt_Color_D = color;
        this.var_java_awt_Color_b = color2;
        this.var_int_M = n2;
    }

    private NotificationType(String string2, Color color, Color color2) {
        this.var_java_lang_String_p = string2;
        this.var_java_awt_Color_D = color;
        this.var_java_awt_Color_b = color2;
        this.var_int_M = 3000;
    }

    public void p(int n) {
        this.var_int_M = n;
    }

    public static boolean i() {
        boolean bl = NotificationType.u();
        return !bl;
    }

    private NotificationType(Color color, Color color2, int n2) {
        this.var_java_lang_String_p = "";
        this.var_java_awt_Color_D = color;
        this.var_java_awt_Color_b = color2;
        this.var_int_M = n2;
    }

    private NotificationType(Color color, Color color2) {
        this.var_java_lang_String_p = "";
        this.var_java_awt_Color_D = color;
        this.var_java_awt_Color_b = color2;
        this.var_int_M = 3000;
    }

    public int G() {
        return this.var_int_M;
    }

    public void V(Color color) {
        this.var_java_awt_Color_D = color;
    }

    public Color S() {
        return this.var_java_awt_Color_b;
    }

    private NotificationType(String string2, Color color) {
        this.var_java_lang_String_p = string2;
        this.var_java_awt_Color_D = color;
        this.var_java_awt_Color_b = color.darker();
        this.var_int_M = 3000;
    }

    public String K() {
        return this.var_java_lang_String_p;
    }

    public Color s() {
        return this.var_java_awt_Color_D;
    }

    public static void S(boolean bl) {
        var_boolean_F = bl;
    }

    static {
        if (!NotificationType.i()) {
            NotificationType.S(true);
        }
        String[] stringArray = new String[]{"Error Message", "SUCCESS", "FRIENDINVITESENT", "ERROR", "Warning message", "Success message", "WARNING", "Friend invite sent"};
        var_gg_vape_ui_notification_NotificationType_arr_u = new NotificationType[]{FRIENDINVITESENT, SUCCESS, WARNING, ERROR};
    }
}

