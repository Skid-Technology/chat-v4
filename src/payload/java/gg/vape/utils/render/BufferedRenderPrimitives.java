package gg.vape.utils.render;

import gg.vape.ui.font.SmoothFontGlyph;
import gg.vape.utils.MutableColor;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class BufferedRenderPrimitives {
   public static void drawRectOutline(float left, float top, float right, float bottom, float lineWidth, Color color) {
      drawLine3D(left, top, 0.0F, right, top, 0.0F, lineWidth, color);
      drawLine3D(right, top, 0.0F, right, bottom, 0.0F, lineWidth, color);
      drawLine3D(right, bottom, 0.0F, left, bottom, 0.0F, lineWidth, color);
      drawLine3D(left, bottom, 0.0F, left, top, 0.0F, lineWidth, color);
   }

   public static void drawLine(double startX, double startY, double endX, double endY, float width, Color color) {
      drawLine((float)startX, (float)startY, (float)endX, (float)endY, width, color);
   }

   public static void drawLine3D(double x1, double y1, double z1, double x2, double y2, double z2, float width, Color color) {
      drawLine3D((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2, width, color);
   }

   public static void fillQuad(
      float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color color
   ) {
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
         .addQuad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, color);
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void drawFontGlyph(float x, float y, SmoothFontGlyph glyph, GlImageTexture texture, Color color, float scale) {
      BufferedGuiRenderPrimitives.fontTexture = texture;
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addFontGlyph(x, y, glyph, color, scale);
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void drawMinecraftColorFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
         .addMinecraftColorFontGlyph(x, y, glyph, textureId, color, scale);
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void drawMinecraftIntensityFontGlyph(float x, float y, SmoothFontGlyph glyph, int textureId, Color color, float scale) {
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
         .addMinecraftIntensityFontGlyph(x, y, glyph, textureId, color, scale);
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void fillRect(double x, double y, double width, double height, Color color) {
      fillRect((float)x, (float)y, (float)width, (float)height, color);
   }

   public static void drawLine3D(float x1, float y1, float z1, float x2, float y2, float z2, float width, Color color) {
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(2, VertexCoordinateMode.DEFAULT, true).addLine(x1, y1, z1, x2, y2, z2, width, color);
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void fillQuad(
      double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color color
   ) {
      fillQuad((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2, (float)x3, (float)y3, (float)z3, (float)x4, (float)y4, (float)z4, color);
   }

   public static void fillBorderAdjustedRect(float x, float y, float width, float height, float borderWidth, Color fillColor, Color borderColor) {
      if (fillColor.equals(borderColor)) {
         borderWidth = 0.0F;
         RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
            .addSolidRect(x, y, width - borderWidth, height - borderWidth, fillColor);
         RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
      } else {
         float var10;
         float var11;
         float var12;
         RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
            .addSolidRect(var10 = x - borderWidth, y, (var11 = width + borderWidth) - borderWidth, (var12 = height + borderWidth) - borderWidth, fillColor);
         RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
         float right = var10 + var11;
         float bottom = y + var12;
      }
   }

   public static void fillRect(float x, float y, float width, float height, Color color) {
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true).addSolidRect(x, y, width, height, color);
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void drawBoxOutline(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color color) {
      drawLine3D(minX, minY, minZ, maxX, minY, minZ, 1.0F, color);
      drawLine3D(maxX, minY, minZ, maxX, minY, maxZ, 1.0F, color);
      drawLine3D(maxX, minY, minZ, maxX, minY, maxZ, 1.0F, color);
      drawLine3D(minX, minY, minZ, minX, minY, maxZ, 1.0F, color);
      drawLine3D(minX, minY, maxZ, maxX, minY, maxZ, 1.0F, color);
      drawLine3D(minX, minY, minZ, minX, maxY, minZ, 1.0F, color);
      drawLine3D(maxX, minY, maxZ, maxX, maxY, maxZ, 1.0F, color);
      drawLine3D(minX, minY, maxZ, minX, maxY, maxZ, 1.0F, color);
      drawLine3D(maxX, minY, minZ, maxX, maxY, minZ, 1.0F, color);
      drawLine3D(minX, maxY, minZ, maxX, maxY, minZ, 1.0F, color);
      drawLine3D(maxX, maxY, minZ, maxX, maxY, maxZ, 1.0F, color);
      drawLine3D(minX, maxY, minZ, minX, maxY, maxZ, 1.0F, color);
      drawLine3D(minX, maxY, maxZ, maxX, maxY, maxZ, 1.0F, color);
   }

   public static void drawBoxOutline(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
      drawBoxOutline((float)minX, (float)minY, (float)minZ, (float)maxX, (float)maxY, (float)maxZ, color);
   }

   public static void drawCylinderSides(double worldX, double worldY, double worldZ, float segments, float radius, float height, Color color) {
      double cameraX = RenderManager.getInterpolatedRenderPosX();
      double cameraY = RenderManager.getInterpolatedRenderPosY();
      double cameraZ = RenderManager.getInterpolatedRenderPosZ();
      float centerX = (float)(worldX - cameraX);
      float baseY = (float)(worldY - cameraY);
      float centerZ = (float)(worldZ - cameraZ);
      RenderUtil.d();
      Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
      OpenGlBackendHolder.backend.enableCapability(3042);
      GL11.glBlendFunc(770, 771);
      OpenGlBackendHolder.backend.setDepthMask(false);
      OpenGlBackendHolder.backend.disableCapability(2884);
      float bottomY = baseY;
      float topY = baseY + height;
      MutableColor transparentColor = new MutableColor(color).withAlpha(0);
      Color opaqueColor = color;
      float previousX = 0.0F;
      float previousZ = 0.0F;
      boolean hasPreviousPoint = false;

      for (int segmentIndex = 0; segmentIndex <= segments; segmentIndex++) {
         float angle = (float)((Math.PI * 2) * segmentIndex / segments);
         float currentX = (float)(radius * Math.cos(angle) + centerX);
         float currentZ = (float)(radius * Math.sin(angle) + centerZ);
         if (!hasPreviousPoint) {
            hasPreviousPoint = true;
            previousX = currentX;
            previousZ = currentZ;
         } else {
            fillGradientQuad(
               currentX, topY, currentZ, previousX, topY, previousZ, previousX, bottomY, previousZ, currentX, bottomY, currentZ, transparentColor, opaqueColor
            );
            previousX = currentX;
            previousZ = currentZ;
         }
      }

      OpenGlBackendHolder.backend.setDepthMask(true);
      OpenGlBackendHolder.backend.disableCapability(3042);
      Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
      RenderUtil.Y();
   }

   public static void drawLine(float startX, float startY, float endX, float endY, float width, Color color) {
      drawLine3D(startX, startY, 0.0F, endX, endY, 0.0F, width, color);
   }

   public static void fillBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color color) {
      fillBox((float)minX, (float)minY, (float)minZ, (float)maxX, (float)maxY, (float)maxZ, color);
   }

   public static void fillBorderAdjustedRect(double x, double y, double width, double height, float borderWidth, Color fillColor, Color borderColor) {
      fillBorderAdjustedRect((float)x, (float)y, (float)width, (float)height, borderWidth, fillColor, borderColor);
   }

   public static void fillBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color color) {
      fillQuad(minX, minY, minZ, minX, maxY, minZ, maxX, maxY, minZ, maxX, minY, minZ, color);
      fillQuad(maxX, minY, maxZ, maxX, maxY, maxZ, minX, maxY, maxZ, minX, minY, maxZ, color);
      fillQuad(maxX, minY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, maxX, minY, maxZ, color);
      fillQuad(minX, minY, maxZ, minX, maxY, maxZ, minX, maxY, minZ, minX, minY, minZ, color);
      fillQuad(minX, maxY, minZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, color);
      fillQuad(minX, minY, minZ, maxX, minY, minZ, maxX, minY, maxZ, minX, minY, maxZ, color);
   }

   public static void drawCompositeRoundedRect(
      float x,
      float y,
      float width,
      float height,
      float shadowSpread,
      float cornerRadius,
      Color shadowColor,
      float shadowOffsetX,
      float shadowOffsetY,
      float strokeWidth,
      Color strokeColor,
      Color fillColor,
      float circleThickness,
      float circleYOffset,
      Color circleColor,
      boolean texturePassthrough
   ) {
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
         .addCompositeRoundedRect(
            x,
            y,
            width,
            height,
            shadowSpread,
            cornerRadius,
            shadowColor,
            shadowOffsetX,
            shadowOffsetY,
            strokeWidth,
            strokeColor,
            fillColor,
            circleThickness,
            circleYOffset,
            circleColor,
            texturePassthrough
         );
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void fillGradientQuad(
      float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Color startColor, Color endColor
   ) {
      RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(4, VertexCoordinateMode.DEFAULT, true)
         .addGradientQuad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, startColor, endColor);
      RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
   }

   public static void drawRectOutline(double left, double top, double right, double bottom, float lineWidth, Color color) {
      drawRectOutline((float)left, (float)top, (float)right, (float)bottom, lineWidth, color);
   }
}
