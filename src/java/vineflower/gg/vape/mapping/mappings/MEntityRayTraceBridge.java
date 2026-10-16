package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.function.Predicate;

public class MEntityRayTraceBridge extends Mapping {
   private static final String GET_CLOSEST_HIT = "getClosesetHit";
   private final MappingMethod getClosestHitMethod;

   public Object getClosestHit(Object bridgeHandle, Object entityHandle, float distance, Predicate<Object> predicate) {
      return this.getClosestHitMethod.invokeObject(bridgeHandle, entityHandle, distance, predicate);
   }

   public MEntityRayTraceBridge() {
      this(MItemAttributeModifiersComponent.getItemAttributeModifiersControlFlowState());
   }

   private MEntityRayTraceBridge(GuiComponent[] controlFlowState) {
      super(MappedClasses.Dr);
      if (controlFlowState != null) {
         this.getClosestHitMethod = this.Y("getClosesetHit", true, MappedClasses.DT, MappedClasses.zc, float.class, Predicate.class);
      } else {
         this.getClosestHitMethod = this.Y("getClosesetHit", true, MappedClasses.DT, MappedClasses.zc, float.class, Predicate.class);
         GuiComponent.setLegacyComponentState(new GuiComponent[5]);
      }
   }
}
