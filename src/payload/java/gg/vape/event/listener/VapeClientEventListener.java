package gg.vape.event.listener;

import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventPreTick;
import gg.vape.module.render.nametags.NameTagsRenderStateTracker;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.EntityModelRenderCache;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.utils.render.PotionEffectIconRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.ResourceManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VapeClientEventListener implements EventListener {
   private final TimerUtil resourceCheckTimer = new TimerUtil();
   private Set<String> resourceManagerEntries;
   private List<String> configuredResourcePacks;
   private boolean resourcePackChangePending;
   private Object resourceMap;

   private static Exception identityException(Exception exception) {
      return exception;
   }

   private void clearRenderCaches() {
      ItemIconRenderer.clear();
      EntityModelRenderCache.clear();
      PotionEffectIconRenderer.clear();
      NameTagsRenderStateTracker.INSTANCE.clear();
   }

   private void checkModernResourceState() {
      List<String> configuredPacks = Minecraft.gameSettings().f$src$Ljava_util_List_$1i0ug5l();
      if (configuredPacks != null) {
         Map currentResourceMap = Minecraft.P();
         if (currentResourceMap == null) {
            ArrayList<String> resourcePackSnapshot = new ArrayList<>(configuredPacks);
            if (this.configuredResourcePacks == null) {
               this.configuredResourcePacks = resourcePackSnapshot;
               this.resourceMap = currentResourceMap;
            } else {
               boolean resourcePacksChangedWithoutMap = !this.configuredResourcePacks.equals(resourcePackSnapshot);
               if (resourcePacksChangedWithoutMap) {
                  boolean resourceMapChanged = false;
                  this.configuredResourcePacks = resourcePackSnapshot;
                  this.resourcePackChangePending = true;
                  if (this.resourcePackChangePending) {
                  }

                  if (!this.resourcePackChangePending) {
                  }

                  if (this.resourceMap == null) {
                     this.resourceMap = currentResourceMap;
                  }
               } else {
                  boolean resourceMapChangedx = false;
                  if (this.resourcePackChangePending) {
                  }

                  if (!this.resourcePackChangePending) {
                  }

                  if (this.resourceMap == null) {
                     this.resourceMap = currentResourceMap;
                  }
               }
            }
         } else {
            ArrayList<String> resourcePackSnapshot = new ArrayList<>(configuredPacks);
            if (this.configuredResourcePacks == null) {
               this.configuredResourcePacks = resourcePackSnapshot;
               this.resourceMap = currentResourceMap;
            } else {
               boolean resourcePacksChanged = !this.configuredResourcePacks.equals(resourcePackSnapshot);
               if (resourcePacksChanged) {
                  boolean resourceMapChangedxx = this.resourceMap != null && currentResourceMap != this.resourceMap;
                  if (resourceMapChangedxx) {
                     this.configuredResourcePacks = resourcePackSnapshot;
                     this.resourcePackChangePending = true;
                     if (this.resourcePackChangePending) {
                        this.resourceMap = currentResourceMap;
                        this.resourcePackChangePending = false;
                        this.clearRenderCaches();
                     } else if (!this.resourcePackChangePending) {
                        this.resourceMap = currentResourceMap;
                        this.clearRenderCaches();
                     } else {
                        if (this.resourceMap == null) {
                           this.resourceMap = currentResourceMap;
                        }
                     }
                  } else {
                     this.configuredResourcePacks = resourcePackSnapshot;
                     this.resourcePackChangePending = true;
                     if (this.resourcePackChangePending) {
                     }

                     if (!this.resourcePackChangePending) {
                     }

                     if (this.resourceMap == null) {
                        this.resourceMap = currentResourceMap;
                     }
                  }
               } else {
                  boolean resourceMapChangedWithoutPackChange = this.resourceMap != null && currentResourceMap != this.resourceMap;
                  if (resourceMapChangedWithoutPackChange) {
                     if (this.resourcePackChangePending) {
                        this.resourceMap = currentResourceMap;
                        this.resourcePackChangePending = false;
                        this.clearRenderCaches();
                     } else if (!this.resourcePackChangePending) {
                        this.resourceMap = currentResourceMap;
                        this.clearRenderCaches();
                     } else {
                        if (this.resourceMap == null) {
                           this.resourceMap = currentResourceMap;
                        }
                     }
                  } else {
                     if (this.resourcePackChangePending) {
                     }

                     if (!this.resourcePackChangePending) {
                     }

                     if (this.resourceMap == null) {
                        this.resourceMap = currentResourceMap;
                     }
                  }
               }
            }
         }
      } else {
         Map currentResourceMap = Minecraft.P();
         if (currentResourceMap != null) {
            this.checkResourceManagerState();
         } else {
            this.checkResourceManagerState();
         }
      }
   }

   private void checkResourceManagerState() {
      ResourceManager packRepository = Minecraft.getResourcePackRepository();
      if (!packRepository.isNull()) {
         Collection<String> entries = packRepository.getSelectedIds();
         if (entries != null) {
            HashSet<String> entrySnapshot = new HashSet<>(entries);
            if (this.resourceManagerEntries == null) {
               this.resourceManagerEntries = entrySnapshot;
            } else {
               if (!this.resourceManagerEntries.equals(entrySnapshot)) {
                  this.resourceManagerEntries = entrySnapshot;
                  this.clearRenderCaches();
               }
            }
         }
      }
   }

   @EventHandler
   public void onTick(EventPreTick eventPreTick) {
      if (this.resourceCheckTimer.hasTimeElapsed(1000L)) {
         this.resourceCheckTimer.reset();

         try {
            if (ForgeVersion.MC_1_21_10.d()) {
               this.checkModernResourceState();
               return;
            }

            this.checkResourceManagerState();
         } catch (Exception var3) {
         }
      }
   }
}
