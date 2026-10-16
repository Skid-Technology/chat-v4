/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class ARBColorBufferFloat {
    final public static int GL_RGBA_FLOAT_MODE_ARB = 34848;
    final public static int GL_CLAMP_VERTEX_COLOR_ARB = 35098;
    final public static int GL_CLAMP_FRAGMENT_COLOR_ARB = 35099;
    final public static int GL_CLAMP_READ_COLOR_ARB = 35100;
    final public static int GL_FIXED_ONLY_ARB = 35101;
    final public static int WGL_TYPE_RGBA_FLOAT_ARB = 8608;
    final public static int GLX_RGBA_FLOAT_TYPE = 8377;
    final public static int GLX_RGBA_FLOAT_BIT = 4;

    private ARBColorBufferFloat() {
    }

    public static void glClampColorARB(int target, int clamp) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glClampColorARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBColorBufferFloat.nglClampColorARB(target, clamp, function_pointer);
    }

    static native void nglClampColorARB(int var0, int var1, long var2);
}

