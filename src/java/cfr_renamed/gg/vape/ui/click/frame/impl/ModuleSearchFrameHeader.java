/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.HalfScaleIconButtonComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.component.input.ModuleSearchInputComponent;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.ModuleSearchFrame;
import gg.vape.ui.click.frame.impl.ModuleSearchFrameHeaderRefreshListener;
import gg.vape.ui.click.frame.impl.hud.HudModuleListPanel;
import gg.vape.ui.click.frame.impl.hud.HudModuleOverviewListFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleSelectorFrame;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.value.ModuleNameSuggestionProvider;
import java.awt.Color;
import java.util.List;

public class ModuleSearchFrameHeader
extends FrameHeaderComponent {
    private final ModuleSearchFrame var_gg_vape_ui_click_frame_impl_ModuleSearchFrame_I;
    private final IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_v;
    private final SquareIconButtonComponent var_gg_vape_ui_click_component_SquareIconButtonComponent_R;
    private final IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_G = new IconButtonComponent("newsearch", 0.9);
    private final ModuleSearchInputComponent var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K;

    public void K$src$V$10w6uwu() {
        this.var_gg_vape_ui_click_frame_impl_ModuleSearchFrame_I.G(null);
        ClientSettings.getFrame(ModuleSearchFrame.class).D(this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.getText());
    }

    @Override
    public double C() {
        return 16.0;
    }

    public void l$src$V$11ec2hr() {
        List<GuiComponent> list = ClientSettings.getFrame(ModuleSearchFrame.class).f();
        for (GuiComponent guiComponent : list) {
            if (!(guiComponent instanceof ModuleComponent)) continue;
            ModuleComponent moduleComponent = (ModuleComponent)guiComponent;
            moduleComponent.collapseValueComponents();
        }
    }

    @Override
    public void I() {
    }

    private void lambda$new$1() {
        this.s();
    }

    @Override
    public void Y(double d) {
        super.Y(d);
    }

    private void lambda$new$0() {
        ClientSettings.activeComponent = this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K;
    }

    @Override
    public void u() {
        this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().l$src$V$1mibm4x();
    }

    public IconButtonComponent G$src$Lgg_vape_ui_click_component_IconButtonComponent_$1pnwa51() {
        return this.var_gg_vape_ui_click_component_IconButtonComponent_v;
    }

    public ModuleSearchFrameHeader(ModuleSearchFrame moduleSearchFrame) {
        super(moduleSearchFrame);
        this.var_gg_vape_ui_click_component_IconButtonComponent_v = new HalfScaleIconButtonComponent("legit_switch");
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R = new SquareIconButtonComponent("newclose");
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K = new ModuleSearchInputComponent(this);
        this.var_gg_vape_ui_click_frame_impl_ModuleSearchFrame_I = moduleSearchFrame;
        this.setPropagateMouseEvents(true);
        this.var_gg_vape_ui_click_component_IconButtonComponent_G.addClickListener(this::lambda$new$0);
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.addClickListener(this::lambda$new$1);
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.addRefreshListener(new ModuleSearchFrameHeaderRefreshListener(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.addClickListener(this::lambda$new$2);
        ModuleNameSuggestionProvider moduleNameSuggestionProvider = new ModuleNameSuggestionProvider(true);
        moduleNameSuggestionProvider.setComparator(null);
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.setSuggestionProvider(moduleNameSuggestionProvider);
        this.addChildren(this.var_gg_vape_ui_click_component_IconButtonComponent_G, this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R, this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K, this.var_gg_vape_ui_click_component_IconButtonComponent_v);
    }

    public void s() {
        this.l$src$V$11ec2hr();
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.setText("");
        this.K$src$V$10w6uwu();
    }

    @Override
    public void F() {
    }

    @Override
    public void H() {
        this.var_gg_vape_ui_click_frame_impl_ModuleSearchFrame_I.p();
        boolean bl = ((ModeSelection)ClientSettings.INSTANCE.searchBarStyle.getValue()).equals(ClientSettings.INSTANCE.floatingSearchBarMode);
        if (bl) {
            if (ClientSettings.INSTANCE.showLegitMode.getEffectiveValue().booleanValue()) {
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(true);
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.K(this.G$src$D$1b2f02a() + 3.0);
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.S(this.n() + 1.0);
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.o(18.0);
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.Y(this.L());
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.setUseExplicitHeight(true);
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.setShowDisabledOverlay(true);
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.setDisabledOverlayColor(Color.RED);
                if (this.var_gg_vape_ui_click_component_IconButtonComponent_v.w$src$Z$e457mb()) {
                    this.var_gg_vape_ui_click_component_IconButtonComponent_v.setOverrideColor(J.z().brighter());
                } else {
                    this.var_gg_vape_ui_click_component_IconButtonComponent_v.setOverrideColor(J.z());
                }
            } else {
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(false);
            }
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.setUseExplicitHeight(true);
            double d = this.G$src$D$1b2f02a() + 6.0;
            if (this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l()) {
                d = this.var_gg_vape_ui_click_component_IconButtonComponent_v.G$src$D$1b2f02a() + this.var_gg_vape_ui_click_component_IconButtonComponent_v.A() + 4.0;
                GuiRenderPrimitives.d(this.var_gg_vape_ui_click_component_IconButtonComponent_v.G$src$D$1b2f02a() + this.var_gg_vape_ui_click_component_IconButtonComponent_v.A() + 1.0, this.n() + 6.0, 6.0, 0.75f, new Color(100, 100, 100, 255));
            }
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.K(d);
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.S(this.n());
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.o(this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l() ? this.A() - this.var_gg_vape_ui_click_component_IconButtonComponent_v.A() - this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.A() - 4.0 : this.A() - this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.A() - 3.0);
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.Y(this.L());
            if (!this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.getText().isEmpty()) {
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(false);
                this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.setVisible(true);
                this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.K(this.G$src$D$1b2f02a() + this.A() - 13.0);
                this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.S(this.n() + 1.0);
                this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.o(10.0);
                this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.Y(this.L());
            } else {
                this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.setVisible(false);
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(true);
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.K(this.G$src$D$1b2f02a() + this.A() - 14.0);
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.S(this.n() + 0.5);
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.o(10.0);
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.Y(this.L());
            }
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.setVisible(true);
            this.o(110.0);
            this.setExplicitWidth(110.0);
            this.setUseExplicitWidth(true);
            return;
        }
        if (ClientSettings.INSTANCE.showLegitMode.getEffectiveValue().booleanValue()) {
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(true);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.K(this.G$src$D$1b2f02a() + 3.0);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.S(this.n() + 1.0);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.o(18.0);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.Y(this.L());
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setUseExplicitHeight(true);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setShowDisabledOverlay(true);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setDisabledOverlayColor(Color.RED);
            if (this.var_gg_vape_ui_click_component_IconButtonComponent_v.w$src$Z$e457mb()) {
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.setOverrideColor(J.z().brighter());
            } else {
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.setOverrideColor(J.z());
            }
        } else {
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(false);
        }
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.setUseExplicitHeight(true);
        double d = this.G$src$D$1b2f02a() + 6.0;
        if (this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l()) {
            // empty if block
        }
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.K(d);
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.S(this.n());
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.o(this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l() ? this.A() - this.var_gg_vape_ui_click_component_IconButtonComponent_v.A() - this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.A() - 4.0 : this.A() - this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.A() - 3.0);
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.Y(this.L());
        if (!this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.getText().isEmpty()) {
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(false);
            this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.setVisible(true);
            this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.K(this.G$src$D$1b2f02a() + this.A() - 13.0);
            this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.S(this.n() + 1.0);
            this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.o(10.0);
            this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.Y(this.L());
        } else {
            this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.setVisible(false);
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(true);
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.K(this.G$src$D$1b2f02a() + this.A() - 14.0);
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.S(this.n() + 0.5);
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.o(10.0);
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.Y(this.L());
        }
        boolean bl2 = this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.V$src$Z$1xhop3l();
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.setVisible(false);
        this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(false);
        if (bl2) {
            this.s();
        }
        this.o(22.0);
        this.setExplicitWidth(22.0);
        this.setUseExplicitWidth(true);
    }

    public ModuleSearchInputComponent A$src$Lgg_vape_ui_click_component_input_ModuleSearchIn$1efzz7n() {
        return this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    private void lambda$new$2() {
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_K.setText("");
        this.K$src$V$10w6uwu();
        if (HudModuleSelectorFrame.overviewVisible) {
            ClientSettings.getFrame(HudModuleOverviewListFrame.class).refreshFavorites();
        } else {
            ClientSettings.getFrame(HudModuleListPanel.class).refreshModules();
        }
        ClientSettings.INSTANCE.switchFrameStack(ClientSettings.hudEditorStack);
    }
}

