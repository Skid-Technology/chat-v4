package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MGlStateManagerTexGenState extends Mapping {
   private static final long e = 8525806735951169817L;
   private static boolean p;
   private MappingMethod T;
   private MappingMethod S;

   public int F(int n) {
      return this.S != null ? this.S.invokeInt(null, (int)e, n) : 0;
   }

   public static boolean z() {
      return p;
   }

   public static void d(boolean bl) {
      p = bl;
   }

   public static boolean F() {
      boolean bl = z();
      return !bl;
   }

   public MGlStateManagerTexGenState() {
      super(MappedClasses.ZC);
      Class[] classArray = new Class[]{int.class, int.class};
      Class<Void> clazz = void.class;
      boolean bl = false;
      String string = "glBindSampler";
      this.T = this.registerStaticMethod(string, bl, clazz, classArray);
      Class[] classArray2 = new Class[]{int.class, int.class};
      Class<Integer> clazz2 = int.class;
      boolean bl2 = false;
      String string2 = "glGetIntegeri";
      this.S = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
   }

   public void K(int n, int n2) {
      if (this.T != null) {
         this.T.invokeVoid(null, n, n2);
      }
   }

   static {
      d(true);
   }
}
