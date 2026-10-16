/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.datas;

import gg.vape.utils.datas.HSBData;
import java.awt.Color;

public class HSBAData
extends HSBData {
    private final float var_float_I;

    public HSBAData(int n, int n2, int n3, int n4, Color color, float f) {
        super(n, n2, n3, n4, color);
        this.var_float_I = f;
    }

    public float n() {
        return this.var_float_I;
    }
}

