package gg.vape.utils.render;

import gg.vape.utils.datas.FloatPair;

public class WorldToScreenProjector {
   public static FloatPair projectToScreen(
      RenderVector3f worldPosition,
      RenderMatrix4f modelMatrix,
      RenderMatrix4f viewMatrix,
      RenderMatrix4f projectionMatrix,
      int viewportWidth,
      int viewportHeight
   ) {
      RenderMatrix4f modelViewMatrix = viewMatrix.multiply(modelMatrix);
      RenderMatrix4f clipMatrix = projectionMatrix.multiply(modelViewMatrix);
      RenderVector4f clipPosition = new RenderVector4f(worldPosition.x, worldPosition.y, worldPosition.z, 1.0F);
      clipMatrix.transform(clipPosition, clipPosition);
      if (clipPosition.w <= 0.01F) {
         return null;
      } else {
         float normalizedX = clipPosition.x / clipPosition.w;
         float normalizedY = clipPosition.y / clipPosition.w;
         float screenX = (normalizedX * 0.5F + 0.5F) * viewportWidth;
         float screenY = (1.0F - (normalizedY * 0.5F + 0.5F)) * viewportHeight;
         return new FloatPair(screenX, screenY);
      }
   }
}
