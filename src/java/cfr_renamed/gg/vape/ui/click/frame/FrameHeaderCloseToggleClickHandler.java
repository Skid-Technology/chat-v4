/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.CloseableFrameHeaderComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;

class FrameHeaderCloseToggleClickHandler
implements GuiClickListener {
    final Frame var_gg_vape_ui_click_frame_Frame_h;
    final CloseableFrameHeaderComponent var_gg_vape_ui_click_frame_CloseableFrameHeaderComponent_x;

    FrameHeaderCloseToggleClickHandler(CloseableFrameHeaderComponent closeableFrameHeaderComponent, Frame frame) {
        this.var_gg_vape_ui_click_frame_CloseableFrameHeaderComponent_x = closeableFrameHeaderComponent;
        this.var_gg_vape_ui_click_frame_Frame_h = frame;
    }

    @Override
    public void onPrimaryClick() {
        if (this.var_gg_vape_ui_click_frame_Frame_h instanceof CollapsibleFrame) {
            ((CollapsibleFrame)((Object)this.var_gg_vape_ui_click_frame_Frame_h)).w();
            CloseableFrameHeaderComponent.c(this.var_gg_vape_ui_click_frame_CloseableFrameHeaderComponent_x, ((CollapsibleFrame)((Object)this.var_gg_vape_ui_click_frame_Frame_h)).q());
        }
    }
}

