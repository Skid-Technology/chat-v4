package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MObjectToIntMapEntry extends Mapping {
   private MappingMethod getIntValueMethod;
   private static String constructorStateMarker;
   private MappingMethod getKeyMethod;

   public static String getConstructorStateMarker() {
      return constructorStateMarker;
   }

   public MObjectToIntMapEntry() {
      this(getConstructorStateMarker());
   }

   private MObjectToIntMapEntry(String constructorStateMarker) {
      super(MappedClasses.OBJECT_TO_INT_MAP_ENTRY);
      Class[] keyParameterTypes = new Class[0];
      Class<Object> keyReturnType = Object.class;
      boolean remapKeyMethod = false;
      String keyMethodName = "getKey";
      this.getKeyMethod = this.Y(keyMethodName, remapKeyMethod, keyReturnType, keyParameterTypes);
      Class[] valueParameterTypes = new Class[0];
      Class<Integer> valueReturnType = int.class;
      boolean remapValueMethod = false;
      String valueMethodName = "getIntValue";
      this.getIntValueMethod = this.Y(valueMethodName, remapValueMethod, valueReturnType, valueParameterTypes);
   }

   public static void setConstructorStateMarker(String marker) {
      constructorStateMarker = marker;
   }

   public int getIntValue(Object entry) {
      return this.getIntValueMethod.invokeInt(entry);
   }

   public Object getKey(Object entry) {
      return this.getKeyMethod.invokeObject(entry);
   }

   static {
      setConstructorStateMarker(null);
   }
}
