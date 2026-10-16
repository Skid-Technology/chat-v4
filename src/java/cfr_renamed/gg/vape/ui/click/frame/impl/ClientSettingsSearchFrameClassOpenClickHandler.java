/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;

public class ClientSettingsSearchFrameClassOpenClickHandler
implements GuiClickListener {
    private final Class var_java_lang_Class_F;
    final ClientSettingsSearchFrame var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrame_K;

    public Class p() {
        return this.var_java_lang_Class_F;
    }

    public ClientSettingsSearchFrameClassOpenClickHandler(ClientSettingsSearchFrame clientSettingsSearchFrame, Class clazz) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsSearchFrame_K = clientSettingsSearchFrame;
        this.var_java_lang_Class_F = clazz;
    }

    @Override
    public void onPrimaryClick() {
        ClientSettings.showFrame(this.var_java_lang_Class_F);
    }
}

