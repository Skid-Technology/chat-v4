package gg.vape.friend.activity;

import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class ActivityItemStack {
   private final int count;
   private final Map<Short, Short> enchantments;
   private final int itemId;
   private final int metadata;

   public ActivityItemStack(int itemId, int count, int metadata, Map<Short, Short> enchantments) {
      this.itemId = itemId;
      this.count = count;
      this.metadata = metadata;
      this.enchantments = enchantments;
   }

   public boolean hasEnchantments() {
      return !this.enchantments.isEmpty();
   }

   public int getItemId() {
      return this.itemId;
   }

   public int getMetadata() {
      return this.metadata;
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         ActivityItemStack activityItemStack = (ActivityItemStack)other;
         if (this.itemId != activityItemStack.itemId) {
            return false;
         } else if (this.count != activityItemStack.count) {
            return false;
         } else {
            return this.metadata != activityItemStack.metadata
               ? false
               : this.enchantments.size() == activityItemStack.enchantments.size() && this.enchantments.equals(activityItemStack.enchantments);
         }
      } else {
         return false;
      }
   }

   public int getCount() {
      return this.count;
   }

   public Map<Short, Short> getEnchantments() {
      return this.enchantments;
   }

   public ActivityItemStackPayload toPayload() {
      return new ActivityItemStackPayload(this.itemId, this.count, this.metadata, this.enchantments);
   }

   @Override
   public int hashCode() {
      int result = this.itemId;
      result = 31 * result + this.count;
      result = 31 * result + this.metadata;
      return 31 * result + this.enchantments.hashCode();
   }

   @Nullable
   public static ActivityItemStack fromPayload(@Nullable ActivityItemStackPayload activityItemStackPayload) {
      return activityItemStackPayload != null && activityItemStackPayload.getItemId() != 0
         ? new ActivityItemStack(
            activityItemStackPayload.getItemId(),
            activityItemStackPayload.getCount(),
            activityItemStackPayload.getMetadata(),
            activityItemStackPayload.getEnchantments()
         )
         : null;
   }

   @Nullable
   public ItemStack toItemStack() {
      if (this.itemId == 0) {
         return null;
      } else {
         Item item = Item.T(this.itemId);
         if (!item.isNotNull()) {
            return null;
         } else {
            ItemStack itemStack = ItemStack.S(item);
            itemStack.s(this.metadata);
            itemStack.Y(this.count);
            if (!this.enchantments.isEmpty()) {
               for (Entry<Short, Short> entry : this.enchantments.entrySet()) {
                  Enchantment enchantment = EnchantmentUtil.k(entry.getKey());
                  if (enchantment != null) {
                     itemStack.v(enchantment, entry.getValue());
                  }
               }
            }

            return itemStack;
         }
      }
   }
}
