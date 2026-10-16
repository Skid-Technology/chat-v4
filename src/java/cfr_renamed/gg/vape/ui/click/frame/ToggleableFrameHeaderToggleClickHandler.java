/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.ToggleableFrameHeaderComponent;

class ToggleableFrameHeaderToggleClickHandler
implements GuiClickListener {
    final ToggleableFrameHeaderComponent var_gg_vape_ui_click_frame_ToggleableFrameHeaderComponent_w;

    @Override
    public void onPrimaryClick() {
        ToggleableFrameHeaderComponent toggleableFrameHeaderComponent = this.var_gg_vape_ui_click_frame_ToggleableFrameHeaderComponent_w;
        boolean bl = !ToggleableFrameHeaderComponent.i(this.var_gg_vape_ui_click_frame_ToggleableFrameHeaderComponent_w);
        ToggleableFrameHeaderComponent.a(toggleableFrameHeaderComponent, bl);
        this.var_gg_vape_ui_click_frame_ToggleableFrameHeaderComponent_w.R();
    }

    ToggleableFrameHeaderToggleClickHandler(ToggleableFrameHeaderComponent toggleableFrameHeaderComponent) {
        this.var_gg_vape_ui_click_frame_ToggleableFrameHeaderComponent_w = toggleableFrameHeaderComponent;
    }
}

