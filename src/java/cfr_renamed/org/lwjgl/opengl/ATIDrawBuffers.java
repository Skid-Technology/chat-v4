/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.APIUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class ATIDrawBuffers {
    final public static int GL_MAX_DRAW_BUFFERS_ATI = 34852;
    final public static int GL_DRAW_BUFFER0_ATI = 34853;
    final public static int GL_DRAW_BUFFER1_ATI = 34854;
    final public static int GL_DRAW_BUFFER2_ATI = 34855;
    final public static int GL_DRAW_BUFFER3_ATI = 34856;
    final public static int GL_DRAW_BUFFER4_ATI = 34857;
    final public static int GL_DRAW_BUFFER5_ATI = 34858;
    final public static int GL_DRAW_BUFFER6_ATI = 34859;
    final public static int GL_DRAW_BUFFER7_ATI = 34860;
    final public static int GL_DRAW_BUFFER8_ATI = 34861;
    final public static int GL_DRAW_BUFFER9_ATI = 34862;
    final public static int GL_DRAW_BUFFER10_ATI = 34863;
    final public static int GL_DRAW_BUFFER11_ATI = 34864;
    final public static int GL_DRAW_BUFFER12_ATI = 34865;
    final public static int GL_DRAW_BUFFER13_ATI = 34866;
    final public static int GL_DRAW_BUFFER14_ATI = 34867;
    final public static int GL_DRAW_BUFFER15_ATI = 34868;

    private ATIDrawBuffers() {
    }

    public static void glDrawBuffersATI(IntBuffer buffers) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glDrawBuffersATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(buffers);
        ATIDrawBuffers.nglDrawBuffersATI(buffers.remaining(), MemoryUtil.getAddress(buffers), function_pointer);
    }

    static native void nglDrawBuffersATI(int var0, long var1, long var3);

    public static void glDrawBuffersATI(int buffer) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glDrawBuffersATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIDrawBuffers.nglDrawBuffersATI(1, APIUtil.getInt(caps, buffer), function_pointer);
    }
}

