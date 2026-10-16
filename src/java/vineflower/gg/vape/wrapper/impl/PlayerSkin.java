package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PlayerSkin extends Wrapper {
   public TextureAtlasSpriteInfo getBodySpriteInfo() {
      return new TextureAtlasSpriteInfo(vapeInstance.getMappingsMapperCompat().playerSkin.getBody(this.I));
   }

   public PlayerSkin(Object playerSkinHandle) {
      super(playerSkinHandle);
   }

   public ResourceLocation getTexture() {
      return ForgeVersion.MC_1_21_10.d()
         ? this.getBodySpriteInfo().getTexturePath()
         : new ResourceLocation(vapeInstance.getMappingsMapperCompat().playerSkin.getTexture(this.I));
   }
}
