package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MStyle extends Mapping {
   private MappingField EMPTY;
   private MappingMethod applyLegacyFormat;

   public MStyle() {
      super(MappedClasses.Va);
      if (ForgeVersion.MC_26_2.d()) {
         this.EMPTY = this.registerStaticField("EMPTY", true, MappedClasses.Va);
         this.applyLegacyFormat = this.Y("applyLegacyFormat", true, MappedClasses.Va, MappedClasses.l5);
      }
   }

   public Object getEmpty() {
      return this.EMPTY == null ? null : this.EMPTY.getObject(null);
   }

   public Object applyLegacyFormat(Object style, Object chatFormatting) {
      return this.applyLegacyFormat == null ? style : this.applyLegacyFormat.invokeObject(style, chatFormatting);
   }
}
