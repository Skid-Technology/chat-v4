/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.input.ClientSettingsSearchInputComponent;
import gg.vape.ui.click.component.input.ModuleSearchInputComponent;
import gg.vape.ui.click.frame.CloseableFrameHeaderComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrame;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeaderInputChangeListener;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeaderManualSyncClickHandler;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeaderSearchFocusClickHandler;
import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeaderSettingsToggleClickHandler;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.value.ModuleNameSuggestionProvider;
import java.awt.Color;

public class ClientSettingsSearchFrameHeader
extends FrameHeaderComponent {
    private final IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_G;
    private int var_int_Q;
    private final IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_v;
    private final ModuleSearchInputComponent var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o;
    private boolean var_boolean_i;
    private final IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_R = new IconButtonComponent("newsettings", 0.9);

    public ClientSettingsSearchFrameHeader(ClientSettingsSearchFrame clientSettingsSearchFrame) {
        super(clientSettingsSearchFrame);
        this.var_gg_vape_ui_click_component_IconButtonComponent_G = new IconButtonComponent("newsync", 0.9);
        this.var_gg_vape_ui_click_component_IconButtonComponent_v = new IconButtonComponent("newsearch", 0.9);
        if (CloseableFrameHeaderComponent.A$src$AI$cq4488() != null) {
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o = new ClientSettingsSearchInputComponent(this, this);
            this.var_boolean_i = false;
            this.var_int_Q = 0;
            ModuleNameSuggestionProvider moduleNameSuggestionProvider = new ModuleNameSuggestionProvider(true);
            moduleNameSuggestionProvider.setComparator(null);
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.setSuggestionProvider(moduleNameSuggestionProvider);
            this.var_gg_vape_ui_click_component_IconButtonComponent_R.addClickListener(new ClientSettingsSearchFrameHeaderSettingsToggleClickHandler(this));
            this.var_gg_vape_ui_click_component_IconButtonComponent_R.w("Open settings");
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.addClickListener(new ClientSettingsSearchFrameHeaderSearchFocusClickHandler(this));
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.w("Search for modules");
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.addClickListener(new ClientSettingsSearchFrameHeaderManualSyncClickHandler(this));
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.w("Save your profiles to the cloud");
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.addKeyTypedListener(new ClientSettingsSearchFrameHeaderInputChangeListener(this, clientSettingsSearchFrame));
            this.addChildren(this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o, this.var_gg_vape_ui_click_component_IconButtonComponent_v, this.var_gg_vape_ui_click_component_IconButtonComponent_G, this.var_gg_vape_ui_click_component_IconButtonComponent_R);
            GuiComponent.setLegacyComponentState(new GuiComponent[1]);
            return;
        }
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o = new ClientSettingsSearchInputComponent(this, this);
        this.var_boolean_i = false;
        this.var_int_Q = 0;
        ModuleNameSuggestionProvider moduleNameSuggestionProvider = new ModuleNameSuggestionProvider(true);
        moduleNameSuggestionProvider.setComparator(null);
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.setSuggestionProvider(moduleNameSuggestionProvider);
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.addClickListener(new ClientSettingsSearchFrameHeaderSettingsToggleClickHandler(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.w("Open settings");
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.addClickListener(new ClientSettingsSearchFrameHeaderSearchFocusClickHandler(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.w("Search for modules");
        this.var_gg_vape_ui_click_component_IconButtonComponent_G.addClickListener(new ClientSettingsSearchFrameHeaderManualSyncClickHandler(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_G.w("Save your profiles to the cloud");
        this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.addKeyTypedListener(new ClientSettingsSearchFrameHeaderInputChangeListener(this, clientSettingsSearchFrame));
        this.addChildren(this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o, this.var_gg_vape_ui_click_component_IconButtonComponent_v, this.var_gg_vape_ui_click_component_IconButtonComponent_G, this.var_gg_vape_ui_click_component_IconButtonComponent_R);
    }

    static IconButtonComponent F(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        return clientSettingsSearchFrameHeader.var_gg_vape_ui_click_component_IconButtonComponent_G;
    }

    public ModuleSearchInputComponent getSearchInput() {
        return this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o;
    }

    public void a(boolean bl) {
        this.var_boolean_i = bl;
    }

    public void focusSearchInput() {
        this.var_boolean_i = true;
        this.getSearchInput().requestFocus();
    }

    @Override
    public void H() {
        double d = this.G$src$D$1b2f02a() + this.A() - 7.5 - 8.0;
        int[] nArray = CloseableFrameHeaderComponent.A$src$AI$cq4488();
        if (this.var_boolean_i) {
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.setVisible(true);
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.K(this.G$src$D$1b2f02a());
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.S(this.n() - 2.5);
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.o(this.A() - 17.5 - 16.0);
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.Y(this.L());
            this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(false);
        } else {
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.setVisible(false);
            this.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o.o(0.0);
            float f = 6.5f;
            float f2 = (float)ImageRenderer.getImageWidth("vapelogo") / f;
            float f3 = (float)ImageRenderer.getImageHeight("vapelogo") / f;
            float f4 = (float)ImageRenderer.getImageWidth("v4") / f;
            float f5 = (float)ImageRenderer.getImageHeight("v4") / f;
            ImageRenderer.drawImage(Color.WHITE, (float)this.G$src$D$1b2f02a() + 6.0f, (float)this.n() + 5.0f, "vapelogo", f2, f3, false);
            ImageRenderer.drawImage(J.z(), (float)this.G$src$D$1b2f02a() + 6.0f + 27.0f, (float)this.n() + 5.0f, "v4", f4, f5, false);
            if (!Vape.INSTANCE.getPublicProfileSettings().autoSave.getEffectiveValue().booleanValue() && Vape.INSTANCE.getSyncThread().hasPendingSave() && System.currentTimeMillis() > Vape.INSTANCE.getSyncThread().getLastSaveTime() + 60000L) {
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(true);
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.K(d);
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.S(this.n());
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.Y(this.L());
                d -= 13.0;
            } else {
                this.var_gg_vape_ui_click_component_IconButtonComponent_G.setVisible(false);
            }
        }
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.setOverrideColor(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().y$src$Z$1f55jvh() ? ClientSettingsSearchFrameHeader.J.var_java_awt_Color_f : null);
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.K(d);
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.S(this.n());
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.Y(this.L());
        d -= 13.0;
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(((ModeSelection)ClientSettings.INSTANCE.searchBarStyle.getValue()).equals(ClientSettings.INSTANCE.integratedSearchBarMode));
        if (this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l()) {
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setOverrideColor(this.var_boolean_i ? ClientSettingsSearchFrameHeader.J.var_java_awt_Color_f : null);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.K(d);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.S(this.n());
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.Y(this.L());
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            CloseableFrameHeaderComponent.o(new int[5]);
        }
    }

    public static int b(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        return clientSettingsSearchFrameHeader.var_int_Q;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static int L(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader, int n) {
        clientSettingsSearchFrameHeader.var_int_Q = n;
        return clientSettingsSearchFrameHeader.var_int_Q;
    }

    public static ModuleSearchInputComponent j(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
        return clientSettingsSearchFrameHeader.var_gg_vape_ui_click_component_input_ModuleSearchInputComponent_o;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }
}

