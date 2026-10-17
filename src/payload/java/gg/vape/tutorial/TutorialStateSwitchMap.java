package gg.vape.tutorial;

public class TutorialStateSwitchMap {
   public static final int[] D = new int[TutorialState.values().length];

   TutorialStateSwitchMap() {
   }

   static {
      try {
         D[TutorialState.WELCOME.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         D[TutorialState.COMPLETED_ALL.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         D[TutorialState.COMPLETED_TUTORIAL.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }
   }
}
