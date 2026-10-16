/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class EXTShaderImageLoadStore {
    final public static int GL_MAX_IMAGE_UNITS_EXT = 36664;
    final public static int GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS_EXT = 36665;
    final public static int GL_MAX_IMAGE_SAMPLES_EXT = 36973;
    final public static int GL_IMAGE_BINDING_NAME_EXT = 36666;
    final public static int GL_IMAGE_BINDING_LEVEL_EXT = 36667;
    final public static int GL_IMAGE_BINDING_LAYERED_EXT = 36668;
    final public static int GL_IMAGE_BINDING_LAYER_EXT = 36669;
    final public static int GL_IMAGE_BINDING_ACCESS_EXT = 36670;
    final public static int GL_IMAGE_BINDING_FORMAT_EXT = 36974;
    final public static int GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT_EXT = 1;
    final public static int GL_ELEMENT_ARRAY_BARRIER_BIT_EXT = 2;
    final public static int GL_UNIFORM_BARRIER_BIT_EXT = 4;
    final public static int GL_TEXTURE_FETCH_BARRIER_BIT_EXT = 8;
    final public static int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT_EXT = 32;
    final public static int GL_COMMAND_BARRIER_BIT_EXT = 64;
    final public static int GL_PIXEL_BUFFER_BARRIER_BIT_EXT = 128;
    final public static int GL_TEXTURE_UPDATE_BARRIER_BIT_EXT = 256;
    final public static int GL_BUFFER_UPDATE_BARRIER_BIT_EXT = 512;
    final public static int GL_FRAMEBUFFER_BARRIER_BIT_EXT = 1024;
    final public static int GL_TRANSFORM_FEEDBACK_BARRIER_BIT_EXT = 2048;
    final public static int GL_ATOMIC_COUNTER_BARRIER_BIT_EXT = 4096;
    final public static int GL_ALL_BARRIER_BITS_EXT = -1;
    final public static int GL_IMAGE_1D_EXT = 36940;
    final public static int GL_IMAGE_2D_EXT = 36941;
    final public static int GL_IMAGE_3D_EXT = 36942;
    final public static int GL_IMAGE_2D_RECT_EXT = 36943;
    final public static int GL_IMAGE_CUBE_EXT = 36944;
    final public static int GL_IMAGE_BUFFER_EXT = 36945;
    final public static int GL_IMAGE_1D_ARRAY_EXT = 36946;
    final public static int GL_IMAGE_2D_ARRAY_EXT = 36947;
    final public static int GL_IMAGE_CUBE_MAP_ARRAY_EXT = 36948;
    final public static int GL_IMAGE_2D_MULTISAMPLE_EXT = 36949;
    final public static int GL_IMAGE_2D_MULTISAMPLE_ARRAY_EXT = 36950;
    final public static int GL_INT_IMAGE_1D_EXT = 36951;
    final public static int GL_INT_IMAGE_2D_EXT = 36952;
    final public static int GL_INT_IMAGE_3D_EXT = 36953;
    final public static int GL_INT_IMAGE_2D_RECT_EXT = 36954;
    final public static int GL_INT_IMAGE_CUBE_EXT = 36955;
    final public static int GL_INT_IMAGE_BUFFER_EXT = 36956;
    final public static int GL_INT_IMAGE_1D_ARRAY_EXT = 36957;
    final public static int GL_INT_IMAGE_2D_ARRAY_EXT = 36958;
    final public static int GL_INT_IMAGE_CUBE_MAP_ARRAY_EXT = 36959;
    final public static int GL_INT_IMAGE_2D_MULTISAMPLE_EXT = 36960;
    final public static int GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY_EXT = 36961;
    final public static int GL_UNSIGNED_INT_IMAGE_1D_EXT = 36962;
    final public static int GL_UNSIGNED_INT_IMAGE_2D_EXT = 36963;
    final public static int GL_UNSIGNED_INT_IMAGE_3D_EXT = 36964;
    final public static int GL_UNSIGNED_INT_IMAGE_2D_RECT_EXT = 36965;
    final public static int GL_UNSIGNED_INT_IMAGE_CUBE_EXT = 36966;
    final public static int GL_UNSIGNED_INT_IMAGE_BUFFER_EXT = 36967;
    final public static int GL_UNSIGNED_INT_IMAGE_1D_ARRAY_EXT = 36968;
    final public static int GL_UNSIGNED_INT_IMAGE_2D_ARRAY_EXT = 36969;
    final public static int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY_EXT = 36970;
    final public static int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_EXT = 36971;
    final public static int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY_EXT = 36972;

    private EXTShaderImageLoadStore() {
    }

    public static void glBindImageTextureEXT(int index, int texture, int level, boolean layered, int layer, int access, int format) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBindImageTextureEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        EXTShaderImageLoadStore.nglBindImageTextureEXT(index, texture, level, layered, layer, access, format, function_pointer);
    }

    static native void nglBindImageTextureEXT(int var0, int var1, int var2, boolean var3, int var4, int var5, int var6, long var7);

    public static void glMemoryBarrierEXT(int barriers) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glMemoryBarrierEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        EXTShaderImageLoadStore.nglMemoryBarrierEXT(barriers, function_pointer);
    }

    static native void nglMemoryBarrierEXT(int var0, long var1);
}

