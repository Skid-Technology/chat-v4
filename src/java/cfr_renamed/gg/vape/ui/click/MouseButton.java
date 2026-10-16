/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click;

import gg.vape.ui.click.MouseClickButton;

public enum MouseButton {
    LEFT_CLICK,
    RIGHT_CLICK,
    MIDDLE_CLICK,
    UNKNOWN;

    private final static MouseButton[] var_gg_vape_ui_click_MouseButton_arr_J;

    public static MouseButton A(int n) {
        for (MouseButton mouseButton : MouseButton.values()) {
            if (mouseButton.ordinal() != n) continue;
            return mouseButton;
        }
        return UNKNOWN;
    }

    public MouseClickButton G() {
        return this == LEFT_CLICK ? MouseClickButton.LEFT_CLICK : (this == RIGHT_CLICK ? MouseClickButton.RIGHT_CLICK : (this == MIDDLE_CLICK ? MouseClickButton.MIDDLE_CLICK : null));
    }

    static {
        String[] stringArray = new String[]{"LEFT_CLICK", "MIDDLE_CLICK", "RIGHT_CLICK", "UNKNOWN"};
        var_gg_vape_ui_click_MouseButton_arr_J = new MouseButton[]{LEFT_CLICK, RIGHT_CLICK, MIDDLE_CLICK, UNKNOWN};
    }
}

