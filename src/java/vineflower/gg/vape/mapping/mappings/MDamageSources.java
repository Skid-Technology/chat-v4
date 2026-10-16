package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MDamageSources extends Mapping {
   private final MappingMethod explosionMethod;
   private static int controlFlowState;
   private final MappingMethod fallMethod;
   private final MappingMethod playerAttackMethod = this.Y("playerAttack", true, MappedClasses.uB, MappedClasses.Yl);

   public MDamageSources() {
      super(MappedClasses.ZZ);
      this.fallMethod = this.Y("fall", true, MappedClasses.uB);
      this.explosionMethod = this.Y("explosion", true, MappedClasses.uB, MappedClasses.zc, MappedClasses.zc);
   }

   public Object getFallDamageSource(Object damageSources) {
      return this.fallMethod.invokeObject(damageSources);
   }

   public Object getPlayerAttackDamageSource(Object damageSources, Object player) {
      return this.playerAttackMethod.invokeObject(damageSources, player);
   }

   public static int getDamageSourcesControlFlowState() {
      return controlFlowState;
   }

   public static void setDamageSourcesControlFlowState(int state) {
      controlFlowState = state;
   }

   public static int getControlFlowConstant() {
      getDamageSourcesControlFlowState();
      return 112;
   }

   public Object getExplosionDamageSource(Object damageSources) {
      return this.explosionMethod.invokeObject(damageSources, null, null);
   }

   static {
      setDamageSourcesControlFlowState(0);
   }
}
