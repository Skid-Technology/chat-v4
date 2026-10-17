package gg.vape.module.macro;

import gg.vape.module.Macro;

public class CommandMacro extends Macro {
   @Override
   public MacroAction createAction() {
      return new CommandMacroAction(this);
   }

   public CommandMacro(String name) {
      super(name);
   }
}
