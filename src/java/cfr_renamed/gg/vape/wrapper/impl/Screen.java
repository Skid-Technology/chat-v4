/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Holder;
import gg.vape.wrapper.impl.ResourceLocation;

public class Screen
extends Wrapper {
    private static int[] controlFlowState;

    public static void setControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public static int[] getControlFlowState() {
        return controlFlowState;
    }

    public Screen(Object screenHandle) {
        super(screenHandle);
    }

    public static ResourceLocation getMobEffectSprite(Holder holder) {
        Object spriteLocationHandle = Screen.vapeInstance.getMappings().screen.getMobEffectSprite(holder.getObject());
        return new ResourceLocation(spriteLocationHandle);
    }

    static {
        if (Screen.getControlFlowState() != null) {
            Screen.setControlFlowState(new int[1]);
        }
    }
}

