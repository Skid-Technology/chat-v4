/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory;

import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.utility.armorswitch.ArmorMaterialType;
import org.jetbrains.annotations.Nullable;

public class ArmorItemMappingEntry
extends ItemMappingEntry {
    private final ArmorMaterialType armorMaterial;

    public ArmorItemMappingEntry(ItemMappingEntry entry, ArmorMaterialType armorMaterial) {
        this(entry.getResourceKey(), entry.getModernId(), entry.getLegacyIdString(), armorMaterial);
    }

    public ArmorItemMappingEntry(String name, String legacyName, @Nullable String modernName, ArmorMaterialType armorMaterial) {
        super(name, legacyName, modernName);
        this.armorMaterial = armorMaterial;
    }

    public ArmorMaterialType getArmorMaterial() {
        return this.armorMaterial;
    }
}

