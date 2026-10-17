package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MFontRenderer extends Mapping {
   private MappingMethod X;
   private MappingMethod L;
   public final MappingMethod T;
   private MappingMethod i;
   private MappingMethod b;
   private MappingMethod c;
   private MappingMethod l;
   private MappingMethod m;
   public MappingMethod h;
   private MappingMethod P;
   private MappingMethod W;
   private MappingMethod g;

   private int D(Object object, String string, float f, float f2, int n, boolean bl, Object object2, Object object3, Object object4, int n2, int n3) {
      if (this.P == null) {
         return 0;
      } else if (ForgeVersion.MC_1_21_6.d()) {
         this.P.invokeVoid(object, string, f, f2, n, bl, object2, object3, object4, n2, n3);
         return 0;
      } else {
         return this.P.invokeInt(object, string, f, f2, n, bl, object2, object3, object4, n2, n3);
      }
   }

   public int C(Object object, Object object2, float f, float f2, int n, boolean bl, Object object3, Object object4, boolean bl2, int n2, int n3) {
      return this.W.invokeInt(object, object2, f, f2, n, bl, object3, object4, bl2, n2, n3);
   }

   public static int i(
      MFontRenderer mFontRenderer,
      Object object,
      String string,
      float f,
      float f2,
      int n,
      boolean bl,
      Object object2,
      Object object3,
      Object object4,
      int n2,
      int n3
   ) {
      return mFontRenderer.D(object, string, f, f2, n, bl, object2, object3, object4, n2, n3);
   }

   private int b(Object object, Object object2, Object object3, float f, float f2, int n) {
      return this.b.invokeInt(object, object2, object3, f, f2, n);
   }

   public int S(Object object, Object object2, String string, float f, float f2, int n) {
      return this.h.invokeInt(object, object2, string, f, f2, n);
   }

   public int u(Object object, String string, int n, int n2, int n3) {
      return this.h.invokeInt(object, string, n, n2, n3);
   }

   public int m(Object object) {
      return 10;
   }

   public int Y(Object object, String string, float f, float f2, int n, boolean bl) {
      return ForgeVersion.MC_1_7_10.L() ? this.L.invokeInt(object, string, (int)f, (int)f2, n, bl) : this.L.invokeInt(object, string, f, f2, n, bl);
   }

   public int drawStringWithShadow(Object object, String string, float f, float f2, int n) {
      return this.l.invokeInt(object, string, f, f2, n);
   }

   public MFontRenderer() {
      this(ScaledResolution.q());
   }

   private MFontRenderer(int n) {
      super(MappedClasses.uQ);
      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] classArray = new Class[]{String.class};
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "width";
         this.T = this.Y(string, bl, clazz, classArray);
         if (ForgeVersion.MC_26_1.d()) {
            if (ForgeVersion.MC_26_2.v()) {
               Class[] classArray2 = new Class[]{
                  String.class, float.class, float.class, int.class, boolean.class, MappedClasses.ZA, MappedClasses.ZK, MappedClasses.Y8, int.class, int.class
               };
               Class<Void> clazz2 = void.class;
               boolean bl2 = true;
               String string2 = "drawInBatch";
               this.P = this.Y(string2, bl2, clazz2, classArray2);
            }

            Class[] classArray3 = new Class[]{MappedClasses.uQ, String.class, int.class, int.class, int.class, boolean.class};
            Class<Void> clazz3 = void.class;
            boolean bl3 = true;
            String string3 = "text";
            Class clazz4 = MappedClasses.m;
            this.m = this.registerInstanceMethodForOwner(clazz4, string3, bl3, clazz3, classArray3);
         } else {
            Class[] classArray4 = new Class[]{
               String.class, float.class, float.class, int.class, boolean.class, MappedClasses.qr, MappedClasses.ZK, MappedClasses.Y8, int.class, int.class
            };
            Class<Integer> clazz5 = int.class;
            boolean bl4 = true;
            String string4 = "drawInBatch";
            this.P = this.Y(string4, bl4, clazz5, classArray4);
            Class[] classArray5 = new Class[]{MappedClasses.uQ, String.class, int.class, int.class, int.class, boolean.class};
            Class<Integer> clazz6 = int.class;
            boolean bl5 = true;
            String string5 = "drawString";
            Class clazz7 = MappedClasses.m;
            this.m = this.registerInstanceMethodForOwner(clazz7, string5, bl5, clazz6, classArray5);
         }
      } else {
         if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray = new Class[]{String.class, int.class, int.class, int.class};
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "drawStringWithShadow";
            this.c = this.Y(string, bl, clazz, classArray);
            Class[] classArray6 = new Class[]{String.class, int.class, int.class, int.class};
            Class<Integer> clazz8 = int.class;
            boolean bl6 = true;
            String string6 = "drawString";
            this.h = this.Y(string6, bl6, clazz8, classArray6);
            Class[] classArray7 = new Class[]{String.class, int.class, int.class, int.class, boolean.class};
            Class<Integer> clazz9 = int.class;
            boolean bl7 = true;
            String string7 = "renderString";
            this.L = this.Y(string7, bl7, clazz9, classArray7);
         } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{MappedClasses.DQ, MappedClasses.Yr, float.class, float.class, int.class};
            Class<Integer> clazz = int.class;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_243246_a";
            this.b = this.Y(string, bl, clazz, classArray);
            Class[] classArray8 = new Class[]{MappedClasses.DQ, MappedClasses.Yr, float.class, float.class, int.class};
            Class<Integer> clazz10 = int.class;
            boolean bl8 = Wrapper.isNativeAvailable;
            String string8 = "func_243248_b";
            this.X = this.Y(string8, bl8, clazz10, classArray8);
            if (ForgeVersion.MC_1_17.d()) {
               Class[] classArray9 = new Class[]{MappedClasses.DQ, String.class, float.class, float.class, int.class};
               Class<Integer> clazz11 = int.class;
               boolean bl9 = false;
               String string9 = "m_92750_";
               this.g = this.Y(string9, bl9, clazz11, classArray9);
            } else {
               Class[] classArray10 = new Class[]{MappedClasses.DQ, String.class, float.class, float.class, int.class};
               Class<Integer> clazz12 = int.class;
               boolean bl10 = Wrapper.isNativeAvailable;
               String string10 = "func_238405_a_";
               this.g = this.Y(string10, bl10, clazz12, classArray10);
            }

            Class[] classArray11 = new Class[]{MappedClasses.DQ, String.class, float.class, float.class, int.class};
            Class<Integer> clazz13 = int.class;
            boolean bl11 = Wrapper.isNativeAvailable;
            String string11 = "func_238421_b_";
            this.h = this.Y(string11, bl11, clazz13, classArray11);
            Class[] classArray12 = new Class[]{
               MappedClasses.Yr, float.class, float.class, int.class, boolean.class, MappedClasses.qr, MappedClasses.ZK, boolean.class, int.class, int.class
            };
            Class<Integer> clazz14 = int.class;
            boolean bl12 = Wrapper.isNativeAvailable;
            String string12 = "func_243247_a";
            this.W = this.Y(string12, bl12, clazz14, classArray12);
         } else {
            Class[] classArray = new Class[]{String.class, float.class, float.class, int.class, boolean.class};
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "renderString";
            this.L = this.Y(string, bl, clazz, classArray);
            Class[] classArray13 = new Class[]{String.class, float.class, float.class, int.class};
            Class<Integer> clazz15 = int.class;
            boolean bl13 = true;
            String string13 = "drawStringWithShadow";
            this.l = this.Y(string13, bl13, clazz15, classArray13);
            Class[] classArray14 = new Class[0];
            Class<Void> clazz16 = void.class;
            boolean bl14 = true;
            String string14 = "resetStyles";
            this.i = this.Y(string14, bl14, clazz16, classArray14);
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
               Class[] classArray15 = new Class[]{String.class, int.class, int.class, int.class};
               Class<Integer> clazz17 = int.class;
               boolean bl15 = true;
               String string15 = "drawString";
               this.h = this.Y(string15, bl15, clazz17, classArray15);
            } else {
               Class[] classArray16 = new Class[]{String.class, int.class, int.class, int.class};
               Class<Integer> clazz18 = int.class;
               boolean bl16 = Wrapper.isNativeAvailable;
               String string16 = "func_78276_b";
               this.h = this.Y(string16, bl16, clazz18, classArray16);
            }
         }

         if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray = new Class[]{String.class};
            Class<Integer> clazz = int.class;
            boolean bl = false;
            String string = "m_92895_";
            this.T = this.Y(string, bl, clazz, classArray);
         } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{String.class};
            Class<Integer> clazz = int.class;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_78256_a";
            this.T = this.Y(string, bl, clazz, classArray);
         } else {
            Class[] classArray = new Class[]{String.class};
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "getStringWidth";
            this.T = this.Y(string, bl, clazz, classArray);
         }
      }
   }

   public int e(Object object, String string) {
      return this.T.invokeInt(object, string);
   }

   public int C(Object object, Object object2, String string, int n, int n2, int n3, boolean bl) {
      if (ForgeVersion.MC_1_21_6.d()) {
         this.m.invokeVoid(object, object2, string, n, n2, n3, bl);
         return 0;
      } else {
         return this.m.invokeInt(object, object2, string, n, n2, n3, bl);
      }
   }

   public static int A(MFontRenderer mFontRenderer, Object object, Object object2, Object object3, float f, float f2, int n) {
      return mFontRenderer.b(object, object2, object3, f, f2, n);
   }

   private int A(Object object, Object object2, Object object3, float f, float f2, int n) {
      return this.X.invokeInt(object, object2, object3, f, f2, n);
   }

   public int M(Object object, Object object2, String string, float f, float f2, int n) {
      return this.g.invokeInt(object, object2, string, f, f2, n);
   }

   public static int q(MFontRenderer mFontRenderer, Object object, Object object2, Object object3, float f, float f2, int n) {
      return mFontRenderer.A(object, object2, object3, f, f2, n);
   }

   public int d(Object object, String string, int n, int n2, int n3) {
      return this.c.invokeInt(object, string, n, n2, n3);
   }

   public void J(Object object) {
      this.i.invokeVoidNoArgs(object);
   }
}
