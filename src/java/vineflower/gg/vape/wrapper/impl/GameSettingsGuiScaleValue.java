package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GameSettingsGuiScaleValue extends Wrapper {
   public static int getAntialiasingLevel() {
      return vapeInstance.getMappingsMapperCompat().gameSettingsGuiScaleValue == null
         ? 0
         : vapeInstance.getMappingsMapperCompat().gameSettingsGuiScaleValue.getAntialiasingLevel();
   }

   public GameSettingsGuiScaleValue(Object valueHandle) {
      super(valueHandle);
   }
}
