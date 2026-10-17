package gg.vape.utils.render;

import java.util.Objects;

public class PotionEffectIconKey {
   private int effectId;

   public int getEffectId() {
      return this.effectId;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getEffectId());
   }

   public PotionEffectIconKey(int effectId) {
      this.effectId = effectId;
   }

   @Override
   public boolean equals(Object object) {
      if (object instanceof PotionEffectIconKey) {
         PotionEffectIconKey other = (PotionEffectIconKey)object;
         return other.getEffectId() == this.getEffectId();
      } else {
         return false;
      }
   }
}
