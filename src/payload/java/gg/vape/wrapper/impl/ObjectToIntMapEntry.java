package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ObjectToIntMapEntry extends Wrapper {
   public int getIntValue() {
      return vapeInstance.getMappings().objectToIntMapEntry.getIntValue(this.I);
   }

   public ObjectToIntMapEntry(Object wrappedObject) {
      super(wrappedObject);
   }

   public Object getKey() {
      return vapeInstance.getMappings().objectToIntMapEntry.getKey(this.I);
   }
}
