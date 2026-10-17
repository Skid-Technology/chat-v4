package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MVec3I;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;

public class Vec3i extends Wrapper {
   private static GuiComponent[] guiComponents;

   public static Vec3i create(int x, int y, int z) {
      return new Vec3i(MVec3I.getConstructor(vapeInstance.getMappings().vec3i).newInstance(x, y, z));
   }

   public Vec3i(Object handle) {
      super(handle);
   }

   public static void setGuiComponents(GuiComponent[] components) {
      guiComponents = components;
   }

   public int getY() {
      return vapeInstance.getMappings().vec3i.getY(this.I);
   }

   public int getZ() {
      return vapeInstance.getMappings().vec3i.getZ(this.I);
   }

   public static GuiComponent[] getGuiComponents() {
      return guiComponents;
   }

   public int getX() {
      return vapeInstance.getMappings().vec3i.getX(this.I);
   }

   static {
      if (getGuiComponents() != null) {
         setGuiComponents(new GuiComponent[1]);
      }
   }
}
