package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMatrixStack extends Mapping {
   private MappingMethod i;
   private MappingMethod B;
   private static int[] P;
   private MappingMethod m;
   private MappingMethod D;
   private MappingMethod W;
   private MappingMethod j;
   private MappingMethod Q;
   private MappingMethod M;
   private MappingMethod Z;

   public MMatrixStack() {
      super(MappedClasses.DQ);
      Class[] classArray = new Class[0];
      Class<Void> clazz = void.class;
      boolean bl = false;
      String string = "<init>";
      this.D = this.Y(string, bl, clazz, classArray);
      Class[] classArray2 = new Class[0];
      Class clazz2 = MappedClasses.G;
      boolean bl2 = true;
      String string2 = "getLast";
      this.i = this.Y(string2, bl2, clazz2, classArray2);
      Class[] classArray3 = new Class[]{float.class, float.class, float.class};
      Class<Void> clazz3 = void.class;
      boolean bl3 = true;
      String string3 = "scale";
      this.Z = this.Y(string3, bl3, clazz3, classArray3);
      Class[] classArray4 = new Class[]{MappedClasses.qI};
      Class<Void> clazz4 = void.class;
      boolean bl4 = true;
      String string4 = "rotate";
      this.Q = this.Y(string4, bl4, clazz4, classArray4);
      Class[] classArray5 = new Class[]{double.class, double.class, double.class};
      Class<Void> clazz5 = void.class;
      boolean bl5 = true;
      String string5 = "translate";
      this.M = this.Y(string5, bl5, clazz5, classArray5);
      Class[] classArray6 = new Class[0];
      Class<Void> clazz6 = void.class;
      boolean bl6 = true;
      String string6 = "push";
      this.m = this.Y(string6, bl6, clazz6, classArray6);
      Class[] classArray7 = new Class[0];
      Class<Void> clazz7 = void.class;
      boolean bl7 = true;
      String string7 = "pop";
      this.B = this.Y(string7, bl7, clazz7, classArray7);
      if (l() != null) {
         if (ForgeVersion.MC_1_17.d()) {
            this.j = ForgeVersion.MC_26_2.d()
               ? this.Y("mulPose", true, void.class, MappedClasses.ZA)
               : this.Y("mulPoseMatrix", true, void.class, MappedClasses.qr);
            Class[] classArray9 = new Class[0];
            Class<Void> clazz9 = void.class;
            boolean bl9 = true;
            String string9 = "setIdentity";
            this.W = this.Y(string9, bl9, clazz9, classArray9);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            m(new int[3]);
         }
      } else {
         Class[] classArray10 = new Class[0];
         Class<Void> clazz10 = void.class;
         boolean bl10 = true;
         String string10 = "setIdentity";
         this.W = this.Y(string10, bl10, clazz10, classArray10);
         if (GuiComponent.getLegacyComponentState() == null) {
            m(new int[3]);
         }
      }
   }

   public static int[] l() {
      return P;
   }

   public static void d(MMatrixStack mMatrixStack, Object object, double d, double d2, double d3) {
      mMatrixStack.p(object, d, d2, d3);
   }

   private void j(Object object) {
      this.m.invokeVoidNoArgs(object);
   }

   private void p(Object object, double d, double d2, double d3) {
      this.M.invokeVoid(object, d, d2, d3);
   }

   public static Object S(MMatrixStack mMatrixStack, Object object) {
      return mMatrixStack.q(object);
   }

   private void x(Object object, Object object2) {
      this.j.invokeVoid(object, object2);
   }

   public static void u(MMatrixStack mMatrixStack, Object object) {
      mMatrixStack.g(object);
   }

   public static void b(MMatrixStack mMatrixStack, Object object) {
      mMatrixStack.h(object);
   }

   public static void r(MMatrixStack mMatrixStack, Object object, Object object2) {
      mMatrixStack.k(object, object2);
   }

   private void g(Object object) {
      this.B.invokeVoidNoArgs(object);
   }

   public static void m(int[] nArray) {
      P = nArray;
   }

   private Object t() {
      return this.D.newInstance();
   }

   private Object q(Object object) {
      return this.i.invokeObject(object);
   }

   private void k(Object object, Object object2) {
      this.Q.invokeVoid(object, object2);
   }

   public static void o(MMatrixStack mMatrixStack, Object object, Object object2) {
      mMatrixStack.x(object, object2);
   }

   public static void o(MMatrixStack mMatrixStack, Object object) {
      mMatrixStack.j(object);
   }

   public static void o(MMatrixStack mMatrixStack, Object object, float f, float f2, float f3) {
      mMatrixStack.v(object, f, f2, f3);
   }

   private void v(Object object, float f, float f2, float f3) {
      this.Z.invokeVoid(object, f, f2, f3);
   }

   public static Object U(MMatrixStack mMatrixStack) {
      return mMatrixStack.t();
   }

   private void h(Object object) {
      this.W.invokeVoidNoArgs(object);
   }

   static {
      m(new int[3]);
   }
}
