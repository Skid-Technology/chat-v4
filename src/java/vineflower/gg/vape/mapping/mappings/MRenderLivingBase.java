package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.nio.FloatBuffer;
import java.util.List;

public class MRenderLivingBase extends Mapping {
   private MappingField g;
   public MappingMethod c;
   public MappingMethod O;
   private MappingField G;
   private MappingMethod Q;
   private MappingMethod h;
   public MappingMethod r;
   private MappingField f;
   private MappingField o;

   public static Object i(MRenderLivingBase mRenderLivingBase, Object object) {
      return mRenderLivingBase.I(object);
   }

   public static void O(MRenderLivingBase mRenderLivingBase, Object object, Object object2) {
      mRenderLivingBase.B(object, object2);
   }

   private Object I(Object object) {
      return this.G.getObject(object);
   }

   private void B(Object object, Object object2) {
      this.G.setObject(object, object2);
   }

   private Object d(Object object) {
      if (ForgeVersion.MC_1_16_5.d()) {
         return this.g.getObject(object);
      } else {
         return ForgeVersion.MC_1_7_10.L() ? this.o.getObject(object) : this.Q.invokeObject(object);
      }
   }

   public static FloatBuffer y(MRenderLivingBase mRenderLivingBase, Object object) {
      return mRenderLivingBase.D(object);
   }

   public static Object x(MRenderLivingBase mRenderLivingBase, Object object) {
      return mRenderLivingBase.d(object);
   }

   public MRenderLivingBase() {
      this(MRenderManager.O());
   }

   private MRenderLivingBase(String[] stringArray) {
      super(MappedClasses.Fq);
      if (ForgeVersion.MC_1_21_10.d()) {
         this.c = null;
         Class clazz = MappedClasses.V6;
         boolean bl = true;
         String string = "entityModel";
         this.g = this.J(string, bl, clazz);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         Class[] classArray = new Class[]{MappedClasses.zc, float.class, float.class, MappedClasses.DQ, MappedClasses.ZK, int.class};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "render";
         Class clazz2 = MappedClasses.VQ;
         this.c = this.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
         Class clazz3 = MappedClasses.V6;
         boolean bl2 = true;
         String string2 = "entityModel";
         this.g = this.J(string2, bl2, clazz3);
      } else if (ForgeVersion.MC_1_7_10.L()) {
         Class clazz = MappedClasses.M;
         boolean bl = true;
         String string = "mainModel";
         this.o = this.J(string, bl, clazz);
         Class[] classArray = new Class[]{MappedClasses.zm, double.class, double.class, double.class};
         Class<Void> clazz4 = void.class;
         boolean bl3 = true;
         String string3 = "passSpecialRender";
         this.r = this.Y(string3, bl3, clazz4, classArray);
      } else {
         if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            Class<List> clazz = List.class;
            boolean bl = true;
            String string = "layerRenderers";
            this.G = this.J(string, bl, clazz);
         } else {
            Class<List> clazz = List.class;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "field_177097_h";
            this.G = this.J(string, bl, clazz);
         }

         Class<FloatBuffer> clazz = FloatBuffer.class;
         boolean bl = true;
         String string = "brightnessBuffer";
         this.f = this.J(string, bl, clazz);
         Class[] classArray = new Class[0];
         Class clazz5 = MappedClasses.M;
         boolean bl4 = true;
         String string4 = "getMainModel";
         this.Q = this.Y(string4, bl4, clazz5, classArray);
         Class[] classArray2 = new Class[]{MappedClasses.zm, double.class, double.class, double.class};
         Class<Void> clazz6 = void.class;
         boolean bl5 = true;
         String string5 = "renderName";
         this.c = this.Y(string5, bl5, clazz6, classArray2);
         Class[] classArray3 = new Class[0];
         Class<Void> clazz7 = void.class;
         boolean bl6 = true;
         String string6 = "unsetBrightness";
         this.h = this.Y(string6, bl6, clazz7, classArray3);
         Class[] classArray4 = new Class[]{MappedClasses.zm, float.class, boolean.class};
         Class<Boolean> clazz8 = boolean.class;
         boolean bl7 = true;
         String string7 = "setBrightness";
         this.O = this.Y(string7, bl7, clazz8, classArray4);
      }
   }

   boolean A(Object object, Object object2, float f, boolean bl) {
      return this.O.invokeBoolean(object, object2, f, bl);
   }

   private void t(Object object) {
      this.h.invokeVoidNoArgs(object);
   }

   private FloatBuffer D(Object object) {
      return (FloatBuffer)this.f.getObject(object);
   }
}
