/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.SettingsFrameHeaderComponent;

class SettingsFrameHeaderSecondaryButtonClickHandler
implements GuiClickListener {
    final SettingsFrameHeaderComponent var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_Q;

    SettingsFrameHeaderSecondaryButtonClickHandler(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        this.var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_Q = settingsFrameHeaderComponent;
    }

    @Override
    public void onPrimaryClick() {
        if (SettingsFrameHeaderComponent.O(this.var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_Q) != null) {
            SettingsFrameHeaderComponent.O(this.var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_Q).onPrimaryClick();
        }
    }
}

