package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.utils.Base64Util;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public interface TextFilterCondition<T extends InventoryFilterCondition<T>> extends InventoryFilterCondition<T> {
   @UnmodifiableView List<String> getTexts();

   T removeText(String var1);

   T addText(String var1);

   T withMatchMode(TextMatchMode var1);

   TextMatchMode getMatchMode();

   T clearText();

   @Override
   default JsonObject toJson() {
      JsonObject jsonObject = InventoryFilterCondition.super.toJson();
      JsonArray jsonArray = new JsonArray();

      for (String text : this.getTexts()) {
         jsonArray.add(new JsonPrimitive(Base64Util.encodeUtf8Base64(text)));
      }

      jsonObject.addProperty("operator", this.getMatchMode().getName());
      jsonObject.add("text", jsonArray);
      return jsonObject;
   }
}
