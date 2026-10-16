package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MLightTexture extends Mapping {
   public MappingMethod s;
   public MappingMethod k;
   public MappingMethod d;

   public static void B(MLightTexture mLightTexture, Object object, float f) {
      mLightTexture.k(object, f);
   }

   public static void z(MLightTexture mLightTexture, Object object) {
      mLightTexture.d(object);
   }

   private void F(Object object) {
      this.s.invokeVoidNoArgs(object);
   }

   private void k(Object object, float f) {
      this.d.invokeVoid(object, f);
   }

   public static void f(MLightTexture mLightTexture, Object object) {
      mLightTexture.F(object);
   }

   private void d(Object object) {
      this.k.invokeVoidNoArgs(object);
   }

   public MLightTexture() {
      this(MEntityRenderer.X());
   }

   private MLightTexture(int n) {
      super(MappedClasses.zH);
      if (n != 0) {
         Class[] classArray = new Class[0];
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "enableLightmap";
         this.k = this.Y(string, bl, clazz, classArray);
      } else {
         if (ForgeVersion.MC_1_21_11.v()) {
            Class[] classArray = new Class[]{float.class};
            Class<Void> clazz = void.class;
            boolean bl = true;
            String string = "updateLightmap";
            this.d = this.Y(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[0];
            Class<Void> clazz2 = void.class;
            boolean bl2 = true;
            String string2 = "disableLightmap";
            this.s = this.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[0];
            Class<Void> clazz3 = void.class;
            boolean bl3 = true;
            String string3 = "enableLightmap";
            this.k = this.Y(string3, bl3, clazz3, classArray3);
         }
      }
   }
}
