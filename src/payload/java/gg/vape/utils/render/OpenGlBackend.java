package gg.vape.utils.render;

public interface OpenGlBackend {
   void pushMatrix();

   void translate(float var1, float var2, float var3);

   void rotate(double var1, double var3, double var5, double var7);

   void setColor(double var1, float var3, float var4);

   void setScissor(int var1, int var2, int var3, int var4);

   void loadIdentity();

   float getFloatState(int var1);

   void popMatrix();

   void setLineWidth(float var1);

   void setAlphaFunction(int var1, float var2);

   void scale(float var1, float var2, float var3);

   void rotate(float var1, float var2, float var3, float var4);

   void setColor(float var1, float var2, float var3);

   void setNormal(float var1, float var2, float var3);

   void enableCapability(int var1);

   int getIntegerState(int var1);

   void translate(double var1, double var3, double var5);

   void endPrimitive();

   void beginPrimitive(int var1);

   void disableCapabilityState(int var1);

   boolean isCapabilityEnabled(int var1);

   void addVertex(double var1, double var3, double var5);

   void setNormal(double var1, double var3, double var5);

   void scale(double var1, double var3, double var5);

   void setColor(double var1, double var3, double var5, double var7);

   void setColor(float var1, float var2, float var3, float var4);

   void setDepthMask(boolean var1);

   default float getFloat(int parameter) {
      return this.getFloatState(parameter);
   }

   default void disableCapability(int capability) {
      this.disableCapabilityState(capability);
   }
}
