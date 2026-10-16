/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.Context;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.ContextGL;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.DrawableGL;
import org.lwjgl.opengl.DrawableLWJGL;
import org.lwjgl.opengl.PixelFormatLWJGL;

public final class SharedDrawable
extends DrawableGL {
    public SharedDrawable(Drawable drawable) throws LWJGLException {
        this.context = (ContextGL)((DrawableLWJGL)drawable).createSharedContext();
    }

    public ContextGL createSharedContext() {
        throw new UnsupportedOperationException();
    }

    public void setCLSharingProperties(PointerBuffer x0) throws LWJGLException {
        super.setCLSharingProperties(x0);
    }

    public void destroy() {
        super.destroy();
    }

    public void releaseContext() throws LWJGLException {
        super.releaseContext();
    }

    public void makeCurrent() throws LWJGLException {
        super.makeCurrent();
    }

    public boolean isCurrent() throws LWJGLException {
        return super.isCurrent();
    }

    public void initContext(float x0, float x1, float x2) {
        super.initContext(x0, x1, x2);
    }

    public void swapBuffers() throws LWJGLException {
        super.swapBuffers();
    }

    public void setSwapInterval(int x0) {
        super.setSwapInterval(x0);
    }

    public void checkGLError() {
        super.checkGLError();
    }

    public ContextGL getContext() {
        return super.getContext();
    }

    public PixelFormatLWJGL getPixelFormat() {
        return super.getPixelFormat();
    }

    public void setPixelFormat(PixelFormatLWJGL x0, ContextAttribs x1) throws LWJGLException {
        super.setPixelFormat(x0, x1);
    }

    public void setPixelFormat(PixelFormatLWJGL x0) throws LWJGLException {
        super.setPixelFormat(x0);
    }

    public Context createSharedContext() throws LWJGLException {
        return this.createSharedContext();
    }
}

