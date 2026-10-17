package gg.vape.event.impl;

public enum ProfileListMutationAction {
   ADD,
   REMOVE;

   private static final ProfileListMutationAction[] VALUES_COPY = new ProfileListMutationAction[]{ADD, REMOVE};

   static {
      String[] var10000 = new String[]{"REMOVE", "ADD"};
   }
}
