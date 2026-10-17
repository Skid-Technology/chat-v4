package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityRenderer extends Mapping {
   private MappingMethod g;
   private MappingField K;
   public final MappingMethod k;
   private MappingField q;
   public MappingMethod V;
   private MappingMethod m;
   private MappingField c;
   private MappingField fogRendererField;
   private MappingField f;
   public MappingMethod C;
   public MappingMethod H;
   public MappingMethod x;
   public MappingMethod extractRenderStateMethod;
   public final MappingMethod M;
   private MappingField N;
   private MappingField X;
   public MappingMethod i;
   private MappingField d;
   public MappingMethod L;
   private MappingField u;
   private static int s;
   public final MappingMethod J;
   private final MappingField j;
   private MappingField G;
   private MappingField Z;
   private MappingMethod l;
   public MappingMethod e;
   private MappingMethod p;
   private MappingField v;
   private final MappingField D;
   private MappingField P;
   private MappingField U;
   private MappingField z;
   private MappingField n;
   private MappingMethod Q;
   public MappingMethod W;
   private MappingField h;
   public MappingMethod B;
   public MappingMethod a;
   private MappingField A;
   private MappingMethod r;

   public static Object P(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.k(object);
   }

   private Object f(Object object) {
      return this.n.getObject(object);
   }

   private void u(Object object, Object object2) {
      if (this.C != null) {
         if (ForgeVersion.MC_26_2.d()) {
            this.C.invokeVoid(object, object2);
         } else if (ForgeVersion.MC_26_1.d()) {
            this.C.invokeVoid(object, object2, false);
         } else {
            this.C.invokeVoid(object, object2);
         }
      }
   }

   public static void N(MEntityRenderer mEntityRenderer, Object object, float f) {
      mEntityRenderer.W(object, f);
   }

   private float j(Object object) {
      return this.D == null ? 1.0F : this.D.getFloat(object);
   }

   private Object L(Object object, Object object2, float f, boolean bl) {
      if (ForgeVersion.MC_26_1.d()) {
         return this.O(object);
      } else if (ForgeVersion.MC_1_21_4.d()) {
         float f2 = this.m.invokeFloat(object, object2, f, bl);
         return this.r.invokeObject(object, f2);
      } else if (ForgeVersion.MC_1_20_6.d()) {
         double d = this.m.invokeDouble(object, object2, f, bl);
         return this.r.invokeObject(object, d);
      } else {
         return this.r.invokeObject(object, object2, f, bl);
      }
   }

   public static boolean c$src$Z$4ble0w(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.v(object);
   }

   public static Object Z(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.J(object);
   }

   public static void q(MEntityRenderer mEntityRenderer, Object object, Object object2) {
      mEntityRenderer.G(object, object2);
   }

   public static void z(MEntityRenderer mEntityRenderer, Object object, Object object2, float f) {
      mEntityRenderer.S(object, object2, f);
   }

   private void a(Object object, Object object2) {
      this.v.setObject(object, object2);
   }

   private void y(Object object, float f) {
      if (this.j != null) {
         this.j.setFloat(object, f);
      }
   }

   public static void i(MEntityRenderer mEntityRenderer, Object object, float f) {
      mEntityRenderer.M(object, f);
   }

   private float h(Object object) {
      return this.j == null ? 1.0F : this.j.getFloat(object);
   }

   public static float A(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.h(object);
   }

   public static Object t(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.N(object);
   }

   public static void M(MEntityRenderer mEntityRenderer, Object object, Object object2, float f) {
      mEntityRenderer.m(object, object2, f);
   }

   public static void f(MEntityRenderer mEntityRenderer, Object object, double d) {
      mEntityRenderer.S(object, d);
   }

   private void L(Object object, float f) {
      if (this.D != null) {
         this.D.setFloat(object, f);
      }
   }

   private void S(Object object, double d) {
      if (ForgeVersion.MC_1_7_10.Y()) {
         this.e.invokeVoidNoArgs(object);
      } else {
         this.e.invokeVoid(object, d);
      }
   }

   private void b(Object object, float f) {
      if (this.c != null) {
         this.c.setFloat(object, f);
      }
   }

   public static void F(MEntityRenderer mEntityRenderer, Object object, float f, long l) {
      mEntityRenderer.z(object, f, l);
   }

   public static void u(MEntityRenderer mEntityRenderer, Object object, float f) {
      mEntityRenderer.b(object, f);
   }

   private Object a(Object object) {
      return this.U.getObject(object);
   }

   private Object O(Object object) {
      if (this.K == null) {
         return null;
      } else {
         Object object2 = this.K.getObject(object);
         if (object2 == null) {
            return null;
         } else {
            Object object3 = this.u.getObject(object2);
            if (object3 == null) {
               return null;
            } else {
               Object object4 = this.f.getObject(object3);
               return object4 == null ? null : this.h.getObject(object4);
            }
         }
      }
   }

   public static Object o(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.O(object);
   }

   public static void h(MEntityRenderer mEntityRenderer, Object object, float f, long l, Object object2) {
      mEntityRenderer.J(object, f, l, object2);
   }

   private void x(Object object, double d) {
      if (ForgeVersion.MC_1_7_10.Y()) {
         this.W.invokeVoidNoArgs(object);
      } else {
         this.W.invokeVoid(object, d);
      }
   }

   public static void x(MEntityRenderer mEntityRenderer, Object object, float f) {
      mEntityRenderer.y(object, f);
   }

   private void G(Object object, Object object2) {
      this.a.invokeVoid(object, object2);
   }

   public static Object b(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.u(object);
   }

   private void m(Object object, Object object2, float f) {
      if (this.l != null) {
         this.l.invokeVoid(object, object2, f);
      }
   }

   private void Y(Object object, float f, long l) {
      this.J.invokeVoid(object, f);
   }

   public static Object E(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.z(object);
   }

   public static int n() {
      int n = X();
      return n == 0 ? 57 : 0;
   }

   private boolean v(Object object) {
      return this.Z.getBoolean(object);
   }

   private void S(Object object, Object object2, float f) {
      if (this.g != null) {
         this.g.invokeVoid(object, object2, f);
      }
   }

   private Object u(Object object) {
      if (this.P == null) {
         Object object2 = this.K.getObject(object);
         return object2 == null ? null : this.G.getObject(object2);
      } else {
         return this.P.getObject(object);
      }
   }

   public static int X() {
      return s;
   }

   private Object s() {
      return this.Q.invokeObject(null);
   }

   private void z(Object object, float f, long l) {
      this.a.invokeVoid(object, f, l);
   }

   public static Object Q(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.f(object);
   }

   private int I(Object object) {
      if (this.q == null) {
         return 0;
      } else {
         return ForgeVersion.MC_1_21_6.d() ? (int)this.q.getFloat(object) : this.q.getInt(object);
      }
   }

   private Object k(Object object) {
      return this.X.getObject(object);
   }

   private Object A(Object object) {
      return this.A.getObject(object);
   }

   public static void T(MEntityRenderer mEntityRenderer, Object object, Object object2) {
      mEntityRenderer.a(object, object2);
   }

   public static Object getFogRenderer(MEntityRenderer mappings, Object entityRenderer) {
      return mappings.getFogRendererHandle(entityRenderer);
   }

   private void d(Object object, Object object2) {
      this.N.setObject(object, object2);
   }

   public MEntityRenderer() {
      this(X());
   }

   private MEntityRenderer(int n) {
      super(MappedClasses.FW);
      if (ForgeVersion.MC_1_21_6.v()) {
         Class clazz = MappedClasses.qY;
         boolean bl = true;
         String string = "resourceManager";
         this.X = this.J(string, bl, clazz);
      } else {
         if (ForgeVersion.MC_26_1.d()) {
            Class clazz = MappedClasses.uI;
            boolean bl = true;
            String string = "gameRenderState";
            this.K = this.J(string, bl, clazz);
            Class clazz2 = MappedClasses.i;
            boolean bl2 = true;
            String string2 = "guiRenderState";
            Class clazz3 = MappedClasses.uI;
            this.G = this.registerInstanceFieldForOwner(clazz3, string2, bl2, clazz2);
            Class clazz4 = MappedClasses.z6;
            boolean bl3 = true;
            String string3 = "levelRenderState";
            Class clazz5 = MappedClasses.uI;
            this.u = this.registerInstanceFieldForOwner(clazz5, string3, bl3, clazz4);
            Class clazz6 = MappedClasses.zf;
            boolean bl4 = true;
            String string4 = "cameraRenderState";
            Class clazz7 = MappedClasses.z6;
            this.f = this.registerInstanceFieldForOwner(clazz7, string4, bl4, clazz6);
            Class clazz8 = MappedClasses.qr;
            boolean bl5 = true;
            String string5 = "projectionMatrix";
            Class clazz9 = MappedClasses.zf;
            this.h = this.registerInstanceFieldForOwner(clazz9, string5, bl5, clazz8);
         } else {
            Class clazz = MappedClasses.i;
            boolean bl = true;
            String string = "guiRenderState";
            this.P = this.J(string, bl, clazz);
         }

         Class clazz = MappedClasses.Vq;
         boolean bl = true;
         String string = "screenEffectRenderer";
         this.A = this.J(string, bl, clazz);
         Class clazz10 = MappedClasses.FOG_RENDERER;
         boolean bl6 = true;
         String string6 = "fogRenderer";
         this.fogRendererField = this.J(string6, bl6, clazz10);
         Class clazz11 = MappedClasses.w;
         boolean bl7 = true;
         String string7 = "guiRenderer";
         this.z = this.J(string7, bl7, clazz11);
      }

      if (ForgeVersion.MC_1_16_5.v()) {
         Class[] classArray = new Class[]{int.class, float.class};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "setupFog";
         this.L = this.Y(string, bl, clazz, classArray);
      }

      if (ForgeVersion.MC_1_20_6.v()) {
         if (ForgeVersion.MC_1_12_2.d()) {
            Class<?> clazz = DescUtils.getArrayType(MappedClasses.zC);
            boolean bl = true;
            String string = "SHADERS_TEXTURES";
            this.d = this.registerStaticField(string, bl, clazz);
         } else {
            Class<?> clazz = DescUtils.getArrayType(MappedClasses.zC);
            boolean bl = true;
            String string = "shaderResourceLocations";
            this.d = this.registerStaticField(string, bl, clazz);
         }
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         if (ForgeVersion.MC_1_21_0.v()) {
            Class clazz = MappedClasses.Fo;
            boolean bl = true;
            String string = "shaderGroup";
            this.N = this.J(string, bl, clazz);
         }

         Class clazz = MappedClasses.zH;
         boolean bl = true;
         String string = ForgeVersion.MC_26_1.d() ? "lightmap" : "lightmapTexture";
         this.U = this.J(string, bl, clazz);
         if (ForgeVersion.MC_26_1.v()) {
            Class[] classArray = new Class[]{MappedClasses.zJ, MappedClasses.zc, boolean.class, boolean.class, float.class};
            Class<Void> clazz12 = void.class;
            String string8 = "update";
            this.x = this.methodBuilder(string8, clazz12, classArray)
               .setOwnerClass(MappedClasses.lt)
               .setNameForVersion(ForgeVersion.MC_1_21_11.n(), "setup")
               .setParameterTypesForVersion(ForgeVersion.MC_1_21_11.n(), MappedClasses.YU, MappedClasses.zc, boolean.class, boolean.class, float.class)
               .buildMethod();
         } else {
            Class[] classArray = new Class[]{MappedClasses.uy};
            Class<Void> clazz13 = void.class;
            String string9 = "update";
            this.x = this.methodBuilder(string9, clazz13, classArray).setOwnerClass(MappedClasses.lt).buildMethod();
         }

         Class clazz14 = MappedClasses.lt;
         boolean bl8 = true;
         String string10 = ForgeVersion.MC_26_1.d() ? "mainCamera" : "activeRender";
         this.n = this.J(string10, bl8, clazz14);
         if (ForgeVersion.MC_26_1.v()) {
            Class[] classArray = new Class[]{MappedClasses.DQ, float.class};
            Class<Void> clazz15 = void.class;
            boolean bl9 = true;
            String string11 = "hurtCameraEffect";
            this.l = this.Y(string11, bl9, clazz15, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.DQ, float.class};
            Class<Void> clazz16 = void.class;
            boolean bl10 = true;
            String string12 = "applyBobbing";
            this.g = this.Y(string12, bl10, clazz16, classArray2);
         }

         if (ForgeVersion.MC_26_1.d()) {
            MappingField mappingField2 = null;
         } else {
            Class<Integer> clazz17 = int.class;
            boolean bl11 = true;
            String string13 = "rendererUpdateCount";
            MappingField mappingField2 = this.q = this.J(string13, bl11, clazz17);
         }

         if (ForgeVersion.MC_1_21_0.v()) {
            Class[] classArray = new Class[]{MappedClasses.qr};
            Class<Void> clazz18 = void.class;
            boolean bl12 = true;
            String string14 = "resetProjectionMatrix";
            this.p = this.Y(string14, bl12, clazz18, classArray);
         }

         Class clazz19 = MappedClasses.zc;
         boolean bl13 = true;
         String string15 = "pointedEntity";
         Class clazz20 = MappedClasses.uP;
         this.v = this.registerInstanceFieldForOwner(clazz20, string15, bl13, clazz19);
         if (ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_26_1.d()) {
               this.m = null;
               this.r = null;
            } else {
               Class[] classArray = new Class[]{MappedClasses.lt, float.class, boolean.class};
               Class<Double> clazz21 = double.class;
               boolean bl14 = true;
               String string16 = "getFov";
               this.m = this.Y(string16, bl14, clazz21, classArray);
               Class[] classArray3 = new Class[]{double.class};
               Class clazz22 = MappedClasses.qr;
               boolean bl15 = true;
               String string17 = "getProjectionMatrix";
               this.r = this.Y(string17, bl15, clazz22, classArray3);
            }
         } else {
            Class[] classArray = new Class[]{MappedClasses.lt, float.class, boolean.class};
            Class<Double> clazz23 = double.class;
            boolean bl16 = Wrapper.isNativeAvailable;
            String string18 = "func_215311_a";
            this.m = this.Y(string18, bl16, clazz23, classArray);
            Class[] classArray4 = new Class[]{MappedClasses.lt, float.class, boolean.class};
            Class clazz24 = MappedClasses.qr;
            boolean bl17 = true;
            String string19 = "getProjectionMatrix";
            this.r = this.Y(string19, bl17, clazz24, classArray4);
         }

         Class[] classArray = new Class[]{float.class, long.class, MappedClasses.DQ};
         Class<Void> clazz25 = void.class;
         String string20 = "renderLevel";
         this.a = this.methodBuilder(string20, clazz25, classArray)
            .setParameterTypesForVersion(ForgeVersion.MC_1_21_0.n(), MappedClasses.uy)
            .setParameterTypesForVersion(ForgeVersion.MC_1_20_6.S(), float.class, long.class)
            .buildMethod();
         if (ForgeVersion.MC_1_21_11.d()) {
            String string21 = ForgeVersion.MC_26_1.d() ? "update" : "updateCamera";
            Class[] classArray5;
            if (ForgeVersion.MC_26_2.d()) {
               classArray5 = new Class[]{MappedClasses.uy};
            } else if (ForgeVersion.MC_26_1.d()) {
               Class[] classArray6 = new Class[]{MappedClasses.uy, null};
               classArray5 = classArray6;
               classArray6[1] = boolean.class;
            } else {
               Class[] classArray7 = new Class[1];
               classArray5 = classArray7;
               classArray7[0] = MappedClasses.uy;
            }

            Class<Void> clazz26 = void.class;
            boolean bl18 = true;
            this.C = this.Y(string21, bl18, clazz26, classArray5);
            if (ForgeVersion.MC_26_1.d()) {
               this.extractRenderStateMethod = this.Y(
                  ForgeVersion.MC_26_2.d() ? "extract" : "extractRenderState", true, void.class, MappedClasses.uy, boolean.class
               );
            }
         }
      } else {
         Class clazzx = MappedClasses.Fo;
         boolean blx = true;
         String stringx = ForgeVersion.c() >= 23 ? "shaderGroup" : "theShaderGroup";
         this.N = this.J(stringx, blx, clazzx);
         Class clazz27 = MappedClasses.zc;
         boolean bl19 = true;
         String string23 = "pointedEntity";
         this.v = this.J(string23, bl19, clazz27);
         Class[] classArray = new Class[]{float.class, int.class};
         Class<Void> clazz28 = void.class;
         boolean bl20 = true;
         String string24 = "setupCameraTransform";
         this.i = this.Y(string24, bl20, clazz28, classArray);
         Class[] classArray9 = new Class[]{float.class, long.class};
         Class<Void> clazz29 = void.class;
         boolean bl21 = true;
         String string25 = "renderWorld";
         this.a = this.Y(string25, bl21, clazz29, classArray9);
         if (ForgeVersion.MC_1_8_9.d()) {
            Class[] classArray10 = new Class[]{int.class, float.class, long.class};
            Class<Void> clazz30 = void.class;
            boolean bl22 = true;
            String string26 = "renderWorldPass";
            this.H = this.Y(string26, bl22, clazz30, classArray10);
         }

         Class[] classArray11 = new Class[]{float.class};
         Class<Void> clazz31 = void.class;
         boolean bl23 = true;
         String string27 = "orientCamera";
         this.B = this.Y(string27, bl23, clazz31, classArray11);
      }

      if (ForgeVersion.MC_26_1.d()) {
         this.j = null;
         this.D = null;
      } else {
         Class<Float> clazzx = float.class;
         boolean blx = true;
         String stringx = "fovModifierHand";
         this.j = this.J(stringx, blx, clazzx);
         Class<Float> clazz32 = float.class;
         boolean bl24 = true;
         String string28 = "fovModifierHandPrev";
         this.D = this.J(string28, bl24, clazz32);
      }

      Class[] classArray = new Class[]{int.class, int.class};
      Class<Void> clazzx = void.class;
      boolean blx = true;
      String stringx = "updateShaderGroupSize";
      this.M = this.Y(stringx, blx, clazzx, classArray);
      if (ForgeVersion.MC_26_1.d()) {
         this.k = null;
         MappingMethod mappingMethod = null;
      } else {
         Class[] classArray12 = new Class[]{float.class};
         Class<Void> clazz33 = void.class;
         boolean bl25 = true;
         String string29 = "getMouseOver";
         MappingMethod mappingMethod = this.k = this.Y(string29, bl25, clazz33, classArray12);
      }

      if (ForgeVersion.MC_26_1.d()) {
         MappingField mappingField = null;
      } else {
         Class<Float> clazz34 = float.class;
         boolean bl26 = true;
         String string30 = "farPlaneDistance";
         MappingField mappingField = this.c = this.J(string30, bl26, clazz34);
      }

      if (ForgeVersion.MC_1_7_10.Y()) {
         Class<Boolean> clazz35 = boolean.class;
         boolean bl27 = true;
         String string31 = "useShader";
         this.Z = this.J(string31, bl27, clazz35);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray13 = new Class[]{float.class, long.class, boolean.class};
            Class<Void> clazz36 = void.class;
            boolean bl28 = true;
            String string32 = "updateCameraAndRender";
            this.J = this.Y(string32, bl28, clazz36, classArray13);
         } else {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
               Class[] classArray14 = new Class[]{float.class, long.class};
               Class<Void> clazz37 = void.class;
               boolean bl29 = true;
               String string33 = "updateCameraAndRender";
               this.J = this.Y(string33, bl29, clazz37, classArray14);
            } else {
               Class[] classArray15 = new Class[]{float.class, long.class};
               Class<Void> clazz38 = void.class;
               boolean bl30 = Wrapper.isNativeAvailable;
               String string34 = "func_181560_a";
               this.J = this.Y(string34, bl30, clazz38, classArray15);
            }

            Class[] classArray16 = new Class[0];
            Class<Void> clazz39 = void.class;
            boolean bl31 = true;
            String string35 = "disableLightmap";
            this.e = this.Y(string35, bl31, clazz39, classArray16);
            Class[] classArray17 = new Class[0];
            Class<Void> clazz40 = void.class;
            boolean bl32 = true;
            String string36 = "enableLightmap";
            this.W = this.Y(string36, bl32, clazz40, classArray17);
         }
      } else {
         Class[] classArray18 = new Class[]{double.class};
         Class<Void> clazz41 = void.class;
         boolean bl33 = true;
         String string37 = "disableLightmap";
         this.e = this.Y(string37, bl33, clazz41, classArray18);
         Class[] classArray19 = new Class[]{double.class};
         Class<Void> clazz42 = void.class;
         boolean bl34 = true;
         String string38 = "enableLightmap";
         this.W = this.Y(string38, bl34, clazz42, classArray19);
         if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            Class[] classArray20 = new Class[]{float.class};
            Class<Void> clazz43 = void.class;
            boolean bl35 = true;
            String string39 = "updateCameraAndRender";
            this.J = this.Y(string39, bl35, clazz43, classArray20);
         } else {
            Class[] classArray21 = new Class[]{float.class};
            Class<Void> clazz44 = void.class;
            boolean bl36 = Wrapper.isNativeAvailable;
            String string40 = "func_78480_b";
            this.J = this.Y(string40, bl36, clazz44, classArray21);
         }
      }

      if (ForgeVersion.MC_1_17.d()) {
         if (ForgeVersion.MC_1_21_6.v()) {
            Class[] classArray22 = new Class[]{int.class, int.class, float.class};
            Class<Void> clazz45 = void.class;
            boolean bl37 = true;
            String string41 = "renderItemActivationAnimation";
            this.V = this.Y(string41, bl37, clazz45, classArray22);
         }

         if (ForgeVersion.MC_1_21_0.H().y()) {
            Class[] classArray23 = new Class[0];
            Class clazz46 = MappedClasses.Fv;
            boolean bl38 = true;
            String string42 = "getPositionColorShader";
            this.Q = this.registerStaticMethod(string42, bl38, clazz46, classArray23);
         }
      }
   }

   public static void X(MEntityRenderer mEntityRenderer, Object object, float f, long l) {
      mEntityRenderer.Y(object, f, l);
   }

   private void w(Object object, int n, int n2) {
      this.M.invokeVoid(object, n, n2);
   }

   private void S(Object object, boolean bl) {
      this.Z.setBoolean(object, bl);
   }

   public static int m(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.I(object);
   }

   private void W(Object object, float f) {
      if (this.k != null) {
         this.k.invokeVoid(object, f);
      }
   }

   private void J(Object object, float f, long l, Object object2) {
      this.a.invokeVoid(object, f, l, object2);
   }

   private void X(Object object, float f, int n) {
      this.i.invokeVoid(object, f, n);
   }

   public static Object[] k(MEntityRenderer mEntityRenderer) {
      return mEntityRenderer.F();
   }

   public static void j(MEntityRenderer mEntityRenderer, Object object, float f) {
      mEntityRenderer.L(object, f);
   }

   private Object getFogRendererHandle(Object entityRenderer) {
      return this.fogRendererField.getObject(entityRenderer);
   }

   private Object J(Object object) {
      return this.v.getObject(object);
   }

   public static void D(MEntityRenderer mEntityRenderer, Object object, float f, int n) {
      mEntityRenderer.X(object, f, n);
   }

   public static Object z(MEntityRenderer mEntityRenderer, Object object, Object object2, float f, boolean bl) {
      return mEntityRenderer.L(object, object2, f, bl);
   }

   public static void i(MEntityRenderer mEntityRenderer, Object object, int n, int n2) {
      mEntityRenderer.w(object, n, n2);
   }

   private Object N(Object object) {
      return this.N.getObject(object);
   }

   public static void o(MEntityRenderer mEntityRenderer, Object object, Object object2) {
      mEntityRenderer.z(object, object2);
   }

   public static void a(MEntityRenderer mEntityRenderer, Object object, Object object2) {
      mEntityRenderer.u(object, object2);
   }

   private void z(Object object, Object object2) {
      this.p.invokeVoid(object, object2);
   }

   public static void c(int n) {
      s = n;
   }

   private Object[] F() {
      return this.d.getObjectArray(null);
   }

   public static void C(MEntityRenderer mEntityRenderer, Object object, boolean bl) {
      mEntityRenderer.S(object, bl);
   }

   public static Object O(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.a(object);
   }

   public static float c(MEntityRenderer mEntityRenderer, Object object) {
      return mEntityRenderer.j(object);
   }

   private Object z(Object object) {
      return this.z.getObject(object);
   }

   public static void D(MEntityRenderer mEntityRenderer, Object object, Object object2) {
      mEntityRenderer.d(object, object2);
   }

   private void M(Object object, float f) {
      this.B.invokeVoid(object, f);
   }

   public static Object G(MEntityRenderer mEntityRenderer) {
      return mEntityRenderer.s();
   }

   public static void V(MEntityRenderer mEntityRenderer, Object object, double d) {
      mEntityRenderer.x(object, d);
   }

   public void extractRenderState(Object entityRenderer, Object deltaTracker) {
      if (this.extractRenderStateMethod != null) {
         this.extractRenderStateMethod.invokeVoid(entityRenderer, deltaTracker, true);
      }
   }

   static {
      c(0);
   }
}
