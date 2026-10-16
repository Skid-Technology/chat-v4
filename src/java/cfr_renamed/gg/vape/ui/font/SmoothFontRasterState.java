/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font;

import gg.vape.ui.font.stb.StbByteBufferView;
import gg.vape.ui.font.stb.StbTrueTypeActiveEdge;
import gg.vape.ui.font.stb.StbTrueTypeAlignedQuad;
import gg.vape.ui.font.stb.StbTrueTypeBakedChar;
import gg.vape.ui.font.stb.StbTrueTypeBitmap;
import gg.vape.ui.font.stb.StbTrueTypeCharStringContext;
import gg.vape.ui.font.stb.StbTrueTypeEdge;
import gg.vape.ui.font.stb.StbTrueTypeFontInfo;
import gg.vape.ui.font.stb.StbTrueTypeGlyphPackNode;
import gg.vape.ui.font.stb.StbTrueTypeKerningEntry;
import gg.vape.ui.font.stb.StbTrueTypePackAtlasState;
import gg.vape.ui.font.stb.StbTrueTypePackContext;
import gg.vape.ui.font.stb.StbTrueTypePackRange;
import gg.vape.ui.font.stb.StbTrueTypePackRectangle;
import gg.vape.ui.font.stb.StbTrueTypePackedChar;
import gg.vape.ui.font.stb.StbTrueTypePoint;
import gg.vape.ui.font.stb.StbTrueTypeVertex;
import java.util.Arrays;

public class SmoothFontRasterState {
    final public static int var_int_d = 0;
    final public static int var_int_x;
    final public static int var_int_O;
    final public static int var_int_n;
    final public static int var_int_M;
    final public static int var_int_b = 1;
    final static boolean var_boolean_G;
    final public static int var_int_z;
    final public static int var_int_m;
    final public static int Fc;
    final public static int var_int_P;
    final public static int var_int_q;
    final public static int var_int_J = 1;
    final public static int var_int_j;
    final public static int var_int_W;
    final public static int var_int_R;
    final public static int var_int_T;
    final public static int var_int_e;
    final public static int var_int_B;
    final public static int var_int_u = 0;
    final public static int var_int_i;
    final public static int var_int_I;
    final public static int var_int_k = 0;
    final public static int var_int_Q;
    final public static int var_int_h;
    final public static int FM = 1;
    final public static int var_int_a;
    final public static int F1;
    final public static int var_int_g;
    final public static int Fb;
    final public static int var_int_s = 0;
    final public static int var_int_y = 1;
    final public static int var_int_V;
    final public static int FC;
    private final static int var_int_c;
    final public static int var_int_v;
    final public static int var_int_E;
    final public static int var_int_w = 1;
    final public static int var_int_N;
    final public static int var_int_p = 1;
    final public static int var_int_D;
    final public static int var_int_U;
    final public static int Ff;
    final public static int var_int_K = 0;
    final public static int var_int_F;
    final public static int var_int_L;
    final public static int var_int_l = 0;
    final public static int var_int_t;
    final public static int var_int_f;
    final public static int var_int_o;
    final public static int var_int_A;
    final public static int var_int_Z;
    final public static int var_int_C;
    final public static int var_int_X = 1;
    final public static int var_int_S;
    final public static int var_int_r;
    final public static int var_int_H;
    final public static int Fy;
    final public static int var_int_Y;

    public static int F(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        if (stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g.var_int_y != 0) {
            SmoothFontRasterState.m(stbTrueTypeFontInfo, n, nArray, nArray2, nArray3, nArray4);
        } else {
            int n2 = SmoothFontRasterState.G(stbTrueTypeFontInfo, n);
            if (n2 < 0) {
                return 0;
            }
            if (nArray != null) {
                nArray[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n2 + 2);
            }
            if (nArray2 != null) {
                nArray2[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n2 + 4);
            }
            if (nArray3 != null) {
                nArray3[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n2 + 6);
            }
            if (nArray4 != null) {
                nArray4[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n2 + 8);
            }
        }
        return 1;
    }

    static StbByteBufferView e(StbByteBufferView frame, int n) {
        StbByteBufferView frame2 = frame.k();
        SmoothFontRasterState.i(frame2, 0);
        int n2 = SmoothFontRasterState.p(frame2);
        int n3 = SmoothFontRasterState.Q(frame2);
        if (!(var_boolean_G || n >= 0 && n < n2)) {
            throw new AssertionError();
        }
        if (!(var_boolean_G || n3 >= 1 && n3 <= 4)) {
            throw new AssertionError();
        }
        SmoothFontRasterState.K(frame2, n * n3);
        int n4 = (int)SmoothFontRasterState.u(frame2, n3);
        int n5 = (int)SmoothFontRasterState.u(frame2, n3);
        return SmoothFontRasterState.E(frame2, 2 + (n2 + 1) * n3 + n4, n5 - n4);
    }

    public static int y(byte[] byArray, String string, int n) {
        return SmoothFontRasterState.h(byArray, string.getBytes(), n);
    }

    static int a(StbTrueTypeVertex[] stbTrueTypeVertexArray, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        if (n3 != 0) {
            if (n2 != 0) {
                SmoothFontRasterState.d(stbTrueTypeVertexArray[n++], 3, n8 + n6 >> 1, n9 + n7 >> 1, n8, n9);
            }
            SmoothFontRasterState.d(stbTrueTypeVertexArray[n++], 3, n4, n5, n6, n7);
        } else if (n2 != 0) {
            SmoothFontRasterState.d(stbTrueTypeVertexArray[n++], 3, n4, n5, n8, n9);
        } else {
            SmoothFontRasterState.d(stbTrueTypeVertexArray[n++], 2, n4, n5, 0, 0);
        }
        return n;
    }

