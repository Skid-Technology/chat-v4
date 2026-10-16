package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.tree.JumpInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.LabelNode;
import java.util.ArrayList;
import java.util.List;

final class Subroutine {
   final LabelNode start;
   final boolean[] localsUsed;
   final List<JumpInsnNode> callers;

   Subroutine(LabelNode start, int maxLocals, JumpInsnNode caller) {
      this.start = start;
      this.localsUsed = new boolean[maxLocals];
      this.callers = new ArrayList<>();
      this.callers.add(caller);
   }

   Subroutine(Subroutine subroutine) {
      this.start = subroutine.start;
      this.localsUsed = (boolean[])subroutine.localsUsed.clone();
      this.callers = new ArrayList<>(subroutine.callers);
   }

   public boolean merge(Subroutine subroutine) {
      boolean changed = false;

      for (int i = 0; i < this.localsUsed.length; i++) {
         if (subroutine.localsUsed[i] && !this.localsUsed[i]) {
            this.localsUsed[i] = true;
            changed = true;
         }
      }

      if (subroutine.start == this.start) {
         for (int ix = 0; ix < subroutine.callers.size(); ix++) {
            JumpInsnNode caller = subroutine.callers.get(ix);
            if (!this.callers.contains(caller)) {
               this.callers.add(caller);
               changed = true;
            }
         }
      }

      return changed;
   }
}
