package gg.vape.module.render.entity;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.WorldClient;
import java.util.LinkedHashSet;

public class RenderEntityContextCacheListener implements EventListener {
   private long tickCounter;

   @EventHandler
   public void onWorldChange(EventWorldChange event) {
      RenderEntityContextCache.clear();
   }

   @EventHandler(priority = EventPriority.LOWEST)
   public void onTick(EventPreTick event) {
      this.tickCounter++;
      if (this.tickCounter % 10L == 0L) {
         RenderEntityContextCache.clearNameCaches();
      }

      EntityPlayerSP viewer = event.getThePlayer();
      if (!viewer.isNull()) {
         WorldClient world = event.getWorld();
         if (!world.isNull()) {
            LinkedHashSet<Integer> staleEntityIds = new LinkedHashSet<>();

            for (RenderEntityContext context : RenderEntityContextCache.getContexts()) {
               Entity entity = world.V(context.getEntityId());
               if (!entity.isNull() && entity.isInstance(MappedClasses.zm)) {
                  EntityLivingBase livingEntity = new EntityLivingBase(entity.getObject());
                  context.update(livingEntity, viewer);
               } else {
                  staleEntityIds.add(context.getEntityId());
               }
            }

            for (Integer entityId : staleEntityIds) {
               RenderEntityContextCache.remove(entityId);
            }
         }
      }
   }
}
