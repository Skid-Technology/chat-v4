package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MVoxelShape extends Mapping {
   private final MappingMethod isEmptyMethod;
   private final MappingMethod getBoundingBoxMethod = this.Y("getBoundingBox", true, MappedClasses.uk);

   public MVoxelShape() {
      super(MappedClasses.la);
      this.isEmptyMethod = this.methodBuilder("isEmpty", boolean.class).buildMethod();
   }

   public boolean isEmpty(Object shapeHandle) {
      return this.isEmptyMethod.invokeBoolean(shapeHandle);
   }

   public Object getBoundingBox(Object shapeHandle) {
      return this.getBoundingBoxMethod.invokeObject(shapeHandle);
   }
}
