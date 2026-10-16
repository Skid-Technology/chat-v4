/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render.shader;

import gg.vape.utils.render.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class RoundedCornerMaskShaderProgram
extends ShaderProgram {
    final public static int CORNER_TOP_LEFT = 1;
    final public static int ALL_CORNERS;
    final public static int CORNER_TOP_RIGHT;
    private final static String VERTEX_SHADER_SOURCE;
    private final static String FRAGMENT_SHADER_SOURCE;
    final public static int CORNER_BOTTOM_LEFT;
    final public static int CORNER_BOTTOM_RIGHT;

    public RoundedCornerMaskShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
        super(vertexShaderSource, fragmentShaderSource);
    }

    public void setRadius(float radius) {
        GL20.glUniform1f(0, radius);
    }

    public RoundedCornerMaskShaderProgram() {
        super(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
    }

    public void setInnerRect(float left, float top, float right, float bottom) {
        GL20.glUniform4f(1, left, top, right, bottom);
    }

    public void setCornerMask(int cornerMask) {
        boolean topLeft = (cornerMask & 1) != 0;
        boolean topRight = (cornerMask & CORNER_TOP_RIGHT) != 0;
        boolean bottomRight = (cornerMask & CORNER_BOTTOM_RIGHT) != 0;
        boolean bottomLeft = (cornerMask & CORNER_BOTTOM_LEFT) != 0;
        GL20.glUniform4f(3, topLeft ? 1.0f : 0.0f, topRight ? 1.0f : 0.0f, bottomRight ? 1.0f : 0.0f, bottomLeft ? 1.0f : 0.0f);
    }

    public void setSpread(float spread) {
        GL20.glUniform1f(2, spread);
    }

    static {
        FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\n\nlayout(location = 0) uniform float u_Radius;\nlayout(location = 1) uniform vec4 u_InnerRect;\nlayout(location = 2) uniform float u_Spread;\nlayout(location = 3) uniform vec4 u_Corners; // (topLeft, topRight, bottomRight, bottomLeft)\n\nin vec2 f_Position;\nout vec4 fragColor;\n\nvoid main() {\n    vec2 tl = u_InnerRect.xy - f_Position;\n    vec2 br = f_Position - u_InnerRect.zw;\n\n    vec2 dis = max(br, tl);\n\n    // Determine if the fragment is in one of the corners\n    bool inTopLeft = (f_Position.x <= u_InnerRect.x && f_Position.y <= u_InnerRect.y);\n    bool inBottomLeft = (f_Position.x <= u_InnerRect.x && f_Position.y >= u_InnerRect.w);\n    bool inTopRight = (f_Position.x >= u_InnerRect.z && f_Position.y <= u_InnerRect.y);\n    bool inBottomRight = (f_Position.x >= u_InnerRect.z && f_Position.y >= u_InnerRect.w);\n\n    // Check if the respective corner should be drawn based on u_Corners\n    bool drawTopLeft = inTopLeft && u_Corners.x > 0.5;\n    bool drawTopRight = inTopRight && u_Corners.y > 0.5;\n    bool drawBottomLeft = inBottomLeft && u_Corners.w > 0.5;\n    bool drawBottomRight = inBottomRight && u_Corners.z > 0.5;\n\n    bool inCorner = drawTopLeft || drawTopRight || drawBottomLeft || drawBottomRight;\n\n    float v = length(max(vec2(0.0), dis)) - u_Radius;\n    float a = inCorner ? 1.0 - smoothstep(0.0, u_Spread, v) : 0.0;\n\n    // Only draw the selected corners, nothing in the middle\n    fragColor = inCorner ? gl_Color * vec4(1.0, 1.0, 1.0, 1.0 - a) : vec4(0.0, 0.0, 0.0, 0.0);\n}";
        VERTEX_SHADER_SOURCE = "#version 430 compatibility\n\nout vec2 f_Position;\n\nvoid main() {\n    f_Position = gl_Vertex.xy;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n    gl_FrontColor = gl_Color;\n}";
        long[] cornerMaskValues = new long[]{486331774129930242L, -2271953926246891505L, -4654026927017820152L, 8199249025132658692L};
        CORNER_TOP_RIGHT = (int)cornerMaskValues[0];
        CORNER_BOTTOM_RIGHT = (int)cornerMaskValues[3];
        CORNER_BOTTOM_LEFT = (int)cornerMaskValues[2];
        ALL_CORNERS = (int)cornerMaskValues[1];
    }
}

