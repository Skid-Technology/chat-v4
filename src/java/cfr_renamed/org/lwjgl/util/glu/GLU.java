/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.glu;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLUtessellator;
import org.lwjgl.util.glu.MipMap;
import org.lwjgl.util.glu.Project;
import org.lwjgl.util.glu.Registry;
import org.lwjgl.util.glu.tessellation.GLUtessellatorImpl;

public class GLU {
    final static float PI = (float)Math.PI;
    final public static int GLU_INVALID_ENUM = 100900;
    final public static int GLU_INVALID_VALUE = 100901;
    final public static int GLU_OUT_OF_MEMORY = 100902;
    final public static int GLU_INCOMPATIBLE_GL_VERSION = 100903;
    final public static int GLU_VERSION = 100800;
    final public static int GLU_EXTENSIONS = 100801;
    final public static boolean GLU_TRUE = true;
    final public static boolean GLU_FALSE = false;
    final public static int GLU_SMOOTH = 100000;
    final public static int GLU_FLAT = 100001;
    final public static int GLU_NONE = 100002;
    final public static int GLU_POINT = 100010;
    final public static int GLU_LINE = 100011;
    final public static int GLU_FILL = 100012;
    final public static int GLU_SILHOUETTE = 100013;
    final public static int GLU_OUTSIDE = 100020;
    final public static int GLU_INSIDE = 100021;
    final public static double GLU_TESS_MAX_COORD = 1.0E150;
    final public static double TESS_MAX_COORD = 1.0E150;
    final public static int GLU_TESS_WINDING_RULE = 100140;
    final public static int GLU_TESS_BOUNDARY_ONLY = 100141;
    final public static int GLU_TESS_TOLERANCE = 100142;
    final public static int GLU_TESS_WINDING_ODD = 100130;
    final public static int GLU_TESS_WINDING_NONZERO = 100131;
    final public static int GLU_TESS_WINDING_POSITIVE = 100132;
    final public static int GLU_TESS_WINDING_NEGATIVE = 100133;
    final public static int GLU_TESS_WINDING_ABS_GEQ_TWO = 100134;
    final public static int GLU_TESS_BEGIN = 100100;
    final public static int GLU_TESS_VERTEX = 100101;
    final public static int GLU_TESS_END = 100102;
    final public static int GLU_TESS_ERROR = 100103;
    final public static int GLU_TESS_EDGE_FLAG = 100104;
    final public static int GLU_TESS_COMBINE = 100105;
    final public static int GLU_TESS_BEGIN_DATA = 100106;
    final public static int GLU_TESS_VERTEX_DATA = 100107;
    final public static int GLU_TESS_END_DATA = 100108;
    final public static int GLU_TESS_ERROR_DATA = 100109;
    final public static int GLU_TESS_EDGE_FLAG_DATA = 100110;
    final public static int GLU_TESS_COMBINE_DATA = 100111;
    final public static int GLU_TESS_ERROR1 = 100151;
    final public static int GLU_TESS_ERROR2 = 100152;
    final public static int GLU_TESS_ERROR3 = 100153;
    final public static int GLU_TESS_ERROR4 = 100154;
    final public static int GLU_TESS_ERROR5 = 100155;
    final public static int GLU_TESS_ERROR6 = 100156;
    final public static int GLU_TESS_ERROR7 = 100157;
    final public static int GLU_TESS_ERROR8 = 100158;
    final public static int GLU_TESS_MISSING_BEGIN_POLYGON = 100151;
    final public static int GLU_TESS_MISSING_BEGIN_CONTOUR = 100152;
    final public static int GLU_TESS_MISSING_END_POLYGON = 100153;
    final public static int GLU_TESS_MISSING_END_CONTOUR = 100154;
    final public static int GLU_TESS_COORD_TOO_LARGE = 100155;
    final public static int GLU_TESS_NEED_COMBINE_CALLBACK = 100156;
    final public static int GLU_AUTO_LOAD_MATRIX = 100200;
    final public static int GLU_CULLING = 100201;
    final public static int GLU_SAMPLING_TOLERANCE = 100203;
    final public static int GLU_DISPLAY_MODE = 100204;
    final public static int GLU_PARAMETRIC_TOLERANCE = 100202;
    final public static int GLU_SAMPLING_METHOD = 100205;
    final public static int GLU_U_STEP = 100206;
    final public static int GLU_V_STEP = 100207;
    final public static int GLU_PATH_LENGTH = 100215;
    final public static int GLU_PARAMETRIC_ERROR = 100216;
    final public static int GLU_DOMAIN_DISTANCE = 100217;
    final public static int GLU_MAP1_TRIM_2 = 100210;
    final public static int GLU_MAP1_TRIM_3 = 100211;
    final public static int GLU_OUTLINE_POLYGON = 100240;
    final public static int GLU_OUTLINE_PATCH = 100241;
    final public static int GLU_NURBS_ERROR1 = 100251;
    final public static int GLU_NURBS_ERROR2 = 100252;
    final public static int GLU_NURBS_ERROR3 = 100253;
    final public static int GLU_NURBS_ERROR4 = 100254;
    final public static int GLU_NURBS_ERROR5 = 100255;
    final public static int GLU_NURBS_ERROR6 = 100256;
    final public static int GLU_NURBS_ERROR7 = 100257;
    final public static int GLU_NURBS_ERROR8 = 100258;
    final public static int GLU_NURBS_ERROR9 = 100259;
    final public static int GLU_NURBS_ERROR10 = 100260;
    final public static int GLU_NURBS_ERROR11 = 100261;
    final public static int GLU_NURBS_ERROR12 = 100262;
    final public static int GLU_NURBS_ERROR13 = 100263;
    final public static int GLU_NURBS_ERROR14 = 100264;
    final public static int GLU_NURBS_ERROR15 = 100265;
    final public static int GLU_NURBS_ERROR16 = 100266;
    final public static int GLU_NURBS_ERROR17 = 100267;
    final public static int GLU_NURBS_ERROR18 = 100268;
    final public static int GLU_NURBS_ERROR19 = 100269;
    final public static int GLU_NURBS_ERROR20 = 100270;
    final public static int GLU_NURBS_ERROR21 = 100271;
    final public static int GLU_NURBS_ERROR22 = 100272;
    final public static int GLU_NURBS_ERROR23 = 100273;
    final public static int GLU_NURBS_ERROR24 = 100274;
    final public static int GLU_NURBS_ERROR25 = 100275;
    final public static int GLU_NURBS_ERROR26 = 100276;
    final public static int GLU_NURBS_ERROR27 = 100277;
    final public static int GLU_NURBS_ERROR28 = 100278;
    final public static int GLU_NURBS_ERROR29 = 100279;
    final public static int GLU_NURBS_ERROR30 = 100280;
    final public static int GLU_NURBS_ERROR31 = 100281;
    final public static int GLU_NURBS_ERROR32 = 100282;
    final public static int GLU_NURBS_ERROR33 = 100283;
    final public static int GLU_NURBS_ERROR34 = 100284;
    final public static int GLU_NURBS_ERROR35 = 100285;
    final public static int GLU_NURBS_ERROR36 = 100286;
    final public static int GLU_NURBS_ERROR37 = 100287;
    final public static int GLU_CW = 100120;
    final public static int GLU_CCW = 100121;
    final public static int GLU_INTERIOR = 100122;
    final public static int GLU_EXTERIOR = 100123;
    final public static int GLU_UNKNOWN = 100124;
    final public static int GLU_BEGIN = 100100;
    final public static int GLU_VERTEX = 100101;
    final public static int GLU_END = 100102;
    final public static int GLU_ERROR = 100103;
    final public static int GLU_EDGE_FLAG = 100104;

