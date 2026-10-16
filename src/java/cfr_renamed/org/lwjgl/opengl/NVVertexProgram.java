/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.NVProgram;
import org.lwjgl.opengl.StateTracker;

public final class NVVertexProgram
extends NVProgram {
    final public static int GL_VERTEX_PROGRAM_NV = 34336;
    final public static int GL_VERTEX_PROGRAM_POINT_SIZE_NV = 34370;
    final public static int GL_VERTEX_PROGRAM_TWO_SIDE_NV = 34371;
    final public static int GL_VERTEX_STATE_PROGRAM_NV = 34337;
    final public static int GL_ATTRIB_ARRAY_SIZE_NV = 34339;
    final public static int GL_ATTRIB_ARRAY_STRIDE_NV = 34340;
    final public static int GL_ATTRIB_ARRAY_TYPE_NV = 34341;
    final public static int GL_CURRENT_ATTRIB_NV = 34342;
    final public static int GL_PROGRAM_PARAMETER_NV = 34372;
    final public static int GL_ATTRIB_ARRAY_POINTER_NV = 34373;
    final public static int GL_TRACK_MATRIX_NV = 34376;
    final public static int GL_TRACK_MATRIX_TRANSFORM_NV = 34377;
    final public static int GL_MAX_TRACK_MATRIX_STACK_DEPTH_NV = 34350;
    final public static int GL_MAX_TRACK_MATRICES_NV = 34351;
    final public static int GL_CURRENT_MATRIX_STACK_DEPTH_NV = 34368;
    final public static int GL_CURRENT_MATRIX_NV = 34369;
    final public static int GL_VERTEX_PROGRAM_BINDING_NV = 34378;
    final public static int GL_MODELVIEW_PROJECTION_NV = 34345;
    final public static int GL_MATRIX0_NV = 34352;
    final public static int GL_MATRIX1_NV = 34353;
    final public static int GL_MATRIX2_NV = 34354;
    final public static int GL_MATRIX3_NV = 34355;
    final public static int GL_MATRIX4_NV = 34356;
    final public static int GL_MATRIX5_NV = 34357;
    final public static int GL_MATRIX6_NV = 34358;
    final public static int GL_MATRIX7_NV = 34359;
    final public static int GL_IDENTITY_NV = 34346;
    final public static int GL_INVERSE_NV = 34347;
    final public static int GL_TRANSPOSE_NV = 34348;
    final public static int GL_INVERSE_TRANSPOSE_NV = 34349;
    final public static int GL_VERTEX_ATTRIB_ARRAY0_NV = 34384;
    final public static int GL_VERTEX_ATTRIB_ARRAY1_NV = 34385;
    final public static int GL_VERTEX_ATTRIB_ARRAY2_NV = 34386;
    final public static int GL_VERTEX_ATTRIB_ARRAY3_NV = 34387;
    final public static int GL_VERTEX_ATTRIB_ARRAY4_NV = 34388;
    final public static int GL_VERTEX_ATTRIB_ARRAY5_NV = 34389;
    final public static int GL_VERTEX_ATTRIB_ARRAY6_NV = 34390;
    final public static int GL_VERTEX_ATTRIB_ARRAY7_NV = 34391;
    final public static int GL_VERTEX_ATTRIB_ARRAY8_NV = 34392;
    final public static int GL_VERTEX_ATTRIB_ARRAY9_NV = 34393;
    final public static int GL_VERTEX_ATTRIB_ARRAY10_NV = 34394;
    final public static int GL_VERTEX_ATTRIB_ARRAY11_NV = 34395;
    final public static int GL_VERTEX_ATTRIB_ARRAY12_NV = 34396;
    final public static int GL_VERTEX_ATTRIB_ARRAY13_NV = 34397;
    final public static int GL_VERTEX_ATTRIB_ARRAY14_NV = 34398;
    final public static int GL_VERTEX_ATTRIB_ARRAY15_NV = 34399;
    final public static int GL_MAP1_VERTEX_ATTRIB0_4_NV = 34400;
    final public static int GL_MAP1_VERTEX_ATTRIB1_4_NV = 34401;
    final public static int GL_MAP1_VERTEX_ATTRIB2_4_NV = 34402;
    final public static int GL_MAP1_VERTEX_ATTRIB3_4_NV = 34403;
    final public static int GL_MAP1_VERTEX_ATTRIB4_4_NV = 34404;
    final public static int GL_MAP1_VERTEX_ATTRIB5_4_NV = 34405;
    final public static int GL_MAP1_VERTEX_ATTRIB6_4_NV = 34406;
    final public static int GL_MAP1_VERTEX_ATTRIB7_4_NV = 34407;
    final public static int GL_MAP1_VERTEX_ATTRIB8_4_NV = 34408;
    final public static int GL_MAP1_VERTEX_ATTRIB9_4_NV = 34409;
    final public static int GL_MAP1_VERTEX_ATTRIB10_4_NV = 34410;
    final public static int GL_MAP1_VERTEX_ATTRIB11_4_NV = 34411;
    final public static int GL_MAP1_VERTEX_ATTRIB12_4_NV = 34412;
    final public static int GL_MAP1_VERTEX_ATTRIB13_4_NV = 34413;
    final public static int GL_MAP1_VERTEX_ATTRIB14_4_NV = 34414;
    final public static int GL_MAP1_VERTEX_ATTRIB15_4_NV = 34415;
    final public static int GL_MAP2_VERTEX_ATTRIB0_4_NV = 34416;
    final public static int GL_MAP2_VERTEX_ATTRIB1_4_NV = 34417;
    final public static int GL_MAP2_VERTEX_ATTRIB2_4_NV = 34418;
    final public static int GL_MAP2_VERTEX_ATTRIB3_4_NV = 34419;
    final public static int GL_MAP2_VERTEX_ATTRIB4_4_NV = 34420;
    final public static int GL_MAP2_VERTEX_ATTRIB5_4_NV = 34421;
    final public static int GL_MAP2_VERTEX_ATTRIB6_4_NV = 34422;
    final public static int GL_MAP2_VERTEX_ATTRIB7_4_NV = 34423;
    final public static int GL_MAP2_VERTEX_ATTRIB8_4_NV = 34424;
    final public static int GL_MAP2_VERTEX_ATTRIB9_4_NV = 34425;
    final public static int GL_MAP2_VERTEX_ATTRIB10_4_NV = 34426;
    final public static int GL_MAP2_VERTEX_ATTRIB11_4_NV = 34427;
    final public static int GL_MAP2_VERTEX_ATTRIB12_4_NV = 34428;
    final public static int GL_MAP2_VERTEX_ATTRIB13_4_NV = 34429;
    final public static int GL_MAP2_VERTEX_ATTRIB14_4_NV = 34430;
    final public static int GL_MAP2_VERTEX_ATTRIB15_4_NV = 34431;

    private NVVertexProgram() {
    }

    public static void glExecuteProgramNV(int target, int id, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glExecuteProgramNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        NVVertexProgram.nglExecuteProgramNV(target, id, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglExecuteProgramNV(int var0, int var1, long var2, long var4);

    public static void glGetProgramParameterNV(int target, int index, int parameterName, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramParameterfvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        NVVertexProgram.nglGetProgramParameterfvNV(target, index, parameterName, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetProgramParameterfvNV(int var0, int var1, int var2, long var3, long var5);

    public static void glGetProgramParameterNV(int target, int index, int parameterName, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramParameterdvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        NVVertexProgram.nglGetProgramParameterdvNV(target, index, parameterName, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetProgramParameterdvNV(int var0, int var1, int var2, long var3, long var5);

    public static void glGetTrackMatrixNV(int target, int address, int parameterName, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetTrackMatrixivNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        NVVertexProgram.nglGetTrackMatrixivNV(target, address, parameterName, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetTrackMatrixivNV(int var0, int var1, int var2, long var3, long var5);

    public static void glGetVertexAttribNV(int index, int parameterName, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVertexAttribfvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        NVVertexProgram.nglGetVertexAttribfvNV(index, parameterName, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetVertexAttribfvNV(int var0, int var1, long var2, long var4);

    public static void glGetVertexAttribNV(int index, int parameterName, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVertexAttribdvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        NVVertexProgram.nglGetVertexAttribdvNV(index, parameterName, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetVertexAttribdvNV(int var0, int var1, long var2, long var4);

    public static void glGetVertexAttribNV(int index, int parameterName, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVertexAttribivNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        NVVertexProgram.nglGetVertexAttribivNV(index, parameterName, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetVertexAttribivNV(int var0, int var1, long var2, long var4);

    public static ByteBuffer glGetVertexAttribPointerNV(int index, int parameterName, long result_size) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetVertexAttribPointervNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        ByteBuffer __result = NVVertexProgram.nglGetVertexAttribPointervNV(index, parameterName, result_size, function_pointer);
        return LWJGLUtil.CHECKS && __result == null ? null : __result.order(ByteOrder.nativeOrder());
    }

    static native ByteBuffer nglGetVertexAttribPointervNV(int var0, int var1, long var2, long var4);

    public static void glProgramParameter4fNV(int target, int index, float x, float y, float z, float w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramParameter4fNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglProgramParameter4fNV(target, index, x, y, z, w, function_pointer);
    }

    static native void nglProgramParameter4fNV(int var0, int var1, float var2, float var3, float var4, float var5, long var6);

    public static void glProgramParameter4dNV(int target, int index, double x, double y, double z, double w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramParameter4dNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglProgramParameter4dNV(target, index, x, y, z, w, function_pointer);
    }

    static native void nglProgramParameter4dNV(int var0, int var1, double var2, double var4, double var6, double var8, long var10);

    public static void glProgramParameters4NV(int target, int index, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramParameters4fvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(params);
        NVVertexProgram.nglProgramParameters4fvNV(target, index, params.remaining() >> 2, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglProgramParameters4fvNV(int var0, int var1, int var2, long var3, long var5);

    public static void glProgramParameters4NV(int target, int index, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramParameters4dvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(params);
        NVVertexProgram.nglProgramParameters4dvNV(target, index, params.remaining() >> 2, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglProgramParameters4dvNV(int var0, int var1, int var2, long var3, long var5);

    public static void glTrackMatrixNV(int target, int address, int matrix, int transform) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glTrackMatrixNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglTrackMatrixNV(target, address, matrix, transform, function_pointer);
    }

    static native void nglTrackMatrixNV(int var0, int var1, int var2, int var3, long var4);

    public static void glVertexAttribPointerNV(int index, int size, int type, int stride, DoubleBuffer buffer) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureArrayVBOdisabled(caps);
        BufferChecks.checkDirect(buffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences((ContextCapabilities)caps).glVertexAttribPointer_buffer[index] = buffer;
        }
        NVVertexProgram.nglVertexAttribPointerNV(index, size, type, stride, MemoryUtil.getAddress(buffer), function_pointer);
    }

    public static void glVertexAttribPointerNV(int index, int size, int type, int stride, FloatBuffer buffer) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureArrayVBOdisabled(caps);
        BufferChecks.checkDirect(buffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences((ContextCapabilities)caps).glVertexAttribPointer_buffer[index] = buffer;
        }
        NVVertexProgram.nglVertexAttribPointerNV(index, size, type, stride, MemoryUtil.getAddress(buffer), function_pointer);
    }

    public static void glVertexAttribPointerNV(int index, int size, int type, int stride, ByteBuffer buffer) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureArrayVBOdisabled(caps);
        BufferChecks.checkDirect(buffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences((ContextCapabilities)caps).glVertexAttribPointer_buffer[index] = buffer;
        }
        NVVertexProgram.nglVertexAttribPointerNV(index, size, type, stride, MemoryUtil.getAddress(buffer), function_pointer);
    }

    public static void glVertexAttribPointerNV(int index, int size, int type, int stride, IntBuffer buffer) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureArrayVBOdisabled(caps);
        BufferChecks.checkDirect(buffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences((ContextCapabilities)caps).glVertexAttribPointer_buffer[index] = buffer;
        }
        NVVertexProgram.nglVertexAttribPointerNV(index, size, type, stride, MemoryUtil.getAddress(buffer), function_pointer);
    }

    public static void glVertexAttribPointerNV(int index, int size, int type, int stride, ShortBuffer buffer) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureArrayVBOdisabled(caps);
        BufferChecks.checkDirect(buffer);
        if (LWJGLUtil.CHECKS) {
            StateTracker.getReferences((ContextCapabilities)caps).glVertexAttribPointer_buffer[index] = buffer;
        }
        NVVertexProgram.nglVertexAttribPointerNV(index, size, type, stride, MemoryUtil.getAddress(buffer), function_pointer);
    }

    static native void nglVertexAttribPointerNV(int var0, int var1, int var2, int var3, long var4, long var6);

    public static void glVertexAttribPointerNV(int index, int size, int type, int stride, long buffer_buffer_offset) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribPointerNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        GLChecks.ensureArrayVBOenabled(caps);
        NVVertexProgram.nglVertexAttribPointerNVBO(index, size, type, stride, buffer_buffer_offset, function_pointer);
    }

    static native void nglVertexAttribPointerNVBO(int var0, int var1, int var2, int var3, long var4, long var6);

    public static void glVertexAttrib1sNV(int index, short x) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib1sNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib1sNV(index, x, function_pointer);
    }

    static native void nglVertexAttrib1sNV(int var0, short var1, long var2);

    public static void glVertexAttrib1fNV(int index, float x) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib1fNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib1fNV(index, x, function_pointer);
    }

    static native void nglVertexAttrib1fNV(int var0, float var1, long var2);

    public static void glVertexAttrib1dNV(int index, double x) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib1dNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib1dNV(index, x, function_pointer);
    }

    static native void nglVertexAttrib1dNV(int var0, double var1, long var3);

    public static void glVertexAttrib2sNV(int index, short x, short y) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib2sNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib2sNV(index, x, y, function_pointer);
    }

    static native void nglVertexAttrib2sNV(int var0, short var1, short var2, long var3);

    public static void glVertexAttrib2fNV(int index, float x, float y) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib2fNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib2fNV(index, x, y, function_pointer);
    }

    static native void nglVertexAttrib2fNV(int var0, float var1, float var2, long var3);

    public static void glVertexAttrib2dNV(int index, double x, double y) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib2dNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib2dNV(index, x, y, function_pointer);
    }

    static native void nglVertexAttrib2dNV(int var0, double var1, double var3, long var5);

    public static void glVertexAttrib3sNV(int index, short x, short y, short z) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib3sNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib3sNV(index, x, y, z, function_pointer);
    }

    static native void nglVertexAttrib3sNV(int var0, short var1, short var2, short var3, long var4);

    public static void glVertexAttrib3fNV(int index, float x, float y, float z) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib3fNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib3fNV(index, x, y, z, function_pointer);
    }

    static native void nglVertexAttrib3fNV(int var0, float var1, float var2, float var3, long var4);

    public static void glVertexAttrib3dNV(int index, double x, double y, double z) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib3dNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib3dNV(index, x, y, z, function_pointer);
    }

    static native void nglVertexAttrib3dNV(int var0, double var1, double var3, double var5, long var7);

    public static void glVertexAttrib4sNV(int index, short x, short y, short z, short w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib4sNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib4sNV(index, x, y, z, w, function_pointer);
    }

    static native void nglVertexAttrib4sNV(int var0, short var1, short var2, short var3, short var4, long var5);

    public static void glVertexAttrib4fNV(int index, float x, float y, float z, float w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib4fNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib4fNV(index, x, y, z, w, function_pointer);
    }

    static native void nglVertexAttrib4fNV(int var0, float var1, float var2, float var3, float var4, long var5);

    public static void glVertexAttrib4dNV(int index, double x, double y, double z, double w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib4dNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib4dNV(index, x, y, z, w, function_pointer);
    }

    static native void nglVertexAttrib4dNV(int var0, double var1, double var3, double var5, double var7, long var9);

    public static void glVertexAttrib4ubNV(int index, byte x, byte y, byte z, byte w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttrib4ubNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        NVVertexProgram.nglVertexAttrib4ubNV(index, x, y, z, w, function_pointer);
    }

    static native void nglVertexAttrib4ubNV(int var0, byte var1, byte var2, byte var3, byte var4, long var5);

    public static void glVertexAttribs1NV(int index, ShortBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs1svNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs1svNV(index, v.remaining(), MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs1svNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs1NV(int index, FloatBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs1fvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs1fvNV(index, v.remaining(), MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs1fvNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs1NV(int index, DoubleBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs1dvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs1dvNV(index, v.remaining(), MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs1dvNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs2NV(int index, ShortBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs2svNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs2svNV(index, v.remaining() >> 1, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs2svNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs2NV(int index, FloatBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs2fvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs2fvNV(index, v.remaining() >> 1, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs2fvNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs2NV(int index, DoubleBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs2dvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs2dvNV(index, v.remaining() >> 1, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs2dvNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs3NV(int index, ShortBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs3svNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs3svNV(index, v.remaining() / 3, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs3svNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs3NV(int index, FloatBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs3fvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs3fvNV(index, v.remaining() / 3, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs3fvNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs3NV(int index, DoubleBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs3dvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs3dvNV(index, v.remaining() / 3, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs3dvNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs4NV(int index, ShortBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs4svNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs4svNV(index, v.remaining() >> 2, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs4svNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs4NV(int index, FloatBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs4fvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs4fvNV(index, v.remaining() >> 2, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs4fvNV(int var0, int var1, long var2, long var4);

    public static void glVertexAttribs4NV(int index, DoubleBuffer v) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glVertexAttribs4dvNV;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(v);
        NVVertexProgram.nglVertexAttribs4dvNV(index, v.remaining() >> 2, MemoryUtil.getAddress(v), function_pointer);
    }

    static native void nglVertexAttribs4dvNV(int var0, int var1, long var2, long var4);
}

