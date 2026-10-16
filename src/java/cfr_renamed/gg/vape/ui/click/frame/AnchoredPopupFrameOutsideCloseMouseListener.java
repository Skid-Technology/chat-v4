/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import java.awt.Point;

class AnchoredPopupFrameOutsideCloseMouseListener
implements GuiMouseListener {
    final GuiComponent var_gg_vape_ui_click_component_GuiComponent_J;
    final AnchoredPopupFrame var_gg_vape_ui_click_frame_AnchoredPopupFrame_q;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (!this.var_gg_vape_ui_click_frame_AnchoredPopupFrame_q.w$src$Z$e457mb() && !this.var_gg_vape_ui_click_component_GuiComponent_J.w$src$Z$e457mb()) {
            ClientSettings.removePopup(this.var_gg_vape_ui_click_frame_AnchoredPopupFrame_q);
            Runnable runnable = AnchoredPopupFrame.b(this.var_gg_vape_ui_click_frame_AnchoredPopupFrame_q);
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    AnchoredPopupFrameOutsideCloseMouseListener(AnchoredPopupFrame anchoredPopupFrame, GuiComponent guiComponent) {
        this.var_gg_vape_ui_click_frame_AnchoredPopupFrame_q = anchoredPopupFrame;
        this.var_gg_vape_ui_click_component_GuiComponent_J = guiComponent;
    }
}

