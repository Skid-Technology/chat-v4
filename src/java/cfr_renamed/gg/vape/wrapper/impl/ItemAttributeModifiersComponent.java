/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ItemAttributeModifiersComponent$Entry;
import java.util.ArrayList;
import java.util.List;

public class ItemAttributeModifiersComponent
extends Wrapper {
    public List<Object> getRawModifiers() {
        return ItemAttributeModifiersComponent.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MItemAttributeModifiersComponent_r.getModifiers(this.I);
    }

    public List<ItemAttributeModifiersComponent$Entry> getEntries() {
        ArrayList<ItemAttributeModifiersComponent$Entry> entries = new ArrayList<ItemAttributeModifiersComponent$Entry>();
        for (Object modifier : this.getRawModifiers()) {
            entries.add(new ItemAttributeModifiersComponent$Entry(modifier));
        }
        return entries;
    }

    public ItemAttributeModifiersComponent(Object wrappedObject) {
        super(wrappedObject);
    }
}

