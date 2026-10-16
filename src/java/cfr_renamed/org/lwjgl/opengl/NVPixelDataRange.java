/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class NVPixelDataRange {
    final public static int GL_WRITE_PIXEL_DATA_RANGE_NV = 34936;
    final public static int GL_READ_PIXEL_DATA_RANGE_NV = 34937;
    final public static int GL_WRITE_PIXEL_DATA_RANGE_LENGTH_NV = 34938;
    final public static int GL_READ_PIXEL_DATA_RANGE_LENGTH_NV = 34939;
    final public static int GL_WRITE_PIXEL_DATA_RANGE_POINTER_NV = 34940;
    final public static int GL_READ_PIXEL_DATA_RANGE_POINTER_NV = 34941;

    private NVPixelDataRange() {
    }

    public static void glPixelDataRangeNV(int target, ByteBuffer data) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data);
        NVPixelDataRange.nglPixelDataRangeNV(target, data.remaining(), MemoryUtil.getAddress(data), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, DoubleBuffer data) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data);
        NVPixelDataRange.nglPixelDataRangeNV(target, data.remaining() << 3, MemoryUtil.getAddress(data), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, FloatBuffer data) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data);
        NVPixelDataRange.nglPixelDataRangeNV(target, data.remaining() << 2, MemoryUtil.getAddress(data), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, IntBuffer data) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data);
        NVPixelDataRange.nglPixelDataRangeNV(target, data.remaining() << 2, MemoryUtil.getAddress(data), function_pointer);
    }

    public static void glPixelDataRangeNV(int target, ShortBuffer data) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(data);
        NVPixelDataRange.nglPixelDataRangeNV(target, data.remaining() << 1, MemoryUtil.getAddress(data), function_pointer);
    }

    static native void nglPixelDataRangeNV(int var0, int var1, long var2, long var4);

    public static void glFlushPixelDataRangeNV(int target) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glFlushPixelDataRangeNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVPixelDataRange.nglFlushPixelDataRangeNV(target, function_pointer);
    }

    static native void nglFlushPixelDataRangeNV(int var0, long var1);
}

