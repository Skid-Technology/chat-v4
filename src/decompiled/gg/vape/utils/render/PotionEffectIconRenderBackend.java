/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.wrapper.impl.PotionEffect;

public interface PotionEffectIconRenderBackend {
    public void render(float var1, float var2, int var3, int var4, float var5);

    default public void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
        this.render(x, y, width, height, opacity);
    }

    public void capture(PotionEffect var1);

    public void dispose();
}

