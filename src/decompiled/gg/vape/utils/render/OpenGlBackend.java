/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public interface OpenGlBackend {
    public void pushMatrix();

    public void translate(float var1, float var2, float var3);

    public void rotate(double var1, double var3, double var5, double var7);

    public void setColor(double var1, float var3, float var4);

    public void setScissor(int var1, int var2, int var3, int var4);

    public void loadIdentity();

    public float getFloatState(int var1);

    public void popMatrix();

    public void setLineWidth(float var1);

    public void setAlphaFunction(int var1, float var2);

    public void scale(float var1, float var2, float var3);

    public void rotate(float var1, float var2, float var3, float var4);

    public void setColor(float var1, float var2, float var3);

    public void setNormal(float var1, float var2, float var3);

    public void enableCapability(int var1);

    public int getIntegerState(int var1);

    public void translate(double var1, double var3, double var5);

    public void endPrimitive();

    public void beginPrimitive(int var1);

    public void disableCapabilityState(int var1);

    public boolean isCapabilityEnabled(int var1);

    public void addVertex(double var1, double var3, double var5);

    public void setNormal(double var1, double var3, double var5);

    public void scale(double var1, double var3, double var5);

    public void setColor(double var1, double var3, double var5, double var7);

    public void setColor(float var1, float var2, float var3, float var4);

    public void setDepthMask(boolean var1);

    default public float getFloat(int parameter) {
        return this.getFloatState(parameter);
    }

    default public void disableCapability(int capability) {
        this.disableCapabilityState(capability);
    }
}

