/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.quickactions;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.quickactions.QuickActionsFrame;

public class QuickActionsHeaderCloseCallback
implements GuiClickListener {
    final QuickActionsFrame var_gg_vape_ui_click_frame_impl_quickactions_QuickActionsFrame_u;

    @Override
    public void onPrimaryClick() {
        QuickActionsFrame.Y(this.var_gg_vape_ui_click_frame_impl_quickactions_QuickActionsFrame_u, 3);
    }

    public QuickActionsHeaderCloseCallback(QuickActionsFrame sz_02) {
        this.var_gg_vape_ui_click_frame_impl_quickactions_QuickActionsFrame_u = sz_02;
    }
}

