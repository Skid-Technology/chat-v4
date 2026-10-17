package gg.vape.module.render.hud;

import gg.vape.unmap.INamed;
import java.util.ArrayList;
import java.util.List;

public class HudModuleGroup implements INamed {
   public static final HudModuleGroup GAME = new HudModuleGroup("Game");
   public static final List<HudModuleGroup> GROUPS = new ArrayList<>();
   private final String name;
   private final String key;
   public static final HudModuleGroup ALL = new HudModuleGroup("All");
   public static final HudModuleGroup HUD = new HudModuleGroup("HUD");
   public static final HudModuleGroup FAVORITE = new HudModuleGroup("Favorite");

   @Override
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
      GROUPS.add(FAVORITE);
      GROUPS.add(ALL);
      GROUPS.add(HUD);
      GROUPS.add(GAME);
   }
}
