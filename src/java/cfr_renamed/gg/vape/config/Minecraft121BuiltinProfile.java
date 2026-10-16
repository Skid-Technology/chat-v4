/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.config;

import gg.vape.config.BuiltinProfile;
import gg.vape.module.blatant.AutoAnchor;
import gg.vape.module.combat.AimAssist;
import gg.vape.module.combat.CrystalAura;
import gg.vape.module.combat.ShieldBreaker;
import gg.vape.module.combat.Sprint;
import gg.vape.module.combat.Triggerbot;
import gg.vape.module.utility.AutoMace;
import gg.vape.module.utility.AutoTotem;
import gg.vape.module.utility.WindCharge;
import gg.vape.wrapper.impl.ForgeVersion;

public class Minecraft121BuiltinProfile
extends BuiltinProfile {
    private final static String PROFILE_NAME = "Modern PVP";

    @Override
    protected void configureModules() {
        this.selectModule(Triggerbot.class);
        this.selectModule(AimAssist.class);
        this.selectModule(ShieldBreaker.class);
        this.selectModule(AutoMace.class);
        this.selectModule(Sprint.class);
        this.selectModule(CrystalAura.class);
        this.selectModule(AutoTotem.class);
        this.selectModule(AutoAnchor.class);
        this.selectModule(WindCharge.class);
    }

    public Minecraft121BuiltinProfile() {
        super(PROFILE_NAME);
    }

    @Override
    public boolean isApplicable() {
        return ForgeVersion.MC_1_21_0.d();
    }
}

