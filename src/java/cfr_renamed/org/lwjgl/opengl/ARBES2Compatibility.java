/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL41;

public final class ARBES2Compatibility {
    final public static int GL_SHADER_COMPILER = 36346;
    final public static int GL_NUM_SHADER_BINARY_FORMATS = 36345;
    final public static int GL_MAX_VERTEX_UNIFORM_VECTORS = 36347;
    final public static int GL_MAX_VARYING_VECTORS = 36348;
    final public static int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 36349;
    final public static int GL_IMPLEMENTATION_COLOR_READ_TYPE = 35738;
    final public static int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 35739;
    final public static int GL_FIXED = 5132;
    final public static int GL_LOW_FLOAT = 36336;
    final public static int GL_MEDIUM_FLOAT = 36337;
    final public static int GL_HIGH_FLOAT = 36338;
    final public static int GL_LOW_INT = 36339;
    final public static int GL_MEDIUM_INT = 36340;
    final public static int GL_HIGH_INT = 36341;
    final public static int GL_RGB565 = 36194;

    private ARBES2Compatibility() {
    }

    public static void glReleaseShaderCompiler() {
        GL41.glReleaseShaderCompiler();
    }

    public static void glShaderBinary(IntBuffer shaders, int binaryformat, ByteBuffer binary) {
        GL41.glShaderBinary(shaders, binaryformat, binary);
    }

    public static void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
        GL41.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
    }

    public static void glDepthRangef(float n, float f) {
        GL41.glDepthRangef(n, f);
    }

    public static void glClearDepthf(float d) {
        GL41.glClearDepthf(d);
    }
}

