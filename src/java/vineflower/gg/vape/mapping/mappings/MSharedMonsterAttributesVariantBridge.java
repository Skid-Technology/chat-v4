package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSharedMonsterAttributesVariantBridge extends Mapping {
   private final MappingField noneField = this.registerStaticField("NONE", true, MappedClasses.Vx);
   private final MappingField allField = this.registerStaticField("ALL", true, MappedClasses.Vx);

   public MSharedMonsterAttributesVariantBridge() {
      super(MappedClasses.Vx);
   }

   public Object getNone() {
      return this.noneField.getObject(null);
   }

   public Object getAll() {
      return this.allField.getObject(null);
   }
}
