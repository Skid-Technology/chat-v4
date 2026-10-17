package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRender extends Mapping {
   private MappingMethod I;
   public MappingMethod p;
   public MappingMethod z;
   public MappingMethod w;

   private Object e(Object object, Object object2) {
      return this.w.invokeObject(object, object2);
   }

   public static Object s(MRender mRender, Object object, Object object2, float f) {
      return mRender.w(object, object2, f);
   }

   public static Object s(MRender mRender, Object object, Object object2) {
      return mRender.e(object, object2);
   }

   private void S(Object object, Object object2, double d, double d2, double d3, float f, float f2) {
      this.p.invokeVoid(object, object2, d, d2, d3, f, f2);
   }

   public static void E(MRender mRender, Object object, Object object2, double d, double d2, double d3, float f, float f2) {
      mRender.S(object, object2, d, d2, d3, f, f2);
   }

   public MRender() {
      this(MRenderManager.O());
   }

   private MRender(String[] stringArray) {
      super(MappedClasses.VQ);
      if (ForgeVersion.MC_1_21_6.d()) {
         Class[] classArray = new Class[]{MappedClasses.zc, float.class};
         Class clazz = MappedClasses.qX;
         boolean bl = true;
         String string = "createRenderState";
         this.I = this.Y(string, bl, clazz, classArray);
      }

      if (ForgeVersion.MC_1_21_10.d()) {
         Class[] classArray = new Class[]{MappedClasses.zc, MappedClasses.qX, float.class};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "extractRenderState";
         this.z = this.Y(string, bl, clazz, classArray);
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         if (ForgeVersion.MC_1_21_10.v()) {
            Class[] classArray = new Class[]{MappedClasses.zc, float.class, float.class, MappedClasses.DQ, MappedClasses.ZK, int.class};
            Class<Void> clazz = void.class;
            boolean bl = true;
            String string = "render";
            this.p = this.Y(string, bl, clazz, classArray);
         }
      } else {
         Class[] classArray = new Class[]{MappedClasses.zc, double.class, double.class, double.class, float.class, float.class};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "doRender";
         this.p = this.Y(string, bl, clazz, classArray);
      }

      if (ForgeVersion.MC_1_21_4.d()) {
         Class[] classArray = new Class[]{MappedClasses.qX};
         Class clazz = MappedClasses.zC;
         boolean bl = true;
         String string = "getTextureLocation";
         Class clazz2 = MappedClasses.Fq;
         this.w = this.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
      } else {
         Class[] classArray = new Class[]{MappedClasses.zc};
         Class clazz = MappedClasses.zC;
         boolean bl = true;
         String string = "getEntityTexture";
         this.w = this.Y(string, bl, clazz, classArray);
      }
   }

   private Object w(Object object, Object object2, float f) {
      return this.I.invokeObject(object, object2, f);
   }
}