    static void p(byte[] byArray, int n, int n2, int n3, int n4, int n5) {
        byte[] byArray2 = new byte[8];
        int n6 = n3 - n5;
        Arrays.fill(byArray2, 0);
        for (int i = 0; i < n2; ++i) {
            int n7;
            Arrays.fill(byArray2, 0, n5, 0);
            int n8 = 0;
            switch (n5) {
                case 2: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                        byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 2);
                    }
                    break;
                }
                case 3: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                        byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 3);
                    }
                    break;
                }
                case 4: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                        byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 4);
                    }
                    break;
                }
                case 5: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                        byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 5);
                    }
                    break;
                }
                default: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                        byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / n5);
                    }
                }
            }
            while (n7 < n3) {
                byArray[n + n7 * n4] = (byte)((n8 -= byArray2[n7 & 7] & 0xFF) / n5);
                ++n7;
            }
            ++n;
        }
    }

    static int B(byte[] byArray, int n) {
        return ((byArray[n] & 0xFF) << 24) + ((byArray[n + 1] & 0xFF) << 16) + ((byArray[n + 2] & 0xFF) << 8) + (byArray[n + 3] & 0xFF);
    }

    static float A(float f, float f2, float f3) {
        return (f2 + f3) / 2.0f * f;
    }

    static void V(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, int n, int n2) {
        if (n > stbTrueTypeCharStringContext.var_int_c || stbTrueTypeCharStringContext.var_int_v == 0) {
            stbTrueTypeCharStringContext.var_int_c = n;
        }
        if (n2 > stbTrueTypeCharStringContext.var_int_f || stbTrueTypeCharStringContext.var_int_v == 0) {
            stbTrueTypeCharStringContext.var_int_f = n2;
        }
        if (n < stbTrueTypeCharStringContext.var_int_y || stbTrueTypeCharStringContext.var_int_v == 0) {
            stbTrueTypeCharStringContext.var_int_y = n;
        }
        if (n2 < stbTrueTypeCharStringContext.var_int_Z || stbTrueTypeCharStringContext.var_int_v == 0) {
            stbTrueTypeCharStringContext.var_int_Z = n2;
        }
        stbTrueTypeCharStringContext.var_int_v = 1;
    }

    public static void m(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        SmoothFontRasterState.p(stbTrueTypeFontInfo, n, f, f2, 0.0f, 0.0f, nArray, nArray2, nArray3, nArray4);
    }

    public static void o(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, float f3, float f4, int n5, int n6, float[] fArray, float[] fArray2, int n7) {
        SmoothFontRasterState.H(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, f3, f4, n5, n6, fArray, fArray2, SmoothFontRasterState.T(stbTrueTypeFontInfo, n7));
    }

    static StbByteBufferView x(byte[] byArray, int n, int n2) {
        if (!var_boolean_G && n2 >= 0x40000000) {
            throw new AssertionError();
        }
        StbByteBufferView frame = new StbByteBufferView();
        frame.var_byte_arr_f = byArray;
        frame.var_int_C = n;
        frame.var_int_y = n2;
        frame.var_int_h = 0;
        return frame;
    }

    public static int d(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
        int n3 = 0;
        if (stbTrueTypeFontInfo.var_int_G != 0) {
            n3 += SmoothFontRasterState.c(stbTrueTypeFontInfo, n, n2);
        } else if (stbTrueTypeFontInfo.var_int_L != 0) {
            n3 += SmoothFontRasterState.v(stbTrueTypeFontInfo, n, n2);
        }
        return n3;
    }

    static void b(StbTrueTypePackAtlasState stbTrueTypePackAtlasState, int n, int n2, StbTrueTypeGlyphPackNode[] stbTrueTypeGlyphPackNodeArray, int n3) {
        stbTrueTypePackAtlasState.var_int_f = n;
        stbTrueTypePackAtlasState.var_int_F = n2;
        stbTrueTypePackAtlasState.var_int_n = 0;
        stbTrueTypePackAtlasState.var_int_a = 0;
        stbTrueTypePackAtlasState.var_int_t = 0;
    }

    public static String j(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int n, int n2, int n3, int n4) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        int n5 = stbTrueTypeFontInfo.var_int_U;
        long l = SmoothFontRasterState.v(byArray, n5, "name");
        if (l == 0L) {
            return null;
        }
        int n6 = SmoothFontRasterState.x(byArray, (int)l + 2);
        int n7 = (int)l + SmoothFontRasterState.x(byArray, (int)l + 4);
        for (int i = 0; i < n6; ++i) {
            int n8 = (int)l + 6 + 12 * i;
            if (n != SmoothFontRasterState.x(byArray, n8 + 0) || n2 != SmoothFontRasterState.x(byArray, n8 + 2) || n3 != SmoothFontRasterState.x(byArray, n8 + 4) || n4 != SmoothFontRasterState.x(byArray, n8 + 6)) continue;
            nArray[0] = SmoothFontRasterState.x(byArray, n8 + 8);
            int n9 = n7 + SmoothFontRasterState.x(byArray, n8 + 10);
            byte[] byArray2 = new byte[nArray[0]];
            System.arraycopy(byArray, n9, byArray2, 0, nArray[0]);
            return new String(byArray2);
        }
        return null;
    }

    public static int c(byte[] byArray, int n) {
        return SmoothFontRasterState.E(byArray, n);
    }

    public static byte[] F(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, float f3, float f4, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        int[] nArray5 = new int[1];
        int[] nArray6 = new int[1];
        int[] nArray7 = new int[1];
        int[] nArray8 = new int[1];
        StbTrueTypeBitmap stbTrueTypeBitmap = new StbTrueTypeBitmap();
        StbTrueTypeVertex[] stbTrueTypeVertexArray = SmoothFontRasterState.m(stbTrueTypeFontInfo, n);
        int n2 = stbTrueTypeVertexArray != null ? stbTrueTypeVertexArray.length : 0;
        int n3 = n2;
        if (f == 0.0f) {
            f = f2;
        }
        if (f2 == 0.0f) {
            if (f == 0.0f) {
                return null;
            }
            f2 = f;
        }
        SmoothFontRasterState.G(stbTrueTypeFontInfo, n, f, f2, f3, f4, nArray5, nArray6, nArray7, nArray8);
        stbTrueTypeBitmap.var_int_K = nArray7[0] - nArray5[0];
        stbTrueTypeBitmap.var_int_c = nArray8[0] - nArray6[0];
        stbTrueTypeBitmap.var_byte_arr_E = null;
        if (nArray != null) {
            nArray[0] = stbTrueTypeBitmap.var_int_K;
        }
        if (nArray2 != null) {
            nArray2[0] = stbTrueTypeBitmap.var_int_c;
        }
        if (nArray3 != null) {
            nArray3[0] = nArray5[0];
        }
        if (nArray4 != null) {
            nArray4[0] = nArray6[0];
        }
        if (stbTrueTypeBitmap.var_int_K != 0 && stbTrueTypeBitmap.var_int_c != 0) {
            stbTrueTypeBitmap.var_byte_arr_E = new byte[stbTrueTypeBitmap.var_int_K * stbTrueTypeBitmap.var_int_c];
            stbTrueTypeBitmap.var_int_h = 0;
            if (stbTrueTypeBitmap.var_byte_arr_E != null) {
                stbTrueTypeBitmap.var_int_A = stbTrueTypeBitmap.var_int_K;
                SmoothFontRasterState.b(stbTrueTypeBitmap, 0.35f, stbTrueTypeVertexArray, n2, f, f2, f3, f4, nArray5[0], nArray6[0], 1);
            }
        }
        return stbTrueTypeBitmap.var_byte_arr_E;
    }

    static void Q(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, float f, float f2) {
        SmoothFontRasterState.d(stbTrueTypeCharStringContext);
        stbTrueTypeCharStringContext.var_float_s = stbTrueTypeCharStringContext.var_float_C += f;
        stbTrueTypeCharStringContext.var_float_p = stbTrueTypeCharStringContext.var_float_o += f2;
        SmoothFontRasterState.A(stbTrueTypeCharStringContext, 1, (int)stbTrueTypeCharStringContext.var_float_C, (int)stbTrueTypeCharStringContext.var_float_o, 0, 0, 0, 0);
    }

    static void K(StbByteBufferView frame, int n) {
        SmoothFontRasterState.i(frame, frame.var_int_h + n);
    }

    static long v(byte[] byArray, int n, String string) {
        int n2 = SmoothFontRasterState.x(byArray, n + 4);
        long l = n + 12;
        for (int i = 0; i < n2; ++i) {
            long l2 = l + (long)(16 * i);
            if (!SmoothFontRasterState.f(byArray, (int)l2, string)) continue;
            return SmoothFontRasterState.c$src$J$1nktx4p(byArray, (int)l2 + 8);
        }
        return 0L;
    }

    static StbTrueTypeVertex[] o(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        StbTrueTypeVertex[] stbTrueTypeVertexArray = null;
        int n3 = 0;
        int n4 = SmoothFontRasterState.G(stbTrueTypeFontInfo, n);
        if (n4 < 0) {
            return null;
        }
        short s = SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n4);
        if (s > 0) {
            int n5;
            int n6;
            int n2 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = n4 + 10;
            int n11 = SmoothFontRasterState.x(byArray, n4 + 10 + s * 2);
            int n12 = n4 + 10 + s * 2 + 2 + n11;
            int n13 = 1 + SmoothFontRasterState.x(byArray, n10 + s * 2 - 2);
            int n14 = n13 + 2 * s;
            stbTrueTypeVertexArray = new StbTrueTypeVertex[n14];
            for (n6 = 0; n6 < n14; ++n6) {
                stbTrueTypeVertexArray[n6] = new StbTrueTypeVertex();
            }
            int n15 = 0;
            int n16 = 0;
            int n17 = n14 - n13;
            int n18 = n12;
            for (n6 = 0; n6 < n13; ++n6) {
                if (n16 == 0) {
                    if (((n2 = byArray[n18++] & 0xFF) & 8) != 0) {
                        n16 = byArray[n18++] & 0xFF;
                    }
                } else {
                    --n16;
                }
                stbTrueTypeVertexArray[n17 + n6].var_int_O = n2;
            }
            int n19 = 0;
            for (n6 = 0; n6 < n13; ++n6) {
                n2 = stbTrueTypeVertexArray[n17 + n6].var_int_O;
                if ((n2 & 2) != 0) {
                    n5 = byArray[n18++] & 0xFF;
                    n19 += (n2 & 0x10) != 0 ? n5 : -n5;
                } else if ((n2 & 0x10) == 0) {
                    n19 += (short)((byArray[n18] & 0xFF) * 256 + (byArray[n18 + 1] & 0xFF));
                    n18 += 2;
                }
                stbTrueTypeVertexArray[n17 + n6].var_short_f = (short)n19;
            }
            int n20 = 0;
            for (n6 = 0; n6 < n13; ++n6) {
                n2 = stbTrueTypeVertexArray[n17 + n6].var_int_O;
                if ((n2 & 4) != 0) {
                    n5 = byArray[n18++] & 0xFF;
                    n20 += (n2 & 0x20) != 0 ? n5 : -n5;
                } else if ((n2 & 0x20) == 0) {
                    n20 += (short)((byArray[n18] & 0xFF) * 256 + (byArray[n18 + 1] & 0xFF));
                    n18 += 2;
                }
                stbTrueTypeVertexArray[n17 + n6].var_short_H = (short)n20;
            }
            n3 = 0;
            int n21 = 0;
            int n22 = 0;
            int n23 = 0;
            int n24 = 0;
            int n25 = 0;
            int n26 = 0;
            for (n6 = 0; n6 < n13; ++n6) {
                n2 = stbTrueTypeVertexArray[n17 + n6].var_int_O;
                n19 = stbTrueTypeVertexArray[n17 + n6].var_short_f;
                n20 = stbTrueTypeVertexArray[n17 + n6].var_short_H;
                if (n15 == n6) {
                    if (n6 != 0) {
                        n3 = SmoothFontRasterState.a(stbTrueTypeVertexArray, n3, n8, n9, n26, n25, n22, n21, n24, n23);
                    }
                    n9 = (n2 & 1) == 0 ? 1 : 0;
                    int n27 = n9;
                    if (n9 != 0) {
                        n22 = n19;
                        n21 = n20;
                        if ((stbTrueTypeVertexArray[n17 + n6 + 1].var_int_O & 1) == 0) {
                            n26 = n19 + stbTrueTypeVertexArray[n17 + n6 + 1].var_short_f >> 1;
                            n25 = n20 + stbTrueTypeVertexArray[n17 + n6 + 1].var_short_H >> 1;
                        } else {
                            n26 = stbTrueTypeVertexArray[n17 + n6 + 1].var_short_f;
                            n25 = stbTrueTypeVertexArray[n17 + n6 + 1].var_short_H;
                            ++n6;
                        }
                    } else {
                        n26 = n19;
                        n25 = n20;
                    }
                    SmoothFontRasterState.d(stbTrueTypeVertexArray[n3++], 1, n26, n25, 0, 0);
                    n8 = 0;
                    n15 = 1 + SmoothFontRasterState.x(byArray, n10 + n7 * 2);
                    ++n7;
                    continue;
                }
                if ((n2 & 1) == 0) {
                    if (n8 != 0) {
                        SmoothFontRasterState.d(stbTrueTypeVertexArray[n3++], 3, n24 + n19 >> 1, n23 + n20 >> 1, n24, n23);
                    }
                    n24 = n19;
                    n23 = n20;
                    n8 = 1;
                    continue;
                }
                if (n8 != 0) {
                    SmoothFontRasterState.d(stbTrueTypeVertexArray[n3++], 3, n19, n20, n24, n23);
                } else {
                    SmoothFontRasterState.d(stbTrueTypeVertexArray[n3++], 2, n19, n20, 0, 0);
                }
                n8 = 0;
            }
            n3 = SmoothFontRasterState.a(stbTrueTypeVertexArray, n3, n8, n9, n26, n25, n22, n21, n24, n23);
        } else if (s < 0) {
            int n2 = 1;
            int n28 = n4 + 10;
            n3 = 0;
            stbTrueTypeVertexArray = null;
            while (n2 != 0) {
                int n29 = 0;
                StbTrueTypeVertex[] stbTrueTypeVertexArray2 = null;
                StbTrueTypeVertex[] stbTrueTypeVertexArray3 = null;
                float[] fArray = new float[]{1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
                int n30 = SmoothFontRasterState.x(byArray, n28);
                int n31 = SmoothFontRasterState.x(byArray, n28 += 2);
                n28 += 2;
                if ((n30 & 2) != 0) {
                    if ((n30 & 1) != 0) {
                        fArray[4] = SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28);
                        fArray[5] = SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28 += 2);
                        n28 += 2;
                    } else {
                        fArray[4] = SmoothFontRasterState.u(byArray, n28);
                        fArray[5] = SmoothFontRasterState.u(byArray, ++n28);
                        ++n28;
                    }
                } else if (!var_boolean_G) {
                    throw new AssertionError();
                }
                if ((n30 & 8) != 0) {
                    fArray[0] = fArray[3] = (float)SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28) / 16384.0f;
                    n28 += 2;
                    fArray[2] = 0.0f;
                    fArray[1] = 0.0f;
                } else if ((n30 & 0x40) != 0) {
                    fArray[0] = (float)SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28) / 16384.0f;
                    fArray[2] = 0.0f;
                    fArray[1] = 0.0f;
                    fArray[3] = (float)SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28 += 2) / 16384.0f;
                    n28 += 2;
                } else if ((n30 & 0x80) != 0) {
                    fArray[0] = (float)SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28) / 16384.0f;
                    fArray[1] = (float)SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28 += 2) / 16384.0f;
                    fArray[2] = (float)SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28 += 2) / 16384.0f;
                    fArray[3] = (float)SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n28 += 2) / 16384.0f;
                    n28 += 2;
                }
                float f = (float)Math.sqrt(fArray[0] * fArray[0] + fArray[1] * fArray[1]);
                float f2 = (float)Math.sqrt(fArray[2] * fArray[2] + fArray[3] * fArray[3]);
                stbTrueTypeVertexArray2 = SmoothFontRasterState.m(stbTrueTypeFontInfo, n31);
                if (stbTrueTypeVertexArray2 != null) {
                    n29 = stbTrueTypeVertexArray2.length;
                }
                if (n29 > 0) {
                    for (int i = 0; i < n29; ++i) {
                        StbTrueTypeVertex stbTrueTypeVertex = stbTrueTypeVertexArray2[i];
                        short s2 = stbTrueTypeVertex.var_short_f;
                        short s3 = stbTrueTypeVertex.var_short_H;
                        stbTrueTypeVertex.var_short_f = (short)(f * (fArray[0] * (float)s2 + fArray[2] * (float)s3 + fArray[4]));
                        stbTrueTypeVertex.var_short_H = (short)(f2 * (fArray[1] * (float)s2 + fArray[3] * (float)s3 + fArray[5]));
                        s2 = stbTrueTypeVertex.var_short_h;
                        s3 = stbTrueTypeVertex.var_short_D;
                        stbTrueTypeVertex.var_short_h = (short)(f * (fArray[0] * (float)s2 + fArray[2] * (float)s3 + fArray[4]));
                        stbTrueTypeVertex.var_short_D = (short)(f2 * (fArray[1] * (float)s2 + fArray[3] * (float)s3 + fArray[5]));
                    }
                    stbTrueTypeVertexArray3 = new StbTrueTypeVertex[n3 + n29];
                    if (n3 > 0 && stbTrueTypeVertexArray != null) {
                        System.arraycopy(stbTrueTypeVertexArray, 0, stbTrueTypeVertexArray3, 0, n3);
                    }
                    System.arraycopy(stbTrueTypeVertexArray2, 0, stbTrueTypeVertexArray3, n3, n29);
                    stbTrueTypeVertexArray = stbTrueTypeVertexArray3;
                    n3 += n29;
                }
                n2 = n30 & 0x20;
            }
        }
        if (stbTrueTypeVertexArray != null && n3 < stbTrueTypeVertexArray.length) {
            StbTrueTypeVertex[] stbTrueTypeVertexArray4 = new StbTrueTypeVertex[n3];
            System.arraycopy(stbTrueTypeVertexArray, 0, stbTrueTypeVertexArray4, 0, n3);
            return stbTrueTypeVertexArray4;
        }
        return stbTrueTypeVertexArray;
    }

    public static void w(StbTrueTypePackContext stbTrueTypePackContext, int n) {
        stbTrueTypePackContext.var_int_h = n;
    }

    public static void S(StbTrueTypePackContext stbTrueTypePackContext) {
    }

    static int x(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) * 256 + (byArray[n + 1] & 0xFF);
    }

    static boolean d(float[] fArray, float[] fArray2) {
        return fArray[0] == fArray2[0] && fArray[1] == fArray2[1];
    }

    static StbByteBufferView B(StbByteBufferView frame) {
        int n = frame.var_int_h;
        int n2 = SmoothFontRasterState.p(frame);
        if (n2 != 0) {
            int n3 = SmoothFontRasterState.Q(frame);
            if (!(var_boolean_G || n3 >= 1 && n3 <= 4)) {
                throw new AssertionError();
            }
            SmoothFontRasterState.K(frame, n3 * n2);
            SmoothFontRasterState.K(frame, (int)SmoothFontRasterState.u(frame, n3) - 1);
        }
        return SmoothFontRasterState.E(frame, n, frame.var_int_h - n);
    }

    public static byte[] Y(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        return SmoothFontRasterState.q(stbTrueTypeFontInfo, f, f2, 0.0f, 0.0f, n, nArray, nArray2, nArray3, nArray4);
    }

    static void c(float[] fArray, int n, float[] fArray2, int n2, int n3, StbTrueTypeActiveEdge stbTrueTypeActiveEdge, float f) {
        float f2 = f + 1.0f;
        while (stbTrueTypeActiveEdge != null) {
            float f3;
            if (!var_boolean_G && !(stbTrueTypeActiveEdge.var_float_m >= f)) {
                throw new AssertionError();
            }
            if (stbTrueTypeActiveEdge.var_float_L == 0.0f) {
                f3 = stbTrueTypeActiveEdge.var_float_e;
                if (f3 < (float)n3) {
                    if (f3 >= 0.0f) {
                        SmoothFontRasterState.A(fArray, n, (int)f3, stbTrueTypeActiveEdge, f3, f, f3, f2);
                        SmoothFontRasterState.A(fArray2, n2, (int)f3 + 1, stbTrueTypeActiveEdge, f3, f, f3, f2);
                    } else {
                        SmoothFontRasterState.A(fArray2, n2, 0, stbTrueTypeActiveEdge, f3, f, f3, f2);
                    }
                }
            } else {
                float f4;
                float f5;
                float f6;
                float f7;
                float f8;
                float f9;
                float f10;
                float f11;
                float f12;
                f3 = stbTrueTypeActiveEdge.var_float_e;
                float f13 = stbTrueTypeActiveEdge.var_float_L;
                float f14 = f3 + f13;
                float f15 = stbTrueTypeActiveEdge.var_float_N;
                if (!(var_boolean_G || stbTrueTypeActiveEdge.var_float_j <= f2 && stbTrueTypeActiveEdge.var_float_m >= f)) {
                    throw new AssertionError();
                }
                if (stbTrueTypeActiveEdge.var_float_j > f) {
                    f12 = f3 + f13 * (stbTrueTypeActiveEdge.var_float_j - f);
                    f11 = stbTrueTypeActiveEdge.var_float_j;
                } else {
                    f12 = f3;
                    f11 = f;
                }
                if (stbTrueTypeActiveEdge.var_float_m < f2) {
                    f10 = f3 + f13 * (stbTrueTypeActiveEdge.var_float_m - f);
                    f9 = stbTrueTypeActiveEdge.var_float_m;
                } else {
                    f10 = f14;
                    f9 = f2;
                }
                if (f12 >= 0.0f && f10 >= 0.0f && f12 < (float)n3 && f10 < (float)n3) {
                    int n4;
                    if ((int)f12 == (int)f10) {
                        n4 = (int)f12;
                        float f16 = (f9 - f11) * stbTrueTypeActiveEdge.var_float_T;
                        int n5 = n + n4;
                        fArray[n5] = fArray[n5] + SmoothFontRasterState.E(f16, f12, (float)n4 + 1.0f, f10, (float)n4 + 1.0f);
                        int n6 = n2 + n4 + 1;
                        fArray2[n6] = fArray2[n6] + f16;
                    } else {
                        if (f12 > f10) {
                            f11 = f2 - (f11 - f);
                            f9 = f2 - (f9 - f);
                            float f17 = f11;
                            f11 = f9;
                            f9 = f17;
                            f17 = f10;
                            f10 = f12;
                            f12 = f17;
                            f13 = -f13;
                            f15 = -f15;
                            f17 = f3;
                            f3 = f14;
                            f14 = f17;
                        }
                        n4 = (int)f12;
                        int n7 = (int)f10;
                        f8 = f + f15 * ((float)(n4 + 1) - f3);
                        f7 = f + f15 * ((float)n7 - f3);
                        if (f8 > f2) {
                            f8 = f2;
                        }
                        f6 = stbTrueTypeActiveEdge.var_float_T;
                        f5 = f6 * (f8 - f11);
                        int n8 = n + n4;
                        fArray[n8] = fArray[n8] + SmoothFontRasterState.S(f5, (float)(n4 + 1) - f12);
                        if (f7 > f2) {
                            f7 = f2;
                            f15 = (f7 - f8) / (float)(n7 - (n4 + 1));
                        }
                        f4 = f6 * f15 * 1.0f;
                        for (int i = n4 + 1; i < n7; ++i) {
                            int n9 = n + i;
                            fArray[n9] = fArray[n9] + (f5 + f4 / 2.0f);
                            f5 += f4;
                        }
                        int n10 = n + n7;
                        fArray[n10] = fArray[n10] + (f5 + f6 * SmoothFontRasterState.E(f9 - f7, n7, (float)n7 + 1.0f, f10, (float)n7 + 1.0f));
                        int n11 = n2 + n7 + 1;
                        fArray2[n11] = fArray2[n11] + f6 * (f9 - f11);
                    }
                } else {
                    for (int i = 0; i < n3; ++i) {
                        float f18 = f;
                        float f19 = i;
                        f8 = i + 1;
                        f7 = f14;
                        f4 = f2;
                        f6 = ((float)i - f3) / f13 + f;
                        f5 = ((float)(i + 1) - f3) / f13 + f;
                        if (f3 < f19 && f7 > f8) {
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f3, f18, f19, f6);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f8, f5);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f8, f5, f7, f4);
                            continue;
                        }
                        if (f7 < f19 && f3 > f8) {
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f3, f18, f8, f5);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f8, f5, f19, f6);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f7, f4);
                            continue;
                        }
                        if (f3 < f19 && f7 > f19) {
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f3, f18, f19, f6);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f7, f4);
                            continue;
                        }
                        if (f7 < f19 && f3 > f19) {
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f3, f18, f19, f6);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f7, f4);
                            continue;
                        }
                        if (f3 < f8 && f7 > f8) {
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f3, f18, f8, f5);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f8, f5, f7, f4);
                            continue;
                        }
                        if (f7 < f8 && f3 > f8) {
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f3, f18, f8, f5);
                            SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f8, f5, f7, f4);
                            continue;
                        }
                        SmoothFontRasterState.A(fArray, n, i, stbTrueTypeActiveEdge, f3, f18, f7, f4);
                    }
                }
            }
            stbTrueTypeActiveEdge = stbTrueTypeActiveEdge.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l;
        }
    }

    static StbTrueTypeActiveEdge O(StbTrueTypeEdge stbTrueTypeEdge, int n, float f) {
        float f2;
        StbTrueTypeActiveEdge stbTrueTypeActiveEdge = new StbTrueTypeActiveEdge();
        stbTrueTypeActiveEdge.var_float_L = f2 = (stbTrueTypeEdge.var_float_i - stbTrueTypeEdge.var_float_d) / (stbTrueTypeEdge.var_float_u - stbTrueTypeEdge.var_float_X);
        stbTrueTypeActiveEdge.var_float_N = f2 != 0.0f ? 1.0f / f2 : 0.0f;
        stbTrueTypeActiveEdge.var_float_e = stbTrueTypeEdge.var_float_d + f2 * (f - stbTrueTypeEdge.var_float_X);
        stbTrueTypeActiveEdge.var_float_e -= (float)n;
        stbTrueTypeActiveEdge.var_float_T = stbTrueTypeEdge.var_int_W != 0 ? 1.0f : -1.0f;
        stbTrueTypeActiveEdge.var_float_j = stbTrueTypeEdge.var_float_X;
        stbTrueTypeActiveEdge.var_float_m = stbTrueTypeEdge.var_float_u;
        stbTrueTypeActiveEdge.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l = null;
        return stbTrueTypeActiveEdge;
    }

    public static int c(StbTrueTypePackContext stbTrueTypePackContext, byte[] byArray, int n, StbTrueTypePackRange[] stbTrueTypePackRangeArray, int n2) {
        int n3;
        StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
        int n4 = 1;
        for (n3 = 0; n3 < n2; ++n3) {
            for (int i = 0; i < stbTrueTypePackRangeArray[n3].var_int_b; ++i) {
                stbTrueTypePackRangeArray[n3].var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M[i].var_int_p = 0;
                stbTrueTypePackRangeArray[n3].var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M[i].var_int_q = 0;
                stbTrueTypePackRangeArray[n3].var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M[i].var_int_k = 0;
                stbTrueTypePackRangeArray[n3].var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M[i].var_int_b = 0;
            }
        }
        int n5 = 0;
        for (n3 = 0; n3 < n2; ++n3) {
            n5 += stbTrueTypePackRangeArray[n3].var_int_b;
        }
        StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray = new StbTrueTypePackRectangle[n5];
        for (n3 = 0; n3 < n5; ++n3) {
            stbTrueTypePackRectangleArray[n3] = new StbTrueTypePackRectangle();
        }
        SmoothFontRasterState.z(stbTrueTypeFontInfo, byArray, SmoothFontRasterState.c(byArray, n));
        n5 = SmoothFontRasterState.G(stbTrueTypePackContext, stbTrueTypeFontInfo, stbTrueTypePackRangeArray, n2, stbTrueTypePackRectangleArray);
        SmoothFontRasterState.H(stbTrueTypePackContext, stbTrueTypePackRectangleArray, n5);
        n4 = SmoothFontRasterState.E(stbTrueTypePackContext, stbTrueTypeFontInfo, stbTrueTypePackRangeArray, n2, stbTrueTypePackRectangleArray);
        return n4;
    }

    static int p(StbByteBufferView frame) {
        return (int)SmoothFontRasterState.u(frame, 2);
    }

    static short E$src$S$1b9gd7y(byte[] byArray, int n) {
        return (short)((byArray[n] & 0xFF) * 256 + (byArray[n + 1] & 0xFF));
    }

    public static void N(byte[] byArray, int n, float f, float[] fArray, float[] fArray2, float[] fArray3) {
        int[] nArray = new int[1];
        int[] nArray2 = new int[1];
        int[] nArray3 = new int[1];
        StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
        SmoothFontRasterState.z(stbTrueTypeFontInfo, byArray, SmoothFontRasterState.c(byArray, n));
        float f2 = f > 0.0f ? SmoothFontRasterState.V(stbTrueTypeFontInfo, f) : SmoothFontRasterState.b(stbTrueTypeFontInfo, -f);
        SmoothFontRasterState.x(stbTrueTypeFontInfo, nArray, nArray2, nArray3);
        fArray[0] = (float)nArray[0] * f2;
        fArray2[0] = (float)nArray2[0] * f2;
        fArray3[0] = (float)nArray3[0] * f2;
    }

    public static StbTrueTypeVertex[] t(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        return SmoothFontRasterState.m(stbTrueTypeFontInfo, SmoothFontRasterState.T(stbTrueTypeFontInfo, n));
    }

    static long c$src$J$1nktx4p(byte[] byArray, int n) {
        return ((long)(byArray[n] & 0xFF) << 24) + ((long)(byArray[n + 1] & 0xFF) << 16) + ((long)(byArray[n + 2] & 0xFF) << 8) + (long)(byArray[n + 3] & 0xFF);
    }

    static int q(byte[] byArray, int n, int n2) {
        int n3 = SmoothFontRasterState.x(byArray, n);
        switch (n3) {
            case 1: {
                int n4 = SmoothFontRasterState.x(byArray, n + 2);
                int n5 = SmoothFontRasterState.x(byArray, n + 4);
                int n6 = n + 6;
                if (n2 < n4 || n2 >= n4 + n5) break;
                return SmoothFontRasterState.x(byArray, n6 + 2 * (n2 - n4));
            }
            case 2: {
                int n7 = SmoothFontRasterState.x(byArray, n + 2);
                int n8 = n + 4;
                int n9 = 0;
                int n10 = n7 - 1;
                int n11 = n2;
                while (n9 <= n10) {
                    int n12 = n9 + n10 >> 1;
                    int n13 = n8 + 6 * n12;
                    int n14 = SmoothFontRasterState.x(byArray, n13);
                    int n15 = SmoothFontRasterState.x(byArray, n13 + 2);
                    if (n11 < n14) {
                        n10 = n12 - 1;
                        continue;
                    }
                    if (n11 > n15) {
                        n9 = n12 + 1;
                        continue;
                    }
                    return SmoothFontRasterState.x(byArray, n13 + 4);
                }
                break;
            }
            default: {
                return -1;
            }
        }
        return 0;
    }

    static void M(StbByteBufferView frame) {
        int n = SmoothFontRasterState.K(frame);
        if (!var_boolean_G && n < 28) {
            throw new AssertionError();
        }
        if (n == 30) {
            int n2;
            SmoothFontRasterState.K(frame, 1);
            while (frame.var_int_h < frame.var_int_y && ((n2 = SmoothFontRasterState.Q(frame)) & 0xF) != 15 && n2 >> 4 != 15) {
            }
        } else {
            SmoothFontRasterState.v(frame);
        }
    }

    public static int J(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
        if (stbTrueTypeFontInfo.var_int_L == 0 && stbTrueTypeFontInfo.var_int_G == 0) {
            return 0;
        }
        return SmoothFontRasterState.d(stbTrueTypeFontInfo, SmoothFontRasterState.T(stbTrueTypeFontInfo, n), SmoothFontRasterState.T(stbTrueTypeFontInfo, n2));
    }

    static StbTrueTypeVertex[] a$src$ALgg_vape_ui_font_stb_StbTrueTypeVertex_$1sz52d4(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        StbTrueTypeCharStringContext stbTrueTypeCharStringContext = new StbTrueTypeCharStringContext(1);
        StbTrueTypeCharStringContext stbTrueTypeCharStringContext2 = new StbTrueTypeCharStringContext(0);
        if (SmoothFontRasterState.V(stbTrueTypeFontInfo, n, stbTrueTypeCharStringContext) != 0) {
            StbTrueTypeVertex[] stbTrueTypeVertexArray = new StbTrueTypeVertex[stbTrueTypeCharStringContext.var_int_q];
            for (int i = 0; i < stbTrueTypeVertexArray.length; ++i) {
                stbTrueTypeVertexArray[i] = new StbTrueTypeVertex();
            }
            stbTrueTypeCharStringContext2.var_gg_vape_ui_font_stb_StbTrueTypeVertex_arr_V = stbTrueTypeVertexArray;
            if (SmoothFontRasterState.V(stbTrueTypeFontInfo, n, stbTrueTypeCharStringContext2) != 0) {
                if (!var_boolean_G && stbTrueTypeCharStringContext2.var_int_q != stbTrueTypeCharStringContext.var_int_q) {
                    throw new AssertionError();
                }
                return stbTrueTypeVertexArray;
            }
        }
        return null;
    }

    static int J(StbTrueTypeFontInfo stbTrueTypeFontInfo) {
        if (stbTrueTypeFontInfo.var_int_Q < 0) {
            long l = SmoothFontRasterState.v(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_U, "SVG ");
            if (l != 0L) {
                long l2 = SmoothFontRasterState.c$src$J$1nktx4p(stbTrueTypeFontInfo.var_byte_arr_S, (int)(l + 2L));
                stbTrueTypeFontInfo.var_int_Q = (int)(l + l2);
            } else {
                stbTrueTypeFontInfo.var_int_Q = 0;
            }
        }
        return stbTrueTypeFontInfo.var_int_Q;
    }

    static void b(StbTrueTypeBitmap stbTrueTypeBitmap, StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n, int n2, int n3, int n4) {
        StbTrueTypeActiveEdge stbTrueTypeActiveEdge = null;
        float[] fArray = new float[129];
        float[] fArray2 = stbTrueTypeBitmap.var_int_K > 64 ? new float[stbTrueTypeBitmap.var_int_K * 2 + 1] : fArray;
        int n5 = 0;
        int n6 = stbTrueTypeBitmap.var_int_K;
        int n7 = n4;
        stbTrueTypeEdgeArray[n].var_float_X = (float)(n4 + stbTrueTypeBitmap.var_int_c) + 1.0f;
        int n8 = 0;
        for (int i = 0; i < stbTrueTypeBitmap.var_int_c; ++i) {
            float f = (float)n7 + 0.0f;
            float f2 = (float)n7 + 1.0f;
            Arrays.fill(fArray2, n5, n5 + stbTrueTypeBitmap.var_int_K, 0.0f);
            Arrays.fill(fArray2, n6, n6 + stbTrueTypeBitmap.var_int_K + 1, 0.0f);
            StbTrueTypeActiveEdge stbTrueTypeActiveEdge2 = null;
            StbTrueTypeActiveEdge stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge;
            while (stbTrueTypeActiveEdge3 != null) {
                if (stbTrueTypeActiveEdge3.var_float_m <= f) {
                    if (stbTrueTypeActiveEdge2 != null) {
                        stbTrueTypeActiveEdge2.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l = stbTrueTypeActiveEdge3.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l;
                    } else {
                        stbTrueTypeActiveEdge = stbTrueTypeActiveEdge3.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l;
                    }
                    stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge2 != null ? stbTrueTypeActiveEdge2.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l : stbTrueTypeActiveEdge;
                    continue;
                }
                stbTrueTypeActiveEdge2 = stbTrueTypeActiveEdge3;
                stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge3.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l;
            }
            while (n8 < n && stbTrueTypeEdgeArray[n8].var_float_X <= f2) {
                if (stbTrueTypeEdgeArray[n8].var_float_X != stbTrueTypeEdgeArray[n8].var_float_u) {
                    StbTrueTypeActiveEdge stbTrueTypeActiveEdge4 = SmoothFontRasterState.O(stbTrueTypeEdgeArray[n8], n3, f);
                    if (i == 0 && n4 != 0 && stbTrueTypeActiveEdge4.var_float_m < f) {
                        stbTrueTypeActiveEdge4.var_float_m = f;
                    }
                    stbTrueTypeActiveEdge4.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l = stbTrueTypeActiveEdge;
                    stbTrueTypeActiveEdge = stbTrueTypeActiveEdge4;
                }
                ++n8;
            }
            if (stbTrueTypeActiveEdge != null) {
                SmoothFontRasterState.c(fArray2, n5, fArray2, n6, stbTrueTypeBitmap.var_int_K, stbTrueTypeActiveEdge, f);
            }
            float f3 = 0.0f;
            for (int j = 0; j < stbTrueTypeBitmap.var_int_K; ++j) {
                float f4 = fArray2[n5 + j] + (f3 += fArray2[n6 + j]);
                int n9 = (int)(f4 = Math.abs(f4) * 255.0f + 0.5f);
                if (n9 > 255) {
                    n9 = 255;
                }
                stbTrueTypeBitmap.var_byte_arr_E[stbTrueTypeBitmap.var_int_h + i * stbTrueTypeBitmap.var_int_A + j] = (byte)n9;
            }
            stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge;
            while (stbTrueTypeActiveEdge3 != null) {
                stbTrueTypeActiveEdge3.var_float_e += stbTrueTypeActiveEdge3.var_float_L;
                stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge3.var_gg_vape_ui_font_stb_StbTrueTypeActiveEdge_l;
            }
            ++n7;
        }
    }

    public static void K(StbTrueTypePackedChar[] stbTrueTypePackedCharArray, int n, int n2, int n3, float[] fArray, float[] fArray2, StbTrueTypeAlignedQuad stbTrueTypeAlignedQuad, int n4) {
        float f = 1.0f / (float)n;
        float f2 = 1.0f / (float)n2;
        StbTrueTypePackedChar stbTrueTypePackedChar = stbTrueTypePackedCharArray[n3];
        if (n4 != 0) {
            float f3 = (int)Math.floor(fArray[0] + stbTrueTypePackedChar.var_float_s + 0.5f);
            float f4 = (int)Math.floor(fArray2[0] + stbTrueTypePackedChar.var_float_G + 0.5f);
            stbTrueTypeAlignedQuad.var_float_v = f3;
            stbTrueTypeAlignedQuad.var_float_x = f4;
            stbTrueTypeAlignedQuad.var_float_K = f3 + stbTrueTypePackedChar.var_float_t - stbTrueTypePackedChar.var_float_s;
            stbTrueTypeAlignedQuad.var_float_M = f4 + stbTrueTypePackedChar.var_float_R - stbTrueTypePackedChar.var_float_G;
        } else {
            stbTrueTypeAlignedQuad.var_float_v = fArray[0] + stbTrueTypePackedChar.var_float_s;
            stbTrueTypeAlignedQuad.var_float_x = fArray2[0] + stbTrueTypePackedChar.var_float_G;
            stbTrueTypeAlignedQuad.var_float_K = fArray[0] + stbTrueTypePackedChar.var_float_t;
            stbTrueTypeAlignedQuad.var_float_M = fArray2[0] + stbTrueTypePackedChar.var_float_R;
        }
        stbTrueTypeAlignedQuad.var_float_G = (float)stbTrueTypePackedChar.var_int_b * f;
        stbTrueTypeAlignedQuad.var_float_O = (float)stbTrueTypePackedChar.var_int_k * f2;
        stbTrueTypeAlignedQuad.var_float_S = (float)stbTrueTypePackedChar.var_int_q * f;
        stbTrueTypeAlignedQuad.var_float_C = (float)stbTrueTypePackedChar.var_int_p * f2;
        fArray[0] = fArray[0] + stbTrueTypePackedChar.var_float_A;
    }

    static int K(StbByteBufferView frame) {
        if (frame.var_int_h >= frame.var_int_y) {
            return 0;
        }
        return frame.var_byte_arr_f[frame.var_int_C + frame.var_int_h] & 0xFF;
    }

    static StbByteBufferView W(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        StbByteBufferView frame = stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_N.k();
        int n2 = -1;
        SmoothFontRasterState.i(frame, 0);
        int n3 = SmoothFontRasterState.Q(frame);
        if (n3 == 0) {
            SmoothFontRasterState.K(frame, n);
            n2 = SmoothFontRasterState.Q(frame);
        } else if (n3 == 3) {
            int n4 = SmoothFontRasterState.p(frame);
            int n5 = SmoothFontRasterState.p(frame);
            for (int i = 0; i < n4; ++i) {
                int n6 = SmoothFontRasterState.Q(frame);
                int n7 = SmoothFontRasterState.p(frame);
                if (n >= n5 && n < n7) {
                    n2 = n6;
                    break;
                }
                n5 = n7;
            }
        }
        if (n2 == -1) {
            return SmoothFontRasterState.x(null, 0, 0);
        }
        return SmoothFontRasterState.u(stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g, SmoothFontRasterState.e(stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_H, n2));
    }

    public static int E(StbTrueTypePackContext stbTrueTypePackContext, StbTrueTypeFontInfo stbTrueTypeFontInfo, StbTrueTypePackRange[] stbTrueTypePackRangeArray, int n, StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray) {
        int n2 = -1;
        int n3 = 1;
        int n4 = stbTrueTypePackContext.var_int_c;
        int n5 = stbTrueTypePackContext.var_int_m;
        int n6 = 0;
        for (int i = 0; i < n; ++i) {
            float f = stbTrueTypePackRangeArray[i].var_float_E;
            float f2 = f > 0.0f ? SmoothFontRasterState.V(stbTrueTypeFontInfo, f) : SmoothFontRasterState.b(stbTrueTypeFontInfo, -f);
            stbTrueTypePackContext.var_int_c = stbTrueTypePackRangeArray[i].var_int_i;
            stbTrueTypePackContext.var_int_m = stbTrueTypePackRangeArray[i].var_int_y;
            float f3 = 1.0f / (float)stbTrueTypePackContext.var_int_c;
            float f4 = 1.0f / (float)stbTrueTypePackContext.var_int_m;
            float f5 = SmoothFontRasterState.U(stbTrueTypePackContext.var_int_c);
            float f6 = SmoothFontRasterState.U(stbTrueTypePackContext.var_int_m);
            for (int j = 0; j < stbTrueTypePackRangeArray[i].var_int_b; ++j) {
                StbTrueTypePackRectangle stbTrueTypePackRectangle = stbTrueTypePackRectangleArray[n6];
                if (stbTrueTypePackRectangle.var_int_M != 0 && stbTrueTypePackRectangle.var_int_N != 0 && stbTrueTypePackRectangle.var_int_e != 0) {
                    StbTrueTypePackedChar stbTrueTypePackedChar = stbTrueTypePackRangeArray[i].var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M[j];
                    int[] nArray = new int[1];
                    int[] nArray2 = new int[1];
                    int[] nArray3 = new int[1];
                    int[] nArray4 = new int[1];
                    int[] nArray5 = new int[1];
                    int[] nArray6 = new int[1];
                    int n7 = stbTrueTypePackRangeArray[i].var_int_arr_N == null ? stbTrueTypePackRangeArray[i].var_int_R + j : stbTrueTypePackRangeArray[i].var_int_arr_N[j];
                    int n8 = SmoothFontRasterState.T(stbTrueTypeFontInfo, n7);
                    int n9 = stbTrueTypePackContext.var_int_G;
                    stbTrueTypePackRectangle.var_int_P += n9;
                    stbTrueTypePackRectangle.var_int_J += n9;
                    stbTrueTypePackRectangle.var_int_N -= n9;
                    stbTrueTypePackRectangle.var_int_e -= n9;
                    SmoothFontRasterState.q(stbTrueTypeFontInfo, n8, nArray, nArray2);
                    SmoothFontRasterState.Z(stbTrueTypeFontInfo, n8, f2 * (float)stbTrueTypePackContext.var_int_c, f2 * (float)stbTrueTypePackContext.var_int_m, nArray3, nArray4, nArray5, nArray6);
                    SmoothFontRasterState.j(stbTrueTypeFontInfo, stbTrueTypePackContext.var_byte_arr_X, stbTrueTypePackRectangle.var_int_P + stbTrueTypePackRectangle.var_int_J * stbTrueTypePackContext.var_int_b, stbTrueTypePackRectangle.var_int_N - stbTrueTypePackContext.var_int_c + 1, stbTrueTypePackRectangle.var_int_e - stbTrueTypePackContext.var_int_m + 1, stbTrueTypePackContext.var_int_b, f2 * (float)stbTrueTypePackContext.var_int_c, f2 * (float)stbTrueTypePackContext.var_int_m, 0.0f, 0.0f, n8);
                    if (stbTrueTypePackContext.var_int_c > 1) {
                        SmoothFontRasterState.H(stbTrueTypePackContext.var_byte_arr_X, stbTrueTypePackRectangle.var_int_P + stbTrueTypePackRectangle.var_int_J * stbTrueTypePackContext.var_int_b, stbTrueTypePackRectangle.var_int_N, stbTrueTypePackRectangle.var_int_e, stbTrueTypePackContext.var_int_b, stbTrueTypePackContext.var_int_c);
                    }
                    if (stbTrueTypePackContext.var_int_m > 1) {
                        SmoothFontRasterState.p(stbTrueTypePackContext.var_byte_arr_X, stbTrueTypePackRectangle.var_int_P + stbTrueTypePackRectangle.var_int_J * stbTrueTypePackContext.var_int_b, stbTrueTypePackRectangle.var_int_N, stbTrueTypePackRectangle.var_int_e, stbTrueTypePackContext.var_int_b, stbTrueTypePackContext.var_int_m);
                    }
                    stbTrueTypePackedChar.var_int_b = (short)stbTrueTypePackRectangle.var_int_P;
                    stbTrueTypePackedChar.var_int_k = (short)stbTrueTypePackRectangle.var_int_J;
                    stbTrueTypePackedChar.var_int_q = (short)(stbTrueTypePackRectangle.var_int_P + stbTrueTypePackRectangle.var_int_N);
                    stbTrueTypePackedChar.var_int_p = (short)(stbTrueTypePackRectangle.var_int_J + stbTrueTypePackRectangle.var_int_e);
                    stbTrueTypePackedChar.var_float_A = f2 * (float)nArray[0];
                    stbTrueTypePackedChar.var_float_s = (float)nArray3[0] * f3 + f5;
                    stbTrueTypePackedChar.var_float_G = (float)nArray4[0] * f4 + f6;
                    stbTrueTypePackedChar.var_float_t = (float)(nArray3[0] + stbTrueTypePackRectangle.var_int_N) * f3 + f5;
                    stbTrueTypePackedChar.var_float_R = (float)(nArray4[0] + stbTrueTypePackRectangle.var_int_e) * f4 + f6;
                    if (n8 == 0) {
                        n2 = j;
                    }
                } else if (stbTrueTypePackContext.var_int_h != 0) {
                    n3 = 0;
                } else if (stbTrueTypePackRectangle.var_int_M != 0 && stbTrueTypePackRectangle.var_int_N == 0 && stbTrueTypePackRectangle.var_int_e == 0 && n2 >= 0) {
                    stbTrueTypePackRangeArray[i].var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M[j] = stbTrueTypePackRangeArray[i].var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M[n2];
                } else {
                    n3 = 0;
                }
                ++n6;
            }
        }
        stbTrueTypePackContext.var_int_c = n4;
        stbTrueTypePackContext.var_int_m = n5;
        return n3;
    }

    static boolean d(byte[] byArray, int n, int n2, int n3, int n4, int n5) {
        return (byArray[n] & 0xFF) == n2 && (byArray[n + 1] & 0xFF) == n3 && (byArray[n + 2] & 0xFF) == n4 && (byArray[n + 3] & 0xFF) == n5;
    }

    static int s(byte[] byArray) {
        if (SmoothFontRasterState.c$src$Z$1nktxih(byArray, 0)) {
            return 1;
        }
        if (SmoothFontRasterState.f(byArray, 0, "ttcf") && (SmoothFontRasterState.c$src$J$1nktx4p(byArray, 4) == 65536L || SmoothFontRasterState.c$src$J$1nktx4p(byArray, 4) == 131072L)) {
            return SmoothFontRasterState.B(byArray, 8);
        }
        return 0;
    }

    static void A(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (stbTrueTypeCharStringContext.var_int_J != 0) {
            SmoothFontRasterState.V(stbTrueTypeCharStringContext, n2, n3);
            if (n == 4) {
                SmoothFontRasterState.V(stbTrueTypeCharStringContext, n4, n5);
                SmoothFontRasterState.V(stbTrueTypeCharStringContext, n6, n7);
            }
        } else {
            SmoothFontRasterState.d(stbTrueTypeCharStringContext.var_gg_vape_ui_font_stb_StbTrueTypeVertex_arr_V[stbTrueTypeCharStringContext.var_int_q], n, n2, n3, n4, n5);
            stbTrueTypeCharStringContext.var_gg_vape_ui_font_stb_StbTrueTypeVertex_arr_V[stbTrueTypeCharStringContext.var_int_q].var_short_a = (short)n6;
            stbTrueTypeCharStringContext.var_gg_vape_ui_font_stb_StbTrueTypeVertex_arr_V[stbTrueTypeCharStringContext.var_int_q].var_short_s = (short)n7;
        }
        ++stbTrueTypeCharStringContext.var_int_q;
    }

    static int Q(StbByteBufferView frame) {
        if (frame.var_int_h >= frame.var_int_y) {
            return 0;
        }
        return frame.var_byte_arr_f[frame.var_int_C + frame.var_int_h++] & 0xFF;
    }

    static int E(byte[] byArray, int n, byte[] byArray2, int n2, int n3, int n4, int n5) {
        int n6 = SmoothFontRasterState.x(byArray, n + 2);
        int n7 = n + SmoothFontRasterState.x(byArray, n + 4);
        for (int i = 0; i < n6; ++i) {
            int n8 = n + 6 + 12 * i;
            int n9 = SmoothFontRasterState.x(byArray, n8 + 6);
            if (n9 != n4) continue;
            int n10 = SmoothFontRasterState.x(byArray, n8 + 0);
            int n11 = SmoothFontRasterState.x(byArray, n8 + 2);
            int n12 = SmoothFontRasterState.x(byArray, n8 + 4);
            if (!(n10 == 0 || n10 == 3 && n11 == 1 || n10 == 3 && n11 == 10)) continue;
            int n13 = SmoothFontRasterState.x(byArray, n8 + 8);
            int n14 = SmoothFontRasterState.x(byArray, n8 + 10);
            int n15 = SmoothFontRasterState.Y(byArray2, n2, n3, byArray, n7 + n14, n13);
            if (n15 < 0) continue;
            if (i + 1 < n6 && SmoothFontRasterState.x(byArray, n8 + 12 + 6) == n5 && SmoothFontRasterState.x(byArray, n8 + 12) == n10 && SmoothFontRasterState.x(byArray, n8 + 12 + 2) == n11 && SmoothFontRasterState.x(byArray, n8 + 12 + 4) == n12) {
                n13 = SmoothFontRasterState.x(byArray, n8 + 12 + 8);
                n14 = SmoothFontRasterState.x(byArray, n8 + 12 + 10);
                if (n13 == 0) {
                    if (n15 != n3) continue;
                } else if (n15 >= n3 || (byArray2[n2 + n15] & 0xFF) != 32 || !SmoothFontRasterState.d(byArray2, n2 + ++n15, n3 - n15, byArray, n7 + n14, n13)) continue;
                return 1;
            }
            if (n15 != n3) continue;
            return 1;
        }
        return 0;
    }

    static int Y(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        int n5 = 0;
        while (n4 > 0) {
            int n6 = (byArray2[n3] & 0xFF) * 256 + (byArray2[n3 + 1] & 0xFF);
            if (n6 < 128) {
                if (n5 >= n2) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != n6) {
                    return -1;
                }
            } else if (n6 < 2048) {
                if (n5 + 1 >= n2) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 192 + (n6 >> 6)) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 128 + (n6 & 0x3F)) {
                    return -1;
                }
            } else if (n6 >= 55296 && n6 < 56320) {
                int n7 = (byArray2[n3 + 2] & 0xFF) * 256 + (byArray2[n3 + 3] & 0xFF);
                if (n5 + 3 >= n2) {
                    return -1;
                }
                int n8 = (n6 - 55296 << 10) + (n7 - 56320) + 65536;
                if ((byArray[n + n5++] & 0xFF) != 240 + (n8 >> 18)) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 128 + (n8 >> 12 & 0x3F)) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 128 + (n8 >> 6 & 0x3F)) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 128 + (n8 & 0x3F)) {
                    return -1;
                }
                n3 += 2;
                n4 -= 2;
            } else {
                if (n6 >= 56320 && n6 < 57344) {
                    return -1;
                }
                if (n5 + 2 >= n2) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 224 + (n6 >> 12)) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 128 + (n6 >> 6 & 0x3F)) {
                    return -1;
                }
                if ((byArray[n + n5++] & 0xFF) != 128 + (n6 & 0x3F)) {
                    return -1;
                }
            }
            n3 += 2;
            n4 -= 2;
        }
        return n5;
    }

    public static void j(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, float f3, float f4, int n5) {
        int[] nArray = new int[1];
        int[] nArray2 = new int[1];
        StbTrueTypeVertex[] stbTrueTypeVertexArray = SmoothFontRasterState.m(stbTrueTypeFontInfo, n5);
        int n6 = stbTrueTypeVertexArray != null ? stbTrueTypeVertexArray.length : 0;
        StbTrueTypeBitmap stbTrueTypeBitmap = new StbTrueTypeBitmap();
        SmoothFontRasterState.G(stbTrueTypeFontInfo, n5, f, f2, f3, f4, nArray, nArray2, null, null);
        stbTrueTypeBitmap.var_byte_arr_E = byArray;
        stbTrueTypeBitmap.var_int_h = n;
        stbTrueTypeBitmap.var_int_K = n2;
        stbTrueTypeBitmap.var_int_c = n3;
        stbTrueTypeBitmap.var_int_A = n4;
        if (stbTrueTypeBitmap.var_int_K != 0 && stbTrueTypeBitmap.var_int_c != 0) {
            SmoothFontRasterState.b(stbTrueTypeBitmap, 0.35f, stbTrueTypeVertexArray, n6, f, f2, f3, f4, nArray[0], nArray2[0], 1);
        }
    }

    public static float b(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f) {
        int n = SmoothFontRasterState.x(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_c + 18);
        return f / (float)n;
    }

    public static byte[] q(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, float f3, float f4, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        return SmoothFontRasterState.F(stbTrueTypeFontInfo, f, f2, f3, f4, SmoothFontRasterState.T(stbTrueTypeFontInfo, n), nArray, nArray2, nArray3, nArray4);
    }

    public static void Y(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        nArray[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_c + 36);
        nArray2[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_c + 38);
        nArray3[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_c + 40);
        nArray4[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_c + 42);
    }

    static StbTrueTypePoint[] P(StbTrueTypeVertex[] stbTrueTypeVertexArray, int n, float f, int[][] nArray, int[] nArray2) {
        int n2;
        StbTrueTypePoint[] stbTrueTypePointArray = null;
        int[] nArray3 = new int[]{0};
        float f2 = f * f;
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < n; ++n2) {
            if (stbTrueTypeVertexArray[n2].var_int_O != 1) continue;
            ++n3;
        }
        nArray2[0] = n3;
        if (n3 == 0) {
            return null;
        }
        nArray[0] = new int[n3];
        for (int i = 0; i < 2; ++i) {
            float f3 = 0.0f;
            float f4 = 0.0f;
            if (i == 1) {
                stbTrueTypePointArray = new StbTrueTypePoint[nArray3[0]];
                for (n2 = 0; n2 < stbTrueTypePointArray.length; ++n2) {
                    stbTrueTypePointArray[n2] = new StbTrueTypePoint();
                }
            }
            nArray3[0] = 0;
            n3 = -1;
            block9: for (n2 = 0; n2 < n; ++n2) {
                switch (stbTrueTypeVertexArray[n2].var_int_O) {
                    case 1: {
                        if (n3 >= 0) {
                            nArray[0][n3] = nArray3[0] - n4;
                        }
                        ++n3;
                        n4 = nArray3[0];
                        f3 = stbTrueTypeVertexArray[n2].var_short_f;
                        f4 = stbTrueTypeVertexArray[n2].var_short_H;
                        int n5 = nArray3[0];
                        nArray3[0] = n5 + 1;
                        SmoothFontRasterState.r(stbTrueTypePointArray, n5, f3, f4);
                        continue block9;
                    }
                    case 2: {
                        f3 = stbTrueTypeVertexArray[n2].var_short_f;
                        f4 = stbTrueTypeVertexArray[n2].var_short_H;
                        int n6 = nArray3[0];
                        nArray3[0] = n6 + 1;
                        SmoothFontRasterState.r(stbTrueTypePointArray, n6, f3, f4);
                        continue block9;
                    }
                    case 3: {
                        SmoothFontRasterState.y(stbTrueTypePointArray, nArray3, f3, f4, stbTrueTypeVertexArray[n2].var_short_h, stbTrueTypeVertexArray[n2].var_short_D, stbTrueTypeVertexArray[n2].var_short_f, stbTrueTypeVertexArray[n2].var_short_H, f2, 0);
                        f3 = stbTrueTypeVertexArray[n2].var_short_f;
                        f4 = stbTrueTypeVertexArray[n2].var_short_H;
                        continue block9;
                    }
                    case 4: {
                        SmoothFontRasterState.w(stbTrueTypePointArray, nArray3, f3, f4, stbTrueTypeVertexArray[n2].var_short_h, stbTrueTypeVertexArray[n2].var_short_D, stbTrueTypeVertexArray[n2].var_short_a, stbTrueTypeVertexArray[n2].var_short_s, stbTrueTypeVertexArray[n2].var_short_f, stbTrueTypeVertexArray[n2].var_short_H, f2, 0);
                        f3 = stbTrueTypeVertexArray[n2].var_short_f;
                        f4 = stbTrueTypeVertexArray[n2].var_short_H;
                    }
                }
            }
            nArray[0][n3] = nArray3[0] - n4;
        }
        return stbTrueTypePointArray;
    }

    static int i(float f, float f2, int n, StbTrueTypeVertex[] stbTrueTypeVertexArray) {
        float[] fArray = new float[2];
        float[] fArray2 = new float[]{1.0f, 0.0f};
        int n2 = 0;
        float f3 = f2 % 1.0f;
        if (f3 < 0.01f) {
            f2 += 0.01f;
        } else if (f3 > 0.99f) {
            f2 -= 0.01f;
        }
        fArray[0] = f;
        fArray[1] = f2;
        for (int i = 0; i < n; ++i) {
            short s;
            short s2;
            short s3;
            short s4;
            if (stbTrueTypeVertexArray[i].var_int_O == 2) {
                s4 = stbTrueTypeVertexArray[i - 1].var_short_f;
                s3 = stbTrueTypeVertexArray[i - 1].var_short_H;
                s2 = stbTrueTypeVertexArray[i].var_short_f;
                s = stbTrueTypeVertexArray[i].var_short_H;
                if (f2 > (float)Math.min(s3, s) && f2 < (float)Math.max(s3, s) && f > (float)Math.min(s4, s2)) {
                    float f4;
                    float f42 = (f2 - (float)s3) / (float)(s - s3) * (float)(s2 - s4) + (float)s4;
                    if (f4 < f) {
                        n2 += s3 < s ? 1 : -1;
                    }
                }
            }
            if (stbTrueTypeVertexArray[i].var_int_O != 3) continue;
            s4 = stbTrueTypeVertexArray[i - 1].var_short_f;
            s3 = stbTrueTypeVertexArray[i - 1].var_short_H;
            s2 = stbTrueTypeVertexArray[i].var_short_h;
            s = stbTrueTypeVertexArray[i].var_short_D;
            short s5 = stbTrueTypeVertexArray[i].var_short_f;
            short s6 = stbTrueTypeVertexArray[i].var_short_H;
            int n3 = Math.min(s4, Math.min(s2, s5));
            int n4 = Math.min(s3, Math.min(s, s6));
            int n5 = Math.max(s3, Math.max(s, s6));
            if (!(f2 > (float)n4 && f2 < (float)n5 && f > (float)n3)) continue;
            float[] fArray3 = new float[2];
            float[] fArray4 = new float[2];
            float[] fArray5 = new float[2];
            float[][] fArray6 = new float[2][2];
            fArray3[0] = s4;
            fArray3[1] = s3;
            fArray4[0] = s2;
            fArray4[1] = s;
            fArray5[0] = s5;
            fArray5[1] = s6;
            if (SmoothFontRasterState.d(fArray3, fArray4) || SmoothFontRasterState.d(fArray4, fArray5)) {
                float f5;
                s4 = stbTrueTypeVertexArray[i - 1].var_short_f;
                s3 = stbTrueTypeVertexArray[i - 1].var_short_H;
                s2 = stbTrueTypeVertexArray[i].var_short_f;
                s = stbTrueTypeVertexArray[i].var_short_H;
                if (!(f2 > (float)Math.min(s3, s)) || !(f2 < (float)Math.max(s3, s)) || !(f > (float)Math.min(s4, s2)) || !((f5 = (f2 - (float)s3) / (float)(s - s3) * (float)(s2 - s4) + (float)s4) < f)) continue;
                n2 += s3 < s ? 1 : -1;
                continue;
            }
            int n6 = SmoothFontRasterState.J(fArray, fArray2, fArray3, fArray4, fArray5, fArray6);
            if (n6 >= 1 && fArray6[0][0] < 0.0f) {
                n2 += fArray6[0][1] < 0.0f ? -1 : 1;
            }
            if (n6 < 2 || !(fArray6[1][0] < 0.0f)) continue;
            n2 += fArray6[1][1] < 0.0f ? -1 : 1;
        }
        return n2;
    }

    static boolean S(byte[] byArray, int n, byte[] byArray2, int n2, int n3, int n4) {
        long l;
        if (!SmoothFontRasterState.c$src$Z$1nktxih(byArray, n)) {
            return false;
        }
        if (n4 != 0 && (SmoothFontRasterState.x(byArray, (int)(l = SmoothFontRasterState.v(byArray, n, "head")) + 44) & 7) != (n4 & 7)) {
            return false;
        }
        l = SmoothFontRasterState.v(byArray, n, "name");
        if (l == 0L) {
            return false;
        }
        if (n4 != 0) {
            if (SmoothFontRasterState.E(byArray, (int)l, byArray2, n2, n3, 16, -1) != 0) {
                return true;
            }
            if (SmoothFontRasterState.E(byArray, (int)l, byArray2, n2, n3, 1, -1) != 0) {
                return true;
            }
            if (SmoothFontRasterState.E(byArray, (int)l, byArray2, n2, n3, 3, -1) != 0) {
                return true;
            }
        } else {
            if (SmoothFontRasterState.E(byArray, (int)l, byArray2, n2, n3, 16, 17) != 0) {
                return true;
            }
            if (SmoothFontRasterState.E(byArray, (int)l, byArray2, n2, n3, 1, 2) != 0) {
                return true;
            }
            if (SmoothFontRasterState.E(byArray, (int)l, byArray2, n2, n3, 3, -1) != 0) {
                return true;
            }
        }
        return false;
    }

    static void A(float[] fArray, int n, int n2, StbTrueTypeActiveEdge stbTrueTypeActiveEdge, float f, float f2, float f3, float f4) {
        if (f2 == f4) {
            return;
        }
        if (!var_boolean_G && !(f2 < f4)) {
            throw new AssertionError();
        }
        if (!var_boolean_G && !(stbTrueTypeActiveEdge.var_float_j <= stbTrueTypeActiveEdge.var_float_m)) {
            throw new AssertionError();
        }
        if (f2 > stbTrueTypeActiveEdge.var_float_m) {
            return;
        }
        if (f4 < stbTrueTypeActiveEdge.var_float_j) {
            return;
        }
        if (f2 < stbTrueTypeActiveEdge.var_float_j) {
            f += (f3 - f) * (stbTrueTypeActiveEdge.var_float_j - f2) / (f4 - f2);
            f2 = stbTrueTypeActiveEdge.var_float_j;
        }
        if (f4 > stbTrueTypeActiveEdge.var_float_m) {
            f3 += (f3 - f) * (stbTrueTypeActiveEdge.var_float_m - f4) / (f4 - f2);
            f4 = stbTrueTypeActiveEdge.var_float_m;
        }
        if (f <= (float)n2 && f3 <= (float)n2) {
            int n3 = n + n2;
            fArray[n3] = fArray[n3] + stbTrueTypeActiveEdge.var_float_T * (f4 - f2);
        } else if (!(f >= (float)(n2 + 1)) || !(f3 >= (float)(n2 + 1))) {
            int n4 = n + n2;
            fArray[n4] = fArray[n4] + stbTrueTypeActiveEdge.var_float_T * (f4 - f2) * (1.0f - (f - (float)n2 + (f3 - (float)n2)) / 2.0f);
        }
    }

    static int y(StbTrueTypePoint[] stbTrueTypePointArray, int[] nArray, float f, float f2, float f3, float f4, float f5, float f6, float f7, int n) {
        float f8 = (f + 2.0f * f3 + f5) / 4.0f;
        float f9 = (f2 + 2.0f * f4 + f6) / 4.0f;
        float f10 = (f + f5) / 2.0f - f8;
        float f11 = (f2 + f6) / 2.0f - f9;
        if (n > 16) {
            return 1;
        }
        if (f10 * f10 + f11 * f11 > f7) {
            SmoothFontRasterState.y(stbTrueTypePointArray, nArray, f, f2, (f + f3) / 2.0f, (f2 + f4) / 2.0f, f8, f9, f7, n + 1);
            SmoothFontRasterState.y(stbTrueTypePointArray, nArray, f8, f9, (f3 + f5) / 2.0f, (f4 + f6) / 2.0f, f5, f6, f7, n + 1);
        } else {
            SmoothFontRasterState.r(stbTrueTypePointArray, nArray[0], f5, f6);
            nArray[0] = nArray[0] + 1;
        }
        return 1;
    }

    public static void n(StbTrueTypePackContext stbTrueTypePackContext, int n, int n2) {
        if (!var_boolean_G && n > 8) {
            throw new AssertionError();
        }
        if (!var_boolean_G && n2 > 8) {
            throw new AssertionError();
        }
        if (n <= 8) {
            stbTrueTypePackContext.var_int_c = n;
        }
        if (n2 <= 8) {
            stbTrueTypePackContext.var_int_m = n2;
        }
    }

    static int L(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n) {
        long l;
        stbTrueTypeFontInfo.var_byte_arr_S = byArray;
        stbTrueTypeFontInfo.var_int_U = n;
        stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g = SmoothFontRasterState.x(null, 0, 0);
        long l2 = SmoothFontRasterState.v(byArray, n, "cmap");
        stbTrueTypeFontInfo.var_int_v = (int)SmoothFontRasterState.v(byArray, n, "loca");
        stbTrueTypeFontInfo.var_int_c = (int)SmoothFontRasterState.v(byArray, n, "head");
        stbTrueTypeFontInfo.var_int_p = (int)SmoothFontRasterState.v(byArray, n, "glyf");
        stbTrueTypeFontInfo.var_int_w = (int)SmoothFontRasterState.v(byArray, n, "hhea");
        stbTrueTypeFontInfo.var_int_d = (int)SmoothFontRasterState.v(byArray, n, "hmtx");
        stbTrueTypeFontInfo.var_int_L = (int)SmoothFontRasterState.v(byArray, n, "kern");
        stbTrueTypeFontInfo.var_int_G = (int)SmoothFontRasterState.v(byArray, n, "GPOS");
        if (l2 == 0L || stbTrueTypeFontInfo.var_int_c == 0 || stbTrueTypeFontInfo.var_int_w == 0 || stbTrueTypeFontInfo.var_int_d == 0) {
            return 0;
        }
        if (stbTrueTypeFontInfo.var_int_p != 0) {
            if (stbTrueTypeFontInfo.var_int_v == 0) {
                return 0;
            }
        } else {
            long l3 = 2L;
            long l4 = 0L;
            long l5 = 0L;
            long l6 = 0L;
            long l7 = SmoothFontRasterState.v(byArray, n, "CFF ");
            if (l7 == 0L) {
                return 0;
            }
            stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_H = SmoothFontRasterState.x(null, 0, 0);
            stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_N = SmoothFontRasterState.x(null, 0, 0);
            stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g = SmoothFontRasterState.x(byArray, (int)l7, 0x20000000);
            StbByteBufferView frame = stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g.k();
            SmoothFontRasterState.K(frame, 2);
            SmoothFontRasterState.i(frame, SmoothFontRasterState.Q(frame));
            SmoothFontRasterState.B(frame);
            StbByteBufferView frame2 = SmoothFontRasterState.B(frame);
            StbByteBufferView frame3 = SmoothFontRasterState.e(frame2, 0);
            SmoothFontRasterState.B(frame);
            stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_j = SmoothFontRasterState.B(frame);
            long[] lArray = new long[]{l4};
            SmoothFontRasterState.X(frame3, 17, 1, lArray);
            l4 = lArray[0];
            long[] lArray2 = new long[]{l3};
            SmoothFontRasterState.X(frame3, 262, 1, lArray2);
            l3 = lArray2[0];
            long[] lArray3 = new long[]{l5};
            SmoothFontRasterState.X(frame3, 292, 1, lArray3);
            l5 = lArray3[0];
            long[] lArray4 = new long[]{l6};
            SmoothFontRasterState.X(frame3, 293, 1, lArray4);
            l6 = lArray4[0];
            stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_J = SmoothFontRasterState.u(frame, frame3);
            if (l3 != 2L) {
                return 0;
            }
            if (l4 == 0L) {
                return 0;
            }
            if (l5 != 0L) {
                if (l6 == 0L) {
                    return 0;
                }
                SmoothFontRasterState.i(frame, (int)l5);
                stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_H = SmoothFontRasterState.B(frame);
                stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_N = SmoothFontRasterState.E(frame, (int)l6, frame.var_int_y - (int)l6);
            }
            SmoothFontRasterState.i(frame, (int)l4);
            stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_z = SmoothFontRasterState.B(frame);
        }
        stbTrueTypeFontInfo.var_int_h = (l = SmoothFontRasterState.v(byArray, n, "maxp")) != 0L ? SmoothFontRasterState.x(byArray, (int)(l + 4L)) : 65535;
        stbTrueTypeFontInfo.var_int_Q = -1;
        int n2 = SmoothFontRasterState.x(byArray, (int)(l2 + 2L));
        stbTrueTypeFontInfo.var_int_Y = 0;
        block7: for (int i = 0; i < n2; ++i) {
            long l8 = l2 + 4L + (long)(8 * i);
            switch (SmoothFontRasterState.x(byArray, (int)l8)) {
                case 3: {
                    switch (SmoothFontRasterState.x(byArray, (int)l8 + 2)) {
                        case 1: 
                        case 10: {
                            stbTrueTypeFontInfo.var_int_Y = (int)(l2 + SmoothFontRasterState.c$src$J$1nktx4p(byArray, (int)l8 + 4));
                        }
                    }
                    continue block7;
                }
                case 0: {
                    stbTrueTypeFontInfo.var_int_Y = (int)(l2 + SmoothFontRasterState.c$src$J$1nktx4p(byArray, (int)l8 + 4));
                }
            }
        }
        if (stbTrueTypeFontInfo.var_int_Y == 0) {
            return 0;
        }
        stbTrueTypeFontInfo.var_int_T = SmoothFontRasterState.x(byArray, stbTrueTypeFontInfo.var_int_c + 50);
        return 1;
    }

    public static int v(StbTrueTypeFontInfo stbTrueTypeFontInfo) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        int n = stbTrueTypeFontInfo.var_int_L;
        if (n == 0) {
            return 0;
        }
        if (SmoothFontRasterState.x(byArray, n + 2) < 1) {
            return 0;
        }
        if (SmoothFontRasterState.x(byArray, n + 8) != 1) {
            return 0;
        }
        return SmoothFontRasterState.x(byArray, n + 10);
    }

    public static int z(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n) {
        return SmoothFontRasterState.L(stbTrueTypeFontInfo, byArray, n);
    }

    static int E(byte[] byArray, int n) {
        if (SmoothFontRasterState.c$src$Z$1nktxih(byArray, 0)) {
            return n == 0 ? 0 : -1;
        }
        if (SmoothFontRasterState.f(byArray, 0, "ttcf") && (SmoothFontRasterState.c$src$J$1nktx4p(byArray, 4) == 65536L || SmoothFontRasterState.c$src$J$1nktx4p(byArray, 4) == 131072L)) {
            int n2 = SmoothFontRasterState.B(byArray, 8);
            if (n >= n2) {
                return -1;
            }
            return (int)SmoothFontRasterState.c$src$J$1nktx4p(byArray, 12 + n * 4);
        }
        return -1;
    }

    static void d(StbTrueTypeCharStringContext stbTrueTypeCharStringContext) {
        if (stbTrueTypeCharStringContext.var_float_s != stbTrueTypeCharStringContext.var_float_C || stbTrueTypeCharStringContext.var_float_p != stbTrueTypeCharStringContext.var_float_o) {
            SmoothFontRasterState.A(stbTrueTypeCharStringContext, 2, (int)stbTrueTypeCharStringContext.var_float_s, (int)stbTrueTypeCharStringContext.var_float_p, 0, 0, 0, 0);
        }
    }

    static void H(byte[] byArray, int n, int n2, int n3, int n4, int n5) {
        byte[] byArray2 = new byte[8];
        int n6 = n2 - n5;
        Arrays.fill(byArray2, 0);
        for (int i = 0; i < n3; ++i) {
            int n7;
            Arrays.fill(byArray2, 0, n5, 0);
            int n8 = 0;
            switch (n5) {
                case 2: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7];
                        byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 2);
                    }
                    break;
                }
                case 3: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7];
                        byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 3);
                    }
                    break;
                }
                case 4: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7];
                        byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 4);
                    }
                    break;
                }
                case 5: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7];
                        byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / 5);
                    }
                    break;
                }
                default: {
                    for (n7 = 0; n7 <= n6; ++n7) {
                        byArray2[n7 + n5 & 7] = byArray[n + n7];
                        byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 0xFF) - (byArray2[n7 & 7] & 0xFF)) / n5);
                    }
                }
            }
            while (n7 < n2) {
                byArray[n + n7] = (byte)((n8 -= byArray2[n7 & 7] & 0xFF) / n5);
                ++n7;
            }
            n += n4;
        }
    }

    public static StbTrueTypeVertex[] m(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        if (stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g.var_int_y == 0) {
            return SmoothFontRasterState.o(stbTrueTypeFontInfo, n);
        }
        return SmoothFontRasterState.a$src$ALgg_vape_ui_font_stb_StbTrueTypeVertex_$1sz52d4(stbTrueTypeFontInfo, n);
    }

    public static void u(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, float f3, float f4, int n5) {
        SmoothFontRasterState.j(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, f3, f4, SmoothFontRasterState.T(stbTrueTypeFontInfo, n5));
    }

    public static void F(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, int n5) {
        SmoothFontRasterState.u(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, 0.0f, 0.0f, n5);
    }

    static int L(byte[] byArray, int n, int n2) {
        int n3 = SmoothFontRasterState.x(byArray, n);
        switch (n3) {
            case 1: {
                int n4 = SmoothFontRasterState.x(byArray, n + 2);
                int n5 = 0;
                int n6 = n4 - 1;
                int n7 = n2;
                while (n5 <= n6) {
                    int n8 = n + 4;
                    int n9 = n5 + n6 >> 1;
                    int n10 = SmoothFontRasterState.x(byArray, n8 + 2 * n9);
                    int n11 = n10;
                    if (n7 < n11) {
                        n6 = n9 - 1;
                        continue;
                    }
                    if (n7 > n11) {
                        n5 = n9 + 1;
                        continue;
                    }
                    return n9;
                }
                break;
            }
            case 2: {
                int n12 = SmoothFontRasterState.x(byArray, n + 2);
                int n13 = n + 4;
                int n14 = 0;
                int n15 = n12 - 1;
                int n16 = n2;
                while (n14 <= n15) {
                    int n17 = n14 + n15 >> 1;
                    int n18 = n13 + 6 * n17;
                    int n19 = SmoothFontRasterState.x(byArray, n18);
                    int n20 = SmoothFontRasterState.x(byArray, n18 + 2);
                    if (n16 < n19) {
                        n15 = n17 - 1;
                        continue;
                    }
                    if (n16 > n20) {
                        n14 = n17 + 1;
                        continue;
                    }
                    int n21 = SmoothFontRasterState.x(byArray, n18 + 4);
                    return n21 + n2 - n19;
                }
                break;
            }
            default: {
                return -1;
            }
        }
        return -1;
    }

    public static int g(String string, String string2) {
        byte[] byArray;
        byte[] byArray2 = string.getBytes();
        return SmoothFontRasterState.d(byArray2, 0, byArray2.length, byArray = string2.getBytes(), 0, byArray.length) ? 1 : 0;
    }

    static int X(float f, float f2, float f3, float[] fArray) {
        float f4 = -f / 3.0f;
        float f5 = f * (2.0f * f * f - 9.0f * f2) / 27.0f + f3;
        float f6 = f2 - f * f / 3.0f;
        float f7 = f6 * f6 * f6;
        float f8 = f5 * f5 + 4.0f * f7 / 27.0f;
        if (f8 >= 0.0f) {
            float f9 = (float)Math.sqrt(f8);
            float f10 = (-f5 + f9) / 2.0f;
            float f11 = (-f5 - f9) / 2.0f;
            f10 = SmoothFontRasterState.w(f10);
            f11 = SmoothFontRasterState.w(f11);
            fArray[0] = f4 + f10 + f11;
            return 1;
        }
        float f12 = (float)Math.sqrt(-f6 / 3.0f);
        float f13 = (float)Math.acos(-Math.sqrt(-27.0f / f7) * (double)f5 / 2.0) / 3.0f;
        float f14 = (float)Math.cos(f13);
        float f15 = (float)Math.cos((double)f13 - 1.570796) * 1.7320508f;
        fArray[0] = f4 + f12 * 2.0f * f14;
        fArray[1] = f4 - f12 * (f14 + f15);
        fArray[2] = f4 - f12 * (f14 - f15);
        return 3;
    }

    static long u(StbByteBufferView frame, int n) {
        long l = 0L;
        if (!(var_boolean_G || n >= 1 && n <= 4)) {
            throw new AssertionError();
        }
        for (int i = 0; i < n; ++i) {
            l = l << 8 | (long)SmoothFontRasterState.Q(frame);
        }
        return l;
    }

    public static int U(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray) {
        return SmoothFontRasterState.B(stbTrueTypeFontInfo, SmoothFontRasterState.T(stbTrueTypeFontInfo, n), nArray);
    }

    public static int A(StbTrueTypeFontInfo stbTrueTypeFontInfo, StbTrueTypeKerningEntry[] stbTrueTypeKerningEntryArray, int n) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        int n2 = stbTrueTypeFontInfo.var_int_L;
        if (n2 == 0) {
            return 0;
        }
        if (SmoothFontRasterState.x(byArray, n2 + 2) < 1) {
            return 0;
        }
        if (SmoothFontRasterState.x(byArray, n2 + 8) != 1) {
            return 0;
        }
        int n3 = SmoothFontRasterState.x(byArray, n2 + 10);
        if (n < n3) {
            n3 = n;
        }
        for (int i = 0; i < n3; ++i) {
            stbTrueTypeKerningEntryArray[i].var_int_q = SmoothFontRasterState.x(byArray, n2 + 18 + i * 6);
            stbTrueTypeKerningEntryArray[i].var_int_o = SmoothFontRasterState.x(byArray, n2 + 20 + i * 6);
            stbTrueTypeKerningEntryArray[i].var_int_y = SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n2 + 22 + i * 6);
        }
        return n3;
    }

    public static int G(StbTrueTypePackContext stbTrueTypePackContext, StbTrueTypeFontInfo stbTrueTypeFontInfo, StbTrueTypePackRange[] stbTrueTypePackRangeArray, int n, StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray) {
        boolean bl = false;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            float f = stbTrueTypePackRangeArray[i].var_float_E;
            float f2 = f > 0.0f ? SmoothFontRasterState.V(stbTrueTypeFontInfo, f) : SmoothFontRasterState.b(stbTrueTypeFontInfo, -f);
            stbTrueTypePackRangeArray[i].var_int_i = stbTrueTypePackContext.var_int_c;
            stbTrueTypePackRangeArray[i].var_int_y = stbTrueTypePackContext.var_int_m;
            for (int j = 0; j < stbTrueTypePackRangeArray[i].var_int_b; ++j) {
                int[] nArray = new int[1];
                int[] nArray2 = new int[1];
                int[] nArray3 = new int[1];
                int[] nArray4 = new int[1];
                int n3 = stbTrueTypePackRangeArray[i].var_int_arr_N == null ? stbTrueTypePackRangeArray[i].var_int_R + j : stbTrueTypePackRangeArray[i].var_int_arr_N[j];
                int n4 = SmoothFontRasterState.T(stbTrueTypeFontInfo, n3);
                if (n4 == 0 && (stbTrueTypePackContext.var_int_h != 0 || bl)) {
                    stbTrueTypePackRectangleArray[n2].var_int_e = 0;
                    stbTrueTypePackRectangleArray[n2].var_int_N = 0;
                } else {
                    SmoothFontRasterState.G(stbTrueTypeFontInfo, n4, f2 * (float)stbTrueTypePackContext.var_int_c, f2 * (float)stbTrueTypePackContext.var_int_m, 0.0f, 0.0f, nArray, nArray2, nArray3, nArray4);
                    stbTrueTypePackRectangleArray[n2].var_int_N = nArray3[0] - nArray[0] + stbTrueTypePackContext.var_int_G + stbTrueTypePackContext.var_int_c - 1;
                    stbTrueTypePackRectangleArray[n2].var_int_e = nArray4[0] - nArray2[0] + stbTrueTypePackContext.var_int_G + stbTrueTypePackContext.var_int_m - 1;
                    if (n4 == 0) {
                        bl = true;
                    }
                }
                ++n2;
            }
        }
        return n2;
    }

    static StbByteBufferView p(StbByteBufferView frame, int n) {
        int n2 = SmoothFontRasterState.q(frame);
        int n3 = 107;
        if (n2 >= 33900) {
            n3 = 32768;
        } else if (n2 >= 1240) {
            n3 = 1131;
        }
        if ((n += n3) < 0 || n >= n2) {
            return SmoothFontRasterState.x(null, 0, 0);
        }
        return SmoothFontRasterState.e(frame, n);
    }

    static float E(float f, float f2, float f3, float f4, float f5) {
        return SmoothFontRasterState.A(f, f3 - f2, f5 - f4);
    }

    static int V(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, StbTrueTypeCharStringContext stbTrueTypeCharStringContext) {
        boolean bl = true;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        boolean bl2 = false;
        float[] fArray = new float[48];
        StbByteBufferView[] frameArray = new StbByteBufferView[10];
        StbByteBufferView frame = stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_J.k();
        StbByteBufferView frame2 = SmoothFontRasterState.e(stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_z, n);
        while (frame2.var_int_h < frame2.var_int_y) {
            boolean bl3 = true;
            int n5 = SmoothFontRasterState.Q(frame2);
            block0 : switch (n5) {
                case 19: 
                case 20: {
                    if (bl) {
                        n2 += n4 / 2;
                    }
                    bl = false;
                    SmoothFontRasterState.K(frame2, (n2 + 7) / 8);
                    break;
                }
                case 1: 
                case 3: 
                case 18: 
                case 23: {
                    n2 += n4 / 2;
                    break;
                }
                case 21: {
                    bl = false;
                    if (n4 < 2) {
                        return 0;
                    }
                    SmoothFontRasterState.Q(stbTrueTypeCharStringContext, fArray[n4 - 2], fArray[n4 - 1]);
                    break;
                }
                case 4: {
                    bl = false;
                    if (n4 < 1) {
                        return 0;
                    }
                    SmoothFontRasterState.Q(stbTrueTypeCharStringContext, 0.0f, fArray[n4 - 1]);
                    break;
                }
                case 22: {
                    bl = false;
                    if (n4 < 1) {
                        return 0;
                    }
                    SmoothFontRasterState.Q(stbTrueTypeCharStringContext, fArray[n4 - 1], 0.0f);
                    break;
                }
                case 5: {
                    int n6 = 0;
                    if (n4 < 2) {
                        return 0;
                    }
                    while (n6 + 1 < n4) {
                        SmoothFontRasterState.z(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1]);
                        n6 += 2;
                    }
                    break;
                }
                case 7: {
                    int n6 = 0;
                    if (n4 < 1) {
                        return 0;
                    }
                    for (n6 = 0; n6 < n4; ++n6) {
                        SmoothFontRasterState.z(stbTrueTypeCharStringContext, 0.0f, fArray[n6]);
                        if (++n6 >= n4) break block0;
                        SmoothFontRasterState.z(stbTrueTypeCharStringContext, fArray[n6], 0.0f);
                    }
                    break;
                }
                case 6: {
                    if (n4 < 1) {
                        return 0;
                    }
                    for (int n6 = 0; n6 < n4; ++n6) {
                        SmoothFontRasterState.z(stbTrueTypeCharStringContext, fArray[n6], 0.0f);
                        if (++n6 >= n4) break block0;
                        SmoothFontRasterState.z(stbTrueTypeCharStringContext, 0.0f, fArray[n6]);
                    }
                    break;
                }
                case 31: {
                    int n6 = 0;
                    if (n4 < 4) {
                        return 0;
                    }
                    while (n6 + 3 < n4) {
                        SmoothFontRasterState.s(stbTrueTypeCharStringContext, fArray[n6], 0.0f, fArray[n6 + 1], fArray[n6 + 2], n4 - n6 == 5 ? fArray[n6 + 4] : 0.0f, fArray[n6 + 3]);
                        if ((n6 += 4) + 3 >= n4) break block0;
                        SmoothFontRasterState.s(stbTrueTypeCharStringContext, 0.0f, fArray[n6], fArray[n6 + 1], fArray[n6 + 2], fArray[n6 + 3], n4 - n6 == 5 ? fArray[n6 + 4] : 0.0f);
                        n6 += 4;
                    }
                    break;
                }
                case 30: {
                    int n6 = 0;
                    if (n4 < 4) {
                        return 0;
                    }
                    while (n6 + 3 < n4) {
                        SmoothFontRasterState.s(stbTrueTypeCharStringContext, 0.0f, fArray[n6], fArray[n6 + 1], fArray[n6 + 2], fArray[n6 + 3], n4 - n6 == 5 ? fArray[n6 + 4] : 0.0f);
                        if ((n6 += 4) + 3 >= n4) break block0;
                        SmoothFontRasterState.s(stbTrueTypeCharStringContext, fArray[n6], 0.0f, fArray[n6 + 1], fArray[n6 + 2], n4 - n6 == 5 ? fArray[n6 + 4] : 0.0f, fArray[n6 + 3]);
                        n6 += 4;
                    }
                    break;
                }
                case 8: {
                    int n6 = 0;
                    if (n4 < 6) {
                        return 0;
                    }
                    while (n6 + 5 < n4) {
                        SmoothFontRasterState.s(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1], fArray[n6 + 2], fArray[n6 + 3], fArray[n6 + 4], fArray[n6 + 5]);
                        n6 += 6;
                    }
                    break;
                }
                case 24: {
                    int n6 = 0;
                    if (n4 < 8) {
                        return 0;
                    }
                    while (n6 + 5 < n4 - 2) {
                        SmoothFontRasterState.s(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1], fArray[n6 + 2], fArray[n6 + 3], fArray[n6 + 4], fArray[n6 + 5]);
                        n6 += 6;
                    }
                    if (n6 + 1 >= n4) {
                        return 0;
                    }
                    SmoothFontRasterState.z(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1]);
                    break;
                }
                case 25: {
                    int n6 = 0;
                    if (n4 < 8) {
                        return 0;
                    }
                    while (n6 + 1 < n4 - 6) {
                        SmoothFontRasterState.z(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1]);
                        n6 += 2;
                    }
                    if (n6 + 5 >= n4) {
                        return 0;
                    }
                    SmoothFontRasterState.s(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1], fArray[n6 + 2], fArray[n6 + 3], fArray[n6 + 4], fArray[n6 + 5]);
                    break;
                }
                case 26: 
                case 27: {
                    int n6 = 0;
                    if (n4 < 4) {
                        return 0;
                    }
                    float f = 0.0f;
                    if ((n4 & 1) != 0) {
                        f = fArray[n6];
                        ++n6;
                    }
                    while (n6 + 3 < n4) {
                        if (n5 == 27) {
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, fArray[n6], f, fArray[n6 + 1], fArray[n6 + 2], fArray[n6 + 3], 0.0f);
                        } else {
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f, fArray[n6], fArray[n6 + 1], fArray[n6 + 2], 0.0f, fArray[n6 + 3]);
                        }
                        f = 0.0f;
                        n6 += 4;
                    }
                    break;
                }
                case 10: {
                    if (!bl2) {
                        if (stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_N.var_int_y != 0) {
                            frame = SmoothFontRasterState.W(stbTrueTypeFontInfo, n);
                        }
                        bl2 = true;
                    }
                }
                case 29: {
                    if (n4 < 1) {
                        return 0;
                    }
                    int n7 = (int)fArray[--n4];
                    if (n3 >= 10) {
                        return 0;
                    }
                    frameArray[n3++] = frame2.k();
                    frame2 = SmoothFontRasterState.p(n5 == 10 ? frame : stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_j, n7);
                    if (frame2.var_int_y == 0) {
                        return 0;
                    }
                    frame2.var_int_h = 0;
                    bl3 = false;
                    break;
                }
                case 11: {
                    if (n3 <= 0) {
                        return 0;
                    }
                    frame2 = frameArray[--n3];
                    bl3 = false;
                    break;
                }
                case 14: {
                    SmoothFontRasterState.d(stbTrueTypeCharStringContext);
                    return 1;
                }
                case 12: {
                    float f;
                    int n8 = SmoothFontRasterState.Q(frame2);
                    switch (n8) {
                        case 34: {
                            if (n4 < 7) {
                                return 0;
                            }
                            f = fArray[0];
                            float f2 = fArray[1];
                            float f3 = fArray[2];
                            float f4 = fArray[3];
                            float f5 = fArray[4];
                            float f6 = fArray[5];
                            float f7 = fArray[6];
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f, 0.0f, f2, f3, f4, 0.0f);
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f5, 0.0f, f6, -f3, f7, 0.0f);
                            break block0;
                        }
                        case 35: {
                            if (n4 < 13) {
                                return 0;
                            }
                            f = fArray[0];
                            float f8 = fArray[1];
                            float f2 = fArray[2];
                            float f3 = fArray[3];
                            float f4 = fArray[4];
                            float f9 = fArray[5];
                            float f5 = fArray[6];
                            float f10 = fArray[7];
                            float f6 = fArray[8];
                            float f11 = fArray[9];
                            float f7 = fArray[10];
                            float f12 = fArray[11];
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f, f8, f2, f3, f4, f9);
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f5, f10, f6, f11, f7, f12);
                            break block0;
                        }
                        case 36: {
                            if (n4 < 9) {
                                return 0;
                            }
                            f = fArray[0];
                            float f8 = fArray[1];
                            float f2 = fArray[2];
                            float f3 = fArray[3];
                            float f4 = fArray[4];
                            float f5 = fArray[5];
                            float f6 = fArray[6];
                            float f11 = fArray[7];
                            float f7 = fArray[8];
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f, f8, f2, f3, f4, 0.0f);
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f5, 0.0f, f6, f11, f7, -(f8 + f3 + f11));
                            break block0;
                        }
                        case 37: {
                            float f12;
                            if (n4 < 11) {
                                return 0;
                            }
                            float f2 = fArray[0];
                            float f8 = fArray[1];
                            float f22 = fArray[2];
                            float f3 = fArray[3];
                            float f4 = fArray[4];
                            float f9 = fArray[5];
                            float f5 = fArray[6];
                            float f10 = fArray[7];
                            float f6 = fArray[8];
                            float f11 = fArray[9];
                            float f7 = f12 = fArray[10];
                            float f13 = f2 + f22 + f4 + f5 + f6;
                            float f14 = f8 + f3 + f9 + f10 + f11;
                            if (Math.abs(f13) > Math.abs(f14)) {
                                f12 = -f14;
                            } else {
                                f7 = -f13;
                            }
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f2, f8, f22, f3, f4, f9);
                            SmoothFontRasterState.s(stbTrueTypeCharStringContext, f5, f10, f6, f11, f7, f12);
                            break block0;
                        }
                    }
                    return 0;
                }
                default: {
                    float f;
                    if (n5 != 255 && n5 != 28 && n5 < 32) {
                        return 0;
                    }
                    if (n5 == 255) {
                        f = (float)((int)SmoothFontRasterState.g(frame2)) / 65536.0f;
                    } else {
                        SmoothFontRasterState.K(frame2, -1);
                        f = (short)SmoothFontRasterState.v(frame2);
                    }
                    if (n4 >= 48) {
                        return 0;
                    }
                    fArray[n4++] = f;
                    bl3 = false;
                }
            }
            if (!bl3) continue;
            n4 = 0;
        }
        return 0;
    }

    public static void b(StbTrueTypeBitmap stbTrueTypeBitmap, float f, StbTrueTypeVertex[] stbTrueTypeVertexArray, int n, float f2, float f3, float f4, float f5, int n2, int n3, int n4) {
        int[] nArray;
        int[][] nArrayArray;
        float f6 = f2 > f3 ? f3 : f2;
        StbTrueTypePoint[] stbTrueTypePointArray = SmoothFontRasterState.P(stbTrueTypeVertexArray, n, f / f6, nArrayArray = new int[][]{null}, nArray = new int[]{0});
        if (stbTrueTypePointArray != null) {
            SmoothFontRasterState.b(stbTrueTypeBitmap, stbTrueTypePointArray, nArrayArray[0], nArray[0], f2, f3, f4, f5, n2, n3, n4);
        }
    }

    static int h(byte[] byArray, byte[] byArray2, int n) {
        int n3;
        int n2 = 0;
        while ((n3 = SmoothFontRasterState.c(byArray, n2)) >= 0) {
            if (SmoothFontRasterState.S(byArray, n3, byArray2, 0, byArray2.length, n)) {
                return n3;
            }
            ++n2;
        }
        return n3;
    }

    public static int C(StbTrueTypePackContext stbTrueTypePackContext, byte[] byArray, int n, int n2, int n3, int n4) {
        StbTrueTypePackAtlasState stbTrueTypePackAtlasState = new StbTrueTypePackAtlasState();
        int n5 = n - n4;
        StbTrueTypeGlyphPackNode[] stbTrueTypeGlyphPackNodeArray = new StbTrueTypeGlyphPackNode[n5];
        for (int i = 0; i < n5; ++i) {
            stbTrueTypeGlyphPackNodeArray[i] = new StbTrueTypeGlyphPackNode();
        }
        stbTrueTypePackContext.var_int_w = n;
        stbTrueTypePackContext.var_int_d = n2;
        stbTrueTypePackContext.var_byte_arr_X = byArray;
        stbTrueTypePackContext.var_gg_vape_ui_font_stb_StbTrueTypePackAtlasState_J = stbTrueTypePackAtlasState;
        stbTrueTypePackContext.var_gg_vape_ui_font_stb_StbTrueTypeGlyphPackNode_arr_j = stbTrueTypeGlyphPackNodeArray;
        stbTrueTypePackContext.var_int_G = n4;
        stbTrueTypePackContext.var_int_b = n3 != 0 ? n3 : n;
        stbTrueTypePackContext.var_int_c = 1;
        stbTrueTypePackContext.var_int_m = 1;
        stbTrueTypePackContext.var_int_h = 0;
        SmoothFontRasterState.b(stbTrueTypePackAtlasState, n - n4, n2 - n4, stbTrueTypeGlyphPackNodeArray, n5);
        if (byArray != null) {
            Arrays.fill(byArray, 0, n * n2, 0);
        }
        return 1;
    }

    static int N(byte[] byArray, int n) {
        return byArray[n] & 0xFF;
    }

    static void w(StbTrueTypePoint[] stbTrueTypePointArray, int[] nArray, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n) {
        float f10 = f3 - f;
        float f11 = f4 - f2;
        float f12 = f5 - f3;
        float f13 = f6 - f4;
        float f14 = f7 - f5;
        float f15 = f8 - f6;
        float f16 = f7 - f;
        float f17 = f8 - f2;
        float f18 = (float)(Math.sqrt(f10 * f10 + f11 * f11) + Math.sqrt(f12 * f12 + f13 * f13) + Math.sqrt(f14 * f14 + f15 * f15));
        float f19 = (float)Math.sqrt(f16 * f16 + f17 * f17);
        float f20 = f18 * f18 - f19 * f19;
        if (n > 16) {
            return;
        }
        if (f20 > f9) {
            float f21 = (f + f3) / 2.0f;
            float f22 = (f2 + f4) / 2.0f;
            float f23 = (f3 + f5) / 2.0f;
            float f24 = (f4 + f6) / 2.0f;
            float f25 = (f5 + f7) / 2.0f;
            float f26 = (f6 + f8) / 2.0f;
            float f27 = (f21 + f23) / 2.0f;
            float f28 = (f22 + f24) / 2.0f;
            float f29 = (f23 + f25) / 2.0f;
            float f30 = (f24 + f26) / 2.0f;
            float f31 = (f27 + f29) / 2.0f;
            float f32 = (f28 + f30) / 2.0f;
            SmoothFontRasterState.w(stbTrueTypePointArray, nArray, f, f2, f21, f22, f27, f28, f31, f32, f9, n + 1);
            SmoothFontRasterState.w(stbTrueTypePointArray, nArray, f31, f32, f29, f30, f25, f26, f7, f8, f9, n + 1);
        } else {
            SmoothFontRasterState.r(stbTrueTypePointArray, nArray[0], f7, f8);
            nArray[0] = nArray[0] + 1;
        }
    }

    static int J(float[] fArray, float[] fArray2, float[] fArray3, float[] fArray4, float[] fArray5, float[][] fArray6) {
        float f;
        float f2;
        float f3;
        float f4 = fArray3[1] * fArray2[0] - fArray3[0] * fArray2[1];
        float f5 = fArray4[1] * fArray2[0] - fArray4[0] * fArray2[1];
        float f6 = fArray5[1] * fArray2[0] - fArray5[0] * fArray2[1];
        float f7 = fArray[1] * fArray2[0] - fArray[0] * fArray2[1];
        float f8 = f4 - 2.0f * f5 + f6;
        float f9 = f5 - f4;
        float f10 = f4 - f7;
        float f11 = 0.0f;
        float f12 = 0.0f;
        int n = 0;
        if ((double)f8 != 0.0) {
            f3 = f9 * f9 - f8 * f10;
            if ((double)f3 > 0.0) {
                f2 = -1.0f / f8;
                f = (float)Math.sqrt(f3);
                f11 = (f9 + f) * f2;
                f12 = (f9 - f) * f2;
                if ((double)f11 >= 0.0 && (double)f11 <= 1.0) {
                    n = 1;
                }
                if ((double)f > 0.0 && (double)f12 >= 0.0 && (double)f12 <= 1.0) {
                    if (n == 0) {
                        f11 = f12;
                    }
                    ++n;
                }
            }
        } else {
            f11 = f10 / (-2.0f * f9);
            if ((double)f11 >= 0.0 && (double)f11 <= 1.0) {
                n = 1;
            }
        }
        if (n == 0) {
            return 0;
        }
        f3 = 1.0f / (fArray2[0] * fArray2[0] + fArray2[1] * fArray2[1]);
        f2 = fArray2[0] * f3;
        f = fArray2[1] * f3;
        float f13 = fArray3[0] * f2 + fArray3[1] * f;
        float f14 = fArray4[0] * f2 + fArray4[1] * f;
        float f15 = fArray5[0] * f2 + fArray5[1] * f;
        float f16 = fArray[0] * f2 + fArray[1] * f;
        float f17 = f14 - f13;
        float f18 = f15 - f13;
        float f19 = f13 - f16;
        fArray6[0][0] = f19 + f11 * (2.0f - 2.0f * f11) * f17 + f11 * f11 * f18;
        fArray6[0][1] = f8 * f11 + f9;
        if (n > 1) {
            fArray6[1][0] = f19 + f12 * (2.0f - 2.0f * f12) * f17 + f12 * f12 * f18;
            fArray6[1][1] = f8 * f12 + f9;
            return 2;
        }
        return 1;
    }

    static int i(byte[] byArray, int n, float f, byte[] byArray2, int n2, int n3, int n4, int n5, StbTrueTypeBakedChar[] stbTrueTypeBakedCharArray) {
        StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
        if (SmoothFontRasterState.L(stbTrueTypeFontInfo, byArray, n) == 0) {
            return -1;
        }
        Arrays.fill(byArray2, 0, n2 * n3, 0);
        int n6 = 1;
        int n7 = 1;
        int n8 = 1;
        float f2 = SmoothFontRasterState.V(stbTrueTypeFontInfo, f);
        for (int i = 0; i < n5; ++i) {
            int[] nArray = new int[1];
            int[] nArray2 = new int[1];
            int[] nArray3 = new int[1];
            int[] nArray4 = new int[1];
            int[] nArray5 = new int[1];
            int[] nArray6 = new int[1];
            int n9 = SmoothFontRasterState.T(stbTrueTypeFontInfo, n4 + i);
            SmoothFontRasterState.q(stbTrueTypeFontInfo, n9, nArray, nArray2);
            SmoothFontRasterState.Z(stbTrueTypeFontInfo, n9, f2, f2, nArray3, nArray4, nArray5, nArray6);
            int n10 = nArray5[0] - nArray3[0];
            int n11 = nArray6[0] - nArray4[0];
            if (n7 + n10 + 1 >= n2) {
                n6 = n8;
                n7 = 1;
            }
            if (n6 + n11 + 1 >= n3) {
                return -i;
            }
            SmoothFontRasterState.I(stbTrueTypeFontInfo, byArray2, n7 + n6 * n2, n10, n11, n2, f2, f2, n9);
            stbTrueTypeBakedCharArray[i].var_int_V = (short)n7;
            stbTrueTypeBakedCharArray[i].var_int_Q = (short)n6;
            stbTrueTypeBakedCharArray[i].var_int_U = (short)(n7 + n10);
            stbTrueTypeBakedCharArray[i].var_int_D = (short)(n6 + n11);
            stbTrueTypeBakedCharArray[i].var_float_l = f2 * (float)nArray[0];
            stbTrueTypeBakedCharArray[i].var_float_B = nArray3[0];
            stbTrueTypeBakedCharArray[i].var_float_k = nArray4[0];
            n7 = n7 + n10 + 1;
            if (n6 + n11 + 1 <= n8) continue;
            n8 = n6 + n11 + 1;
        }
        return n8;
    }

    static int m(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        StbTrueTypeCharStringContext stbTrueTypeCharStringContext = new StbTrueTypeCharStringContext(1);
        int n2 = SmoothFontRasterState.V(stbTrueTypeFontInfo, n, stbTrueTypeCharStringContext);
        if (n2 != 0) {
            if (nArray != null) {
                int n3 = 0;
                int[] nArray5 = nArray;
                nArray5[n3] = stbTrueTypeCharStringContext.var_int_y;
            }
            if (nArray2 != null) {
                int n4 = 0;
                int[] nArray6 = nArray2;
                nArray6[n4] = stbTrueTypeCharStringContext.var_int_Z;
            }
            if (nArray3 != null) {
                int n5 = 0;
                int[] nArray7 = nArray3;
                nArray7[n5] = stbTrueTypeCharStringContext.var_int_c;
            }
            if (nArray4 != null) {
                int n6 = 0;
                int[] nArray8 = nArray4;
                nArray8[n6] = stbTrueTypeCharStringContext.var_int_f;
            }
            return stbTrueTypeCharStringContext.var_int_q;
        }
        if (nArray != null) {
            int n7 = 0;
            int[] nArray9 = nArray;
            nArray9[n7] = 0;
        }
        if (nArray2 != null) {
            int n8 = 0;
            int[] nArray10 = nArray2;
            nArray10[n8] = 0;
        }
        if (nArray3 != null) {
            int n9 = 0;
            int[] nArray11 = nArray3;
            nArray11[n9] = 0;
        }
        if (nArray4 != null) {
            int n10 = 0;
            int[] nArray12 = nArray4;
            nArray12[n10] = 0;
        }
        return 0;
    }

    static void i(StbByteBufferView frame, int n) {
        if (!(var_boolean_G || n <= frame.var_int_y && n >= 0)) {
            throw new AssertionError();
        }
        frame.var_int_h = n > frame.var_int_y || n < 0 ? frame.var_int_y : n;
    }

    public static byte[] N(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        return SmoothFontRasterState.F(stbTrueTypeFontInfo, f, f2, 0.0f, 0.0f, n, nArray, nArray2, nArray3, nArray4);
    }

    public static void G(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, float f3, float f4, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        int[] nArray5 = new int[]{0};
        int[] nArray6 = new int[]{0};
        int[] nArray7 = new int[]{0};
        int[] nArray8 = new int[]{0};
        if (SmoothFontRasterState.F(stbTrueTypeFontInfo, n, nArray5, nArray6, nArray7, nArray8) == 0) {
            if (nArray != null) {
                nArray[0] = 0;
            }
            if (nArray2 != null) {
                nArray2[0] = 0;
            }
            if (nArray3 != null) {
                nArray3[0] = 0;
            }
            if (nArray4 != null) {
                nArray4[0] = 0;
            }
        } else {
            if (nArray != null) {
                nArray[0] = (int)Math.floor((float)nArray5[0] * f + f3);
            }
            if (nArray2 != null) {
                nArray2[0] = (int)Math.floor((float)(-nArray8[0]) * f2 + f4);
            }
            if (nArray3 != null) {
                nArray3[0] = (int)Math.ceil((float)nArray7[0] * f + f3);
            }
            if (nArray4 != null) {
                nArray4[0] = (int)Math.ceil((float)(-nArray6[0]) * f2 + f4);
            }
        }
    }

    static long v(StbByteBufferView frame) {
        int n = SmoothFontRasterState.Q(frame);
        if (n >= 32 && n <= 246) {
            return n - 139;
        }
        if (n >= 247 && n <= 250) {
            return (n - 247) * 256 + SmoothFontRasterState.Q(frame) + 108;
        }
        if (n >= 251 && n <= 254) {
            return -(n - 251) * 256 - SmoothFontRasterState.Q(frame) - 108;
        }
        if (n == 28) {
            return SmoothFontRasterState.p(frame);
        }
        if (n == 29) {
            return SmoothFontRasterState.g(frame);
        }
        if (!var_boolean_G) {
            throw new AssertionError();
        }
        return 0L;
    }

    static void G(StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n, int n2) {
        while (n2 > 12) {
            StbTrueTypeEdge stbTrueTypeEdge;
            int n3 = n2 >> 1;
            boolean bl2 = stbTrueTypeEdgeArray[n].var_float_X < stbTrueTypeEdgeArray[n + n3].var_float_X;
            boolean bl = stbTrueTypeEdgeArray[n + n3].var_float_X < stbTrueTypeEdgeArray[n + n2 - 1].var_float_X;
            boolean bl3 = bl;
            if (bl2 != bl) {
                boolean bl4 = stbTrueTypeEdgeArray[n].var_float_X < stbTrueTypeEdgeArray[n + n2 - 1].var_float_X;
                int n4 = bl4 == bl ? 0 : n2 - 1;
                stbTrueTypeEdge = stbTrueTypeEdgeArray[n + n4];
                stbTrueTypeEdgeArray[n + n4] = stbTrueTypeEdgeArray[n + n3];
                stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdge;
            }
            stbTrueTypeEdge = stbTrueTypeEdgeArray[n];
            stbTrueTypeEdgeArray[n] = stbTrueTypeEdgeArray[n + n3];
            stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdge;
            int n5 = 1;
            int n6 = n2 - 1;
            while (true) {
                if (stbTrueTypeEdgeArray[n + n5].var_float_X < stbTrueTypeEdgeArray[n].var_float_X) {
                    ++n5;
                    continue;
                }
                while (stbTrueTypeEdgeArray[n].var_float_X < stbTrueTypeEdgeArray[n + n6].var_float_X) {
                    --n6;
                }
                if (n5 >= n6) break;
                stbTrueTypeEdge = stbTrueTypeEdgeArray[n + n5];
                stbTrueTypeEdgeArray[n + n5] = stbTrueTypeEdgeArray[n + n6];
                stbTrueTypeEdgeArray[n + n6] = stbTrueTypeEdge;
                ++n5;
                --n6;
            }
            if (n6 < n2 - n5) {
                SmoothFontRasterState.G(stbTrueTypeEdgeArray, n, n6);
                n += n5;
                n2 -= n5;
                continue;
            }
            SmoothFontRasterState.G(stbTrueTypeEdgeArray, n + n5, n2 - n5);
            n2 = n6;
        }
    }

    public static byte[] a(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, int n, int n2, int n3, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        return SmoothFontRasterState.A(stbTrueTypeFontInfo, f, SmoothFontRasterState.T(stbTrueTypeFontInfo, n), n2, n3, f2, nArray, nArray2, nArray3, nArray4);
    }

    public static int P(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        if (stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g.var_int_y != 0) {
            return SmoothFontRasterState.m(stbTrueTypeFontInfo, n, null, null, null, null) == 0 ? 1 : 0;
        }
        int n2 = SmoothFontRasterState.G(stbTrueTypeFontInfo, n);
        if (n2 < 0) {
            return 1;
        }
        short s = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n2);
        return s == 0 ? 1 : 0;
    }

    public static void q(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2) {
        int n2 = SmoothFontRasterState.x(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_w + 34);
        if (n < n2) {
            if (nArray != null) {
                nArray[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_d + 4 * n);
            }
            if (nArray2 != null) {
                nArray2[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_d + 4 * n + 2);
            }
        } else {
            if (nArray != null) {
                nArray[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_d + 4 * (n2 - 1));
            }
            if (nArray2 != null) {
                nArray2[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_d + 4 * n2 + 2 * (n - n2));
            }
        }
    }

    static int c(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
        if (stbTrueTypeFontInfo.var_int_G == 0) {
            return 0;
        }
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        int n3 = stbTrueTypeFontInfo.var_int_G;
        if (SmoothFontRasterState.x(byArray, n3 + 0) != 1) {
            return 0;
        }
        if (SmoothFontRasterState.x(byArray, n3 + 2) != 0) {
            return 0;
        }
        int n4 = SmoothFontRasterState.x(byArray, n3 + 8);
        int n5 = n3 + n4;
        int n6 = SmoothFontRasterState.x(byArray, n5);
        for (int i = 0; i < n6; ++i) {
            int n7 = SmoothFontRasterState.x(byArray, n5 + 2 + 2 * i);
            int n8 = n5 + n7;
            int n9 = SmoothFontRasterState.x(byArray, n8);
            int n10 = SmoothFontRasterState.x(byArray, n8 + 4);
            int n11 = n8 + 6;
            if (n9 != 2) continue;
            block5: for (int j = 0; j < n10; ++j) {
                int n12 = SmoothFontRasterState.x(byArray, n11 + 2 * j);
                int n13 = n8 + n12;
                int n14 = SmoothFontRasterState.x(byArray, n13);
                int n15 = SmoothFontRasterState.x(byArray, n13 + 2);
                int n16 = SmoothFontRasterState.L(byArray, n13 + n15, n);
                if (n16 == -1) continue;
                switch (n14) {
                    case 1: {
                        int n17;
                        int n18;
                        int n26 = SmoothFontRasterState.x(byArray, n13 + 4);
                        int n27 = SmoothFontRasterState.x(byArray, n13 + 6);
                        if (n26 == 4 && n27 == 0) {
                            int n25 = 2;
                            int n24 = SmoothFontRasterState.x(byArray, n13 + 8);
                            int n23 = SmoothFontRasterState.x(byArray, n13 + 10 + 2 * n16);
                            int n22 = n13 + n23;
                            int n28 = SmoothFontRasterState.x(byArray, n22);
                            int n29 = n22 + 2;
                            if (n16 >= n24) {
                                return 0;
                            }
                            int n21 = n2;
                            int n20 = n28 - 1;
                            int n19 = 0;
                            while (n19 <= n20) {
                                n18 = n19 + n20 >> 1;
                                int n30 = n29 + (2 + n25) * n18;
                                int n31 = SmoothFontRasterState.x(byArray, n30);
                                n17 = n31;
                                if (n21 < n17) {
                                    n20 = n18 - 1;
                                    continue;
                                }
                                if (n21 > n17) {
                                    n19 = n18 + 1;
                                    continue;
                                }
                                short s = SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n30 + 2);
                                return s;
                            }
                            continue block5;
                        }
                        return 0;
                    }
                    case 2: {
                        int n17;
                        int n18;
                        int n19 = SmoothFontRasterState.x(byArray, n13 + 4);
                        int n20 = SmoothFontRasterState.x(byArray, n13 + 6);
                        if (n19 == 4 && n20 == 0) {
                            n18 = SmoothFontRasterState.x(byArray, n13 + 8);
                            n17 = SmoothFontRasterState.x(byArray, n13 + 10);
                            int n21 = SmoothFontRasterState.q(byArray, n13 + n18, n);
                            int n26 = SmoothFontRasterState.q(byArray, n13 + n17, n2);
                            int n27 = SmoothFontRasterState.x(byArray, n13 + 12);
                            int n25 = SmoothFontRasterState.x(byArray, n13 + 14);
                            if (n21 < 0 || n21 >= n27) {
                                return 0;
                            }
                            if (n26 < 0 || n26 >= n25) {
                                return 0;
                            }
                            int n23 = n13 + 16;
                            int n22 = n23 + 2 * (n21 * n25);
                            short n24 = SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n22 + 2 * n26);
                            return n24;
                        }
                        return 0;
                    }
                    default: {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }

    static int G(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        int n2;
        int n3;
        if (!var_boolean_G && stbTrueTypeFontInfo.var_gg_vape_ui_font_stb_StbByteBufferView_g.var_int_y != 0) {
            throw new AssertionError();
        }
        if (n >= stbTrueTypeFontInfo.var_int_h) {
            return -1;
        }
        if (stbTrueTypeFontInfo.var_int_T >= 2) {
            return -1;
        }
        if (stbTrueTypeFontInfo.var_int_T == 0) {
            n3 = stbTrueTypeFontInfo.var_int_p + SmoothFontRasterState.x(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_v + n * 2) * 2;
            n2 = stbTrueTypeFontInfo.var_int_p + SmoothFontRasterState.x(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_v + n * 2 + 2) * 2;
        } else {
            n3 = (int)((long)stbTrueTypeFontInfo.var_int_p + SmoothFontRasterState.c$src$J$1nktx4p(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_v + n * 4));
            n2 = (int)((long)stbTrueTypeFontInfo.var_int_p + SmoothFontRasterState.c$src$J$1nktx4p(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_v + n * 4 + 4));
        }
        return n3 == n2 ? -1 : n3;
    }

    static boolean c$src$Z$1nktxih(byte[] byArray, int n) {
        if (SmoothFontRasterState.d(byArray, n, 49, 0, 0, 0)) {
            return true;
        }
        if (SmoothFontRasterState.f(byArray, n, "typ1")) {
            return true;
        }
        if (SmoothFontRasterState.f(byArray, n, "OTTO")) {
            return true;
        }
        if (SmoothFontRasterState.d(byArray, n, 0, 1, 0, 0)) {
            return true;
        }
        return SmoothFontRasterState.f(byArray, n, "true");
    }

    public static int X(byte[] byArray, int n, float f, byte[] byArray2, int n2, int n3, int n4, int n5, StbTrueTypeBakedChar[] stbTrueTypeBakedCharArray) {
        return SmoothFontRasterState.i(byArray, n, f, byArray2, n2, n3, n4, n5, stbTrueTypeBakedCharArray);
    }

    static void r(StbTrueTypePoint[] stbTrueTypePointArray, int n, float f, float f2) {
        if (stbTrueTypePointArray == null) {
            return;
        }
        stbTrueTypePointArray[n].var_float_M = f;
        stbTrueTypePointArray[n].var_float_E = f2;
    }

    public static int a(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        int n2 = SmoothFontRasterState.J(stbTrueTypeFontInfo);
        if (n2 == 0) {
            return -1;
        }
        int n3 = SmoothFontRasterState.x(byArray, n2);
        int n4 = n2 + 2;
        for (int i = 0; i < n3; ++i) {
            int n5 = n4 + 12 * i;
            if (n < SmoothFontRasterState.x(byArray, n5) || n > SmoothFontRasterState.x(byArray, n5 + 2)) continue;
            return n5;
        }
        return -1;
    }

    public static void Z(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        SmoothFontRasterState.G(stbTrueTypeFontInfo, n, f, f2, 0.0f, 0.0f, nArray, nArray2, nArray3, nArray4);
    }

    public static int B(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        if (stbTrueTypeFontInfo.var_int_Q == 0) {
            return 0;
        }
        int n2 = SmoothFontRasterState.a(stbTrueTypeFontInfo, n);
        if (n2 >= 0) {
            if (nArray != null) {
                nArray[0] = stbTrueTypeFontInfo.var_int_Q + (int)SmoothFontRasterState.c$src$J$1nktx4p(byArray, n2 + 4);
            }
            return (int)SmoothFontRasterState.c$src$J$1nktx4p(byArray, n2 + 8);
        }
        return 0;
    }

    public static int s(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        return SmoothFontRasterState.F(stbTrueTypeFontInfo, SmoothFontRasterState.T(stbTrueTypeFontInfo, n), nArray, nArray2, nArray3, nArray4);
    }

    static float U(int n) {
        if (n == 0) {
            return 0.0f;
        }
        return (float)(-(n - 1)) / (2.0f * (float)n);
    }

    static int v(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        int n3 = stbTrueTypeFontInfo.var_int_L;
        if (n3 == 0) {
            return 0;
        }
        if (SmoothFontRasterState.x(byArray, n3 + 2) < 1) {
            return 0;
        }
        if (SmoothFontRasterState.x(byArray, n3 + 8) != 1) {
            return 0;
        }
        int n4 = 0;
        int n5 = SmoothFontRasterState.x(byArray, n3 + 10) - 1;
        long l = (long)n << 16 | (long)n2;
        while (n4 <= n5) {
            int n6 = n4 + n5 >> 1;
            long l2 = SmoothFontRasterState.c$src$J$1nktx4p(byArray, n3 + 18 + n6 * 6);
            if (l < l2) {
                n5 = n6 - 1;
                continue;
            }
            if (l > l2) {
                n4 = n6 + 1;
                continue;
            }
            return SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n3 + 22 + n6 * 6);
        }
        return 0;
    }

    public static void V(StbTrueTypeBakedChar[] stbTrueTypeBakedCharArray, int n, int n2, int n3, float[] fArray, float[] fArray2, StbTrueTypeAlignedQuad stbTrueTypeAlignedQuad, int n4) {
        float f = n4 != 0 ? 0.0f : -0.5f;
        float f2 = 1.0f / (float)n;
        float f3 = 1.0f / (float)n2;
        StbTrueTypeBakedChar stbTrueTypeBakedChar = stbTrueTypeBakedCharArray[n3];
        int n5 = (int)Math.floor(fArray[0] + stbTrueTypeBakedChar.var_float_B + 0.5f);
        int n6 = (int)Math.floor(fArray2[0] + stbTrueTypeBakedChar.var_float_k + 0.5f);
        stbTrueTypeAlignedQuad.var_float_v = (float)n5 + f;
        stbTrueTypeAlignedQuad.var_float_x = (float)n6 + f;
        stbTrueTypeAlignedQuad.var_float_K = (float)(n5 + stbTrueTypeBakedChar.var_int_U - stbTrueTypeBakedChar.var_int_V) + f;
        stbTrueTypeAlignedQuad.var_float_M = (float)(n6 + stbTrueTypeBakedChar.var_int_D - stbTrueTypeBakedChar.var_int_Q) + f;
        stbTrueTypeAlignedQuad.var_float_G = (float)stbTrueTypeBakedChar.var_int_V * f2;
        stbTrueTypeAlignedQuad.var_float_O = (float)stbTrueTypeBakedChar.var_int_Q * f3;
        stbTrueTypeAlignedQuad.var_float_S = (float)stbTrueTypeBakedChar.var_int_U * f2;
        stbTrueTypeAlignedQuad.var_float_C = (float)stbTrueTypeBakedChar.var_int_D * f3;
        fArray[0] = fArray[0] + stbTrueTypeBakedChar.var_float_l;
    }

    static void s(StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n) {
        SmoothFontRasterState.G(stbTrueTypeEdgeArray, 0, n);
        SmoothFontRasterState.N(stbTrueTypeEdgeArray, 0, n);
    }

    static void d(StbTrueTypeVertex stbTrueTypeVertex, int n, int n2, int n3, int n4, int n5) {
        stbTrueTypeVertex.var_int_O = n;
        stbTrueTypeVertex.var_short_f = (short)n2;
        stbTrueTypeVertex.var_short_H = (short)n3;
        stbTrueTypeVertex.var_short_h = (short)n4;
        stbTrueTypeVertex.var_short_D = (short)n5;
    }

    static boolean d(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        return n2 == SmoothFontRasterState.Y(byArray, n, n2, byArray2, n3, n4);
    }

    static void t(StbTrueTypePackAtlasState stbTrueTypePackAtlasState, StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray, int n) {
        int n2;
        for (n2 = 0; n2 < n; ++n2) {
            if (stbTrueTypePackAtlasState.var_int_n + stbTrueTypePackRectangleArray[n2].var_int_N > stbTrueTypePackAtlasState.var_int_f) {
                stbTrueTypePackAtlasState.var_int_n = 0;
                stbTrueTypePackAtlasState.var_int_a = stbTrueTypePackAtlasState.var_int_t;
            }
            if (stbTrueTypePackAtlasState.var_int_a + stbTrueTypePackRectangleArray[n2].var_int_e > stbTrueTypePackAtlasState.var_int_F) break;
            stbTrueTypePackRectangleArray[n2].var_int_P = stbTrueTypePackAtlasState.var_int_n;
            stbTrueTypePackRectangleArray[n2].var_int_J = stbTrueTypePackAtlasState.var_int_a;
            stbTrueTypePackRectangleArray[n2].var_int_M = 1;
            stbTrueTypePackAtlasState.var_int_n += stbTrueTypePackRectangleArray[n2].var_int_N;
            if (stbTrueTypePackAtlasState.var_int_a + stbTrueTypePackRectangleArray[n2].var_int_e <= stbTrueTypePackAtlasState.var_int_t) continue;
            stbTrueTypePackAtlasState.var_int_t = stbTrueTypePackAtlasState.var_int_a + stbTrueTypePackRectangleArray[n2].var_int_e;
        }
        while (n2 < n) {
            stbTrueTypePackRectangleArray[n2].var_int_M = 0;
            ++n2;
        }
    }

    public static float N(float f) {
        return -f;
    }

    public static void x(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int[] nArray2, int[] nArray3) {
        if (nArray != null) {
            nArray[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_w + 4);
        }
        if (nArray2 != null) {
            nArray2[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_w + 6);
        }
        if (nArray3 != null) {
            nArray3[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_w + 8);
        }
    }

    static void s(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = stbTrueTypeCharStringContext.var_float_C + f;
        float f8 = stbTrueTypeCharStringContext.var_float_o + f2;
        float f9 = f7 + f3;
        float f10 = f8 + f4;
        stbTrueTypeCharStringContext.var_float_C = f9 + f5;
        stbTrueTypeCharStringContext.var_float_o = f10 + f6;
        SmoothFontRasterState.A(stbTrueTypeCharStringContext, 4, (int)stbTrueTypeCharStringContext.var_float_C, (int)stbTrueTypeCharStringContext.var_float_o, (int)f7, (int)f8, (int)f9, (int)f10);
    }

    public static void I(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, int n5) {
        SmoothFontRasterState.j(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, 0.0f, 0.0f, n5);
    }

    public static void c(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2) {
        SmoothFontRasterState.q(stbTrueTypeFontInfo, SmoothFontRasterState.T(stbTrueTypeFontInfo, n), nArray, nArray2);
    }

    static StbByteBufferView E(StbByteBufferView frame, int n, int n2) {
        StbByteBufferView frame2 = SmoothFontRasterState.x(null, 0, 0);
        if (n < 0 || n2 < 0 || n > frame.var_int_y || n2 > frame.var_int_y - n) {
            return frame2;
        }
        frame2.var_byte_arr_f = frame.var_byte_arr_f;
        frame2.var_int_C = frame.var_int_C + n;
        frame2.var_int_y = n2;
        return frame2;
    }

    static int q(StbByteBufferView frame) {
        SmoothFontRasterState.i(frame, 0);
        return SmoothFontRasterState.p(frame);
    }

    public static int J(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int[] nArray2, int[] nArray3) {
        int n = (int)SmoothFontRasterState.v(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_U, "OS/2");
        if (n == 0) {
            return 0;
        }
        if (nArray != null) {
            nArray[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n + 68);
        }
        if (nArray2 != null) {
            nArray2[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n + 70);
        }
        if (nArray3 != null) {
            nArray3[0] = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, n + 72);
        }
        return 1;
    }

    static StbByteBufferView u(StbByteBufferView frame, StbByteBufferView frame2) {
        long[] lArray = new long[]{0L};
        long[] lArray2 = new long[]{0L, 0L};
        SmoothFontRasterState.X(frame2, 18, 2, lArray2);
        if (lArray2[1] == 0L || lArray2[0] == 0L) {
            return SmoothFontRasterState.x(null, 0, 0);
        }
        StbByteBufferView frame3 = SmoothFontRasterState.E(frame, (int)lArray2[1], (int)lArray2[0]);
        SmoothFontRasterState.X(frame3, 19, 1, lArray);
        if (lArray[0] == 0L) {
            return SmoothFontRasterState.x(null, 0, 0);
        }
        SmoothFontRasterState.i(frame, (int)(lArray2[1] + lArray[0]));
        return SmoothFontRasterState.B(frame);
    }

    public static void H(StbTrueTypePackContext stbTrueTypePackContext, StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray, int n) {
        SmoothFontRasterState.t(stbTrueTypePackContext.var_gg_vape_ui_font_stb_StbTrueTypePackAtlasState_J, stbTrueTypePackRectangleArray, n);
    }

    static void X(StbByteBufferView frame, int n, int n2, long[] lArray) {
        StbByteBufferView frame2 = SmoothFontRasterState.C(frame, n);
        for (int i = 0; i < n2 && frame2.var_int_h < frame2.var_int_y; ++i) {
            lArray[i] = SmoothFontRasterState.v(frame2);
        }
    }

    public static void p(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, float f3, float f4, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        SmoothFontRasterState.G(stbTrueTypeFontInfo, SmoothFontRasterState.T(stbTrueTypeFontInfo, n), f, f2, f3, f4, nArray, nArray2, nArray3, nArray4);
    }

    static void z(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, float f, float f2) {
        stbTrueTypeCharStringContext.var_float_C += f;
        stbTrueTypeCharStringContext.var_float_o += f2;
        SmoothFontRasterState.A(stbTrueTypeCharStringContext, 2, (int)stbTrueTypeCharStringContext.var_float_C, (int)stbTrueTypeCharStringContext.var_float_o, 0, 0, 0, 0);
    }

    static float S(float f, float f2) {
        return f * f2 / 2.0f;
    }

    public static int T(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
        byte[] byArray = stbTrueTypeFontInfo.var_byte_arr_S;
        int n2 = stbTrueTypeFontInfo.var_int_Y;
        int n3 = SmoothFontRasterState.x(byArray, n2 + 0);
        if (n3 == 0) {
            int n4 = SmoothFontRasterState.x(byArray, n2 + 2);
            if (n < n4 - 6) {
                return SmoothFontRasterState.N(byArray, n2 + 6 + n);
            }
            return 0;
        }
        if (n3 == 6) {
            long l = SmoothFontRasterState.x(byArray, n2 + 6);
            long l2 = SmoothFontRasterState.x(byArray, n2 + 8);
            if ((long)n >= l && (long)n < l + l2) {
                return SmoothFontRasterState.x(byArray, n2 + 10 + (n - (int)l) * 2);
            }
            return 0;
        }
        if (n3 == 2) {
            if (!var_boolean_G) {
                throw new AssertionError();
            }
            return 0;
        }
        if (n3 == 4) {
            int n5;
            int n6;
            int n7 = SmoothFontRasterState.x(byArray, n2 + 6) >> 1;
            int n8 = SmoothFontRasterState.x(byArray, n2 + 8) >> 1;
            int n9 = SmoothFontRasterState.x(byArray, n2 + 10);
            int n10 = SmoothFontRasterState.x(byArray, n2 + 12) >> 1;
            int n11 = n6 = n2 + 14;
            if (n > 65535) {
                return 0;
            }
            if (n >= SmoothFontRasterState.x(byArray, n11 + n10 * 2)) {
                n11 += n10 * 2;
            }
            n11 -= 2;
            while (n9 != 0) {
                n5 = SmoothFontRasterState.x(byArray, n11 + (n8 >>= 1) * 2);
                if (n > n5) {
                    n11 += n8 * 2;
                }
                --n9;
            }
            int n12 = (n11 += 2) - n6 >> 1;
            int n13 = SmoothFontRasterState.x(byArray, n2 + 14 + n7 * 2 + 2 + 2 * n12);
            int n14 = SmoothFontRasterState.x(byArray, n6 + 2 * n12);
            if (n < n13 || n > n14) {
                return 0;
            }
            n5 = SmoothFontRasterState.x(byArray, n2 + 14 + n7 * 6 + 2 + 2 * n12);
            if (n5 == 0) {
                return n + SmoothFontRasterState.E$src$S$1b9gd7y(byArray, n2 + 14 + n7 * 4 + 2 + 2 * n12) & 0xFFFF;
            }
            return SmoothFontRasterState.x(byArray, n5 + (n - n13) * 2 + n2 + 14 + n7 * 6 + 2 + 2 * n12);
        }
        if (n3 == 12 || n3 == 13) {
            long l = SmoothFontRasterState.c$src$J$1nktx4p(byArray, n2 + 12);
            int n15 = 0;
            int n16 = (int)l;
            while (n15 < n16) {
                int n17 = n15 + (n16 - n15 >> 1);
                long l3 = SmoothFontRasterState.c$src$J$1nktx4p(byArray, n2 + 16 + n17 * 12);
                long l4 = SmoothFontRasterState.c$src$J$1nktx4p(byArray, n2 + 16 + n17 * 12 + 4);
                if ((long)n < l3) {
                    n16 = n17;
                    continue;
                }
                if ((long)n > l4) {
                    n15 = n17 + 1;
                    continue;
                }
                long l5 = SmoothFontRasterState.c$src$J$1nktx4p(byArray, n2 + 16 + n17 * 12 + 8);
                if (n3 == 12) {
                    return (int)(l5 + (long)n - l3);
                }
                return (int)l5;
            }
            return 0;
        }
        if (!var_boolean_G) {
            throw new AssertionError();
        }
        return 0;
    }

    public static void H(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, float f3, float f4, int n5, int n6, float[] fArray, float[] fArray2, int n7) {
        SmoothFontRasterState.j(stbTrueTypeFontInfo, byArray, n, n2 - (n5 - 1), n3 - (n6 - 1), n4, f, f2, f3, f4, n7);
        if (n5 > 1) {
            SmoothFontRasterState.H(byArray, n, n2, n3, n4, n5);
        }
        if (n6 > 1) {
            SmoothFontRasterState.p(byArray, n, n2, n3, n4, n6);
        }
        fArray[0] = SmoothFontRasterState.U(n5);
        fArray2[0] = SmoothFontRasterState.U(n6);
    }

    static void N(StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n, int n2) {
        for (int i = 1; i < n2; ++i) {
            int n3;
            StbTrueTypeEdge stbTrueTypeEdge = stbTrueTypeEdgeArray[n + i];
            for (n3 = i; n3 > 0; --n3) {
                StbTrueTypeEdge stbTrueTypeEdge2 = stbTrueTypeEdgeArray[n + n3 - 1];
                boolean bl = stbTrueTypeEdge.var_float_X < stbTrueTypeEdge2.var_float_X;
                boolean bl2 = bl;
                if (!bl) break;
                stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdgeArray[n + n3 - 1];
            }
            if (i == n3) continue;
            stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdge;
        }
    }

    static StbByteBufferView C(StbByteBufferView frame, int n) {
        SmoothFontRasterState.i(frame, 0);
        while (frame.var_int_h < frame.var_int_y) {
            int n2 = frame.var_int_h;
            while (SmoothFontRasterState.K(frame) >= 28) {
                SmoothFontRasterState.M(frame);
            }
            int n3 = frame.var_int_h;
            int n4 = SmoothFontRasterState.Q(frame);
            if (n4 == 12) {
                n4 = SmoothFontRasterState.Q(frame) | 0x100;
            }
            if (n4 != n) continue;
            return SmoothFontRasterState.E(frame, n2, n3 - n2);
        }
        return SmoothFontRasterState.E(frame, 0, 0);
    }

    static long g(StbByteBufferView frame) {
        return SmoothFontRasterState.u(frame, 4);
    }

    public static float V(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f) {
        int n = SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_w + 4) - SmoothFontRasterState.E$src$S$1b9gd7y(stbTrueTypeFontInfo.var_byte_arr_S, stbTrueTypeFontInfo.var_int_w + 6);
        return f / (float)n;
    }

    static void b(StbTrueTypeBitmap stbTrueTypeBitmap, StbTrueTypePoint[] stbTrueTypePointArray, int[] nArray, int n, float f, float f2, float f3, float f4, int n2, int n3, int n4) {
        int n5;
        float f5 = n4 != 0 ? -f2 : f2;
        int n6 = 1;
        int n7 = 0;
        for (n5 = 0; n5 < n; ++n5) {
            n7 += nArray[n5];
        }
        StbTrueTypeEdge[] stbTrueTypeEdgeArray = new StbTrueTypeEdge[n7 + 1];
        for (n5 = 0; n5 <= n7; ++n5) {
            stbTrueTypeEdgeArray[n5] = new StbTrueTypeEdge();
        }
        n7 = 0;
        int n8 = 0;
        for (n5 = 0; n5 < n; ++n5) {
            int n9 = n8;
            n8 += nArray[n5];
            int n10 = nArray[n5] - 1;
            int n11 = 0;
            while (n11 < nArray[n5]) {
                int n12 = n11;
                int n13 = n10;
                if (stbTrueTypePointArray[n9 + n10].var_float_E != stbTrueTypePointArray[n9 + n11].var_float_E) {
                    stbTrueTypeEdgeArray[n7].var_int_W = 0;
                    if (n4 != 0 ? stbTrueTypePointArray[n9 + n10].var_float_E > stbTrueTypePointArray[n9 + n11].var_float_E : stbTrueTypePointArray[n9 + n10].var_float_E < stbTrueTypePointArray[n9 + n11].var_float_E) {
                        stbTrueTypeEdgeArray[n7].var_int_W = 1;
                        n12 = n10;
                        n13 = n11;
                    }
                    stbTrueTypeEdgeArray[n7].var_float_d = stbTrueTypePointArray[n9 + n12].var_float_M * f + f3;
                    stbTrueTypeEdgeArray[n7].var_float_X = (stbTrueTypePointArray[n9 + n12].var_float_E * f5 + f4) * (float)n6;
                    stbTrueTypeEdgeArray[n7].var_float_i = stbTrueTypePointArray[n9 + n13].var_float_M * f + f3;
                    stbTrueTypeEdgeArray[n7].var_float_u = (stbTrueTypePointArray[n9 + n13].var_float_E * f5 + f4) * (float)n6;
                    ++n7;
                }
                n10 = n11++;
            }
        }
        SmoothFontRasterState.s(stbTrueTypeEdgeArray, n7);
        SmoothFontRasterState.b(stbTrueTypeBitmap, stbTrueTypeEdgeArray, n7, n6, n2, n3);
    }

    public static int G(StbTrueTypePackContext stbTrueTypePackContext, byte[] byArray, int n, float f, int n2, int n3, StbTrueTypePackedChar[] stbTrueTypePackedCharArray) {
        StbTrueTypePackRange stbTrueTypePackRange = new StbTrueTypePackRange();
        stbTrueTypePackRange.var_int_R = n2;
        stbTrueTypePackRange.var_int_arr_N = null;
        stbTrueTypePackRange.var_int_b = n3;
        stbTrueTypePackRange.var_gg_vape_ui_font_stb_StbTrueTypePackedChar_arr_M = stbTrueTypePackedCharArray;
        stbTrueTypePackRange.var_float_E = f;
        return SmoothFontRasterState.c(stbTrueTypePackContext, byArray, n, new StbTrueTypePackRange[]{stbTrueTypePackRange}, 1);
    }

    public static byte[] A(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, int n, int n2, int n3, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
        float f5;
        float f4;
        float f3;
        float f10;
        float f6;
        float f7;
        float f8;
        float f9;
        float f11 = f;
        float f12 = f;
        int[] nArray5 = new int[1];
        int[] nArray6 = new int[1];
        int[] nArray7 = new int[1];
        int[] nArray8 = new int[1];
        if (f == 0.0f) {
            return null;
        }
        SmoothFontRasterState.G(stbTrueTypeFontInfo, n, f, f, 0.0f, 0.0f, nArray5, nArray6, nArray7, nArray8);
        if (nArray5[0] == nArray7[0] || nArray6[0] == nArray8[0]) {
            return null;
        }
        nArray5[0] = nArray5[0] - n2;
        nArray6[0] = nArray6[0] - n2;
        nArray7[0] = nArray7[0] + n2;
        nArray8[0] = nArray8[0] + n2;
        int n4 = nArray7[0] - nArray5[0];
        int n5 = nArray8[0] - nArray6[0];
        if (nArray != null) {
            nArray[0] = n4;
        }
        if (nArray2 != null) {
            nArray2[0] = n5;
        }
        if (nArray3 != null) {
            nArray3[0] = nArray5[0];
        }
        if (nArray4 != null) {
            nArray4[0] = nArray6[0];
        }
        f12 = -f12;
        float f13 = 9.765625E-4f;
        float f14 = 9.536743E-7f;
        StbTrueTypeVertex[] stbTrueTypeVertexArray = SmoothFontRasterState.m(stbTrueTypeFontInfo, n);
        int n6 = stbTrueTypeVertexArray != null ? stbTrueTypeVertexArray.length : 0;
        byte[] byArray = new byte[n4 * n5];
        float[] fArray = new float[n6];
        int n7 = 0;
        int n8 = n6 - 1;
        while (n7 < n6) {
            float f15;
            if (stbTrueTypeVertexArray[n7].var_int_O == 2) {
                float f16;
                f9 = (float)stbTrueTypeVertexArray[n8].var_short_f * f11;
                f8 = (float)stbTrueTypeVertexArray[n7].var_short_f * f11;
                f7 = (float)stbTrueTypeVertexArray[n8].var_short_H * f12;
                f6 = (float)stbTrueTypeVertexArray[n7].var_short_H * f12;
                f10 = (float)Math.sqrt((f9 - f8) * (f9 - f8) + (f7 - f6) * (f7 - f6));
                f15 = f16 < 9.765625E-4f ? 0.0f : 1.0f / f10;
            } else if (stbTrueTypeVertexArray[n7].var_int_O == 3) {
                float f17;
                f10 = (float)stbTrueTypeVertexArray[n7].var_short_f * f11;
                f9 = (float)stbTrueTypeVertexArray[n7].var_short_h * f11;
                f8 = (float)stbTrueTypeVertexArray[n8].var_short_f * f11;
                float f152 = f10 - 2.0f * f9 + f8;
                f3 = (float)stbTrueTypeVertexArray[n7].var_short_H * f12;
                f7 = (float)stbTrueTypeVertexArray[n7].var_short_D * f12;
                f6 = (float)stbTrueTypeVertexArray[n8].var_short_H * f12;
                f4 = f3 - 2.0f * f7 + f6;
                f5 = f152 * f152 + f4 * f4;
                f15 = f17 >= 9.536743E-7f ? 1.0f / f5 : 0.0f;
            } else {
                f15 = 0.0f;
            }
            fArray[n7] = f15;
            n8 = n7++;
        }
        for (int i = nArray6[0]; i < nArray8[0]; ++i) {
            for (int j = nArray5[0]; j < nArray7[0]; ++j) {
                float f18;
                f6 = 999999.0f;
                f9 = (float)j + 0.5f;
                f7 = (float)i + 0.5f;
                f10 = f9 / f11;
                f3 = f7 / f12;
                int n9 = SmoothFontRasterState.i(f10, f3, n6, stbTrueTypeVertexArray);
                for (n7 = 0; n7 < n6; ++n7) {
                    float f16;
                    float f19;
                    float f182;
                    float f17;
                    float f41;
                    float f20;
                    float f21;
                    float f22;
                    float f23;
                    float f24;
                    float f25;
                    float f26;
                    float f27;
                    float f28;
                    float f29;
                    float f30;
                    f4 = (float)stbTrueTypeVertexArray[n7].var_short_f * f11;
                    f5 = (float)stbTrueTypeVertexArray[n7].var_short_H * f12;
                    if (stbTrueTypeVertexArray[n7].var_int_O == 2 && fArray[n7] != 0.0f) {
                        float f31;
                        float f32;
                        f30 = (float)stbTrueTypeVertexArray[n7 - 1].var_short_f * f11;
                        f29 = (float)stbTrueTypeVertexArray[n7 - 1].var_short_H * f12;
                        f28 = (f4 - f9) * (f4 - f9) + (f5 - f7) * (f5 - f7);
                        if (f28 < f6 * f6) {
                            f6 = (float)Math.sqrt(f28);
                        }
                        f27 = Math.abs((f30 - f4) * (f5 - f7) - (f29 - f5) * (f4 - f9)) * fArray[n7];
                        if (!(f32 < f6)) continue;
                        f26 = f4 - f9;
                        f25 = f30 - f4;
                        f24 = f5 - f7;
                        f23 = f29 - f5;
                        float f312 = -(f26 * f25 + f24 * f23) / (f25 * f25 + f23 * f23);
                        if (!(f31 >= 0.0f) || !(f312 <= 1.0f)) continue;
                        f6 = f27;
                        continue;
                    }
                    if (stbTrueTypeVertexArray[n7].var_int_O != 3) continue;
                    f30 = (float)stbTrueTypeVertexArray[n7 - 1].var_short_f * f11;
                    f29 = (float)stbTrueTypeVertexArray[n7 - 1].var_short_H * f12;
                    f27 = (float)stbTrueTypeVertexArray[n7].var_short_h * f11;
                    f28 = (float)stbTrueTypeVertexArray[n7].var_short_D * f12;
                    f25 = Math.min(Math.min(f4, f27), f30);
                    f23 = Math.min(Math.min(f5, f28), f29);
                    f26 = Math.max(Math.max(f4, f27), f30);
                    f24 = Math.max(Math.max(f5, f28), f29);
                    if (!(f9 > f25 - f6 && f9 < f26 + f6 && f7 > f23 - f6 && f7 < f24 + f6)) continue;
                    int n10 = 0;
                    float f32 = f27 - f4;
                    float f33 = f28 - f5;
                    float f34 = f4 - 2.0f * f27 + f30;
                    float f35 = f5 - 2.0f * f28 + f29;
                    float f36 = f4 - f9;
                    float f37 = f5 - f7;
                    float[] fArray2 = new float[]{0.0f, 0.0f, 0.0f};
                    float f38 = fArray[n7];
                    if ((double)f38 == 0.0) {
                        f22 = 3.0f * (f32 * f34 + f33 * f35);
                        f21 = 2.0f * (f32 * f32 + f33 * f33) + (f36 * f34 + f37 * f35);
                        f20 = f36 * f32 + f37 * f33;
                        if (Math.abs(f22) < 9.536743E-7f) {
                            if (Math.abs(f21) >= 9.536743E-7f) {
                                fArray2[n10++] = -f20 / f21;
                            }
                        } else {
                            float f39 = f21 * f21 - 4.0f * f22 * f20;
                            if (f39 < 0.0f) {
                                n10 = 0;
                            } else {
                                float f40 = (float)Math.sqrt(f39);
                                fArray2[0] = (-f21 - f40) / (2.0f * f22);
                                fArray2[1] = (-f21 + f40) / (2.0f * f22);
                                n10 = 2;
                            }
                        }
                    } else {
                        f22 = 3.0f * (f32 * f34 + f33 * f35) * f38;
                        f21 = (2.0f * (f32 * f32 + f33 * f33) + (f36 * f34 + f37 * f35)) * f38;
                        f20 = (f36 * f32 + f37 * f33) * f38;
                        n10 = SmoothFontRasterState.X(f22, f21, f20, fArray2);
                    }
                    if ((f41 = (f4 - f9) * (f4 - f9) + (f5 - f7) * (f5 - f7)) < f6 * f6) {
                        f6 = (float)Math.sqrt(f41);
                    }
                    if (n10 >= 1 && fArray2[0] >= 0.0f && fArray2[0] <= 1.0f) {
                        float f39;
                        f17 = fArray2[0];
                        f182 = 1.0f - f17;
                        f19 = f182 * f182 * f4 + 2.0f * f17 * f182 * f27 + f17 * f17 * f30;
                        f16 = f182 * f182 * f5 + 2.0f * f17 * f182 * f28 + f17 * f17 * f29;
                        f41 = (f19 - f9) * (f19 - f9) + (f16 - f7) * (f16 - f7);
                        if (f39 < f6 * f6) {
                            f6 = (float)Math.sqrt(f41);
                        }
                    }
                    if (n10 >= 2 && fArray2[1] >= 0.0f && fArray2[1] <= 1.0f) {
                        float f40;
                        f17 = fArray2[1];
                        f182 = 1.0f - f17;
                        f19 = f182 * f182 * f4 + 2.0f * f17 * f182 * f27 + f17 * f17 * f30;
                        f16 = f182 * f182 * f5 + 2.0f * f17 * f182 * f28 + f17 * f17 * f29;
                        f41 = (f19 - f9) * (f19 - f9) + (f16 - f7) * (f16 - f7);
                        if (f40 < f6 * f6) {
                            f6 = (float)Math.sqrt(f41);
                        }
                    }
                    if (!(n10 >= 3 && fArray2[2] >= 0.0f && fArray2[2] <= 1.0f && (f41 = ((f19 = (f182 = 1.0f - (f17 = fArray2[2])) * f182 * f4 + 2.0f * f17 * f182 * f27 + f17 * f17 * f30) - f9) * (f19 - f9) + ((f16 = f182 * f182 * f5 + 2.0f * f17 * f182 * f28 + f17 * f17 * f29) - f7) * (f16 - f7)) < f6 * f6)) continue;
                    f6 = (float)Math.sqrt(f41);
                }
                if (n9 == 0) {
                    f6 = -f6;
                }
                f8 = (float)n3 + f2 * f6;
                if (f18 < 0.0f) {
                    f8 = 0.0f;
                } else if (f8 > 255.0f) {
                    f8 = 255.0f;
                }
                byArray[(i - nArray6[0]) * n4 + (j - nArray5[0])] = (byte)f8;
            }
        }
        return byArray;
    }

    static float w(float f) {
        if (f < 0.0f) {
            return -((float)Math.pow(-f, 0.3333333432674408));
        }
        return (float)Math.pow(f, 0.3333333432674408);
    }

    static int u(byte[] byArray, int n) {
        return byArray[n];
    }

    static boolean f(byte[] byArray, int n, String string) {
        return SmoothFontRasterState.d(byArray, n, (int)string.charAt(0), (int)string.charAt(1), (int)string.charAt(2), (int)string.charAt(3));
    }

    public static int p(byte[] byArray) {
        return SmoothFontRasterState.s(byArray);
    }

    static {
        var_int_j = 23;
        var_int_S = 19;
        var_int_L = 4;
        var_int_q = 4;
        var_int_o = 3;
        var_int_C = 2;
        var_int_B = 1053;
        var_int_T = 11;
        var_int_a = 6;
        var_int_x = 2;
        Fb = 1041;
        var_int_i = 2;
        var_int_E = 7;
        var_int_R = 1042;
        var_int_r = 10;
        var_int_M = 1036;
        Ff = 8;
        var_int_z = 5;
        var_int_I = 2;
        var_int_A = 4;
        var_int_n = 3;
        var_int_f = 1049;
        var_int_O = 6;
        var_int_g = 1043;
        var_int_Y = 10;
        var_int_v = 2;
        var_int_H = 12;
        var_int_P = 1033;
        var_int_F = 2052;
        var_int_V = 2;
        var_int_U = 1037;
        var_int_c = 7;
        var_int_m = 3;
        Fc = 1033;
        var_int_t = 3;
        var_int_D = 5;
        var_int_W = 32;
        var_int_N = 1031;
        FC = 3;
        Fy = 2;
        var_int_Q = 8;
        F1 = 4;
        var_int_h = 4;
        var_int_Z = 1040;
        var_int_e = 33;
        boolean bl = SmoothFontRasterState.class.desiredAssertionStatus();
        boolean bl2 = true;
        var_boolean_G = false;
    }
}

