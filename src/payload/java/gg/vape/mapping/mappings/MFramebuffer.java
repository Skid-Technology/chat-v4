package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MFramebuffer extends Mapping {
   private MappingField l;
   private final MappingMethod r;
   private final MappingMethod f;
   private final MappingMethod P;
   private final MappingMethod F;
   private final MappingMethod H;
   private final MappingMethod S;
   private final MappingMethod V;
   private final MappingMethod W;

   public static void D(MFramebuffer mFramebuffer, Object object) {
      mFramebuffer.x(object);
   }

   public static void bindFramebuffer(MFramebuffer mFramebuffer, Object object, boolean bl) {
      mFramebuffer.bindFramebuffer(object, bl);
   }

   public static void G(MFramebuffer mFramebuffer, Object object, int n, int n2) {
      mFramebuffer.E(object, n, n2);
   }

   public static void l(MFramebuffer mFramebuffer, Object object) {
      mFramebuffer.A(object);
   }

   public static void e(MFramebuffer mFramebuffer, Object object) {
      mFramebuffer.I(object);
   }

   private void bindFramebuffer(Object object, boolean bl) {
      if (this.r != null) {
         this.r.invokeVoid(object, bl);
      }
   }

   public static void m(MFramebuffer mFramebuffer, Object object) {
      mFramebuffer.Q(object);
   }

   private void Q(Object object) {
      if (this.P != null) {
         this.P.invokeVoidNoArgs(object);
      }
   }

   private void k(Object object, int n, int n2, boolean bl) {
      if (this.f != null) {
         if (ForgeVersion.MC_1_21_0.d()) {
            this.f.invokeVoid(object, n, n2);
         } else {
            this.f.invokeVoid(object, n, n2, bl);
         }
      }
   }

   public static int L(MFramebuffer mFramebuffer, Object object) {
      return mFramebuffer.r(object);
   }

   public MFramebuffer() {
      this(MShaderGroup.V());
   }

   private MFramebuffer(String[] stringArray) {
      super(MappedClasses.ll);
      if (ForgeVersion.MC_1_21_4.d()) {
         this.S = null;
         this.V = null;
         this.f = null;
         this.r = null;
         this.P = null;
         this.F = null;
         this.W = null;
         this.H = null;
      } else if (ForgeVersion.MC_1_16_5.d()) {
         if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray = new Class[]{int.class, int.class};
            Class<Void> clazz = void.class;
            boolean bl = false;
            String string = "<init>";
            Class clazz2 = MappedClasses.zA;
            this.S = this.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
         } else {
            Class[] classArray = new Class[]{int.class, int.class, boolean.class, boolean.class};
            Class<Void> clazz = void.class;
            boolean bl = false;
            String string = "<init>";
            this.S = this.Y(string, bl, clazz, classArray);
         }

         Class[] classArray = new Class[]{int.class, int.class};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "createBindFramebuffers";
         Class clazz3 = MappedClasses.Fo;
         this.V = this.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
         Class[] classArray2 = new Class[]{int.class, int.class, boolean.class};
         Class<Void> clazz4 = void.class;
         boolean bl2 = true;
         String string2 = "createBuffers";
         this.f = this.Y(string2, bl2, clazz4, classArray2);
         Class[] classArray3 = new Class[]{boolean.class};
         Class<Void> clazz5 = void.class;
         boolean bl3 = true;
         String string3 = "bindFramebuffer";
         this.r = this.Y(string3, bl3, clazz5, classArray3);
         Class[] classArray4 = new Class[0];
         Class<Void> clazz6 = void.class;
         boolean bl4 = true;
         String string4 = "unbindFramebuffer";
         this.P = this.Y(string4, bl4, clazz6, classArray4);
         Class[] classArray5 = new Class[0];
         Class<Void> clazz7 = void.class;
         boolean bl5 = true;
         String string5 = "bindFramebufferTexture";
         this.F = this.Y(string5, bl5, clazz7, classArray5);
         Class[] classArray6 = new Class[0];
         Class<Void> clazz8 = void.class;
         boolean bl6 = true;
         String string6 = "unbindFramebufferTexture";
         this.W = this.Y(string6, bl6, clazz8, classArray6);
         Class[] classArray7 = new Class[0];
         Class<Void> clazz9 = void.class;
         boolean bl7 = true;
         String string7 = "deleteFramebuffer";
         this.H = this.Y(string7, bl7, clazz9, classArray7);
      } else {
         Class[] classArray = new Class[]{int.class, int.class, boolean.class};
         Class<Void> clazz = void.class;
         boolean bl = false;
         String string = "<init>";
         this.S = this.Y(string, bl, clazz, classArray);
         Class[] classArray8 = new Class[]{int.class, int.class};
         Class<Void> clazz10 = void.class;
         boolean bl8 = true;
         String string8 = "createBindFramebuffer";
         this.V = this.Y(string8, bl8, clazz10, classArray8);
         Class[] classArray9 = new Class[]{int.class, int.class};
         Class<Void> clazz11 = void.class;
         boolean bl9 = true;
         String string9 = "createFramebuffer";
         this.f = this.Y(string9, bl9, clazz11, classArray9);
         Class[] classArray10 = new Class[]{boolean.class};
         Class<Void> clazz12 = void.class;
         boolean bl10 = true;
         String string10 = "bindFramebuffer";
         this.r = this.Y(string10, bl10, clazz12, classArray10);
         Class[] classArray11 = new Class[0];
         Class<Void> clazz13 = void.class;
         boolean bl11 = true;
         String string11 = "unbindFramebuffer";
         this.P = this.Y(string11, bl11, clazz13, classArray11);
         Class[] classArray12 = new Class[0];
         Class<Void> clazz14 = void.class;
         boolean bl12 = true;
         String string12 = "bindFramebufferTexture";
         this.F = this.Y(string12, bl12, clazz14, classArray12);
         Class[] classArray13 = new Class[0];
         Class<Void> clazz15 = void.class;
         boolean bl13 = true;
         String string13 = "unbindFramebufferTexture";
         this.W = this.Y(string13, bl13, clazz15, classArray13);
         Class[] classArray14 = new Class[0];
         Class<Void> clazz16 = void.class;
         boolean bl14 = true;
         String string14 = "deleteFramebuffer";
         this.H = this.Y(string14, bl14, clazz16, classArray14);
      }

      if (ForgeVersion.MC_1_21_6.v()) {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "depthBuffer";
         this.l = this.J(string, bl, clazz);
      }
   }

   private void E(Object object, int n, int n2) {
      if (this.f != null) {
         this.f.invokeVoid(object, n, n2);
      }
   }

   public static Object create(MFramebuffer mFramebuffer, int n, int n2, boolean bl) {
      return mFramebuffer.create(n, n2, bl);
   }

   private void x(Object object) {
      if (this.W != null) {
         this.W.invokeVoidNoArgs(object);
      }
   }

   private Object create(int n, int n2, boolean bl) {
      if (this.S == null) {
         return null;
      } else if (ForgeVersion.MC_1_17.d()) {
         return this.S.newInstance(n, n2);
      } else {
         return ForgeVersion.MC_1_16_5.d() ? this.S.newInstance(n, n2, bl, false) : this.S.newInstance(n, n2, bl);
      }
   }

   private int r(Object object) {
      return this.l.getInt(object);
   }

   private void A(Object object) {
      if (this.F != null) {
         this.F.invokeVoidNoArgs(object);
      }
   }

   private void f(Object object, int n, int n2) {
      if (this.V != null) {
         this.V.invokeVoid(object, n, n2);
      }
   }

   public static void w(MFramebuffer mFramebuffer, Object object, int n, int n2, boolean bl) {
      mFramebuffer.k(object, n, n2, bl);
   }

   public static void depthBuffer(MFramebuffer mFramebuffer, Object object, int n) {
      mFramebuffer.depthBuffer(object, n);
   }

   private void I(Object object) {
      if (this.H != null) {
         this.H.invokeVoidNoArgs(object);
      }
   }

   public static void f(MFramebuffer mFramebuffer, Object object, int n, int n2) {
      mFramebuffer.f(object, n, n2);
   }

   private void depthBuffer(Object object, int n) {
      this.l.setInt(object, n);
   }
}
