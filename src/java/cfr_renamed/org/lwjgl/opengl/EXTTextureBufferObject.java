/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class EXTTextureBufferObject {
    final public static int GL_TEXTURE_BUFFER_EXT = 35882;
    final public static int GL_MAX_TEXTURE_BUFFER_SIZE_EXT = 35883;
    final public static int GL_TEXTURE_BINDING_BUFFER_EXT = 35884;
    final public static int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_EXT = 35885;
    final public static int GL_TEXTURE_BUFFER_FORMAT_EXT = 35886;

    private EXTTextureBufferObject() {
    }

    public static void glTexBufferEXT(int target, int internalformat, int buffer) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glTexBufferEXT;
        BufferChecks.checkFunctionAddress(function_pointer);
        EXTTextureBufferObject.nglTexBufferEXT(target, internalformat, buffer, function_pointer);
    }

    static native void nglTexBufferEXT(int var0, int var1, int var2, long var3);
}

