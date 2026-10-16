package gg.vape.wrapper.impl;

public class EmptyDataComponentMap extends DataComponentMap {
   public EmptyDataComponentMap(Object componentMapHandle) {
      super(componentMapHandle);
   }

   public static EmptyDataComponentMap create() {
      Object componentMapHandle = vapeInstance.getMappingsMapperCompat().emptyDataComponentMap.create(DataComponentMap.u().getObject());
      return new EmptyDataComponentMap(componentMapHandle);
   }
}
