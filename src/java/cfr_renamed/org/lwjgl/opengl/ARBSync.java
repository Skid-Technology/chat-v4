/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GLSync;

public final class ARBSync {
    final public static int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    final public static int GL_OBJECT_TYPE = 37138;
    final public static int GL_SYNC_CONDITION = 37139;
    final public static int GL_SYNC_STATUS = 37140;
    final public static int GL_SYNC_FLAGS = 37141;
    final public static int GL_SYNC_FENCE = 37142;
    final public static int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    final public static int GL_UNSIGNALED = 37144;
    final public static int GL_SIGNALED = 37145;
    final public static int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    final public static long GL_TIMEOUT_IGNORED = -1L;
    final public static int GL_ALREADY_SIGNALED = 37146;
    final public static int GL_TIMEOUT_EXPIRED = 37147;
    final public static int GL_CONDITION_SATISFIED = 37148;
    final public static int GL_WAIT_FAILED = 37149;

    private ARBSync() {
    }

    public static GLSync glFenceSync(int condition, int flags) {
        return GL32.glFenceSync(condition, flags);
    }

    public static boolean glIsSync(GLSync sync) {
        return GL32.glIsSync(sync);
    }

    public static void glDeleteSync(GLSync sync) {
        GL32.glDeleteSync(sync);
    }

    public static int glClientWaitSync(GLSync sync, int flags, long timeout) {
        return GL32.glClientWaitSync(sync, flags, timeout);
    }

    public static void glWaitSync(GLSync sync, int flags, long timeout) {
        GL32.glWaitSync(sync, flags, timeout);
    }

    public static void glGetInteger64(int pname, LongBuffer params) {
        GL32.glGetInteger64(pname, params);
    }

    public static long glGetInteger64(int pname) {
        return GL32.glGetInteger64(pname);
    }

    public static void glGetSync(GLSync sync, int pname, IntBuffer length, IntBuffer values) {
        GL32.glGetSync(sync, pname, length, values);
    }

    @Deprecated
    public static int glGetSync(GLSync sync, int pname) {
        return GL32.glGetSynci(sync, pname);
    }

    public static int glGetSynci(GLSync sync, int pname) {
        return GL32.glGetSynci(sync, pname);
    }
}

