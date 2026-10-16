package gg.vape.unmap;

import java.util.HashMap;

public class PropertyContainer {
   public static PropertyKey<Boolean> LEGACY_FLAG = new PropertyContainerBooleanKeyC();
   public static PropertyKey<Boolean> BETA_BADGE = new PropertyContainerBooleanKeyB();
   private static String legacyLabel;
   public static PropertyKey<Boolean> NEW_BADGE = new PropertyContainerBooleanKeyA();
   private HashMap<PropertyKey<?>, Object> properties = new HashMap<>();

   public <T> void putProperty(PropertyKey<T> propertyKey, T value) {
      this.properties.put(propertyKey, value);
   }

   public <T> T getProperty(PropertyKey<T> propertyKey) {
      return (T)(!this.properties.containsKey(propertyKey) ? propertyKey.getDefaultValue() : this.properties.get(propertyKey));
   }

   public static String getLabel() {
      return legacyLabel;
   }

   public static void setLabel(String label) {
      legacyLabel = label;
   }

   static {
      setLabel("m4RdLb");
   }
}
