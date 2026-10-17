package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MEntityRayTraceResult extends Mapping {
   private static final String CONSTRUCTOR = "<init>";
   private final MappingMethod getEntityMethod;
   private final MappingMethod constructor = this.Y("<init>", false, void.class, MappedClasses.zc, MappedClasses.qP);

   public Object create(Object entityHandle, Object hitPositionHandle) {
      return this.constructor.newInstance(entityHandle, hitPositionHandle);
   }

   public MEntityRayTraceResult() {
      super(MappedClasses.zl);
      this.getEntityMethod = this.Y("getEntity", true, MappedClasses.zc);
   }

   public Object getEntity(Object rayTraceResultHandle) {
      return this.getEntityMethod.invokeObject(rayTraceResultHandle);
   }
}
