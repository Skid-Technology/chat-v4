/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.render.proj;

import gg.vape.wrapper.impl.EntityEnderPearl;
import java.awt.Color;

public interface IProjectile {
    default public float getCollisionRadius() {
        return 0.125f;
    }

    public boolean matches(EntityEnderPearl var1);

    default public float getCollisionHeight() {
        return 0.25f;
    }

    public Color getColor();
}

