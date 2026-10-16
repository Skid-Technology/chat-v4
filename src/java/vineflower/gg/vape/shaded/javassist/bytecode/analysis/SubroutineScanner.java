package gg.vape.shaded.javassist.bytecode.analysis;

import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ExceptionTable;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.bytecode.Opcode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubroutineScanner implements Opcode {
   private Subroutine[] subroutines;
   Map<Integer, Subroutine> subTable = new HashMap<>();
   Set<Integer> done = new HashSet<>();

   public Subroutine[] scan(MethodInfo method) throws BadBytecode {
      CodeAttribute code = method.getCodeAttribute();
      CodeIterator iter = code.iterator();
      this.subroutines = new Subroutine[code.getCodeLength()];
      this.subTable.clear();
      this.done.clear();
      this.scan(0, iter, null);
      ExceptionTable exceptions = code.getExceptionTable();

      for (int i = 0; i < exceptions.size(); i++) {
         int handler = exceptions.handlerPc(i);
         this.scan(handler, iter, this.subroutines[exceptions.startPc(i)]);
      }

      return this.subroutines;
   }

   private void scan(int pos, CodeIterator iter, Subroutine sub) throws BadBytecode {
      if (!this.done.contains(pos)) {
         this.done.add(pos);
         int old = iter.lookAhead();
         iter.move(pos);

         boolean next;
         do {
            pos = iter.next();
            next = this.scanOp(pos, iter, sub) && iter.hasNext();
         } while (next);

         iter.move(old);
      }
   }

   private boolean scanOp(int pos, CodeIterator iter, Subroutine sub) throws BadBytecode {
      this.subroutines[pos] = sub;
      int opcode = iter.byteAt(pos);
      if (opcode == 170) {
         this.scanTableSwitch(pos, iter, sub);
         return false;
      } else if (opcode == 171) {
         this.scanLookupSwitch(pos, iter, sub);
         return false;
      } else if (!Util.isReturn(opcode) && opcode != 169 && opcode != 191) {
         if (Util.isJumpInstruction(opcode)) {
            int target = Util.getJumpTarget(pos, iter);
            if (opcode != 168 && opcode != 201) {
               this.scan(target, iter, sub);
               if (Util.isGoto(opcode)) {
                  return false;
               }
            } else {
               Subroutine s = this.subTable.get(target);
               if (s == null) {
                  s = new Subroutine(target, pos);
                  this.subTable.put(target, s);
                  this.scan(target, iter, s);
               } else {
                  s.addCaller(pos);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void scanLookupSwitch(int pos, CodeIterator iter, Subroutine sub) throws BadBytecode {
      int index = (pos & -4) + 4;
      this.scan(pos + iter.s32bitAt(index), iter, sub);
      index += 4;
      int npairs = iter.s32bitAt(index);
      int var10000 = npairs * 8;
      index += 4;
      int end = var10000 + index;

      for (int var10 = index + 4; var10 < end; var10 += 8) {
         int target = iter.s32bitAt(var10) + pos;
         this.scan(target, iter, sub);
      }
   }

   private void scanTableSwitch(int pos, CodeIterator iter, Subroutine sub) throws BadBytecode {
      int index = (pos & -4) + 4;
      this.scan(pos + iter.s32bitAt(index), iter, sub);
      index += 4;
      int low = iter.s32bitAt(index);
      index += 4;
      int high = iter.s32bitAt(index);
      int var10000 = (high - low + 1) * 4;
      index += 4;

      for (int end = var10000 + index; index < end; index += 4) {
         int target = iter.s32bitAt(index) + pos;
         this.scan(target, iter, sub);
      }
   }
}
