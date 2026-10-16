/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;

public class ClientSettingsFrameResetGuiLayoutClickHandler
implements GuiClickListener {
    final ClientSettingsFrame var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_j;

    @Override
    public void onPrimaryClick() {
        ClientSettingsFrame.n(this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_j);
    }

    public ClientSettingsFrameResetGuiLayoutClickHandler(ClientSettingsFrame clientSettingsFrame) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_j = clientSettingsFrame;
    }
}

