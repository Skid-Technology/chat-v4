/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.module.ModuleComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrame;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeaderActionClickHandler;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeaderClearModuleSelectionClickHandler;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeaderCollapseToggleClickHandler;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeaderNextModuleClickHandler;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeaderSearchIconClickHandler;
import gg.vape.ui.click.frame.impl.ModuleCategoryFrameHeaderSearchLabelClickHandler;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.RenderUtils;

public class ModuleCategoryFrameHeader
extends FrameHeaderComponent {
    private TextLabel var_gg_vape_ui_click_component_gui_TextLabel_K = new TextLabel("", 0.75);
    private int ZG = 0;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_Q;
    private TextLabel var_gg_vape_ui_click_component_gui_TextLabel_G;
    private String var_java_lang_String_R;
    private String var_java_lang_String_O;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_v;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_o;
    private TextLabel var_gg_vape_ui_click_component_gui_TextLabel_I = new TextLabel("", 0.75);
    private ModuleCategoryFrame var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i;

    @Override
    public void I() {
    }

    @Override
    public void F() {
    }

    public void y(int n) {
        this.ZG = n;
    }

    public static ModuleCategoryFrame e(ModuleCategoryFrameHeader moduleCategoryFrameHeader) {
        return moduleCategoryFrameHeader.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i;
    }

    public void h() {
        if (ClientSettings.moduleSearchActive) {
            ClientSettings.moduleSearchActive = false;
            ClientSettings.refreshModuleCategoryHeaders();
        }
        if (this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.N$src$Lgg_vape_module_Mod_$1rbaf6a() != null) {
            this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.G(null);
        }
        ClientSettings.refreshCategoryHeader(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.G$src$Lgg_vape_module_Category_$qyt4o7());
        for (GuiComponent guiComponent : this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().f()) {
            if (guiComponent instanceof FrameHeaderComponent) continue;
            if (!(guiComponent instanceof ModuleComponent)) {
                guiComponent.setVisible(false);
                continue;
            }
            guiComponent.setVisible(!this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.q() && ((ModuleComponent)guiComponent).getModule().isVisible());
        }
        this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().l$src$V$1mibm4x();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public ModuleCategoryFrameHeader(ModuleCategoryFrame moduleCategoryFrame, String string, String string2) {
        super(moduleCategoryFrame);
        this.var_gg_vape_ui_click_component_gui_TextLabel_G = new TextLabel("Edit");
        this.var_gg_vape_ui_click_component_IconButtonComponent_o = new IconButtonComponent("newhide", 0.7);
        this.var_gg_vape_ui_click_component_IconButtonComponent_v = new IconButtonComponent("moduleback");
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q = new IconButtonComponent("upcollapse", 0.3);
        this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i = moduleCategoryFrame;
        this.var_java_lang_String_O = string;
        this.var_java_lang_String_R = string2;
        this.var_gg_vape_ui_click_component_IconButtonComponent_o.addClickListener(new ModuleCategoryFrameHeaderSearchIconClickHandler(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_o.w("Edit hidden modules");
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.addClickListener(new ModuleCategoryFrameHeaderClearModuleSelectionClickHandler(this));
        this.var_gg_vape_ui_click_component_gui_TextLabel_G.addClickListener(new ModuleCategoryFrameHeaderSearchLabelClickHandler(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.addClickListener(new ModuleCategoryFrameHeaderCollapseToggleClickHandler(this, moduleCategoryFrame));
        this.addChildren(this.var_gg_vape_ui_click_component_gui_TextLabel_G, this.var_gg_vape_ui_click_component_IconButtonComponent_v, this.var_gg_vape_ui_click_component_IconButtonComponent_o, this.var_gg_vape_ui_click_component_IconButtonComponent_Q, this.var_gg_vape_ui_click_component_gui_TextLabel_I, this.var_gg_vape_ui_click_component_gui_TextLabel_K);
    }

    @Override
    public void u() {
        if (this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.N$src$Lgg_vape_module_Mod_$1rbaf6a() != null && ClientSettings.moduleSearchActive) {
            this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.G(null);
        }
    }

    public void s(Mod mod) {
        if (mod == null) {
            this.var_gg_vape_ui_click_component_gui_TextLabel_K.setLabelText("");
            return;
        }
        this.var_gg_vape_ui_click_component_gui_TextLabel_K.setLabelText("< " + mod.getName().toUpperCase().substring(0, 3));
        this.var_gg_vape_ui_click_component_gui_TextLabel_K.setClickListener(new ModuleCategoryFrameHeaderActionClickHandler(this, mod));
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        double d = smoothFontRenderer.d(this.var_java_lang_String_R);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0 + 1.0;
        float f = (float)ImageRenderer.getImageWidth(this.var_java_lang_String_O) / 3.5f;
        float f2 = (float)ImageRenderer.getImageHeight(this.var_java_lang_String_O) / 3.5f;
        double d3 = (float)this.G$src$D$1b2f02a() + 6.0f;
        double d4 = this.n() + this.L() / 2.0 - (double)(f2 / 2.0f) + 1.0;
        smoothFontRenderer.d(this.var_java_lang_String_R, d3 + (double)f + 4.0, d2, ModuleCategoryFrameHeader.J.var_java_awt_Color_A);
        if (this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.N$src$Lgg_vape_module_Mod_$1rbaf6a() != null && !ClientSettings.moduleSearchActive) {
            this.var_gg_vape_ui_click_component_IconButtonComponent_o.setVisible(false);
            this.var_gg_vape_ui_click_component_gui_TextLabel_G.setVisible(false);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setOverrideColor(ModuleCategoryFrameHeader.J.var_java_awt_Color_A);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(true);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.K(this.G$src$D$1b2f02a() + 5.0);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.S(this.n());
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.Y(this.L());
            this.var_gg_vape_ui_click_component_IconButtonComponent_Q.setVisible(false);
            this.var_gg_vape_ui_click_component_gui_TextLabel_K.setVisible(true);
            this.var_gg_vape_ui_click_component_gui_TextLabel_I.setVisible(true);
            this.var_gg_vape_ui_click_component_gui_TextLabel_I.K(this.G$src$D$1b2f02a() + this.A() - 5.0 - this.var_gg_vape_ui_click_component_gui_TextLabel_I.A());
            this.var_gg_vape_ui_click_component_gui_TextLabel_I.S(this.n());
            this.var_gg_vape_ui_click_component_gui_TextLabel_I.Y(this.L());
            this.var_gg_vape_ui_click_component_gui_TextLabel_K.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - this.var_gg_vape_ui_click_component_gui_TextLabel_I.A() - this.var_gg_vape_ui_click_component_gui_TextLabel_K.A());
            this.var_gg_vape_ui_click_component_gui_TextLabel_K.S(this.n());
            this.var_gg_vape_ui_click_component_gui_TextLabel_K.Y(this.L());
        } else {
            this.var_gg_vape_ui_click_component_gui_TextLabel_K.setVisible(false);
            this.var_gg_vape_ui_click_component_gui_TextLabel_I.setVisible(false);
            this.var_gg_vape_ui_click_component_IconButtonComponent_Q.setVisible(true);
            ImageRenderer.drawImage(ModuleCategoryFrameHeader.J.var_java_awt_Color_A, (float)d3, (float)d4, this.var_java_lang_String_O, f, f2, false);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(false);
            if (ClientSettings.moduleSearchActive) {
                this.var_gg_vape_ui_click_component_IconButtonComponent_o.setVisible(false);
                this.var_gg_vape_ui_click_component_gui_TextLabel_G.setFontScale(0.75);
                this.var_gg_vape_ui_click_component_gui_TextLabel_G.setVisible(true);
                this.var_gg_vape_ui_click_component_gui_TextLabel_G.setLabelText("Done");
                this.var_gg_vape_ui_click_component_gui_TextLabel_G.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - 16.0 - smoothFontRenderer.N(this.var_gg_vape_ui_click_component_gui_TextLabel_G.getText()) / 2.0);
                this.var_gg_vape_ui_click_component_gui_TextLabel_G.S(this.n());
                this.var_gg_vape_ui_click_component_gui_TextLabel_G.Y(this.L());
            } else {
                boolean bl = this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().getBounds().Z(RenderUtils.h());
                if (((ModuleCategoryFrameHeader)this.getParentFrameComponent().j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc()).g$src$I$pd0er5() > 0) {
                    double d5 = smoothFontRenderer.N("" + this.ZG);
                    if (bl) {
                        smoothFontRenderer.d("" + this.ZG, this.G$src$D$1b2f02a() + this.A() - 5.0 - 16.0 - 3.0 - d5, d2, ModuleCategoryFrameHeader.J.var_java_awt_Color_Z);
                    }
                    this.var_gg_vape_ui_click_component_IconButtonComponent_o.setIconResource("newhide");
                } else {
                    this.var_gg_vape_ui_click_component_IconButtonComponent_o.setIconResource("newedit");
                }
                this.var_gg_vape_ui_click_component_IconButtonComponent_o.setVisible(bl);
                this.var_gg_vape_ui_click_component_gui_TextLabel_G.setVisible(false);
                this.var_gg_vape_ui_click_component_IconButtonComponent_o.setOverrideColor(ClientSettings.moduleSearchActive ? ModuleCategoryFrameHeader.J.var_java_awt_Color_f : null);
                this.var_gg_vape_ui_click_component_IconButtonComponent_o.K(this.G$src$D$1b2f02a() + this.A() - 10.0 - 16.0);
                this.var_gg_vape_ui_click_component_IconButtonComponent_o.S(this.n() + 1.0);
                this.var_gg_vape_ui_click_component_IconButtonComponent_o.Y(this.L());
            }
            this.var_gg_vape_ui_click_component_IconButtonComponent_Q.K(this.G$src$D$1b2f02a() + this.A() - 7.5 - 8.0);
            this.var_gg_vape_ui_click_component_IconButtonComponent_Q.S(this.n());
            this.var_gg_vape_ui_click_component_IconButtonComponent_Q.Y(this.L());
            this.var_gg_vape_ui_click_component_IconButtonComponent_Q.setIconResource(this.var_gg_vape_ui_click_frame_impl_ModuleCategoryFrame_i.q() ? "downexpand" : "upcollapse");
        }
    }

    public void S(Mod mod) {
        if (mod == null) {
            this.var_gg_vape_ui_click_component_gui_TextLabel_I.setLabelText("");
            return;
        }
        this.var_gg_vape_ui_click_component_gui_TextLabel_I.setLabelText(mod.getName().toUpperCase().substring(0, 3) + " >");
        this.var_gg_vape_ui_click_component_gui_TextLabel_I.setClickListener(new ModuleCategoryFrameHeaderNextModuleClickHandler(this, mod));
    }

    public int g$src$I$pd0er5() {
        return this.ZG;
    }
}

