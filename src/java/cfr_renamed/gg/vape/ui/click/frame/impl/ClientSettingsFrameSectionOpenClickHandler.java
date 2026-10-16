/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSectionFrame;

public class ClientSettingsFrameSectionOpenClickHandler
implements GuiClickListener {
    final String var_java_lang_String_c;
    final ClientSettingsFrame var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_I;

    @Override
    public void onPrimaryClick() {
        ClientSettingsSectionFrame clientSettingsSectionFrame = ClientSettingsFrame.m(this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_I, this.var_java_lang_String_c);
        clientSettingsSectionFrame.addChildren((GuiComponent[])ClientSettingsFrame.t(this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_I).get(this.var_java_lang_String_c));
    }

    public ClientSettingsFrameSectionOpenClickHandler(ClientSettingsFrame sG2, String string) {
        this.var_gg_vape_ui_click_frame_impl_ClientSettingsFrame_I = sG2;
        this.var_java_lang_String_c = string;
    }
}

