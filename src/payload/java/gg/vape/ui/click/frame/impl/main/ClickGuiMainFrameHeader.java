package gg.vape.ui.click.frame.impl.main;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GlyphIconComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.ClientSettingsComponentFactory;
import gg.vape.ui.click.frame.impl.ClientSettingsFrameSectionLabelComponent;
import gg.vape.ui.click.frame.impl.ThemeComponentGroupKey;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ClickGuiMainFrameHeader extends FrameHeaderComponent {
   private static final double HEADER_HEIGHT = 40.0;
   private static final double EDGE_PADDING = 6.0;
   private static final double ACTION_ICON_SPACING = 15.0;
   private final GlyphIconComponent syncIcon;
   private final ClickGuiMainFrameHeaderActionComponent refreshAction = new ClickGuiMainFrameHeaderActionComponent();
   private final GlyphIconComponent settingsIcon;
   private final List<ClickGuiSectionTabComponent> sectionTabs;
   private final List<GlyphIconComponent> actionIcons;
   private final GlyphIconComponent refreshIcon = new GlyphIconComponent("weapons", 6.0, 6.0, 10.0, 10.0, J.V, J.f, null);
   private final GlyphIconComponent overlaysIcon;

   private static void lambda$null$5(GuiComponent[] guiComponentArray, PanelComponent panelComponent) {
      if (guiComponentArray != null) {
         double d = Math.max(0.0, panelComponent.A());

         for (GuiComponent guiComponent : guiComponentArray) {
            guiComponent.o(d);
            guiComponent.setExplicitWidth(d);
            panelComponent.h(guiComponent);
         }
      }
   }

   private void lambda$new$1() {
      Vape.INSTANCE.getSyncThread().requestSave();
      this.syncIcon.setVisible(false);
   }

   private static void lambda$new$0() {
      ClientSettings.INSTANCE.switchFrameStack(ClientSettings.mainStack);
      Vape.INSTANCE.getPublicProfileSettings().guiStyle.setValue((ModeSelection)Vape.INSTANCE.getPublicProfileSettings().framesGuiStyle);
      Vape.INSTANCE.getPublicProfileSettings().guiStyle.setValue((ModeSelection)Vape.INSTANCE.getPublicProfileSettings().centralGuiStyle);
      Vape.INSTANCE.getNotificationManager().showInfo("Refreshed", "StandaloneGUI refreshed", 1000L);
   }

   public void addSectionTab(ClickGuiSectionTabComponent clickGuiSectionTabComponent) {
      this.sectionTabs.add(clickGuiSectionTabComponent);
      this.addChildren(clickGuiSectionTabComponent);
   }

   private static void lambda$null$6(ThemeComponentGroupKey themeComponentGroupKey, ClickGuiMainFrame clickGuiMainFrame, String string, String string2) {
      Map<ThemeComponentGroupKey, GuiComponent[]> map = ClientSettingsComponentFactory.d(J, Vape.INSTANCE.getClientSettings(), ClientSettings.INSTANCE, true);
      GuiComponent[] guiComponentArray = map.get(themeComponentGroupKey);
      clickGuiMainFrame.showOverlay(
         ClickGuiOverlaySpec.builder()
            .title(string)
            .initializeSidecar(arg_0 -> lambda$null$4(string2, arg_0))
            .initializeContent(arg_0 -> lambda$null$5(guiComponentArray, arg_0))
            .transitionMode(ClickGuiOverlayTransitionMode.PUSH)
            .build()
      );
   }

   private static void lambda$new$8(ClickGuiMainFrame clickGuiMainFrame) {
      clickGuiMainFrame.showOverlay(
         ClickGuiOverlaySpec.builder()
            .title("Settings")
            .initializeSidecar(ClickGuiMainFrameHeader::lambda$null$3)
            .initializeContent(arg_0 -> lambda$null$7(clickGuiMainFrame, arg_0))
            .build()
      );
   }

   @Override
   public void H() {
      if (!Vape.INSTANCE.getPublicProfileSettings().autoSave.getEffectiveValue()
         && Vape.INSTANCE.getSyncThread().hasPendingSave()
         && System.currentTimeMillis() > Vape.INSTANCE.getSyncThread().getLastSaveTime() + 60000L) {
         this.syncIcon.setVisible(true);
      } else {
         this.syncIcon.setVisible(false);
      }

      double d2 = this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() != null
         ? this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().G$src$D$1b2f02a()
         : this.G$src$D$1b2f02a();
      double d3 = this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() != null ? this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().n() : this.n();
      double d4 = this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() != null ? this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().A() : this.A();
      this.K(d2);
      this.S(d3);
      this.o(d4);
      double d5 = this.G$src$D$1b2f02a();
      double d6 = this.n();
      double d7 = this.A();
      double d8 = this.L();
      GuiRenderPrimitives.p(d5, d6, d7, d8, J.r, false, 2.0F, 1.0F, 0.0F, J.B, 3);
      GuiRenderPrimitives.C(d5, d6 + d8 - 1.0, d7, 0.5, J.s);
      this.refreshAction.K(d5 + 6.0);
      this.refreshAction.S(d6 + 6.0);
      this.refreshAction.y(6.5F);
      double d9 = d6 + 5.0;
      int n = 0;

      for (int i = this.actionIcons.size() - 1; i >= 0; i--) {
         GlyphIconComponent glyphIconComponent = this.actionIcons.get(i);
         if (glyphIconComponent.V$src$Z$1xhop3l()) {
            double d = d5 + d7 - 10.0 - 5.0 - n * 15.0;
            glyphIconComponent.K(d);
            glyphIconComponent.S(d9);
            n++;
         }
      }

      double d10 = d5 + 6.0;
      double d = d6 + d8 - 20.0 - 2.0;

      for (ClickGuiSectionTabComponent clickGuiSectionTabComponent : this.sectionTabs) {
         double d11 = clickGuiSectionTabComponent.getLabelHeight();
         clickGuiSectionTabComponent.o(d11);
         clickGuiSectionTabComponent.Y(20.0);
         clickGuiSectionTabComponent.K(d10);
         clickGuiSectionTabComponent.S(d);
         d10 += d11 + 12.0;
      }
   }

   private static void lambda$null$4(String string, ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
      clickGuiSidecarPanelBase.setLeadingIconKey(string != null ? string : "newsettings");
   }

   private static void lambda$null$3(ClickGuiSidecarPanelBase clickGuiSidecarPanelBase) {
      clickGuiSidecarPanelBase.setLeadingIconKey("newsettings");
   }

   private static void lambda$null$7(ClickGuiMainFrame clickGuiMainFrame, PanelComponent panelComponent) {
      Map<ThemeComponentGroupKey, GuiComponent[]> map = ClientSettingsComponentFactory.d(J, Vape.INSTANCE.getClientSettings(), ClientSettings.INSTANCE, true);

      for (Entry<ThemeComponentGroupKey, GuiComponent[]> object22 : map.entrySet()) {
         Object object = object22.getKey();
         ThemeComponentGroupKey groupKey = (ThemeComponentGroupKey)object;
         String string = groupKey.h();
         String string2 = groupKey.u();
         ClientSettingsFrameSectionLabelComponent clientSettingsFrameSectionLabelComponent = new ClientSettingsFrameSectionLabelComponent(string);
         double d = Math.max(0.0, panelComponent.A());
         clientSettingsFrameSectionLabelComponent.o(d);
         clientSettingsFrameSectionLabelComponent.setExplicitWidth(d);
         clientSettingsFrameSectionLabelComponent.Y(18.0);
         clientSettingsFrameSectionLabelComponent.setDisabledOverlayColor(J.m);
         clientSettingsFrameSectionLabelComponent.addClickListener(() -> lambda$null$6(groupKey, clickGuiMainFrame, string, string2));
         panelComponent.h(clientSettingsFrameSectionLabelComponent);
      }

      for (Object object : ClientSettingsComponentFactory.M(J, Vape.INSTANCE.getClientSettings(), ClientSettings.INSTANCE, true)) {
         ((GuiComponent)object).setExplicitWidth(panelComponent.A());
         ((GuiComponent)object).o(panelComponent.A());
         panelComponent.h((GuiComponent)object);
      }
   }

   public ClickGuiMainFrameHeader(ClickGuiMainFrame clickGuiMainFrame) {
      super(clickGuiMainFrame);
      this.syncIcon = new GlyphIconComponent("newsync", 6.0, 6.0, 10.0, 10.0, J.V, J.f, null);
      this.overlaysIcon = new GlyphIconComponent("newoverlays_2x", 6.0, 6.0, 10.0, 10.0, J.V, J.f, null);
      this.settingsIcon = new GlyphIconComponent("newsettings", 6.0, 6.0, 10.0, 10.0, J.V, J.f, null);
      this.sectionTabs = new ArrayList<>();
      this.actionIcons = new ArrayList<>();
      this.Y(40.0);
      this.setShowDisabledOverlay(false);
      this.refreshIcon.setCenterHorizontally(true);
      this.refreshIcon.setCenterVertically(true);
      this.refreshIcon.setClickListener(ClickGuiMainFrameHeader::lambda$new$0);
      this.syncIcon.setCenterHorizontally(true);
      this.syncIcon.setCenterVertically(true);
      this.syncIcon.w("Save your profiles to the cloud");
      this.syncIcon.setClickListener(this::lambda$new$1);
      this.overlaysIcon.setCenterHorizontally(true);
      this.overlaysIcon.setCenterVertically(true);
      this.overlaysIcon.setBackgroundAnimationColors(J.t, J.M);
      this.overlaysIcon.setCornerRadius(5.0F);
      this.overlaysIcon.setClickListener(ClickGuiMainFrameHeader::lambda$new$2);
      this.settingsIcon.setCenterHorizontally(true);
      this.settingsIcon.setCenterVertically(true);
      this.settingsIcon.setBackgroundAnimationColors(J.t, J.M);
      this.settingsIcon.setCornerRadius(5.0F);
      this.settingsIcon.setClickListener(() -> lambda$new$8(clickGuiMainFrame));
      this.actionIcons.addAll(Arrays.asList(this.syncIcon, this.overlaysIcon, this.settingsIcon));
      this.addChildren(this.refreshAction, this.syncIcon, this.overlaysIcon, this.settingsIcon);
   }

   private static void lambda$new$2() {
      ClientSettings.clickGuiFrameManager.showLayer(ClickGuiLayer.OVERLAYS);
   }
}
