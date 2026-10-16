package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MVector3f extends Mapping {
   private final MappingMethod constructor = this.Y("<init>", false, void.class, float.class, float.class, float.class);
   private MappingField xField;
   private MappingField yField;
   private MappingField zField;
   private MappingMethod rotationDegreesMethod;

   public float getZ(Object instance) {
      return this.zField.getFloat(instance);
   }

   public Object rotationDegrees(Object instance, float degrees) {
      return this.rotationDegreesMethod.invokeObject(instance, degrees);
   }

   public Object newInstance(float x, float y, float z) {
      return this.constructor.newInstance(x, y, z);
   }

   public MVector3f() {
      super(MappedClasses.qb);
      if (BlockData.W() != null) {
         if (ForgeVersion.MC_1_20_6.v()) {
            this.rotationDegreesMethod = this.Y("rotationDegrees", true, MappedClasses.qI, float.class);
         }

         this.xField = this.J("x", true, float.class);
         this.yField = this.J("y", true, float.class);
         this.zField = this.J("z", true, float.class);
      }
   }

   public float getY(Object instance) {
      return this.yField.getFloat(instance);
   }

   public float getX(Object instance) {
      return this.xField.getFloat(instance);
   }
}
