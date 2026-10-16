package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSoundEvent extends Mapping {
   private static final String NAME_FIELD_NAME = "name";
   private final MappingField nameField = this.J("name", true, MappedClasses.zC);

   public MSoundEvent() {
      super(MappedClasses.Y6);
   }

   public Object getName(Object soundEvent) {
      return this.nameField.getObject(soundEvent);
   }
}
