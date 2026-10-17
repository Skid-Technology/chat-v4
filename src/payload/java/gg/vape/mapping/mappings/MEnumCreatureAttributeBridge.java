package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEnumCreatureAttributeBridge extends Mapping {
   private static final String UNDEFINED_FIELD_NAME = "UNDEFINED";
   private final MappingField undefinedField = this.registerStaticField("UNDEFINED", true, MappedClasses.O);

   public MEnumCreatureAttributeBridge() {
      super(MappedClasses.O);
   }

   public Object getUndefined() {
      return this.undefinedField.getObject(null);
   }
}
