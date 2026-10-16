/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiKeyTypedListener;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

public class ClientSettingsSearchFrameHeaderInputChangeListener
implements GuiKeyTypedListener {
    final ClientSettingsSearchFrameHeader var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_W;
    final ClientSettingsSearchFrame var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrame_y;

    @Override
    public void onKeyTyped(char c, int n) {
        if (ClientSettingsSearchFrameHeader.b(this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_W) != ClientSettingsSearchFrameHeader.j(this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_W).getText().length()) {
            this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrame_y.rebuildContent();
            ClientSettings.activeTooltips = null;
        }
        ClientSettingsSearchFrameHeader.L(this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_W, ClientSettingsSearchFrameHeader.j(this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_W).getText().length());
    }

    public ClientSettingsSearchFrameHeaderInputChangeListener(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader, ClientSettingsSearchFrame clientSettingsSearchFrame) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrameHeader_W = clientSettingsSearchFrameHeader;
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrame_y = clientSettingsSearchFrame;
    }
}

