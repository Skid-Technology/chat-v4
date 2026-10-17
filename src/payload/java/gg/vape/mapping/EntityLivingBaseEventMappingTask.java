package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventEntityRendererRayTrace;
import gg.vape.event.impl.EventPostEntityUpdate;
import gg.vape.event.impl.EventPostLivingTravel;
import gg.vape.event.impl.EventPotionEffectCheck;
import gg.vape.event.impl.EventPreEntityUpdate;
import gg.vape.event.impl.EventPreLivingTravel;
import gg.vape.wrapper.impl.ForgeVersion;

public class EntityLivingBaseEventMappingTask extends JavassistMappingTask {
   public EntityLivingBaseEventMappingTask() {
      super(MappedClasses.zm);
   }

   @Override
   public void transform() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Rr.V;
      this.c(mappingMethod, EventPreEntityUpdate.class, "$0");
      this.k(mappingMethod, EventPostEntityUpdate.class, "$0");
      MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().hx.U;
      this.c(mappingMethod2, EventPreLivingTravel.class, "$0");
      this.k(mappingMethod2, EventPostLivingTravel.class, "$0");
      if (ForgeVersion.MC_1_8_9.L()) {
         if (!Vape.INSTANCE.isLabyModPresent()) {
            EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().Rr.jL, EventEntityRendererRayTrace.class);
            eventInjectionSpec.setConstructorArguments("$0, $1");
            eventInjectionSpec.setReturnExpression("($r) $event.getVec();");
            this.registerEventInjection(eventInjectionSpec);
         }

         EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().hx.s, EventPotionEffectCheck.class);
         eventInjectionSpec.setConstructorArguments("$0, $1");
         eventInjectionSpec.setReturnExpression("($r) $event.isActive();");
         this.registerEventInjection(eventInjectionSpec);
      }
   }
}
