package gg.vape.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.unmap.ItemLimitData;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LimitValue extends ListValue<ItemLimitData, LimitValue> {
   private final Color accentColor;
   public static final Color NEUTRAL_LIST_COLOR = new Color(170, 170, 170);
   public static final Color ALLOW_LIST_COLOR = new Color(0, 170, 0);
   private final int defaultTotalStacks;
   public static final Color BLOCK_LIST_COLOR = new Color(170, 0, 0);
   private boolean includeOffhand = false;

   public void replaceEntries(List<ItemLimitData> entries) {
      ((List)super.getValue()).clear();
      ((List)super.getValue()).addAll(entries);
   }

   public static LimitValue createInternal(Object owner, String id, String name, Color accentColor, int defaultTotalStacks, List<ItemLimitData> entries) {
      LimitValue limitValue = new LimitValue(owner, id, name, accentColor, defaultTotalStacks, entries);
      ((List)limitValue.getValue()).addAll(entries);
      return limitValue;
   }

   public LimitValue copyDefinition() {
      return new LimitValue(
         null, this.getId(), this.getName(), this.getAccentColor(), this.getDefaultTotalStacks(), (List<ItemLimitData>)this.getDefaultValue()
      );
   }

   public ItemLimitData addEntry(String value, int legacyIndex) {
      return this.addEntry(new ItemLimitData(value, legacyIndex));
   }

   public ItemLimitData addEntry(ItemLimitData itemLimitData) {
      ((List)this.getValue()).add(itemLimitData);
      if (this.defaultTotalStacks == -1) {
         itemLimitData.setTotalStacks(-1);
      }

      return itemLimitData;
   }

   public boolean matchesEnabledNameOrHasNoEnabledEntries(String name) {
      boolean hasNoEnabledEntries = true;

      for (ItemLimitData entry : (List)this.getValue()) {
         if (entry.isMatchingEnabled()) {
            if (entry.getName().toLowerCase().equals(name.toLowerCase())) {
               return true;
            }

            hasNoEnabledEntries = false;
         }
      }

      return hasNoEnabledEntries;
   }

   @Override
   public boolean loadJson(JsonObject jsonObject) {
      if (!jsonObject.get("id").getAsString().equalsIgnoreCase(this.getId())) {
         return super.loadJson(jsonObject);
      } else {
         if (jsonObject.get("value").isJsonArray()) {
            JsonArray jsonArray = jsonObject.get("value").getAsJsonArray();

            for (ItemLimitData itemLimitData : new ArrayList((Collection)this.getValue())) {
               this.removeEntry(itemLimitData);
            }

            for (JsonElement jsonElement : jsonArray) {
               try {
                  ItemLimitData itemLimitData2 = new ItemLimitData("", -1);
                  itemLimitData2.loadJson(jsonElement.getAsJsonObject());
                  this.addEntry(itemLimitData2);
               } catch (Exception var7) {
               }
            }
         }

         return true;
      }
   }

   @Override
   public String getDisplayValue() {
      List<ItemLimitData> list = (List<ItemLimitData>)this.getValue();
      if (list.isEmpty()) {
         return "None";
      } else {
         return list.size() == 1 ? list.get(0).getName() : list.get(0).getName() + " +" + (list.size() - 1);
      }
   }

   public boolean matchesOrEmpty(ItemStack itemStack) {
      if (((List)this.getValue()).isEmpty()) {
         return true;
      } else {
         for (ItemLimitData itemLimitData : (List)this.getValue()) {
            if (itemLimitData.matches(itemStack)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean matchesHeldItem(boolean includeOffhand) {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (player.isNull()) {
         return false;
      } else {
         ItemStack mainHandItem = Minecraft.thePlayer().getHeldItemHand();
         if (this.matches(mainHandItem)) {
            return true;
         } else {
            if (ForgeVersion.MC_1_12_2.d()) {
               ItemStack offHandItem = player.i(EnumHand.offHand());
               if (includeOffhand && this.matches(offHandItem)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public int getDefaultTotalStacks() {
      return this.defaultTotalStacks;
   }

   public boolean matchesHeldItem() {
      return this.matchesHeldItem(this.includeOffhand);
   }

   public boolean doesNotMatch(ItemStack itemStack) {
      for (ItemLimitData itemLimitData : (List)this.getValue()) {
         if (itemLimitData.matches(itemStack)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void parse(String string) {
   }

   public LimitValue copyValueDefinition() {
      return this.copyDefinition();
   }

   public void removeEntry(ItemLimitData itemLimitData) {
      try {
         ((List)this.getValue()).remove(itemLimitData);
      } catch (Exception var3) {
      }
   }

   public Color getAccentColor() {
      return this.accentColor;
   }

   public void setDirectValue(List<ItemLimitData> list) {
      this.setEntries(list);
   }

   public void setEntries(List<ItemLimitData> entries) {
      super.setDirectValue(entries);
   }

   @Override
   public JsonObject toJson(boolean bl) {
      JsonObject jsonObject = this.toJson();
      JsonArray jsonArray = new JsonArray();

      for (ItemLimitData itemLimitData : (List)this.getValue()) {
         jsonArray.add(itemLimitData.toJson());
      }

      jsonObject.add("value", jsonArray);
      return jsonObject;
   }

   public boolean isValid(ItemStack itemStack, boolean requireEntryWhenEmpty) {
      if (requireEntryWhenEmpty && ((List)this.getValue()).isEmpty()) {
         return false;
      } else if (!requireEntryWhenEmpty && ((List)this.getValue()).isEmpty()) {
         return true;
      } else {
         for (ItemLimitData itemLimitData : (List)this.getValue()) {
            if (itemLimitData.matches(itemStack)) {
               return true;
            }
         }

         return false;
      }
   }

   public ItemLimitData findEntry(String name) {
      for (ItemLimitData entry : (List)this.getValue()) {
         if (entry.getName().toLowerCase().equals(name.toLowerCase())) {
            return entry;
         }
      }

      return null;
   }

   public static LimitValue create(Object owner, String id, String name, Color accentColor, ItemLimitData... entries) {
      return createInternal(owner, id, name, accentColor, -1, Arrays.asList(entries));
   }

   public ItemLimitData createEntry(String value, int legacyIndex) {
      return this.addEntry(value, legacyIndex);
   }

   public boolean matches(ItemStack itemStack) {
      return this.isValid(itemStack, false);
   }

   @Override
   public boolean isDefault() {
      List<ItemLimitData> list = (List<ItemLimitData>)this.getValue();
      List<ItemLimitData> list2 = (List<ItemLimitData>)this.getDefaultValue();
      return list.size() == list2.size() && list.equals(list2);
   }

   public void setValue(List<ItemLimitData> entries) {
      this.replaceEntries(entries);
   }

   @Override
   public void reset() {
      super.setValue(new ArrayList((Collection)this.getDefaultValue()));
   }

   public LimitValue setIncludeOffhand(boolean includeOffhand) {
      this.includeOffhand = includeOffhand;
      return this;
   }

   public static LimitValue create(Object owner, String id, String name, Color accentColor, List<ItemLimitData> entries) {
      return createInternal(owner, id, name, accentColor, -1, entries);
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   private LimitValue(Object owner, String id, String name, Color accentColor, int defaultTotalStacks, List<ItemLimitData> entries) {
      super(owner, id, name, entries);
      this.accentColor = accentColor;
      this.defaultTotalStacks = defaultTotalStacks;
   }
}
