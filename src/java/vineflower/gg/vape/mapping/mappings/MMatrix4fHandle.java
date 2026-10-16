package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MMatrix4fHandle extends Mapping {
   private MappingField n;
   private MappingField p;
   private MappingMethod z;
   private MappingField r;
   private MappingMethod J;
   private MappingMethod Y;
   private MappingMethod Z;
   private MappingMethod v;
   private MappingField g;
   private MappingField P;
   private MappingField b;

   public MMatrix4fHandle() {
      this(MMatrixStack.l());
   }

   private MMatrix4fHandle(int[] nArray) {
      super(MappedClasses.Dy);
      Class[] classArray = new Class[]{int.class};
      this.Y = this.registerConstructor(classArray);
      Class[] classArray2 = new Class[0];
      Class clazz = MappedClasses.Dy;
      boolean bl = false;
      String string = "pushMatrix";
      this.z = this.Y(string, bl, clazz, classArray2);
      Class[] classArray3 = new Class[0];
      Class clazz2 = MappedClasses.Dy;
      boolean bl2 = false;
      String string2 = "popMatrix";
      this.v = this.Y(string2, bl2, clazz2, classArray3);
      Class[] classArray4 = new Class[]{float.class, float.class};
      Class clazz3 = MappedClasses.Zw;
      boolean bl3 = false;
      String string3 = "scale";
      Class clazz4 = MappedClasses.Zw;
      this.Z = this.registerInstanceMethodForOwner(clazz4, string3, bl3, clazz3, classArray4);
      Class[] classArray5 = new Class[]{float.class, float.class};
      Class clazz5 = MappedClasses.Zw;
      boolean bl4 = false;
      String string4 = "translate";
      Class clazz6 = MappedClasses.Zw;
      this.J = this.registerInstanceMethodForOwner(clazz6, string4, bl4, clazz5, classArray5);
      if (nArray != null) {
         Class<Float> clazz7 = float.class;
         boolean bl5 = false;
         String string5 = "m00";
         Class clazz8 = MappedClasses.Zw;
         this.g = this.registerInstanceFieldForOwner(clazz8, string5, bl5, clazz7);
         Class<Float> clazz9 = float.class;
         boolean bl6 = false;
         String string6 = "m01";
         Class clazz10 = MappedClasses.Zw;
         this.P = this.registerInstanceFieldForOwner(clazz10, string6, bl6, clazz9);
         Class<Float> clazz11 = float.class;
         boolean bl7 = false;
         String string7 = "m10";
         Class clazz12 = MappedClasses.Zw;
         this.r = this.registerInstanceFieldForOwner(clazz12, string7, bl7, clazz11);
         Class<Float> clazz13 = float.class;
         boolean bl8 = false;
         String string8 = "m11";
         Class clazz14 = MappedClasses.Zw;
         this.b = this.registerInstanceFieldForOwner(clazz14, string8, bl8, clazz13);
         Class<Float> clazz15 = float.class;
         boolean bl9 = false;
         String string9 = "m20";
         Class clazz16 = MappedClasses.Zw;
         this.p = this.registerInstanceFieldForOwner(clazz16, string9, bl9, clazz15);
         Class<Float> clazz17 = float.class;
         boolean bl10 = false;
         String string10 = "m21";
         Class clazz18 = MappedClasses.Zw;
         this.n = this.registerInstanceFieldForOwner(clazz18, string10, bl10, clazz17);
      } else {
         Class<Float> clazz19 = float.class;
         boolean bl11 = false;
         String string11 = "m00";
         Class clazz20 = MappedClasses.Zw;
         this.g = this.registerInstanceFieldForOwner(clazz20, string11, bl11, clazz19);
         Class<Float> clazz21 = float.class;
         boolean bl12 = false;
         String string12 = "m01";
         Class clazz22 = MappedClasses.Zw;
         this.P = this.registerInstanceFieldForOwner(clazz22, string12, bl12, clazz21);
         Class<Float> clazz23 = float.class;
         boolean bl13 = false;
         String string13 = "m10";
         Class clazz24 = MappedClasses.Zw;
         this.r = this.registerInstanceFieldForOwner(clazz24, string13, bl13, clazz23);
         Class<Float> clazz25 = float.class;
         boolean bl14 = false;
         String string14 = "m11";
         Class clazz26 = MappedClasses.Zw;
         this.b = this.registerInstanceFieldForOwner(clazz26, string14, bl14, clazz25);
         Class<Float> clazz27 = float.class;
         boolean bl15 = false;
         String string15 = "m20";
         Class clazz28 = MappedClasses.Zw;
         this.p = this.registerInstanceFieldForOwner(clazz28, string15, bl15, clazz27);
         Class<Float> clazz29 = float.class;
         boolean bl16 = false;
         String string16 = "m21";
         Class clazz30 = MappedClasses.Zw;
         this.n = this.registerInstanceFieldForOwner(clazz30, string16, bl16, clazz29);
         GuiComponent.setLegacyComponentState(new GuiComponent[4]);
      }
   }

   public static void j(MMatrix4fHandle mMatrix4fHandle, Object object, float f) {
      mMatrix4fHandle.T(object, f);
   }

   private void o(Object object, float f) {
      this.P.setFloat(object, f);
   }

   public static void t(MMatrix4fHandle mMatrix4fHandle, Object object, float f) {
      mMatrix4fHandle.w(object, f);
   }

   public static Object x(MMatrix4fHandle mMatrix4fHandle, Object object, float f, float f2) {
      return mMatrix4fHandle.C(object, f, f2);
   }

   private Object C(Object object, float f, float f2) {
      return this.Z.invokeObject(object, f, f2);
   }

   private void b(Object object, float f) {
      this.r.setFloat(object, f);
   }

   private void i(Object object, float f) {
      this.p.setFloat(object, f);
   }

   private Object r(Object object, float f, float f2) {
      return this.J.invokeObject(object, f, f2);
   }

   public static Object C(MMatrix4fHandle mMatrix4fHandle, Object object) {
      return mMatrix4fHandle.s(object);
   }

   private void w(Object object, float f) {
      this.g.setFloat(object, f);
   }

   private void T(Object object, float f) {
      this.n.setFloat(object, f);
   }

   private void F(Object object, float f) {
      this.b.setFloat(object, f);
   }

   public static void a(MMatrix4fHandle mMatrix4fHandle, Object object, float f) {
      mMatrix4fHandle.i(object, f);
   }

   private Object c(int n) {
      return this.Y.newInstance(n);
   }

   public static Object u(MMatrix4fHandle mMatrix4fHandle, Object object) {
      return mMatrix4fHandle.j(object);
   }

   private Object j(Object object) {
      return this.v.invokeObject(object);
   }

   public static Object j(MMatrix4fHandle mMatrix4fHandle, Object object, float f, float f2) {
      return mMatrix4fHandle.r(object, f, f2);
   }

   public static Object R(MMatrix4fHandle mMatrix4fHandle, int n) {
      return mMatrix4fHandle.c(n);
   }

   public static void F(MMatrix4fHandle mMatrix4fHandle, Object object, float f) {
      mMatrix4fHandle.F(object, f);
   }

   public static void L(MMatrix4fHandle mMatrix4fHandle, Object object, float f) {
      mMatrix4fHandle.b(object, f);
   }

   public static void U(MMatrix4fHandle mMatrix4fHandle, Object object, float f) {
      mMatrix4fHandle.o(object, f);
   }

   private Object s(Object object) {
      return this.z.invokeObject(object);
   }
}
