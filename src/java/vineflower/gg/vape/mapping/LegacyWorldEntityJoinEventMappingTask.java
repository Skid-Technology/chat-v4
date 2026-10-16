package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.wrapper.impl.ForgeVersion;

public class LegacyWorldEntityJoinEventMappingTask extends JavassistMappingTask {
   private static final String c;

   @Override
   public void transform() {
      if (!ForgeVersion.MC_1_16_5.d()) {
         if (!ForgeVersion.MC_1_12_2.d()) {
            MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Cy.q;
            this.c(mappingMethod, EventEntityJoinWorld.class, c);
         }
      }
   }

   public LegacyWorldEntityJoinEventMappingTask() {
      super(MappedClasses.YU);
   }

   static {
      try {
         c = "$1";
      } catch (Exception var1) {
         throw new ExceptionInInitializerError(var1);
      }
   }
}
