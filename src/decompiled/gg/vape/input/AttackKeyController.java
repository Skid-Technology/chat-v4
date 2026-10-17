/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.input;

import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.KeyBindingHelper;
import gg.vape.module.Mod;
import gg.vape.wrapper.impl.Minecraft;

public class AttackKeyController {
    public static void pressAttackKey() {
        KeyBindingHelper.updateKeyBinding(Minecraft.a_w3_0_S().F(), true, true);
    }

    public static void releaseAttackKey() {
        KeyBindingHelper.updateKeyBinding(Minecraft.a_w3_0_S().F(), false, false);
    }

    public static boolean requestSyntheticAttack(Mod module) {
        SyntheticAttackRequestEvent event = new SyntheticAttackRequestEvent(module);
        if (event.fire()) {
            return false;
        }
        AttackKeyController.pressAttackKey();
        return true;
    }
}

