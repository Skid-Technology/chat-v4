/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL32;

public final class ARBProvokingVertex {
    final public static int GL_FIRST_VERTEX_CONVENTION = 36429;
    final public static int GL_LAST_VERTEX_CONVENTION = 36430;
    final public static int GL_PROVOKING_VERTEX = 36431;
    final public static int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;

    private ARBProvokingVertex() {
    }

    public static void glProvokingVertex(int mode) {
        GL32.glProvokingVertex(mode);
    }
}

