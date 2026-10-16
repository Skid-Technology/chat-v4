/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.PublicProfileSortMode;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrame;
import java.awt.Color;

class PublicProfileSortModeButton
extends TextButton {
    private final PublicProfileSortMode sortMode;

    @Override
    public void H() {
        super.H();
        PublicProfilesFrame publicProfilesFrame = ClientSettings.getFrame(PublicProfilesFrame.class);
        if (publicProfilesFrame.Z$src$Lgg_vape_config_PublicProfileSortMode_$18pvsyy() == this.sortMode) {
            this.setNormalTextColor(Color.WHITE);
            this.setBackgroundAnimationColors(PublicProfileSortModeButton.J.var_java_awt_Color_B, PublicProfileSortModeButton.J.var_java_awt_Color_O);
        } else {
            this.setNormalTextColor(PublicProfileSortModeButton.J.var_java_awt_Color_h);
            this.setBackgroundAnimationColors(PublicProfileSortModeButton.J.var_java_awt_Color_i, PublicProfileSortModeButton.J.var_java_awt_Color_y);
        }
    }

    PublicProfileSortModeButton(String string, double d, Color color, Color color2, PublicProfileSortMode publicProfileSortMode) {
        super(string, d, color, color2);
        this.sortMode = publicProfileSortMode;
    }
}

