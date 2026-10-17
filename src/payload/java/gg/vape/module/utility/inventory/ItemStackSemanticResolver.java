package gg.vape.module.utility.inventory;

import gg.vape.Vape;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.utility.armorswitch.ArmorMaterialType;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ItemStackSemanticResolver {
   private final List<ItemMappingEntry> mappings = new ArrayList<>();
   private final List<ItemMappingEntry> legacyMappings = new ArrayList<>();
   public static boolean LOG_MISSING_MAPPINGS = true;

   public void loadMappings() {
      String resourceName = "universal_items.csv";
      byte[] resourceBytes = Vape.readResource(resourceName);

      for (String line : new String(resourceBytes).split("\n")) {
         String mappingLine = line.trim();
         ItemMappingEntry itemMappingEntry = ItemMappingEntry.parse(mappingLine);
         if (itemMappingEntry.resolveItem() != null && ItemStackScoreUtil.R(itemMappingEntry.resolveItem())) {
            for (ArmorMaterialType armorMaterialType : ArmorMaterialType.values()) {
               if (armorMaterialType.G(itemMappingEntry.getResourceKey())) {
                  itemMappingEntry = new ArmorItemMappingEntry(itemMappingEntry, armorMaterialType);
               }
            }
         }

         this.mappings.add(itemMappingEntry);
         if (itemMappingEntry.getLegacyIdString() != null) {
            this.legacyMappings.add(itemMappingEntry);
         }
      }
   }

   @Nullable
   public ItemMappingEntry findByName(String name) {
      for (ItemMappingEntry itemMappingEntry : this.mappings) {
         if (itemMappingEntry.getResourceKey().equals(name)) {
            return itemMappingEntry;
         }
      }

      return null;
   }

   public void reportMissingMappings() {
      ArrayList<ItemStack> missingItems = new ArrayList<>();

      for (ItemStack itemStack : ItemStackScoreUtil.S()) {
         ItemMappingEntry itemMappingEntry = this.resolve(itemStack);
         if (itemMappingEntry == null) {
            missingItems.add(itemStack);
         }
      }

      if (LOG_MISSING_MAPPINGS) {
         if (!missingItems.isEmpty()) {
            Vape.debugLog("Failed to find " + missingItems.size() + " item(s):");
         }
      }
   }

   @Nullable
   public ItemMappingEntry findLegacyMapping(int itemId, int metadata) {
      for (ItemMappingEntry itemMappingEntry : this.legacyMappings) {
         assert itemMappingEntry.getLegacyId() != null;

         if (itemMappingEntry.getLegacyId() == itemId && itemMappingEntry.getMetadata() != null && itemMappingEntry.getMetadata() == metadata) {
            return itemMappingEntry;
         }
      }

      return null;
   }

   @Nullable
   public ItemMappingEntry findLegacyMapping(int itemId) {
      for (ItemMappingEntry itemMappingEntry : this.legacyMappings) {
         assert itemMappingEntry.getLegacyId() != null;

         if (itemMappingEntry.getLegacyId() == itemId && (itemMappingEntry.getMetadata() == null || itemMappingEntry.getMetadata() == 0)) {
            return itemMappingEntry;
         }
      }

      return null;
   }

   @Nullable
   public ItemMappingEntry resolve(ItemStack itemStack) {
      if (itemStack.isNull()) {
         return null;
      } else {
         Item item = itemStack.getItem();
         if (item.isNull()) {
            return null;
         } else if (ForgeVersion.MC_1_16_5.d()) {
            String itemName = item.getObject().toString();
            String normalizedName = ForgeVersion.MC_1_21_0.d() ? itemName : "minecraft:" + itemName;
            return this.findByName(normalizedName);
         } else {
            int itemId = item.P();
            int metadata = itemStack.L();
            if (!item.p()) {
               metadata = 0;
            }

            ItemMappingEntry itemMappingEntry;
            if ((itemMappingEntry = this.findLegacyMapping(itemId, metadata)) != null) {
               return itemMappingEntry;
            } else {
               return itemStack.L() != 0 ? null : this.findLegacyMapping(itemId);
            }
         }
      }
   }
}
