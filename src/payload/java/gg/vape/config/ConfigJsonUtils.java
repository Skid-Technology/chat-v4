package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.input.KeyboardCodeUtil;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.Base64Util;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

public class ConfigJsonUtils {
   private static GuiComponent[] sharedGuiComponents;
   private static final String BASE64_PREFIX = "b64:";

   @Nullable
   public static Boolean getBoolean(JsonObject object, String key) {
      return getPrimitiveValue(object, key, JsonPrimitive::getAsBoolean);
   }

   public static void setSharedGuiComponents(GuiComponent[] components) {
      sharedGuiComponents = components;
   }

   public static GuiComponent[] getSharedGuiComponents() {
      return sharedGuiComponents;
   }

   @Nullable
   public static String getString(JsonObject object, String key) {
      return getElementValue(object, key, ConfigJsonUtils::getStringValue);
   }

   public static boolean getBooleanOrFalse(JsonObject object, String key) {
      Boolean value = getBoolean(object, key);
      return value != null ? value : false;
   }

   public static String getDecodedStringOrEmpty(JsonObject object, String key) {
      if (object.get(key) != null && !object.get(key).isJsonNull()) {
         String value = object.get(key).getAsString();
         if (value.startsWith(BASE64_PREFIX)) {
            value = Base64Util.decodeUtf8Base64(value.split(":")[1]);
         }

         return value;
      } else {
         return "";
      }
   }

   @Nullable
   public static Long getLong(JsonObject object, String key) {
      return getPrimitiveValue(object, key, JsonPrimitive::getAsLong);
   }

   @Nullable
   public static JsonArray getJsonArray(JsonObject object, String key) {
      return getElementValue(object, key, ConfigJsonUtils::getJsonArrayValue);
   }

   private static Object applyPrimitiveConverter(Function converter, JsonElement element) {
      return !element.isJsonPrimitive() ? null : converter.apply(element.getAsJsonPrimitive());
   }

   private static JsonArray getJsonArrayValue(JsonElement element) {
      return !element.isJsonArray() ? null : element.getAsJsonArray();
   }

   @Nullable
   private static <T> T getPrimitiveValue(JsonObject object, String key, Function<JsonPrimitive, T> converter) {
      return getElementValue(object, key, element -> (T)applyPrimitiveConverter(converter, element));
   }

   @Nullable
   public static String getDecodedBase64String(JsonObject object, String key) {
      String encodedValue = getString(object, key);
      return encodedValue == null ? null : Base64Util.decodeUtf8Base64(encodedValue);
   }

   private static IllegalArgumentException preserveIllegalArgumentException(IllegalArgumentException error) {
      return error;
   }

   @Nullable
   public static UUID getUuid(JsonObject object, String key) {
      String value = getString(object, key);
      if (value == null) {
         return null;
      } else {
         try {
            return UUID.fromString(value);
         } catch (IllegalArgumentException var4) {
            return null;
         }
      }
   }

   @Nullable
   public static Double getDouble(JsonObject object, String key) {
      return getPrimitiveValue(object, key, JsonPrimitive::getAsDouble);
   }

   public static List<Integer> parseInputCodes(JsonArray array, boolean convertLegacyKeyCodes) {
      List<Integer> serializedCodes = getIntegerList(array);
      ArrayList<Integer> inputCodes = new ArrayList<>();

      for (Integer inputCode : serializedCodes) {
         if (inputCode != 0) {
            if (convertLegacyKeyCodes && inputCode > 0) {
               inputCode = KeyboardCodeUtil.convertLegacyKeyCode(inputCode);
            }

            if (inputCode != 0 && inputCode != 27) {
               inputCodes.add(inputCode);
            }
         }
      }

      return inputCodes;
   }

   @Nullable
   public static Short getShort(JsonObject object, String key) {
      return getPrimitiveValue(object, key, JsonPrimitive::getAsShort);
   }

   private static JsonObject getJsonObjectValue(JsonElement element) {
      return !element.isJsonObject() ? null : element.getAsJsonObject();
   }

   @Nullable
   public static Integer getInteger(JsonObject object, String key) {
      return getPrimitiveValue(object, key, JsonPrimitive::getAsInt);
   }

   public static List<Integer> getIntegerList(JsonArray array) {
      ArrayList<Integer> values = new ArrayList<>();

      for (JsonElement element : array) {
         JsonPrimitive primitive;
         if (!element.isJsonNull() && element.isJsonPrimitive() && (primitive = element.getAsJsonPrimitive()).isNumber()) {
            values.add(primitive.getAsInt());
         }
      }

      return values;
   }

   @Nullable
   public static JsonObject getJsonObject(JsonObject object, String key) {
      return getElementValue(object, key, ConfigJsonUtils::getJsonObjectValue);
   }

   @Nullable
   private static <T> T getElementValue(JsonObject object, String key, Function<JsonElement, T> converter) {
      JsonElement element = object.get(key);
      return element != null && !element.isJsonNull() ? converter.apply(element) : null;
   }

   private static String getStringValue(JsonElement element) {
      return !element.isJsonPrimitive() ? null : element.getAsString();
   }

   static {
      setSharedGuiComponents(new GuiComponent[5]);
   }
}
