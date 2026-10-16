/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.render.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.ui.click.frame.impl.hud.CoordinatesHudFrame;
import gg.vape.unmap.ModeOption;
import gg.vape.value.ModeValue;

public class CoordinatesHudModule
extends HudModule {
    final public ModeOption verticalMode = new ModeOption("Vertical");
    final public ModeOption horizontalMode = new ModeOption("Horizontal");
    final public ModeValue displayMode = ModeValue.create((Object)this, "Display Type", this.horizontalMode, this.horizontalMode, this.verticalMode);

    public CoordinatesHudModule() {
        super("Coords", HudModuleGroup.HUD, "coords", CoordinatesHudFrame.class);
        this.setSuffix("Shows your current XYZ coordinates");
        this.addValue(this.displayMode);
    }
}

