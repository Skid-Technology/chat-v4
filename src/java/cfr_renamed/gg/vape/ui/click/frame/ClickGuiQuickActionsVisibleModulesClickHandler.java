/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.ClickGuiQuickActionsComponent;
import gg.vape.ui.click.frame.impl.VisibleModuleListFrame;

public class ClickGuiQuickActionsVisibleModulesClickHandler
implements GuiClickListener {
    final ClickGuiQuickActionsComponent var_gg_vape_ui_click_frame_ClickGuiQuickActionsComponent_v;

    @Override
    public void onPrimaryClick() {
        ClientSettings.showFrame(VisibleModuleListFrame.class);
    }

    public ClickGuiQuickActionsVisibleModulesClickHandler(ClickGuiQuickActionsComponent clickGuiQuickActionsComponent) {
        this.var_gg_vape_ui_click_frame_ClickGuiQuickActionsComponent_v = clickGuiQuickActionsComponent;
    }
}

