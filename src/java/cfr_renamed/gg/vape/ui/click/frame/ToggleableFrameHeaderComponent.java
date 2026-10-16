/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.CloseableFrameHeaderComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.ToggleableFrameHeaderToggleClickHandler;
import gg.vape.value.Value;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToggleableFrameHeaderComponent
extends CloseableFrameHeaderComponent {
    private List<GuiComponent> om;
    private final static String jb = "newsettings";
    private boolean var_boolean_o;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_i = new IconButtonComponent("newsettings");

    public void q(GuiComponent ... guiComponentArray) {
        this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().addChildren(guiComponentArray);
        this.om.addAll(Arrays.asList(guiComponentArray));
        for (GuiComponent guiComponent : this.om) {
            guiComponent.setDisabledOverlayColor(ToggleableFrameHeaderComponent.J.var_java_awt_Color_r);
            guiComponent.setRemovable(false);
            guiComponent.setVisible(false);
            guiComponent.setDisabledOverlayColor(ToggleableFrameHeaderComponent.J.var_java_awt_Color_r);
            Value value = guiComponent.getBoundValue();
            if (value == null || value.getParent() == null) continue;
            Color color = value.getOverrideColor() == null ? ToggleableFrameHeaderComponent.J.var_java_awt_Color_r.darker() : value.getOverrideColor();
            guiComponent.setDisabledOverlayColor(color);
        }
    }

    public boolean I$src$Z$f74e2a() {
        return this.var_boolean_o;
    }

    public IconButtonComponent L$src$Lgg_vape_ui_click_component_IconButtonComponent_$1i7gwfq() {
        return this.var_gg_vape_ui_click_component_IconButtonComponent_i;
    }

    public void R() {
        for (GuiComponent guiComponent : this.om) {
            guiComponent.setVisible(this.var_boolean_o);
        }
        this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().l$src$V$1mibm4x();
    }

    public ToggleableFrameHeaderComponent(Frame frame, String string, String string2) {
        this(frame, string, string2, 1.0);
    }

    @Override
    public void H() {
        super.H();
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.setOverrideColor(this.var_boolean_o ? ToggleableFrameHeaderComponent.J.var_java_awt_Color_f : null);
        double d = this.G$src$D$1b2f02a() + this.A() - 12.5;
        this.getClass();
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.K(d - 16.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.S(this.n());
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.Y(this.L());
    }

    public ToggleableFrameHeaderComponent(Frame frame, String string, String string2, double d) {
        super(frame, string, string2, d);
        this.om = new ArrayList<GuiComponent>();
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.addClickListener(new ToggleableFrameHeaderToggleClickHandler(this));
        this.addChildren(this.var_gg_vape_ui_click_component_IconButtonComponent_i);
    }

    static boolean i(ToggleableFrameHeaderComponent toggleableFrameHeaderComponent) {
        return toggleableFrameHeaderComponent.var_boolean_o;
    }

    static boolean a(ToggleableFrameHeaderComponent toggleableFrameHeaderComponent, boolean bl) {
        toggleableFrameHeaderComponent.var_boolean_o = bl;
        return toggleableFrameHeaderComponent.var_boolean_o;
    }

    public void L(boolean bl) {
        this.var_boolean_o = bl;
        this.R();
    }
}

