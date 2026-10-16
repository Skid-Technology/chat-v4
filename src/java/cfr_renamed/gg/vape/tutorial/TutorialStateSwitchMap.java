/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.tutorial;

import gg.vape.tutorial.TutorialState;

public class TutorialStateSwitchMap {
    final public static int[] var_int_arr_D;

    TutorialStateSwitchMap() {
    }

    static {
        var_int_arr_D = new int[TutorialState.values().length];
        try {
            TutorialStateSwitchMap.var_int_arr_D[TutorialState.WELCOME.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            TutorialStateSwitchMap.var_int_arr_D[TutorialState.COMPLETED_ALL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            TutorialStateSwitchMap.var_int_arr_D[TutorialState.COMPLETED_TUTORIAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

