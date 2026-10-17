package gg.vape.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gg.vape.input.BindSet;

public class BindValue extends Value<BindSet, BindValue> {
   public BindValue(Object owner, String name, BindSet bindSet) {
      super(owner, name, bindSet);
   }

   public BindValue copyDefinition() {
      return new BindValue(null, this.getId(), this.getValue());
   }

   public BindValue copyValueDefinition() {
      return this.copyDefinition();
   }

   @Override
   public void parse(String serializedValue) {
   }

   @Override
   public JsonObject toJson(boolean includeValue) {
      JsonObject jsonObject = super.toJson(includeValue);
      jsonObject.add("binds", this.getValue().serializeBoundInputs());
      jsonObject.add("value", null);
      return jsonObject;
   }

   @Override
   public boolean isDefault() {
      for (Integer defaultKey : this.getValue().getDefaultKeys()) {
         if (!this.getValue().getBoundInputs().contains(defaultKey)) {
            return false;
         }
      }

      return this.getValue().getBoundInputs().size() == this.getValue().getDefaultKeys().size();
   }

   @Override
   public boolean loadJson(JsonObject jsonObject) {
      JsonArray binds = jsonObject.getAsJsonArray("binds");
      this.getValue().loadBoundInputs(binds, false);
      return super.loadJson(jsonObject);
   }

   public static BindValue createWithKey(Object owner, String name, int keyCode) {
      return new BindValue(owner, name, new BindSet(keyCode));
   }

   public static BindValue createEmpty(Object owner, String name) {
      return new BindValue(owner, name, new BindSet());
   }
}
