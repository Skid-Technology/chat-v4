package gg.vape.tutorial;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.gui.UnderlinedTextLabel;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
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
      return 145.0;
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
   public void H() {
      this.setDisabledOverlayColor(new Color(24, 24, 24, 255));
      GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), new Color(38, 38, 38, 255), 1.0F, 2.0F, 1.0F);
      super.H();
   }

   @Override
   public void Y() {
      if (this.getParentFrameComponent() != null) {
         this.K(this.getParentFrameComponent().G$src$D$1b2f02a());
         this.S(this.getParentFrameComponent().n());
         this.o(this.getParentFrameComponent().A());
         this.Y(this.getParentFrameComponent().L());
      }

      this.titleLabel.K(this.G$src$D$1b2f02a() + 8.0);
      this.titleLabel.S(this.n() + 6.0);
      this.titleLabel.setWrapWidth(this.A() - 16.0);
      this.titleLabel.o(this.A() - 16.0);
      this.descriptionLabel.K(this.G$src$D$1b2f02a() + 8.0);
      this.descriptionLabel.S(this.titleLabel.n() + Math.max(14.0, this.titleLabel.L()) + 4.0);
      this.descriptionLabel.setWrapWidth(this.A() - 16.0);
      this.descriptionLabel.o(this.A() - 16.0);
      this.skipLabel.Y(12.0);
      this.skipLabel.o(46.0);
      this.skipLabel.setFontScale(0.75);
      this.skipLabel.setTextColor(new Color(120, 120, 120, 255));
      this.skipLabel.K(this.G$src$D$1b2f02a() + 8.0);
      this.skipLabel.S(this.n() + this.L() - this.skipLabel.L() - 6.0);
      this.actionButton.Y(14.0);
      this.actionButton.o(36.0);
      this.actionButton.K(this.G$src$D$1b2f02a() + this.A() - this.actionButton.A() - 8.0);
      this.actionButton.S(this.n() + this.L() - this.actionButton.L() - 5.0);
      if (!this.actionButton.getDisabledOverlayColor().equals(ClientSettings.INSTANCE.getAccentColor())) {
         this.actionButton.setBackgroundAnimationColors(ClientSettings.INSTANCE.getAccentColor(), ClientSettings.INSTANCE.getAccentColor().brighter());
      }
   }

   public TextButton getActionButton() {
      return this.actionButton;
   }

   @Override
   public void V() {
   }

   @Override
   public double C() {
      double descHeight = this.descriptionLabel.getText().isEmpty() ? 0.0 : (this.descriptionLabel.L() > 0.0 ? this.descriptionLabel.L() : 18.0);
      double titleHeight = this.titleLabel.L() > 0.0 ? this.titleLabel.L() : 14.0;
      return Math.max(62.0, titleHeight + descHeight + 30.0);
   }

   public TutorialActionComponent(String string, String string2) {
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
      this.titleLabel = new WrappedTextComponent(string, 1.0, new Color(220, 220, 220), true, 0.0);
      this.descriptionLabel = new WrappedTextComponent(string2, 0.85, new Color(150, 150, 150, 255), false, 0.0);
      this.skipLabel = new UnderlinedTextLabel("Skip tutorial");
      this.actionButton = new TextButton("Ok", ClientSettings.INSTANCE.getAccentColor(), ClientSettings.INSTANCE.getAccentColor().brighter());
      this.skipLabel.o(46.0);
      this.skipLabel.Y(12.0);
      this.actionButton.o(36.0);
      this.actionButton.Y(14.0);
      this.o(145.0);
      this.titleLabel.setWrapWidth(129.0);
      this.titleLabel.setUseExplicitWidth(true);
      this.titleLabel.o(129.0);
      this.descriptionLabel.setWrapWidth(129.0);
      this.descriptionLabel.setUseExplicitWidth(true);
      this.descriptionLabel.o(129.0);
      this.descriptionLabel.Y(0.0);
      this.addChildren(this.titleLabel, this.descriptionLabel, this.skipLabel, this.actionButton);
      this.l$src$V$1mibm4x();
   }
}
