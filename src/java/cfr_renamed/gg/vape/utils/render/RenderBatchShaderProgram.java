/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.Vape;
import java.nio.charset.StandardCharsets;
import org.lwjgl.opengl.GL20;

public class RenderBatchShaderProgram {
    public int programId;
    final public int projectionUniformLocation;
    private static int[] legacyState;
    final public int modelUniformLocation;
    final public int viewUniformLocation;

    private int compileShader(String resourcePath) {
        int shaderType;
        if (resourcePath.endsWith(".frag")) {
            shaderType = 35632;
        } else if (resourcePath.endsWith(".vert")) {
            shaderType = 35633;
        } else {
            throw new RuntimeException("Unable to set type");
        }
        byte[] shaderBytes = Vape.readResource(resourcePath);
        int shaderId = GL20.glCreateShader(shaderType);
        String shaderSource = new String(shaderBytes, StandardCharsets.UTF_8);
        GL20.glShaderSource(shaderId, shaderSource);
        GL20.glCompileShader(shaderId);
        String compileError = null;
        if (GL20.glGetShaderi(shaderId, 35713) == 0) {
            compileError = GL20.glGetShaderInfoLog(shaderId, 512);
        }
        if (compileError != null) {
            throw new RuntimeException("Unable to compile shader: " + shaderType + " - " + compileError);
        }
        return shaderId;
    }

    public static int[] getLegacyState() {
        return legacyState;
    }

    private static RuntimeException propagateRuntimeException(RuntimeException runtimeException) {
        return runtimeException;
    }

    public static void setLegacyState(int[] legacyState) {
        RenderBatchShaderProgram.legacyState = legacyState;
    }

    private void linkProgram(int firstShaderId, int secondShaderId) {
        this.programId = GL20.glCreateProgram();
        GL20.glAttachShader(this.programId, firstShaderId);
        GL20.glAttachShader(this.programId, secondShaderId);
        GL20.glLinkProgram(this.programId);
        if (GL20.glGetProgrami(this.programId, 35714) == 0) {
            String linkError = GL20.glGetProgramInfoLog(this.programId, 8224);
            throw new RuntimeException("Unable to link shader: " + linkError);
        }
        GL20.glDeleteShader(firstShaderId);
        GL20.glDeleteShader(secondShaderId);
    }

    public RenderBatchShaderProgram(String firstShaderPath, String secondShaderPath) {
        int firstShaderId = this.compileShader(firstShaderPath);
        int secondShaderId = this.compileShader(secondShaderPath);
        this.linkProgram(firstShaderId, secondShaderId);
        this.projectionUniformLocation = GL20.glGetUniformLocation(this.programId, "u_Projection");
        this.modelUniformLocation = GL20.glGetUniformLocation(this.programId, "u_Model");
        this.viewUniformLocation = GL20.glGetUniformLocation(this.programId, "u_View");
    }

    public void bind() {
        GL20.glUseProgram(this.programId);
    }

    static {
        RenderBatchShaderProgram.setLegacyState(new int[3]);
    }
}

