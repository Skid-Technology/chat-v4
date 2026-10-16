/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;

class ClientSettingsSectionFrameCloseSearchClickHandler
implements GuiClickListener {
    final ClientSettingsSectionFrame var_gg_vape_ui_click_frame_impl_ClientSettingsSectionFrame_V;

    @Override
    public void onPrimaryClick() {
        ClientSettings.setFrameVisibility(ClientSettingsSearchFrame.class, false);
    }

    ClientSettingsSectionFrameCloseSearchClickHandler(ClientSettingsSectionFrame clientSettingsSectionFrame) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSectionFrame_V = clientSettingsSectionFrame;
    }
}

