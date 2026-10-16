package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItemActionResult extends Mapping {
   private final MappingMethod withoutItemMethod;
   private final MappingMethod heldItemTransformedToGetter;
   private final MappingMethod wasItemInteractionMethod;
   private final MappingMethod constructor;
   private final MappingMethod heldItemTransformedToMethod;
   private final MappingMethod consumesActionMethod;

   public boolean consumesAction(Object resultHandle) {
      return this.consumesActionMethod == null ? true : this.consumesActionMethod.invokeBoolean(resultHandle);
   }

   public MItemActionResult() {
      this(BlockData.W());
   }

   private MItemActionResult(String[] controlFlowState) {
      super(MappedClasses.zO);
      if (controlFlowState != null) {
         Class[] constructorParameterTypes = new Class[]{MappedClasses.Zi, MappedClasses.lj};
         this.constructor = this.registerConstructor(constructorParameterTypes);
         if (ForgeVersion.MC_1_21_10.v()) {
            Class[] consumesActionParameterTypes = new Class[0];
            Class<Boolean> consumesActionReturnType = boolean.class;
            boolean consumesActionPublic = true;
            String consumesActionMethodName = "consumesAction";
            this.consumesActionMethod = this.Y(consumesActionMethodName, consumesActionPublic, consumesActionReturnType, consumesActionParameterTypes);
         } else {
            this.consumesActionMethod = null;
         }

         Class[] transformParameterTypes = new Class[]{MappedClasses.VK};
         Class transformReturnType = MappedClasses.zO;
         boolean transformPublic = true;
         String transformMethodName = "heldItemTransformedTo";
         this.heldItemTransformedToMethod = this.Y(transformMethodName, transformPublic, transformReturnType, transformParameterTypes);
         Class[] transformedItemGetterParameterTypes = new Class[0];
         Class transformedItemGetterReturnType = MappedClasses.VK;
         boolean transformedItemGetterPublic = true;
         String transformedItemGetterName = "heldItemTransformedTo";
         this.heldItemTransformedToGetter = this.Y(
            transformedItemGetterName, transformedItemGetterPublic, transformedItemGetterReturnType, transformedItemGetterParameterTypes
         );
         Class[] withoutItemParameterTypes = new Class[0];
         Class withoutItemReturnType = MappedClasses.zO;
         boolean withoutItemPublic = true;
         String withoutItemMethodName = "withoutItem";
         this.withoutItemMethod = this.Y(withoutItemMethodName, withoutItemPublic, withoutItemReturnType, withoutItemParameterTypes);
         Class[] wasItemInteractionParameterTypes = new Class[0];
         Class<Boolean> wasItemInteractionReturnType = boolean.class;
         boolean wasItemInteractionPublic = true;
         String wasItemInteractionMethodName = "wasItemInteraction";
         this.wasItemInteractionMethod = this.Y(
            wasItemInteractionMethodName, wasItemInteractionPublic, wasItemInteractionReturnType, wasItemInteractionParameterTypes
         );
      } else {
         this.consumesActionMethod = null;
         Class[] transformParameterTypes = new Class[]{MappedClasses.VK};
         Class transformReturnType = MappedClasses.zO;
         boolean transformPublic = true;
         String transformMethodName = "heldItemTransformedTo";
         this.heldItemTransformedToMethod = this.Y(transformMethodName, transformPublic, transformReturnType, transformParameterTypes);
         Class[] transformedItemGetterParameterTypes = new Class[0];
         Class transformedItemGetterReturnType = MappedClasses.VK;
         boolean transformedItemGetterPublic = true;
         String transformedItemGetterName = "heldItemTransformedTo";
         this.heldItemTransformedToGetter = this.Y(
            transformedItemGetterName, transformedItemGetterPublic, transformedItemGetterReturnType, transformedItemGetterParameterTypes
         );
         Class[] withoutItemParameterTypes = new Class[0];
         Class withoutItemReturnType = MappedClasses.zO;
         boolean withoutItemPublic = true;
         String withoutItemMethodName = "withoutItem";
         this.withoutItemMethod = this.Y(withoutItemMethodName, withoutItemPublic, withoutItemReturnType, withoutItemParameterTypes);
         Class[] wasItemInteractionParameterTypes = new Class[0];
         Class<Boolean> wasItemInteractionReturnType = boolean.class;
         boolean wasItemInteractionPublic = true;
         String wasItemInteractionMethodName = "wasItemInteraction";
         this.wasItemInteractionMethod = this.Y(
            wasItemInteractionMethodName, wasItemInteractionPublic, wasItemInteractionReturnType, wasItemInteractionParameterTypes
         );
         this.constructor = null;
      }
   }

   public Object getHeldItemTransformedTo(Object resultHandle) {
      return this.heldItemTransformedToGetter.invokeObject(resultHandle);
   }

   public Object create(Object swingSource, Object itemContext) {
      return this.constructor.invokeObject(null, swingSource, itemContext);
   }

   public boolean wasItemInteraction(Object resultHandle) {
      return this.wasItemInteractionMethod.invokeBoolean(resultHandle);
   }

   public Object heldItemTransformedTo(Object resultHandle, Object itemStack) {
      return this.heldItemTransformedToMethod.invokeObject(resultHandle, itemStack);
   }

   public Object withoutItem(Object resultHandle) {
      return this.withoutItemMethod.invokeObject(resultHandle);
   }
}
