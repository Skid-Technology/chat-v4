/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL45;

public final class ARBClipControl {
    final public static int GL_LOWER_LEFT = 36001;
    final public static int GL_UPPER_LEFT = 36002;
    final public static int GL_NEGATIVE_ONE_TO_ONE = 37726;
    final public static int GL_ZERO_TO_ONE = 37727;
    final public static int GL_CLIP_ORIGIN = 37724;
    final public static int GL_CLIP_DEPTH_MODE = 37725;

    private ARBClipControl() {
    }

    public static void glClipControl(int origin, int depth) {
        GL45.glClipControl(origin, depth);
    }
}

