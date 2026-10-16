package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Opcodes;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.FieldInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.InvokeDynamicInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.LdcInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodInsnNode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes {
   public SourceInterpreter() {
      super(589824);
      if (this.getClass() != SourceInterpreter.class) {
         throw new IllegalStateException();
      }
   }

   protected SourceInterpreter(int api) {
      super(api);
   }

   public SourceValue newValue(Type type) {
      return type == Type.VOID_TYPE ? null : new SourceValue(type == null ? 1 : type.getSize());
   }

   public SourceValue newOperation(AbstractInsnNode insn) {
      int size;
      switch (insn.getOpcode()) {
         case 9:
         case 10:
         case 14:
         case 15:
            size = 2;
            break;
         case 18:
            Object value = ((LdcInsnNode)insn).cst;
            if (value instanceof Long || value instanceof Double) {
               size = 2;
            } else if (value instanceof ConstantDynamic) {
               size = ((ConstantDynamic)value).getSize();
            } else {
               size = 1;
            }
            break;
         case 178:
            size = Type.getType(((FieldInsnNode)insn).desc).getSize();
            break;
         default:
            size = 1;
      }

      return new SourceValue(size, insn);
   }

   public SourceValue copyOperation(AbstractInsnNode insn, SourceValue value) {
      return new SourceValue(value.getSize(), insn);
   }

   public SourceValue unaryOperation(AbstractInsnNode insn, SourceValue value) {
      int size;
      switch (insn.getOpcode()) {
         case 117:
         case 119:
         case 133:
         case 135:
         case 138:
         case 140:
         case 141:
         case 143:
            size = 2;
            break;
         case 180:
            size = Type.getType(((FieldInsnNode)insn).desc).getSize();
            break;
         default:
            size = 1;
      }

      return new SourceValue(size, insn);
   }

   public SourceValue binaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2) {
      int size;
      switch (insn.getOpcode()) {
         case 47:
         case 49:
         case 97:
         case 99:
         case 101:
         case 103:
         case 105:
         case 107:
         case 109:
         case 111:
         case 113:
         case 115:
         case 121:
         case 123:
         case 125:
         case 127:
         case 129:
         case 131:
            size = 2;
            break;
         default:
            size = 1;
      }

      return new SourceValue(size, insn);
   }

   public SourceValue ternaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2, SourceValue value3) {
      return new SourceValue(1, insn);
   }

   public SourceValue naryOperation(AbstractInsnNode insn, List<? extends SourceValue> values) {
      int opcode = insn.getOpcode();
      int size;
      if (opcode == 197) {
         size = 1;
      } else if (opcode == 186) {
         size = Type.getReturnType(((InvokeDynamicInsnNode)insn).desc).getSize();
      } else {
         size = Type.getReturnType(((MethodInsnNode)insn).desc).getSize();
      }

      return new SourceValue(size, insn);
   }

   public void returnOperation(AbstractInsnNode insn, SourceValue value, SourceValue expected) {
   }

   public SourceValue merge(SourceValue value1, SourceValue value2) {
      if (value1.insns instanceof SmallSet && value2.insns instanceof SmallSet) {
         Set<AbstractInsnNode> setUnion = ((SmallSet)value1.insns).union((SmallSet<AbstractInsnNode>)value2.insns);
         return setUnion == value1.insns && value1.size == value2.size ? value1 : new SourceValue(Math.min(value1.size, value2.size), setUnion);
      } else if (value1.size == value2.size && containsAll(value1.insns, value2.insns)) {
         return value1;
      } else {
         HashSet<AbstractInsnNode> setUnion = new HashSet<>();
         setUnion.addAll(value1.insns);
         setUnion.addAll(value2.insns);
         return new SourceValue(Math.min(value1.size, value2.size), setUnion);
      }
   }

   private static <E> boolean containsAll(Set<E> self, Set<E> other) {
      return self.size() < other.size() ? false : self.containsAll(other);
   }
}
