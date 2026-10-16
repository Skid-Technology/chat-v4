/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.SettingsFrameHeaderComponent;

class SettingsFrameHeaderPrimaryButtonClickHandler
implements GuiClickListener {
    final SettingsFrameHeaderComponent var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_D;
    final Frame var_gg_vape_ui_click_frame_Frame_p;

    @Override
    public void onPrimaryClick() {
        if (SettingsFrameHeaderComponent.D(this.var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_D) != null) {
            SettingsFrameHeaderComponent.D(this.var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_D).onPrimaryClick();
        }
        ClientSettings.setFrameVisibility(this.var_gg_vape_ui_click_frame_Frame_p.getClass(), false);
    }

    SettingsFrameHeaderPrimaryButtonClickHandler(SettingsFrameHeaderComponent settingsFrameHeaderComponent, Frame frame) {
        this.var_gg_vape_ui_click_frame_impl_SettingsFrameHeaderComponent_D = settingsFrameHeaderComponent;
        this.var_gg_vape_ui_click_frame_Frame_p = frame;
    }
}

