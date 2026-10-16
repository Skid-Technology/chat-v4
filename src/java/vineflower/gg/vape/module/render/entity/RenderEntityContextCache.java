package gg.vape.module.render.entity;

import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ITextComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class RenderEntityContextCache {
   private static final HashMap<Integer, ITextComponent> customNames = new HashMap<>();
   private static final Object lock = new Object();
   private static final Map<Integer, RenderEntityContext> contexts = new LinkedHashMap<>();
   private static final HashMap<Integer, ITextComponent> displayNameCache = new HashMap<>();

   @Nullable
   public static void setCustomName(EntityPlayer player, ITextComponent customName) {
      customNames.put(player.S(), customName);
   }

   public static ITextComponent getCustomName(EntityPlayer player) {
      return customNames.containsKey(player.S()) ? customNames.get(player.S()) : null;
   }

   public static void clear() {
      if (!contexts.isEmpty()) {
         synchronized (lock) {
            contexts.clear();
         }
      }
   }

   public static void remove(int... entityIds) {
      synchronized (lock) {
         for (int entityId : entityIds) {
            contexts.remove(entityId);
         }
      }
   }

   public static Collection<RenderEntityContext> getContexts() {
      synchronized (lock) {
         return new ArrayList<>(contexts.values());
      }
   }

   public static void clearNameCaches() {
      displayNameCache.clear();
      customNames.clear();
   }

   private static RenderEntityContext createContext(EntityLivingBase entity, EntityPlayerSP viewer, Integer entityId) {
      RenderEntityContext context = new RenderEntityContext(entityId, entity, viewer);
      context.update(entity, viewer);
      return context;
   }

   @Nullable
   public static ITextComponent getDisplayName(EntityPlayer player) {
      if (displayNameCache.containsKey(player.S())) {
         return displayNameCache.get(player.S());
      } else {
         ITextComponent displayName = player.Q();
         displayNameCache.put(player.S(), displayName);
         return displayName;
      }
   }

   public static RenderEntityContext getOrCreate(EntityLivingBase entity, EntityPlayerSP viewer) {
      synchronized (lock) {
         return contexts.computeIfAbsent(entity.S(), entityId -> createContext(entity, viewer, entityId));
      }
   }
}
