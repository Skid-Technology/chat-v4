package gg.vape.asm.matcher;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class MethodNodeMatcher {
   private static String opaqueMarker;
   private final Class targetClass;
   private final ClassMethodReferenceIndex referenceIndex;
   private final ClassNode classNode;

   public String findMethodName(String descriptor) {
      for (MethodNode method : this.classNode.methods) {
         if (descriptor.equals(method.desc) && this.matchesMethod(method)) {
            return method.name;
         }
      }

      return null;
   }

   public abstract boolean matchesMethod(MethodNode var1);

   public MethodNodeMatcher(Class targetClass) {
      this.targetClass = targetClass;
      this.classNode = ClassNodeCache.getClassNode(targetClass);
      this.referenceIndex = ClassNodeCache.getClassReferences(targetClass);
   }

   public MethodInstructionIndex getMethodInstructionIndex(MethodNode methodNode) {
      return this.referenceIndex.getMethodIndexes().get(methodNode);
   }

   public static String getOpaqueMarker() {
      return opaqueMarker;
   }

   public static void setOpaqueMarker(String marker) {
      opaqueMarker = marker;
   }

   static {
      if (getOpaqueMarker() == null) {
         setOpaqueMarker("h95Sqb");
      }
   }
}
