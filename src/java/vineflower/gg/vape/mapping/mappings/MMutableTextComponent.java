package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMutableTextComponent extends Mapping {
   private static int[] W;
   public MappingMethod r;
   public MappingMethod e;

   public Object p(Object object, Object object2) {
      return this.r.invokeObject(object, object2);
   }

   public static int[] e() {
      return W;
   }

   public static void U(int[] nArray) {
      W = nArray;
   }

   public MMutableTextComponent() {
      this(e());
   }

   private MMutableTextComponent(int[] nArray) {
      super(MappedClasses.qQ);
      if (nArray != null) {
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{MappedClasses.Yr};
            Class clazz = MappedClasses.YO;
            boolean bl = true;
            String string = "append";
            this.r = this.Y(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.Va};
            Class clazz2 = MappedClasses.YO;
            boolean bl2 = true;
            String string2 = "setStyle";
            this.e = this.Y(string2, bl2, clazz2, classArray2);
         }
      } else {
         if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray = new Class[]{MappedClasses.Yr};
            Class clazz = MappedClasses.YO;
            boolean bl = true;
            String string = "append";
            this.r = this.Y(string, bl, clazz, classArray);
            Class[] classArray3 = new Class[]{MappedClasses.Va};
            Class clazz3 = MappedClasses.YO;
            boolean bl3 = true;
            String string3 = "setStyle";
            this.e = this.Y(string3, bl3, clazz3, classArray3);
         }
      }
   }

   public Object v(Object object, Object object2) {
      return this.e.invokeObject(object, object2);
   }

   static {
      U(null);
   }
}
