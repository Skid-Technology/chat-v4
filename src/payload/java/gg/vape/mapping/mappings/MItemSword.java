package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItemSword extends Mapping {
   private MappingMethod Q;
   private MappingField E;
   private MappingMethod T;
   private MappingField d;
   private MappingField V;

   public static float T(MItemSword mItemSword, Object object) {
      return mItemSword.r(object);
   }

   public static Object x(MItemSword mItemSword, Object object) {
      return mItemSword.B(object);
   }

   private float U(Object object) {
      return ForgeVersion.MC_1_20_6.d() ? this.Q.invokeFloat(object) : this.d.getFloat(object);
   }

   private Object B(Object object) {
      return this.E.getObject(object);
   }

   public static float X(MItemSword mItemSword, Object object) {
      return mItemSword.l(object);
   }

   public static float c(MItemSword mItemSword, Object object) {
      return mItemSword.U(object);
   }

   private float r(Object object) {
      return this.T.invokeFloat(object);
   }

   private float l(Object object) {
      return this.V.getFloat(object);
   }

   public MItemSword() {
      this(MItemStack.f());
   }

   private MItemSword(int n) {
      super(MappedClasses.Ye);
      if (ForgeVersion.MC_1_16_5.d()) {
         if (ForgeVersion.MC_1_21_4.v()) {
            Class clazz = MappedClasses.lh;
            boolean bl = true;
            String string = "tier";
            Class clazz2 = MappedClasses.uT;
            this.E = this.registerInstanceFieldForOwner(clazz2, string, bl, clazz);
         }

         if (ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_1_21_4.v()) {
               Class[] classArray = new Class[0];
               Class<Float> clazz = float.class;
               boolean bl = true;
               String string = "getSpeed";
               Class clazz3 = MappedClasses.lh;
               this.Q = this.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
               Class[] classArray2 = new Class[0];
               Class<Float> clazz4 = float.class;
               boolean bl2 = true;
               String string2 = "getAttackDamageBonus";
               Class clazz5 = MappedClasses.lh;
               this.T = this.registerInstanceMethodForOwner(clazz5, string2, bl2, clazz4, classArray2);
            }
         } else {
            Class[] classArray = new Class[0];
            Class<Float> clazz = float.class;
            boolean bl = true;
            String string = "getAttackDamage";
            this.T = this.Y(string, bl, clazz, classArray);
            Class<Float> clazz6 = float.class;
            boolean bl3 = true;
            String string3 = "efficiency";
            this.d = this.J(string3, bl3, clazz6);
         }
      } else {
         Class clazz = MappedClasses.F5;
         boolean bl = true;
         String string = "toolMaterial";
         this.E = this.J(string, bl, clazz);
         Class<Float> clazz7 = float.class;
         boolean bl4 = true;
         String string4 = ForgeVersion.c() >= 23 ? "attackDamage" : "damageVsEntity";
         this.V = this.J(string4, bl4, clazz7);
         Class<Float> clazz8 = float.class;
         boolean bl5 = true;
         String string5 = ForgeVersion.c() >= 23 ? "efficiency" : "efficiencyOnProperMaterial";
         this.d = this.J(string5, bl5, clazz8);
      }
   }
}
