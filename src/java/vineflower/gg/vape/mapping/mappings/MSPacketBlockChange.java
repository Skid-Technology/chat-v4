package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketBlockChange extends Mapping {
   private MappingMethod getBlockPositionMethod;
   private MappingField legacyXField;
   private MappingField legacyBlockStateField;
   private MappingField legacyZField;
   private MappingMethod getBlockStateMethod;
   private MappingField legacyYField;

   public Object getLegacyBlockState(Object packet) {
      return this.legacyBlockStateField.getObject(packet);
   }

   public int getLegacyX(Object packet) {
      return this.legacyXField.getInt(packet);
   }

   public int getLegacyY(Object packet) {
      return this.legacyYField.getInt(packet);
   }

   public int getLegacyZ(Object packet) {
      return this.legacyZField.getInt(packet);
   }

   public Object getBlockState(Object packet) {
      return this.getBlockStateMethod.invokeObject(packet);
   }

   public MSPacketBlockChange() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MSPacketBlockChange(int[] controlFlowState) {
      super(MappedClasses.DD);
      if (controlFlowState != null) {
         if (ForgeVersion.MC_1_7_10.Y()) {
            this.getBlockStateMethod = this.methodBuilder("getBlockState", MappedClasses.Vv)
               .setTypeForVersion(ForgeVersion.MC_1_16_5.n(), MappedClasses.Zl)
               .buildMethod();
            this.getBlockPositionMethod = this.methodBuilder("getBlockPosition", MappedClasses.lf)
               .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getPos")
               .buildMethod();
         } else {
            this.legacyXField = this.J("field_148887_a", false, int.class);
            this.legacyYField = this.J("field_148885_b", false, int.class);
            this.legacyZField = this.J("field_148886_c", false, int.class);
         }
      } else {
         this.legacyZField = this.J("field_148886_c", false, int.class);
      }
   }

   public Object getBlockPosition(Object packet) {
      return this.getBlockPositionMethod.invokeObject(packet);
   }
}
