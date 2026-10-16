/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.unmap.ItemLimitData;
import gg.vape.value.ListValue;
import gg.vape.value.Value;
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

public class LimitValue
extends ListValue<ItemLimitData, LimitValue> {
    private final Color accentColor;
    final public static Color NEUTRAL_LIST_COLOR;
    final public static Color ALLOW_LIST_COLOR;
    private final int defaultTotalStacks;
    final public static Color BLOCK_LIST_COLOR;
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
        return new LimitValue(null, this.getId(), this.getName(), this.getAccentColor(), this.getDefaultTotalStacks(), (List)this.getDefaultValue());
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
            if (!entry.isMatchingEnabled()) continue;
            if (entry.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
            hasNoEnabledEntries = false;
        }
        return hasNoEnabledEntries;
    }

    @Override
    public boolean loadJson(JsonObject jsonObject) {
        if (jsonObject.get("id").getAsString().equalsIgnoreCase(this.getId())) {
            if (jsonObject.get("value").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("value").getAsJsonArray();
                ArrayList arrayList = new ArrayList((Collection)this.getValue());
                for (ItemLimitData itemLimitData : arrayList) {
                    this.removeEntry(itemLimitData);
                }
                for (JsonElement jsonElement : jsonArray) {
                    try {
                        ItemLimitData itemLimitData2 = new ItemLimitData("", -1);
                        itemLimitData2.loadJson(jsonElement.getAsJsonObject());
                        this.addEntry(itemLimitData2);
                    }
                    catch (Exception exception) {}
                }
            }
            return true;
        }
        return super.loadJson(jsonObject);
    }

    @Override
    public String getDisplayValue() {
        List list = (List)this.getValue();
        if (list.isEmpty()) {
            return "None";
        }
        if (list.size() == 1) {
            return ((ItemLimitData)list.get(0)).getName();
        }
        return ((ItemLimitData)list.get(0)).getName() + " +" + (list.size() - 1);
    }

    public boolean matchesOrEmpty(ItemStack itemStack) {
        if (((List)this.getValue()).isEmpty()) {
            return true;
        }
        for (ItemLimitData itemLimitData : (List)this.getValue()) {
            if (!itemLimitData.matches(itemStack)) continue;
            return true;
        }
        return false;
    }

    public boolean matchesHeldItem(boolean includeOffhand) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return false;
        }
        ItemStack mainHandItem = Minecraft.thePlayer().getHeldItemHand();
        if (this.matches(mainHandItem)) {
            return true;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            ItemStack offHandItem = player.i(EnumHand.offHand());
            if (includeOffhand && this.matches(offHandItem)) {
                return true;
            }
        }
        return false;
    }

    public int getDefaultTotalStacks() {
        return this.defaultTotalStacks;
    }

    public boolean matchesHeldItem() {
        return this.matchesHeldItem(this.includeOffhand);
    }

    public boolean doesNotMatch(ItemStack itemStack) {
        for (ItemLimitData itemLimitData : (List)this.getValue()) {
            if (!itemLimitData.matches(itemStack)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void parse(String string) {
    }

    @Override
    public LimitValue copyValueDefinition() {
        return this.copyDefinition();
    }

    public void removeEntry(ItemLimitData itemLimitData) {
        try {
            ((List)this.getValue()).remove(itemLimitData);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Color getAccentColor() {
        return this.accentColor;
    }

    @Override
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
        }
        if (!requireEntryWhenEmpty && ((List)this.getValue()).isEmpty()) {
            return true;
        }
        for (ItemLimitData itemLimitData : (List)this.getValue()) {
            if (!itemLimitData.matches(itemStack)) continue;
            return true;
        }
        return false;
    }

    public ItemLimitData findEntry(String name) {
        for (ItemLimitData entry : (List)this.getValue()) {
            if (!entry.getName().toLowerCase().equals(name.toLowerCase())) continue;
            return entry;
        }
        return null;
    }

    public static LimitValue create(Object owner, String id, String name, Color accentColor, ItemLimitData ... entries) {
        return LimitValue.createInternal(owner, id, name, accentColor, -1, Arrays.asList(entries));
    }

    @Override
    public ItemLimitData createEntry(String value, int legacyIndex) {
        return this.addEntry(value, legacyIndex);
    }

    public boolean matches(ItemStack itemStack) {
        return this.isValid(itemStack, false);
    }

    @Override
    public boolean isDefault() {
        List list = (List)this.getValue();
        List list2 = (List)this.getDefaultValue();
        return list.size() == list2.size() && list.equals(list2);
    }

    @Override
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
        return LimitValue.createInternal(owner, id, name, accentColor, -1, entries);
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    private LimitValue(Object owner, String id, String name, Color accentColor, int defaultTotalStacks, List<ItemLimitData> entries) {
        super(owner, id, name, entries);
        this.accentColor = accentColor;
        this.defaultTotalStacks = defaultTotalStacks;
    }

    @Override
    public Object createEntry(String string, int n) {
        return this.createEntry(string, n);
    }

    @Override
    public void setValue(Object object) {
        this.setValue((List)object);
    }

    @Override
    public void setDirectValue(Object object) {
        this.setDirectValue((List)object);
    }

    @Override
    public Value copyValueDefinition() {
        return this.copyValueDefinition();
    }

    static {
        ALLOW_LIST_COLOR = new Color(0, 170, 0);
        NEUTRAL_LIST_COLOR = new Color(170, 170, 170);
        BLOCK_LIST_COLOR = new Color(170, 0, 0);
    }
}

