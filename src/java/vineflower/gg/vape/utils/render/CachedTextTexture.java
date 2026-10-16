package gg.vape.utils.render;

import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

class CachedTextTexture {
   GlFramebuffer framebuffer;

   void render(float x, float y, int width, int height) {
      OpenGlBackendHolder.backend.enableCapability(2903);
      boolean textureEnabled = GL11.glIsEnabled(3553);
      boolean lightingEnabled = GL11.glIsEnabled(2896);
      boolean alphaTestEnabled = GL11.glIsEnabled(3008);
      boolean blendEnabled = GL11.glIsEnabled(3042);
      if (!textureEnabled) {
         GlStateManager.enableTexture2D();
      }

      if (lightingEnabled) {
         GlStateManager.disableLighting();
      }

      if (lightingEnabled) {
         GlStateManager.disableLighting();
      }

      if (!alphaTestEnabled) {
         GlStateManager.enableAlpha();
      }

      if (!blendEnabled) {
         GlStateManager.enableBlend();
      }

      int previousFramebufferId = GL11.glGetInteger(36006);
      int previousTextureId = GL11.glGetInteger(32873);
      this.framebuffer.bindColorTexture();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      float minU = 0.0F;
      float maxV = 1.0F;
      float maxU = 1.0F;
      float minV = 0.0F;
      GL11.glBegin(7);
      GL11.glTexCoord2f(maxU, maxV);
      GL11.glVertex2f(x + width, y);
      GL11.glTexCoord2f(minU, maxV);
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(minU, minV);
      GL11.glVertex2f(x, y + height);
      GL11.glTexCoord2f(maxU, minV);
      GL11.glVertex2f(x + width, y + height);
      GL11.glEnd();
      GL11.glPopMatrix();
      this.framebuffer.restorePreviousTexture();
      GlStateManager.bindTexture(previousTextureId);
      if (!textureEnabled) {
         GlStateManager.disableTexture2D();
      }

      if (lightingEnabled) {
         GlStateManager.enableLighting();
      }

      if (alphaTestEnabled) {
         GlStateManager.enableAlpha();
      }

      if (blendEnabled) {
         GlStateManager.enableBlend();
      }
   }

   void build(String text, int color) {
      int textWidth = Minecraft.getFontRenderer().getStringWidth(text);
      int textHeight = Minecraft.getFontRenderer().FONT_HEIGHT(text);
      FloatBuffer previousProjectionMatrix = BufferUtils.createFloatBuffer(16);
      GL11.glGetFloat(2983, previousProjectionMatrix);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glPushMatrix();
      GL11.glOrtho(0.0, textWidth, textHeight, 0.0, 1000.0, 3000.0);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      int previousTextureId = GL11.glGetInteger(32873);
      this.framebuffer = new GlFramebuffer(textWidth, textHeight, true);
      this.framebuffer.createAttachments(textWidth, textHeight);
      this.framebuffer.bind(true);
      Minecraft.getFontRenderer().drawString(text, 0.0, 0.0, color);
      this.framebuffer.bindColorTexture();
      this.framebuffer.unbind();
      GlStateManager.bindTexture(previousTextureId);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glPopMatrix();
      GL11.glLoadMatrix(previousProjectionMatrix);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glPopMatrix();
   }
}
