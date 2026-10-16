/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.friend.ui;

import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;

public class OnlinePlayerPreviewSettings {
    final public NumberValue fieldOfView;
    final public BooleanValue levelView;
    final public NumberValue scale = NumberValue.create(this, "Size", "#.#", "", 0.5, 1.0, 2.0);
    final public NumberValue framesPerSecond = NumberValue.create(this, "FPS", "#", "", 1.0, 30.0, 60.0);

    public OnlinePlayerPreviewSettings() {
        this.fieldOfView = NumberValue.create(this, "FOV", "#", "", 50.0, 90.0, 150.0);
        this.levelView = BooleanValue.create(this, "Level view", true);
    }
}

