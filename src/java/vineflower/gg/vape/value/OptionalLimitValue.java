package gg.vape.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OptionalLimitValue extends ListValue<OptionalLimitEntry, OptionalLimitValue> {
   public static final Color ALLOW_LIST_COLOR = new Color(0, 170, 0);
   public static final Color BLOCK_LIST_COLOR = new Color(170, 0, 0);
   private final Color accentColor;
   public static final Color NEUTRAL_LIST_COLOR = new Color(170, 170, 170);

   public List<String> getEnabledValues() {
      ArrayList<String> enabledValues = new ArrayList<>();

      for (OptionalLimitEntry entry : (List)this.getValue()) {
         if (entry.isEntryEnabled()) {
            enabledValues.add(entry.getValue());
         }
      }

      return enabledValues;
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   @Override
   public void parse(String string) {
   }

   public OptionalLimitValue copyDefinition() {
      return new OptionalLimitValue(null, this.getId(), this.getName(), this.getAccentColor());
   }

   public OptionalLimitEntry createEntry(String value, int legacyIndex) {
      return this.addEntry(value, legacyIndex);
   }

   private OptionalLimitValue(Object owner, String id, String name, Color accentColor) {
      super(owner, id, name);
      this.accentColor = accentColor;
   }

   public void setValue(List<OptionalLimitEntry> entries) {
      this.replaceEntries(entries);
   }

   public OptionalLimitValue copyValueDefinition() {
      return this.copyDefinition();
   }

   @Override
   public JsonObject toJson(boolean bl) {
      JsonObject jsonObject = this.toJson();
      JsonArray jsonArray = new JsonArray();

      for (OptionalLimitEntry entry : (List)this.getValue()) {
         jsonArray.add(entry.toJson());
      }

      jsonObject.add("value", jsonArray);
      return jsonObject;
   }

   public OptionalLimitEntry addEntry(String value) {
      OptionalLimitEntry entry = new OptionalLimitEntry(value);
      ((List)this.getValue()).add(entry);
      return entry;
   }

   public OptionalLimitEntry addEntry(String value, int legacyIndex) {
      return this.addEntry(value);
   }

   @Override
   public boolean loadJson(JsonObject jsonObject) {
      if (!jsonObject.get("id").getAsString().equalsIgnoreCase(this.getId())) {
         return super.loadJson(jsonObject);
      } else {
         if (jsonObject.get("value").isJsonArray()) {
            JsonArray jsonArray = jsonObject.get("value").getAsJsonArray();

            for (OptionalLimitEntry entry : new ArrayList((Collection)this.getValue())) {
               this.removeEntry(entry);
            }

            for (JsonElement jsonElement : jsonArray) {
               try {
                  OptionalLimitEntry entry = this.addEntry("");
                  entry.loadJson(jsonElement.getAsJsonObject());
               } catch (Exception var7) {
               }
            }
         }

         return true;
      }
   }

   public void replaceEntries(List<OptionalLimitEntry> entries) {
      ArrayList<OptionalLimitEntry> replacementEntries = new ArrayList<>(entries);
      ArrayList<OptionalLimitEntry> existingEntries = new ArrayList<>((Collection<? extends OptionalLimitEntry>)this.getValue());
      ((List)this.getValue()).clear();

      for (OptionalLimitEntry entry : existingEntries) {
         this.removeEntry(entry);
      }

      for (OptionalLimitEntry entry : replacementEntries) {
         this.addEntry(entry.getValue());
      }
   }

   public boolean matches(String value, boolean defaultWhenEmpty) {
      if (((List)this.getValue()).isEmpty()) {
         return defaultWhenEmpty;
      } else {
         for (OptionalLimitEntry entry : (List)this.getValue()) {
            if (entry.isEntryEnabled() && entry.getValue().equalsIgnoreCase(value)) {
               return true;
            }
         }

         return false;
      }
   }

   public static OptionalLimitValue createWithDescription(Object owner, String id, String name, String description, Color accentColor, List<String> values) {
      OptionalLimitValue optionalLimitValue = new OptionalLimitValue(owner, id, name, accentColor);

      for (String value : values) {
         optionalLimitValue.addEntry(value);
      }

      optionalLimitValue.setDefaultValue(new ArrayList((Collection)optionalLimitValue.getValue()));
      return optionalLimitValue.setDescription(description);
   }

   public static OptionalLimitValue create(Object owner, String id, String name, Color accentColor, String... values) {
      return createWithDescription(owner, id, name, "List of Names/Strings", accentColor, Arrays.asList(values));
   }

   public void removeEntry(OptionalLimitEntry entry) {
      try {
         ((List)this.getValue()).remove(entry);
      } catch (Exception var3) {
      }
   }

   @Override
   public String getDisplayValue() {
      List<OptionalLimitEntry> list = (List<OptionalLimitEntry>)this.getValue();
      if (list.isEmpty()) {
         return "None";
      } else {
         return list.size() == 1 ? list.get(0).getValue() : list.get(0).getValue() + " +" + (list.size() - 1);
      }
   }

   public Color getAccentColor() {
      return this.accentColor;
   }
}
