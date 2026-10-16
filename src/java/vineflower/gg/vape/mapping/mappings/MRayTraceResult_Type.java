package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;

public class MRayTraceResult_Type extends Mapping {
   public final MappingField blockField;
   public final MappingField entityField;
   public final MappingField missField;

   public MRayTraceResult_Type() {
      super(MappedClasses.lk);
      Class missFieldType = MappedClasses.lk;
      boolean missFieldPublic = Wrapper.isNativeAvailable;
      String missFieldName = "MISS";
      this.missField = this.registerStaticField(missFieldName, missFieldPublic, missFieldType);
      Class blockFieldType = MappedClasses.lk;
      boolean blockFieldPublic = Wrapper.isNativeAvailable;
      String blockFieldName = "BLOCK";
      this.blockField = this.registerStaticField(blockFieldName, blockFieldPublic, blockFieldType);
      Class entityFieldType = MappedClasses.lk;
      boolean entityFieldPublic = Wrapper.isNativeAvailable;
      String entityFieldName = "ENTITY";
      this.entityField = this.registerStaticField(entityFieldName, entityFieldPublic, entityFieldType);
   }

   public static Object getBlock(MRayTraceResult_Type mapping) {
      return mapping.readBlock();
   }

   private Object readEntity() {
      return this.entityField.getObject(null);
   }

   public Object getMiss() {
      return this.missField.getObject(null);
   }

   private Object readBlock() {
      return this.blockField.getObject(null);
   }

   public static Object getEntity(MRayTraceResult_Type mapping) {
      return mapping.readEntity();
   }
}
