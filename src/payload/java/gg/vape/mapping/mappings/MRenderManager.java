package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderManager extends Mapping {
   public MappingMethod r;
   private MappingField Y;
   private MappingField X;
   private MappingField D;
   private MappingField C;
   private MappingField p;
   private static String[] m;
   public MappingMethod W;
   private MappingField k;
   private MappingField L;
   public MappingMethod j;
   public MappingMethod x;
   private final MappingMethod l;
   private MappingField d;

   public static Object q(MRenderManager mRenderManager, Object object) {
      return mRenderManager.x(object);
   }

   private Object K(Object object, Object object2) {
      return this.l.invokeObject(object, object2);
   }

   public static double t(MRenderManager mRenderManager, Object object) {
      return mRenderManager.r(object);
   }

   private Object O(Object object) {
      return this.X.getObject(object);
   }

   private Object p() {
      return this.d.getObject(null);
   }

   private Object x(Object object) {
      return this.D.getObject(object);
   }

   public MRenderManager() {
      this(O());
   }

   private MRenderManager(String[] stringArray) {
      super(MappedClasses.Dc);
      if (ForgeVersion.MC_1_21_0.d()) {
         if (ForgeVersion.MC_1_21_4.d()) {
            if (ForgeVersion.MC_1_21_10.d()) {
               Class[] classArray = new Class[]{MappedClasses.zc, MappedClasses.qh, double.class, double.class, double.class};
               Class<Boolean> clazz = boolean.class;
               boolean bl = true;
               String string = "shouldRender";
               this.j = this.Y(string, bl, clazz, classArray);
            } else {
               Class[] classArray = new Class[]{
                  MappedClasses.zc, double.class, double.class, double.class, float.class, MappedClasses.DQ, MappedClasses.ZK, int.class, MappedClasses.VQ
               };
               Class<Void> clazz = void.class;
               boolean bl = true;
               String string = "render";
               this.x = this.Y(string, bl, clazz, classArray);
               Class[] classArray2 = new Class[]{
                  MappedClasses.qX, double.class, double.class, double.class, MappedClasses.DQ, MappedClasses.ZK, int.class, MappedClasses.VQ
               };
               Class<Void> clazz2 = void.class;
               boolean bl2 = true;
               String string2 = "render";
               this.r = this.Y(string2, bl2, clazz2, classArray2);
            }
         } else {
            Class[] classArray = new Class[]{
               MappedClasses.zc, double.class, double.class, double.class, float.class, float.class, MappedClasses.DQ, MappedClasses.ZK, int.class
            };
            Class<Void> clazz = void.class;
            boolean bl = true;
            String string = "render";
            this.x = this.Y(string, bl, clazz, classArray);
         }
      }

      if (ForgeVersion.MC_1_7_10.L()) {
         Class clazz = MappedClasses.Dc;
         boolean bl = true;
         String string = "instance";
         this.d = this.registerStaticField(string, bl, clazz);
      }

      if (ForgeVersion.MC_1_8_9.L()) {
         if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            Class[] classArray = new Class[]{MappedClasses.zc, double.class, double.class, double.class, float.class, float.class, boolean.class};
            Class<Boolean> clazz = boolean.class;
            boolean bl = true;
            String string = "doRenderEntity";
            this.W = this.Y(string, bl, clazz, classArray);
         } else {
            Class[] classArray = new Class[]{MappedClasses.zc, double.class, double.class, double.class, float.class, float.class, boolean.class};
            Class<Boolean> clazz = boolean.class;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_147939_a";
            this.W = this.Y(string, bl, clazz, classArray);
         }
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         Class clazz = MappedClasses.lt;
         boolean bl = true;
         String string = "info";
         this.X = this.J(string, bl, clazz);
         Class[] classArray = new Class[]{MappedClasses.zc};
         Class clazz3 = MappedClasses.VQ;
         boolean bl3 = true;
         String string3 = "getRenderer";
         this.l = this.Y(string3, bl3, clazz3, classArray);
         if (ForgeVersion.MC_1_21_10.v()) {
            Class clazz4 = MappedClasses.qI;
            boolean bl4 = true;
            String string4 = "cameraOrientation";
            this.D = this.J(string4, bl4, clazz4);
            Class[] classArray3 = new Class[]{
               MappedClasses.zc, double.class, double.class, double.class, float.class, float.class, MappedClasses.DQ, MappedClasses.ZK, int.class
            };
            Class<Void> clazz5 = void.class;
            boolean bl5 = true;
            String string5 = "renderEntityStatic";
            this.W = this.Y(string5, bl5, clazz5, classArray3);
         }
      } else {
         Class<Float> clazz = float.class;
         boolean bl = true;
         String string = "playerViewX";
         this.p = this.J(string, bl, clazz);
         Class<Float> clazz6 = float.class;
         boolean bl6 = true;
         String string6 = "playerViewY";
         this.k = this.J(string6, bl6, clazz6);
         Class<Double> clazz7 = double.class;
         boolean bl7 = true;
         String string7 = "viewerPosX";
         this.Y = this.J(string7, bl7, clazz7);
         Class<Double> clazz8 = double.class;
         boolean bl8 = true;
         String string8 = "viewerPosY";
         this.L = this.J(string8, bl8, clazz8);
         Class<Double> clazz9 = double.class;
         boolean bl9 = true;
         String string9 = "viewerPosZ";
         this.C = this.J(string9, bl9, clazz9);
         Class[] classArray = new Class[]{MappedClasses.zc};
         Class clazz10 = MappedClasses.VQ;
         boolean bl10 = true;
         String string10 = "getEntityRenderObject";
         this.l = this.Y(string10, bl10, clazz10, classArray);
      }

      if (GuiComponent.getLegacyComponentState() == null) {
         o(new String[5]);
      }
   }

   private float d(Object object) {
      return this.k.getFloat(object);
   }

   public static float o(MRenderManager mRenderManager, Object object) {
      return mRenderManager.d(object);
   }

   public static float r(MRenderManager mRenderManager, Object object) {
      return mRenderManager.f(object);
   }

   public static double M(MRenderManager mRenderManager, Object object) {
      return mRenderManager.t(object);
   }

   private double r(Object object) {
      return this.C.getDouble(object);
   }

   public static double N(MRenderManager mRenderManager, Object object) {
      return mRenderManager.F(object);
   }

   private float f(Object object) {
      return this.p.getFloat(object);
   }

   private double t(Object object) {
      return this.Y.getDouble(object);
   }

   public static Object e(MRenderManager mRenderManager, Object object) {
      return mRenderManager.O(object);
   }

   public static String[] O() {
      return m;
   }

   private double F(Object object) {
      return this.L.getDouble(object);
   }

   public static Object O(MRenderManager mRenderManager, Object object, Object object2) {
      return mRenderManager.K(object, object2);
   }

   public static void o(String[] stringArray) {
      m = stringArray;
   }

   public static Object T(MRenderManager mRenderManager) {
      return mRenderManager.p();
   }

   static {
      o(null);
   }
}
