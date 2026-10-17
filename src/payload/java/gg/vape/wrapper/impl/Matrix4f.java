package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMatrix4f;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.wrapper.Wrapper;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Matrix4f extends Wrapper {
   public Matrix4f(Object object) {
      super(object);
   }

   public Matrix4f c(FloatBuffer floatBuffer) {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new Matrix4f(MMatrix4f.P(vapeInstance.getMappings().qJ, floatBuffer));
      } else {
         MMatrix4f.d(vapeInstance.getMappings().qJ, this.I, floatBuffer);
         return null;
      }
   }

   public void a(Matrix4f matrix4f) {
      MMatrix4f.l(vapeInstance.getMappings().qJ, this.I, matrix4f);
   }

   public RenderMatrix4f m$src$Lgg_vape_utils_render_RenderMatrix4f_$1hodrum() {
      FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
      this.M(floatBuffer);
      float[] fArray = new float[16];
      floatBuffer.get(fArray);
      return new RenderMatrix4f(fArray);
   }

   public static Matrix4f G() {
      return new Matrix4f(MMatrix4f.E(vapeInstance.getMappings().qJ));
   }

   public void M(FloatBuffer floatBuffer) {
      MMatrix4f.M(vapeInstance.getMappings().qJ, this.I, floatBuffer);
   }

   public Matrix4f K(float f, float f2, float f3) {
      return new Matrix4f(MMatrix4f.O(vapeInstance.getMappings().qJ, this.I, f, f2, f3));
   }
}
