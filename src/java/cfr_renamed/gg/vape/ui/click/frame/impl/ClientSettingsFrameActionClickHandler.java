/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;

public class ClientSettingsFrameActionClickHandler
implements GuiClickListener {
    final ClientSettingsFrame var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_p;
    final String var_java_lang_String_k;

    @Override
    public void onPrimaryClick() {
        ClientSettingsSectionFrame clientSettingsSectionFrame = ClientSettingsFrame.m(this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_p, this.var_java_lang_String_k);
        clientSettingsSectionFrame.addChildren((GuiComponent[])ClientSettingsFrame.t(this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_p).get(this.var_java_lang_String_k));
    }

    public ClientSettingsFrameActionClickHandler(ClientSettingsFrame sG2, String string) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_p = sG2;
        this.var_java_lang_String_k = string;
    }
}

