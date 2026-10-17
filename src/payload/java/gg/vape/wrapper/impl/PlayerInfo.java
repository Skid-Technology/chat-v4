package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MPlayerInfo;
import gg.vape.wrapper.Wrapper;

public class PlayerInfo extends Wrapper {
   public ITextComponent R() {
      return new ITextComponent(MPlayerInfo.l(vapeInstance.getMappingsMapperCompat().CG, this.getObject()));
   }

   public ScorePlayerTeam X() {
      return new ScorePlayerTeam(MPlayerInfo.Z(vapeInstance.getMappingsMapperCompat().CG, this.getObject()));
   }

   public ResourceLocation i() {
      Object object = MPlayerInfo.J(vapeInstance.getMappingsMapperCompat().CG, this.I);
      if (object == null) {
         return null;
      } else {
         return ForgeVersion.MC_1_20_6.d() ? new PlayerSkin(object).getTexture() : new ResourceLocation(object);
      }
   }

   public GameProfile v() {
      return new GameProfile(MPlayerInfo.W(vapeInstance.getMappingsMapperCompat().CG, this.I));
   }

   public PlayerInfo(Object object) {
      super(object);
   }

   public int z() {
      return MPlayerInfo.H(vapeInstance.getMappingsMapperCompat().CG, this.I);
   }
}
