package gg.vape.asm.matcher;

import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import java.util.HashMap;
import java.util.Map;

public class ClassMethodReferenceIndex {
   private Map<MethodNode, MethodInstructionIndex> methodIndexes = new HashMap<>();
   private ClassNode classNode;

   public void buildIndex() {
      for (MethodNode method : this.classNode.methods) {
         MethodInstructionIndex methodIndex = new MethodInstructionIndex(method);
         methodIndex.buildIndex();
         this.methodIndexes.put(method, methodIndex);
      }
   }

   public Map<MethodNode, MethodInstructionIndex> getMethodIndexes() {
      return this.methodIndexes;
   }

   public ClassMethodReferenceIndex(ClassNode classNode) {
      this.classNode = classNode;
      this.buildIndex();
   }
}
