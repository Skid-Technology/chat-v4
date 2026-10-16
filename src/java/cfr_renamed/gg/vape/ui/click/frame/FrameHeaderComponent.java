/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.impl.ModuleSearchFrame;

public abstract class FrameHeaderComponent
extends GuiComponent {
    private Frame var_gg_vape_ui_click_frame_Frame_b;
    private static boolean var_boolean_a;

    public Frame w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() {
        return this.var_gg_vape_ui_click_frame_Frame_b;
    }

    public static boolean U$src$Z$vux5o0() {
        boolean bl = FrameHeaderComponent.l$src$Z$w7kfbb();
        return false;
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void addChildren(GuiComponent ... guiComponentArray) {
        super.addChildren(guiComponentArray);
        for (GuiComponent guiComponent : guiComponentArray) {
            guiComponent.setParentHeader(this);
        }
    }

    @Override
    public double C() {
        return 18.0;
    }

    @Override
    public double x() {
        return 110.0;
    }

    public void K(double d, double d2) {
        for (GuiComponent guiComponent : this.f()) {
            guiComponent.K(guiComponent.G$src$D$1b2f02a() + d);
            guiComponent.S(guiComponent.n() + d2);
            if (!(guiComponent instanceof FrameComponent)) continue;
            ((FrameComponent)guiComponent).R(d, d2);
        }
    }

    @Override
    public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
        if (guiMouseEvent.getAction().equals((Object)MouseButton.RIGHT_CLICK) && this.var_gg_vape_ui_click_frame_Frame_b instanceof CollapsibleFrame && !(this.var_gg_vape_ui_click_frame_Frame_b instanceof ModuleSearchFrame)) {
            ((CollapsibleFrame)((Object)this.var_gg_vape_ui_click_frame_Frame_b)).w();
            return;
        }
        super.dispatchMouseEvent(guiMouseEvent);
    }

    public FrameHeaderComponent(Frame frame) {
        this.var_gg_vape_ui_click_frame_Frame_b = frame;
    }

    public static boolean l$src$Z$w7kfbb() {
        return var_boolean_a;
    }

    public static void f(boolean bl) {
        var_boolean_a = bl;
    }

    public boolean D(int n, int n2) {
        for (GuiComponent guiComponent : this.f()) {
            if (!guiComponent.getBounds().J(n, n2)) continue;
            return false;
        }
        return this.getBounds().J(n, n2);
    }

    static {
        if (!FrameHeaderComponent.l$src$Z$w7kfbb()) {
            FrameHeaderComponent.f(true);
        }
    }
}

