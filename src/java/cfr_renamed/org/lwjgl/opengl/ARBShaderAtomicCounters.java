/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL42;

public final class ARBShaderAtomicCounters {
    final public static int GL_ATOMIC_COUNTER_BUFFER = 37568;
    final public static int GL_ATOMIC_COUNTER_BUFFER_BINDING = 37569;
    final public static int GL_ATOMIC_COUNTER_BUFFER_START = 37570;
    final public static int GL_ATOMIC_COUNTER_BUFFER_SIZE = 37571;
    final public static int GL_ATOMIC_COUNTER_BUFFER_DATA_SIZE = 37572;
    final public static int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTERS = 37573;
    final public static int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTER_INDICES = 37574;
    final public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_VERTEX_SHADER = 37575;
    final public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_CONTROL_SHADER = 37576;
    final public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_EVALUATION_SHADER = 37577;
    final public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_GEOMETRY_SHADER = 37578;
    final public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_FRAGMENT_SHADER = 37579;
    final public static int GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS = 37580;
    final public static int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS = 37581;
    final public static int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS = 37582;
    final public static int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS = 37583;
    final public static int GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS = 37584;
    final public static int GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS = 37585;
    final public static int GL_MAX_VERTEX_ATOMIC_COUNTERS = 37586;
    final public static int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS = 37587;
    final public static int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS = 37588;
    final public static int GL_MAX_GEOMETRY_ATOMIC_COUNTERS = 37589;
    final public static int GL_MAX_FRAGMENT_ATOMIC_COUNTERS = 37590;
    final public static int GL_MAX_COMBINED_ATOMIC_COUNTERS = 37591;
    final public static int GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE = 37592;
    final public static int GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS = 37596;
    final public static int GL_ACTIVE_ATOMIC_COUNTER_BUFFERS = 37593;
    final public static int GL_UNIFORM_ATOMIC_COUNTER_BUFFER_INDEX = 37594;
    final public static int GL_UNSIGNED_INT_ATOMIC_COUNTER = 37595;

    private ARBShaderAtomicCounters() {
    }

    public static void glGetActiveAtomicCounterBuffer(int program, int bufferIndex, int pname, IntBuffer params) {
        GL42.glGetActiveAtomicCounterBuffer(program, bufferIndex, pname, params);
    }

    public static int glGetActiveAtomicCounterBuffer(int program, int bufferIndex, int pname) {
        return GL42.glGetActiveAtomicCounterBuffer(program, bufferIndex, pname);
    }
}

