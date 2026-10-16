package gg.vape.wrapper.impl;

import gg.vape.module.render.freecam.FreecamPlayerBridge;
import gg.vape.ui.click.component.GuiComponent;

public class AbstractClientPlayer extends EntityPlayer {
   private static GuiComponent[] o;

   public static void R(GuiComponent[] guiComponentArray) {
      o = guiComponentArray;
   }

   @Override
   public FreecamPlayerBridge getFreecamPlayerBridge() {
      return new FreecamPlayerBridge(vapeInstance.getMappings().Dk.getClientAvatarState(this.I));
   }

   public AbstractClientPlayer(Object object) {
      super(object);
   }

   public static GuiComponent[] I() {
      return o;
   }

   public ResourceLocation O() {
      return ForgeVersion.MC_1_20_6.d()
         ? new PlayerSkin(vapeInstance.getMappings().Dk.getLocationSkin(this.I)).getTexture()
         : new ResourceLocation(vapeInstance.getMappings().Dk.getLocationSkin(this.I));
   }

   static {
      if (I() != null) {
         R(new GuiComponent[5]);
      }
   }
}
