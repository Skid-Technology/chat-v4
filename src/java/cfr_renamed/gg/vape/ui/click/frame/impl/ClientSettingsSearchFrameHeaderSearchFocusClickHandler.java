/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

class ClientSettingsSearchFrameHeaderSearchFocusClickHandler
implements GuiClickListener {
    final ClientSettingsSearchFrameHeader var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_q;

    @Override
    public void onPrimaryClick() {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_q.focusSearchInput();
    }

    ClientSettingsSearchFrameHeaderSearchFocusClickHandler(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_q = clientSettingsSearchFrameHeader;
    }
}

