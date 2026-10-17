package gg.vape.module.blatant.antibot;

import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.value.Value;
import java.awt.Color;

public class AntiBotBooleanValue extends Value<Integer, AntiBotBooleanValue> {
   private static final int RGB_MASK = 16777215;

   public AntiBotBooleanValue(Object owner, String name, Integer defaultColor) {
      super(owner, name, defaultColor);
   }

   public static AntiBotBooleanValue create(Object owner, String name, Integer defaultColor) {
      return new AntiBotBooleanValue(owner, name, defaultColor);
   }

   @Override
   public String getDisplayValue() {
      Integer color = this.getValue();
      return color == null ? "None" : "#" + String.format("%06X", color);
   }

   @Override
   public void parse(String value) {
      if (value != null && !value.isEmpty()) {
         try {
            if (value.startsWith("0x") || value.startsWith("0X")) {
               this.setValue(Integer.parseInt(value.substring(2), 16));
            } else if (value.startsWith("#")) {
               this.setValue(Integer.parseInt(value.substring(1), 16));
            } else {
               this.setValue(Integer.parseInt(value));
            }
         } catch (NumberFormatException var3) {
            this.setValue(this.getDefaultValue());
         }
      } else {
         this.setValue(this.getDefaultValue());
      }
   }

   @Override
   public Color getDisplayColor() {
      Integer color = this.getValue();
      return color == null ? Color.WHITE : new Color(color);
   }

   public AntiBotBooleanValue copy() {
      AntiBotBooleanValue copy = new AntiBotBooleanValue(null, this.getName(), this.getDefaultValue());
      copy.setValue(this.getValue());
      return copy;
   }

   public AntiBotBooleanValue copyValueDefinition() {
      return this.copy();
   }

   @Override
   public JsonObject toJson(boolean includeDefaults) {
      JsonObject jsonObject = this.toJson();
      Integer color = this.getValue();
      if (color != null) {
         jsonObject.addProperty("value", "0x" + Integer.toHexString(color).toUpperCase());
      }

      return jsonObject;
   }

   public void setColor(Color color) {
      if (color == null) {
         this.setValue(null);
      } else {
         this.setValue(color.getRGB() & 16777215);
      }
   }

   @Override
   public boolean loadJson(JsonObject jsonObject) {
      if (this.matchesJsonId(jsonObject)) {
         String string = ConfigJsonUtils.getString(jsonObject, "value");
         if (string != null) {
            this.parse(string);
         }

         this.notifyChangeListeners();
         return true;
      } else {
         return false;
      }
   }

   public static AntiBotBooleanValue createWithDescription(Object owner, String name, String description, Integer defaultColor) {
      AntiBotBooleanValue value = new AntiBotBooleanValue(owner, name, defaultColor);
      value.setDescription(description);
      return value;
   }
}
