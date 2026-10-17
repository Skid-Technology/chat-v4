/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MNBTTagCompound;
import gg.vape.wrapper.impl.TagBase;
import gg.vape.wrapper.impl.TagList;
import java.util.Map;
import java.util.Set;

public class TagCompound
extends TagBase {
    public Map<String, Object> getTagMap() {
        return MNBTTagCompound.a(TagCompound.vapeInstance.getMappingsMapperCompat().Dx, this.I);
    }

    public TagCompound(Object object) {
        super(object);
    }

    public TagList getTagList(String string, int n) {
        return new TagList(MNBTTagCompound.c(TagCompound.vapeInstance.getMappingsMapperCompat().Dx, this.I, string, n));
    }

    public boolean hasKeyOfType(String string, int n) {
        return MNBTTagCompound.z(TagCompound.vapeInstance.getMappingsMapperCompat().Dx, this.I, string, n);
    }

    public Set<String> getKeySet() {
        return MNBTTagCompound.a(TagCompound.vapeInstance.getMappingsMapperCompat().Dx, this.I).keySet();
    }

    public TagBase getTag(String string) {
        return new TagCompound(MNBTTagCompound.c(TagCompound.vapeInstance.getMappingsMapperCompat().Dx, this.I, string));
    }

    public short getShort(String string) {
        return MNBTTagCompound.z(TagCompound.vapeInstance.getMappingsMapperCompat().Dx, this.I, string);
    }
}

