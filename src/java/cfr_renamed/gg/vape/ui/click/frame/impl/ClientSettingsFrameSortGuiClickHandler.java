/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;

public class ClientSettingsFrameSortGuiClickHandler
implements GuiClickListener {
    final ClientSettingsFrame var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_c;

    public ClientSettingsFrameSortGuiClickHandler(ClientSettingsFrame sG2) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_c = sG2;
    }

    @Override
    public void onPrimaryClick() {
        ClientSettingsFrame.a(this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_c);
    }
}

