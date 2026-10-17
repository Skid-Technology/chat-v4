package gg.vape.module.utility.inventory.cleaner;

public enum InventoryMatcherListMode {
   WHITELIST,
   BLACKLIST;

   private static final InventoryMatcherListMode[] VALUES = new InventoryMatcherListMode[]{WHITELIST, BLACKLIST};

   static {
      String[] var10000 = new String[]{"WHITELIST", "BLACKLIST"};
   }
}
