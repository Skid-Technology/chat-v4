/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderSearchLabelClickHandler
implements GuiClickListener {
    final ModuleCategoryFrameHeader var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_r;

    @Override
    public void onPrimaryClick() {
        boolean bl = !ClientSettings.moduleSearchActive;
        ClientSettings.moduleSearchActive = bl;
        ClientSettings.refreshModuleCategoryHeaders();
    }

    public ModuleCategoryFrameHeaderSearchLabelClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader) {
        this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_r = moduleCategoryFrameHeader;
    }
}

