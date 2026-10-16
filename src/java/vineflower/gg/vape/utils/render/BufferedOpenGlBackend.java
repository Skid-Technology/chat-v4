package gg.vape.utils.render;

import org.lwjgl.opengl.GL11;

public class BufferedOpenGlBackend implements OpenGlBackend {
   private int[] ignoredCapabilities = new int[]{3008, 3553, 2896};

   @Override
   public void endPrimitive() {
   }

   @Override
   public void popMatrix() {
      BufferedGuiRenderPrimitives.matrixStack.pop();
   }

   @Override
   public void scale(float x, float y, float z) {
      BufferedGuiRenderPrimitives.matrixStack.scale(x, y, z);
   }

   @Override
   public void pushMatrix() {
      BufferedGuiRenderPrimitives.matrixStack.pushIdentity();
   }

   @Override
   public void setAlphaFunction(int function, float reference) {
   }

   @Override
   public void enableCapability(int capability) {
      for (int ignoredCapability : this.ignoredCapabilities) {
         if (capability == ignoredCapability) {
            return;
         }
      }

      BufferedGuiRenderPrimitives.capabilityState.enableCapability(capability);
      GL11.glEnable(capability);
   }

   @Override
   public void disableCapabilityState(int capability) {
      for (int ignoredCapability : this.ignoredCapabilities) {
         if (capability == ignoredCapability) {
            return;
         }
      }

      BufferedGuiRenderPrimitives.capabilityState.disableCapability(capability);
      GL11.glDisable(capability);
   }

   @Override
   public void setColor(float red, float green, float blue, float alpha) {
   }

   @Override
   public void setLineWidth(float width) {
   }

   @Override
   public boolean isCapabilityEnabled(int capability) {
      for (int i = 0; i < this.ignoredCapabilities.length; i++) {
         if (capability == this.ignoredCapabilities[i]) {
            return false;
         }
      }

      return GL11.glIsEnabled(capability);
   }

   @Override
   public void setColor(float red, float green, float blue) {
   }

   @Override
   public void loadIdentity() {
   }

   @Override
   public void scale(double x, double y, double z) {
      this.scale((float)x, (float)y, (float)z);
   }

   @Override
   public void setColor(double red, float green, float blue) {
   }

   @Override
   public void addVertex(double x, double y, double z) {
   }

   @Override
   public void setNormal(float x, float y, float z) {
   }

   @Override
   public void setDepthMask(boolean enabled) {
      BufferedGuiRenderPrimitives.capabilityState.setDepthWriteEnabled(enabled);
   }

   @Override
   public void translate(float x, float y, float z) {
      BufferedGuiRenderPrimitives.matrixStack.translate(x, y, z);
   }

   @Override
   public void rotate(double angle, double x, double y, double z) {
      this.rotate((float)angle, (float)x, (float)y, (float)z);
   }

   @Override
   public void beginPrimitive(int mode) {
   }

   @Override
   public float getFloatState(int parameter) {
      return parameter == 3010 ? 0.0F : GL11.glGetFloat(parameter);
   }

   @Override
   public void setColor(double red, double green, double blue, double alpha) {
   }

   @Override
   public void rotate(float angle, float x, float y, float z) {
      BufferedGuiRenderPrimitives.matrixStack.rotate(angle, x, y, z);
   }

   @Override
   public void translate(double x, double y, double z) {
      this.translate((float)x, (float)y, (float)z);
   }

   @Override
   public int getIntegerState(int parameter) {
      return parameter == 3009 ? 0 : GL11.glGetInteger(parameter);
   }

   @Override
   public void setNormal(double x, double y, double z) {
      this.setNormal((float)x, (float)y, (float)z);
   }

   @Override
   public void setScissor(int x, int y, int width, int height) {
      BufferedGuiRenderPrimitives.scissorRect = new GlScissorRect(x, y, width, height);
      GL11.glScissor(x, y, width, height);
   }
}
