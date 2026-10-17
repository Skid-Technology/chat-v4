package gg.vape.module.render;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender2D;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.render.OffscreenRenderContext;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.FontRenderer;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Arrows extends Mod {
   private final BooleanValue showDistance;
   private final NumberValue radiusScale;
   private final ColorValue color = ColorValue.create(this, "Color", new Color(255, 0, 0));
   private static final long MODULE_COLOR = 6320396365828000318L;
   private final BooleanValue scaleOpacity;
   private final Map<Entity, double[]> projectedPositions;

   @EventHandler
   public void onRender2D(EventRender2D event) {
      EntityPlayerSP player = event.getThePlayer();
      if (!player.isNull() && !event.getWorld().isNull()) {
         int displayWidth = Minecraft.J();
         int displayHeight = Minecraft.h();
         float centerX = displayWidth / 4.0F;
         float centerY = displayHeight / 4.0F;
         GuiRenderPrimitives.u(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Color.WHITE);
         FontRenderer fontRenderer = event.getFontRenderer();

         for (Entity entity : this.projectedPositions.keySet()) {
            double[] projectedPosition = this.projectedPositions.get(entity);
            double screenX = projectedPosition[0];
            double screenY = Minecraft.h() - projectedPosition[1];
            if (!(projectedPosition[2] < 1.0) || !this.isOnScreen(screenX / 2.0, screenY / 2.0, displayWidth, displayHeight)) {
               float angle = this.getAngle(centerX, screenX / 2.0, centerY, screenY / 2.0) + (projectedPosition[2] > 1.0 ? 180 : 0);
               double horizontalRadius = centerX * this.radiusScale.getValue();
               double verticalRadius = centerY * this.radiusScale.getValue();
               double arrowX = Math.sqrt(
                  1.0 / (1.0 / (horizontalRadius * horizontalRadius) + Math.pow(Math.tan(Math.toRadians(angle)), 2.0) / (verticalRadius * verticalRadius))
               );
               double arrowY = Math.tan(Math.toRadians(angle)) * arrowX;
               float wrappedAngle = MathUtil.wrapAngleTo180(angle + 90.0F);
               if (wrappedAngle < 0.0F) {
                  arrowX = -arrowX;
                  if (wrappedAngle > -180.0F) {
                     arrowY = -arrowY;
                  }
               }

               int distanceAlpha = (int)(player.getDistanceToEntity(entity) * 1.5);
               if (distanceAlpha > 255) {
                  distanceAlpha = 255;
               }

               if (this.showDistance.getEffectiveValue() && distanceAlpha < 255) {
                  OpenGlBackendHolder.backend.pushMatrix();
                  RenderUtils.g();
                  String distanceText = (int)player.getDistanceToEntity(entity) + "m";
                  OpenGlBackendHolder.backend.translate(arrowX + centerX, arrowY + centerY, 0.0);
                  OpenGlBackendHolder.backend.scale(0.5, 0.5, 0.0);
                  boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
                  GlStateManager.enableBlend();
                  Color textColor = new Color(255, 255, 255, 255 - (this.scaleOpacity.getEffectiveValue() ? distanceAlpha : 0));
                  if (GuiRenderPrimitives.d()) {
                     MatrixStack matrixStack = MatrixStack.A();
                     matrixStack.H();
                     float guiScale = Minecraft.p().k(Minecraft.gameSettings().T(), false) / 2.0F;
                     float renderScale = 1.0F;
                     float inverseGuiScale = 1.0F / guiScale;
                     arrowX = Math.ceil(arrowX);
                     arrowY = Math.ceil(arrowY);
                     arrowX /= renderScale;
                     arrowY /= renderScale;
                     matrixStack.S(inverseGuiScale, inverseGuiScale, inverseGuiScale);
                     matrixStack.S(renderScale, renderScale, renderScale);
                     matrixStack.i(BufferedGuiRenderPrimitives.matrixStack.peek().toMinecraftMatrix());
                     fontRenderer.V(
                        distanceText,
                        -fontRenderer.getStringWidth(distanceText) / 2.0F,
                        -fontRenderer.getHalfFontHeight(distanceText),
                        ColorUtil.packRgba(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), textColor.getAlpha()),
                        matrixStack
                     );
                  } else {
                     fontRenderer.drawStringWithShadow(
                        distanceText,
                        -fontRenderer.getStringWidth(distanceText) / 2.0F,
                        -fontRenderer.getHalfFontHeight(distanceText),
                        ColorUtil.packRgba(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), textColor.getAlpha())
                     );
                  }

                  if (!blendEnabled) {
                     GlStateManager.disableBlend();
                  }

                  RenderUtils.f();
                  OpenGlBackendHolder.backend.popMatrix();
               }

               OpenGlBackendHolder.backend.pushMatrix();
               RenderUtils.g();
               OpenGlBackendHolder.backend.translate(arrowX + centerX, arrowY + centerY, 0.0);
               OpenGlBackendHolder.backend.rotate(angle - 90.0F, 0.0F, 0.0F, 1.0F);
               OpenGlBackendHolder.backend.scale(0.375, 0.5, 0.0);
               Color configuredColor = this.color.getMutableColor();
               int alpha = configuredColor.getAlpha();
               if (this.scaleOpacity.getEffectiveValue()) {
                  alpha = 255 - distanceAlpha;
               }

               Color arrowColor = new Color(configuredColor.getRed(), configuredColor.getGreen(), configuredColor.getBlue(), alpha);
               ImageRenderer.drawResWithShadow(arrowColor, -16.0F, 0.0F, "exo", 1.0F, false);
               RenderUtils.f();
               OpenGlBackendHolder.backend.popMatrix();
            }
         }

         this.projectedPositions.clear();
      }
   }

   private boolean isOnScreen(double x, double y, int displayWidth, int displayHeight) {
      return x > 0.0 && y > 0.0 && x < displayWidth / 2 && y < displayHeight / 2;
   }

   @EventHandler
   public void onRender3D(EventRender3D event) {
      this.projectedPositions.clear();
      RenderUtil.d();

      for (Object entityHandle : Minecraft.theWorld().z()) {
         Entity entity = new Entity(entityHandle);
         if (this.shouldTrack(entity)) {
            double renderX = entity.M() + (entity.z() - entity.M()) * event.getTicks() - RenderManager.getInterpolatedRenderPosX();
            double renderY = entity.W() + (entity.N() - entity.W()) * event.getTicks() - RenderManager.getInterpolatedRenderPosY();
            double renderZ = entity.m$src$D$fwnne5() + (entity.h() - entity.m$src$D$fwnne5()) * event.getTicks() - RenderManager.getInterpolatedRenderPosZ();
            double[] projectedPosition = RenderUtil.W(renderX, renderY, renderZ);
            this.projectedPositions.put(entity, projectedPosition);
         }
      }

      RenderUtil.Y();
   }

   public boolean shouldTrack(Entity entity) {
      if (OffscreenRenderContext.isRenderingOffscreen()) {
         return false;
      } else if (!entity.isInstance(MappedClasses.Yl)) {
         return false;
      } else if (entity.isInstance(MappedClasses.z5)) {
         return false;
      } else {
         EntityPlayer player = new EntityPlayer(entity.getObject());
         if (Vape.INSTANCE.getClientSettings().isBot(player)) {
            return false;
         } else {
            return Vape.INSTANCE.getClientSettings().isTeammate(player) ? false : !Vape.INSTANCE.getFriendManager().isFriend(player.getName());
         }
      }
   }

   public Arrows() {
      super("Arrows", 14223934, Category.RENDER, "Draws arrows on screen when entities\nare out of your field of view.");
      this.showDistance = BooleanValue.create(this, "Show Distance", false, "Renders the distance next to the arrow.");
      this.scaleOpacity = BooleanValue.create(this, "Scale Opacity", false, "Lowers the opacity the farther they are.");
      this.radiusScale = NumberValue.create(this, "Radius Scale", "#.##", "x", 0.0, 0.5, 1.0, 0.05);
      this.projectedPositions = new HashMap<>();
      this.addValue(this.color, this.radiusScale, this.showDistance, this.scaleOpacity);
   }

   private float getAngle(double centerX, double x, double centerY, double y) {
      return (float)Math.toDegrees(Math.atan2(y - centerY, x - centerX));
   }
}
