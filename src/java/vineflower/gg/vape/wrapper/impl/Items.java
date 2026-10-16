package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItems;
import gg.vape.wrapper.Wrapper;

public class Items extends Wrapper {
   public static Items perspective() {
      return new Items(MItems.getPerspective(vapeInstance.getMappingsMapperCompat().projectionType));
   }

   public static Items orthographic() {
      return new Items(MItems.getOrthographic(vapeInstance.getMappingsMapperCompat().projectionType));
   }

   public Items(Object handle) {
      super(handle);
   }
}
