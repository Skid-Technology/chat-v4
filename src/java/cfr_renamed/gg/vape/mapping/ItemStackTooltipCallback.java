/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.mapping.MappedClasses;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.TagBase;
import gg.vape.wrapper.impl.TagCompound;
import gg.vape.wrapper.impl.TextComponentString;
import java.util.ArrayList;
import java.util.List;

public class ItemStackTooltipCallback
implements InsertedEventCallback {
    private final Object var_java_lang_Object_P;
    private final Object var_java_lang_Object_z;
    public static boolean var_boolean_T;
    private List<Object> var_java_util_List_java_lang_Object__U;
    private final Object var_java_lang_Object_M;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_q;

    @Override
    public boolean fire() {
        if (var_boolean_T) {
            var_boolean_T = false;
            return false;
        }
        ItemStack itemStack = new ItemStack(this.var_java_lang_Object_z);
        EntityPlayer entityPlayer = new EntityPlayer(this.var_java_lang_Object_P);
        var_boolean_T = true;
        this.var_java_util_List_java_lang_Object__U = (List)itemStack.A(entityPlayer, this.var_java_lang_Object_M);
        if (Vape.INSTANCE.getClientSettings().showNbtTags.getEffectiveValue().booleanValue()) {
            if (itemStack.isNull() || itemStack.l() == null || this.var_java_util_List_java_lang_Object__U == null) {
                return true;
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String string : new TagCompound(itemStack.l()).getKeySet()) {
                if (string.equals("display") || string.equals("Unbreakable") || string.equals("HideFlags") || string.equals("overrideMeta") || string.equals("AttributeModifiers")) continue;
                TagBase tagBase = new TagCompound(itemStack.l()).getTag(string);
                arrayList.add("");
                if (tagBase.isInstance(MappedClasses.Yg)) {
                    TagCompound tagCompound = new TagCompound(tagBase);
                    arrayList.add("\u00a79" + string);
                    for (String string2 : tagCompound.getKeySet()) {
                        TagBase tagBase2 = tagCompound.getTag(string2);
                        if (tagBase2.isInstance(MappedClasses.Yg) || tagBase2.isInstance(MappedClasses.qt)) continue;
                        arrayList.add(string2 + ": " + tagBase2.getObject().toString());
                    }
                    continue;
                }
                arrayList.add(string + ": " + tagBase.getObject().toString());
            }
            if (arrayList.isEmpty()) {
                return true;
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                for (String string : arrayList) {
                    this.var_java_util_List_java_lang_Object__U.add(TextComponentString.create(string).getObject());
                }
            } else {
                this.var_java_util_List_java_lang_Object__U.addAll(arrayList);
            }
        }
        return true;
    }

    public static GuiComponent[] c() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_q;
    }

    public static void m(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_q = guiComponentArray;
    }

    public ItemStackTooltipCallback(Object object, Object object2, boolean bl) {
        this.var_java_lang_Object_z = object;
        this.var_java_lang_Object_P = object2;
        this.var_java_lang_Object_M = bl;
    }

    public ItemStackTooltipCallback(Object object, Object object2, Object object3) {
        this.var_java_lang_Object_z = object;
        this.var_java_lang_Object_P = object2;
        this.var_java_lang_Object_M = object3;
    }

    public List<?> getTooltip() {
        return this.var_java_util_List_java_lang_Object__U;
    }

    static {
        ItemStackTooltipCallback.m(new GuiComponent[4]);
        var_boolean_T = false;
    }
}

