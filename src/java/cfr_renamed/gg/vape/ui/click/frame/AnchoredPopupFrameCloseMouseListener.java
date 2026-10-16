/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.AnchoredPopupFrame;
import java.awt.Point;

class AnchoredPopupFrameCloseMouseListener
implements GuiMouseListener {
    final AnchoredPopupFrame var_gg_vape_ui_click_frame_AnchoredPopupFrame_O;

    AnchoredPopupFrameCloseMouseListener(AnchoredPopupFrame anchoredPopupFrame) {
        this.var_gg_vape_ui_click_frame_AnchoredPopupFrame_O = anchoredPopupFrame;
    }

    @Override
    public void g(Point point, MouseClickButton uA) {
        ClientSettings.removePopup(this.var_gg_vape_ui_click_frame_AnchoredPopupFrame_O);
        Runnable runnable = AnchoredPopupFrame.b(this.var_gg_vape_ui_click_frame_AnchoredPopupFrame_O);
        if (runnable != null) {
            runnable.run();
        }
    }
}

