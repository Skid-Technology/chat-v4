/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;

class ClientSettingsSectionFrameCloseSettingsClickHandler
implements GuiClickListener {
    final ClientSettingsSectionFrame var_gg_vape_ui_click_frame_impl_ClientSettingsSectionFrame_J;

    ClientSettingsSectionFrameCloseSettingsClickHandler(ClientSettingsSectionFrame clientSettingsSectionFrame) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSectionFrame_J = clientSettingsSectionFrame;
    }

    @Override
    public void onPrimaryClick() {
        ClientSettings.setFrameVisibility(ClientSettingsFrame.class, false);
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSectionFrame_J.t(false, false);
    }
}

