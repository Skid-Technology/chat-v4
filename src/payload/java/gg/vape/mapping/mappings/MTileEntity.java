package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.TileEntity;

public class MTileEntity extends Mapping {
   private MappingField xCoordinateField;
   private MappingField yCoordinateField;
   private MappingField zCoordinateField;
   private MappingField positionField;

   public Object getBlockPos(Object tileEntity) {
      return this.positionField.getObject(tileEntity);
   }

   public int getY(Object tileEntity) {
      return this.yCoordinateField.getInt(tileEntity);
   }

   public int getX(Object tileEntity) {
      return this.xCoordinateField.getInt(tileEntity);
   }

   public MTileEntity() {
      this(MTileEntityMobSpawner.getMobSpawnerControlFlowState());
   }

   private MTileEntity(int[] initializationState) {
      super(MappedClasses.ZI);
      TileEntity.setUsesLegacyCoordinates(ForgeVersion.MC_1_7_10.L());
      if (initializationState != null) {
         Class positionType = MappedClasses.lf;
         boolean remap = true;
         String fieldName = "pos";
         this.positionField = this.J(fieldName, remap, positionType);
      } else {
         if (ForgeVersion.MC_1_7_10.L()) {
            Class<Integer> coordinateType = int.class;
            boolean remapXField = true;
            String xFieldName = "xCoord";
            this.xCoordinateField = this.J(xFieldName, remapXField, coordinateType);
            Class<Integer> yCoordinateType = int.class;
            boolean remapYField = true;
            String yFieldName = "yCoord";
            this.yCoordinateField = this.J(yFieldName, remapYField, yCoordinateType);
            Class<Integer> zCoordinateType = int.class;
            boolean remapZField = true;
            String zFieldName = "zCoord";
            this.zCoordinateField = this.J(zFieldName, remapZField, zCoordinateType);
         } else {
            Class positionType = MappedClasses.lf;
            boolean remap = true;
            String fieldName = "pos";
            this.positionField = this.J(fieldName, remap, positionType);
         }
      }
   }

   public int getZ(Object tileEntity) {
      return this.zCoordinateField.getInt(tileEntity);
   }
}
