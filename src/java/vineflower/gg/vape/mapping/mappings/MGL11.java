package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MGL11 extends Mapping {
   private MappingMethod Q;
   private MappingMethod H;
   public MappingMethod S;
   private MappingMethod j;

   private void W(int n, int n2, int n3, FloatBuffer floatBuffer) {
      if (!GuiRenderPrimitives.V()) {
         this.S.invokeVoid(null, n, n3, floatBuffer);
      } else {
         this.S.invokeVoid(null, n, n2, n3, floatBuffer);
      }
   }

   public static void H(MGL11 mGL11, int n, int n2, int n3, FloatBuffer floatBuffer) {
      mGL11.W(n, n2, n3, floatBuffer);
   }

   private void m(int n, IntBuffer intBuffer) {
      this.j.invokeVoid(null, n, intBuffer);
   }

   public static void Y(MGL11 mGL11, int n, FloatBuffer floatBuffer) {
      mGL11.G(n, floatBuffer);
   }

   public static void X(MGL11 mGL11, int n, IntBuffer intBuffer) {
      mGL11.m(n, intBuffer);
   }

   public static void J(MGL11 mGL11, FloatBuffer floatBuffer) {
      mGL11.s(floatBuffer);
   }

   private void G(int n, FloatBuffer floatBuffer) {
      this.Q.invokeVoid(null, n, floatBuffer);
   }

   public MGL11() {
      this(MGlStateManagerTexGenState.F());
   }

   private MGL11(boolean bl) {
      super(MappedClasses.J);
      if (bl) {
         Class[] classArray = new Class[]{int.class, FloatBuffer.class};
         Class<Void> clazz = void.class;
         boolean bl2 = false;
         String string = "glGetFloatv";
         this.Q = this.registerStaticMethod(string, bl2, clazz, classArray);
      } else {
         if (!GuiRenderPrimitives.V()) {
            Class[] classArray = new Class[]{int.class, IntBuffer.class};
            Class<Void> clazz = void.class;
            boolean bl3 = false;
            String string = "glGetInteger";
            this.j = this.registerStaticMethod(string, bl3, clazz, classArray);
            Class[] classArray2 = new Class[]{int.class, int.class, FloatBuffer.class};
            Class<Void> clazz2 = void.class;
            boolean bl4 = false;
            String string2 = "glVertexPointer";
            this.S = this.registerStaticMethod(string2, bl4, clazz2, classArray2);
            Class[] classArray3 = new Class[]{FloatBuffer.class};
            Class<Void> clazz3 = void.class;
            boolean bl5 = false;
            String string3 = "glMultMatrix";
            this.H = this.registerStaticMethod(string3, bl5, clazz3, classArray3);
            Class[] classArray4 = new Class[]{int.class, FloatBuffer.class};
            Class<Void> clazz4 = void.class;
            boolean bl6 = false;
            String string4 = "glGetFloat";
            this.Q = this.registerStaticMethod(string4, bl6, clazz4, classArray4);
         } else {
            Class[] classArray = new Class[]{int.class, IntBuffer.class};
            Class<Void> clazz = void.class;
            boolean bl7 = false;
            String string = "glGetIntegerv";
            this.j = this.registerStaticMethod(string, bl7, clazz, classArray);
            Class[] classArray5 = new Class[]{int.class, int.class, int.class, FloatBuffer.class};
            Class<Void> clazz5 = void.class;
            boolean bl8 = false;
            String string5 = "glVertexPointer";
            this.S = this.registerStaticMethod(string5, bl8, clazz5, classArray5);
            Class[] classArray6 = new Class[]{FloatBuffer.class};
            Class<Void> clazz6 = void.class;
            boolean bl9 = false;
            String string6 = "glMultMatrixf";
            this.H = this.registerStaticMethod(string6, bl9, clazz6, classArray6);
            Class[] classArray7 = new Class[]{int.class, FloatBuffer.class};
            Class<Void> clazz7 = void.class;
            boolean bl10 = false;
            String string7 = "glGetFloatv";
            this.Q = this.registerStaticMethod(string7, bl10, clazz7, classArray7);
         }
      }
   }

   private void s(FloatBuffer floatBuffer) {
      this.H.invokeVoid(null, floatBuffer);
   }
}
