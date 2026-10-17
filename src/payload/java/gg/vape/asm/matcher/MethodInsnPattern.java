package gg.vape.asm.matcher;

import org.objectweb.asm.tree.MethodInsnNode;

public class MethodInsnPattern extends InstructionPattern {
   public MethodInsnPattern(int opcode, String owner, String name, String descriptor) {
      super(opcode, owner, name, descriptor);
   }

   public static MethodInsnPattern fromNode(MethodInsnNode methodInstruction) {
      return new MethodInsnPattern(methodInstruction.getOpcode(), methodInstruction.owner, methodInstruction.name, methodInstruction.desc);
   }
}
