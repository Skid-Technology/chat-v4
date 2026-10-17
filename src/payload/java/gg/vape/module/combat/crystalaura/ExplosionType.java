package gg.vape.module.combat.crystalaura;

public class ExplosionType {
   public static final ExplosionType ANCHOR;
   public static final ExplosionType BED;
   private static int controlFlowState;
   private final String label;
   public static final ExplosionType CRYSTAL;
   private final float explosionPower;

   public static void setControlFlowState(int state) {
      controlFlowState = state;
   }

   private ExplosionType(float power, String name) {
      this.explosionPower = power;
      this.label = name;
   }

   public String getLabel() {
      return this.label;
   }

   public static int getDerivedControlFlowState() {
      int state = getControlFlowState();
      return state == 0 ? 87 : 0;
   }

   public float getExplosionPower() {
      return this.explosionPower;
   }

   public static int getControlFlowState() {
      return controlFlowState;
   }

   static {
      if (getDerivedControlFlowState() == 0) {
         setControlFlowState(55);
      }

      CRYSTAL = new ExplosionType(6.0F, "Crystal");
      ANCHOR = new ExplosionType(5.0F, "Anchor");
      BED = new ExplosionType(5.0F, "Bed");
   }
}
