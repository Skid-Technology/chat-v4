/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.SettingsSubpageFrame;

public class SettingsSubpageFrameRefreshClickHandler
implements GuiClickListener {
    final SettingsSubpageFrame var_gg_vape_ui_click_frame_SettingsSubpageFrame_C;

    public SettingsSubpageFrameRefreshClickHandler(SettingsSubpageFrame settingsSubpageFrame) {
        this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_C = settingsSubpageFrame;
    }

    @Override
    public void onPrimaryClick() {
        this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_C.w();
    }
}

