package gg.vape.notification;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.ui.theme.ThemeColors;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class Notification extends AbstractNotification {
   private boolean started;
   private long expiresAt = Long.MAX_VALUE;
   private static final String LARGE_ALERT_ICON = "noti_alert_large";
   private final BlurRegionRenderer blurRenderer = new BlurRegionRenderer(0, 0);
   private long durationMillis;
   private double cachedWidth = -1.0;
   private double cachedHeight = -1.0;

   private double getRenderX() {
      return this.getCurrentX() + 3.0 + Minecraft.J() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0;
   }

   public long getExpiresAt() {
      return this.expiresAt;
   }

   @Override
   public boolean shouldRemove() {
      return this.getCurrentX() >= 1.0;
   }

   @Override
   public void handleClick(double mouseX, double mouseY) {
      if (this.getBounds().J(mouseX, mouseY)) {
         this.dismiss();
      }
   }

   public Notification(NotificationType type, String title, NotificationContent content, double x, double y, long durationMillis) {
      super(type, title, content, x, y);
      this.durationMillis = durationMillis;
   }

   private double getRenderY() {
      return this.getCurrentY() + Minecraft.h() / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() / 2.0;
   }

   public double getRemainingProgress() {
      return Math.max(Math.min((double)(this.expiresAt - System.currentTimeMillis()) / this.durationMillis, 1.0), 0.0);
   }

   public void setDuration(long durationMillis) {
      this.durationMillis = durationMillis;
      this.expiresAt = System.currentTimeMillis() + durationMillis;
   }

   @Override
   public double getWidth() {
      if (this.cachedWidth < 0.0) {
         double iconMargin = 18.0;
         double minimumContentWidth = 100.0 + iconMargin;
         this.cachedWidth = Math.max(minimumContentWidth, super.getWidth()) + iconMargin;
      }

      return this.cachedWidth;
   }

   @Override
   public double getHeight() {
      if (this.cachedHeight < 0.0) {
         this.cachedHeight = super.getHeight();
      }

      return this.cachedHeight;
   }

   @Override
   public void dismiss() {
      this.setTargetX(5.0);
   }

   @Override
   public void render() {
      if (!this.started) {
         this.started = true;
         this.setDuration(this.durationMillis);
      }

      float inverseScale = (float)(1.0 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor());
      boolean blendingEnabled = GL11.glIsEnabled(3042);
      float scale = 1.0F / inverseScale;
      if (GuiRenderPrimitives.d()) {
         OpenGlBackendHolder.backend.scale(scale, scale, scale);
      }

      double renderX = this.getRenderX();
      double renderY = this.getRenderY();
      double width = this.getWidth();
      double height = this.getHeight();
      boolean hovered = this.getBounds().Z(RenderUtils.h());
      this.blurRenderer.setDimensions((int)width * 2, (int)height * 2);
      this.blurRenderer.renderBlur((int)renderX, (int)renderY, 20.0F, 3.0F);
      Color iconColor = this.getRenderColor();
      Color backgroundColor = new Color(0, 0, 0, 173);
      Color borderColor = new Color(48, 48, 48, 255);
      if (hovered) {
         backgroundColor = new Color(0, 0, 0, 200);
         borderColor = new Color(60, 60, 60, 255);
      }

      GuiRenderPrimitives.e(renderX, renderY, width, height, backgroundColor, true, 3.0F, 1.0F);
      GuiRenderPrimitives.P(renderX, renderY, width, height, borderColor, 3.0F, 1.0F, 1.0F);
      ImageRenderer.drawResWithShadow(iconColor, (float)renderX - 4.0F, (float)renderY - 6.0F, this.getIconResource(), 1.0F, true);
      if (this.getType().equals(NotificationType.ALERT)) {
         ImageRenderer.drawResWithShadow(iconColor, (float)renderX - 2.0F, (float)renderY, "noti_alert_large", 0.65F, false);
      }

      Vape.INSTANCE
         .getFontManager()
         .W(0.9, true)
         .d(
            this.getTitle(),
            renderX + 23.0,
            renderY + 8.0,
            this.getType().equals(NotificationType.ALERT) ? new Color(this.getType().getColor()) : ThemeColors.J.A
         );
      this.getContent().render(renderX + 23.0, renderY + 21.0);
      double remainingProgress = this.getRemainingProgress();
      if (remainingProgress < 100.0) {
         GuiRenderPrimitives.e(
            renderX + 1.0, renderY + height - 1.5, width * remainingProgress - 1.0, 0.5, new Color(this.getType().getColor()), false, 1.0F, 1.0F
         );
      }

      if (remainingProgress <= 0.0) {
         this.dismiss();
      }

      if (GuiRenderPrimitives.d()) {
         OpenGlBackendHolder.backend.scale(inverseScale, inverseScale, inverseScale);
      }

      OpenGlBackendHolder.backend.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (blendingEnabled) {
         GL11.glEnable(3042);
      }
   }

   private RectData getBounds() {
      return new RectData(this.getRenderX(), this.getRenderY(), this.getWidth(), this.getHeight());
   }

   public boolean isExpired() {
      return System.currentTimeMillis() >= this.expiresAt;
   }
}
