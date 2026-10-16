package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMaterial extends Mapping {
   public MappingField airField;
   private static int[] constructorState;
   private final MappingMethod isLiquidMethod;
   private MappingField fireField;
   public final MappingMethod isReplaceableMethod;
   public final MappingMethod isSolidMethod;
   private MappingField waterField;
   public final MappingMethod blocksMovementMethod;
   private MappingMethod isToolNotRequiredMethod;
   private MappingField vineField;

   public boolean blocksMovement(Object material) {
      return this.blocksMovementMethod.invokeBoolean(material);
   }

   public static int[] getConstructorState() {
      return constructorState;
   }

   public boolean isSolid(Object material) {
      return this.isSolidMethod.invokeBoolean(material);
   }

   public boolean isReplaceable(Object material) {
      return this.isReplaceableMethod.invokeBoolean(material);
   }

   public boolean isToolNotRequired(Object material) {
      return this.isToolNotRequiredMethod.invokeBoolean(material);
   }

   public MMaterial() {
      this(getConstructorState());
   }

   private MMaterial(int[] constructorState) {
      super(MappedClasses.ZN);
      if (ForgeVersion.MC_1_20_6.v()) {
         if (ForgeVersion.MC_1_12_2.d()) {
            Class materialClass = MappedClasses.ZN;
            boolean remapField = true;
            String airFieldName = "AIR";
            this.airField = this.registerStaticField(airFieldName, remapField, materialClass);
            Class waterOwner = MappedClasses.ZN;
            boolean remapWaterField = true;
            String waterFieldName = "WATER";
            this.waterField = this.registerStaticField(waterFieldName, remapWaterField, waterOwner);
            Class fireOwner = MappedClasses.ZN;
            boolean remapFireField = true;
            String fireFieldName = "FIRE";
            this.fireField = this.registerStaticField(fireFieldName, remapFireField, fireOwner);
            if (ForgeVersion.MC_1_16_5.d()) {
               Class vineOwner = MappedClasses.ZN;
               boolean remapVineField = true;
               String vineFieldName = "TALL_PLANTS";
               this.vineField = this.registerStaticField(vineFieldName, remapVineField, vineOwner);
            } else {
               Class vineOwner = MappedClasses.ZN;
               boolean remapVineField = true;
               String vineFieldName = "VINE";
               this.vineField = this.registerStaticField(vineFieldName, remapVineField, vineOwner);
            }
         } else {
            Class materialClass = MappedClasses.ZN;
            boolean remapField = true;
            String airFieldName = "air";
            this.airField = this.registerStaticField(airFieldName, remapField, materialClass);
            Class waterOwner = MappedClasses.ZN;
            boolean remapWaterField = true;
            String waterFieldName = "water";
            this.waterField = this.registerStaticField(waterFieldName, remapWaterField, waterOwner);
            Class vineOwner = MappedClasses.ZN;
            boolean remapVineField = true;
            String vineFieldName = "vine";
            this.vineField = this.registerStaticField(vineFieldName, remapVineField, vineOwner);
            Class fireOwner = MappedClasses.ZN;
            boolean remapFireField = true;
            String fireFieldName = "fire";
            this.fireField = this.registerStaticField(fireFieldName, remapFireField, fireOwner);
         }
      }

      if (ForgeVersion.MC_1_16_5.v()) {
         Class[] parameterTypes = new Class[0];
         Class<Boolean> returnType = boolean.class;
         boolean remapMethod = true;
         String methodName = "isToolNotRequired";
         this.isToolNotRequiredMethod = this.Y(methodName, remapMethod, returnType, parameterTypes);
      }

      Class[] replaceableParameterTypes = new Class[0];
      Class<Boolean> replaceableReturnType = boolean.class;
      boolean remapReplaceableMethod = true;
      String replaceableMethodName = "isReplaceable";
      this.isReplaceableMethod = this.Y(replaceableMethodName, remapReplaceableMethod, replaceableReturnType, replaceableParameterTypes);
      Class[] blocksMovementParameterTypes = new Class[0];
      Class<Boolean> blocksMovementReturnType = boolean.class;
      boolean remapBlocksMovementMethod = true;
      String blocksMovementMethodName = "blocksMovement";
      this.blocksMovementMethod = this.Y(blocksMovementMethodName, remapBlocksMovementMethod, blocksMovementReturnType, blocksMovementParameterTypes);
      Class[] solidParameterTypes = new Class[0];
      Class<Boolean> solidReturnType = boolean.class;
      boolean remapSolidMethod = true;
      String solidMethodName = "isSolid";
      this.isSolidMethod = this.Y(solidMethodName, remapSolidMethod, solidReturnType, solidParameterTypes);
      Class[] liquidParameterTypes = new Class[0];
      Class<Boolean> liquidReturnType = boolean.class;
      boolean remapLiquidMethod = true;
      String liquidMethodName = "isLiquid";
      this.isLiquidMethod = this.Y(liquidMethodName, remapLiquidMethod, liquidReturnType, liquidParameterTypes);
   }

   public boolean isLiquid(Object material) {
      return this.isLiquidMethod.invokeBoolean(material);
   }

   public static void setConstructorState(int[] state) {
      constructorState = state;
   }

   public Object getAir() {
      return this.airField.getObject(null);
   }

   public Object getFire() {
      return this.fireField.getObject(null);
   }

   public Object getWater() {
      return this.waterField.getObject(null);
   }

   public Object getVine() {
      return this.vineField.getObject(null);
   }

   static {
      setConstructorState(null);
   }
}
