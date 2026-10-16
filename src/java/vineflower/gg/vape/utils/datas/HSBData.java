package gg.vape.utils.datas;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;

public class HSBData {
   private static int K;
   public AtomicBoolean Q;
   public int N;
   public int D;
   public Color U;
   public int F;
   public int O;

   public HSBData(int n, int n2, int n3, int n4, Color color) {
      this(n, n2, n3, n4, color, null);
   }

   public HSBData(int n, int n2, int n3, int n4, Color color, AtomicBoolean atomicBoolean) {
      this.F = n;
      this.O = n2;
      this.D = n3;
      this.N = n4;
      this.U = color;
      this.Q = atomicBoolean;
   }

   public boolean q() {
      return this.Q == null ? true : this.Q.get();
   }

   public int D() {
      return this.O;
   }

   public int U() {
      return this.F;
   }

   public int Y() {
      return this.D;
   }

   public static int H() {
      return K;
   }

   public static int m() {
      int n = H();
      return n == 0 ? 72 : 0;
   }

   public static void k(int n) {
      K = n;
   }

   static {
      if (m() != 0) {
         k(32);
      }
   }
}
