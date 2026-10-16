/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderActionClickHandler
implements GuiClickListener {
    final ModuleCategoryFrameHeader var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_d;
    final Mod var_gg_vape_module_Mod_E;

    public ModuleCategoryFrameHeaderActionClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, Mod mod) {
        this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_d = moduleCategoryFrameHeader;
        this.var_gg_vape_module_Mod_E = mod;
    }

    @Override
    public void onPrimaryClick() {
        ModuleCategoryFrameHeader.e(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_d).G(this.var_gg_vape_module_Mod_E);
        ModuleCategoryFrameHeader.e(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_d).W(this.var_gg_vape_module_Mod_E).expandValueComponents();
        ModuleCategoryFrameHeader.e(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_d).l$src$V$1mibm4x();
    }
}

