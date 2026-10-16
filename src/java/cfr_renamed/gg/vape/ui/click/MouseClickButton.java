/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click;

public enum MouseClickButton {
    LEFT_CLICK,
    MIDDLE_CLICK,
    RIGHT_CLICK;

    private final static MouseClickButton[] var_gg_vape_ui_click_MouseClickButton_arr_v;
    private static boolean var_boolean_I;

    public static boolean P() {
        boolean bl = MouseClickButton.p();
        return true;
    }

    public static boolean p() {
        return var_boolean_I;
    }

    public static void b(boolean bl) {
        var_boolean_I = bl;
    }

    static {
        if (MouseClickButton.p()) {
            MouseClickButton.b(true);
        }
        String[] stringArray = new String[]{"RIGHT_CLICK", "MIDDLE_CLICK", "LEFT_CLICK"};
        var_gg_vape_ui_click_MouseClickButton_arr_v = new MouseClickButton[]{LEFT_CLICK, MIDDLE_CLICK, RIGHT_CLICK};
    }
}

