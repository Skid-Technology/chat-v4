/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL43;

public final class ARBProgramInterfaceQuery {
    final public static int GL_UNIFORM = 37601;
    final public static int GL_UNIFORM_BLOCK = 37602;
    final public static int GL_PROGRAM_INPUT = 37603;
    final public static int GL_PROGRAM_OUTPUT = 37604;
    final public static int GL_BUFFER_VARIABLE = 37605;
    final public static int GL_SHADER_STORAGE_BLOCK = 37606;
    final public static int GL_VERTEX_SUBROUTINE = 37608;
    final public static int GL_TESS_CONTROL_SUBROUTINE = 37609;
    final public static int GL_TESS_EVALUATION_SUBROUTINE = 37610;
    final public static int GL_GEOMETRY_SUBROUTINE = 37611;
    final public static int GL_FRAGMENT_SUBROUTINE = 37612;
    final public static int GL_COMPUTE_SUBROUTINE = 37613;
    final public static int GL_VERTEX_SUBROUTINE_UNIFORM = 37614;
    final public static int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = 37615;
    final public static int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = 37616;
    final public static int GL_GEOMETRY_SUBROUTINE_UNIFORM = 37617;
    final public static int GL_FRAGMENT_SUBROUTINE_UNIFORM = 37618;
    final public static int GL_COMPUTE_SUBROUTINE_UNIFORM = 37619;
    final public static int GL_TRANSFORM_FEEDBACK_VARYING = 37620;
    final public static int GL_ACTIVE_RESOURCES = 37621;
    final public static int GL_MAX_NAME_LENGTH = 37622;
    final public static int GL_MAX_NUM_ACTIVE_VARIABLES = 37623;
    final public static int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = 37624;
    final public static int GL_NAME_LENGTH = 37625;
    final public static int GL_TYPE = 37626;
    final public static int GL_ARRAY_SIZE = 37627;
    final public static int GL_OFFSET = 37628;
    final public static int GL_BLOCK_INDEX = 37629;
    final public static int GL_ARRAY_STRIDE = 37630;
    final public static int GL_MATRIX_STRIDE = 37631;
    final public static int GL_IS_ROW_MAJOR = 37632;
    final public static int GL_ATOMIC_COUNTER_BUFFER_INDEX = 37633;
    final public static int GL_BUFFER_BINDING = 37634;
    final public static int GL_BUFFER_DATA_SIZE = 37635;
    final public static int GL_NUM_ACTIVE_VARIABLES = 37636;
    final public static int GL_ACTIVE_VARIABLES = 37637;
    final public static int GL_REFERENCED_BY_VERTEX_SHADER = 37638;
    final public static int GL_REFERENCED_BY_TESS_CONTROL_SHADER = 37639;
    final public static int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 37640;
    final public static int GL_REFERENCED_BY_GEOMETRY_SHADER = 37641;
    final public static int GL_REFERENCED_BY_FRAGMENT_SHADER = 37642;
    final public static int GL_REFERENCED_BY_COMPUTE_SHADER = 37643;
    final public static int GL_TOP_LEVEL_ARRAY_SIZE = 37644;
    final public static int GL_TOP_LEVEL_ARRAY_STRIDE = 37645;
    final public static int GL_LOCATION = 37646;
    final public static int GL_LOCATION_INDEX = 37647;
    final public static int GL_IS_PER_PATCH = 37607;

    private ARBProgramInterfaceQuery() {
    }

    public static void glGetProgramInterface(int program, int programInterface, int pname, IntBuffer params) {
        GL43.glGetProgramInterface(program, programInterface, pname, params);
    }

    public static int glGetProgramInterfacei(int program, int programInterface, int pname) {
        return GL43.glGetProgramInterfacei(program, programInterface, pname);
    }

    public static int glGetProgramResourceIndex(int program, int programInterface, ByteBuffer name) {
        return GL43.glGetProgramResourceIndex(program, programInterface, name);
    }

    public static int glGetProgramResourceIndex(int program, int programInterface, CharSequence name) {
        return GL43.glGetProgramResourceIndex(program, programInterface, name);
    }

    public static void glGetProgramResourceName(int program, int programInterface, int index, IntBuffer length, ByteBuffer name) {
        GL43.glGetProgramResourceName(program, programInterface, index, length, name);
    }

    public static String glGetProgramResourceName(int program, int programInterface, int index, int bufSize) {
        return GL43.glGetProgramResourceName(program, programInterface, index, bufSize);
    }

    public static void glGetProgramResource(int program, int programInterface, int index, IntBuffer props, IntBuffer length, IntBuffer params) {
        GL43.glGetProgramResource(program, programInterface, index, props, length, params);
    }

    public static int glGetProgramResourceLocation(int program, int programInterface, ByteBuffer name) {
        return GL43.glGetProgramResourceLocation(program, programInterface, name);
    }

    public static int glGetProgramResourceLocation(int program, int programInterface, CharSequence name) {
        return GL43.glGetProgramResourceLocation(program, programInterface, name);
    }

    public static int glGetProgramResourceLocationIndex(int program, int programInterface, ByteBuffer name) {
        return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
    }

    public static int glGetProgramResourceLocationIndex(int program, int programInterface, CharSequence name) {
        return GL43.glGetProgramResourceLocationIndex(program, programInterface, name);
    }
}

