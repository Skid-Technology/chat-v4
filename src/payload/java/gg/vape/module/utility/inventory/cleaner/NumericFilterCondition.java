package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;

public interface NumericFilterCondition<T extends InventoryFilterCondition<T>> extends InventoryFilterCondition<T> {
   String OPERATOR_KEY = "operator";

   T withOperator(ComparisonOperator var1);

   ComparisonOperator getOperator();

   T parseValue(String var1) throws NumberFormatException;

   String getValueText();

   @Override
   default JsonObject toJson() {
      JsonObject jsonObject = InventoryFilterCondition.super.toJson();
      jsonObject.addProperty("operator", this.getOperator().getName());
      return jsonObject;
   }
}
