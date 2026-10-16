/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.APIUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public class ARBProgram {
    final public static int GL_PROGRAM_FORMAT_ASCII_ARB = 34933;
    final public static int GL_PROGRAM_LENGTH_ARB = 34343;
    final public static int GL_PROGRAM_FORMAT_ARB = 34934;
    final public static int GL_PROGRAM_BINDING_ARB = 34423;
    final public static int GL_PROGRAM_INSTRUCTIONS_ARB = 34976;
    final public static int GL_MAX_PROGRAM_INSTRUCTIONS_ARB = 34977;
    final public static int GL_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34978;
    final public static int GL_MAX_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34979;
    final public static int GL_PROGRAM_TEMPORARIES_ARB = 34980;
    final public static int GL_MAX_PROGRAM_TEMPORARIES_ARB = 34981;
    final public static int GL_PROGRAM_NATIVE_TEMPORARIES_ARB = 34982;
    final public static int GL_MAX_PROGRAM_NATIVE_TEMPORARIES_ARB = 34983;
    final public static int GL_PROGRAM_PARAMETERS_ARB = 34984;
    final public static int GL_MAX_PROGRAM_PARAMETERS_ARB = 34985;
    final public static int GL_PROGRAM_NATIVE_PARAMETERS_ARB = 34986;
    final public static int GL_MAX_PROGRAM_NATIVE_PARAMETERS_ARB = 34987;
    final public static int GL_PROGRAM_ATTRIBS_ARB = 34988;
    final public static int GL_MAX_PROGRAM_ATTRIBS_ARB = 34989;
    final public static int GL_PROGRAM_NATIVE_ATTRIBS_ARB = 34990;
    final public static int GL_MAX_PROGRAM_NATIVE_ATTRIBS_ARB = 34991;
    final public static int GL_MAX_PROGRAM_LOCAL_PARAMETERS_ARB = 34996;
    final public static int GL_MAX_PROGRAM_ENV_PARAMETERS_ARB = 34997;
    final public static int GL_PROGRAM_UNDER_NATIVE_LIMITS_ARB = 34998;
    final public static int GL_PROGRAM_STRING_ARB = 34344;
    final public static int GL_PROGRAM_ERROR_POSITION_ARB = 34379;
    final public static int GL_CURRENT_MATRIX_ARB = 34369;
    final public static int GL_TRANSPOSE_CURRENT_MATRIX_ARB = 34999;
    final public static int GL_CURRENT_MATRIX_STACK_DEPTH_ARB = 34368;
    final public static int GL_MAX_PROGRAM_MATRICES_ARB = 34351;
    final public static int GL_MAX_PROGRAM_MATRIX_STACK_DEPTH_ARB = 34350;
    final public static int GL_PROGRAM_ERROR_STRING_ARB = 34932;
    final public static int GL_MATRIX0_ARB = 35008;
    final public static int GL_MATRIX1_ARB = 35009;
    final public static int GL_MATRIX2_ARB = 35010;
    final public static int GL_MATRIX3_ARB = 35011;
    final public static int GL_MATRIX4_ARB = 35012;
    final public static int GL_MATRIX5_ARB = 35013;
    final public static int GL_MATRIX6_ARB = 35014;
    final public static int GL_MATRIX7_ARB = 35015;
    final public static int GL_MATRIX8_ARB = 35016;
    final public static int GL_MATRIX9_ARB = 35017;
    final public static int GL_MATRIX10_ARB = 35018;
    final public static int GL_MATRIX11_ARB = 35019;
    final public static int GL_MATRIX12_ARB = 35020;
    final public static int GL_MATRIX13_ARB = 35021;
    final public static int GL_MATRIX14_ARB = 35022;
    final public static int GL_MATRIX15_ARB = 35023;
    final public static int GL_MATRIX16_ARB = 35024;
    final public static int GL_MATRIX17_ARB = 35025;
    final public static int GL_MATRIX18_ARB = 35026;
    final public static int GL_MATRIX19_ARB = 35027;
    final public static int GL_MATRIX20_ARB = 35028;
    final public static int GL_MATRIX21_ARB = 35029;
    final public static int GL_MATRIX22_ARB = 35030;
    final public static int GL_MATRIX23_ARB = 35031;
    final public static int GL_MATRIX24_ARB = 35032;
    final public static int GL_MATRIX25_ARB = 35033;
    final public static int GL_MATRIX26_ARB = 35034;
    final public static int GL_MATRIX27_ARB = 35035;
    final public static int GL_MATRIX28_ARB = 35036;
    final public static int GL_MATRIX29_ARB = 35037;
    final public static int GL_MATRIX30_ARB = 35038;
    final public static int GL_MATRIX31_ARB = 35039;

    public static void glProgramStringARB(int target, int format, ByteBuffer string) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramStringARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(string);
        ARBProgram.nglProgramStringARB(target, format, string.remaining(), MemoryUtil.getAddress(string), function_pointer);
    }

    static native void nglProgramStringARB(int var0, int var1, int var2, long var3, long var5);

    public static void glProgramStringARB(int target, int format, CharSequence string) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramStringARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBProgram.nglProgramStringARB(target, format, string.length(), APIUtil.getBuffer(caps, string), function_pointer);
    }

    public static void glBindProgramARB(int target, int program) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBindProgramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBProgram.nglBindProgramARB(target, program, function_pointer);
    }

    static native void nglBindProgramARB(int var0, int var1, long var2);

    public static void glDeleteProgramsARB(IntBuffer programs) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glDeleteProgramsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(programs);
        ARBProgram.nglDeleteProgramsARB(programs.remaining(), MemoryUtil.getAddress(programs), function_pointer);
    }

    static native void nglDeleteProgramsARB(int var0, long var1, long var3);

    public static void glDeleteProgramsARB(int program) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glDeleteProgramsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBProgram.nglDeleteProgramsARB(1, APIUtil.getInt(caps, program), function_pointer);
    }

    public static void glGenProgramsARB(IntBuffer programs) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGenProgramsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(programs);
        ARBProgram.nglGenProgramsARB(programs.remaining(), MemoryUtil.getAddress(programs), function_pointer);
    }

    static native void nglGenProgramsARB(int var0, long var1, long var3);

    public static int glGenProgramsARB() {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGenProgramsARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        IntBuffer programs = APIUtil.getBufferInt(caps);
        ARBProgram.nglGenProgramsARB(1, MemoryUtil.getAddress(programs), function_pointer);
        return programs.get(0);
    }

    public static void glProgramEnvParameter4fARB(int target, int index, float x, float y, float z, float w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramEnvParameter4fARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBProgram.nglProgramEnvParameter4fARB(target, index, x, y, z, w, function_pointer);
    }

    static native void nglProgramEnvParameter4fARB(int var0, int var1, float var2, float var3, float var4, float var5, long var6);

    public static void glProgramEnvParameter4dARB(int target, int index, double x, double y, double z, double w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramEnvParameter4dARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBProgram.nglProgramEnvParameter4dARB(target, index, x, y, z, w, function_pointer);
    }

    static native void nglProgramEnvParameter4dARB(int var0, int var1, double var2, double var4, double var6, double var8, long var10);

    public static void glProgramEnvParameter4ARB(int target, int index, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramEnvParameter4fvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglProgramEnvParameter4fvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglProgramEnvParameter4fvARB(int var0, int var1, long var2, long var4);

    public static void glProgramEnvParameter4ARB(int target, int index, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramEnvParameter4dvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglProgramEnvParameter4dvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglProgramEnvParameter4dvARB(int var0, int var1, long var2, long var4);

    public static void glProgramLocalParameter4fARB(int target, int index, float x, float y, float z, float w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramLocalParameter4fARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBProgram.nglProgramLocalParameter4fARB(target, index, x, y, z, w, function_pointer);
    }

    static native void nglProgramLocalParameter4fARB(int var0, int var1, float var2, float var3, float var4, float var5, long var6);

    public static void glProgramLocalParameter4dARB(int target, int index, double x, double y, double z, double w) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramLocalParameter4dARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        ARBProgram.nglProgramLocalParameter4dARB(target, index, x, y, z, w, function_pointer);
    }

    static native void nglProgramLocalParameter4dARB(int var0, int var1, double var2, double var4, double var6, double var8, long var10);

    public static void glProgramLocalParameter4ARB(int target, int index, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramLocalParameter4fvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglProgramLocalParameter4fvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglProgramLocalParameter4fvARB(int var0, int var1, long var2, long var4);

    public static void glProgramLocalParameter4ARB(int target, int index, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glProgramLocalParameter4dvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglProgramLocalParameter4dvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglProgramLocalParameter4dvARB(int var0, int var1, long var2, long var4);

    public static void glGetProgramEnvParameterARB(int target, int index, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramEnvParameterfvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglGetProgramEnvParameterfvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetProgramEnvParameterfvARB(int var0, int var1, long var2, long var4);

    public static void glGetProgramEnvParameterARB(int target, int index, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramEnvParameterdvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglGetProgramEnvParameterdvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetProgramEnvParameterdvARB(int var0, int var1, long var2, long var4);

    public static void glGetProgramLocalParameterARB(int target, int index, FloatBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramLocalParameterfvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglGetProgramLocalParameterfvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetProgramLocalParameterfvARB(int var0, int var1, long var2, long var4);

    public static void glGetProgramLocalParameterARB(int target, int index, DoubleBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramLocalParameterdvARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglGetProgramLocalParameterdvARB(target, index, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetProgramLocalParameterdvARB(int var0, int var1, long var2, long var4);

    public static void glGetProgramARB(int target, int parameterName, IntBuffer params) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramivARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(params, 4);
        ARBProgram.nglGetProgramivARB(target, parameterName, MemoryUtil.getAddress(params), function_pointer);
    }

    static native void nglGetProgramivARB(int var0, int var1, long var2, long var4);

    @Deprecated
    public static int glGetProgramARB(int target, int parameterName) {
        return ARBProgram.glGetProgramiARB(target, parameterName);
    }

    public static int glGetProgramiARB(int target, int parameterName) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramivARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        IntBuffer params = APIUtil.getBufferInt(caps);
        ARBProgram.nglGetProgramivARB(target, parameterName, MemoryUtil.getAddress(params), function_pointer);
        return params.get(0);
    }

    public static void glGetProgramStringARB(int target, int parameterName, ByteBuffer paramString) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramStringARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkDirect(paramString);
        ARBProgram.nglGetProgramStringARB(target, parameterName, MemoryUtil.getAddress(paramString), function_pointer);
    }

    static native void nglGetProgramStringARB(int var0, int var1, long var2, long var4);

    public static String glGetProgramStringARB(int target, int parameterName) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGetProgramStringARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        int programLength = ARBProgram.glGetProgramiARB(target, 34343);
        ByteBuffer paramString = APIUtil.getBufferByte(caps, programLength);
        ARBProgram.nglGetProgramStringARB(target, parameterName, MemoryUtil.getAddress(paramString), function_pointer);
        paramString.limit(programLength);
        return APIUtil.getString(caps, paramString);
    }

    public static boolean glIsProgramARB(int program) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glIsProgramARB;
        BufferChecks.checkFunctionAddress(function_pointer);
        boolean __result = ARBProgram.nglIsProgramARB(program, function_pointer);
        return __result;
    }

    static native boolean nglIsProgramARB(int var0, long var1);
}

