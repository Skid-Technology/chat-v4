/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.friend;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponentContract;
import java.awt.Color;

public enum FriendRelationColorState {
    FRIEND(ClientSettings.INSTANCE.getAccentColor()),
    SYNCED(GuiComponentContract.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_T),
    ENEMY(GuiComponentContract.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_d);

    Color color;
    private final static FriendRelationColorState[] VALUES;

    private FriendRelationColorState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    static {
        String[] relationNames = new String[]{"ENEMY", "FRIEND", "SYNCED"};
        VALUES = new FriendRelationColorState[]{FRIEND, SYNCED, ENEMY};
    }
}

