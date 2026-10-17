/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Locale;

public class MTextFormatting
extends Mapping {
    private MappingMethod getColorMethod;
    private MappingMethod getNameMethod;
    private MappingMethod getByNameMethod;

    public Integer getColor(Object formatting) {
        if (formatting == null) {
            return null;
        }
        if (ForgeVersion.MC_26_2.d()) {
            int n3 = ((Enum)formatting).ordinal();
            int[] nArray = new int[]{0, 170, 43520, 43690, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF};
            return n3 >= 0 && n3 < nArray.length ? Integer.valueOf(nArray[n3]) : null;
        }
        return (Integer)this.getColorMethod.invokeObject(formatting, new Object[0]);
    }

    public MTextFormatting() {
        this(BlockData.W());
    }

    private MTextFormatting(String[] stringArray) {
        super(MappedClasses.l5);
        if (ForgeVersion.MC_1_21_4.d() && ForgeVersion.MC_26_2.v()) {
            this.getNameMethod = this.Y("getName", true, String.class, new Class[0]);
            this.getColorMethod = this.Y("getColor", true, Integer.class, new Class[0]);
            this.getByNameMethod = this.registerStaticMethod("getByName", true, MappedClasses.l5, String.class);
        } else if (ForgeVersion.MC_1_21_4.v()) {
            this.getByNameMethod = this.registerStaticMethod("getByName", true, MappedClasses.l5, String.class);
        }
    }

    public Object getByName(String name) {
        if (name == null) {
            return null;
        }
        if (ForgeVersion.MC_26_2.d()) {
            String string2 = name.toUpperCase(Locale.ROOT).replace(' ', '_').replace('-', '_');
            for (Object t3 : MappedClasses.l5.getEnumConstants()) {
                if (!((Enum)t3).name().equals(string2)) continue;
                return t3;
            }
            return null;
        }
        return this.getByNameMethod.invokeObject(null, name);
    }

    public String getName(Object formatting) {
        if (formatting == null) {
            return null;
        }
        if (ForgeVersion.MC_26_2.d()) {
            return ((Enum)formatting).name().toLowerCase(Locale.ROOT);
        }
        return (String)this.getNameMethod.invokeObject(formatting, new Object[0]);
    }
}

