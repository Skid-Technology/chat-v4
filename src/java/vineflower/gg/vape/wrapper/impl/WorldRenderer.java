package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MWorldRenderer;
import gg.vape.wrapper.Wrapper;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class WorldRenderer extends Wrapper {
   public WorldRenderer(Object object) {
      super(object);
   }

   public void Q(boolean bl) {
      MWorldRenderer.X(vapeInstance.getMappings().qZ, this.I, bl);
   }

   public IntBuffer O() {
      if (ForgeVersion.MC_1_21_0.d()) {
         Vape.notifyNativeStackTrace();
         return null;
      } else if (ForgeVersion.MC_1_16_5.d()) {
         ByteBuffer byteBuffer = (ByteBuffer)MWorldRenderer.t(vapeInstance.getMappings().qZ, this.I);
         return byteBuffer.asIntBuffer();
      } else {
         return (IntBuffer)MWorldRenderer.v(vapeInstance.getMappings().qZ, this.I);
      }
   }

   public int o(int n) {
      if (ForgeVersion.MC_1_16_5.d()) {
         Vape.notifyNativeStackTrace();
         return -1;
      } else {
         return MWorldRenderer.G(vapeInstance.getMappings().qZ, this.I, n);
      }
   }
}
