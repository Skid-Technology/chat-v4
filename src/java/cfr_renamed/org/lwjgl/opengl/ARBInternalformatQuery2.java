/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.opengl.GL43;

public final class ARBInternalformatQuery2 {
    final public static int GL_TEXTURE_1D_ARRAY = 35864;
    final public static int GL_TEXTURE_2D_ARRAY = 35866;
    final public static int GL_TEXTURE_CUBE_MAP_ARRAY = 36873;
    final public static int GL_TEXTURE_RECTANGLE = 34037;
    final public static int GL_TEXTURE_BUFFER = 35882;
    final public static int GL_RENDERBUFFER = 36161;
    final public static int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    final public static int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    final public static int GL_NUM_SAMPLE_COUNTS = 37760;
    final public static int GL_INTERNALFORMAT_SUPPORTED = 33391;
    final public static int GL_INTERNALFORMAT_PREFERRED = 33392;
    final public static int GL_INTERNALFORMAT_RED_SIZE = 33393;
    final public static int GL_INTERNALFORMAT_GREEN_SIZE = 33394;
    final public static int GL_INTERNALFORMAT_BLUE_SIZE = 33395;
    final public static int GL_INTERNALFORMAT_ALPHA_SIZE = 33396;
    final public static int GL_INTERNALFORMAT_DEPTH_SIZE = 33397;
    final public static int GL_INTERNALFORMAT_STENCIL_SIZE = 33398;
    final public static int GL_INTERNALFORMAT_SHARED_SIZE = 33399;
    final public static int GL_INTERNALFORMAT_RED_TYPE = 33400;
    final public static int GL_INTERNALFORMAT_GREEN_TYPE = 33401;
    final public static int GL_INTERNALFORMAT_BLUE_TYPE = 33402;
    final public static int GL_INTERNALFORMAT_ALPHA_TYPE = 33403;
    final public static int GL_INTERNALFORMAT_DEPTH_TYPE = 33404;
    final public static int GL_INTERNALFORMAT_STENCIL_TYPE = 33405;
    final public static int GL_MAX_WIDTH = 33406;
    final public static int GL_MAX_HEIGHT = 33407;
    final public static int GL_MAX_DEPTH = 33408;
    final public static int GL_MAX_LAYERS = 33409;
    final public static int GL_MAX_COMBINED_DIMENSIONS = 33410;
    final public static int GL_COLOR_COMPONENTS = 33411;
    final public static int GL_DEPTH_COMPONENTS = 33412;
    final public static int GL_STENCIL_COMPONENTS = 33413;
    final public static int GL_COLOR_RENDERABLE = 33414;
    final public static int GL_DEPTH_RENDERABLE = 33415;
    final public static int GL_STENCIL_RENDERABLE = 33416;
    final public static int GL_FRAMEBUFFER_RENDERABLE = 33417;
    final public static int GL_FRAMEBUFFER_RENDERABLE_LAYERED = 33418;
    final public static int GL_FRAMEBUFFER_BLEND = 33419;
    final public static int GL_READ_PIXELS = 33420;
    final public static int GL_READ_PIXELS_FORMAT = 33421;
    final public static int GL_READ_PIXELS_TYPE = 33422;
    final public static int GL_TEXTURE_IMAGE_FORMAT = 33423;
    final public static int GL_TEXTURE_IMAGE_TYPE = 33424;
    final public static int GL_GET_TEXTURE_IMAGE_FORMAT = 33425;
    final public static int GL_GET_TEXTURE_IMAGE_TYPE = 33426;
    final public static int GL_MIPMAP = 33427;
    final public static int GL_MANUAL_GENERATE_MIPMAP = 33428;
    final public static int GL_AUTO_GENERATE_MIPMAP = 33429;
    final public static int GL_COLOR_ENCODING = 33430;
    final public static int GL_SRGB_READ = 33431;
    final public static int GL_SRGB_WRITE = 33432;
    final public static int GL_SRGB_DECODE_ARB = 33433;
    final public static int GL_FILTER = 33434;
    final public static int GL_VERTEX_TEXTURE = 33435;
    final public static int GL_TESS_CONTROL_TEXTURE = 33436;
    final public static int GL_TESS_EVALUATION_TEXTURE = 33437;
    final public static int GL_GEOMETRY_TEXTURE = 33438;
    final public static int GL_FRAGMENT_TEXTURE = 33439;
    final public static int GL_COMPUTE_TEXTURE = 33440;
    final public static int GL_TEXTURE_SHADOW = 33441;
    final public static int GL_TEXTURE_GATHER = 33442;
    final public static int GL_TEXTURE_GATHER_SHADOW = 33443;
    final public static int GL_SHADER_IMAGE_LOAD = 33444;
    final public static int GL_SHADER_IMAGE_STORE = 33445;
    final public static int GL_SHADER_IMAGE_ATOMIC = 33446;
    final public static int GL_IMAGE_TEXEL_SIZE = 33447;
    final public static int GL_IMAGE_COMPATIBILITY_CLASS = 33448;
    final public static int GL_IMAGE_PIXEL_FORMAT = 33449;
    final public static int GL_IMAGE_PIXEL_TYPE = 33450;
    final public static int GL_IMAGE_FORMAT_COMPATIBILITY_TYPE = 37063;
    final public static int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST = 33452;
    final public static int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST = 33453;
    final public static int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE = 33454;
    final public static int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE = 33455;
    final public static int GL_TEXTURE_COMPRESSED_BLOCK_WIDTH = 33457;
    final public static int GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT = 33458;
    final public static int GL_TEXTURE_COMPRESSED_BLOCK_SIZE = 33459;
    final public static int GL_CLEAR_BUFFER = 33460;
    final public static int GL_TEXTURE_VIEW = 33461;
    final public static int GL_VIEW_COMPATIBILITY_CLASS = 33462;
    final public static int GL_FULL_SUPPORT = 33463;
    final public static int GL_CAVEAT_SUPPORT = 33464;
    final public static int GL_IMAGE_CLASS_4_X_32 = 33465;
    final public static int GL_IMAGE_CLASS_2_X_32 = 33466;
    final public static int GL_IMAGE_CLASS_1_X_32 = 33467;
    final public static int GL_IMAGE_CLASS_4_X_16 = 33468;
    final public static int GL_IMAGE_CLASS_2_X_16 = 33469;
    final public static int GL_IMAGE_CLASS_1_X_16 = 33470;
    final public static int GL_IMAGE_CLASS_4_X_8 = 33471;
    final public static int GL_IMAGE_CLASS_2_X_8 = 33472;
    final public static int GL_IMAGE_CLASS_1_X_8 = 33473;
    final public static int GL_IMAGE_CLASS_11_11_10 = 33474;
    final public static int GL_IMAGE_CLASS_10_10_10_2 = 33475;
    final public static int GL_VIEW_CLASS_128_BITS = 33476;
    final public static int GL_VIEW_CLASS_96_BITS = 33477;
    final public static int GL_VIEW_CLASS_64_BITS = 33478;
    final public static int GL_VIEW_CLASS_48_BITS = 33479;
    final public static int GL_VIEW_CLASS_32_BITS = 33480;
    final public static int GL_VIEW_CLASS_24_BITS = 33481;
    final public static int GL_VIEW_CLASS_16_BITS = 33482;
    final public static int GL_VIEW_CLASS_8_BITS = 33483;
    final public static int GL_VIEW_CLASS_S3TC_DXT1_RGB = 33484;
    final public static int GL_VIEW_CLASS_S3TC_DXT1_RGBA = 33485;
    final public static int GL_VIEW_CLASS_S3TC_DXT3_RGBA = 33486;
    final public static int GL_VIEW_CLASS_S3TC_DXT5_RGBA = 33487;
    final public static int GL_VIEW_CLASS_RGTC1_RED = 33488;
    final public static int GL_VIEW_CLASS_RGTC2_RG = 33489;
    final public static int GL_VIEW_CLASS_BPTC_UNORM = 33490;
    final public static int GL_VIEW_CLASS_BPTC_FLOAT = 33491;

    private ARBInternalformatQuery2() {
    }

    public static void glGetInternalformat(int target, int internalformat, int pname, LongBuffer params) {
        GL43.glGetInternalformat(target, internalformat, pname, params);
    }

    public static long glGetInternalformati64(int target, int internalformat, int pname) {
        return GL43.glGetInternalformati64(target, internalformat, pname);
    }
}

