/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;

public class ClientSettingsFrameOpenSearchClickHandler
implements GuiClickListener {
    final ClientSettingsFrame var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_f;

    @Override
    public void onPrimaryClick() {
        ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
        clientSettingsSearchFrame.M(this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_f.double_G(), this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_f.double_n());
        clientSettingsSearchFrame.t(true, false);
    }

    public ClientSettingsFrameOpenSearchClickHandler(ClientSettingsFrame clientSettingsFrame) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_f = clientSettingsFrame;
    }
}

