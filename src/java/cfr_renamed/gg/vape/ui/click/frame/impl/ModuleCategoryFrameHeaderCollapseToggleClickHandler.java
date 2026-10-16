/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeader;

public class ModuleCategoryFrameHeaderCollapseToggleClickHandler
implements GuiClickListener {
    final ModuleCategoryFrame var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_o;
    final ModuleCategoryFrameHeader var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_u;

    @Override
    public void onPrimaryClick() {
        this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_o.w();
    }

    public ModuleCategoryFrameHeaderCollapseToggleClickHandler(ModuleCategoryFrameHeader moduleCategoryFrameHeader, ModuleCategoryFrame moduleCategoryFrame) {
        this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrameHeader_u = moduleCategoryFrameHeader;
        this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_o = moduleCategoryFrame;
    }
}

