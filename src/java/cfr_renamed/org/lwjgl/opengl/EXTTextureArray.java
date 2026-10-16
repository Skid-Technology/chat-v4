/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.EXTGeometryShader4;

public final class EXTTextureArray {
    final public static int GL_TEXTURE_1D_ARRAY_EXT = 35864;
    final public static int GL_TEXTURE_2D_ARRAY_EXT = 35866;
    final public static int GL_PROXY_TEXTURE_2D_ARRAY_EXT = 35867;
    final public static int GL_PROXY_TEXTURE_1D_ARRAY_EXT = 35865;
    final public static int GL_TEXTURE_BINDING_1D_ARRAY_EXT = 35868;
    final public static int GL_TEXTURE_BINDING_2D_ARRAY_EXT = 35869;
    final public static int GL_MAX_ARRAY_TEXTURE_LAYERS_EXT = 35071;
    final public static int GL_COMPARE_REF_DEPTH_TO_TEXTURE_EXT = 34894;
    final public static int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER_EXT = 36052;
    final public static int GL_SAMPLER_1D_ARRAY_EXT = 36288;
    final public static int GL_SAMPLER_2D_ARRAY_EXT = 36289;
    final public static int GL_SAMPLER_1D_ARRAY_SHADOW_EXT = 36291;
    final public static int GL_SAMPLER_2D_ARRAY_SHADOW_EXT = 36292;

    private EXTTextureArray() {
    }

    public static void glFramebufferTextureLayerEXT(int target, int attachment, int texture, int level, int layer) {
        EXTGeometryShader4.glFramebufferTextureLayerEXT(target, attachment, texture, level, layer);
    }
}

