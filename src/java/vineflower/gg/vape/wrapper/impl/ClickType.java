package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MClickType;
import gg.vape.wrapper.Wrapper;

public class ClickType extends Wrapper {
   public static ClickType[] VALUES = new ClickType[]{pickup(), quickMove(), swap(), cloneStack(), throwStack(), quickCraft(), pickupAll()};

   public static ClickType quickCraft() {
      return new ClickType(MClickType.getQuickCraft(vapeInstance.getMappingsMapperCompat().clickType));
   }

   public ClickType(Object object) {
      super(object);
   }

   public static ClickType pickup() {
      return new ClickType(MClickType.getPickup(vapeInstance.getMappingsMapperCompat().clickType));
   }

   public static ClickType cloneStack() {
      return new ClickType(MClickType.getClone(vapeInstance.getMappingsMapperCompat().clickType));
   }

   public static ClickType quickMove() {
      return new ClickType(MClickType.getQuickMove(vapeInstance.getMappingsMapperCompat().clickType));
   }

   public static ClickType pickupAll() {
      return new ClickType(MClickType.getPickupAll(vapeInstance.getMappingsMapperCompat().clickType));
   }

   public static ClickType throwStack() {
      return new ClickType(MClickType.getThrow(vapeInstance.getMappingsMapperCompat().clickType));
   }

   public static ClickType swap() {
      return new ClickType(MClickType.getSwap(vapeInstance.getMappingsMapperCompat().clickType));
   }
}
