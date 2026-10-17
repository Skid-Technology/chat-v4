package gg.vape.module.render.bedplates;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import java.util.Objects;

public class BedPlateBlockStateKey {
   private final int metadata;
   private final boolean nullItem;
   private final int itemId;

   @Override
   public boolean equals(Object candidate) {
      if (this == candidate) {
         return true;
      } else if (candidate != null && this.getClass() == candidate.getClass()) {
         BedPlateBlockStateKey other = (BedPlateBlockStateKey)candidate;
         return ForgeVersion.MC_1_16_5.d() ? this.itemId == other.itemId : this.itemId == other.itemId && this.metadata == other.metadata;
      } else {
         return false;
      }
   }

   public BedPlateBlockStateKey(int itemId, int metadata) {
      this.itemId = itemId;
      this.metadata = metadata;
      this.nullItem = itemId == 0 || Item.T(itemId).isNull();
   }

   @Override
   public String toString() {
      return "BlockData{id=" + this.itemId + ", meta=" + this.metadata + '}';
   }

   public int getItemId() {
      return this.itemId;
   }

   public int getMetadata() {
      return this.metadata;
   }

   public boolean isNullItem() {
      return this.nullItem;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.itemId, this.metadata);
   }
}
