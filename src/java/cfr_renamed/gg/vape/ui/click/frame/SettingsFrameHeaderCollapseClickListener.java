/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.SettingsFrameHeaderComponent;

class SettingsFrameHeaderCollapseClickListener
implements GuiClickListener {
    final SettingsFrameHeaderComponent var_gg_vape_ui_click_frame_SettingsFrameHeaderComponent_C;
    final Frame var_gg_vape_ui_click_frame_Frame_V;

    @Override
    public void onPrimaryClick() {
        if (this.var_gg_vape_ui_click_frame_Frame_V instanceof CollapsibleFrame) {
            ((CollapsibleFrame)((Object)this.var_gg_vape_ui_click_frame_Frame_V)).w();
        }
    }

    SettingsFrameHeaderCollapseClickListener(SettingsFrameHeaderComponent settingsFrameHeaderComponent, Frame frame) {
        this.var_gg_vape_ui_click_frame_SettingsFrameHeaderComponent_C = settingsFrameHeaderComponent;
        this.var_gg_vape_ui_click_frame_Frame_V = frame;
    }
}

