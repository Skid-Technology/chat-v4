package gg.vape.mapping.mappings;

import gg.vape.config.ClientSettings;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTileEntityChest extends Mapping {
   private MappingMethod getOpennessMethod;
   private final MappingField openCountField;
   private final MappingField opennessField;

   public int getOpenCount(Object chestTileEntity) {
      return this.openCountField.getInt(chestTileEntity);
   }

   public float getOpenness(Object chestTileEntity, float partialTick) {
      return this.getOpennessMethod.invokeFloat(chestTileEntity, partialTick);
   }

   public MTileEntityChest() {
      this(MTileEntityMobSpawner.getMobSpawnerControlFlowState());
   }

   private MTileEntityChest(int[] initializationState) {
      super(MappedClasses.u0);
      if (initializationState != null) {
         GuiComponent.setLegacyComponentState(new GuiComponent[1]);
         Class<Float> opennessType = float.class;
         boolean remapOpennessField = true;
         String opennessFieldName = ClientSettings.IS_LEGACY_1_7 ? "lidAngle" : "field_145972_a";
         this.opennessField = this.J(opennessFieldName, remapOpennessField, opennessType);
         Class<Integer> openCountType = int.class;
         boolean remapOpenCountField = true;
         String openCountFieldName = ClientSettings.IS_LEGACY_1_7 ? "numPlayersUsing" : "field_145973_j";
         this.openCountField = this.J(openCountFieldName, remapOpenCountField, openCountType);
      } else {
         if (ForgeVersion.MC_1_17.d()) {
            Class<Float> opennessType = float.class;
            boolean remapOpennessField = true;
            String opennessFieldName = "openness";
            Class opennessOwner = MappedClasses.lQ;
            this.opennessField = this.registerInstanceFieldForOwner(opennessOwner, opennessFieldName, remapOpennessField, opennessType);
            Class<Integer> openCountType = int.class;
            boolean remapOpenCountField = true;
            String openCountFieldName = "openCount";
            Class openCountOwner = MappedClasses.zn;
            this.openCountField = this.registerInstanceFieldForOwner(openCountOwner, openCountFieldName, remapOpenCountField, openCountType);
            Class[] parameterTypes = new Class[]{float.class};
            Class<Float> returnType = float.class;
            String methodName = "getOpenNess";
            this.getOpennessMethod = this.methodBuilder(methodName, returnType, parameterTypes)
               .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Fs)
               .buildMethod();
         } else {
            Class<Float> opennessType = float.class;
            boolean remapOpennessField = true;
            String opennessFieldName = ClientSettings.IS_LEGACY_1_7 ? "lidAngle" : "field_145972_a";
            this.opennessField = this.J(opennessFieldName, remapOpennessField, opennessType);
            Class<Integer> openCountType = int.class;
            boolean remapOpenCountField = true;
            String openCountFieldName = ClientSettings.IS_LEGACY_1_7 ? "numPlayersUsing" : "field_145973_j";
            this.openCountField = this.J(openCountFieldName, remapOpenCountField, openCountType);
         }
      }
   }

   public float getStoredOpenness(Object chestTileEntity) {
      return this.opennessField.getFloat(chestTileEntity);
   }
}
