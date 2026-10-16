/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL32;

public final class ARBTextureMultisample {
    final public static int GL_SAMPLE_POSITION = 36432;
    final public static int GL_SAMPLE_MASK = 36433;
    final public static int GL_SAMPLE_MASK_VALUE = 36434;
    final public static int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    final public static int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
    final public static int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    final public static int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
    final public static int GL_MAX_SAMPLE_MASK_WORDS = 36441;
    final public static int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
    final public static int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
    final public static int GL_MAX_INTEGER_SAMPLES = 37136;
    final public static int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
    final public static int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
    final public static int GL_TEXTURE_SAMPLES = 37126;
    final public static int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
    final public static int GL_SAMPLER_2D_MULTISAMPLE = 37128;
    final public static int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
    final public static int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
    final public static int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
    final public static int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
    final public static int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;

    private ARBTextureMultisample() {
    }

    public static void glTexImage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) {
        GL32.glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
    }

    public static void glTexImage3DMultisample(int target, int samples, int internalformat, int width, int height, int depth, boolean fixedsamplelocations) {
        GL32.glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
    }

    public static void glGetMultisample(int pname, int index, FloatBuffer val) {
        GL32.glGetMultisample(pname, index, val);
    }

    public static void glSampleMaski(int index, int mask) {
        GL32.glSampleMaski(index, mask);
    }
}

