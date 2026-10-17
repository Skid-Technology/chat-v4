/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.DamageSource;
import gg.vape.wrapper.impl.EnchantmentModifier;

public class EnchantmentModifierDamage
extends EnchantmentModifier {
    public DamageSource getSource() {
        return new DamageSource(EnchantmentModifierDamage.vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.getSource(this.I));
    }

    public void setSource(DamageSource damageSource) {
        EnchantmentModifierDamage.vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.setSource(this.I, damageSource.getObject());
    }

    public int getDamageModifierValue() {
        return EnchantmentModifierDamage.vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.getDamageModifier(this.I);
    }

    public void setDamageModifier(int damageModifier) {
        EnchantmentModifierDamage.vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.setDamageModifier(this.I, damageModifier);
    }

    public EnchantmentModifierDamage(Object handle) {
        super(handle);
    }
}

