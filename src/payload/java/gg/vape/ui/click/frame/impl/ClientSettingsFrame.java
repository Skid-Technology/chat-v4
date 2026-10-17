package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.config.PublicProfileSettings;
import gg.vape.ui.click.component.ColorDividerComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.value.ClientSettingsColorValueEditorComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.PopupFrame;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ClientSettingsFrame extends Frame {
   private ClientSettings r1;
   private ClientSettingsColorValueEditorComponent rV;
   private HashMap<String, GuiComponent[]> r2;
   private gg.vape.module.none.ClientSettings rX = gg.vape.module.none.ClientSettings.INSTANCE;
   private PublicProfileSettings rh;

   @Override
   public void u() {
      ClientSettingsSearchFrame clientSettingsSearchFrame = gg.vape.module.none.ClientSettings.getFrame(ClientSettingsSearchFrame.class);
      ClientSettingsSectionFrame clientSettingsSectionFrame = gg.vape.module.none.ClientSettings.getFrame(ClientSettingsSectionFrame.class);
      if (!clientSettingsSectionFrame.V$src$Z$1xhop3l() && !clientSettingsSearchFrame.V$src$Z$1xhop3l()) {
         if (!this.V$src$Z$1xhop3l()) {
            this.t(true, false);
         }
      } else {
         if (this.V$src$Z$1xhop3l() && clientSettingsSectionFrame.V$src$Z$1xhop3l()) {
            this.t(false, false);
         }

         if (clientSettingsSectionFrame.V$src$Z$1xhop3l()) {
            this.M(clientSettingsSectionFrame.G$src$D$1b2f02a(), clientSettingsSectionFrame.n());
         } else if (clientSettingsSearchFrame.V$src$Z$1xhop3l()
            && this.G$src$D$1b2f02a() != clientSettingsSearchFrame.G$src$D$1b2f02a()
            && this.n() != clientSettingsSearchFrame.n()) {
            this.M(clientSettingsSearchFrame.G$src$D$1b2f02a(), clientSettingsSearchFrame.n());
         }
      }
   }

   private void v(Frame frame) {
      if (frame != null) {
         frame.t(!frame.V$src$Z$1xhop3l(), false);
         if (frame.V$src$Z$1xhop3l()) {
            frame.U();
         }

         frame.K(this.G$src$D$1b2f02a());
         frame.S(this.n());
         frame.l$src$V$1mibm4x();
      }
   }

   private void z(String string, GuiComponent... guiComponentArray) {
      this.r2.put(string, guiComponentArray);
      this.h(new ClientSettingsFrameSectionLabelComponent(string).addClickListener(new ClientSettingsFrameSectionOpenClickHandler(this, string)));
   }

   private static int lambda$sortGui$1(Frame frame, Frame frame2) {
      return frame.L() == frame2.L() ? 0 : Double.compare(frame2.L(), frame.L());
   }

   public static HashMap t(ClientSettingsFrame clientSettingsFrame) {
      return clientSettingsFrame.r2;
   }

   @Override
   public void z(boolean bl) {
      super.z(bl);
      this.Y(238.0);
      GuiRenderPrimitives.C(
         this.G$src$D$1b2f02a(),
         this.n() + this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() + 2.0,
         this.A(),
         this.L() - (this.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L() + 8.0),
         J.i
      );
      SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().E(0.7, false);
      String string = "Vape " + Vape.VERSION;
      double d = smoothFontRenderer.N(string);
      smoothFontRenderer.d(string, this.G$src$D$1b2f02a() + this.A() - d - 4.0, this.n() + this.L() - 4.5, J.h);
   }

   private void Q$src$V$16a7t4t() {
      Map<ThemeComponentGroupKey, GuiComponent[]> map = ClientSettingsComponentFactory.d(J, this.r1, this.rX, false);
      ThemeComponentGroupKey themeComponentGroupKey;
      if (map.containsKey(themeComponentGroupKey = new ThemeComponentGroupKey("GUI", "newgui"))) {
         GuiComponent[] guiComponentArray = map.get(themeComponentGroupKey);
         GuiComponent[] object = new GuiComponent[guiComponentArray.length + 2];
         System.arraycopy(guiComponentArray, 0, object, 0, guiComponentArray.length);
         object[guiComponentArray.length] = new ClientSettingsActionButtonRowComponent(
               "Reset GUI positions", new ClientSettingsFrameResetGuiLayoutClickHandler(this)
            )
            .w("This will reset your GUI back to the default");
         object[guiComponentArray.length + 1] = new ClientSettingsActionButtonRowComponent("Sort GUI", new ClientSettingsFrameSortGuiClickHandler(this))
            .w("Sorts GUI by size");
         map.put(themeComponentGroupKey, object);
      }

      for (Entry entry : map.entrySet()) {
         String string = ((ThemeComponentGroupKey)entry.getKey()).h();
         GuiComponent[] guiComponentArray = (GuiComponent[])entry.getValue();
         this.r2.put(string, guiComponentArray);
         this.h(new ClientSettingsFrameSectionLabelComponent(string).addClickListener(new ClientSettingsFrameActionClickHandler(this, string)));
      }
   }

   public void d$src$V$16knweo() {
      this.rV.resetPaletteSlider();
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   public static ClientSettingsSectionFrame m(ClientSettingsFrame clientSettingsFrame, String string) {
      return clientSettingsFrame.A(string);
   }

   public static void a(ClientSettingsFrame clientSettingsFrame) {
      clientSettingsFrame.Z$src$V$16f5yh2();
   }

   @Override
   public void Y() {
   }

   private static boolean lambda$sortGui$0(Frame frame) {
      return !frame.V$src$Z$1xhop3l()
         || frame.n$src$Z$1fa61uz()
         || !frame.J$src$Z$1eqdghz()
         || frame instanceof HudModuleConfigFrameBase
         || frame instanceof PopupFrame;
   }

   public static void n(ClientSettingsFrame clientSettingsFrame) {
      clientSettingsFrame.k$src$V$16oigk7();
   }

   @Override
   public void v() {
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   private void k$src$V$16oigk7() {
      int n = 10;

      for (Frame frame : gg.vape.module.none.ClientSettings.getAllFrames()) {
         if (frame instanceof ClientSettingsSearchFrame) {
            frame.M(10.0, 20.0);
         } else if (frame.J$src$Z$1eqdghz() && !frame.n$src$Z$1fa61uz()) {
            double var10001 = gg.vape.module.none.ClientSettings.getFrame(ClientSettingsSearchFrame.class).A() + 10.0;
            n += 22;
            frame.M(var10001, n);
         }
      }

      for (Frame framex : gg.vape.module.none.ClientSettings.getAllFrames()) {
         if (!framex.n$src$Z$1fa61uz() && framex.J$src$Z$1eqdghz()) {
            framex.setVisible(framex instanceof ClientSettingsSearchFrame);
         }
      }

      gg.vape.module.none.ClientSettings.clearPositionedFrames();
   }

   private ClientSettingsSectionFrame A(String string) {
      ClientSettingsSectionFrame clientSettingsSectionFrame = gg.vape.module.none.ClientSettings.getFrame(ClientSettingsSectionFrame.class).z(string);
      clientSettingsSectionFrame.s$src$V$yca8r0();
      this.v(clientSettingsSectionFrame);
      clientSettingsSectionFrame.removeMarkedChildren();
      return clientSettingsSectionFrame;
   }

   private void Z$src$V$16f5yh2() {
      ArrayList<Frame> arrayList = new ArrayList<>(gg.vape.module.none.ClientSettings.INSTANCE.getActiveStack().Y());
      arrayList.removeIf(ClientSettingsFrame::lambda$sortGui$0);

      try {
         arrayList.sort(ClientSettingsFrame::lambda$sortGui$1);
         Frame frame = null;

         for (Frame frame2 : arrayList) {
            if (frame2 instanceof ClientSettingsSectionFrame) {
               frame = frame2;
               break;
            }
         }

         if (frame != null) {
            arrayList.remove(frame);
            arrayList.add(0, frame);
         }
      } catch (Exception var14) {
         Vape.logThrowable(var14);
      }

      double d = 32.0;
      double d2 = 32.0;
      double d3 = 0.0;

      for (Frame frame : arrayList) {
         if (d + frame.A() > Minecraft.G().getScaledWidth()) {
            d = 30.0;
            d2 += d3 + 4.0;
            d3 = 0.0;
         }

         double d4 = frame.L() > frame.d$src$D$ibccpu() ? frame.d$src$D$ibccpu() : frame.L();
         if (d4 > d3) {
            d3 = d4;
         }

         frame.M(d, d2);
         d += frame.A() + 2.0;
      }
   }

   @Override
   public String getName() {
      return "Settings";
   }

   public ClientSettingsFrame() {
      this.r1 = Vape.INSTANCE.getClientSettings();
      this.rh = Vape.INSTANCE.getPublicProfileSettings();
      this.r2 = new HashMap<>();
      this.setDisabledOverlayColor(J.r);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      ClientSettingsSearchFrame clientSettingsSearchFrame = gg.vape.module.none.ClientSettings.getFrame(ClientSettingsSearchFrame.class);
      this.Y(clientSettingsSearchFrame.L());
      this.o(clientSettingsSearchFrame.A());
      SettingsFrameHeaderComponent settingsFrameHeaderComponent = new SettingsFrameHeaderComponent(this, "Settings");
      settingsFrameHeaderComponent.n(new ClientSettingsFrameOpenSearchAndCloseSettingsClickHandler(this));
      settingsFrameHeaderComponent.f(new ClientSettingsFrameOpenSearchClickHandler(this));
      this.Y(settingsFrameHeaderComponent);
      this.h(new SpacerComponent(1.0, 2.0));
      this.h(new ColorDividerComponent(J.l));
      this.Q$src$V$16a7t4t();

      for (GuiComponent guiComponent : ClientSettingsComponentFactory.M(J, this.r1, this.rX, false)) {
         if (guiComponent instanceof ClientSettingsColorValueEditorComponent) {
            this.rV = (ClientSettingsColorValueEditorComponent)guiComponent;
         }

         this.h(guiComponent);
      }

      this.setVisible(false);
      this.L(false, false);
      this.setVisible(false);
      this.L(false, false);
   }
}
