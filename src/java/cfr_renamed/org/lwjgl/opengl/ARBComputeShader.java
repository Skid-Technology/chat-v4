/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL43;

public final class ARBComputeShader {
    final public static int GL_COMPUTE_SHADER = 37305;
    final public static int GL_MAX_COMPUTE_UNIFORM_BLOCKS = 37307;
    final public static int GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = 37308;
    final public static int GL_MAX_COMPUTE_IMAGE_UNIFORMS = 37309;
    final public static int GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = 33378;
    final public static int GL_MAX_COMPUTE_UNIFORM_COMPONENTS = 33379;
    final public static int GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = 33380;
    final public static int GL_MAX_COMPUTE_ATOMIC_COUNTERS = 33381;
    final public static int GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = 33382;
    final public static int GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = 37099;
    final public static int GL_MAX_COMPUTE_WORK_GROUP_COUNT = 37310;
    final public static int GL_MAX_COMPUTE_WORK_GROUP_SIZE = 37311;
    final public static int GL_COMPUTE_WORK_GROUP_SIZE = 33383;
    final public static int GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER = 37100;
    final public static int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER = 37101;
    final public static int GL_DISPATCH_INDIRECT_BUFFER = 37102;
    final public static int GL_DISPATCH_INDIRECT_BUFFER_BINDING = 37103;
    final public static int GL_COMPUTE_SHADER_BIT = 32;

    private ARBComputeShader() {
    }

    public static void glDispatchCompute(int num_groups_x, int num_groups_y, int num_groups_z) {
        GL43.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z);
    }

    public static void glDispatchComputeIndirect(long indirect) {
        GL43.glDispatchComputeIndirect(indirect);
    }
}

