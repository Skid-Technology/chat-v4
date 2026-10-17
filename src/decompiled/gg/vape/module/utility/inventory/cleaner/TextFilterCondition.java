/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.UnmodifiableView
 */
package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.TextMatchMode;
import gg.vape.utils.Base64Util;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public interface TextFilterCondition<T extends InventoryFilterCondition<T>>
extends InventoryFilterCondition<T> {
    public @UnmodifiableView List<String> getTexts();

    public T removeText(String var1);

    public T addText(String var1);

    public T withMatchMode(TextMatchMode var1);

    public TextMatchMode getMatchMode();

    public T clearText();

    @Override
    default public JsonObject toJson() {
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

