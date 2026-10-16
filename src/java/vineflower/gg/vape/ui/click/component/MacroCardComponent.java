package gg.vape.ui.click.component;

import func.skidline.RectData;
import gg.vape.module.Macro;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class MacroCardComponent extends GuiComponent {
   private static final double BIND_INPUT_HEIGHT = 10.0;
   @Nullable
   private Runnable settingsAction;
   private final DoubleAnimation dimmingAnimation;
   private final TruncatedTextComponent detailsLabel;
   private static final double SETTINGS_HITBOX_VERTICAL_PADDING = 8.0;
   private final ColorAnimation selectedAccentAnimation;
   private static final double LEGACY_COLOR_CHANNEL_VALUE = 160.0;
   private static final double ICON_SIZE = 6.0;
   @Nullable
   private Runnable auxiliaryAction;
   private boolean dimmed;
   private final RectData settingsHitbox;
   private boolean selected;
   private final IconGlyphComponent settingsIcon;
   private final DoubleAnimation selectionOffsetAnimation;
   private final ColorAnimation cardHoverAnimation;
   private static final double CARD_HEIGHT = 22.0;
   private final TruncatedTextComponent macroNameLabel;
   private final BindableInputComponent bindInput;
   private final IconGlyphComponent macroIcon;
   private static final double SETTINGS_HITBOX_WIDTH_PADDING = 10.0;
   private static final float CORNER_RADIUS = 3.0F;
   @Nullable
   private Runnable cardAction;
   private final ColorAnimation settingsHoverAnimation;
   private static final double CONTENT_PADDING = 8.0;
   private final Macro macro;
   private final ColorAnimation selectedBackgroundAnimation;
   private static final double LABEL_GAP = 6.0;

   public void setSettingsAction(@Nullable Runnable settingsAction) {
      this.settingsAction = settingsAction;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   public boolean isSelected() {
      return this.selected;
   }

   @Override
   public double C() {
      return 22.0;
   }

   private Color applyDimmedAlpha(Color color) {
      if (color == null) {
         return null;
      } else if (this.dimmed && !this.selected) {
         double dimProgress = Math.min(1.0, Math.max(0.0, this.dimmingAnimation.getInterpolatedValue()));
         float alphaMultiplier = (float)(1.0 - 0.8 * dimProgress);
         int dimmedAlpha = Math.max(0, Math.round(color.getAlpha() * alphaMultiplier));
         return new Color(color.getRed(), color.getGreen(), color.getBlue(), dimmedAlpha);
      } else {
         return color;
      }
   }

   @Override
   public void g(GuiMouseEvent mouseEvent) {
      if (mouseEvent.getAction() == MouseButton.LEFT_CLICK || mouseEvent.getAction() == MouseButton.RIGHT_CLICK) {
         if (this.settingsHitbox.J(mouseEvent.getX(), mouseEvent.getY())) {
            if (this.settingsAction != null) {
               this.settingsAction.run();
            }
         } else if (!this.bindInput.V$src$Z$1xhop3l() || !this.bindInput.i(mouseEvent.getX(), mouseEvent.getY())) {
            if (this.cardAction != null) {
               this.cardAction.run();
            }
         }
      }
   }

   private String getBindDisplayText() {
      String bindText = this.macro.getBindText();
      return bindText != null && !bindText.isEmpty() ? bindText : "Set bind";
   }

   public MacroCardComponent(Macro macro, double legacyHeight) {
      this.getClass();
      this.cardHoverAnimation = new ColorAnimation(0.15, J.t, J.z);
      this.getClass();
      this.settingsHoverAnimation = new ColorAnimation(0.15, J.t, J.E);
      this.getClass();
      this.selectedBackgroundAnimation = new ColorAnimation(0.22499999999999998, J.m, J.H);
      this.getClass();
      this.selectedAccentAnimation = new ColorAnimation(0.15, J.R, J.o);
      this.getClass();
      this.selectionOffsetAnimation = new DoubleAnimation(0.15, 0.0, 2.0);
      this.getClass();
      this.dimmingAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
      this.settingsHitbox = new RectData(0.0, 0.0, 0.0, 0.0);
      this.macro = macro;
      this.setPropagateMouseEvents(true);
      this.macroIcon = new IconGlyphComponent("standalone_macro", 6.0F, 6.0F);
      this.macroIcon.setColor(J.W);
      this.settingsIcon = new IconGlyphComponent("settingdots", 6.0F, 6.0F);
      this.settingsIcon.setColor(J.W);
      this.macroNameLabel = new TruncatedTextComponent(macro.getName(), 50.0, 0.75);
      this.macroNameLabel.setHorizontalInset(0.0);
      this.detailsLabel = new TruncatedTextComponent(this.buildDetailsText(), 50.0, 0.625);
      this.detailsLabel.setHorizontalInset(0.0);
      this.detailsLabel.setUseExplicitWidth(true);
      this.bindInput = new BindableInputComponent(this.macro, J.A);
      this.bindInput.setVisible(false);
      this.bindInput.Y(10.0);
      this.addChildren(this.macroIcon, this.macroNameLabel, this.detailsLabel, this.settingsIcon);
      this.addChildren(this.bindInput);
   }

   private String buildDetailsText() {
      int minimumDelay = this.macro.getDelay().getMinimumInt();
      int maximumDelay;
      boolean hasDelayRange = minimumDelay != (maximumDelay = this.macro.getDelay().getMaximumInt());
      StringBuilder details = new StringBuilder(hasDelayRange ? minimumDelay + "-" + maximumDelay : String.valueOf(maximumDelay));
      details.append("ms delay");
      if (this.macro.getDoubleClick().getEffectiveValue()) {
         details.append(" • double click ");
         details.append(this.macro.getDoubleClickDelay().getMaximumInt());
         details.append("ms");
      }

      return details.toString();
   }

   public Macro getMacro() {
      return this.macro;
   }

   public MacroCardComponent(Macro macro) {
      this(macro, 22.0);
   }

   public void setCardAction(@Nullable Runnable cardAction) {
      this.cardAction = cardAction;
   }

   public void setDimmed(boolean dimmed) {
      this.dimmed = dimmed;
   }

   @Override
   public void H() {
      this.selectedBackgroundAnimation.u(this.selected);
      this.selectedAccentAnimation.u(this.selected);
      this.selectionOffsetAnimation.u(this.selected);
      this.dimmingAnimation.u(this.dimmed && !this.selected);
      double cardX = this.G$src$D$1b2f02a() + this.selectionOffsetAnimation.getInterpolatedValue();
      double cardY = this.n();
      double cardWidth = this.A();
      double cardHeight = this.L();
      double centerY = cardY + cardHeight / 2.0;
      double halfWidth = cardWidth / 2.0;
      boolean macroHasBind = this.macro.hasValidBinding();
      String detailsText = this.buildDetailsText();
      if (!detailsText.equals(this.detailsLabel.getText())) {
         this.detailsLabel.setText(detailsText);
      }

      double settingsIconX;
      boolean settingsHovered;
      boolean cardHovered;
      ColorAnimation var10000;
      boolean var10001;
      label67: {
         double settingsIconWidth = this.settingsIcon.A();
         double settingsIconHeight = this.settingsIcon.L();
         settingsIconX = cardX + cardWidth - 6.0 - settingsIconWidth;
         double settingsIconY = centerY - settingsIconHeight / 2.0;
         this.settingsIcon.K(settingsIconX);
         this.settingsIcon.S(settingsIconY);
         this.settingsHitbox.M(settingsIconX - 6.0);
         this.settingsHitbox.O(settingsIconY - 8.0);
         this.settingsHitbox.A(settingsIconWidth + 10.0);
         this.settingsHitbox.U(settingsIconHeight + 16.0);
         MousePosition mousePosition = RenderUtils.h();
         settingsHovered = this.settingsHitbox.Z(mousePosition);
         cardHovered = this.w$src$Z$e457mb();
         ColorAnimation var39 = this.cardHoverAnimation;
         if (!settingsHovered) {
            var10000 = var39;
            if (cardHovered) {
               var10001 = true;
               break label67;
            }
         } else {
            var10000 = var39;
         }

         var10001 = false;
      }

      var10000.u(var10001);
      this.settingsHoverAnimation.u(settingsHovered);
      Color selectedBackgroundColor = this.selectedBackgroundAnimation.getInterpolatedColor();
      GuiRenderPrimitives.B(cardX, cardY, cardWidth, cardHeight, this.applyDimmedAlpha(selectedBackgroundColor), 3.0F);
      Color hoverBackgroundColor = this.cardHoverAnimation.getInterpolatedColor();
      if (hoverBackgroundColor.getAlpha() > 0 && !this.selected) {
         GuiRenderPrimitives.B(cardX, cardY, cardWidth, cardHeight, this.applyDimmedAlpha(hoverBackgroundColor), 3.0F);
      }

      Color settingsHoverColor = this.settingsHoverAnimation.getInterpolatedColor();
      if (settingsHoverColor.getAlpha() > 0) {
         GuiRenderPrimitives.p(
            this.settingsHitbox.o(),
            this.settingsHitbox.W(),
            this.settingsHitbox.e(),
            this.settingsHitbox.R(),
            this.applyDimmedAlpha(settingsHoverColor),
            false,
            2.0F,
            1.0F,
            0.0F,
            J.u,
            6
         );
      }

      IconGlyphComponent var40 = this.settingsIcon;
      IconGlyphComponent var42;
      MacroCardComponent var43;
      Color var10002;
      if (settingsHovered) {
         var42 = var40;
         var43 = this;
         var10002 = J.f;
      } else {
         var42 = var40;
         var43 = this;
         var10002 = J.W;
      }

      var42.setColor(var43.applyDimmedAlpha(var10002));
      double bindInputX = settingsIconX - 8.0 - this.bindInput.A();
      double bindInputY = centerY - 5.0;
      boolean bindCaptureActive = this.bindInput.getCaptureTask().isCapturing();
      boolean showBindInput = macroHasBind || bindCaptureActive || !settingsHovered && cardHovered;
      this.bindInput.K(bindInputX);
      this.bindInput.S(bindInputY);
      this.bindInput.o(this.bindInput.getBindLabel().getRenderedWidth());
      this.bindInput.Y(10.0);
      this.bindInput.setVisible(showBindInput);
      GuiRenderPrimitives.p(cardX, cardY, halfWidth, cardHeight, this.applyDimmedAlpha(J.E), false, 3.0F, 1.0F, 0.0F, this.applyDimmedAlpha(J.u), 9);
      GuiRenderPrimitives.C(cardX + halfWidth - 1.0, cardY - 0.5, 1.0, cardHeight, this.applyDimmedAlpha(J.z));
      double macroIconX = cardX + 8.0;
      this.macroIcon.setColor(this.applyDimmedAlpha(ClientSettings.INSTANCE.getAccentColor()));
      this.macroIcon.K(macroIconX);
      this.macroIcon.S(centerY - this.macroIcon.L() / 2.0);
      this.macroNameLabel.setBold(this.selected);
      this.macroNameLabel.setTextColor(this.selected ? this.applyDimmedAlpha(Color.WHITE) : this.applyDimmedAlpha(J.A));
      double macroNameX = macroIconX + this.macroIcon.A() + 6.0;
      this.macroNameLabel.K(macroNameX);
      this.macroNameLabel.S(cardY);
      this.macroNameLabel.o(halfWidth - this.macroIcon.A() - 8.0 - 6.0 - 5.0);
      this.macroNameLabel.setMaxWidth(this.macroNameLabel.A());
      this.macroNameLabel.Y(cardHeight);
      this.detailsLabel.setTextColor(this.selected ? this.applyDimmedAlpha(J.A) : this.applyDimmedAlpha(J.h));
      this.detailsLabel.K(cardX + halfWidth + 5.0);
      this.detailsLabel.S(cardY);
      this.detailsLabel.o(halfWidth - (this.bindInput.V$src$Z$1xhop3l() ? this.bindInput.A() : 0.0) - this.settingsHitbox.e() - 8.0 - 6.0 - 8.0);
      this.detailsLabel.setMaxWidth(this.detailsLabel.A());
      this.detailsLabel.Y(cardHeight);
   }

   public boolean isDimmed() {
      return this.dimmed;
   }

   public void setAuxiliaryAction(@Nullable Runnable auxiliaryAction) {
      this.auxiliaryAction = auxiliaryAction;
   }
}
