/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MMappedClassSlotFp;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MLanguage
extends Mapping {
    private final MappingField unicodeField = ForgeVersion.MC_1_16_5.d() ? this.registerInstanceFieldForOwner(MappedClasses.Vi, "field_239496_d_", Wrapper.isNativeAvailable, Boolean.TYPE) : (ForgeVersion.MC_1_7_10.L() ? this.J("field_135029_d", Wrapper.isNativeAvailable, Boolean.TYPE) : this.J("unicode", true, Boolean.TYPE));

    public boolean isUnicode(Object languageHandle) {
        return this.unicodeField.getBoolean(languageHandle);
    }

    public MLanguage() {
        this(MMappedClassSlotFp.l());
    }

    private MLanguage(GuiComponent[] legacyComponentState) {
        super(MappedClasses.Fn);
    }
}

