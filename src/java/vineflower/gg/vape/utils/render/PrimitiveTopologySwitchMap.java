package gg.vape.utils.render;

public class PrimitiveTopologySwitchMap {
   public static final int[] TOPOLOGY_SWITCH_MAP = new int[PrimitiveTopology.values().length];

   PrimitiveTopologySwitchMap() {
   }

   static {
      try {
         TOPOLOGY_SWITCH_MAP[PrimitiveTopology.LINES.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         TOPOLOGY_SWITCH_MAP[PrimitiveTopology.QUADS.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         TOPOLOGY_SWITCH_MAP[PrimitiveTopology.TRIANGLES.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         TOPOLOGY_SWITCH_MAP[PrimitiveTopology.LINES_LOOP.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }
   }
}