    public static void gluLookAt(float eyex, float eyey, float eyez, float centerx, float centery, float centerz, float upx, float upy, float upz) {
        Project.gluLookAt(eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);
    }

    public static void gluOrtho2D(float left, float right, float bottom, float top) {
        GL11.glOrtho(left, right, bottom, top, -1.0, 1.0);
    }

    public static void gluPerspective(float fovy, float aspect, float zNear, float zFar) {
        Project.gluPerspective(fovy, aspect, zNear, zFar);
    }

    public static boolean gluProject(float objx, float objy, float objz, FloatBuffer modelMatrix, FloatBuffer projMatrix, IntBuffer viewport, FloatBuffer win_pos) {
        return Project.gluProject(objx, objy, objz, modelMatrix, projMatrix, viewport, win_pos);
    }

    public static boolean gluUnProject(float winx, float winy, float winz, FloatBuffer modelMatrix, FloatBuffer projMatrix, IntBuffer viewport, FloatBuffer obj_pos) {
        return Project.gluUnProject(winx, winy, winz, modelMatrix, projMatrix, viewport, obj_pos);
    }

    public static void gluPickMatrix(float x, float y, float width, float height, IntBuffer viewport) {
        Project.gluPickMatrix(x, y, width, height, viewport);
    }

    public static String gluGetString(int name) {
        return Registry.gluGetString(name);
    }

    public static boolean gluCheckExtension(String extName, String extString) {
        return Registry.gluCheckExtension(extName, extString);
    }

    public static int gluBuild2DMipmaps(int target, int components, int width, int height, int format, int type, ByteBuffer data) {
        return MipMap.gluBuild2DMipmaps(target, components, width, height, format, type, data);
    }

    public static int gluScaleImage(int format, int widthIn, int heightIn, int typeIn, ByteBuffer dataIn, int widthOut, int heightOut, int typeOut, ByteBuffer dataOut) {
        return MipMap.gluScaleImage(format, widthIn, heightIn, typeIn, dataIn, widthOut, heightOut, typeOut, dataOut);
    }

    public static String gluErrorString(int error_code) {
        switch (error_code) {
            case 100900: {
                return "Invalid enum (glu)";
            }
            case 100901: {
                return "Invalid value (glu)";
            }
            case 100902: {
                return "Out of memory (glu)";
            }
        }
        return Util.translateGLErrorString(error_code);
    }

    public static GLUtessellator gluNewTess() {
        return new GLUtessellatorImpl();
    }
}

