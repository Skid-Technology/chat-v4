/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MMappedClassSlotNRegistration
extends Mapping {
    private static int var_int_g;

    public MMappedClassSlotNRegistration() {
        super(MappedClasses.var_java_lang_Class_N);
    }

    public static void A(int n) {
        var_int_g = n;
    }

    public static int W() {
        int n = MMappedClassSlotNRegistration.r();
        return 0;
    }

    public static int r() {
        return var_int_g;
    }

    static {
        if (MMappedClassSlotNRegistration.r() == 0) {
            MMappedClassSlotNRegistration.A(63);
        }
    }
}

