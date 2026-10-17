/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import java.util.Objects;

class CachedTextTextureKey {
    String text;
    int color;

    public String getText() {
        return this.text;
    }

    public CachedTextTextureKey(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public boolean equals(Object object) {
        if (!(object instanceof CachedTextTextureKey)) {
            return false;
        }
        CachedTextTextureKey other = (CachedTextTextureKey)object;
        if (!other.getText().equals(this.getText())) {
            return false;
        }
        return other.getColor() == this.getColor();
    }

    public int hashCode() {
        return Objects.hash(this.getText(), this.getColor());
    }
}

