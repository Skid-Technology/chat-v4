package gg.vape.tutorial;

import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class TutorialTargetSelector<T extends GuiComponent> {
   private static boolean obfuscationState;
   private final Class<T> targetType;

   public static void setObfuscationState(boolean state) {
      obfuscationState = state;
   }

   public Class<T> getTargetType() {
      return this.targetType;
   }

   public abstract boolean matches(T var1);

   public static boolean isObfuscationStateDisabled() {
      boolean state = getObfuscationState();
      return !state;
   }

   public static boolean getObfuscationState() {
      return obfuscationState;
   }

   public TutorialTargetSelector(Class<T> clazz) {
      this.targetType = clazz;
   }

   public ArrayList<GuiComponent> findTargets(GuiComponent guiComponent) {
      return this.getTargetType().isInstance(guiComponent) && this.matches(this.getTargetType().cast(guiComponent))
         ? new ArrayList<>(Arrays.asList(guiComponent))
         : null;
   }

   static {
      if (!isObfuscationStateDisabled()) {
         setObfuscationState(true);
      }
   }
}
