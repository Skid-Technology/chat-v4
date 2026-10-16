/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.unmap.Stencil;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.impl.Framebuffer;
import gg.vape.wrapper.impl.Minecraft;
import java.util.HashMap;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public final class StencilUtil {
    private int currentLayer = 1;
    private final HashMap<Integer, Stencil> stencilStatesByLayer = new HashMap();
    private final static StencilUtil INSTANCE = new StencilUtil();
    private boolean invertedComparison;

    public void configureNotEqualStencilState() {
        this.applyStencilState(new Stencil(this, 517, this.currentLayer, this.getStencilMask(), 7680, 7680, 7680));
    }

    public void reset() {
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        this.stencilStatesByLayer.clear();
        this.currentLayer = 1;
    }

    public static void attachStencilBuffer(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.getDepthBuffer());
        int n = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, n);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.J(), Minecraft.h());
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, n);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, n);
    }

    public int getStencilMask() {
        return (int)(Math.pow(2.0, this.getStencilBitDepth()) - 1.0);
    }

    public static StencilUtil getInstance() {
        return INSTANCE;
    }

    public void drawRectangle(double left, double top, double right, double bottom) {
        GL11.glBegin(7);
        GL11.glVertex2d(left, bottom);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glVertex2d(left, top);
        GL11.glEnd();
    }

    public static void ensureFramebufferStencilBuffer() {
        Framebuffer framebuffer = Minecraft.getFrameBuffer();
        if (framebuffer.isNotNull() && framebuffer.getDepthBuffer() > -1) {
            StencilUtil.attachStencilBuffer(framebuffer);
            framebuffer.setDepthBuffer(-1);
        }
    }

    public void applyStencilState(Stencil stencil) {
        GL11.glStencilFunc(Stencil.function, Stencil.referenceValue, Stencil.mask);
        GL11.glStencilOp(Stencil.stencilFailOperation, Stencil.depthFailOperation, Stencil.depthPassOperation);
        this.stencilStatesByLayer.put(this.currentLayer, stencil);
    }

    public int getCurrentLayer() {
        return this.currentLayer;
    }

    public void configureLayerIncrement() {
        this.applyStencilState(new Stencil(this, this.invertedComparison ? 519 : 512, this.currentLayer, this.getStencilMask(), 7681, 7680, 7680));
    }

    public void configureLayerMask(boolean useCurrentLayer) {
        this.applyStencilState(new Stencil(this, this.invertedComparison ? 519 : 512, useCurrentLayer ? this.currentLayer : this.currentLayer - 1, this.getStencilMask(), 7681, 7681, 7681));
    }

    public void setInvertedComparison(boolean invertedComparison) {
        this.invertedComparison = invertedComparison;
    }

    public void configureEqualLayerTest() {
        this.applyStencilState(new Stencil(this, 514, this.currentLayer, this.getStencilMask(), 7680, 7680, 7680));
    }

    public void pushLayer() {
        if (this.currentLayer == 1) {
            GL11.glClearStencil(0);
            GL11.glClear(1024);
        }
        OpenGlBackendHolder.backend.enableCapability(2960);
        ++this.currentLayer;
        if (this.currentLayer > this.getStencilMask()) {
            System.out.println("StencilUtil: Reached maximum amount of layers!");
            this.currentLayer = 1;
        }
    }

    public Stencil getCurrentStencilState() {
        return this.stencilStatesByLayer.get(this.currentLayer);
    }

    public void popLayer() {
        if (this.currentLayer == 1) {
            System.out.println("StencilUtil: No layers found!");
            return;
        }
        --this.currentLayer;
        if (this.currentLayer == 1) {
            OpenGlBackendHolder.backend.disableCapability(2960);
        } else {
            Stencil stencil = this.stencilStatesByLayer.remove(this.currentLayer);
            if (stencil != null) {
                stencil.apply();
            }
        }
    }

    public void drawCircle(double centerX, double centerY, double radius) {
        GL11.glBegin(6);
        for (int angleDegrees = 0; angleDegrees <= 360; ++angleDegrees) {
            double offsetX = Math.sin((double)angleDegrees * Math.PI / 180.0) * radius;
            double offsetY = Math.cos((double)angleDegrees * Math.PI / 180.0) * radius;
            GL11.glVertex2d(centerX + offsetX, centerY + offsetY);
        }
        GL11.glEnd();
    }

    public int getStencilBitDepth() {
        return GL11.glGetInteger(3415);
    }
}

