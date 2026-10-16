/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.TimerUtil;
import gg.vape.wrapper.impl.ForgeVersion;

public class MI18n
extends Mapping {
    private final MappingField languageField = ForgeVersion.MC_26_2.d() ? null : (ForgeVersion.MC_1_16_5.d() ? this.registerStaticField("field_239501_a_", true, MappedClasses.Fn) : this.registerStaticField("i18nLocale", true, MappedClasses.Fn));
    private final MappingMethod formatMethod = this.registerStaticMethod(ForgeVersion.MC_26_2.d() ? "get" : "format", true, String.class, String.class, Object[].class);

    public MI18n() {
        this(TimerUtil.p());
    }

    private MI18n(String timerState) {
        super(MappedClasses.Vm);
    }

    public String format(String translationKey, Object ... arguments) {
        return (String)this.formatMethod.invokeObject(null, translationKey, arguments);
    }

    public Object getLanguage() {
        if (this.languageField == null) {
            return null;
        }
        return this.languageField.getObject(null);
    }
}

