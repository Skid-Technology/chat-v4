package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MWorldInfo extends Mapping {
   private MappingField worldTimeField;
   private static String[] constructorState;
   public MappingMethod getWorldTimeMethod;

   public long getWorldTime(Object worldInfo) {
      return this.worldTimeField.getLong(worldInfo);
   }

   public static void setWorldInfoConstructorState(String[] state) {
      constructorState = state;
   }

   public static String[] getWorldInfoConstructorState() {
      return constructorState;
   }

   public MWorldInfo() {
      this(getWorldInfoConstructorState());
   }

   private MWorldInfo(String[] constructorState) {
      super(MappedClasses.WORLD_INFO);
      Class<Long> fieldType = long.class;
      boolean remapField = true;
      String fieldName = "worldTime";
      this.worldTimeField = this.J(fieldName, remapField, fieldType);
      Class[] parameterTypes = new Class[0];
      Class<Long> returnType = long.class;
      boolean remapMethod = true;
      String methodName = "getWorldTime";
      this.getWorldTimeMethod = this.Y(methodName, remapMethod, returnType, parameterTypes);
      if (GuiComponent.getLegacyComponentState() == null) {
         setWorldInfoConstructorState(new String[1]);
      }
   }

   static {
      setWorldInfoConstructorState(null);
   }
}
