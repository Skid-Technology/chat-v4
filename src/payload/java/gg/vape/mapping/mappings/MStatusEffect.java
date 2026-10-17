package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MStatusEffect extends Mapping {
   private MappingMethod N;
   private MappingField B;
   private MappingMethod M;
   private MappingMethod O;
   private MappingMethod u;

   private int S(Object object) {
      return this.O.invokeInt(null, object);
   }

   private Object T(Object object) {
      return this.N.invokeObject(object);
   }

   public static Object g(MStatusEffect mStatusEffect, Object object) {
      return mStatusEffect.T(object);
   }

   private boolean q(Object object) {
      return this.u.invokeBoolean(object);
   }

   public Object O(int n) {
      return this.M.invokeObject(null, n);
   }

   public static boolean r(MStatusEffect mStatusEffect, Object object) {
      return mStatusEffect.q(object);
   }

   public MStatusEffect() {
      this(MPotion.K());
   }

   private MStatusEffect(int[] nArray) {
      super(MappedClasses.D3);
      if (nArray == null) {
         if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray = new Class[]{int.class};
            Class clazz = MappedClasses.D3;
            boolean bl = true;
            String string = "get";
            this.M = this.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.D3};
            Class<Integer> clazz2 = int.class;
            boolean bl2 = true;
            String string2 = "getId";
            this.O = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
         }

         Class[] classArray = new Class[0];
         Class<Boolean> clazz = boolean.class;
         boolean bl = true;
         String string = "isBeneficial";
         this.u = this.Y(string, bl, clazz, classArray);
         Class[] classArray3 = new Class[0];
         Class clazz3 = MappedClasses.Yr;
         boolean bl3 = true;
         String string3 = "getDisplayName";
         this.N = this.Y(string3, bl3, clazz3, classArray3);
      }
   }

   public static int a(MStatusEffect mStatusEffect, Object object) {
      return mStatusEffect.S(object);
   }
}
