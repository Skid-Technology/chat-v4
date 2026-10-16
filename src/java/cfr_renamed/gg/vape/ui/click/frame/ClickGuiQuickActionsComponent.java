/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.friend.ui.OnlineCombatStatsSettingsFrame;
import gg.vape.friend.ui.OnlinePlayerPreviewSettingsFrame;
import gg.vape.friend.ui.OnlineRadarSettingsFrame;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.none.TextGuiSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.ClickGuiQuickActionActiveFrameIndicator;
import gg.vape.ui.click.frame.ClickGuiQuickActionsFrameButton;
import gg.vape.ui.click.frame.ClickGuiQuickActionsFrameToggleClickHandler;
import gg.vape.ui.click.frame.ClickGuiQuickActionsOnlineSettingsButton;
import gg.vape.ui.click.frame.ClickGuiQuickActionsVisibleModulesClickHandler;
import gg.vape.ui.click.frame.impl.VisibleModuleListFrame;
import gg.vape.ui.click.frame.impl.online.OnlineConnectionSettingsFrame;
import gg.vape.ui.click.frame.impl.target.TargetInfoSettingsFrame;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiQuickActionsComponent
extends GuiComponent {
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_O;
    private List<ClickGuiQuickActionActiveFrameIndicator> var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_Q;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_i = new IconButtonComponent("newfavorites", 0.8);

    @Override
    public double C() {
        return 18.0;
    }

    @Override
    public double x() {
        return 110.0;
    }

    private static void lambda$new$0() {
        OnlineConnectionSettingsFrame.getInstance().e(false);
    }

    public ClickGuiQuickActionsComponent() {
        this.var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v = new ArrayList<ClickGuiQuickActionActiveFrameIndicator>();
        this.setDisabledOverlayColor(ClickGuiQuickActionsComponent.J.var_java_awt_Color_i);
        this.var_gg_vape_ui_click_component_IconButtonComponent_O = new ClickGuiQuickActionsOnlineSettingsButton(this, "vape_online", 0.75);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q = new ClickGuiQuickActionsFrameButton(this, "newoverlays", 0.6);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.addClickListener(new ClickGuiQuickActionsFrameToggleClickHandler(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.w("Open overlays menu");
        this.var_gg_vape_ui_click_component_IconButtonComponent_O.addClickListener(ClickGuiQuickActionsComponent::lambda$new$0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.addClickListener(new ClickGuiQuickActionsVisibleModulesClickHandler(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.w("Favorites");
        this.addChildren(this.var_gg_vape_ui_click_component_IconButtonComponent_O, this.var_gg_vape_ui_click_component_IconButtonComponent_i, this.var_gg_vape_ui_click_component_IconButtonComponent_Q);
        this.var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v.add(new ClickGuiQuickActionActiveFrameIndicator("newtextgui", ClientSettings.getFrame(TextGuiSettingsFrame.class), 5));
        this.var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v.add(new ClickGuiQuickActionActiveFrameIndicator("newrearview", ClientSettings.getFrame(OnlinePlayerPreviewSettingsFrame.class), 6));
        this.var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v.add(new ClickGuiQuickActionActiveFrameIndicator("newduelinfo", ClientSettings.getFrame(OnlineCombatStatsSettingsFrame.class), 6));
        this.var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v.add(new ClickGuiQuickActionActiveFrameIndicator("newtargetinfo", ClientSettings.getFrame(TargetInfoSettingsFrame.class), 7));
        this.var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v.add(new ClickGuiQuickActionActiveFrameIndicator("newradar", ClientSettings.getFrame(OnlineRadarSettingsFrame.class), 7));
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void H() {
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.getDisabledOverlayColor());
        this.var_gg_vape_ui_click_component_IconButtonComponent_O.K(this.G$src$D$1b2f02a() + 3.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_O.S(this.n());
        this.var_gg_vape_ui_click_component_IconButtonComponent_O.Y(this.L());
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.K(this.G$src$D$1b2f02a() + this.A() - 32.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.S(this.n());
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.Y(this.L());
        this.var_gg_vape_ui_click_component_IconButtonComponent_i.setOverrideColor(ClientSettings.getFrame(VisibleModuleListFrame.class).V$src$Z$1xhop3l() ? (this.var_gg_vape_ui_click_component_IconButtonComponent_i.w$src$Z$e457mb() ? ClickGuiQuickActionsComponent.J.var_java_awt_Color_Y : ClickGuiQuickActionsComponent.J.var_java_awt_Color_I) : null);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.K(this.G$src$D$1b2f02a() + this.A() - 18.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.S(this.n());
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.Y(this.L());
        boolean bl = false;
        double d = this.n() + this.L() / 2.0;
        double d2 = (float)(this.G$src$D$1b2f02a() + this.A() - 17.5 - 8.0);
        for (ClickGuiQuickActionActiveFrameIndicator clickGuiQuickActionActiveFrameIndicator : this.var_java_util_List_gg_vape_ui_click_frame_ClickGuiQuickActionActiveFrameIndicator__v) {
            if (clickGuiQuickActionActiveFrameIndicator.var_gg_vape_ui_click_frame_Frame_F == null || !clickGuiQuickActionActiveFrameIndicator.var_gg_vape_ui_click_frame_Frame_F.V$src$Z$1xhop3l()) continue;
            GuiRenderPrimitives.F(clickGuiQuickActionActiveFrameIndicator.var_java_lang_String_y, d2, d, (double)clickGuiQuickActionActiveFrameIndicator.var_int_W, clickGuiQuickActionActiveFrameIndicator.var_int_W, ClickGuiQuickActionsComponent.J.var_java_awt_Color_W);
            bl = true;
            d2 -= (double)(5.0f + (float)clickGuiQuickActionActiveFrameIndicator.var_int_W);
        }
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.setOverrideColor(bl ? (this.var_gg_vape_ui_click_component_IconButtonComponent_Q.w$src$Z$e457mb() ? ColorUtil.offsetRgb(J.z(), 10.0) : J.z()) : null);
    }

    public IconButtonComponent b$src$Lgg_vape_ui_click_component_IconButtonComponent_$1sg98rj() {
        return this.var_gg_vape_ui_click_component_IconButtonComponent_Q;
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
    }

    @Override
    public void F() {
    }
}

