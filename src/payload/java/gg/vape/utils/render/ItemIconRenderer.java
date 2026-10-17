package gg.vape.utils.render;

import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemArmor;
import gg.vape.wrapper.impl.ItemStack;
import java.util.HashMap;

public class ItemIconRenderer {
   private static String[] legacyMarkers;
   static HashMap<ItemIconKey, ItemIconRenderBackend> cache = new HashMap<>();

   public static void precache(ItemStack itemStack) {
      Item item = itemStack.getItem();
      cacheItem(item.P(), resolveMetadata(itemStack, item), 1.0F);
   }

   public static String[] getLegacyMarkers() {
      return legacyMarkers;
   }

   public static void renderItem(int itemId, int metadata, float x, float y, int width, int height) {
      renderItem(itemId, metadata, x, y, width, height, 1.0F, 1.0F, false);
   }

   private static ItemIconKey createKey(int itemId, int metadata, float scale, ItemStack itemStack) {
      ItemIconKey cacheKey = new ItemIconKey(itemId, metadata, scale);
      Item item;
      if (itemStack != null && ItemStackScoreUtil.R(item = itemStack.getItem()) && ForgeVersion.MC_1_8_9.B()) {
         ItemArmor armorItem = new ItemArmor(item.getObject());
         cacheKey.setArmorColor(armorItem.Y(itemStack));
      }

      return cacheKey;
   }

   public static void renderItem(int itemId, int metadata, float x, float y, int width, int height, float opacity) {
      renderItem(itemId, metadata, x, y, width, height, opacity, 1.0F, false);
   }

   public static void renderItemStack(ItemStack itemStack, float x, float y, int width, int height, float opacity, float scale) {
      renderItemStack(itemStack, null, x, y, width, height, opacity, scale, false);
   }

   public static void renderItem(int itemId, int metadata, float x, float y, int width, int height, float opacity, boolean worldSpace) {
      renderItem(itemId, metadata, x, y, width, height, opacity, 1.0F, worldSpace);
   }

   public static void renderItemStack(ItemStack itemStack, float x, float y, int width, int height, float opacity, boolean worldSpace) {
      renderItemStack(itemStack, null, x, y, width, height, opacity, 1.0F, worldSpace);
   }

   public static void renderItemStack(ItemStack itemStack, float x, float y, int width, int height) {
      renderItemStack(itemStack, null, x, y, width, height, 1.0F, 1.0F, false);
   }

   public static void renderItem(int itemId, int metadata, float x, float y, int width, int height, float opacity, float scale, boolean worldSpace) {
      ItemStack itemStack = ItemStack.S(Item.T(itemId));
      itemStack.s(metadata);
      renderCached(itemId, metadata, itemStack, x, y, width, height, opacity, scale, worldSpace);
   }

   public static void clear() {
      for (ItemIconRenderBackend renderer : cache.values()) {
         renderer.dispose();
      }

      cache.clear();
   }

   private static ItemIconRenderBackend createRenderer(ItemStack itemStack, ItemIconKey cacheKey) {
      ItemIconRenderBackend renderer = (ItemIconRenderBackend)(GuiRenderPrimitives.d() ? new Post117ItemIconFramebufferRenderer() : new ItemIcon());
      renderer.capture(itemStack, cacheKey.getScale());
      cache.put(cacheKey, renderer);
      return renderer;
   }

   public static void renderItemStack(ItemStack itemStack, float x, float y, int width, int height, float opacity) {
      renderItemStack(itemStack, null, x, y, width, height, opacity, 1.0F, false);
   }

   private static int resolveMetadata(ItemStack itemStack, Item item) {
      return !ItemStackScoreUtil.I(item) && !ItemStackScoreUtil.R(item) ? itemStack.L() : 0;
   }

   public static void setLegacyMarkers(String[] legacyMarkers) {
      ItemIconRenderer.legacyMarkers = legacyMarkers;
   }

   public static ItemIconRenderBackend createIfAbsent(ItemIconKey cacheKey, ItemStack itemStack) {
      return !cache.containsKey(cacheKey) ? createRenderer(itemStack, cacheKey) : null;
   }

   public static void renderItemStack(ItemStack itemStack, Item item, float x, float y, int width, int height, float opacity, float scale, boolean worldSpace) {
      if (itemStack != null) {
         if (item == null) {
            item = itemStack.getItem();
         }

         int itemId = item.P();
         int metadata = resolveMetadata(itemStack, item);
         renderCached(itemId, metadata, itemStack, x, y, width, height, opacity, scale, worldSpace);
      }
   }

   public static void precache(int itemId, int metadata) {
      cacheItem(itemId, metadata, 1.0F);
   }

   public static ItemIconRenderBackend cacheItem(int itemId, int metadata, float scale) {
      ItemIconKey cacheKey = new ItemIconKey(itemId, metadata, scale);
      if (!cache.containsKey(cacheKey)) {
         Item item = Item.T(itemId);
         if (item.isNull()) {
            return null;
         } else {
            ItemStack itemStack = ItemStack.S(item);
            itemStack.s(metadata);
            return createRenderer(itemStack, cacheKey);
         }
      } else {
         return null;
      }
   }

   public static void renderItem(int itemId, int metadata, float x, float y, int width, int height, boolean worldSpace) {
      renderItem(itemId, metadata, x, y, width, height, 1.0F, 1.0F, worldSpace);
   }

   private static void renderCached(
      int itemId, int metadata, ItemStack itemStack, float x, float y, int width, int height, float opacity, float scale, boolean worldSpace
   ) {
      ItemIconKey cacheKey = createKey(itemId, metadata, scale, itemStack);
      ItemIconRenderBackend renderer = cache.get(cacheKey);
      if (renderer != null) {
         renderer.renderQueued(x, y, width, height, opacity, worldSpace);
      } else {
         ItemIconRenderBackend createdRenderer = createIfAbsent(cacheKey, itemStack);
         if (createdRenderer != null) {
            createdRenderer.renderQueued(x, y, width, height, opacity, worldSpace);
         }
      }
   }

   static {
      setLegacyMarkers(new String[5]);
   }
}
