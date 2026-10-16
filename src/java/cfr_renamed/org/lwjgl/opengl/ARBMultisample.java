/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class ARBMultisample {
    final public static int GL_MULTISAMPLE_ARB = 32925;
    final public static int GL_SAMPLE_ALPHA_TO_COVERAGE_ARB = 32926;
    final public static int GL_SAMPLE_ALPHA_TO_ONE_ARB = 32927;
    final public static int GL_SAMPLE_COVERAGE_ARB = 32928;
    final public static int GL_SAMPLE_BUFFERS_ARB = 32936;
    final public static int GL_SAMPLES_ARB = 32937;
    final public static int GL_SAMPLE_COVERAGE_VALUE_ARB = 32938;
    final public static int GL_SAMPLE_COVERAGE_INVERT_ARB = 32939;
    final public static int GL_MULTISAMPLE_BIT_ARB = 0x20000000;

    private ARBMultisample() {
    }

    public static void glSampleCoverageARB(float value, boolean invert) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glSampleCoverageARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBMultisample.nglSampleCoverageARB(value, invert, function_pointer);
    }

    static native void nglSampleCoverageARB(float var0, boolean var1, long var2);
}

