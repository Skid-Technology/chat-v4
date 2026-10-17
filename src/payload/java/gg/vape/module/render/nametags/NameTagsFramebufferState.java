package gg.vape.module.render.nametags;

import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class NameTagsFramebufferState {
   private int depthRenderbuffer;
   private final int height;
   private final IntBuffer previousViewport = BufferUtils.createIntBuffer(16);
   private final int offsetX;
   private int previousFramebuffer;
   private final int width;
   private final int offsetY;
   private int colorTexture;
   private int framebuffer;

   public NameTagsFramebufferState(int offsetX, int offsetY, int width, int height) {
      this.offsetX = offsetX;
      this.offsetY = offsetY;
      this.width = width;
      this.height = height;
      this.initialize();
   }

   public void endRendering() {
      if (GuiRenderPrimitives.d()) {
         BufferedGuiRenderPrimitives.projectionMatrix.setIdentity();
         BufferedGuiRenderPrimitives.matrixStack.pop();
      } else {
         GL11.glMatrixMode(5888);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5889);
         GL11.glPopMatrix();
      }

      GL30.glBindFramebuffer(36160, this.previousFramebuffer);
      ((Buffer)this.previousViewport).rewind();
      GL11.glViewport(this.previousViewport.get(0), this.previousViewport.get(1), this.previousViewport.get(2), this.previousViewport.get(3));
   }

   public boolean isValid() {
      if (this.colorTexture == 0) {
         return false;
      } else {
         try {
            return GL11.glIsTexture(this.colorTexture);
         } catch (Exception var2) {
            return false;
         }
      }
   }

   public void beginRendering() {
      ((Buffer)this.previousViewport).clear();
      gg.vape.wrapper.impl.GL11.X(2978, this.previousViewport);
      IntBuffer framebufferBinding = BufferUtils.createIntBuffer(16);
      gg.vape.wrapper.impl.GL11.X(36006, framebufferBinding);
      this.previousFramebuffer = framebufferBinding.get(0);
      GL11.glViewport(0, 0, this.width * 4, this.height * 4);
      GL30.glBindFramebuffer(36160, this.framebuffer);
      GL11.glClear(256);
      GL11.glClear(16384);
      if (GuiRenderPrimitives.d()) {
         BufferedGuiRenderPrimitives.projectionMatrix.setIdentity();
         BufferedGuiRenderPrimitives.projectionMatrix.setOrthographic(0.0F, this.width, this.height, 0.0F, -2000.0F, 1000.0F);
         BufferedGuiRenderPrimitives.matrixStack.pushIdentity();
         BufferedGuiRenderPrimitives.matrixStack.resetCurrent();
         BufferedGuiRenderPrimitives.matrixStack.translate(-this.offsetX, -this.offsetY, 0.0F);
      } else {
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0, this.width, this.height, 0.0, -2000.0, 1000.0);
         GL11.glMatrixMode(5888);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glTranslatef(-this.offsetX, -this.offsetY, 0.0F);
      }
   }

   public int getOffsetY() {
      return this.offsetY;
   }

   private void initialize() {
      IntBuffer framebufferBinding = BufferUtils.createIntBuffer(16);
      gg.vape.wrapper.impl.GL11.X(36006, framebufferBinding);
      int previousFramebuffer = framebufferBinding.get(0);
      IntBuffer textureBinding = BufferUtils.createIntBuffer(16);
      gg.vape.wrapper.impl.GL11.X(32873, textureBinding);
      int previousTexture = textureBinding.get(0);
      IntBuffer renderbufferBinding = BufferUtils.createIntBuffer(16);
      gg.vape.wrapper.impl.GL11.X(36007, renderbufferBinding);
      int previousRenderbuffer = renderbufferBinding.get(0);
      this.framebuffer = GL30.glGenFramebuffers();
      GL30.glBindFramebuffer(36160, this.framebuffer);
      this.colorTexture = GL11.glGenTextures();
      GL11.glBindTexture(3553, this.colorTexture);
      GL11.glTexImage2D(3553, 0, 6408, this.width * 4, this.height * 4, 0, 6408, 5121, (ByteBuffer)null);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL30.glFramebufferTexture2D(36160, 36064, 3553, this.colorTexture, 0);
      this.depthRenderbuffer = GL30.glGenRenderbuffers();
      GL30.glBindRenderbuffer(36161, this.depthRenderbuffer);
      GL30.glRenderbufferStorage(36161, 6402, this.width * 4, this.height * 4);
      GL30.glFramebufferRenderbuffer(36160, 36096, 36161, this.depthRenderbuffer);
      GL30.glCheckFramebufferStatus(36160);
      GL30.glBindFramebuffer(36160, previousFramebuffer);
      GL11.glBindTexture(3553, previousTexture);
      GL30.glBindRenderbuffer(36161, previousRenderbuffer);
   }

   public void dispose() {
      GL30.glDeleteFramebuffers(this.framebuffer);
      GL11.glDeleteTextures(this.colorTexture);
      GL30.glDeleteRenderbuffers(this.depthRenderbuffer);
   }

   public int getOffsetX() {
      return this.offsetX;
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   public int getColorTexture() {
      return this.colorTexture;
   }
}
