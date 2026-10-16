/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.SessionSpoofFrame;

public class SessionSpoofApplyClickHandler
implements GuiClickListener {
    final SessionSpoofFrame var_gg_vape_ui_click_frame_impl_SessionSpoofFrame_P;

    @Override
    public void onPrimaryClick() {
        if (SessionSpoofFrame.t(this.var_gg_vape_ui_click_frame_impl_SessionSpoofFrame_P) != null) {
            SessionSpoofFrame.M(this.var_gg_vape_ui_click_frame_impl_SessionSpoofFrame_P);
        }
    }

    public SessionSpoofApplyClickHandler(SessionSpoofFrame sw_02) {
        this.var_gg_vape_ui_click_frame_impl_SessionSpoofFrame_P = sw_02;
    }
}

