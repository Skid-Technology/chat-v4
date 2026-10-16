/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

class ClientSettingsSearchFrameHeaderManualSyncClickHandler
implements GuiClickListener {
    final ClientSettingsSearchFrameHeader var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_z;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.getSyncThread().requestSave();
        ClientSettingsSearchFrameHeader.F(this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_z).setVisible(false);
    }

    ClientSettingsSearchFrameHeaderManualSyncClickHandler(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_z = clientSettingsSearchFrameHeader;
    }
}

