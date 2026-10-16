/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL40;

public final class ARBTessellationShader {
    final public static int GL_PATCHES = 14;
    final public static int GL_PATCH_VERTICES = 36466;
    final public static int GL_PATCH_DEFAULT_INNER_LEVEL = 36467;
    final public static int GL_PATCH_DEFAULT_OUTER_LEVEL = 36468;
    final public static int GL_TESS_CONTROL_OUTPUT_VERTICES = 36469;
    final public static int GL_TESS_GEN_MODE = 36470;
    final public static int GL_TESS_GEN_SPACING = 36471;
    final public static int GL_TESS_GEN_VERTEX_ORDER = 36472;
    final public static int GL_TESS_GEN_POINT_MODE = 36473;
    final public static int GL_ISOLINES = 36474;
    final public static int GL_FRACTIONAL_ODD = 36475;
    final public static int GL_FRACTIONAL_EVEN = 36476;
    final public static int GL_MAX_PATCH_VERTICES = 36477;
    final public static int GL_MAX_TESS_GEN_LEVEL = 36478;
    final public static int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 36479;
    final public static int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 36480;
    final public static int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 36481;
    final public static int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 36482;
    final public static int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 36483;
    final public static int GL_MAX_TESS_PATCH_COMPONENTS = 36484;
    final public static int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 36485;
    final public static int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 36486;
    final public static int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 36489;
    final public static int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 36490;
    final public static int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 34924;
    final public static int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 34925;
    final public static int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 36382;
    final public static int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 36383;
    final public static int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = 34032;
    final public static int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = 34033;
    final public static int GL_TESS_EVALUATION_SHADER = 36487;
    final public static int GL_TESS_CONTROL_SHADER = 36488;

    private ARBTessellationShader() {
    }

    public static void glPatchParameteri(int pname, int value) {
        GL40.glPatchParameteri(pname, value);
    }

    public static void glPatchParameter(int pname, FloatBuffer values) {
        GL40.glPatchParameter(pname, values);
    }
}

