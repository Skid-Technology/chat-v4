package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.wrapper.impl.GL11;
import gg.vape.wrapper.impl.GlStateManager;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

public class GlFramebuffer {
   public int framebufferHeight;
   public static boolean skipTextureStateTracking = false;
   public boolean depthAttachmentEnabled;
   public int textureHeight;
   public int framebufferWidth;
   public int colorTextureId;
   public int depthRenderbufferId;
   private static String[] legacyMarkers;
   public int textureFilter;
   int previousFramebufferId = -1;
   int previousTextureId = -1;
   public int framebufferId;
   final boolean legacyFlag = true;
   public int textureWidth;
   public float[] clearColor;
   private static final String FRAMEBUFFER_ERROR_PREFIX = "Error creating fbo ";

   public void clear() {
      this.bind(true);
      FloatBuffer previousClearColor = BufferUtils.createFloatBuffer(16);
      GL11.G(3106, previousClearColor);
      org.lwjgl.opengl.GL11.glClearColor(this.clearColor[0], this.clearColor[1], this.clearColor[2], this.clearColor[3]);
      int clearMask = 16384;
      if (this.depthAttachmentEnabled) {
         org.lwjgl.opengl.GL11.glClearDepth(1.0);
         clearMask |= 256;
      }

      org.lwjgl.opengl.GL11.glClear(clearMask);
      org.lwjgl.opengl.GL11.glClearColor(previousClearColor.get(0), previousClearColor.get(1), previousClearColor.get(2), previousClearColor.get(3));
      this.unbind();
   }

   public static void setLegacyMarkers(String[] legacyMarkers) {
      GlFramebuffer.legacyMarkers = legacyMarkers;
   }

   public GlFramebuffer(int width, int height, boolean depthAttachmentEnabled) {
      this.depthAttachmentEnabled = depthAttachmentEnabled;
      this.framebufferId = -1;
      this.colorTextureId = -1;
      this.depthRenderbufferId = -1;
      this.clearColor = new float[4];
      this.clearColor[0] = 1.0F;
      this.clearColor[1] = 1.0F;
      this.clearColor[2] = 1.0F;
      this.clearColor[3] = 0.0F;
      this.initialize(width, height);
   }

   public void setTextureFilter(int textureFilter) {
      this.textureFilter = textureFilter;
      this.bindColorTexture();
      org.lwjgl.opengl.GL11.glTexParameterf(3553, 10241, textureFilter);
      org.lwjgl.opengl.GL11.glTexParameterf(3553, 10240, textureFilter);
      org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, 33071);
      org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, 33071);
      this.restorePreviousTexture();
   }

   public void unbind() {
      GL30.glBindFramebuffer(36160, this.previousFramebufferId);
   }

   public void delete() {
      this.restorePreviousTexture();
      this.unbind();
      if (this.depthRenderbufferId > -1) {
         GL30.glDeleteRenderbuffers(this.depthRenderbufferId);
         this.depthRenderbufferId = -1;
      }

      if (this.colorTextureId > -1) {
         org.lwjgl.opengl.GL11.glDeleteTextures(this.colorTextureId);
         this.colorTextureId = -1;
      }

      if (this.framebufferId > -1) {
         GL30.glDeleteFramebuffers(this.framebufferId);
         this.framebufferId = -1;
      }
   }

   public void checkStatus() {
      int status = GL30.glCheckFramebufferStatus(36160);
      if (status != 36053) {
         Vape.debugLog(FRAMEBUFFER_ERROR_PREFIX + status);
      }
   }

   public static String[] getLegacyMarkers() {
      return legacyMarkers;
   }

   public void bind(boolean updateViewport) {
      int boundFramebufferId = org.lwjgl.opengl.GL11.glGetInteger(36006);
      if (boundFramebufferId != this.framebufferId) {
         this.previousFramebufferId = boundFramebufferId;
      }

      GL30.glBindFramebuffer(36160, this.framebufferId);
      if (updateViewport) {
         org.lwjgl.opengl.GL11.glViewport(0, 0, this.framebufferWidth, this.framebufferHeight);
      }
   }

   public void initialize(int width, int height) {
      boolean depthTestEnabled = org.lwjgl.opengl.GL11.glIsEnabled(2929);
      GlStateManager.enableDepth();
      this.createAttachments(width, height);
      this.checkStatus();
      if (!depthTestEnabled) {
         GlStateManager.disableDepth();
      }
   }

   public void restorePreviousTexture() {
      if (!skipTextureStateTracking) {
         GlStateManager.bindTexture(this.previousTextureId);
      }
   }

   public void bindColorTexture() {
      int boundTextureId;
      if (!skipTextureStateTracking && (boundTextureId = org.lwjgl.opengl.GL11.glGetInteger(32873)) != this.colorTextureId) {
         this.previousTextureId = boundTextureId;
      }

      GlStateManager.bindTexture(this.colorTextureId);
   }

   public void createAttachments(int width, int height) {
      this.framebufferWidth = width;
      this.framebufferHeight = height;
      this.textureWidth = width;
      this.textureHeight = height;
      int previousRenderbufferId = org.lwjgl.opengl.GL11.glGetInteger(36007);
      this.framebufferId = GL30.glGenFramebuffers();
      this.colorTextureId = org.lwjgl.opengl.GL11.glGenTextures();
      if (this.depthAttachmentEnabled) {
         this.depthRenderbufferId = GL30.glGenRenderbuffers();
      }

      this.setTextureFilter(9728);
      this.bindColorTexture();
      org.lwjgl.opengl.GL11.glTexImage2D(3553, 0, 32856, this.textureWidth, this.textureHeight, 0, 6408, 5121, (ByteBuffer)null);
      this.bind(false);
      GL30.glFramebufferTexture2D(36160, 36064, 3553, this.colorTextureId, 0);
      if (this.depthAttachmentEnabled) {
         GL30.glBindRenderbuffer(36161, this.depthRenderbufferId);
         GL30.glRenderbufferStorage(36161, 33190, this.textureWidth, this.textureHeight);
         GL30.glFramebufferRenderbuffer(36160, 36096, 36161, this.depthRenderbufferId);
      }

      this.clear();
      this.unbind();
      this.restorePreviousTexture();
      GL30.glBindRenderbuffer(36161, previousRenderbufferId);
   }

   static {
      setLegacyMarkers(null);
   }
}
