package gg.vape.asm.matcher;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.ArrayList;
import java.util.List;

public class MethodInstructionIndex {
   private List<InstructionPattern> patterns = new ArrayList<>();
   private MethodNode methodNode;
   private static String[] opaqueStringSlots;

   public void buildIndex() {
      for (AbstractInsnNode instruction : this.methodNode.instructions) {
         this.addInstruction(instruction);
      }
   }

   public static String[] getOpaqueStringSlots() {
      return opaqueStringSlots;
   }

   public List<InstructionPattern> getPatterns() {
      return this.patterns;
   }

   public static void setOpaqueStringSlots(String[] slots) {
      opaqueStringSlots = slots;
   }

   public MethodInstructionIndex(MethodNode methodNode) {
      this.methodNode = methodNode;
   }

   private void addInstruction(AbstractInsnNode instruction) {
      if (instruction instanceof MethodInsnNode) {
         this.patterns.add(MethodInsnPattern.fromNode((MethodInsnNode)instruction));
      }

      if (instruction instanceof FieldInsnNode) {
         this.patterns.add(FieldInsnPattern.fromNode((FieldInsnNode)instruction));
      }
   }

   static {
      if (getOpaqueStringSlots() == null) {
         setOpaqueStringSlots(new String[2]);
      }
   }
}
