package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItem_ToolMaterial extends Mapping {
   private final MappingField U;
   private final MappingField n;
   private final MappingField q;
   private final MappingField r;
   private final MappingField h;

   public static float j(MItem_ToolMaterial mItem_ToolMaterial, Object object) {
      return mItem_ToolMaterial.a(object);
   }

   public static Object U(MItem_ToolMaterial mItem_ToolMaterial) {
      return mItem_ToolMaterial.D();
   }

   public MItem_ToolMaterial() {
      this(MItemStack.M());
   }

   private MItem_ToolMaterial(int n) {
      super(MappedClasses.F5);
      if (n != 0) {
         Class<Float> clazz = float.class;
         boolean bl = true;
         String string = ForgeVersion.c() >= 23 ? "attackDamage" : "damageVsEntity";
         this.r = this.J(string, bl, clazz);
         Class clazz2 = MappedClasses.F5;
         boolean bl2 = Wrapper.isNativeAvailable;
         String string2 = "WOOD";
         this.h = this.registerStaticField(string2, bl2, clazz2);
         Class clazz3 = MappedClasses.F5;
         boolean bl3 = Wrapper.isNativeAvailable;
         String string3 = "STONE";
         this.U = this.registerStaticField(string3, bl3, clazz3);
         Class clazz4 = MappedClasses.F5;
         boolean bl4 = Wrapper.isNativeAvailable;
         String string4 = "IRON";
         this.n = this.registerStaticField(string4, bl4, clazz4);
         Class clazz5 = MappedClasses.F5;
         boolean bl5 = Wrapper.isNativeAvailable;
         String string5 = "GOLD";
         this.q = this.registerStaticField(string5, bl5, clazz5);
      } else {
         if (ForgeVersion.MC_1_16_5.d()) {
            Class<Float> clazz = float.class;
            boolean bl = true;
            String string = "attackDamage";
            this.r = this.J(string, bl, clazz);
         } else {
            Class<Float> clazz = float.class;
            boolean bl = true;
            String string = ForgeVersion.c() >= 23 ? "attackDamage" : "damageVsEntity";
            this.r = this.J(string, bl, clazz);
         }

         Class clazz = MappedClasses.F5;
         boolean bl = Wrapper.isNativeAvailable;
         String string = "WOOD";
         this.h = this.registerStaticField(string, bl, clazz);
         Class clazz6 = MappedClasses.F5;
         boolean bl6 = Wrapper.isNativeAvailable;
         String string6 = "STONE";
         this.U = this.registerStaticField(string6, bl6, clazz6);
         Class clazz7 = MappedClasses.F5;
         boolean bl7 = Wrapper.isNativeAvailable;
         String string7 = "IRON";
         this.n = this.registerStaticField(string7, bl7, clazz7);
         Class clazz8 = MappedClasses.F5;
         boolean bl8 = Wrapper.isNativeAvailable;
         String string8 = "GOLD";
         this.q = this.registerStaticField(string8, bl8, clazz8);
      }
   }

   private Object C() {
      return this.h.getObject(null);
   }

   private Object p() {
      return this.q.getObject(null);
   }

   public static Object M(MItem_ToolMaterial mItem_ToolMaterial) {
      return mItem_ToolMaterial.C();
   }

   private float a(Object object) {
      return this.r.getFloat(object);
   }

   public static Object r(MItem_ToolMaterial mItem_ToolMaterial) {
      return mItem_ToolMaterial.S();
   }

   public static Object H(MItem_ToolMaterial mItem_ToolMaterial) {
      return mItem_ToolMaterial.p();
   }

   private Object D() {
      return this.n.getObject(null);
   }

   private Object S() {
      return this.U.getObject(null);
   }
}
