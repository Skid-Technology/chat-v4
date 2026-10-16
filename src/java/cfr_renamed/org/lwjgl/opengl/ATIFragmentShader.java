/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;

public final class ATIFragmentShader {
    final public static int GL_FRAGMENT_SHADER_ATI = 35104;
    final public static int GL_REG_0_ATI = 35105;
    final public static int GL_REG_1_ATI = 35106;
    final public static int GL_REG_2_ATI = 35107;
    final public static int GL_REG_3_ATI = 35108;
    final public static int GL_REG_4_ATI = 35109;
    final public static int GL_REG_5_ATI = 35110;
    final public static int GL_REG_6_ATI = 35111;
    final public static int GL_REG_7_ATI = 35112;
    final public static int GL_REG_8_ATI = 35113;
    final public static int GL_REG_9_ATI = 35114;
    final public static int GL_REG_10_ATI = 35115;
    final public static int GL_REG_11_ATI = 35116;
    final public static int GL_REG_12_ATI = 35117;
    final public static int GL_REG_13_ATI = 35118;
    final public static int GL_REG_14_ATI = 35119;
    final public static int GL_REG_15_ATI = 35120;
    final public static int GL_REG_16_ATI = 35121;
    final public static int GL_REG_17_ATI = 35122;
    final public static int GL_REG_18_ATI = 35123;
    final public static int GL_REG_19_ATI = 35124;
    final public static int GL_REG_20_ATI = 35125;
    final public static int GL_REG_21_ATI = 35126;
    final public static int GL_REG_22_ATI = 35127;
    final public static int GL_REG_23_ATI = 35128;
    final public static int GL_REG_24_ATI = 35129;
    final public static int GL_REG_25_ATI = 35130;
    final public static int GL_REG_26_ATI = 35131;
    final public static int GL_REG_27_ATI = 35132;
    final public static int GL_REG_28_ATI = 35133;
    final public static int GL_REG_29_ATI = 35134;
    final public static int GL_REG_30_ATI = 35135;
    final public static int GL_REG_31_ATI = 35136;
    final public static int GL_CON_0_ATI = 35137;
    final public static int GL_CON_1_ATI = 35138;
    final public static int GL_CON_2_ATI = 35139;
    final public static int GL_CON_3_ATI = 35140;
    final public static int GL_CON_4_ATI = 35141;
    final public static int GL_CON_5_ATI = 35142;
    final public static int GL_CON_6_ATI = 35143;
    final public static int GL_CON_7_ATI = 35144;
    final public static int GL_CON_8_ATI = 35145;
    final public static int GL_CON_9_ATI = 35146;
    final public static int GL_CON_10_ATI = 35147;
    final public static int GL_CON_11_ATI = 35148;
    final public static int GL_CON_12_ATI = 35149;
    final public static int GL_CON_13_ATI = 35150;
    final public static int GL_CON_14_ATI = 35151;
    final public static int GL_CON_15_ATI = 35152;
    final public static int GL_CON_16_ATI = 35153;
    final public static int GL_CON_17_ATI = 35154;
    final public static int GL_CON_18_ATI = 35155;
    final public static int GL_CON_19_ATI = 35156;
    final public static int GL_CON_20_ATI = 35157;
    final public static int GL_CON_21_ATI = 35158;
    final public static int GL_CON_22_ATI = 35159;
    final public static int GL_CON_23_ATI = 35160;
    final public static int GL_CON_24_ATI = 35161;
    final public static int GL_CON_25_ATI = 35162;
    final public static int GL_CON_26_ATI = 35163;
    final public static int GL_CON_27_ATI = 35164;
    final public static int GL_CON_28_ATI = 35165;
    final public static int GL_CON_29_ATI = 35166;
    final public static int GL_CON_30_ATI = 35167;
    final public static int GL_CON_31_ATI = 35168;
    final public static int GL_MOV_ATI = 35169;
    final public static int GL_ADD_ATI = 35171;
    final public static int GL_MUL_ATI = 35172;
    final public static int GL_SUB_ATI = 35173;
    final public static int GL_DOT3_ATI = 35174;
    final public static int GL_DOT4_ATI = 35175;
    final public static int GL_MAD_ATI = 35176;
    final public static int GL_LERP_ATI = 35177;
    final public static int GL_CND_ATI = 35178;
    final public static int GL_CND0_ATI = 35179;
    final public static int GL_DOT2_ADD_ATI = 35180;
    final public static int GL_SECONDARY_INTERPOLATOR_ATI = 35181;
    final public static int GL_NUM_FRAGMENT_REGISTERS_ATI = 35182;
    final public static int GL_NUM_FRAGMENT_CONSTANTS_ATI = 35183;
    final public static int GL_NUM_PASSES_ATI = 35184;
    final public static int GL_NUM_INSTRUCTIONS_PER_PASS_ATI = 35185;
    final public static int GL_NUM_INSTRUCTIONS_TOTAL_ATI = 35186;
    final public static int GL_NUM_INPUT_INTERPOLATOR_COMPONENTS_ATI = 35187;
    final public static int GL_NUM_LOOPBACK_COMPONENTS_ATI = 35188;
    final public static int GL_COLOR_ALPHA_PAIRING_ATI = 35189;
    final public static int GL_SWIZZLE_STR_ATI = 35190;
    final public static int GL_SWIZZLE_STQ_ATI = 35191;
    final public static int GL_SWIZZLE_STR_DR_ATI = 35192;
    final public static int GL_SWIZZLE_STQ_DQ_ATI = 35193;
    final public static int GL_SWIZZLE_STRQ_ATI = 35194;
    final public static int GL_SWIZZLE_STRQ_DQ_ATI = 35195;
    final public static int GL_RED_BIT_ATI = 1;
    final public static int GL_GREEN_BIT_ATI = 2;
    final public static int GL_BLUE_BIT_ATI = 4;
    final public static int GL_2X_BIT_ATI = 1;
    final public static int GL_4X_BIT_ATI = 2;
    final public static int GL_8X_BIT_ATI = 4;
    final public static int GL_HALF_BIT_ATI = 8;
    final public static int GL_QUARTER_BIT_ATI = 16;
    final public static int GL_EIGHTH_BIT_ATI = 32;
    final public static int GL_SATURATE_BIT_ATI = 64;
    final public static int GL_COMP_BIT_ATI = 2;
    final public static int GL_NEGATE_BIT_ATI = 4;
    final public static int GL_BIAS_BIT_ATI = 8;

