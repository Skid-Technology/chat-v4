package gg.vape.ui.click.frame.impl.main;

public enum ClickGuiSection {
   MODULES("Modules"),
   FRIENDS("Friends"),
   PROFILES("Profiles");

   private static final ClickGuiSection[] R = new ClickGuiSection[]{MODULES, FRIENDS, PROFILES};
   private final String a;

   public String A() {
      return this.a;
   }

   private ClickGuiSection(String string2) {
      this.a = string2;
   }

   static {
      String[] var10000 = new String[]{"FRIENDS", "PROFILES", "Profiles", "Modules", "Friends", "MODULES"};
   }
}
