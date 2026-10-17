package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEntitySize extends Mapping {
   private final MappingField widthField = this.J("width", true, float.class);
   private final MappingField heightField = this.J("height", true, float.class);

   public MEntitySize() {
      super(MappedClasses.Ve);
   }

   public float getWidth(Object sizeHandle) {
      return this.widthField.getFloat(sizeHandle);
   }

   public float getHeight(Object sizeHandle) {
      return this.heightField.getFloat(sizeHandle);
   }
}
