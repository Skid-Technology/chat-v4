package gg.vape.utils;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;
import java.util.Comparator;

public class EntityArmorValueComparator implements Comparator<Entity> {
   public int compare(Entity firstEntity, Entity secondEntity) {
      return this.compareByArmorValue(firstEntity, secondEntity);
   }

   public int compareByArmorValue(Entity firstEntity, Entity secondEntity) {
      if (firstEntity.isInstance(MappedClasses.Yl) && secondEntity.isInstance(MappedClasses.Yl)) {
         return Float.compare(this.calculateArmorValue(firstEntity), this.calculateArmorValue(secondEntity));
      } else {
         EntityPlayerSP player = Minecraft.thePlayer();
         return Float.compare(player.getDistanceToEntity(firstEntity), player.getDistanceToEntity(secondEntity));
      }
   }

   private float calculateArmorValue(Entity entity) {
      float armorValue = 0.0F;
      EntityPlayer player = new EntityPlayer(entity.getObject());
      if (player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt().isNotNull()) {
         armorValue += ItemStackScoreUtil.I$src$F$dh3k81(player.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt());
         PotionEffect potionEffect;
         if (player.i(PotionRegistry.t) && (potionEffect = player.b(PotionRegistry.t)).k() > 0) {
            armorValue = (float)(armorValue * (1.375 * potionEffect.L()));
         }
      }

      return armorValue;
   }
}
