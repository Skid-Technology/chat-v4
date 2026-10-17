package gg.vape.tutorial;

import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.ui.click.component.gui.UnderlinedTextLabel;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class TutorialWelcomePanel extends TutorialOverlayPanelBase {
   private static int obfuscationState;

   public static void setObfuscationState(int state) {
      obfuscationState = state;
   }

   public static int getObfuscationState() {
      return obfuscationState;
   }

   @Override
   public void H() {
      this.setDisabledOverlayColor(new Color(26, 26, 26, 255));
      GuiRenderPrimitives.P(this.G$src$D$1b2f02a() - 1.0, this.n() - 1.0, this.A() + 2.0, this.L() + 2.0 + 2.0, new Color(38, 38, 38, 255), 1.0F, 2.0F, 1.0F);
      super.H();
   }

   @Override
   public void c() {
      super.c();
   }

   @Override
   public double C() {
      return 140.0;
   }

   public TutorialWelcomePanel() {
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().t(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().I(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().U(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().u(false);
      this.h(new IconGlyphComponent("mask_l_1", 32.0F, 38.0F), "topLeft");
      this.h(new IconGlyphComponent("mask_r_1", 32.0F, 46.0F), "bottomRight");
      this.h(new WrappingTextLabelComponent("Welcome", 1.25, J.Z), "spanwidth, offsetY 20, offsetX 10");
      this.h(
         new WrappingTextLabelComponent(
            "Thank you for choosing Vape V4!\n \nIf you are new to Vape, or clients in general, please proceed with the tutorial", 0.8, J.Z
         ),
         "spanwidth, offsetY 30, offsetX 20"
      );
      UnderlinedTextLabel underlinedTextLabel = new UnderlinedTextLabel("Skip tutorial", 0.85, J.h);
      this.h(underlinedTextLabel, "offsetX 30, offsetY 80");
      TutorialWelcomeStartButton tutorialWelcomeStartButton = new TutorialWelcomeStartButton(this, "Tutorial", J.U, J.U.brighter());
      tutorialWelcomeStartButton.o(44.0);
      tutorialWelcomeStartButton.Y(14.0);
      this.h(tutorialWelcomeStartButton, "offsetX 70, offsetY 77");
      underlinedTextLabel.setClickListener(new TutorialWelcomeSkipAllClickHandler(this));
      tutorialWelcomeStartButton.setClickListener(new TutorialWelcomeStartClickHandler(this));
   }

   @Override
   public double x() {
      return 150.0;
   }

   public static int P$src$I$x6acxy() {
      int n = getObfuscationState();
      return 0;
   }

   static {
      setObfuscationState(88);
   }
}
