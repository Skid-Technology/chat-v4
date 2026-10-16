/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.Mod;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderNextModuleClickHandler
implements GuiClickListener {
    final Mod var_gg_vape_module_Mod_i;
    final ModuleCategoryFrameHeader var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_U;

    public ModuleCategoryFrameHeaderNextModuleClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, Mod mod) {
        this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_U = moduleCategoryFrameHeader;
        this.var_gg_vape_module_Mod_i = mod;
    }

    @Override
    public void onPrimaryClick() {
        ModuleCategoryFrameHeader.e(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_U).G(this.var_gg_vape_module_Mod_i);
        ModuleCategoryFrameHeader.e(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_U).W(this.var_gg_vape_module_Mod_i).expandValueComponents();
        ModuleCategoryFrameHeader.e(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_U).l$src$V$1mibm4x();
    }
}

