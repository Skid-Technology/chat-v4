/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MBiomeRegistrySwitch;
import gg.vape.ui.click.component.GuiComponent;

public class MBiomeRegistryName
extends Mapping {
    private final static String NAME_FIELD_NAME = "name";
    private final MappingField nameField = this.J("name", true, String.class);

    public MBiomeRegistryName() {
        this(MBiomeRegistrySwitch.getBiomeRegistryControlFlowState());
    }

    private MBiomeRegistryName(int[] biomeRegistryControlFlowState) {
        super(MappedClasses.var_java_lang_Class_h);
        if (biomeRegistryControlFlowState != null) {
            return;
        }
        GuiComponent.setLegacyComponentState(new GuiComponent[5]);
    }

    private String readName(Object registryName) {
        return (String)this.nameField.getObject(registryName);
    }

    public static String getName(MBiomeRegistryName mapping, Object registryName) {
        return mapping.readName(registryName);
    }
}

