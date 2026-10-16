/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.Frame;
import java.awt.Point;

public class FrameOutsideChildClickFilterMouseListener
implements GuiMouseListener {
    final Frame var_gg_vape_ui_click_frame_Frame_I;
    final GuiComponent var_gg_vape_ui_click_component_GuiComponent_s;
    final Frame var_gg_vape_ui_click_frame_Frame_G;

    public FrameOutsideChildClickFilterMouseListener(GuiComponent guiComponent, Frame frame, Frame frame2) {
        this.var_gg_vape_ui_click_component_GuiComponent_s = guiComponent;
        this.var_gg_vape_ui_click_frame_Frame_I = frame;
        this.var_gg_vape_ui_click_frame_Frame_G = frame2;
    }

    @Override
    public boolean Q(Point point) {
        if (this.var_gg_vape_ui_click_frame_Frame_I.getBounds().R(point) && !this.var_gg_vape_ui_click_frame_Frame_G.getBounds().R(point)) {
            return true;
        }
        return GuiMouseListener.super.Q(point);
    }
}