    private ATIFragmentShader() {
    }

    public static int glGenFragmentShadersATI(int range) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glGenFragmentShadersATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        int __result = ATIFragmentShader.nglGenFragmentShadersATI(range, function_pointer);
        return __result;
    }

    static native int nglGenFragmentShadersATI(int var0, long var1);

    public static void glBindFragmentShaderATI(int id) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBindFragmentShaderATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglBindFragmentShaderATI(id, function_pointer);
    }

    static native void nglBindFragmentShaderATI(int var0, long var1);

    public static void glDeleteFragmentShaderATI(int id) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glDeleteFragmentShaderATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglDeleteFragmentShaderATI(id, function_pointer);
    }

    static native void nglDeleteFragmentShaderATI(int var0, long var1);

    public static void glBeginFragmentShaderATI() {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glBeginFragmentShaderATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglBeginFragmentShaderATI(function_pointer);
    }

    static native void nglBeginFragmentShaderATI(long var0);

    public static void glEndFragmentShaderATI() {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glEndFragmentShaderATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglEndFragmentShaderATI(function_pointer);
    }

    static native void nglEndFragmentShaderATI(long var0);

    public static void glPassTexCoordATI(int dst, int coord, int swizzle) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glPassTexCoordATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglPassTexCoordATI(dst, coord, swizzle, function_pointer);
    }

    static native void nglPassTexCoordATI(int var0, int var1, int var2, long var3);

    public static void glSampleMapATI(int dst, int interp, int swizzle) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glSampleMapATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglSampleMapATI(dst, interp, swizzle, function_pointer);
    }

    static native void nglSampleMapATI(int var0, int var1, int var2, long var3);

    public static void glColorFragmentOp1ATI(int op, int dst, int dstMask, int dstMod, int arg1, int arg1Rep, int arg1Mod) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glColorFragmentOp1ATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglColorFragmentOp1ATI(op, dst, dstMask, dstMod, arg1, arg1Rep, arg1Mod, function_pointer);
    }

    static native void nglColorFragmentOp1ATI(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glColorFragmentOp2ATI(int op, int dst, int dstMask, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glColorFragmentOp2ATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglColorFragmentOp2ATI(op, dst, dstMask, dstMod, arg1, arg1Rep, arg1Mod, arg2, arg2Rep, arg2Mod, function_pointer);
    }

    static native void nglColorFragmentOp2ATI(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

    public static void glColorFragmentOp3ATI(int op, int dst, int dstMask, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod, int arg3, int arg3Rep, int arg3Mod) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glColorFragmentOp3ATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglColorFragmentOp3ATI(op, dst, dstMask, dstMod, arg1, arg1Rep, arg1Mod, arg2, arg2Rep, arg2Mod, arg3, arg3Rep, arg3Mod, function_pointer);
    }

    static native void nglColorFragmentOp3ATI(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, long var13);

    public static void glAlphaFragmentOp1ATI(int op, int dst, int dstMod, int arg1, int arg1Rep, int arg1Mod) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glAlphaFragmentOp1ATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglAlphaFragmentOp1ATI(op, dst, dstMod, arg1, arg1Rep, arg1Mod, function_pointer);
    }

    static native void nglAlphaFragmentOp1ATI(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

    public static void glAlphaFragmentOp2ATI(int op, int dst, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glAlphaFragmentOp2ATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglAlphaFragmentOp2ATI(op, dst, dstMod, arg1, arg1Rep, arg1Mod, arg2, arg2Rep, arg2Mod, function_pointer);
    }

    static native void nglAlphaFragmentOp2ATI(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glAlphaFragmentOp3ATI(int op, int dst, int dstMod, int arg1, int arg1Rep, int arg1Mod, int arg2, int arg2Rep, int arg2Mod, int arg3, int arg3Rep, int arg3Mod) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glAlphaFragmentOp3ATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        ATIFragmentShader.nglAlphaFragmentOp3ATI(op, dst, dstMod, arg1, arg1Rep, arg1Mod, arg2, arg2Rep, arg2Mod, arg3, arg3Rep, arg3Mod, function_pointer);
    }

    static native void nglAlphaFragmentOp3ATI(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, long var12);

    public static void glSetFragmentShaderConstantATI(int dst, FloatBuffer pfValue) {
        ContextCapabilities caps = GLContext.getCapabilities();
        long function_pointer = caps.glSetFragmentShaderConstantATI;
        BufferChecks.checkFunctionAddress(function_pointer);
        BufferChecks.checkBuffer(pfValue, 4);
        ATIFragmentShader.nglSetFragmentShaderConstantATI(dst, MemoryUtil.getAddress(pfValue), function_pointer);
    }

    static native void nglSetFragmentShaderConstantATI(int var0, long var1, long var3);
}

