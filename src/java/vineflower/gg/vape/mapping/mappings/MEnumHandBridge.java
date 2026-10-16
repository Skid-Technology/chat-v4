package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MEnumHandBridge extends Mapping {
   private static final String b = "QUADS";
   private static int d;
   private MappingField s;

   public static Object c(MEnumHandBridge mEnumHandBridge) {
      return mEnumHandBridge.h();
   }

   private Object h() {
      return this.s.getObject(null);
   }

   public MEnumHandBridge() {
      this(V());
   }

   private MEnumHandBridge(int n) {
      super(MappedClasses.YF);
      Class clazz = MappedClasses.YF;
      boolean bl = true;
      String string = b;
      this.s = this.registerStaticField(string, bl, clazz);
      if (GuiComponent.getLegacyComponentState() == null) {
         int n2 = n + 1;
         q(n2);
      }
   }

   public static void q(int n) {
      d = n;
   }

   public static int s() {
      return d;
   }

   public static int V() {
      int n = s();
      return n == 0 ? 92 : 0;
   }

   static {
      q(0);
   }
}
