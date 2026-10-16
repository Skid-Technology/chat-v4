/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.target;

import gg.vape.value.BooleanValue;

public class TargetInfoSettings {
    final public BooleanValue potsUsedComparator;
    final public BooleanValue damageComparator;
    final public BooleanValue showHovered = BooleanValue.create(this, "Show Hovered", true, "Show information on a hovered entity if not attacking.");
    final public BooleanValue hitsComparator;
    final public BooleanValue comboCounter;

    public TargetInfoSettings() {
        this.damageComparator = BooleanValue.create(this, "Damage Comparator", true, "Measures strength of target compared to yourself\nConsiders armor and weapon damage");
        this.comboCounter = BooleanValue.create(this, "Combo Counter", true, "Shows how many hits in a direct row you've landed, or taken, from target");
        this.hitsComparator = BooleanValue.create(this, "Hits Comparator", false, "Measures how many hits you've landed compared to target");
        this.potsUsedComparator = BooleanValue.create(this, "Pots Used Comparator", false, "Measures how many heal pots you've used compared to target");
    }
}

