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
   public static final int d = 0;
   public static final int x = 2;
   public static final int O = 6;
   public static final int n = 3;
   public static final int M = 1036;
   public static final int b = 1;
   static final boolean G = false;
   public static final int z = 5;
   public static final int m = 3;
   public static final int Fc = 1033;
   public static final int P = 1033;
   public static final int q = 4;
   public static final int J = 1;
   public static final int j = 23;
   public static final int W = 32;
   public static final int R = 1042;
   public static final int T = 11;
   public static final int e = 33;
   public static final int B = 1053;
   public static final int u = 0;
   public static final int i = 2;
   public static final int I = 2;
   public static final int k = 0;
   public static final int Q = 8;
   public static final int h = 4;
   public static final int FM = 1;
   public static final int a = 6;
   public static final int F1 = 4;
   public static final int g = 1043;
   public static final int Fb = 1041;
   public static final int s = 0;
   public static final int y = 1;
   public static final int V = 2;
   public static final int FC = 3;
   private static final int c = 7;
   public static final int v = 2;
   public static final int E = 7;
   public static final int w = 1;
   public static final int N = 1031;
   public static final int p = 1;
   public static final int D = 5;
   public static final int U = 1037;
   public static final int Ff = 8;
   public static final int K = 0;
   public static final int F = 2052;
   public static final int L = 4;
   public static final int l = 0;
   public static final int t = 3;
   public static final int f = 1049;
   public static final int o = 3;
   public static final int A = 4;
   public static final int Z = 1040;
   public static final int C = 2;
   public static final int X = 1;
   public static final int S = 19;
   public static final int r = 10;
   public static final int H = 12;
   public static final int Fy = 2;
   public static final int Y = 10;

   public static int F(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      if (stbTrueTypeFontInfo.g.y != 0) {
         m(stbTrueTypeFontInfo, n, nArray, nArray2, nArray3, nArray4);
      } else {
         int n2 = G(stbTrueTypeFontInfo, n);
         if (n2 < 0) {
            return 0;
         }

         if (nArray != null) {
            nArray[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n2 + 2);
         }

         if (nArray2 != null) {
            nArray2[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n2 + 4);
         }

         if (nArray3 != null) {
            nArray3[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n2 + 6);
         }

         if (nArray4 != null) {
            nArray4[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n2 + 8);
         }
      }

      return 1;
   }

   static StbByteBufferView e(StbByteBufferView frame, int n) {
      StbByteBufferView frame2 = frame.k();
      i(frame2, 0);
      int n2 = p(frame2);
      int n3 = Q(frame2);
      if (G || n >= 0 && n < n2) {
         if (G || n3 >= 1 && n3 <= 4) {
            K(frame2, n * n3);
            int n4 = (int)u(frame2, n3);
            int n5 = (int)u(frame2, n3);
            return E(frame2, 2 + (n2 + 1) * n3 + n4, n5 - n4);
         } else {
            throw new AssertionError();
         }
      } else {
         throw new AssertionError();
      }
   }

   public static int y(byte[] byArray, String string, int n) {
      return h(byArray, string.getBytes(), n);
   }

   static int a(StbTrueTypeVertex[] stbTrueTypeVertexArray, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
      if (n3 != 0) {
         if (n2 != 0) {
            d(stbTrueTypeVertexArray[n++], 3, n8 + n6 >> 1, n9 + n7 >> 1, n8, n9);
         }

         d(stbTrueTypeVertexArray[n++], 3, n4, n5, n6, n7);
      } else if (n2 != 0) {
         d(stbTrueTypeVertexArray[n++], 3, n4, n5, n8, n9);
      } else {
         d(stbTrueTypeVertexArray[n++], 2, n4, n5, 0, 0);
      }

      return n;
   }

   static void p(byte[] byArray, int n, int n2, int n3, int n4, int n5) {
      byte[] byArray2 = new byte[8];
      int n6 = n3 - n5;
      Arrays.fill(byArray2, (byte)0);

      for (int i = 0; i < n2; i++) {
         Arrays.fill(byArray2, 0, n5, (byte)0);
         int n8 = 0;
         int n7;
         switch (n5) {
            case 2:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                  byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 255) - (byArray2[n7 & 7] & 255)) / 2);
               }
               break;
            case 3:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                  byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 255) - (byArray2[n7 & 7] & 255)) / 3);
               }
               break;
            case 4:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                  byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 255) - (byArray2[n7 & 7] & 255)) / 4);
               }
               break;
            case 5:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                  byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 255) - (byArray2[n7 & 7] & 255)) / 5);
               }
               break;
            default:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7 * n4];
                  byArray[n + n7 * n4] = (byte)((n8 += (byArray[n + n7 * n4] & 255) - (byArray2[n7 & 7] & 255)) / n5);
               }
         }

         while (n7 < n3) {
            byArray[n + n7 * n4] = (byte)((n8 -= byArray2[n7 & 7] & 255) / n5);
            n7++;
         }

         n++;
      }
   }

   static int B(byte[] byArray, int n) {
      return ((byArray[n] & 0xFF) << 24) + ((byArray[n + 1] & 0xFF) << 16) + ((byArray[n + 2] & 0xFF) << 8) + (byArray[n + 3] & 0xFF);
   }

   static float A(float f, float f2, float f3) {
      return (f2 + f3) / 2.0F * f;
   }

   static void V(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, int n, int n2) {
      if (n > stbTrueTypeCharStringContext.c || stbTrueTypeCharStringContext.v == 0) {
         stbTrueTypeCharStringContext.c = n;
      }

      if (n2 > stbTrueTypeCharStringContext.f || stbTrueTypeCharStringContext.v == 0) {
         stbTrueTypeCharStringContext.f = n2;
      }

      if (n < stbTrueTypeCharStringContext.y || stbTrueTypeCharStringContext.v == 0) {
         stbTrueTypeCharStringContext.y = n;
      }

      if (n2 < stbTrueTypeCharStringContext.Z || stbTrueTypeCharStringContext.v == 0) {
         stbTrueTypeCharStringContext.Z = n2;
      }

      stbTrueTypeCharStringContext.v = 1;
   }

   public static void m(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      p(stbTrueTypeFontInfo, n, f, f2, 0.0F, 0.0F, nArray, nArray2, nArray3, nArray4);
   }

   public static void o(
      StbTrueTypeFontInfo stbTrueTypeFontInfo,
      byte[] byArray,
      int n,
      int n2,
      int n3,
      int n4,
      float f,
      float f2,
      float f3,
      float f4,
      int n5,
      int n6,
      float[] fArray,
      float[] fArray2,
      int n7
   ) {
      H(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, f3, f4, n5, n6, fArray, fArray2, T(stbTrueTypeFontInfo, n7));
   }

   static StbByteBufferView x(byte[] byArray, int n, int n2) {
      if (!G && n2 >= 1073741824) {
         throw new AssertionError();
      } else {
         StbByteBufferView frame = new StbByteBufferView();
         frame.f = byArray;
         frame.C = n;
         frame.y = n2;
         frame.h = 0;
         return frame;
      }
   }

   public static int d(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
      int n3 = 0;
      if (stbTrueTypeFontInfo.G != 0) {
         n3 += c(stbTrueTypeFontInfo, n, n2);
      } else if (stbTrueTypeFontInfo.L != 0) {
         n3 += v(stbTrueTypeFontInfo, n, n2);
      }

      return n3;
   }

   static void b(StbTrueTypePackAtlasState stbTrueTypePackAtlasState, int n, int n2, StbTrueTypeGlyphPackNode[] stbTrueTypeGlyphPackNodeArray, int n3) {
      stbTrueTypePackAtlasState.f = n;
      stbTrueTypePackAtlasState.F = n2;
      stbTrueTypePackAtlasState.n = 0;
      stbTrueTypePackAtlasState.a = 0;
      stbTrueTypePackAtlasState.t = 0;
   }

   public static String j(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int n, int n2, int n3, int n4) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      int n5 = stbTrueTypeFontInfo.U;
      long l = v(byArray, n5, "name");
      if (l == 0L) {
         return null;
      } else {
         int n6 = x(byArray, (int)l + 2);
         int n7 = (int)l + x(byArray, (int)l + 4);

         for (int i = 0; i < n6; i++) {
            int n8 = (int)l + 6 + 12 * i;
            if (n == x(byArray, n8 + 0) && n2 == x(byArray, n8 + 2) && n3 == x(byArray, n8 + 4) && n4 == x(byArray, n8 + 6)) {
               nArray[0] = x(byArray, n8 + 8);
               int n9 = n7 + x(byArray, n8 + 10);
               byte[] byArray2 = new byte[nArray[0]];
               System.arraycopy(byArray, n9, byArray2, 0, nArray[0]);
               return new String(byArray2);
            }
         }

         return null;
      }
   }

   public static int c(byte[] byArray, int n) {
      return E(byArray, n);
   }

   public static byte[] F(
      StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, float f3, float f4, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4
   ) {
      int[] nArray5 = new int[1];
      int[] nArray6 = new int[1];
      int[] nArray7 = new int[1];
      int[] nArray8 = new int[1];
      StbTrueTypeBitmap stbTrueTypeBitmap = new StbTrueTypeBitmap();
      StbTrueTypeVertex[] stbTrueTypeVertexArray = m(stbTrueTypeFontInfo, n);
      int n2 = stbTrueTypeVertexArray != null ? stbTrueTypeVertexArray.length : 0;
      if (f == 0.0F) {
         f = f2;
      }

      if (f2 == 0.0F) {
         if (f == 0.0F) {
            return null;
         }

         f2 = f;
      }

      G(stbTrueTypeFontInfo, n, f, f2, f3, f4, nArray5, nArray6, nArray7, nArray8);
      stbTrueTypeBitmap.K = nArray7[0] - nArray5[0];
      stbTrueTypeBitmap.c = nArray8[0] - nArray6[0];
      stbTrueTypeBitmap.E = null;
      if (nArray != null) {
         nArray[0] = stbTrueTypeBitmap.K;
      }

      if (nArray2 != null) {
         nArray2[0] = stbTrueTypeBitmap.c;
      }

      if (nArray3 != null) {
         nArray3[0] = nArray5[0];
      }

      if (nArray4 != null) {
         nArray4[0] = nArray6[0];
      }

      if (stbTrueTypeBitmap.K != 0 && stbTrueTypeBitmap.c != 0) {
         stbTrueTypeBitmap.E = new byte[stbTrueTypeBitmap.K * stbTrueTypeBitmap.c];
         stbTrueTypeBitmap.h = 0;
         if (stbTrueTypeBitmap.E != null) {
            stbTrueTypeBitmap.A = stbTrueTypeBitmap.K;
            b(stbTrueTypeBitmap, 0.35F, stbTrueTypeVertexArray, n2, f, f2, f3, f4, nArray5[0], nArray6[0], 1);
         }
      }

      return stbTrueTypeBitmap.E;
   }

   static void Q(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, float f, float f2) {
      d(stbTrueTypeCharStringContext);
      stbTrueTypeCharStringContext.s = stbTrueTypeCharStringContext.C += f;
      stbTrueTypeCharStringContext.p = stbTrueTypeCharStringContext.o += f2;
      A(stbTrueTypeCharStringContext, 1, (int)stbTrueTypeCharStringContext.C, (int)stbTrueTypeCharStringContext.o, 0, 0, 0, 0);
   }

   static void K(StbByteBufferView frame, int n) {
      i(frame, frame.h + n);
   }

   static long v(byte[] byArray, int n, String string) {
      int n2 = x(byArray, n + 4);
      long l = n + 12;

      for (int i = 0; i < n2; i++) {
         long l2 = l + 16 * i;
         if (f(byArray, (int)l2, string)) {
            return c$src$J$1nktx4p(byArray, (int)l2 + 8);
         }
      }

      return 0L;
   }

   static StbTrueTypeVertex[] o(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      StbTrueTypeVertex[] stbTrueTypeVertexArray = null;
      int n3 = 0;
      int n4 = G(stbTrueTypeFontInfo, n);
      if (n4 < 0) {
         return null;
      } else {
         short s = E$src$S$1b9gd7y(byArray, n4);
         if (s > 0) {
            int n2 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = n4 + 10;
            int n11 = x(byArray, n4 + 10 + s * 2);
            int n12 = n4 + 10 + s * 2 + 2 + n11;
            int n13 = 1 + x(byArray, n10 + s * 2 - 2);
            int n14 = n13 + 2 * s;
            stbTrueTypeVertexArray = new StbTrueTypeVertex[n14];

            for (int n6 = 0; n6 < n14; n6++) {
               stbTrueTypeVertexArray[n6] = new StbTrueTypeVertex();
            }

            int n15 = 0;
            int n16 = 0;
            int n17 = n14 - n13;
            int n18 = n12;

            for (int var46 = 0; var46 < n13; var46++) {
               if (n16 == 0) {
                  if (((n2 = byArray[n18++] & 255) & 8) != 0) {
                     n16 = byArray[n18++] & 255;
                  }
               } else {
                  n16--;
               }

               stbTrueTypeVertexArray[n17 + var46].O = n2;
            }

            int n19 = 0;

            for (int var47 = 0; var47 < n13; var47++) {
               n2 = stbTrueTypeVertexArray[n17 + var47].O;
               if ((n2 & 2) != 0) {
                  int n5 = byArray[n18++] & 255;
                  n19 += (n2 & 16) != 0 ? n5 : -n5;
               } else if ((n2 & 16) == 0) {
                  n19 += (short)((byArray[n18] & 255) * 256 + (byArray[n18 + 1] & 255));
                  n18 += 2;
               }

               stbTrueTypeVertexArray[n17 + var47].f = (short)n19;
            }

            int n20 = 0;

            for (int var48 = 0; var48 < n13; var48++) {
               n2 = stbTrueTypeVertexArray[n17 + var48].O;
               if ((n2 & 4) != 0) {
                  int n5 = byArray[n18++] & 255;
                  n20 += (n2 & 32) != 0 ? n5 : -n5;
               } else if ((n2 & 32) == 0) {
                  n20 += (short)((byArray[n18] & 255) * 256 + (byArray[n18 + 1] & 255));
                  n18 += 2;
               }

               stbTrueTypeVertexArray[n17 + var48].H = (short)n20;
            }

            n3 = 0;
            int n21 = 0;
            int n22 = 0;
            int n23 = 0;
            int n24 = 0;
            int n25 = 0;
            int n26 = 0;

            for (int var49 = 0; var49 < n13; var49++) {
               n2 = stbTrueTypeVertexArray[n17 + var49].O;
               int var66 = stbTrueTypeVertexArray[n17 + var49].f;
               int var67 = stbTrueTypeVertexArray[n17 + var49].H;
               if (n15 == var49) {
                  if (var49 != 0) {
                     n3 = a(stbTrueTypeVertexArray, n3, n8, n9, n26, n25, n22, n21, n24, n23);
                  }

                  n9 = (n2 & 1) == 0 ? 1 : 0;
                  if (n9 != 0) {
                     n22 = var66;
                     n21 = var67;
                     if ((stbTrueTypeVertexArray[n17 + var49 + 1].O & 1) == 0) {
                        n26 = var66 + stbTrueTypeVertexArray[n17 + var49 + 1].f >> 1;
                        n25 = var67 + stbTrueTypeVertexArray[n17 + var49 + 1].H >> 1;
                     } else {
                        n26 = stbTrueTypeVertexArray[n17 + var49 + 1].f;
                        n25 = stbTrueTypeVertexArray[n17 + var49 + 1].H;
                        var49++;
                     }
                  } else {
                     n26 = var66;
                     n25 = var67;
                  }

                  d(stbTrueTypeVertexArray[n3++], 1, n26, n25, 0, 0);
                  n8 = 0;
                  n15 = 1 + x(byArray, n10 + n7 * 2);
                  n7++;
               } else if ((n2 & 1) == 0) {
                  if (n8 != 0) {
                     d(stbTrueTypeVertexArray[n3++], 3, n24 + var66 >> 1, n23 + var67 >> 1, n24, n23);
                  }

                  n24 = var66;
                  n23 = var67;
                  n8 = 1;
               } else {
                  if (n8 != 0) {
                     d(stbTrueTypeVertexArray[n3++], 3, var66, var67, n24, n23);
                  } else {
                     d(stbTrueTypeVertexArray[n3++], 2, var66, var67, 0, 0);
                  }

                  n8 = 0;
               }
            }

            n3 = a(stbTrueTypeVertexArray, n3, n8, n9, n26, n25, n22, n21, n24, n23);
         } else if (s < 0) {
            int n2 = 1;
            int n28 = n4 + 10;
            n3 = 0;
            stbTrueTypeVertexArray = null;

            while (n2 != 0) {
               int n29 = 0;
               StbTrueTypeVertex[] stbTrueTypeVertexArray2 = null;
               StbTrueTypeVertex[] stbTrueTypeVertexArray3 = null;
               float[] fArray = new float[]{1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F};
               int n30 = x(byArray, n28);
               n28 += 2;
               int n31 = x(byArray, n28);
               n28 += 2;
               if ((n30 & 2) != 0) {
                  if ((n30 & 1) != 0) {
                     fArray[4] = E$src$S$1b9gd7y(byArray, n28);
                     n28 += 2;
                     fArray[5] = E$src$S$1b9gd7y(byArray, n28);
                     n28 += 2;
                  } else {
                     fArray[4] = u(byArray, n28);
                     fArray[5] = u(byArray, ++n28);
                     n28++;
                  }
               } else if (!G) {
                  throw new AssertionError();
               }

               if ((n30 & 8) != 0) {
                  fArray[0] = fArray[3] = E$src$S$1b9gd7y(byArray, n28) / 16384.0F;
                  n28 += 2;
                  fArray[2] = 0.0F;
                  fArray[1] = 0.0F;
               } else if ((n30 & 64) != 0) {
                  fArray[0] = E$src$S$1b9gd7y(byArray, n28) / 16384.0F;
                  fArray[2] = 0.0F;
                  fArray[1] = 0.0F;
                  n28 += 2;
                  fArray[3] = E$src$S$1b9gd7y(byArray, n28) / 16384.0F;
                  n28 += 2;
               } else if ((n30 & 128) != 0) {
                  fArray[0] = E$src$S$1b9gd7y(byArray, n28) / 16384.0F;
                  n28 += 2;
                  fArray[1] = E$src$S$1b9gd7y(byArray, n28) / 16384.0F;
                  n28 += 2;
                  fArray[2] = E$src$S$1b9gd7y(byArray, n28) / 16384.0F;
                  n28 += 2;
                  fArray[3] = E$src$S$1b9gd7y(byArray, n28) / 16384.0F;
                  n28 += 2;
               }

               float f = (float)Math.sqrt(fArray[0] * fArray[0] + fArray[1] * fArray[1]);
               float f2 = (float)Math.sqrt(fArray[2] * fArray[2] + fArray[3] * fArray[3]);
               stbTrueTypeVertexArray2 = m(stbTrueTypeFontInfo, n31);
               if (stbTrueTypeVertexArray2 != null) {
                  n29 = stbTrueTypeVertexArray2.length;
               }

               if (n29 > 0) {
                  for (int i = 0; i < n29; i++) {
                     StbTrueTypeVertex stbTrueTypeVertex = stbTrueTypeVertexArray2[i];
                     short s2 = stbTrueTypeVertex.f;
                     short s3 = stbTrueTypeVertex.H;
                     stbTrueTypeVertex.f = (short)(f * (fArray[0] * s2 + fArray[2] * s3 + fArray[4]));
                     stbTrueTypeVertex.H = (short)(f2 * (fArray[1] * s2 + fArray[3] * s3 + fArray[5]));
                     s2 = stbTrueTypeVertex.h;
                     s3 = stbTrueTypeVertex.D;
                     stbTrueTypeVertex.h = (short)(f * (fArray[0] * s2 + fArray[2] * s3 + fArray[4]));
                     stbTrueTypeVertex.D = (short)(f2 * (fArray[1] * s2 + fArray[3] * s3 + fArray[5]));
                  }

                  stbTrueTypeVertexArray3 = new StbTrueTypeVertex[n3 + n29];
                  if (n3 > 0 && stbTrueTypeVertexArray != null) {
                     System.arraycopy(stbTrueTypeVertexArray, 0, stbTrueTypeVertexArray3, 0, n3);
                  }

                  System.arraycopy(stbTrueTypeVertexArray2, 0, stbTrueTypeVertexArray3, n3, n29);
                  stbTrueTypeVertexArray = stbTrueTypeVertexArray3;
                  n3 += n29;
               }

               n2 = n30 & 32;
            }
         }

         if (stbTrueTypeVertexArray != null && n3 < stbTrueTypeVertexArray.length) {
            StbTrueTypeVertex[] stbTrueTypeVertexArray4 = new StbTrueTypeVertex[n3];
            System.arraycopy(stbTrueTypeVertexArray, 0, stbTrueTypeVertexArray4, 0, n3);
            return stbTrueTypeVertexArray4;
         } else {
            return stbTrueTypeVertexArray;
         }
      }
   }

   public static void w(StbTrueTypePackContext stbTrueTypePackContext, int n) {
      stbTrueTypePackContext.h = n;
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
      int n = frame.h;
      int n2 = p(frame);
      if (n2 != 0) {
         int n3 = Q(frame);
         if (!G && (n3 < 1 || n3 > 4)) {
            throw new AssertionError();
         }

         K(frame, n3 * n2);
         K(frame, (int)u(frame, n3) - 1);
      }

      return E(frame, n, frame.h - n);
   }

   public static byte[] Y(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      return q(stbTrueTypeFontInfo, f, f2, 0.0F, 0.0F, n, nArray, nArray2, nArray3, nArray4);
   }

   static void c(float[] fArray, int n, float[] fArray2, int n2, int n3, StbTrueTypeActiveEdge stbTrueTypeActiveEdge, float f) {
      float f2 = f + 1.0F;

      while (stbTrueTypeActiveEdge != null) {
         if (!G && !(stbTrueTypeActiveEdge.m >= f)) {
            throw new AssertionError();
         }

         if (stbTrueTypeActiveEdge.L == 0.0F) {
            float f3 = stbTrueTypeActiveEdge.e;
            if (f3 < n3) {
               if (f3 >= 0.0F) {
                  A(fArray, n, (int)f3, stbTrueTypeActiveEdge, f3, f, f3, f2);
                  A(fArray2, n2, (int)f3 + 1, stbTrueTypeActiveEdge, f3, f, f3, f2);
               } else {
                  A(fArray2, n2, 0, stbTrueTypeActiveEdge, f3, f, f3, f2);
               }
            }
         } else {
            float f3 = stbTrueTypeActiveEdge.e;
            float f13 = stbTrueTypeActiveEdge.L;
            float f14 = f3 + f13;
            float f15 = stbTrueTypeActiveEdge.N;
            if (!G && (!(stbTrueTypeActiveEdge.j <= f2) || !(stbTrueTypeActiveEdge.m >= f))) {
               throw new AssertionError();
            }

            float f11;
            float f12;
            if (stbTrueTypeActiveEdge.j > f) {
               f12 = f3 + f13 * (stbTrueTypeActiveEdge.j - f);
               f11 = stbTrueTypeActiveEdge.j;
            } else {
               f12 = f3;
               f11 = f;
            }

            float f9;
            float f10;
            if (stbTrueTypeActiveEdge.m < f2) {
               f10 = f3 + f13 * (stbTrueTypeActiveEdge.m - f);
               f9 = stbTrueTypeActiveEdge.m;
            } else {
               f10 = f14;
               f9 = f2;
            }

            if (f12 >= 0.0F && f10 >= 0.0F && f12 < n3 && f10 < n3) {
               if ((int)f12 == (int)f10) {
                  int n4 = (int)f12;
                  float f16 = (f9 - f11) * stbTrueTypeActiveEdge.T;
                  int n5 = n + n4;
                  fArray[n5] += E(f16, f12, n4 + 1.0F, f10, n4 + 1.0F);
                  int n6 = n2 + n4 + 1;
                  fArray2[n6] += f16;
               } else {
                  if (f12 > f10) {
                     f11 = f2 - (f11 - f);
                     f9 = f2 - (f9 - f);
                     f11 = f9;
                     f9 = f11;
                     float var36 = f10;
                     f10 = f12;
                     f12 = var36;
                     f13 = -f13;
                     f15 = -f15;
                     f3 = f14;
                  }

                  int n4 = (int)f12;
                  int n7 = (int)f10;
                  float f8 = f + f15 * (n4 + 1 - f3);
                  float f7 = f + f15 * (n7 - f3);
                  if (f8 > f2) {
                     f8 = f2;
                  }

                  float f6 = stbTrueTypeActiveEdge.T;
                  float f5 = f6 * (f8 - f11);
                  int n8 = n + n4;
                  fArray[n8] += S(f5, n4 + 1 - f12);
                  if (f7 > f2) {
                     f7 = f2;
                     f15 = (f2 - f8) / (n7 - (n4 + 1));
                  }

                  float f4 = f6 * f15 * 1.0F;

                  for (int i = n4 + 1; i < n7; i++) {
                     int n9 = n + i;
                     fArray[n9] += f5 + f4 / 2.0F;
                     f5 += f4;
                  }

                  int n10 = n + n7;
                  fArray[n10] += f5 + f6 * E(f9 - f7, n7, n7 + 1.0F, f10, n7 + 1.0F);
                  int n11 = n2 + n7 + 1;
                  fArray2[n11] += f6 * (f9 - f11);
               }
            } else {
               for (int i = 0; i < n3; i++) {
                  float f19 = i;
                  float f8x = i + 1;
                  float f6 = (i - f3) / f13 + f;
                  float f5 = (i + 1 - f3) / f13 + f;
                  if (f3 < f19 && f14 > f8x) {
                     A(fArray, n, i, stbTrueTypeActiveEdge, f3, f, f19, f6);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f8x, f5);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f8x, f5, f14, f2);
                  } else if (f14 < f19 && f3 > f8x) {
                     A(fArray, n, i, stbTrueTypeActiveEdge, f3, f, f8x, f5);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f8x, f5, f19, f6);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f14, f2);
                  } else if (f3 < f19 && f14 > f19) {
                     A(fArray, n, i, stbTrueTypeActiveEdge, f3, f, f19, f6);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f14, f2);
                  } else if (f14 < f19 && f3 > f19) {
                     A(fArray, n, i, stbTrueTypeActiveEdge, f3, f, f19, f6);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f19, f6, f14, f2);
                  } else if (f3 < f8x && f14 > f8x) {
                     A(fArray, n, i, stbTrueTypeActiveEdge, f3, f, f8x, f5);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f8x, f5, f14, f2);
                  } else if (f14 < f8x && f3 > f8x) {
                     A(fArray, n, i, stbTrueTypeActiveEdge, f3, f, f8x, f5);
                     A(fArray, n, i, stbTrueTypeActiveEdge, f8x, f5, f14, f2);
                  } else {
                     A(fArray, n, i, stbTrueTypeActiveEdge, f3, f, f14, f2);
                  }
               }
            }
         }

         stbTrueTypeActiveEdge = stbTrueTypeActiveEdge.l;
      }
   }

   static StbTrueTypeActiveEdge O(StbTrueTypeEdge stbTrueTypeEdge, int n, float f) {
      StbTrueTypeActiveEdge stbTrueTypeActiveEdge = new StbTrueTypeActiveEdge();
      float f2;
      stbTrueTypeActiveEdge.L = f2 = (stbTrueTypeEdge.i - stbTrueTypeEdge.d) / (stbTrueTypeEdge.u - stbTrueTypeEdge.X);
      stbTrueTypeActiveEdge.N = f2 != 0.0F ? 1.0F / f2 : 0.0F;
      stbTrueTypeActiveEdge.e = stbTrueTypeEdge.d + f2 * (f - stbTrueTypeEdge.X);
      stbTrueTypeActiveEdge.e -= n;
      stbTrueTypeActiveEdge.T = stbTrueTypeEdge.W != 0 ? 1.0F : -1.0F;
      stbTrueTypeActiveEdge.j = stbTrueTypeEdge.X;
      stbTrueTypeActiveEdge.m = stbTrueTypeEdge.u;
      stbTrueTypeActiveEdge.l = null;
      return stbTrueTypeActiveEdge;
   }

   public static int c(StbTrueTypePackContext stbTrueTypePackContext, byte[] byArray, int n, StbTrueTypePackRange[] stbTrueTypePackRangeArray, int n2) {
      StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
      int n4 = 1;

      for (int n3 = 0; n3 < n2; n3++) {
         for (int i = 0; i < stbTrueTypePackRangeArray[n3].b; i++) {
            stbTrueTypePackRangeArray[n3].M[i].p = 0;
            stbTrueTypePackRangeArray[n3].M[i].q = 0;
            stbTrueTypePackRangeArray[n3].M[i].k = 0;
            stbTrueTypePackRangeArray[n3].M[i].b = 0;
         }
      }

      int n5 = 0;

      for (int var10 = 0; var10 < n2; var10++) {
         n5 += stbTrueTypePackRangeArray[var10].b;
      }

      StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray = new StbTrueTypePackRectangle[n5];

      for (int var11 = 0; var11 < n5; var11++) {
         stbTrueTypePackRectangleArray[var11] = new StbTrueTypePackRectangle();
      }

      z(stbTrueTypeFontInfo, byArray, c(byArray, n));
      n5 = G(stbTrueTypePackContext, stbTrueTypeFontInfo, stbTrueTypePackRangeArray, n2, stbTrueTypePackRectangleArray);
      H(stbTrueTypePackContext, stbTrueTypePackRectangleArray, n5);
      return E(stbTrueTypePackContext, stbTrueTypeFontInfo, stbTrueTypePackRangeArray, n2, stbTrueTypePackRectangleArray);
   }

   static int p(StbByteBufferView frame) {
      return (int)u(frame, 2);
   }

   static short E$src$S$1b9gd7y(byte[] byArray, int n) {
      return (short)((byArray[n] & 255) * 256 + (byArray[n + 1] & 255));
   }

   public static void N(byte[] byArray, int n, float f, float[] fArray, float[] fArray2, float[] fArray3) {
      int[] nArray = new int[1];
      int[] nArray2 = new int[1];
      int[] nArray3 = new int[1];
      StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
      z(stbTrueTypeFontInfo, byArray, c(byArray, n));
      float f2 = f > 0.0F ? V(stbTrueTypeFontInfo, f) : b(stbTrueTypeFontInfo, -f);
      x(stbTrueTypeFontInfo, nArray, nArray2, nArray3);
      fArray[0] = nArray[0] * f2;
      fArray2[0] = nArray2[0] * f2;
      fArray3[0] = nArray3[0] * f2;
   }

   public static StbTrueTypeVertex[] t(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      return m(stbTrueTypeFontInfo, T(stbTrueTypeFontInfo, n));
   }

   static long c$src$J$1nktx4p(byte[] byArray, int n) {
      return ((long)(byArray[n] & 255) << 24) + ((long)(byArray[n + 1] & 255) << 16) + ((long)(byArray[n + 2] & 255) << 8) + (byArray[n + 3] & 255);
   }

   static int q(byte[] byArray, int n, int n2) {
      int n3 = x(byArray, n);
      switch (n3) {
         case 1:
            int n4 = x(byArray, n + 2);
            int n5 = x(byArray, n + 4);
            int n6 = n + 6;
            if (n2 >= n4 && n2 < n4 + n5) {
               return x(byArray, n6 + 2 * (n2 - n4));
            }
            break;
         case 2:
            int n7 = x(byArray, n + 2);
            int n8 = n + 4;
            int n9 = 0;
            int n10 = n7 - 1;
            int n11 = n2;

            while (n9 <= n10) {
               int n12 = n9 + n10 >> 1;
               int n13 = n8 + 6 * n12;
               int n14 = x(byArray, n13);
               int n15 = x(byArray, n13 + 2);
               if (n11 < n14) {
                  n10 = n12 - 1;
               } else {
                  if (n11 <= n15) {
                     return x(byArray, n13 + 4);
                  }

                  n9 = n12 + 1;
               }
            }
            break;
         default:
            return -1;
      }

      return 0;
   }

   static void M(StbByteBufferView frame) {
      int n = K(frame);
      if (!G && n < 28) {
         throw new AssertionError();
      } else {
         if (n == 30) {
            K(frame, 1);

            int n2;
            while (frame.h < frame.y && ((n2 = Q(frame)) & 15) != 15 && n2 >> 4 != 15) {
            }
         } else {
            v(frame);
         }
      }
   }

   public static int J(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
      return stbTrueTypeFontInfo.L == 0 && stbTrueTypeFontInfo.G == 0 ? 0 : d(stbTrueTypeFontInfo, T(stbTrueTypeFontInfo, n), T(stbTrueTypeFontInfo, n2));
   }

   static StbTrueTypeVertex[] a$src$ALgg_vape_ui_font_stb_StbTrueTypeVertex_$1sz52d4(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      StbTrueTypeCharStringContext stbTrueTypeCharStringContext = new StbTrueTypeCharStringContext(1);
      StbTrueTypeCharStringContext stbTrueTypeCharStringContext2 = new StbTrueTypeCharStringContext(0);
      if (V(stbTrueTypeFontInfo, n, stbTrueTypeCharStringContext) != 0) {
         StbTrueTypeVertex[] stbTrueTypeVertexArray = new StbTrueTypeVertex[stbTrueTypeCharStringContext.q];

         for (int i = 0; i < stbTrueTypeVertexArray.length; i++) {
            stbTrueTypeVertexArray[i] = new StbTrueTypeVertex();
         }

         stbTrueTypeCharStringContext2.V = stbTrueTypeVertexArray;
         if (V(stbTrueTypeFontInfo, n, stbTrueTypeCharStringContext2) != 0) {
            if (!G && stbTrueTypeCharStringContext2.q != stbTrueTypeCharStringContext.q) {
               throw new AssertionError();
            }

            return stbTrueTypeVertexArray;
         }
      }

      return null;
   }

   static int J(StbTrueTypeFontInfo stbTrueTypeFontInfo) {
      if (stbTrueTypeFontInfo.Q < 0) {
         long l = v(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.U, "SVG ");
         if (l != 0L) {
            long l2 = c$src$J$1nktx4p(stbTrueTypeFontInfo.S, (int)(l + 2L));
            stbTrueTypeFontInfo.Q = (int)(l + l2);
         } else {
            stbTrueTypeFontInfo.Q = 0;
         }
      }

      return stbTrueTypeFontInfo.Q;
   }

   static void b(StbTrueTypeBitmap stbTrueTypeBitmap, StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n, int n2, int n3, int n4) {
      StbTrueTypeActiveEdge stbTrueTypeActiveEdge = null;
      float[] fArray = new float[129];
      float[] fArray2 = stbTrueTypeBitmap.K > 64 ? new float[stbTrueTypeBitmap.K * 2 + 1] : fArray;
      int n5 = 0;
      int n6 = stbTrueTypeBitmap.K;
      int n7 = n4;
      stbTrueTypeEdgeArray[n].X = n4 + stbTrueTypeBitmap.c + 1.0F;
      int n8 = 0;

      for (int i = 0; i < stbTrueTypeBitmap.c; i++) {
         float f = n7 + 0.0F;
         float f2 = n7 + 1.0F;
         Arrays.fill(fArray2, n5, n5 + stbTrueTypeBitmap.K, 0.0F);
         Arrays.fill(fArray2, n6, n6 + stbTrueTypeBitmap.K + 1, 0.0F);
         StbTrueTypeActiveEdge stbTrueTypeActiveEdge2 = null;
         StbTrueTypeActiveEdge stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge;

         while (stbTrueTypeActiveEdge3 != null) {
            if (stbTrueTypeActiveEdge3.m <= f) {
               if (stbTrueTypeActiveEdge2 != null) {
                  stbTrueTypeActiveEdge2.l = stbTrueTypeActiveEdge3.l;
               } else {
                  stbTrueTypeActiveEdge = stbTrueTypeActiveEdge3.l;
               }

               stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge2 != null ? stbTrueTypeActiveEdge2.l : stbTrueTypeActiveEdge;
            } else {
               stbTrueTypeActiveEdge2 = stbTrueTypeActiveEdge3;
               stbTrueTypeActiveEdge3 = stbTrueTypeActiveEdge3.l;
            }
         }

         for (; n8 < n && stbTrueTypeEdgeArray[n8].X <= f2; n8++) {
            if (stbTrueTypeEdgeArray[n8].X != stbTrueTypeEdgeArray[n8].u) {
               StbTrueTypeActiveEdge stbTrueTypeActiveEdge4 = O(stbTrueTypeEdgeArray[n8], n3, f);
               if (i == 0 && n4 != 0 && stbTrueTypeActiveEdge4.m < f) {
                  stbTrueTypeActiveEdge4.m = f;
               }

               stbTrueTypeActiveEdge4.l = stbTrueTypeActiveEdge;
               stbTrueTypeActiveEdge = stbTrueTypeActiveEdge4;
            }
         }

         if (stbTrueTypeActiveEdge != null) {
            c(fArray2, n5, fArray2, n6, stbTrueTypeBitmap.K, stbTrueTypeActiveEdge, f);
         }

         float f3 = 0.0F;

         for (int j = 0; j < stbTrueTypeBitmap.K; j++) {
            float f4 = fArray2[n5 + j] + (f3 += fArray2[n6 + j]);
            int n9 = (int)(Math.abs(f4) * 255.0F + 0.5F);
            if (n9 > 255) {
               n9 = 255;
            }

            stbTrueTypeBitmap.E[stbTrueTypeBitmap.h + i * stbTrueTypeBitmap.A + j] = (byte)n9;
         }

         for (StbTrueTypeActiveEdge var22 = stbTrueTypeActiveEdge; var22 != null; var22 = var22.l) {
            var22.e = var22.e + var22.L;
         }

         n7++;
      }
   }

   public static void K(
      StbTrueTypePackedChar[] stbTrueTypePackedCharArray,
      int n,
      int n2,
      int n3,
      float[] fArray,
      float[] fArray2,
      StbTrueTypeAlignedQuad stbTrueTypeAlignedQuad,
      int n4
   ) {
      float f = 1.0F / n;
      float f2 = 1.0F / n2;
      StbTrueTypePackedChar stbTrueTypePackedChar = stbTrueTypePackedCharArray[n3];
      if (n4 != 0) {
         float f3 = (int)Math.floor(fArray[0] + stbTrueTypePackedChar.s + 0.5F);
         float f4 = (int)Math.floor(fArray2[0] + stbTrueTypePackedChar.G + 0.5F);
         stbTrueTypeAlignedQuad.v = f3;
         stbTrueTypeAlignedQuad.x = f4;
         stbTrueTypeAlignedQuad.K = f3 + stbTrueTypePackedChar.t - stbTrueTypePackedChar.s;
         stbTrueTypeAlignedQuad.M = f4 + stbTrueTypePackedChar.R - stbTrueTypePackedChar.G;
      } else {
         stbTrueTypeAlignedQuad.v = fArray[0] + stbTrueTypePackedChar.s;
         stbTrueTypeAlignedQuad.x = fArray2[0] + stbTrueTypePackedChar.G;
         stbTrueTypeAlignedQuad.K = fArray[0] + stbTrueTypePackedChar.t;
         stbTrueTypeAlignedQuad.M = fArray2[0] + stbTrueTypePackedChar.R;
      }

      stbTrueTypeAlignedQuad.G = stbTrueTypePackedChar.b * f;
      stbTrueTypeAlignedQuad.O = stbTrueTypePackedChar.k * f2;
      stbTrueTypeAlignedQuad.S = stbTrueTypePackedChar.q * f;
      stbTrueTypeAlignedQuad.C = stbTrueTypePackedChar.p * f2;
      fArray[0] += stbTrueTypePackedChar.A;
   }

   static int K(StbByteBufferView frame) {
      return frame.h >= frame.y ? 0 : frame.f[frame.C + frame.h] & 0xFF;
   }

   static StbByteBufferView W(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      StbByteBufferView frame = stbTrueTypeFontInfo.N.k();
      int n2 = -1;
      i(frame, 0);
      int n3 = Q(frame);
      if (n3 == 0) {
         K(frame, n);
         n2 = Q(frame);
      } else if (n3 == 3) {
         int n4 = p(frame);
         int n5 = p(frame);

         for (int i = 0; i < n4; i++) {
            int n6 = Q(frame);
            int n7 = p(frame);
            if (n >= n5 && n < n7) {
               n2 = n6;
               break;
            }

            n5 = n7;
         }
      }

      return n2 == -1 ? x(null, 0, 0) : u(stbTrueTypeFontInfo.g, e(stbTrueTypeFontInfo.H, n2));
   }

   public static int E(
      StbTrueTypePackContext stbTrueTypePackContext,
      StbTrueTypeFontInfo stbTrueTypeFontInfo,
      StbTrueTypePackRange[] stbTrueTypePackRangeArray,
      int n,
      StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray
   ) {
      int n2 = -1;
      int n3 = 1;
      int n4 = stbTrueTypePackContext.c;
      int n5 = stbTrueTypePackContext.m;
      int n6 = 0;

      for (int i = 0; i < n; i++) {
         float f = stbTrueTypePackRangeArray[i].E;
         float f2 = f > 0.0F ? V(stbTrueTypeFontInfo, f) : b(stbTrueTypeFontInfo, -f);
         stbTrueTypePackContext.c = stbTrueTypePackRangeArray[i].i;
         stbTrueTypePackContext.m = stbTrueTypePackRangeArray[i].y;
         float f3 = 1.0F / stbTrueTypePackContext.c;
         float f4 = 1.0F / stbTrueTypePackContext.m;
         float f5 = U(stbTrueTypePackContext.c);
         float f6 = U(stbTrueTypePackContext.m);

         for (int j = 0; j < stbTrueTypePackRangeArray[i].b; j++) {
            StbTrueTypePackRectangle stbTrueTypePackRectangle = stbTrueTypePackRectangleArray[n6];
            if (stbTrueTypePackRectangle.M != 0 && stbTrueTypePackRectangle.N != 0 && stbTrueTypePackRectangle.e != 0) {
               StbTrueTypePackedChar stbTrueTypePackedChar = stbTrueTypePackRangeArray[i].M[j];
               int[] nArray = new int[1];
               int[] nArray2 = new int[1];
               int[] nArray3 = new int[1];
               int[] nArray4 = new int[1];
               int[] nArray5 = new int[1];
               int[] nArray6 = new int[1];
               int n7 = stbTrueTypePackRangeArray[i].N == null ? stbTrueTypePackRangeArray[i].R + j : stbTrueTypePackRangeArray[i].N[j];
               int n8 = T(stbTrueTypeFontInfo, n7);
               int n9 = stbTrueTypePackContext.G;
               stbTrueTypePackRectangle.P += n9;
               stbTrueTypePackRectangle.J += n9;
               stbTrueTypePackRectangle.N -= n9;
               stbTrueTypePackRectangle.e -= n9;
               q(stbTrueTypeFontInfo, n8, nArray, nArray2);
               Z(stbTrueTypeFontInfo, n8, f2 * stbTrueTypePackContext.c, f2 * stbTrueTypePackContext.m, nArray3, nArray4, nArray5, nArray6);
               j(
                  stbTrueTypeFontInfo,
                  stbTrueTypePackContext.X,
                  stbTrueTypePackRectangle.P + stbTrueTypePackRectangle.J * stbTrueTypePackContext.b,
                  stbTrueTypePackRectangle.N - stbTrueTypePackContext.c + 1,
                  stbTrueTypePackRectangle.e - stbTrueTypePackContext.m + 1,
                  stbTrueTypePackContext.b,
                  f2 * stbTrueTypePackContext.c,
                  f2 * stbTrueTypePackContext.m,
                  0.0F,
                  0.0F,
                  n8
               );
               if (stbTrueTypePackContext.c > 1) {
                  H(
                     stbTrueTypePackContext.X,
                     stbTrueTypePackRectangle.P + stbTrueTypePackRectangle.J * stbTrueTypePackContext.b,
                     stbTrueTypePackRectangle.N,
                     stbTrueTypePackRectangle.e,
                     stbTrueTypePackContext.b,
                     stbTrueTypePackContext.c
                  );
               }

               if (stbTrueTypePackContext.m > 1) {
                  p(
                     stbTrueTypePackContext.X,
                     stbTrueTypePackRectangle.P + stbTrueTypePackRectangle.J * stbTrueTypePackContext.b,
                     stbTrueTypePackRectangle.N,
                     stbTrueTypePackRectangle.e,
                     stbTrueTypePackContext.b,
                     stbTrueTypePackContext.m
                  );
               }

               stbTrueTypePackedChar.b = (short)stbTrueTypePackRectangle.P;
               stbTrueTypePackedChar.k = (short)stbTrueTypePackRectangle.J;
               stbTrueTypePackedChar.q = (short)(stbTrueTypePackRectangle.P + stbTrueTypePackRectangle.N);
               stbTrueTypePackedChar.p = (short)(stbTrueTypePackRectangle.J + stbTrueTypePackRectangle.e);
               stbTrueTypePackedChar.A = f2 * nArray[0];
               stbTrueTypePackedChar.s = nArray3[0] * f3 + f5;
               stbTrueTypePackedChar.G = nArray4[0] * f4 + f6;
               stbTrueTypePackedChar.t = (nArray3[0] + stbTrueTypePackRectangle.N) * f3 + f5;
               stbTrueTypePackedChar.R = (nArray4[0] + stbTrueTypePackRectangle.e) * f4 + f6;
               if (n8 == 0) {
                  n2 = j;
               }
            } else if (stbTrueTypePackContext.h != 0) {
               n3 = 0;
            } else if (stbTrueTypePackRectangle.M != 0 && stbTrueTypePackRectangle.N == 0 && stbTrueTypePackRectangle.e == 0 && n2 >= 0) {
               stbTrueTypePackRangeArray[i].M[j] = stbTrueTypePackRangeArray[i].M[n2];
            } else {
               n3 = 0;
            }

            n6++;
         }
      }

      stbTrueTypePackContext.c = n4;
      stbTrueTypePackContext.m = n5;
      return n3;
   }

   static boolean d(byte[] byArray, int n, int n2, int n3, int n4, int n5) {
      return (byArray[n] & 255) == n2 && (byArray[n + 1] & 255) == n3 && (byArray[n + 2] & 255) == n4 && (byArray[n + 3] & 255) == n5;
   }

   static int s(byte[] byArray) {
      if (c$src$Z$1nktxih(byArray, 0)) {
         return 1;
      } else {
         return !f(byArray, 0, "ttcf") || c$src$J$1nktx4p(byArray, 4) != 65536L && c$src$J$1nktx4p(byArray, 4) != 131072L ? 0 : B(byArray, 8);
      }
   }

   static void A(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
      if (stbTrueTypeCharStringContext.J != 0) {
         V(stbTrueTypeCharStringContext, n2, n3);
         if (n == 4) {
            V(stbTrueTypeCharStringContext, n4, n5);
            V(stbTrueTypeCharStringContext, n6, n7);
         }
      } else {
         d(stbTrueTypeCharStringContext.V[stbTrueTypeCharStringContext.q], n, n2, n3, n4, n5);
         stbTrueTypeCharStringContext.V[stbTrueTypeCharStringContext.q].a = (short)n6;
         stbTrueTypeCharStringContext.V[stbTrueTypeCharStringContext.q].s = (short)n7;
      }

      stbTrueTypeCharStringContext.q++;
   }

   static int Q(StbByteBufferView frame) {
      return frame.h >= frame.y ? 0 : frame.f[frame.C + frame.h++] & 0xFF;
   }

   static int E(byte[] byArray, int n, byte[] byArray2, int n2, int n3, int n4, int n5) {
      int n6 = x(byArray, n + 2);
      int n7 = n + x(byArray, n + 4);
      int i = 0;

      while (true) {
         if (i >= n6) {
            return 0;
         }

         int n8 = n + 6 + 12 * i;
         int n9 = x(byArray, n8 + 6);
         if (n9 == n4) {
            int n10 = x(byArray, n8 + 0);
            int n11 = x(byArray, n8 + 2);
            int n12 = x(byArray, n8 + 4);
            if (n10 == 0 || n10 == 3 && n11 == 1 || n10 == 3 && n11 == 10) {
               int n13 = x(byArray, n8 + 8);
               int n14 = x(byArray, n8 + 10);
               int n15 = Y(byArray2, n2, n3, byArray, n7 + n14, n13);
               if (n15 >= 0) {
                  if (i + 1 < n6
                     && x(byArray, n8 + 12 + 6) == n5
                     && x(byArray, n8 + 12) == n10
                     && x(byArray, n8 + 12 + 2) == n11
                     && x(byArray, n8 + 12 + 4) == n12) {
                     n13 = x(byArray, n8 + 12 + 8);
                     n14 = x(byArray, n8 + 12 + 10);
                     if (n13 == 0) {
                        if (n15 == n3) {
                           break;
                        }
                     } else if (n15 < n3 && (byArray2[n2 + n15] & 255) == 32) {
                        n15++;
                        if (d(byArray2, n2 + n15, n3 - n15, byArray, n7 + n14, n13)) {
                           break;
                        }
                     }
                  } else if (n15 == n3) {
                     return 1;
                  }
               }
            }
         }

         i++;
      }

      return 1;
   }

   static int Y(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
      int n5 = 0;

      while (n4 > 0) {
         int n6 = (byArray2[n3] & 255) * 256 + (byArray2[n3 + 1] & 255);
         if (n6 < 128) {
            if (n5 >= n2) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != n6) {
               return -1;
            }
         } else if (n6 < 2048) {
            if (n5 + 1 >= n2) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != 192 + (n6 >> 6)) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != 128 + (n6 & 63)) {
               return -1;
            }
         } else if (n6 >= 55296 && n6 < 56320) {
            int n7 = (byArray2[n3 + 2] & 255) * 256 + (byArray2[n3 + 3] & 255);
            if (n5 + 3 >= n2) {
               return -1;
            }

            int n8 = (n6 - 55296 << 10) + (n7 - 56320) + 65536;
            if ((byArray[n + n5++] & 255) != 240 + (n8 >> 18)) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != 128 + (n8 >> 12 & 63)) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != 128 + (n8 >> 6 & 63)) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != 128 + (n8 & 63)) {
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

            if ((byArray[n + n5++] & 255) != 224 + (n6 >> 12)) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != 128 + (n6 >> 6 & 63)) {
               return -1;
            }

            if ((byArray[n + n5++] & 255) != 128 + (n6 & 63)) {
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
      StbTrueTypeVertex[] stbTrueTypeVertexArray = m(stbTrueTypeFontInfo, n5);
      int n6 = stbTrueTypeVertexArray != null ? stbTrueTypeVertexArray.length : 0;
      StbTrueTypeBitmap stbTrueTypeBitmap = new StbTrueTypeBitmap();
      G(stbTrueTypeFontInfo, n5, f, f2, f3, f4, nArray, nArray2, null, null);
      stbTrueTypeBitmap.E = byArray;
      stbTrueTypeBitmap.h = n;
      stbTrueTypeBitmap.K = n2;
      stbTrueTypeBitmap.c = n3;
      stbTrueTypeBitmap.A = n4;
      if (stbTrueTypeBitmap.K != 0 && stbTrueTypeBitmap.c != 0) {
         b(stbTrueTypeBitmap, 0.35F, stbTrueTypeVertexArray, n6, f, f2, f3, f4, nArray[0], nArray2[0], 1);
      }
   }

   public static float b(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f) {
      int n = x(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.c + 18);
      return f / n;
   }

   public static byte[] q(
      StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, float f3, float f4, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4
   ) {
      return F(stbTrueTypeFontInfo, f, f2, f3, f4, T(stbTrueTypeFontInfo, n), nArray, nArray2, nArray3, nArray4);
   }

   public static void Y(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      nArray[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.c + 36);
      nArray2[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.c + 38);
      nArray3[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.c + 40);
      nArray4[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.c + 42);
   }

   static StbTrueTypePoint[] P(StbTrueTypeVertex[] stbTrueTypeVertexArray, int n, float f, int[][] nArray, int[] nArray2) {
      StbTrueTypePoint[] stbTrueTypePointArray = null;
      int[] nArray3 = new int[]{0};
      float f2 = f * f;
      int n3 = 0;
      int n4 = 0;

      for (int n2 = 0; n2 < n; n2++) {
         if (stbTrueTypeVertexArray[n2].O == 1) {
            n3++;
         }
      }

      nArray2[0] = n3;
      if (n3 == 0) {
         return null;
      } else {
         nArray[0] = new int[n3];

         for (int i = 0; i < 2; i++) {
            float f3 = 0.0F;
            float f4 = 0.0F;
            if (i == 1) {
               stbTrueTypePointArray = new StbTrueTypePoint[nArray3[0]];

               for (int var15 = 0; var15 < stbTrueTypePointArray.length; var15++) {
                  stbTrueTypePointArray[var15] = new StbTrueTypePoint();
               }
            }

            nArray3[0] = 0;
            n3 = -1;

            for (int var16 = 0; var16 < n; var16++) {
               switch (stbTrueTypeVertexArray[var16].O) {
                  case 1:
                     if (n3 >= 0) {
                        nArray[0][n3] = nArray3[0] - n4;
                     }

                     n3++;
                     n4 = nArray3[0];
                     f3 = stbTrueTypeVertexArray[var16].f;
                     f4 = stbTrueTypeVertexArray[var16].H;
                     int n5 = nArray3[0]++;
                     r(stbTrueTypePointArray, n5, f3, f4);
                     break;
                  case 2:
                     f3 = stbTrueTypeVertexArray[var16].f;
                     f4 = stbTrueTypeVertexArray[var16].H;
                     int n6 = nArray3[0]++;
                     r(stbTrueTypePointArray, n6, f3, f4);
                     break;
                  case 3:
                     y(
                        stbTrueTypePointArray,
                        nArray3,
                        f3,
                        f4,
                        stbTrueTypeVertexArray[var16].h,
                        stbTrueTypeVertexArray[var16].D,
                        stbTrueTypeVertexArray[var16].f,
                        stbTrueTypeVertexArray[var16].H,
                        f2,
                        0
                     );
                     f3 = stbTrueTypeVertexArray[var16].f;
                     f4 = stbTrueTypeVertexArray[var16].H;
                     break;
                  case 4:
                     w(
                        stbTrueTypePointArray,
                        nArray3,
                        f3,
                        f4,
                        stbTrueTypeVertexArray[var16].h,
                        stbTrueTypeVertexArray[var16].D,
                        stbTrueTypeVertexArray[var16].a,
                        stbTrueTypeVertexArray[var16].s,
                        stbTrueTypeVertexArray[var16].f,
                        stbTrueTypeVertexArray[var16].H,
                        f2,
                        0
                     );
                     f3 = stbTrueTypeVertexArray[var16].f;
                     f4 = stbTrueTypeVertexArray[var16].H;
               }
            }

            nArray[0][n3] = nArray3[0] - n4;
         }

         return stbTrueTypePointArray;
      }
   }

   static int i(float f, float f2, int n, StbTrueTypeVertex[] stbTrueTypeVertexArray) {
      float[] fArray = new float[2];
      float[] fArray2 = new float[]{1.0F, 0.0F};
      int n2 = 0;
      float f3 = f2 % 1.0F;
      if (f3 < 0.01F) {
         f2 += 0.01F;
      } else if (f3 > 0.99F) {
         f2 -= 0.01F;
      }

      fArray[0] = f;
      fArray[1] = f2;

      for (int i = 0; i < n; i++) {
         if (stbTrueTypeVertexArray[i].O == 2) {
            short s4 = stbTrueTypeVertexArray[i - 1].f;
            short s3 = stbTrueTypeVertexArray[i - 1].H;
            short s2 = stbTrueTypeVertexArray[i].f;
            short s = stbTrueTypeVertexArray[i].H;
            if (f2 > Math.min((int)s3, (int)s) && f2 < Math.max((int)s3, (int)s) && f > Math.min((int)s4, (int)s2) && (f2 - s3) / (s - s3) * (s2 - s4) + s4 < f
               )
             {
               n2 += s3 < s ? 1 : -1;
            }
         }

         if (stbTrueTypeVertexArray[i].O == 3) {
            short s4 = stbTrueTypeVertexArray[i - 1].f;
            short s3 = stbTrueTypeVertexArray[i - 1].H;
            short s2 = stbTrueTypeVertexArray[i].h;
            short s = stbTrueTypeVertexArray[i].D;
            short s5 = stbTrueTypeVertexArray[i].f;
            short s6 = stbTrueTypeVertexArray[i].H;
            int n3 = Math.min(s4, Math.min((int)s2, (int)s5));
            int n4 = Math.min(s3, Math.min((int)s, (int)s6));
            int n5 = Math.max(s3, Math.max((int)s, (int)s6));
            if (f2 > n4 && f2 < n5 && f > n3) {
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
               if (!d(fArray3, fArray4) && !d(fArray4, fArray5)) {
                  int n6 = J(fArray, fArray2, fArray3, fArray4, fArray5, fArray6);
                  if (n6 >= 1 && fArray6[0][0] < 0.0F) {
                     n2 += fArray6[0][1] < 0.0F ? -1 : 1;
                  }

                  if (n6 >= 2 && fArray6[1][0] < 0.0F) {
                     n2 += fArray6[1][1] < 0.0F ? -1 : 1;
                  }
               } else {
                  s4 = stbTrueTypeVertexArray[i - 1].f;
                  s3 = stbTrueTypeVertexArray[i - 1].H;
                  s2 = stbTrueTypeVertexArray[i].f;
                  s = stbTrueTypeVertexArray[i].H;
                  if (f2 > Math.min((int)s3, (int)s)
                     && f2 < Math.max((int)s3, (int)s)
                     && f > Math.min((int)s4, (int)s2)
                     && (f2 - s3) / (s - s3) * (s2 - s4) + s4 < f) {
                     n2 += s3 < s ? 1 : -1;
                  }
               }
            }
         }
      }

      return n2;
   }

   static boolean S(byte[] byArray, int n, byte[] byArray2, int n2, int n3, int n4) {
      if (!c$src$Z$1nktxih(byArray, n)) {
         return false;
      } else if (n4 != 0 && (x(byArray, (int)v(byArray, n, "head") + 44) & 7) != (n4 & 7)) {
         return false;
      } else {
         long l = v(byArray, n, "name");
         if (l == 0L) {
            return false;
         } else {
            if (n4 != 0) {
               if (E(byArray, (int)l, byArray2, n2, n3, 16, -1) != 0) {
                  return true;
               }

               if (E(byArray, (int)l, byArray2, n2, n3, 1, -1) != 0) {
                  return true;
               }

               if (E(byArray, (int)l, byArray2, n2, n3, 3, -1) != 0) {
                  return true;
               }
            } else {
               if (E(byArray, (int)l, byArray2, n2, n3, 16, 17) != 0) {
                  return true;
               }

               if (E(byArray, (int)l, byArray2, n2, n3, 1, 2) != 0) {
                  return true;
               }

               if (E(byArray, (int)l, byArray2, n2, n3, 3, -1) != 0) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   static void A(float[] fArray, int n, int n2, StbTrueTypeActiveEdge stbTrueTypeActiveEdge, float f, float f2, float f3, float f4) {
      if (f2 != f4) {
         if (!G && !(f2 < f4)) {
            throw new AssertionError();
         } else if (!G && !(stbTrueTypeActiveEdge.j <= stbTrueTypeActiveEdge.m)) {
            throw new AssertionError();
         } else if (!(f2 > stbTrueTypeActiveEdge.m)) {
            if (!(f4 < stbTrueTypeActiveEdge.j)) {
               if (f2 < stbTrueTypeActiveEdge.j) {
                  f += (f3 - f) * (stbTrueTypeActiveEdge.j - f2) / (f4 - f2);
                  f2 = stbTrueTypeActiveEdge.j;
               }

               if (f4 > stbTrueTypeActiveEdge.m) {
                  f3 += (f3 - f) * (stbTrueTypeActiveEdge.m - f4) / (f4 - f2);
                  f4 = stbTrueTypeActiveEdge.m;
               }

               if (f <= n2 && f3 <= n2) {
                  int n3 = n + n2;
                  fArray[n3] += stbTrueTypeActiveEdge.T * (f4 - f2);
               } else if (!(f >= n2 + 1) || !(f3 >= n2 + 1)) {
                  int n4 = n + n2;
                  fArray[n4] += stbTrueTypeActiveEdge.T * (f4 - f2) * (1.0F - (f - n2 + (f3 - n2)) / 2.0F);
               }
            }
         }
      }
   }

   static int y(StbTrueTypePoint[] stbTrueTypePointArray, int[] nArray, float f, float f2, float f3, float f4, float f5, float f6, float f7, int n) {
      float f8 = (f + 2.0F * f3 + f5) / 4.0F;
      float f9 = (f2 + 2.0F * f4 + f6) / 4.0F;
      float f10 = (f + f5) / 2.0F - f8;
      float f11 = (f2 + f6) / 2.0F - f9;
      if (n > 16) {
         return 1;
      } else {
         if (f10 * f10 + f11 * f11 > f7) {
            y(stbTrueTypePointArray, nArray, f, f2, (f + f3) / 2.0F, (f2 + f4) / 2.0F, f8, f9, f7, n + 1);
            y(stbTrueTypePointArray, nArray, f8, f9, (f3 + f5) / 2.0F, (f4 + f6) / 2.0F, f5, f6, f7, n + 1);
         } else {
            r(stbTrueTypePointArray, nArray[0], f5, f6);
            nArray[0]++;
         }

         return 1;
      }
   }

   public static void n(StbTrueTypePackContext stbTrueTypePackContext, int n, int n2) {
      if (!G && n > 8) {
         throw new AssertionError();
      } else if (!G && n2 > 8) {
         throw new AssertionError();
      } else {
         if (n <= 8) {
            stbTrueTypePackContext.c = n;
         }

         if (n2 <= 8) {
            stbTrueTypePackContext.m = n2;
         }
      }
   }

   static int L(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n) {
      stbTrueTypeFontInfo.S = byArray;
      stbTrueTypeFontInfo.U = n;
      stbTrueTypeFontInfo.g = x(null, 0, 0);
      long l2 = v(byArray, n, "cmap");
      stbTrueTypeFontInfo.v = (int)v(byArray, n, "loca");
      stbTrueTypeFontInfo.c = (int)v(byArray, n, "head");
      stbTrueTypeFontInfo.p = (int)v(byArray, n, "glyf");
      stbTrueTypeFontInfo.w = (int)v(byArray, n, "hhea");
      stbTrueTypeFontInfo.d = (int)v(byArray, n, "hmtx");
      stbTrueTypeFontInfo.L = (int)v(byArray, n, "kern");
      stbTrueTypeFontInfo.G = (int)v(byArray, n, "GPOS");
      if (l2 != 0L && stbTrueTypeFontInfo.c != 0 && stbTrueTypeFontInfo.w != 0 && stbTrueTypeFontInfo.d != 0) {
         if (stbTrueTypeFontInfo.p != 0) {
            if (stbTrueTypeFontInfo.v == 0) {
               return 0;
            }
         } else {
            long l3 = 2L;
            long l4 = 0L;
            long l5 = 0L;
            long l6 = 0L;
            long l7 = v(byArray, n, "CFF ");
            if (l7 == 0L) {
               return 0;
            }

            stbTrueTypeFontInfo.H = x(null, 0, 0);
            stbTrueTypeFontInfo.N = x(null, 0, 0);
            stbTrueTypeFontInfo.g = x(byArray, (int)l7, 536870912);
            StbByteBufferView frame = stbTrueTypeFontInfo.g.k();
            K(frame, 2);
            i(frame, Q(frame));
            B(frame);
            StbByteBufferView frame2 = B(frame);
            StbByteBufferView frame3 = e(frame2, 0);
            B(frame);
            stbTrueTypeFontInfo.j = B(frame);
            long[] lArray = new long[]{l4};
            X(frame3, 17, 1, lArray);
            l4 = lArray[0];
            long[] lArray2 = new long[]{l3};
            X(frame3, 262, 1, lArray2);
            l3 = lArray2[0];
            long[] lArray3 = new long[]{l5};
            X(frame3, 292, 1, lArray3);
            l5 = lArray3[0];
            long[] lArray4 = new long[]{l6};
            X(frame3, 293, 1, lArray4);
            l6 = lArray4[0];
            stbTrueTypeFontInfo.J = u(frame, frame3);
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

               i(frame, (int)l5);
               stbTrueTypeFontInfo.H = B(frame);
               stbTrueTypeFontInfo.N = E(frame, (int)l6, frame.y - (int)l6);
            }

            i(frame, (int)l4);
            stbTrueTypeFontInfo.z = B(frame);
         }

         long l;
         stbTrueTypeFontInfo.h = (l = v(byArray, n, "maxp")) != 0L ? x(byArray, (int)(l + 4L)) : '\uffff';
         stbTrueTypeFontInfo.Q = -1;
         int n2 = x(byArray, (int)(l2 + 2L));
         stbTrueTypeFontInfo.Y = 0;

         for (int i = 0; i < n2; i++) {
            long l8 = l2 + 4L + 8 * i;
            switch (x(byArray, (int)l8)) {
               case 0:
                  stbTrueTypeFontInfo.Y = (int)(l2 + c$src$J$1nktx4p(byArray, (int)l8 + 4));
                  break;
               case 3:
                  switch (x(byArray, (int)l8 + 2)) {
                     case 1:
                     case 10:
                        stbTrueTypeFontInfo.Y = (int)(l2 + c$src$J$1nktx4p(byArray, (int)l8 + 4));
                  }
            }
         }

         if (stbTrueTypeFontInfo.Y == 0) {
            return 0;
         } else {
            stbTrueTypeFontInfo.T = x(byArray, stbTrueTypeFontInfo.c + 50);
            return 1;
         }
      } else {
         return 0;
      }
   }

   public static int v(StbTrueTypeFontInfo stbTrueTypeFontInfo) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      int n = stbTrueTypeFontInfo.L;
      if (n == 0) {
         return 0;
      } else if (x(byArray, n + 2) < 1) {
         return 0;
      } else {
         return x(byArray, n + 8) != 1 ? 0 : x(byArray, n + 10);
      }
   }

   public static int z(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n) {
      return L(stbTrueTypeFontInfo, byArray, n);
   }

   static int E(byte[] byArray, int n) {
      if (c$src$Z$1nktxih(byArray, 0)) {
         return n == 0 ? 0 : -1;
      } else if (f(byArray, 0, "ttcf") && (c$src$J$1nktx4p(byArray, 4) == 65536L || c$src$J$1nktx4p(byArray, 4) == 131072L)) {
         int n2 = B(byArray, 8);
         return n >= n2 ? -1 : (int)c$src$J$1nktx4p(byArray, 12 + n * 4);
      } else {
         return -1;
      }
   }

   static void d(StbTrueTypeCharStringContext stbTrueTypeCharStringContext) {
      if (stbTrueTypeCharStringContext.s != stbTrueTypeCharStringContext.C || stbTrueTypeCharStringContext.p != stbTrueTypeCharStringContext.o) {
         A(stbTrueTypeCharStringContext, 2, (int)stbTrueTypeCharStringContext.s, (int)stbTrueTypeCharStringContext.p, 0, 0, 0, 0);
      }
   }

   static void H(byte[] byArray, int n, int n2, int n3, int n4, int n5) {
      byte[] byArray2 = new byte[8];
      int n6 = n2 - n5;
      Arrays.fill(byArray2, (byte)0);

      for (int i = 0; i < n3; i++) {
         Arrays.fill(byArray2, 0, n5, (byte)0);
         int n8 = 0;
         int n7;
         switch (n5) {
            case 2:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7];
                  byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 255) - (byArray2[n7 & 7] & 255)) / 2);
               }
               break;
            case 3:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7];
                  byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 255) - (byArray2[n7 & 7] & 255)) / 3);
               }
               break;
            case 4:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7];
                  byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 255) - (byArray2[n7 & 7] & 255)) / 4);
               }
               break;
            case 5:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7];
                  byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 255) - (byArray2[n7 & 7] & 255)) / 5);
               }
               break;
            default:
               for (n7 = 0; n7 <= n6; n7++) {
                  byArray2[n7 + n5 & 7] = byArray[n + n7];
                  byArray[n + n7] = (byte)((n8 += (byArray[n + n7] & 255) - (byArray2[n7 & 7] & 255)) / n5);
               }
         }

         while (n7 < n2) {
            byArray[n + n7] = (byte)((n8 -= byArray2[n7 & 7] & 255) / n5);
            n7++;
         }

         n += n4;
      }
   }

   public static StbTrueTypeVertex[] m(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      return stbTrueTypeFontInfo.g.y == 0 ? o(stbTrueTypeFontInfo, n) : a$src$ALgg_vape_ui_font_stb_StbTrueTypeVertex_$1sz52d4(stbTrueTypeFontInfo, n);
   }

   public static void u(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, float f3, float f4, int n5) {
      j(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, f3, f4, T(stbTrueTypeFontInfo, n5));
   }

   public static void F(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, int n5) {
      u(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, 0.0F, 0.0F, n5);
   }

   static int L(byte[] byArray, int n, int n2) {
      int n3 = x(byArray, n);
      switch (n3) {
         case 1:
            int n4 = x(byArray, n + 2);
            int n5 = 0;
            int n6 = n4 - 1;
            int n7 = n2;

            while (n5 <= n6) {
               int n8 = n + 4;
               int n9 = n5 + n6 >> 1;
               int n10 = x(byArray, n8 + 2 * n9);
               if (n7 < n10) {
                  n6 = n9 - 1;
               } else {
                  if (n7 <= n10) {
                     return n9;
                  }

                  n5 = n9 + 1;
               }
            }
            break;
         case 2:
            int n12 = x(byArray, n + 2);
            int n13 = n + 4;
            int n14 = 0;
            int n15 = n12 - 1;
            int n16 = n2;

            while (n14 <= n15) {
               int n17 = n14 + n15 >> 1;
               int n18 = n13 + 6 * n17;
               int n19 = x(byArray, n18);
               int n20 = x(byArray, n18 + 2);
               if (n16 < n19) {
                  n15 = n17 - 1;
               } else {
                  if (n16 <= n20) {
                     int n21 = x(byArray, n18 + 4);
                     return n21 + n2 - n19;
                  }

                  n14 = n17 + 1;
               }
            }
            break;
         default:
            return -1;
      }

      return -1;
   }

   public static int g(String string, String string2) {
      byte[] byArray2 = string.getBytes();
      byte[] byArray;
      return d(byArray2, 0, byArray2.length, byArray = string2.getBytes(), 0, byArray.length) ? 1 : 0;
   }

   static int X(float f, float f2, float f3, float[] fArray) {
      float f4 = -f / 3.0F;
      float f5 = f * (2.0F * f * f - 9.0F * f2) / 27.0F + f3;
      float f6 = f2 - f * f / 3.0F;
      float f7 = f6 * f6 * f6;
      float f8 = f5 * f5 + 4.0F * f7 / 27.0F;
      if (f8 >= 0.0F) {
         float f9 = (float)Math.sqrt(f8);
         float f10 = (-f5 + f9) / 2.0F;
         float f11 = (-f5 - f9) / 2.0F;
         f10 = w(f10);
         f11 = w(f11);
         fArray[0] = f4 + f10 + f11;
         return 1;
      } else {
         float f12 = (float)Math.sqrt(-f6 / 3.0F);
         float f13 = (float)Math.acos(-Math.sqrt(-27.0F / f7) * f5 / 2.0) / 3.0F;
         float f14 = (float)Math.cos(f13);
         float f15 = (float)Math.cos(f13 - 1.570796) * 1.7320508F;
         fArray[0] = f4 + f12 * 2.0F * f14;
         fArray[1] = f4 - f12 * (f14 + f15);
         fArray[2] = f4 - f12 * (f14 - f15);
         return 3;
      }
   }

   static long u(StbByteBufferView frame, int n) {
      long l = 0L;
      if (G || n >= 1 && n <= 4) {
         for (int i = 0; i < n; i++) {
            l = l << 8 | Q(frame);
         }

         return l;
      } else {
         throw new AssertionError();
      }
   }

   public static int U(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray) {
      return B(stbTrueTypeFontInfo, T(stbTrueTypeFontInfo, n), nArray);
   }

   public static int A(StbTrueTypeFontInfo stbTrueTypeFontInfo, StbTrueTypeKerningEntry[] stbTrueTypeKerningEntryArray, int n) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      int n2 = stbTrueTypeFontInfo.L;
      if (n2 == 0) {
         return 0;
      } else if (x(byArray, n2 + 2) < 1) {
         return 0;
      } else if (x(byArray, n2 + 8) != 1) {
         return 0;
      } else {
         int n3 = x(byArray, n2 + 10);
         if (n < n3) {
            n3 = n;
         }

         for (int i = 0; i < n3; i++) {
            stbTrueTypeKerningEntryArray[i].q = x(byArray, n2 + 18 + i * 6);
            stbTrueTypeKerningEntryArray[i].o = x(byArray, n2 + 20 + i * 6);
            stbTrueTypeKerningEntryArray[i].y = E$src$S$1b9gd7y(byArray, n2 + 22 + i * 6);
         }

         return n3;
      }
   }

   public static int G(
      StbTrueTypePackContext stbTrueTypePackContext,
      StbTrueTypeFontInfo stbTrueTypeFontInfo,
      StbTrueTypePackRange[] stbTrueTypePackRangeArray,
      int n,
      StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray
   ) {
      boolean bl = false;
      int n2 = 0;

      for (int i = 0; i < n; i++) {
         float f = stbTrueTypePackRangeArray[i].E;
         float f2 = f > 0.0F ? V(stbTrueTypeFontInfo, f) : b(stbTrueTypeFontInfo, -f);
         stbTrueTypePackRangeArray[i].i = stbTrueTypePackContext.c;
         stbTrueTypePackRangeArray[i].y = stbTrueTypePackContext.m;

         for (int j = 0; j < stbTrueTypePackRangeArray[i].b; j++) {
            int[] nArray = new int[1];
            int[] nArray2 = new int[1];
            int[] nArray3 = new int[1];
            int[] nArray4 = new int[1];
            int n3 = stbTrueTypePackRangeArray[i].N == null ? stbTrueTypePackRangeArray[i].R + j : stbTrueTypePackRangeArray[i].N[j];
            int n4 = T(stbTrueTypeFontInfo, n3);
            if (n4 != 0 || stbTrueTypePackContext.h == 0 && !bl) {
               G(stbTrueTypeFontInfo, n4, f2 * stbTrueTypePackContext.c, f2 * stbTrueTypePackContext.m, 0.0F, 0.0F, nArray, nArray2, nArray3, nArray4);
               stbTrueTypePackRectangleArray[n2].N = nArray3[0] - nArray[0] + stbTrueTypePackContext.G + stbTrueTypePackContext.c - 1;
               stbTrueTypePackRectangleArray[n2].e = nArray4[0] - nArray2[0] + stbTrueTypePackContext.G + stbTrueTypePackContext.m - 1;
               if (n4 == 0) {
                  bl = true;
               }
            } else {
               stbTrueTypePackRectangleArray[n2].e = 0;
               stbTrueTypePackRectangleArray[n2].N = 0;
            }

            n2++;
         }
      }

      return n2;
   }

   static StbByteBufferView p(StbByteBufferView frame, int n) {
      int n2 = q(frame);
      int n3 = 107;
      if (n2 >= 33900) {
         n3 = 32768;
      } else if (n2 >= 1240) {
         n3 = 1131;
      }

      int var4;
      return (var4 = n + n3) >= 0 && var4 < n2 ? e(frame, var4) : x(null, 0, 0);
   }

   static float E(float f, float f2, float f3, float f4, float f5) {
      return A(f, f3 - f2, f5 - f4);
   }

   static int V(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, StbTrueTypeCharStringContext stbTrueTypeCharStringContext) {
      boolean bl = true;
      int n2 = 0;
      int n3 = 0;
      int n4 = 0;
      boolean bl2 = false;
      float[] fArray = new float[48];
      StbByteBufferView[] frameArray = new StbByteBufferView[10];
      StbByteBufferView frame = stbTrueTypeFontInfo.J.k();
      StbByteBufferView frame2 = e(stbTrueTypeFontInfo.z, n);

      while (frame2.h < frame2.y) {
         boolean bl3;
         bl3 = true;
         int n5 = Q(frame2);
         label261:
         switch (n5) {
            case 1:
            case 3:
            case 18:
            case 23:
            {
               n2 += n4 / 2;
               break;
            }
            case 2:
            case 9:
            case 13:
            case 15:
            case 16:
            case 17:
            case 28:
            default:
            {
               if (n5 != 255 && n5 != 28 && n5 < 32) {
                  return 0;
               }

               float f;
               if (n5 == 255) {
                  f = (int)g(frame2) / 65536.0F;
               } else {
                  K(frame2, -1);
                  f = (short)v(frame2);
               }

               if (n4 >= 48) {
                  return 0;
               }

               fArray[n4++] = f;
               bl3 = false;
               break;
            }
            case 4:
            {
               bl = false;
               if (n4 < 1) {
                  return 0;
               }

               Q(stbTrueTypeCharStringContext, 0.0F, fArray[n4 - 1]);
               break;
            }
            case 5:
            {
               int n6xxx = 0;
               if (n4 < 2) {
                  return 0;
               }

               while (n6xxx + 1 < n4) {
                  z(stbTrueTypeCharStringContext, fArray[n6xxx], fArray[n6xxx + 1]);
                  n6xxx += 2;
               }
               break;
            }
            case 6:
            {
               int n6xxx = 0;
               if (n4 < 1) {
                  return 0;
               }

               while (n6xxx < n4) {
                  z(stbTrueTypeCharStringContext, fArray[n6xxx], 0.0F);
                  if (++n6xxx >= n4) {
                     break label261;
                  }

                  z(stbTrueTypeCharStringContext, 0.0F, fArray[n6xxx]);
                  n6xxx++;
               }
               break;
            }
            case 7:
            {
               int n6xxx = 0;
               if (n4 < 1) {
                  return 0;
               }

               for (int var39 = 0; var39 < n4; var39++) {
                  z(stbTrueTypeCharStringContext, 0.0F, fArray[var39]);
                  if (++var39 >= n4) {
                     break label261;
                  }

                  z(stbTrueTypeCharStringContext, fArray[var39], 0.0F);
               }
               break;
            }
            case 8:
            {
               int n6xxx = 0;
               if (n4 < 6) {
                  return 0;
               }

               while (n6xxx + 5 < n4) {
                  s(stbTrueTypeCharStringContext, fArray[n6xxx], fArray[n6xxx + 1], fArray[n6xxx + 2], fArray[n6xxx + 3], fArray[n6xxx + 4], fArray[n6xxx + 5]);
                  n6xxx += 6;
               }
               break;
            }
            case 10:
            {
               if (!bl2) {
                  if (stbTrueTypeFontInfo.N.y != 0) {
                     frame = W(stbTrueTypeFontInfo, n);
                  }

                  bl2 = true;
               }
            }
            case 29:
            {
               if (n4 < 1) {
                  return 0;
               }

               int n7 = (int)fArray[--n4];
               if (n3 >= 10) {
                  return 0;
               }

               frameArray[n3++] = frame2.k();
               frame2 = p(n5 == 10 ? frame : stbTrueTypeFontInfo.j, n7);
               if (frame2.y == 0) {
                  return 0;
               }

               frame2.h = 0;
               bl3 = false;
               break;
            }
            case 11:
            {
               if (n3 <= 0) {
                  return 0;
               }

               frame2 = frameArray[--n3];
               bl3 = false;
               break;
            }
            case 12:
            {
               int n8 = Q(frame2);
               switch (n8) {
                  case 34:
                  {
                     if (n4 < 7) {
                        return 0;
                     }

                     float fx = fArray[0];
                     float f2 = fArray[1];
                     float f3 = fArray[2];
                     float f4 = fArray[3];
                     float f5 = fArray[4];
                     float f6 = fArray[5];
                     float f7 = fArray[6];
                     s(stbTrueTypeCharStringContext, fx, 0.0F, f2, f3, f4, 0.0F);
                     s(stbTrueTypeCharStringContext, f5, 0.0F, f6, -f3, f7, 0.0F);
                     break label261;
                  }
                  case 35:
                  {
                     if (n4 < 13) {
                        return 0;
                     }

                     float fx = fArray[0];
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
                     s(stbTrueTypeCharStringContext, fx, f8, f2, f3, f4, f9);
                     s(stbTrueTypeCharStringContext, f5, f10, f6, f11, f7, f12);
                     break label261;
                  }
                  case 36:
                  {
                     if (n4 < 9) {
                        return 0;
                     }

                     float fx = fArray[0];
                     float f8 = fArray[1];
                     float f2 = fArray[2];
                     float f3 = fArray[3];
                     float f4 = fArray[4];
                     float f5 = fArray[5];
                     float f6 = fArray[6];
                     float f11 = fArray[7];
                     float f7 = fArray[8];
                     s(stbTrueTypeCharStringContext, fx, f8, f2, f3, f4, 0.0F);
                     s(stbTrueTypeCharStringContext, f5, 0.0F, f6, f11, f7, -(f8 + f3 + f11));
                     break label261;
                  }
                  case 37:
                  {
                     if (n4 < 11) {
                        return 0;
                     }

                     float fx = fArray[0];
                     float f8 = fArray[1];
                     float f2 = fArray[2];
                     float f3 = fArray[3];
                     float f4 = fArray[4];
                     float f9 = fArray[5];
                     float f5 = fArray[6];
                     float f10 = fArray[7];
                     float f6 = fArray[8];
                     float f11 = fArray[9];
                     float f12;
                     float f7 = f12 = fArray[10];
                     float f13 = fx + f2 + f4 + f5 + f6;
                     float f14 = f8 + f3 + f9 + f10 + f11;
                     if (Math.abs(f13) > Math.abs(f14)) {
                        f12 = -f14;
                     } else {
                        f7 = -f13;
                     }

                     s(stbTrueTypeCharStringContext, fx, f8, f2, f3, f4, f9);
                     s(stbTrueTypeCharStringContext, f5, f10, f6, f11, f7, f12);
                     break label261;
                  }
                  default:
                  {
                     return 0;
               }
                  }
            }
            case 14:
            {
               d(stbTrueTypeCharStringContext);
               return 1;
            }
            case 19:
            case 20:
            {
               if (bl) {
                  n2 += n4 / 2;
               }

               bl = false;
               K(frame2, (n2 + 7) / 8);
               break;
            }
            case 21:
            {
               bl = false;
               if (n4 < 2) {
                  return 0;
               }

               Q(stbTrueTypeCharStringContext, fArray[n4 - 2], fArray[n4 - 1]);
               break;
            }
            case 22:
            {
               bl = false;
               if (n4 < 1) {
                  return 0;
               }

               Q(stbTrueTypeCharStringContext, fArray[n4 - 1], 0.0F);
               break;
            }
            case 24:
            {
               int n6 = 0;
               if (n4 < 8) {
                  return 0;
               }

               while (n6 + 5 < n4 - 2) {
                  s(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1], fArray[n6 + 2], fArray[n6 + 3], fArray[n6 + 4], fArray[n6 + 5]);
                  n6 += 6;
               }

               if (n6 + 1 >= n4) {
                  return 0;
               }

               z(stbTrueTypeCharStringContext, fArray[n6], fArray[n6 + 1]);
               break;
            }
            case 25:
            {
               int n6x = 0;
               if (n4 < 8) {
                  return 0;
               }

               while (n6x + 1 < n4 - 6) {
                  z(stbTrueTypeCharStringContext, fArray[n6x], fArray[n6x + 1]);
                  n6x += 2;
               }

               if (n6x + 5 >= n4) {
                  return 0;
               }

               s(stbTrueTypeCharStringContext, fArray[n6x], fArray[n6x + 1], fArray[n6x + 2], fArray[n6x + 3], fArray[n6x + 4], fArray[n6x + 5]);
               break;
            }
            case 26:
            case 27:
            {
               int n6xx = 0;
               if (n4 < 4) {
                  return 0;
               }

               float fx = 0.0F;
               if ((n4 & 1) != 0) {
                  fx = fArray[n6xx];
                  n6xx++;
               }

               while (n6xx + 3 < n4) {
                  if (n5 == 27) {
                     s(stbTrueTypeCharStringContext, fArray[n6xx], fx, fArray[n6xx + 1], fArray[n6xx + 2], fArray[n6xx + 3], 0.0F);
                  } else {
                     s(stbTrueTypeCharStringContext, fx, fArray[n6xx], fArray[n6xx + 1], fArray[n6xx + 2], 0.0F, fArray[n6xx + 3]);
                  }

                  fx = 0.0F;
                  n6xx += 4;
               }
               break;
            }
            case 30:
            {
               int n6xxx = 0;
               if (n4 < 4) {
                  return 0;
               }

               while (n6xxx + 3 < n4) {
                  s(
                     stbTrueTypeCharStringContext,
                     0.0F,
                     fArray[n6xxx],
                     fArray[n6xxx + 1],
                     fArray[n6xxx + 2],
                     fArray[n6xxx + 3],
                     n4 - n6xxx == 5 ? fArray[n6xxx + 4] : 0.0F
                  );
                  n6xxx += 4;
                  if (n6xxx + 3 >= n4) {
                     break label261;
                  }

                  s(
                     stbTrueTypeCharStringContext,
                     fArray[n6xxx],
                     0.0F,
                     fArray[n6xxx + 1],
                     fArray[n6xxx + 2],
                     n4 - n6xxx == 5 ? fArray[n6xxx + 4] : 0.0F,
                     fArray[n6xxx + 3]
                  );
                  n6xxx += 4;
               }
               break;
            }
            case 31:
            {
               int n6xxx = 0;
               if (n4 < 4) {
                  return 0;
               }

               while (n6xxx + 3 < n4) {
                  s(
                     stbTrueTypeCharStringContext,
                     fArray[n6xxx],
                     0.0F,
                     fArray[n6xxx + 1],
                     fArray[n6xxx + 2],
                     n4 - n6xxx == 5 ? fArray[n6xxx + 4] : 0.0F,
                     fArray[n6xxx + 3]
                  );
                  n6xxx += 4;
                  if (n6xxx + 3 >= n4) {
                     break;
                  }

                  s(
                     stbTrueTypeCharStringContext,
                     0.0F,
                     fArray[n6xxx],
                     fArray[n6xxx + 1],
                     fArray[n6xxx + 2],
                     fArray[n6xxx + 3],
                     n4 - n6xxx == 5 ? fArray[n6xxx + 4] : 0.0F
                  );
                  n6xxx += 4;
               }
         }
            }

         if (bl3) {
            n4 = 0;
         }
      }

      return 0;
   }

   public static void b(
      StbTrueTypeBitmap stbTrueTypeBitmap,
      float f,
      StbTrueTypeVertex[] stbTrueTypeVertexArray,
      int n,
      float f2,
      float f3,
      float f4,
      float f5,
      int n2,
      int n3,
      int n4
   ) {
      float f6 = f2 > f3 ? f3 : f2;
      int[] nArray;
      int[][] nArrayArray;
      StbTrueTypePoint[] stbTrueTypePointArray = P(stbTrueTypeVertexArray, n, f / f6, nArrayArray = new int[][]{null}, nArray = new int[]{0});
      if (stbTrueTypePointArray != null) {
         b(stbTrueTypeBitmap, stbTrueTypePointArray, nArrayArray[0], nArray[0], f2, f3, f4, f5, n2, n3, n4);
      }
   }

   static int h(byte[] byArray, byte[] byArray2, int n) {
      int n3;
      for (int n2 = 0; (n3 = c(byArray, n2)) >= 0; n2++) {
         if (S(byArray, n3, byArray2, 0, byArray2.length, n)) {
            return n3;
         }
      }

      return n3;
   }

   public static int C(StbTrueTypePackContext stbTrueTypePackContext, byte[] byArray, int n, int n2, int n3, int n4) {
      StbTrueTypePackAtlasState stbTrueTypePackAtlasState = new StbTrueTypePackAtlasState();
      int n5 = n - n4;
      StbTrueTypeGlyphPackNode[] stbTrueTypeGlyphPackNodeArray = new StbTrueTypeGlyphPackNode[n5];

      for (int i = 0; i < n5; i++) {
         stbTrueTypeGlyphPackNodeArray[i] = new StbTrueTypeGlyphPackNode();
      }

      stbTrueTypePackContext.w = n;
      stbTrueTypePackContext.d = n2;
      stbTrueTypePackContext.X = byArray;
      stbTrueTypePackContext.J = stbTrueTypePackAtlasState;
      stbTrueTypePackContext.j = stbTrueTypeGlyphPackNodeArray;
      stbTrueTypePackContext.G = n4;
      stbTrueTypePackContext.b = n3 != 0 ? n3 : n;
      stbTrueTypePackContext.c = 1;
      stbTrueTypePackContext.m = 1;
      stbTrueTypePackContext.h = 0;
      b(stbTrueTypePackAtlasState, n - n4, n2 - n4, stbTrueTypeGlyphPackNodeArray, n5);
      if (byArray != null) {
         Arrays.fill(byArray, 0, n * n2, (byte)0);
      }

      return 1;
   }

   static int N(byte[] byArray, int n) {
      return byArray[n] & 0xFF;
   }

   static void w(
      StbTrueTypePoint[] stbTrueTypePointArray, int[] nArray, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n
   ) {
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
      if (n <= 16) {
         if (f20 > f9) {
            float f21 = (f + f3) / 2.0F;
            float f22 = (f2 + f4) / 2.0F;
            float f23 = (f3 + f5) / 2.0F;
            float f24 = (f4 + f6) / 2.0F;
            float f25 = (f5 + f7) / 2.0F;
            float f26 = (f6 + f8) / 2.0F;
            float f27 = (f21 + f23) / 2.0F;
            float f28 = (f22 + f24) / 2.0F;
            float f29 = (f23 + f25) / 2.0F;
            float f30 = (f24 + f26) / 2.0F;
            float f31 = (f27 + f29) / 2.0F;
            float f32 = (f28 + f30) / 2.0F;
            w(stbTrueTypePointArray, nArray, f, f2, f21, f22, f27, f28, f31, f32, f9, n + 1);
            w(stbTrueTypePointArray, nArray, f31, f32, f29, f30, f25, f26, f7, f8, f9, n + 1);
         } else {
            r(stbTrueTypePointArray, nArray[0], f7, f8);
            nArray[0]++;
         }
      }
   }

   static int J(float[] fArray, float[] fArray2, float[] fArray3, float[] fArray4, float[] fArray5, float[][] fArray6) {
      float f4 = fArray3[1] * fArray2[0] - fArray3[0] * fArray2[1];
      float f5 = fArray4[1] * fArray2[0] - fArray4[0] * fArray2[1];
      float f6 = fArray5[1] * fArray2[0] - fArray5[0] * fArray2[1];
      float f7 = fArray[1] * fArray2[0] - fArray[0] * fArray2[1];
      float f8 = f4 - 2.0F * f5 + f6;
      float f9 = f5 - f4;
      float f10 = f4 - f7;
      float f11 = 0.0F;
      float f12 = 0.0F;
      int n = 0;
      if (f8 != 0.0) {
         float f3 = f9 * f9 - f8 * f10;
         if (f3 > 0.0) {
            float f2 = -1.0F / f8;
            float f = (float)Math.sqrt(f3);
            f11 = (f9 + f) * f2;
            f12 = (f9 - f) * f2;
            if (f11 >= 0.0 && f11 <= 1.0) {
               n = 1;
            }

            if (f > 0.0 && f12 >= 0.0 && f12 <= 1.0) {
               if (n == 0) {
                  f11 = f12;
               }

               n++;
            }
         }
      } else {
         f11 = f10 / (-2.0F * f9);
         if (f11 >= 0.0 && f11 <= 1.0) {
            n = 1;
         }
      }

      if (n == 0) {
         return 0;
      } else {
         float f3 = 1.0F / (fArray2[0] * fArray2[0] + fArray2[1] * fArray2[1]);
         float f2x = fArray2[0] * f3;
         float fx = fArray2[1] * f3;
         float f13 = fArray3[0] * f2x + fArray3[1] * fx;
         float f14 = fArray4[0] * f2x + fArray4[1] * fx;
         float f15 = fArray5[0] * f2x + fArray5[1] * fx;
         float f16 = fArray[0] * f2x + fArray[1] * fx;
         float f17 = f14 - f13;
         float f18 = f15 - f13;
         float f19 = f13 - f16;
         fArray6[0][0] = f19 + f11 * (2.0F - 2.0F * f11) * f17 + f11 * f11 * f18;
         fArray6[0][1] = f8 * f11 + f9;
         if (n > 1) {
            fArray6[1][0] = f19 + f12 * (2.0F - 2.0F * f12) * f17 + f12 * f12 * f18;
            fArray6[1][1] = f8 * f12 + f9;
            return 2;
         } else {
            return 1;
         }
      }
   }

   static int i(byte[] byArray, int n, float f, byte[] byArray2, int n2, int n3, int n4, int n5, StbTrueTypeBakedChar[] stbTrueTypeBakedCharArray) {
      StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
      if (L(stbTrueTypeFontInfo, byArray, n) == 0) {
         return -1;
      } else {
         Arrays.fill(byArray2, 0, n2 * n3, (byte)0);
         int n6 = 1;
         int n7 = 1;
         int n8 = 1;
         float f2 = V(stbTrueTypeFontInfo, f);

         for (int i = 0; i < n5; i++) {
            int[] nArray = new int[1];
            int[] nArray2 = new int[1];
            int[] nArray3 = new int[1];
            int[] nArray4 = new int[1];
            int[] nArray5 = new int[1];
            int[] nArray6 = new int[1];
            int n9 = T(stbTrueTypeFontInfo, n4 + i);
            q(stbTrueTypeFontInfo, n9, nArray, nArray2);
            Z(stbTrueTypeFontInfo, n9, f2, f2, nArray3, nArray4, nArray5, nArray6);
            int n10 = nArray5[0] - nArray3[0];
            int n11 = nArray6[0] - nArray4[0];
            if (n7 + n10 + 1 >= n2) {
               n6 = n8;
               n7 = 1;
            }

            if (n6 + n11 + 1 >= n3) {
               return -i;
            }

            I(stbTrueTypeFontInfo, byArray2, n7 + n6 * n2, n10, n11, n2, f2, f2, n9);
            stbTrueTypeBakedCharArray[i].V = (short)n7;
            stbTrueTypeBakedCharArray[i].Q = (short)n6;
            stbTrueTypeBakedCharArray[i].U = (short)(n7 + n10);
            stbTrueTypeBakedCharArray[i].D = (short)(n6 + n11);
            stbTrueTypeBakedCharArray[i].l = f2 * nArray[0];
            stbTrueTypeBakedCharArray[i].B = nArray3[0];
            stbTrueTypeBakedCharArray[i].k = nArray4[0];
            n7 = n7 + n10 + 1;
            if (n6 + n11 + 1 > n8) {
               n8 = n6 + n11 + 1;
            }
         }

         return n8;
      }
   }

   static int m(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      StbTrueTypeCharStringContext stbTrueTypeCharStringContext = new StbTrueTypeCharStringContext(1);
      int n2 = V(stbTrueTypeFontInfo, n, stbTrueTypeCharStringContext);
      if (n2 != 0) {
         if (nArray != null) {
            int n3 = 0;
            nArray[n3] = stbTrueTypeCharStringContext.y;
         }

         if (nArray2 != null) {
            int n4 = 0;
            nArray2[n4] = stbTrueTypeCharStringContext.Z;
         }

         if (nArray3 != null) {
            int n5 = 0;
            nArray3[n5] = stbTrueTypeCharStringContext.c;
         }

         if (nArray4 != null) {
            int n6 = 0;
            nArray4[n6] = stbTrueTypeCharStringContext.f;
         }

         return stbTrueTypeCharStringContext.q;
      } else {
         if (nArray != null) {
            int n7 = 0;
            nArray[n7] = 0;
         }

         if (nArray2 != null) {
            int n8 = 0;
            nArray2[n8] = 0;
         }

         if (nArray3 != null) {
            int n9 = 0;
            nArray3[n9] = 0;
         }

         if (nArray4 != null) {
            int n10 = 0;
            nArray4[n10] = 0;
         }

         return 0;
      }
   }

   static void i(StbByteBufferView frame, int n) {
      if (G || n <= frame.y && n >= 0) {
         frame.h = n <= frame.y && n >= 0 ? n : frame.y;
      } else {
         throw new AssertionError();
      }
   }

   public static byte[] N(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, float f2, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      return F(stbTrueTypeFontInfo, f, f2, 0.0F, 0.0F, n, nArray, nArray2, nArray3, nArray4);
   }

   public static void G(
      StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, float f3, float f4, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4
   ) {
      int[] nArray5 = new int[]{0};
      int[] nArray6 = new int[]{0};
      int[] nArray7 = new int[]{0};
      int[] nArray8 = new int[]{0};
      if (F(stbTrueTypeFontInfo, n, nArray5, nArray6, nArray7, nArray8) == 0) {
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
            nArray[0] = (int)Math.floor(nArray5[0] * f + f3);
         }

         if (nArray2 != null) {
            nArray2[0] = (int)Math.floor(-nArray8[0] * f2 + f4);
         }

         if (nArray3 != null) {
            nArray3[0] = (int)Math.ceil(nArray7[0] * f + f3);
         }

         if (nArray4 != null) {
            nArray4[0] = (int)Math.ceil(-nArray6[0] * f2 + f4);
         }
      }
   }

   static long v(StbByteBufferView frame) {
      int n = Q(frame);
      if (n >= 32 && n <= 246) {
         return n - 139;
      } else if (n >= 247 && n <= 250) {
         return (n - 247) * 256 + Q(frame) + 108;
      } else if (n >= 251 && n <= 254) {
         return -(n - 251) * 256 - Q(frame) - 108;
      } else if (n == 28) {
         return p(frame);
      } else if (n == 29) {
         return g(frame);
      } else if (!G) {
         throw new AssertionError();
      } else {
         return 0L;
      }
   }

   static void G(StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n, int n2) {
      label58:
      while (n2 > 12) {
         int n3 = n2 >> 1;
         boolean bl2 = stbTrueTypeEdgeArray[n].X < stbTrueTypeEdgeArray[n + n3].X;
         boolean bl = stbTrueTypeEdgeArray[n + n3].X < stbTrueTypeEdgeArray[n + n2 - 1].X;
         if (bl2 != bl) {
            boolean bl4 = stbTrueTypeEdgeArray[n].X < stbTrueTypeEdgeArray[n + n2 - 1].X;
            int n4 = bl4 == bl ? 0 : n2 - 1;
            StbTrueTypeEdge stbTrueTypeEdge = stbTrueTypeEdgeArray[n + n4];
            stbTrueTypeEdgeArray[n + n4] = stbTrueTypeEdgeArray[n + n3];
            stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdge;
         }

         StbTrueTypeEdge stbTrueTypeEdge = stbTrueTypeEdgeArray[n];
         stbTrueTypeEdgeArray[n] = stbTrueTypeEdgeArray[n + n3];
         stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdge;
         int n5 = 1;
         int n6 = n2 - 1;

         while (true) {
            while (!(stbTrueTypeEdgeArray[n + n5].X < stbTrueTypeEdgeArray[n].X)) {
               while (stbTrueTypeEdgeArray[n].X < stbTrueTypeEdgeArray[n + n6].X) {
                  n6--;
               }

               if (n5 >= n6) {
                  if (n6 < n2 - n5) {
                     G(stbTrueTypeEdgeArray, n, n6);
                     n += n5;
                     n2 -= n5;
                     continue label58;
                  }

                  G(stbTrueTypeEdgeArray, n + n5, n2 - n5);
                  n2 = n6;
                  continue label58;
               }

               stbTrueTypeEdge = stbTrueTypeEdgeArray[n + n5];
               stbTrueTypeEdgeArray[n + n5] = stbTrueTypeEdgeArray[n + n6];
               stbTrueTypeEdgeArray[n + n6] = stbTrueTypeEdge;
               n5++;
               n6--;
            }

            n5++;
         }
      }
   }

   public static byte[] a(
      StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, int n, int n2, int n3, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4
   ) {
      return A(stbTrueTypeFontInfo, f, T(stbTrueTypeFontInfo, n), n2, n3, f2, nArray, nArray2, nArray3, nArray4);
   }

   public static int P(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      if (stbTrueTypeFontInfo.g.y != 0) {
         return m(stbTrueTypeFontInfo, n, null, null, null, null) == 0 ? 1 : 0;
      } else {
         int n2 = G(stbTrueTypeFontInfo, n);
         if (n2 < 0) {
            return 1;
         } else {
            short s = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n2);
            return s == 0 ? 1 : 0;
         }
      }
   }

   public static void q(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2) {
      int n2 = x(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.w + 34);
      if (n < n2) {
         if (nArray != null) {
            nArray[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.d + 4 * n);
         }

         if (nArray2 != null) {
            nArray2[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.d + 4 * n + 2);
         }
      } else {
         if (nArray != null) {
            nArray[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.d + 4 * (n2 - 1));
         }

         if (nArray2 != null) {
            nArray2[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.d + 4 * n2 + 2 * (n - n2));
         }
      }
   }

   static int c(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
      if (stbTrueTypeFontInfo.G == 0) {
         return 0;
      } else {
         byte[] byArray = stbTrueTypeFontInfo.S;
         int n3 = stbTrueTypeFontInfo.G;
         if (x(byArray, n3 + 0) != 1) {
            return 0;
         } else if (x(byArray, n3 + 2) != 0) {
            return 0;
         } else {
            int n4 = x(byArray, n3 + 8);
            int n5 = n3 + n4;
            int n6 = x(byArray, n5);

            for (int i = 0; i < n6; i++) {
               int n7 = x(byArray, n5 + 2 + 2 * i);
               int n8 = n5 + n7;
               int n9 = x(byArray, n8);
               int n10 = x(byArray, n8 + 4);
               int n11 = n8 + 6;
               if (n9 == 2) {
                  for (int j = 0; j < n10; j++) {
                     int n12 = x(byArray, n11 + 2 * j);
                     int n13 = n8 + n12;
                     int n14 = x(byArray, n13);
                     int n15 = x(byArray, n13 + 2);
                     int n16 = L(byArray, n13 + n15, n);
                     if (n16 != -1) {
                        switch (n14) {
                           case 1:
                           {
                              int n26 = x(byArray, n13 + 4);
                              int n27 = x(byArray, n13 + 6);
                              if (n26 != 4 || n27 != 0) {
                                 return 0;
                              }

                              int n25 = 2;
                              int n24 = x(byArray, n13 + 8);
                              int n23 = x(byArray, n13 + 10 + 2 * n16);
                              int n22 = n13 + n23;
                              int n28 = x(byArray, n22);
                              int n29 = n22 + 2;
                              if (n16 >= n24) {
                                 return 0;
                              }

                              int n21 = n2;
                              int n20 = n28 - 1;
                              int n19 = 0;

                              while (n19 <= n20) {
                                 int n18 = n19 + n20 >> 1;
                                 int n30 = n29 + (2 + n25) * n18;
                                 int n31 = x(byArray, n30);
                                 if (n21 < n31) {
                                    n20 = n18 - 1;
                                 } else {
                                    if (n21 <= n31) {
                                       return E$src$S$1b9gd7y(byArray, n30 + 2);
                                    }

                                    n19 = n18 + 1;
                                 }
                              }
                              break;
                           }
                           case 2:
                           {
                              int n19 = x(byArray, n13 + 4);
                              int n20 = x(byArray, n13 + 6);
                              if (n19 == 4 && n20 == 0) {
                                 int n18 = x(byArray, n13 + 8);
                                 int n17 = x(byArray, n13 + 10);
                                 int n21 = q(byArray, n13 + n18, n);
                                 int n26 = q(byArray, n13 + n17, n2);
                                 int n27 = x(byArray, n13 + 12);
                                 int n25 = x(byArray, n13 + 14);
                                 if (n21 >= 0 && n21 < n27) {
                                    if (n26 >= 0 && n26 < n25) {
                                       int n23 = n13 + 16;
                                       int n22 = n23 + 2 * n21 * n25;
                                       return E$src$S$1b9gd7y(byArray, n22 + 2 * n26);
                                    }

                                    return 0;
                                 }

                                 return 0;
                              }

                              return 0;
                           }
                           default:
                           {
                              return 0;
                        }
                           }
                     }
                  }
               }
            }

            return 0;
         }
      }
   }

   static int G(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      if (!G && stbTrueTypeFontInfo.g.y != 0) {
         throw new AssertionError();
      } else if (n >= stbTrueTypeFontInfo.h) {
         return -1;
      } else if (stbTrueTypeFontInfo.T >= 2) {
         return -1;
      } else {
         int n2;
         int n3;
         if (stbTrueTypeFontInfo.T == 0) {
            n3 = stbTrueTypeFontInfo.p + x(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.v + n * 2) * 2;
            n2 = stbTrueTypeFontInfo.p + x(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.v + n * 2 + 2) * 2;
         } else {
            n3 = (int)(stbTrueTypeFontInfo.p + c$src$J$1nktx4p(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.v + n * 4));
            n2 = (int)(stbTrueTypeFontInfo.p + c$src$J$1nktx4p(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.v + n * 4 + 4));
         }

         return n3 == n2 ? -1 : n3;
      }
   }

   static boolean c$src$Z$1nktxih(byte[] byArray, int n) {
      if (d(byArray, n, 49, 0, 0, 0)) {
         return true;
      } else if (f(byArray, n, "typ1")) {
         return true;
      } else if (f(byArray, n, "OTTO")) {
         return true;
      } else {
         return d(byArray, n, 0, 1, 0, 0) ? true : f(byArray, n, "true");
      }
   }

   public static int X(byte[] byArray, int n, float f, byte[] byArray2, int n2, int n3, int n4, int n5, StbTrueTypeBakedChar[] stbTrueTypeBakedCharArray) {
      return i(byArray, n, f, byArray2, n2, n3, n4, n5, stbTrueTypeBakedCharArray);
   }

   static void r(StbTrueTypePoint[] stbTrueTypePointArray, int n, float f, float f2) {
      if (stbTrueTypePointArray != null) {
         stbTrueTypePointArray[n].M = f;
         stbTrueTypePointArray[n].E = f2;
      }
   }

   public static int a(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      int n2 = J(stbTrueTypeFontInfo);
      if (n2 == 0) {
         return -1;
      } else {
         int n3 = x(byArray, n2);
         int n4 = n2 + 2;

         for (int i = 0; i < n3; i++) {
            int n5 = n4 + 12 * i;
            if (n >= x(byArray, n5) && n <= x(byArray, n5 + 2)) {
               return n5;
            }
         }

         return -1;
      }
   }

   public static void Z(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      G(stbTrueTypeFontInfo, n, f, f2, 0.0F, 0.0F, nArray, nArray2, nArray3, nArray4);
   }

   public static int B(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      if (stbTrueTypeFontInfo.Q == 0) {
         return 0;
      } else {
         int n2 = a(stbTrueTypeFontInfo, n);
         if (n2 >= 0) {
            if (nArray != null) {
               nArray[0] = stbTrueTypeFontInfo.Q + (int)c$src$J$1nktx4p(byArray, n2 + 4);
            }

            return (int)c$src$J$1nktx4p(byArray, n2 + 8);
         } else {
            return 0;
         }
      }
   }

   public static int s(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4) {
      return F(stbTrueTypeFontInfo, T(stbTrueTypeFontInfo, n), nArray, nArray2, nArray3, nArray4);
   }

   static float U(int n) {
      return n == 0 ? 0.0F : -(n - 1) / (2.0F * n);
   }

   static int v(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int n2) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      int n3 = stbTrueTypeFontInfo.L;
      if (n3 == 0) {
         return 0;
      } else if (x(byArray, n3 + 2) < 1) {
         return 0;
      } else if (x(byArray, n3 + 8) != 1) {
         return 0;
      } else {
         int n4 = 0;
         int n5 = x(byArray, n3 + 10) - 1;
         long l = (long)n << 16 | n2;

         while (n4 <= n5) {
            int n6 = n4 + n5 >> 1;
            long l2 = c$src$J$1nktx4p(byArray, n3 + 18 + n6 * 6);
            if (l < l2) {
               n5 = n6 - 1;
            } else {
               if (l <= l2) {
                  return E$src$S$1b9gd7y(byArray, n3 + 22 + n6 * 6);
               }

               n4 = n6 + 1;
            }
         }

         return 0;
      }
   }

   public static void V(
      StbTrueTypeBakedChar[] stbTrueTypeBakedCharArray,
      int n,
      int n2,
      int n3,
      float[] fArray,
      float[] fArray2,
      StbTrueTypeAlignedQuad stbTrueTypeAlignedQuad,
      int n4
   ) {
      float f = n4 != 0 ? 0.0F : -0.5F;
      float f2 = 1.0F / n;
      float f3 = 1.0F / n2;
      StbTrueTypeBakedChar stbTrueTypeBakedChar = stbTrueTypeBakedCharArray[n3];
      int n5 = (int)Math.floor(fArray[0] + stbTrueTypeBakedChar.B + 0.5F);
      int n6 = (int)Math.floor(fArray2[0] + stbTrueTypeBakedChar.k + 0.5F);
      stbTrueTypeAlignedQuad.v = n5 + f;
      stbTrueTypeAlignedQuad.x = n6 + f;
      stbTrueTypeAlignedQuad.K = n5 + stbTrueTypeBakedChar.U - stbTrueTypeBakedChar.V + f;
      stbTrueTypeAlignedQuad.M = n6 + stbTrueTypeBakedChar.D - stbTrueTypeBakedChar.Q + f;
      stbTrueTypeAlignedQuad.G = stbTrueTypeBakedChar.V * f2;
      stbTrueTypeAlignedQuad.O = stbTrueTypeBakedChar.Q * f3;
      stbTrueTypeAlignedQuad.S = stbTrueTypeBakedChar.U * f2;
      stbTrueTypeAlignedQuad.C = stbTrueTypeBakedChar.D * f3;
      fArray[0] += stbTrueTypeBakedChar.l;
   }

   static void s(StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n) {
      G(stbTrueTypeEdgeArray, 0, n);
      N(stbTrueTypeEdgeArray, 0, n);
   }

   static void d(StbTrueTypeVertex stbTrueTypeVertex, int n, int n2, int n3, int n4, int n5) {
      stbTrueTypeVertex.O = n;
      stbTrueTypeVertex.f = (short)n2;
      stbTrueTypeVertex.H = (short)n3;
      stbTrueTypeVertex.h = (short)n4;
      stbTrueTypeVertex.D = (short)n5;
   }

   static boolean d(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
      return n2 == Y(byArray, n, n2, byArray2, n3, n4);
   }

   static void t(StbTrueTypePackAtlasState stbTrueTypePackAtlasState, StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray, int n) {
      int n2;
      for (n2 = 0; n2 < n; n2++) {
         if (stbTrueTypePackAtlasState.n + stbTrueTypePackRectangleArray[n2].N > stbTrueTypePackAtlasState.f) {
            stbTrueTypePackAtlasState.n = 0;
            stbTrueTypePackAtlasState.a = stbTrueTypePackAtlasState.t;
         }

         if (stbTrueTypePackAtlasState.a + stbTrueTypePackRectangleArray[n2].e > stbTrueTypePackAtlasState.F) {
            break;
         }

         stbTrueTypePackRectangleArray[n2].P = stbTrueTypePackAtlasState.n;
         stbTrueTypePackRectangleArray[n2].J = stbTrueTypePackAtlasState.a;
         stbTrueTypePackRectangleArray[n2].M = 1;
         stbTrueTypePackAtlasState.n = stbTrueTypePackAtlasState.n + stbTrueTypePackRectangleArray[n2].N;
         if (stbTrueTypePackAtlasState.a + stbTrueTypePackRectangleArray[n2].e > stbTrueTypePackAtlasState.t) {
            stbTrueTypePackAtlasState.t = stbTrueTypePackAtlasState.a + stbTrueTypePackRectangleArray[n2].e;
         }
      }

      while (n2 < n) {
         stbTrueTypePackRectangleArray[n2].M = 0;
         n2++;
      }
   }

   public static float N(float f) {
      return -f;
   }

   public static void x(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int[] nArray2, int[] nArray3) {
      if (nArray != null) {
         nArray[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.w + 4);
      }

      if (nArray2 != null) {
         nArray2[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.w + 6);
      }

      if (nArray3 != null) {
         nArray3[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.w + 8);
      }
   }

   static void s(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, float f, float f2, float f3, float f4, float f5, float f6) {
      float f7 = stbTrueTypeCharStringContext.C + f;
      float f8 = stbTrueTypeCharStringContext.o + f2;
      float f9 = f7 + f3;
      float f10 = f8 + f4;
      stbTrueTypeCharStringContext.C = f9 + f5;
      stbTrueTypeCharStringContext.o = f10 + f6;
      A(stbTrueTypeCharStringContext, 4, (int)stbTrueTypeCharStringContext.C, (int)stbTrueTypeCharStringContext.o, (int)f7, (int)f8, (int)f9, (int)f10);
   }

   public static void I(StbTrueTypeFontInfo stbTrueTypeFontInfo, byte[] byArray, int n, int n2, int n3, int n4, float f, float f2, int n5) {
      j(stbTrueTypeFontInfo, byArray, n, n2, n3, n4, f, f2, 0.0F, 0.0F, n5);
   }

   public static void c(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, int[] nArray, int[] nArray2) {
      q(stbTrueTypeFontInfo, T(stbTrueTypeFontInfo, n), nArray, nArray2);
   }

   static StbByteBufferView E(StbByteBufferView frame, int n, int n2) {
      StbByteBufferView frame2 = x(null, 0, 0);
      if (n >= 0 && n2 >= 0 && n <= frame.y && n2 <= frame.y - n) {
         frame2.f = frame.f;
         frame2.C = frame.C + n;
         frame2.y = n2;
         return frame2;
      } else {
         return frame2;
      }
   }

   static int q(StbByteBufferView frame) {
      i(frame, 0);
      return p(frame);
   }

   public static int J(StbTrueTypeFontInfo stbTrueTypeFontInfo, int[] nArray, int[] nArray2, int[] nArray3) {
      int n = (int)v(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.U, "OS/2");
      if (n == 0) {
         return 0;
      } else {
         if (nArray != null) {
            nArray[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n + 68);
         }

         if (nArray2 != null) {
            nArray2[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n + 70);
         }

         if (nArray3 != null) {
            nArray3[0] = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, n + 72);
         }

         return 1;
      }
   }

   static StbByteBufferView u(StbByteBufferView frame, StbByteBufferView frame2) {
      long[] lArray = new long[]{0L};
      long[] lArray2 = new long[]{0L, 0L};
      X(frame2, 18, 2, lArray2);
      if (lArray2[1] != 0L && lArray2[0] != 0L) {
         StbByteBufferView frame3 = E(frame, (int)lArray2[1], (int)lArray2[0]);
         X(frame3, 19, 1, lArray);
         if (lArray[0] == 0L) {
            return x(null, 0, 0);
         } else {
            i(frame, (int)(lArray2[1] + lArray[0]));
            return B(frame);
         }
      } else {
         return x(null, 0, 0);
      }
   }

   public static void H(StbTrueTypePackContext stbTrueTypePackContext, StbTrueTypePackRectangle[] stbTrueTypePackRectangleArray, int n) {
      t(stbTrueTypePackContext.J, stbTrueTypePackRectangleArray, n);
   }

   static void X(StbByteBufferView frame, int n, int n2, long[] lArray) {
      StbByteBufferView frame2 = C(frame, n);

      for (int i = 0; i < n2 && frame2.h < frame2.y; i++) {
         lArray[i] = v(frame2);
      }
   }

   public static void p(
      StbTrueTypeFontInfo stbTrueTypeFontInfo, int n, float f, float f2, float f3, float f4, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4
   ) {
      G(stbTrueTypeFontInfo, T(stbTrueTypeFontInfo, n), f, f2, f3, f4, nArray, nArray2, nArray3, nArray4);
   }

   static void z(StbTrueTypeCharStringContext stbTrueTypeCharStringContext, float f, float f2) {
      stbTrueTypeCharStringContext.C += f;
      stbTrueTypeCharStringContext.o += f2;
      A(stbTrueTypeCharStringContext, 2, (int)stbTrueTypeCharStringContext.C, (int)stbTrueTypeCharStringContext.o, 0, 0, 0, 0);
   }

   static float S(float f, float f2) {
      return f * f2 / 2.0F;
   }

   public static int T(StbTrueTypeFontInfo stbTrueTypeFontInfo, int n) {
      byte[] byArray = stbTrueTypeFontInfo.S;
      int n2 = stbTrueTypeFontInfo.Y;
      int n3 = x(byArray, n2 + 0);
      if (n3 == 0) {
         int n4 = x(byArray, n2 + 2);
         return n < n4 - 6 ? N(byArray, n2 + 6 + n) : 0;
      } else if (n3 == 6) {
         long l = x(byArray, n2 + 6);
         long l2 = x(byArray, n2 + 8);
         return n >= l && n < l + l2 ? x(byArray, n2 + 10 + (n - (int)l) * 2) : 0;
      } else if (n3 == 2) {
         if (!G) {
            throw new AssertionError();
         } else {
            return 0;
         }
      } else if (n3 == 4) {
         int n7 = x(byArray, n2 + 6) >> 1;
         int n8 = x(byArray, n2 + 8) >> 1;
         int n9 = x(byArray, n2 + 10);
         int n10 = x(byArray, n2 + 12) >> 1;
         int n6;
         int n11 = n6 = n2 + 14;
         if (n > 65535) {
            return 0;
         } else {
            if (n >= x(byArray, n11 + n10 * 2)) {
               n11 += n10 * 2;
            }

            for (n11 -= 2; n9 != 0; n9--) {
               int n5 = x(byArray, n11 + (n8 >>= 1) * 2);
               if (n > n5) {
                  n11 += n8 * 2;
               }
            }

            n11 += 2;
            int n12 = n11 - n6 >> 1;
            int n13 = x(byArray, n2 + 14 + n7 * 2 + 2 + 2 * n12);
            int n14 = x(byArray, n6 + 2 * n12);
            if (n >= n13 && n <= n14) {
               int n5 = x(byArray, n2 + 14 + n7 * 6 + 2 + 2 * n12);
               return n5 == 0
                  ? n + E$src$S$1b9gd7y(byArray, n2 + 14 + n7 * 4 + 2 + 2 * n12) & 65535
                  : x(byArray, n5 + (n - n13) * 2 + n2 + 14 + n7 * 6 + 2 + 2 * n12);
            } else {
               return 0;
            }
         }
      } else if (n3 != 12 && n3 != 13) {
         if (!G) {
            throw new AssertionError();
         } else {
            return 0;
         }
      } else {
         long l = c$src$J$1nktx4p(byArray, n2 + 12);
         int n15 = 0;
         int n16 = (int)l;

         while (n15 < n16) {
            int n17 = n15 + (n16 - n15 >> 1);
            long l3 = c$src$J$1nktx4p(byArray, n2 + 16 + n17 * 12);
            long l4 = c$src$J$1nktx4p(byArray, n2 + 16 + n17 * 12 + 4);
            if (n < l3) {
               n16 = n17;
            } else {
               if (n <= l4) {
                  long l5 = c$src$J$1nktx4p(byArray, n2 + 16 + n17 * 12 + 8);
                  if (n3 == 12) {
                     return (int)(l5 + n - l3);
                  }

                  return (int)l5;
               }

               n15 = n17 + 1;
            }
         }

         return 0;
      }
   }

   public static void H(
      StbTrueTypeFontInfo stbTrueTypeFontInfo,
      byte[] byArray,
      int n,
      int n2,
      int n3,
      int n4,
      float f,
      float f2,
      float f3,
      float f4,
      int n5,
      int n6,
      float[] fArray,
      float[] fArray2,
      int n7
   ) {
      j(stbTrueTypeFontInfo, byArray, n, n2 - (n5 - 1), n3 - (n6 - 1), n4, f, f2, f3, f4, n7);
      if (n5 > 1) {
         H(byArray, n, n2, n3, n4, n5);
      }

      if (n6 > 1) {
         p(byArray, n, n2, n3, n4, n6);
      }

      fArray[0] = U(n5);
      fArray2[0] = U(n6);
   }

   static void N(StbTrueTypeEdge[] stbTrueTypeEdgeArray, int n, int n2) {
      for (int i = 1; i < n2; i++) {
         StbTrueTypeEdge stbTrueTypeEdge = stbTrueTypeEdgeArray[n + i];

         int n3;
         for (n3 = i; n3 > 0; n3--) {
            StbTrueTypeEdge stbTrueTypeEdge2 = stbTrueTypeEdgeArray[n + n3 - 1];
            boolean bl = stbTrueTypeEdge.X < stbTrueTypeEdge2.X;
            if (!bl) {
               break;
            }

            stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdgeArray[n + n3 - 1];
         }

         if (i != n3) {
            stbTrueTypeEdgeArray[n + n3] = stbTrueTypeEdge;
         }
      }
   }

   static StbByteBufferView C(StbByteBufferView frame, int n) {
      i(frame, 0);

      while (frame.h < frame.y) {
         int n2 = frame.h;

         while (K(frame) >= 28) {
            M(frame);
         }

         int n3 = frame.h;
         int n4 = Q(frame);
         if (n4 == 12) {
            n4 = Q(frame) | 256;
         }

         if (n4 == n) {
            return E(frame, n2, n3 - n2);
         }
      }

      return E(frame, 0, 0);
   }

   static long g(StbByteBufferView frame) {
      return u(frame, 4);
   }

   public static float V(StbTrueTypeFontInfo stbTrueTypeFontInfo, float f) {
      int n = E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.w + 4) - E$src$S$1b9gd7y(stbTrueTypeFontInfo.S, stbTrueTypeFontInfo.w + 6);
      return f / n;
   }

   static void b(
      StbTrueTypeBitmap stbTrueTypeBitmap,
      StbTrueTypePoint[] stbTrueTypePointArray,
      int[] nArray,
      int n,
      float f,
      float f2,
      float f3,
      float f4,
      int n2,
      int n3,
      int n4
   ) {
      float f5 = n4 != 0 ? -f2 : f2;
      int n6 = 1;
      int n7 = 0;

      for (int n5 = 0; n5 < n; n5++) {
         n7 += nArray[n5];
      }

      StbTrueTypeEdge[] stbTrueTypeEdgeArray = new StbTrueTypeEdge[n7 + 1];

      for (int var22 = 0; var22 <= n7; var22++) {
         stbTrueTypeEdgeArray[var22] = new StbTrueTypeEdge();
      }

      n7 = 0;
      int n8 = 0;

      for (int var23 = 0; var23 < n; var23++) {
         int n9 = n8;
         n8 += nArray[var23];
         int n10 = nArray[var23] - 1;

         for (int n11 = 0; n11 < nArray[var23]; n10 = n11++) {
            int n12 = n11;
            int n13 = n10;
            if (stbTrueTypePointArray[n9 + n10].E != stbTrueTypePointArray[n9 + n11].E) {
               stbTrueTypeEdgeArray[n7].W = 0;
               if (n4 != 0
                  ? stbTrueTypePointArray[n9 + n10].E > stbTrueTypePointArray[n9 + n11].E
                  : stbTrueTypePointArray[n9 + n10].E < stbTrueTypePointArray[n9 + n11].E) {
                  stbTrueTypeEdgeArray[n7].W = 1;
                  n12 = n10;
                  n13 = n11;
               }

               stbTrueTypeEdgeArray[n7].d = stbTrueTypePointArray[n9 + n12].M * f + f3;
               stbTrueTypeEdgeArray[n7].X = (stbTrueTypePointArray[n9 + n12].E * f5 + f4) * n6;
               stbTrueTypeEdgeArray[n7].i = stbTrueTypePointArray[n9 + n13].M * f + f3;
               stbTrueTypeEdgeArray[n7].u = (stbTrueTypePointArray[n9 + n13].E * f5 + f4) * n6;
               n7++;
            }
         }
      }

      s(stbTrueTypeEdgeArray, n7);
      b(stbTrueTypeBitmap, stbTrueTypeEdgeArray, n7, n6, n2, n3);
   }

   public static int G(
      StbTrueTypePackContext stbTrueTypePackContext, byte[] byArray, int n, float f, int n2, int n3, StbTrueTypePackedChar[] stbTrueTypePackedCharArray
   ) {
      StbTrueTypePackRange stbTrueTypePackRange = new StbTrueTypePackRange();
      stbTrueTypePackRange.R = n2;
      stbTrueTypePackRange.N = null;
      stbTrueTypePackRange.b = n3;
      stbTrueTypePackRange.M = stbTrueTypePackedCharArray;
      stbTrueTypePackRange.E = f;
      return c(stbTrueTypePackContext, byArray, n, new StbTrueTypePackRange[]{stbTrueTypePackRange}, 1);
   }

   public static byte[] A(
      StbTrueTypeFontInfo stbTrueTypeFontInfo, float f, int n, int n2, int n3, float f2, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4
   ) {
      float f11 = f;
      int[] nArray5 = new int[1];
      int[] nArray6 = new int[1];
      int[] nArray7 = new int[1];
      int[] nArray8 = new int[1];
      if (f == 0.0F) {
         return null;
      } else {
         G(stbTrueTypeFontInfo, n, f, f, 0.0F, 0.0F, nArray5, nArray6, nArray7, nArray8);
         if (nArray5[0] != nArray7[0] && nArray6[0] != nArray8[0]) {
            nArray5[0] -= n2;
            nArray6[0] -= n2;
            nArray7[0] += n2;
            nArray8[0] += n2;
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

            float f12 = -f;
            float f13 = 9.765625E-4F;
            float f14 = 9.536743E-7F;
            StbTrueTypeVertex[] stbTrueTypeVertexArray = m(stbTrueTypeFontInfo, n);
            int n6 = stbTrueTypeVertexArray != null ? stbTrueTypeVertexArray.length : 0;
            byte[] byArray = new byte[n4 * n5];
            float[] fArray = new float[n6];
            int n7 = 0;

            for (int n8 = n6 - 1; n7 < n6; n8 = n7++) {
               float f4;
               float f5;
               float f6;
               float f7;
               float f8;
               float f9;
               float f10;
               float f15;
               fArray[n7] = stbTrueTypeVertexArray[n7].O == 2
                  ? (
                     (
                              f10 = (float)Math.sqrt(
                                 ((f9 = stbTrueTypeVertexArray[n8].f * f11) - (f8 = stbTrueTypeVertexArray[n7].f * f11)) * (f9 - f8)
                                    + ((f7 = stbTrueTypeVertexArray[n8].H * f12) - (f6 = stbTrueTypeVertexArray[n7].H * f12)) * (f7 - f6)
                              )
                           )
                           < 9.765625E-4F
                        ? 0.0F
                        : 1.0F / f10
                  )
                  : (
                     stbTrueTypeVertexArray[n7].O == 3
                        ? (
                           (
                                    f5 = (
                                             f15 = stbTrueTypeVertexArray[n7].f * f11
                                                - 2.0F * (stbTrueTypeVertexArray[n7].h * f11)
                                                + stbTrueTypeVertexArray[n8].f * f11
                                          )
                                          * f15
                                       + (
                                             f4 = stbTrueTypeVertexArray[n7].H * f12
                                                - 2.0F * (stbTrueTypeVertexArray[n7].D * f12)
                                                + stbTrueTypeVertexArray[n8].H * f12
                                          )
                                          * f4
                                 )
                                 >= 9.536743E-7F
                              ? 1.0F / f5
                              : 0.0F
                        )
                        : 0.0F
                  );
            }

            for (int i = nArray6[0]; i < nArray8[0]; i++) {
               for (int j = nArray5[0]; j < nArray7[0]; j++) {
                  float f6 = 999999.0F;
                  float f9 = j + 0.5F;
                  float f7 = i + 0.5F;
                  float f10 = f9 / f11;
                  float f3 = f7 / f12;
                  int n9 = i(f10, f3, n6, stbTrueTypeVertexArray);

                  for (int var70 = 0; var70 < n6; var70++) {
                     float f4 = stbTrueTypeVertexArray[var70].f * f11;
                     float f5 = stbTrueTypeVertexArray[var70].H * f12;
                     if (stbTrueTypeVertexArray[var70].O == 2 && fArray[var70] != 0.0F) {
                        float f30 = stbTrueTypeVertexArray[var70 - 1].f * f11;
                        float f29 = stbTrueTypeVertexArray[var70 - 1].H * f12;
                        float f28 = (f4 - f9) * (f4 - f9) + (f5 - f7) * (f5 - f7);
                        if (f28 < f6 * f6) {
                           f6 = (float)Math.sqrt(f28);
                        }

                        float f23;
                        float f24;
                        float f25;
                        float f26;
                        float f27;
                        float f31;
                        if ((f27 = Math.abs((f30 - f4) * (f5 - f7) - (f29 - f5) * (f4 - f9)) * fArray[var70]) < f6
                           && (f31 = -((f26 = f4 - f9) * (f25 = f30 - f4) + (f24 = f5 - f7) * (f23 = f29 - f5)) / (f25 * f25 + f23 * f23)) >= 0.0F
                           && f31 <= 1.0F) {
                           f6 = f27;
                        }
                     } else if (stbTrueTypeVertexArray[var70].O == 3) {
                        float f30x = stbTrueTypeVertexArray[var70 - 1].f * f11;
                        float f29x = stbTrueTypeVertexArray[var70 - 1].H * f12;
                        float f27 = stbTrueTypeVertexArray[var70].h * f11;
                        float f28x = stbTrueTypeVertexArray[var70].D * f12;
                        float f25 = Math.min(Math.min(f4, f27), f30x);
                        float f23 = Math.min(Math.min(f5, f28x), f29x);
                        float f26 = Math.max(Math.max(f4, f27), f30x);
                        float f24 = Math.max(Math.max(f5, f28x), f29x);
                        if (f9 > f25 - f6 && f9 < f26 + f6 && f7 > f23 - f6 && f7 < f24 + f6) {
                           int n10 = 0;
                           float f32 = f27 - f4;
                           float f33 = f28x - f5;
                           float f34 = f4 - 2.0F * f27 + f30x;
                           float f35 = f5 - 2.0F * f28x + f29x;
                           float f36 = f4 - f9;
                           float f37 = f5 - f7;
                           float[] fArray2 = new float[]{0.0F, 0.0F, 0.0F};
                           float f38 = fArray[var70];
                           if (f38 == 0.0) {
                              float f22 = 3.0F * (f32 * f34 + f33 * f35);
                              float f21 = 2.0F * (f32 * f32 + f33 * f33) + (f36 * f34 + f37 * f35);
                              float f20 = f36 * f32 + f37 * f33;
                              if (Math.abs(f22) < 9.536743E-7F) {
                                 if (Math.abs(f21) >= 9.536743E-7F) {
                                    fArray2[n10++] = -f20 / f21;
                                 }
                              } else {
                                 float f39 = f21 * f21 - 4.0F * f22 * f20;
                                 if (f39 < 0.0F) {
                                    n10 = 0;
                                 } else {
                                    float f40 = (float)Math.sqrt(f39);
                                    fArray2[0] = (-f21 - f40) / (2.0F * f22);
                                    fArray2[1] = (-f21 + f40) / (2.0F * f22);
                                    n10 = 2;
                                 }
                              }
                           } else {
                              float f22 = 3.0F * (f32 * f34 + f33 * f35) * f38;
                              float f21 = (2.0F * (f32 * f32 + f33 * f33) + (f36 * f34 + f37 * f35)) * f38;
                              float f20 = (f36 * f32 + f37 * f33) * f38;
                              n10 = X(f22, f21, f20, fArray2);
                           }

                           float f41 = (f4 - f9) * (f4 - f9) + (f5 - f7) * (f5 - f7);
                           if (f41 < f6 * f6) {
                              f6 = (float)Math.sqrt(f41);
                           }

                           float f16;
                           float f17;
                           float f18;
                           float f19;
                           if (n10 >= 1
                              && fArray2[0] >= 0.0F
                              && fArray2[0] <= 1.0F
                              && (
                                    f41 = ((f19 = (f18 = 1.0F - (f17 = fArray2[0])) * f18 * f4 + 2.0F * f17 * f18 * f27 + f17 * f17 * f30x) - f9) * (f19 - f9)
                                       + ((f16 = f18 * f18 * f5 + 2.0F * f17 * f18 * f28x + f17 * f17 * f29x) - f7) * (f16 - f7)
                                 )
                                 < f6 * f6) {
                              f6 = (float)Math.sqrt(f41);
                           }

                           if (n10 >= 2
                              && fArray2[1] >= 0.0F
                              && fArray2[1] <= 1.0F
                              && (
                                    f41 = ((f19 = (f18 = 1.0F - (f17 = fArray2[1])) * f18 * f4 + 2.0F * f17 * f18 * f27 + f17 * f17 * f30x) - f9) * (f19 - f9)
                                       + ((f16 = f18 * f18 * f5 + 2.0F * f17 * f18 * f28x + f17 * f17 * f29x) - f7) * (f16 - f7)
                                 )
                                 < f6 * f6) {
                              f6 = (float)Math.sqrt(f41);
                           }

                           if (n10 >= 3
                              && fArray2[2] >= 0.0F
                              && fArray2[2] <= 1.0F
                              && (
                                    f41 = ((f19 = (f18 = 1.0F - (f17 = fArray2[2])) * f18 * f4 + 2.0F * f17 * f18 * f27 + f17 * f17 * f30x) - f9) * (f19 - f9)
                                       + ((f16 = f18 * f18 * f5 + 2.0F * f17 * f18 * f28x + f17 * f17 * f29x) - f7) * (f16 - f7)
                                 )
                                 < f6 * f6) {
                              f6 = (float)Math.sqrt(f41);
                           }
                        }
                     }
                  }

                  if (n9 == 0) {
                     f6 = -f6;
                  }

                  float f8;
                  if ((f8 = n3 + f2 * f6) < 0.0F) {
                     f8 = 0.0F;
                  } else if (f8 > 255.0F) {
                     f8 = 255.0F;
                  }

                  byArray[(i - nArray6[0]) * n4 + (j - nArray5[0])] = (byte)f8;
               }
            }

            return byArray;
         } else {
            return null;
         }
      }
   }

   static float w(float f) {
      return f < 0.0F ? -((float)Math.pow(-f, 0.33333334F)) : (float)Math.pow(f, 0.33333334F);
   }

   static int u(byte[] byArray, int n) {
      return byArray[n];
   }

   static boolean f(byte[] byArray, int n, String string) {
      return d(byArray, n, string.charAt(0), string.charAt(1), string.charAt(2), string.charAt(3));
   }

   public static int p(byte[] byArray) {
      return s(byArray);
   }

   static {
      boolean bl = SmoothFontRasterState.class.desiredAssertionStatus();
      boolean bl2 = true;
   }
}
