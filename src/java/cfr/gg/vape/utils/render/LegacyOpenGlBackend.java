/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.utils.render.OpenGlBackend;
import org.lwjgl.opengl.GL11;

public class LegacyOpenGlBackend
implements OpenGlBackend {
    @Override
    public void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    @Override
    public void setNormal(double x, double y, double z) {
        GL11.glNormal3d(x, y, z);
    }

    @Override
    public void endPrimitive() {
        GL11.glEnd();
    }

    @Override
    public void setAlphaFunction(int function, float reference) {
        GL11.glAlphaFunc(function, reference);
    }

    @Override
    public void setDepthMask(boolean enabled) {
        GL11.glDepthMask(enabled);
    }

    @Override
    public void disableCapabilityState(int capability) {
        GL11.glDisable(capability);
    }

    @Override
    public void loadIdentity() {
        GL11.glLoadIdentity();
    }

    @Override
    public void enableCapability(int capability) {
        GL11.glEnable(capability);
    }

    @Override
    public void popMatrix() {
        GL11.glPopMatrix();
    }

    @Override
    public void addVertex(double x, double y, double z) {
        GL11.glVertex3d(x, y, z);
    }

    @Override
    public float getFloatState(int parameter) {
        return GL11.glGetFloat(parameter);
    }

    @Override
    public void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    @Override
    public boolean isCapabilityEnabled(int capability) {
        return GL11.glIsEnabled(capability);
    }

    @Override
    public void setColor(float red, float green, float blue, float alpha) {
        GL11.glColor4f(red, green, blue, alpha);
    }

    @Override
    public int getIntegerState(int parameter) {
        return GL11.glGetInteger(parameter);
    }

    @Override
    public void setColor(double red, float green, float blue) {
        GL11.glColor3d(red, green, blue);
    }

    @Override
    public void setColor(float red, float green, float blue) {
        GL11.glColor3f(red, green, blue);
    }

    @Override
    public void rotate(double angle, double x, double y, double z) {
        GL11.glRotated(angle, x, y, z);
    }

    @Override
    public void setColor(double red, double green, double blue, double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    @Override
    public void scale(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    @Override
    public void scale(double x, double y, double z) {
        GL11.glScaled(x, y, z);
    }

    @Override
    public void setScissor(int x, int y, int width, int height) {
        GL11.glScissor(x, y, width, height);
    }

    @Override
    public void setLineWidth(float width) {
        GL11.glLineWidth(width);
    }

    @Override
    public void pushMatrix() {
        GL11.glPushMatrix();
    }

    @Override
    public void setNormal(float x, float y, float z) {
        GL11.glNormal3f(x, y, z);
    }

    @Override
    public void translate(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
    }

    @Override
    public void beginPrimitive(int mode) {
        GL11.glBegin(mode);
    }
}

