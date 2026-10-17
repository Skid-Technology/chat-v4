package gg.vape.wrapper.impl;

public class BlockLiquid extends Block {
   public BlockLiquid(Object object) {
      super(object);
   }

   public static float getLiquidHeightPercent(int n) {
      if (n >= 8) {
         n = 0;
      }

      return (n + 1) / 9.0F;
   }
}
