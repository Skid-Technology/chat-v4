/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.render.hud;

import gg.vape.unmap.INamed;
import java.util.ArrayList;
import java.util.List;

public class HudModuleGroup
implements INamed {
    final public static HudModuleGroup GAME;
    final public static List<HudModuleGroup> GROUPS;
    private final String name;
    private final String key;
    final public static HudModuleGroup ALL;
    final public static HudModuleGroup HUD;
    final public static HudModuleGroup FAVORITE;

    public String toString() {
        return this.name;
    }

    public HudModuleGroup(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public HudModuleGroup(String name) {
        this(name, "");
    }

    public static List<HudModuleGroup> getGroups() {
        return GROUPS;
    }

    @Override
    public String getName() {
        return this.name;
    }

    static {
        FAVORITE = new HudModuleGroup("Favorite");
        ALL = new HudModuleGroup("All");
        HUD = new HudModuleGroup("HUD");
        GAME = new HudModuleGroup("Game");
        GROUPS = new ArrayList<HudModuleGroup>();
        GROUPS.add(FAVORITE);
        GROUPS.add(ALL);
        GROUPS.add(HUD);
        GROUPS.add(GAME);
    }
}

