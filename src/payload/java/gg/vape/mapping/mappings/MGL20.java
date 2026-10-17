package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MGL20 extends Mapping {
   private MappingMethod v;
   private MappingMethod h;

   private void I(int n, int n2, IntBuffer intBuffer) {
      this.v.invokeVoid(null, n, n2, intBuffer);
   }

   public MGL20() {
      this(MGlStateManagerTexGenState.z());
   }

   private MGL20(boolean bl) {
      super(MappedClasses.D4);
      if (bl) {
         if (!GuiRenderPrimitives.V()) {
            Class[] classArray = new Class[]{int.class, int.class, IntBuffer.class};
            Class<Void> clazz = void.class;
            boolean bl2 = false;
            String string = "glGetProgram";
            this.v = this.registerStaticMethod(string, bl2, clazz, classArray);
            Class[] classArray2 = new Class[]{int.class, boolean.class, FloatBuffer.class};
            Class<Void> clazz2 = void.class;
            boolean bl3 = false;
            String string2 = "glUniformMatrix4";
            this.h = this.registerStaticMethod(string2, bl3, clazz2, classArray2);
         } else {
            Class[] classArray = new Class[]{int.class, int.class, IntBuffer.class};
            Class<Void> clazz = void.class;
            boolean bl4 = false;
            String string = "glGetProgramiv";
            this.v = this.registerStaticMethod(string, bl4, clazz, classArray);
            Class[] classArray3 = new Class[]{int.class, boolean.class, FloatBuffer.class};
            Class<Void> clazz3 = void.class;
            boolean bl5 = false;
            String string3 = "glUniformMatrix4fv";
            this.h = this.registerStaticMethod(string3, bl5, clazz3, classArray3);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            MGlStateManagerTexGenState.d(false);
         }
      } else {
         Class[] classArray = new Class[]{int.class, boolean.class, FloatBuffer.class};
         Class<Void> clazz = void.class;
         boolean bl6 = false;
         String string = "glUniformMatrix4fv";
         this.h = this.registerStaticMethod(string, bl6, clazz, classArray);
         if (GuiComponent.getLegacyComponentState() == null) {
            MGlStateManagerTexGenState.d(true);
         }
      }
   }

   public static void X(MGL20 mGL20, int n, boolean bl, FloatBuffer floatBuffer) {
      mGL20.i(n, bl, floatBuffer);
   }

   private void i(int n, boolean bl, FloatBuffer floatBuffer) {
      this.h.invokeVoid(null, n, bl, floatBuffer);
   }

   public static void P(MGL20 mGL20, int n, int n2, IntBuffer intBuffer) {
      mGL20.I(n, n2, intBuffer);
   }
}
