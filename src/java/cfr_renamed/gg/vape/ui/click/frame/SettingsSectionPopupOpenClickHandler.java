/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.ColorDividerComponent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.GuiComponentContract;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.frame.CenteredPopupFrame;
import gg.vape.ui.click.frame.SettingsSectionComponent;
import gg.vape.ui.click.frame.SettingsSubpageFrame;

public class SettingsSectionPopupOpenClickHandler
implements GuiClickListener {
    final SettingsSectionComponent var_gg_vape_ui_click_frame_SettingsSectionComponent_a;
    final SettingsSubpageFrame var_gg_vape_ui_click_frame_SettingsSubpageFrame_P;
    private final static String var_java_lang_String_c = "wrap";

    @Override
    public void onPrimaryClick() {
        double d = this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P.A();
        PanelComponent panelComponent = new PanelComponent(d, this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P.L() - this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().L());
        SettingsSubpageFrame.j(this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P, ClientSettings.createPopup(this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P, panelComponent, CenteredPopupFrame.class));
        SettingsSubpageFrame.q(this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P).s(false);
        SettingsSubpageFrame.q(this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P).n(18.0);
        SettingsSubpageFrame.q(this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P).o(d);
        panelComponent.t(this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P.L() - this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P.i$src$Lgg_vape_ui_click_frame_FrameToolbarComponent_$gnpgc6().L());
        panelComponent.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(var_java_lang_String_c);
        panelComponent.h(new ColorDividerComponent(GuiComponentContract.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_l, 0.5, d), new Object[0]);
        panelComponent.h(new SpacerComponent(0.0, 0.5), new Object[0]);
        for (GuiComponent guiComponent : this.var_gg_vape_ui_click_frame_SettingsSectionComponent_a.n$src$ALgg_vape_ui_click_component_GuiComponent_$ay9kg5()) {
            panelComponent.addChildren(guiComponent);
            guiComponent.setDisabledOverlayColor(GuiComponentContract.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_r);
            if (guiComponent.getBoundValue() != null && guiComponent.getBoundValue().getParent() != null) {
                guiComponent.setDisabledOverlayColor(GuiComponentContract.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_i);
            }
            this.O(guiComponent, d);
        }
        panelComponent.setDisabledOverlayColor(GuiComponentContract.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_r);
    }

    public SettingsSectionPopupOpenClickHandler(SettingsSubpageFrame settingsSubpageFrame, SettingsSectionComponent settingsSectionComponent) {
        this.var_gg_vape_ui_click_frame_SettingsSubpageFrame_P = settingsSubpageFrame;
        this.var_gg_vape_ui_click_frame_SettingsSectionComponent_a = settingsSectionComponent;
    }

    private void O(GuiComponent guiComponent, double d) {
        guiComponent.setUseExplicitWidth(true);
        guiComponent.o(d);
        guiComponent.setExplicitWidth(d);
        for (GuiComponent guiComponent2 : guiComponent.f()) {
            this.O(guiComponent2, d);
        }
    }
}

