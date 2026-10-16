/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MNBTTagList;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.TagCompound;

public class TagList
extends Wrapper {
    public TagCompound getCompoundTagAt(int n) {
        return new TagCompound(MNBTTagList.w(TagList.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MNBTTagList_I, this.I, n));
    }

    public String a(int n) {
        return MNBTTagList.s(TagList.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MNBTTagList_I, this.I, n);
    }

    public int tagCount() {
        return MNBTTagList.R(TagList.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MNBTTagList_I, this.I);
    }

    public TagList(Object object) {
        super(object);
    }
}

