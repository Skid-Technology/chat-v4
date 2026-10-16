package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;

public class MVertexFormat extends MMinecraft {
   private static boolean eW;

   public static void x(boolean bl) {
      eW = bl;
   }

   public MVertexFormat() {
      boolean bl = C();
      Class clazz = MappedClasses.uQ;
      boolean bl2 = true;
      String string = "fontRenderer";
      this.y = this.J(string, bl2, clazz);
      Class clazz2 = MappedClasses.z8;
      boolean bl3 = true;
      String string2 = "itemRenderer";
      this.j = this.J(string2, bl3, clazz2);
   }

   public static boolean k() {
      boolean bl = C();
      return true;
   }

   public static boolean C() {
      return eW;
   }

   static {
      x(false);
   }
}
