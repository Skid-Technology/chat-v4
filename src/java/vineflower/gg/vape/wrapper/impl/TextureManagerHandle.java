package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class TextureManagerHandle extends Wrapper {
   public TextureObjectHandle getColorTexture() {
      return new TextureObjectHandle(vapeInstance.getMappingsMapperCompat().renderTarget.getColorTexture(this.I));
   }

   public int getColorTextureId() {
      TextureObjectHandle colorTexture = this.getColorTexture();
      return colorTexture.isNull() ? -1 : colorTexture.getId();
   }

   public TextureObjectHandle getDepthTexture() {
      return new TextureObjectHandle(vapeInstance.getMappingsMapperCompat().renderTarget.getDepthTexture(this.I));
   }

   public TextureManagerHandle(Object object) {
      super(object);
   }

   public int getDepthTextureId() {
      TextureObjectHandle depthTexture = this.getDepthTexture();
      return depthTexture.isNull() ? -1 : depthTexture.getId();
   }
}
