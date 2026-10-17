package gg.vape.tutorial;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.gui.UnderlinedTextLabel;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.frame.FrameComponent;
import java.awt.Color;

public class TutorialActionComponent extends FrameComponent {
   private final TextButton actionButton;
   private final WrappedTextComponent descriptionLabel;
   private final WrappedTextComponent titleLabel;
   private final UnderlinedTextLabel skipLabel;

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
   }

   @Override
   public double x() {
      return 120.0;
   }

   public void setTitleText(String title) {
      this.titleLabel.setText(title);
   }

   @Override
   public void v() {
   }

   public void setDescriptionText(String description) {
      this.descriptionLabel.setText(description);
   }

   public UnderlinedTextLabel getSkipLabel() {
      return this.skipLabel;
   }

   @Override
   public void F() {
      this.titleLabel.S(this.n() + 3.0);
      this.descriptionLabel.S(this.titleLabel.n() + this.titleLabel.C() + 2.0);
      this.skipLabel.Y(12.0);
      this.skipLabel.o(40.0);
      this.skipLabel.setFontScale(0.7);
      this.skipLabel.setTextColor(new Color(90, 90, 90, 255));
      this.skipLabel.K(this.G$src$D$1b2f02a() - 2.0);
      this.skipLabel.S(this.n() + this.L() - this.skipLabel.L() - 2.0);
      this.actionButton.Y(12.0);
      double textW = this.actionButton.getFontRenderer(0.9) != null ? this.actionButton.getFontRenderer(0.9).N(this.actionButton.getText()) : 16.0;
      double btnWidth = Math.max(32.0, textW + 8.0);
      this.actionButton.o(btnWidth);
      this.actionButton.K(this.G$src$D$1b2f02a() + this.A() - this.actionButton.A() - 6.0);
      this.actionButton.S(this.n() + this.L() - this.actionButton.L() - 2.0);
      if (!this.actionButton.getDisabledOverlayColor().equals(ClientSettings.INSTANCE.getAccentColor())) {
         this.actionButton.setBackgroundAnimationColors(ClientSettings.INSTANCE.getAccentColor(), ClientSettings.INSTANCE.getAccentColor().brighter());
      }
   }

   @Override
   public void H() {
      this.F();
   }

   @Override
   public void Y() {
   }

   public TextButton getActionButton() {
      return this.actionButton;
   }

   @Override
   public void V() {
   }

   @Override
   public double C() {
      double d = 26.0;
      if (this.descriptionLabel.getText().equals("")) {
         d = 16.0;
      }

      return this.titleLabel.C() + this.descriptionLabel.C() + d;
   }

   public TutorialActionComponent(String string, String string2) {
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
      this.titleLabel = new WrappedTextComponent(string, 1.0, new Color(209, 209, 209), true, 6.0);
      this.descriptionLabel = new WrappedTextComponent(string2, 0.9, new Color(140, 140, 140, 255), false, 6.0);
      this.skipLabel = new UnderlinedTextLabel("Skip tutorial");
      this.actionButton = new TextButton("Ok", Color.BLACK);
      this.skipLabel.o(20.0);
      this.skipLabel.Y(20.0);
      this.skipLabel.setShowDisabledOverlay(true);
      this.skipLabel.setDisabledOverlayColor(Color.RED);
      this.actionButton.o(20.0);
      this.actionButton.Y(20.0);
      this.actionButton.setShowDisabledOverlay(true);
      this.actionButton.setDisabledOverlayColor(Color.RED);
      this.o(120.0);
      this.titleLabel.setUseExplicitWidth(true);
      this.titleLabel.o(120.0);
      this.descriptionLabel.setUseExplicitWidth(true);
      this.descriptionLabel.o(120.0);
      this.descriptionLabel.Y(0.0);
      this.addChildren(this.titleLabel, this.descriptionLabel, this.skipLabel, this.actionButton);
      this.l$src$V$1mibm4x();
   }
}
