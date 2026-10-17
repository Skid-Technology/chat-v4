package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Screen extends Wrapper {
   private static int[] controlFlowState;

   public static void setControlFlowState(int[] state) {
      controlFlowState = state;
   }

   public static int[] getControlFlowState() {
      return controlFlowState;
   }

   public Screen(Object screenHandle) {
      super(screenHandle);
   }

   public static ResourceLocation getMobEffectSprite(Holder holder) {
      Object spriteLocationHandle = vapeInstance.getMappings().screen.getMobEffectSprite(holder.getObject());
      return new ResourceLocation(spriteLocationHandle);
   }

   static {
      if (getControlFlowState() != null) {
         setControlFlowState(new int[1]);
      }
   }
}
