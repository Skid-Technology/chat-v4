package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class InputMappingsBridge extends Wrapper {
   public InputMappingsBridge(Object object) {
      super(object);
   }

   public static InputMappingsInput x(int keyCode, int scanCode) {
      if (ForgeVersion.MC_1_21_10.d()) {
         InputMappingsInputFactory keyEvent = InputMappingsInputFactory.create(keyCode, scanCode, 0);
         return new InputMappingsInput(vapeInstance.getMappingsMapperCompat().CE.getInputByCode(keyEvent.getObject()));
      } else {
         return new InputMappingsInput(vapeInstance.getMappingsMapperCompat().CE.getInputByCode(keyCode, scanCode));
      }
   }
}
