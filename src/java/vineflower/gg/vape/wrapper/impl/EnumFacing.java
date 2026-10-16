package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnumFacing;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.Wrapper;

public class EnumFacing extends Wrapper {
   private Integer C;
   public static final int o;
   private static EnumFacing f;
   private static EnumFacing[] i;
   private static EnumFacing s;
   public static final int e = 0;
   public static final int t;
   private EnumFacing v;
   private static EnumFacing K;
   private static EnumFacing[] w;
   private static EnumFacing A;
   public static final int W = 1;
   private Integer S;
   private static EnumFacing H;
   private static EnumFacing P;
   public static final int X;
   public static final int q;
   private Vec3i directionVector;

   public static EnumFacing[] t() {
      if (i == null) {
         Object[] objectArray = ForgeVersion.MC_1_7_10.Y() ? vapeInstance.getMappings().O.H() : vapeInstance.getMappings().O.U();
         EnumFacing[] enumFacingArray = new EnumFacing[6];

         for (Object object : objectArray) {
            EnumFacing enumFacing = new EnumFacing(object);
            enumFacingArray[enumFacing.Y()] = enumFacing;
         }

         i = enumFacingArray;
      }

      return i;
   }

   public static EnumFacing[] c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft() {
      if (w == null) {
         EnumFacing[] enumFacingArray = new EnumFacing[4];
         Object[] objectArray = null;
         if (ForgeVersion.MC_1_7_10.Y() && ForgeVersion.MC_1_16_5.v()) {
            objectArray = vapeInstance.getMappings().O.A();
         }

         if (objectArray != null) {
            for (int i = 0; i < objectArray.length; i++) {
               enumFacingArray[i] = new EnumFacing(objectArray[i]);
            }
         } else {
            for (EnumFacing enumFacing : t()) {
               int n = enumFacing.c();
               if (n != -1) {
                  enumFacingArray[n] = enumFacing;
               }
            }
         }

         w = enumFacingArray;
      }

      return w;
   }

   public static EnumFacing p(double d) {
      return E(MathUtil.floor(d / 90.0 + 0.5) & 3);
   }

   public int o() {
      return this.getDirectionVector().getZ();
   }

   public int C() {
      return this.getDirectionVector().getY();
   }

   public static EnumFacing F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5() {
      if (f == null) {
         f = new EnumFacing(MEnumFacing.z(vapeInstance.getMappings().O).getObject(null));
      }

      return f;
   }

   public static EnumFacing E(int n) {
      EnumFacing[] enumFacingArray = c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft();
      return enumFacingArray[MathUtil.abs(n % enumFacingArray.length)];
   }

   private static IllegalStateException a(IllegalStateException illegalStateException) {
      return illegalStateException;
   }

   public static EnumFacing T(int n) {
      return t()[n];
   }

   public EnumFacing(Object object) {
      super(object);
   }

   public static EnumFacing M() {
      if (A == null) {
         A = new EnumFacing(MEnumFacing.R(vapeInstance.getMappings().O).getObject(null));
      }

      return A;
   }

   public static EnumFacing g$src$Lgg_vape_wrapper_impl_EnumFacing_$1ii8mzu() {
      if (H == null) {
         H = ForgeVersion.MC_1_7_10.Y()
            ? new EnumFacing(MEnumFacing.s(vapeInstance.getMappings().O).getObject(null))
            : new EnumFacing(MEnumFacing.n(vapeInstance.getMappings().O).getObject(null));
      }

      return H;
   }

   public static EnumFacing X() {
      if (P == null) {
         P = ForgeVersion.MC_1_7_10.Y()
            ? new EnumFacing(MEnumFacing.n(vapeInstance.getMappings().O).getObject(null))
            : new EnumFacing(MEnumFacing.s(vapeInstance.getMappings().O).getObject(null));
      }

      return P;
   }

   public Vec3i getDirectionVector() {
      if (this.directionVector == null) {
         if (ForgeVersion.MC_1_7_10.Y()) {
            this.directionVector = new Vec3i(vapeInstance.getMappings().O.e(this.I));
         } else {
            int n = vapeInstance.getMappings().O.V(this.I);
            int n2 = vapeInstance.getMappings().O.A(this.I);
            int n3 = vapeInstance.getMappings().O.s(this.I);
            this.directionVector = new InlineVec3i(n, n2, n3);
         }
      }

      return this.directionVector;
   }

   public static EnumFacing w() {
      if (K == null) {
         K = new EnumFacing(MEnumFacing.b(vapeInstance.getMappings().O).getObject(null));
      }

      return K;
   }

   public static EnumFacing B() {
      if (s == null) {
         s = new EnumFacing(MEnumFacing.E(vapeInstance.getMappings().O).getObject(null));
      }

      return s;
   }

   public EnumFacing getOpposite() {
      if (this.v == null) {
         int n = ForgeVersion.MC_1_7_10.Y() ? vapeInstance.getMappings().O.b(this.I) : vapeInstance.getMappings().O.v(this.I);
         this.v = T(n);
      }

      return this.v;
   }

   public static EnumFacing g(String string) {
      if (string != null) {
         String var1 = string.toLowerCase();
         switch (var1) {
            case "down":
               return B();
            case "up":
               return F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5();
            case "north":
               return w();
            case "south":
               return M();
            case "west":
               return X();
            case "east":
               return g$src$Lgg_vape_wrapper_impl_EnumFacing_$1ii8mzu();
         }
      }

      throw new IllegalStateException("Unmatched EnumFacing Name");
   }

   public String h() {
      return String.valueOf(vapeInstance.getMappings().O.c(this.I));
   }

   public int Y() {
      if (this.C == null) {
         this.C = ForgeVersion.MC_1_7_10.Y() ? vapeInstance.getMappings().O.p(this.I) : vapeInstance.getMappings().O.i(this.I);
      }

      return this.C;
   }

   public int g() {
      return this.getDirectionVector().getX();
   }

   public int c() {
      if (this.S == null) {
         if (ForgeVersion.MC_1_7_10.Y() && ForgeVersion.MC_1_16_5.v()) {
            this.S = vapeInstance.getMappings().O.S(this.I);
         } else {
            int n = -1;
            switch (this.Y()) {
               case 2:
                  n = 2;
                  break;
               case 3:
                  n = 0;
                  break;
               case 4:
                  n = 1;
                  break;
               case 5:
                  n = 3;
            }

            this.S = n;
         }
      }

      return this.S;
   }

   static {
      long[] lArray = new long[]{1773040238696857602L, 8069781883149352964L, 4717156233235136517L, -8897107917115752445L};
      t = (int)lArray[0];
      o = (int)lArray[3];
      X = (int)lArray[2];
      q = (int)lArray[1];
   }
}
