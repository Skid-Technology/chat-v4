package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class TextureAtlasSpriteInfo extends Wrapper {
   public TextureAtlasSpriteInfo(Object handle) {
      super(handle);
   }

   public ResourceLocation getTexturePath() {
      return new ResourceLocation(vapeInstance.getMappingsMapperCompat().textureAtlasSpriteInfo.getTexturePath(this.I));
   }
}
