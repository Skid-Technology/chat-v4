/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class EXTBlendColor {
    final public static int GL_CONSTANT_COLOR_EXT = 32769;
    final public static int GL_ONE_MINUS_CONSTANT_COLOR_EXT = 32770;
    final public static int GL_CONSTANT_ALPHA_EXT = 32771;
    final public static int GL_ONE_MINUS_CONSTANT_ALPHA_EXT = 32772;
    final public static int GL_BLEND_COLOR_EXT = 32773;

    private EXTBlendColor() {
    }

    public static void glBlendColorEXT(float red, float green, float blue, float alpha) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBlendColorEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        EXTBlendColor.nglBlendColorEXT(red, green, blue, alpha, function_pointer);
    }

    static native void nglBlendColorEXT(float var0, float var1, float var2, float var3, long var4);
}

