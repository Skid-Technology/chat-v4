package gg.vape.input;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiKeyTypedDispatcher;
import java.util.HashSet;
import java.util.Set;

public class CharacterInputHandler implements InputEventHandler {
   private Set<Integer> allowedControlCharacters = new HashSet<>();
   private static int legacyState;

   @Override
   public boolean handle(long firstArgument, long secondArgument) {
      char typedCharacter = (char)firstArgument;
      char controlCharacter = 0;
      if (typedCharacter == 167 || typedCharacter < ' ' || typedCharacter == 127) {
         if (this.allowedControlCharacters.contains(Integer.valueOf(typedCharacter))) {
            controlCharacter = typedCharacter;
         }

         if (controlCharacter == 0) {
            return false;
         }
      }

      GuiKeyTypedDispatcher.dispatch(typedCharacter, controlCharacter);
      return !ClientSettings.INSTANCE.inputEnabled;
   }

   public static int getLegacySentinelResult() {
      int state = getLegacyState();
      return state == 0 ? 63 : 0;
   }

   public CharacterInputHandler() {
      this.allowedControlCharacters.add(8);
      this.allowedControlCharacters.add(3);
      this.allowedControlCharacters.add(22);
      this.allowedControlCharacters.add(27);
      this.allowedControlCharacters.add(13);
      this.allowedControlCharacters.add(9);
   }

   public static void setLegacyState(int state) {
      legacyState = state;
   }

   public static int getLegacyState() {
      return legacyState;
   }

   static {
      if (getLegacySentinelResult() == 0) {
         setLegacyState(4);
      }
   }
}
