package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderSystem extends Mapping {
   private MappingMethod X;
   private MappingMethod P;
   private MappingField U;
   private static int[] x;
   public MappingMethod v;
   private MappingMethod S;
   private MappingMethod B;
   private MappingMethod M;
   private MappingField p;
   private MappingMethod N;
   private MappingMethod C;
   private MappingMethod Y;
   private MappingMethod Q;
   private MappingMethod a;
   private MappingMethod f;
   private MappingMethod h;
   private MappingMethod V;
   private MappingMethod w;

   public static void D(MRenderSystem mRenderSystem) {
      mRenderSystem.j();
   }

   public static void S(MRenderSystem mRenderSystem, float f, float f2, float f3, float f4) {
      mRenderSystem.D(f, f2, f3, f4);
   }

   public MRenderSystem() {
      this(K());
   }

   private MRenderSystem(int[] nArray) {
      super(MappedClasses.ls);
      if (ForgeVersion.MC_1_17.d()) {
         if (ForgeVersion.MC_1_21_4.v()) {
            Class[] classArray = new Class[0];
            Class<Void> clazz = void.class;
            boolean bl = true;
            String string = "applyModelViewMatrix";
            this.N = this.registerStaticMethod(string, bl, clazz, classArray);
            Class<int[]> clazz2 = int[].class;
            boolean bl2 = false;
            String string2 = "f_157152_";
            this.U = this.registerStaticField(string2, bl2, clazz2);
         }

         if (ForgeVersion.MC_1_21_6.v()) {
            Class[] classArray = new Class[0];
            Class<Void> clazz = void.class;
            boolean bl = false;
            String string = "m_69478_";
            this.V = this.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{int.class, MappedClasses.zC};
            Class<Void> clazz3 = void.class;
            boolean bl3 = false;
            String string3 = "m_157179_";
            this.X = this.registerStaticMethod(string3, bl3, clazz3, classArray2);
            Class[] classArray3 = new Class[]{int.class, int.class};
            Class<Void> clazz4 = void.class;
            boolean bl4 = false;
            String string4 = "m_69405_";
            this.Q = this.registerStaticMethod(string4, bl4, clazz4, classArray3);
            Class[] classArray4 = new Class[]{float.class, float.class, float.class, float.class};
            Class<Void> clazz5 = void.class;
            boolean bl5 = false;
            String string5 = "m_157429_";
            this.B = this.registerStaticMethod(string5, bl5, clazz5, classArray4);
            Class[] classArray5 = new Class[0];
            Class<Void> clazz6 = void.class;
            boolean bl6 = false;
            String string6 = "m_69482_";
            this.C = this.registerStaticMethod(string6, bl6, clazz6, classArray5);
            Class[] classArray6 = new Class[0];
            Class clazz7 = MappedClasses.qr;
            boolean bl7 = false;
            String string7 = "getProjectionMatrix";
            this.M = this.registerStaticMethod(string7, bl7, clazz7, classArray6);
            Class[] classArray7 = new Class[]{MappedClasses.qr, MappedClasses.VT};
            Class<Void> clazz8 = void.class;
            boolean bl8 = false;
            String string8 = "setProjectionMatrix";
            this.S = this.registerStaticMethod(string8, bl8, clazz8, classArray7);
            Class[] classArray8 = new Class[0];
            Class<Void> clazz9 = void.class;
            boolean bl9 = false;
            String string9 = "disableBlend";
            this.Y = this.registerStaticMethod(string9, bl9, clazz9, classArray8);
            Class[] classArray9 = new Class[0];
            Class<Void> clazz10 = void.class;
            boolean bl10 = false;
            String string10 = "disableDepthTest";
            this.h = this.registerStaticMethod(string10, bl10, clazz10, classArray9);
            Class[] classArray10 = new Class[0];
            Class<Void> clazz11 = void.class;
            boolean bl11 = false;
            String string11 = "defaultBlendFunc";
            this.f = this.registerStaticMethod(string11, bl11, clazz11, classArray10);
         }

         Class clazz = MappedClasses.DQ;
         boolean bl = true;
         String string = "f_157141_";
         this.p = this.registerStaticField(string, bl, clazz);
         if (ForgeVersion.MC_1_21_11.v()) {
            Class[] classArray = new Class[]{float.class};
            Class<Void> clazz12 = void.class;
            boolean bl12 = false;
            String string12 = "lineWidth";
            this.P = this.registerStaticMethod(string12, bl12, clazz12, classArray);
         }

         if (ForgeVersion.MC_26_2.d()) {
            this.w = this.registerStaticMethod("getDevice", true, MappedClasses.GPU_DEVICE);
         }
      } else {
         Class[] classArray = new Class[0];
         Class<Void> clazzx = void.class;
         boolean blx = false;
         String stringx = "defaultAlphaFunc";
         this.v = this.registerStaticMethod(stringx, blx, clazzx, classArray);
      }

      if (ForgeVersion.MC_1_21_6.v()) {
         Class[] classArray = new Class[]{float.class};
         Class<Void> clazzx = void.class;
         boolean blx = false;
         String stringx = "fogDensity";
         this.a = this.registerStaticMethod(stringx, blx, clazzx, classArray);
      }
   }

   public static void h(MRenderSystem mRenderSystem) {
      mRenderSystem.x();
   }

   private void H(float f) {
      this.a.invokeVoid(null, f);
   }

   private void y() {
      this.Y.invokeVoidNoArgs(null);
   }

   public static void o(MRenderSystem mRenderSystem, int n, int n2) {
      mRenderSystem.D(n, n2);
   }

   public static void o(MRenderSystem mRenderSystem, int n, Object object) {
      mRenderSystem.G(n, object);
   }

   private int[] l() {
      return this.U.getIntArray(null);
   }

   public static int[] K() {
      return x;
   }

   public static void n(MRenderSystem mRenderSystem, int n, int n2) {
      mRenderSystem.J(n, n2);
   }

   private void F(float f) {
      this.P.invokeVoid(null, f);
   }

   public static void f(MRenderSystem mRenderSystem) {
      mRenderSystem.I();
   }

   private void J(int n, int n2) {
      int[] nArray = this.l();
      nArray[n] = n2;
      this.U.setIntArray(null, nArray);
   }

   private void I() {
      this.f.invokeVoidNoArgs(null);
   }

   public static int[] x(MRenderSystem mRenderSystem) {
      return mRenderSystem.l();
   }

   private void j() {
      this.C.invokeVoidNoArgs(null);
   }

   public static void F(MRenderSystem mRenderSystem, float f) {
      mRenderSystem.F(f);
   }

   private void G(int n, Object object) {
      this.X.invokeVoid(n, object);
   }

   public static void U(MRenderSystem mRenderSystem) {
      mRenderSystem.K$src$V$1mimvos();
   }

   private void D(int n, int n2) {
      this.Q.invokeVoid(null, n, n2);
   }

   private void K$src$V$1mimvos() {
      this.N.invokeVoidNoArgs(null);
   }

   private void D(float f, float f2, float f3, float f4) {
      this.B.invokeVoid(null, f, f2, f3, f4);
   }

   private Object F() {
      return this.M.invokeObject(null);
   }

   public static void c(MRenderSystem mRenderSystem) {
      mRenderSystem.y();
   }

   private void x() {
      this.h.invokeVoidNoArgs(null);
   }

   public Object P() {
      return this.p.getObject(null);
   }

   public static void D(int[] nArray) {
      x = nArray;
   }

   public static void j(MRenderSystem mRenderSystem) {
      mRenderSystem.S();
   }

   private void S() {
      this.V.invokeVoidNoArgs(null);
   }

   public static void Z(MRenderSystem mRenderSystem, float f) {
      mRenderSystem.H(f);
   }

   private void i(Object object, Object object2) {
      this.S.invokeVoid(null, object, object2);
   }

   public Object getDevice() {
      return this.w == null ? null : this.w.invokeObject(null);
   }

   public static Object b(MRenderSystem mRenderSystem) {
      return mRenderSystem.getDevice();
   }

   static {
      D((int[])null);
   }
}
