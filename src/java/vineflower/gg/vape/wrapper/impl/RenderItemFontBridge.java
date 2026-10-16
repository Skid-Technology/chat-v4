package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderTypeBuffer;
import gg.vape.wrapper.Wrapper;

public class RenderItemFontBridge extends Wrapper {
   public static RenderItemFontBridge V(WorldRenderer wg_12) {
      return ForgeVersion.MC_1_21_0.d() ? null : new RenderItemFontBridge(MRenderTypeBuffer.t(vapeInstance.getMappingsMapperCompat().c, wg_12.getObject()));
   }

   public void X() {
      MRenderTypeBuffer.Q(vapeInstance.getMappingsMapperCompat().c, this.I);
   }

   public RenderItemFontBridge(Object object) {
      super(object);
   }

   public void q() {
      MRenderTypeBuffer.n(vapeInstance.getMappingsMapperCompat().c, this.I);
   }
}
