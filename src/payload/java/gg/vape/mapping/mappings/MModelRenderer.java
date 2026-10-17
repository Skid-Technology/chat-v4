package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MModelRenderer extends Mapping {
   private static int[] y;
   private MappingField J;
   private MappingField o;
   private MappingField v;
   private MappingField q;
   private MappingField x;
   private MappingField r;
   private MappingField D;
   private MappingField l;
   private MappingField w;
   private MappingField F;
   private MappingField I;
   private MappingField e;
   private MappingField a;

   private float K(Object object) {
      return this.o.getFloat(object);
   }

   private void g(Object object, boolean bl) {
      this.w.setBoolean(object, bl);
   }

   public static float U(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.b(object);
   }

   private float e(Object object) {
      return this.e.getFloat(object);
   }

   public static float v(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.x(object);
   }

   private void A(Object object, boolean bl) {
      this.J.setBoolean(object, bl);
   }

   private float b(Object object) {
      return this.I.getFloat(object);
   }

   public static float i(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.M(object);
   }

   public static float t(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.r(object);
   }

   public static float h(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.e(object);
   }

   public static void P(int[] nArray) {
      y = nArray;
   }

   private int o(Object object) {
      return this.D.getInt(object);
   }

   public MModelRenderer() {
      this(T());
   }

   private MModelRenderer(int[] nArray) {
      super(MappedClasses.Yd);
      if (nArray != null) {
         if (ForgeVersion.MC_1_17.d()) {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "xTexOffs";
            Class clazz2 = MappedClasses.q;
            this.r = this.registerInstanceFieldForOwner(clazz2, string, bl, clazz);
            Class<Integer> clazz3 = int.class;
            boolean bl2 = true;
            String string2 = "yTexOffs";
            Class clazz4 = MappedClasses.q;
            this.D = this.registerInstanceFieldForOwner(clazz4, string2, bl2, clazz3);
         } else {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "textureOffsetX";
            this.r = this.J(string, bl, clazz);
            Class<Integer> clazz5 = int.class;
            boolean bl3 = true;
            String string3 = "textureOffsetY";
            this.D = this.J(string3, bl3, clazz5);
         }

         Class<Float> clazz = float.class;
         boolean bl = true;
         String string = "rotateAngleX";
         this.v = this.J(string, bl, clazz);
         Class<Float> clazz6 = float.class;
         boolean bl4 = true;
         String string4 = "rotateAngleY";
         this.o = this.J(string4, bl4, clazz6);
         Class<Float> clazz7 = float.class;
         boolean bl5 = true;
         String string5 = "rotateAngleZ";
         this.I = this.J(string5, bl5, clazz7);
         Class<Float> clazz8 = float.class;
         boolean bl6 = true;
         String string6 = "rotationPointX";
         this.e = this.J(string6, bl6, clazz8);
         Class<Float> clazz9 = float.class;
         boolean bl7 = true;
         String string7 = "rotationPointY";
         this.a = this.J(string7, bl7, clazz9);
         Class<Float> clazz10 = float.class;
         boolean bl8 = true;
         String string8 = "rotationPointZ";
         this.l = this.J(string8, bl8, clazz10);
         Class<Boolean> clazz11 = boolean.class;
         boolean bl9 = true;
         String string9 = "showModel";
         this.J = this.J(string9, bl9, clazz11);
         if (!ForgeVersion.MC_1_16_5.d()) {
            Class<Float> clazz12 = float.class;
            boolean bl10 = true;
            String string10 = "offsetX";
            this.F = this.J(string10, bl10, clazz12);
            Class<Float> clazz13 = float.class;
            boolean bl11 = true;
            String string11 = "offsetY";
            this.x = this.J(string11, bl11, clazz13);
            Class<Float> clazz14 = float.class;
            boolean bl12 = true;
            String string12 = "offsetZ";
            this.q = this.J(string12, bl12, clazz14);
            Class<Boolean> clazz15 = boolean.class;
            boolean bl13 = true;
            String string13 = "isHidden";
            this.w = this.J(string13, bl13, clazz15);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            P(new int[4]);
         }
      } else {
         Class<Integer> clazzx = int.class;
         boolean blx = true;
         String stringx = "textureOffsetY";
         this.D = this.J(stringx, blx, clazzx);
         Class<Float> clazz16 = float.class;
         boolean bl14 = true;
         String string14 = "rotateAngleX";
         this.v = this.J(string14, bl14, clazz16);
         Class<Float> clazz17 = float.class;
         boolean bl15 = true;
         String string15 = "rotateAngleY";
         this.o = this.J(string15, bl15, clazz17);
         Class<Float> clazz18 = float.class;
         boolean bl16 = true;
         String string16 = "rotateAngleZ";
         this.I = this.J(string16, bl16, clazz18);
         Class<Float> clazz19 = float.class;
         boolean bl17 = true;
         String string17 = "rotationPointX";
         this.e = this.J(string17, bl17, clazz19);
         Class<Float> clazz20 = float.class;
         boolean bl18 = true;
         String string18 = "rotationPointY";
         this.a = this.J(string18, bl18, clazz20);
         Class<Float> clazz21 = float.class;
         boolean bl19 = true;
         String string19 = "rotationPointZ";
         this.l = this.J(string19, bl19, clazz21);
         Class<Boolean> clazz22 = boolean.class;
         boolean bl20 = true;
         String string20 = "showModel";
         this.J = this.J(string20, bl20, clazz22);
         Class<Boolean> clazz23 = boolean.class;
         boolean bl21 = true;
         String string21 = "isHidden";
         this.w = this.J(string21, bl21, clazz23);
         if (GuiComponent.getLegacyComponentState() == null) {
            P(new int[4]);
         }

         this.r = null;
      }
   }

   public static int j(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.d(object);
   }

   public static float k(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.I(object);
   }

   public static int[] T() {
      return y;
   }

   private int d(Object object) {
      return this.r.getInt(object);
   }

   private float r(Object object) {
      return this.l.getFloat(object);
   }

   private float I(Object object) {
      return this.q.getFloat(object);
   }

   public static float s(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.K(object);
   }

   public static float b(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.c(object);
   }

   public static int z(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.o(object);
   }

   private float P(Object object) {
      return this.v.getFloat(object);
   }

   private float x(Object object) {
      return this.a.getFloat(object);
   }

   public static float c(MModelRenderer mModelRenderer, Object object) {
      return mModelRenderer.P(object);
   }

   private float c(Object object) {
      return this.F.getFloat(object);
   }

   public static void j(MModelRenderer mModelRenderer, Object object, boolean bl) {
      mModelRenderer.A(object, bl);
   }

   public static void r(MModelRenderer mModelRenderer, Object object, boolean bl) {
      mModelRenderer.g(object, bl);
   }

   private float M(Object object) {
      return this.x.getFloat(object);
   }

   static {
      P(new int[3]);
   }
}
