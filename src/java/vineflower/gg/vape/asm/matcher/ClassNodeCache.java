package gg.vape.asm.matcher;

import gg.vape.runtime.ClassBytecodeCache;
import gg.vape.shaded.org.objectweb.asm.ClassReader;
import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import java.util.HashMap;
import java.util.Map;

public class ClassNodeCache {
   private static Map<Class, ClassNode> classNodes = new HashMap<>();
   private static boolean opaqueState;
   private static Map<Class, ClassMethodReferenceIndex> referenceIndexes = new HashMap<>();

   private static ClassMethodReferenceIndex cacheClassReferences(Class targetClass, Class ignoredKey) {
      ClassMethodReferenceIndex referenceIndex = new ClassMethodReferenceIndex(getClassNode(targetClass));
      return referenceIndexes.put(targetClass, referenceIndex);
   }

   public static void setOpaqueState(boolean state) {
      opaqueState = state;
   }

   private static ClassNode cacheClassNode(Class targetClass, Class ignoredKey) {
      byte[] bytecode = ClassBytecodeCache.getClassBytecode(targetClass);
      ClassReader classReader = new ClassReader(bytecode);
      ClassNode classNode = new ClassNode();
      classReader.accept(classNode, 0);
      return classNodes.put(targetClass, classNode);
   }

   public static boolean getOpaqueState() {
      return opaqueState;
   }

   public static boolean opaquePredicate() {
      boolean state = getOpaqueState();
      return false;
   }

   public static ClassNode getClassNode(Class targetClass) {
      classNodes.computeIfAbsent(targetClass, ignoredKey -> cacheClassNode(targetClass, ignoredKey));
      return classNodes.get(targetClass);
   }

   public static ClassMethodReferenceIndex getClassReferences(Class targetClass) {
      referenceIndexes.computeIfAbsent(targetClass, ignoredKey -> cacheClassReferences(targetClass, ignoredKey));
      return referenceIndexes.get(targetClass);
   }

   static {
      setOpaqueState(true);
   }
}
