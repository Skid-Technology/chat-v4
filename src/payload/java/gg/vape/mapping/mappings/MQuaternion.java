package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MQuaternion extends Mapping {
   private MappingField xField;
   private MappingField yField;
   private MappingField zField;
   private MappingField wField;
   private MappingMethod eulerAnglesConstructor;
   private MappingMethod componentsConstructor;

   public float getZ(Object instance) {
      return this.zField.getFloat(instance);
   }

   public float getX(Object instance) {
      return this.xField.getFloat(instance);
   }

   public MQuaternion() {
      this(BlockData.W());
   }

   private MQuaternion(String[] initializationGuard) {
      super(MappedClasses.qI);
      if (initializationGuard != null) {
         if (ForgeVersion.MC_1_20_6.v()) {
            this.eulerAnglesConstructor = this.Y("<init>", false, void.class, float.class, float.class, float.class, boolean.class);
         }

         this.componentsConstructor = this.Y("<init>", false, void.class, float.class, float.class, float.class, float.class);
         this.xField = this.J("x", true, float.class);
         this.yField = this.J("y", true, float.class);
         this.zField = this.J("z", true, float.class);
         this.wField = this.J("w", true, float.class);
      }
   }

   public float getW(Object instance) {
      return this.wField.getFloat(instance);
   }

   public Object newFromEulerAngles(float xAngle, float yAngle, float zAngle, boolean degrees) {
      return this.eulerAnglesConstructor.newInstance(xAngle, yAngle, zAngle, degrees);
   }

   public float getY(Object instance) {
      return this.yField.getFloat(instance);
   }

   public Object newFromComponents(float x, float y, float z, float w) {
      return this.componentsConstructor.newInstance(x, y, z, w);
   }
}
