/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosEditor;
import java.awt.Point;

class FrameMacrosEditorRefreshFrameMouseListener
implements GuiMouseListener {
    final FrameMacros var_gg_vape_ui_click_frame_impl_FrameMacros_j;
    final FrameMacrosEditor var_gg_vape_ui_click_frame_impl_FrameMacrosEditor_k;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.var_gg_vape_ui_click_frame_impl_FrameMacros_j.l$src$Lgg_vape_ui_click_frame_impl_FrameMacrosEditor_$1712c36();
    }

    FrameMacrosEditorRefreshFrameMouseListener(FrameMacrosEditor frameMacrosEditor, FrameMacros frameMacros) {
        this.var_gg_vape_ui_click_frame_impl_FrameMacrosEditor_k = frameMacrosEditor;
        this.var_gg_vape_ui_click_frame_impl_FrameMacros_j = frameMacros;
    }
}

