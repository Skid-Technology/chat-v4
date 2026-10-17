package gg.vape.wrapper.impl;

public class RenderPlayer extends RenderLivingBase<EntityPlayer> {
   public RenderPlayer(Object renderPlayerHandle) {
      super(renderPlayerHandle);
   }

   public ModelBiped getModelBipedMain() {
      return new ModelBiped(vapeInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
   }

   public PlayerModel getMainModel() {
      return new PlayerModel(vapeInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
   }
}
