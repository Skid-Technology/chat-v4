package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.wrapper.impl.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface InventoryFilterCondition<T extends InventoryFilterCondition<?>> extends Cloneable {
   @Nullable
   default JsonObject toJson() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", this.getType().name());
      return jsonObject;
   }

   boolean matches(ItemStack var1);

   InventoryFilterConditionType getType();

   T copy();

   static InventoryFilterCondition<?> fromJson(JsonObject jsonObject) {
      InventoryFilterConditionType type = InventoryFilterConditionType.valueOf(jsonObject.get("type").getAsString());
      return (InventoryFilterCondition<?>)type.getJsonFactory().apply(jsonObject);
   }
}
