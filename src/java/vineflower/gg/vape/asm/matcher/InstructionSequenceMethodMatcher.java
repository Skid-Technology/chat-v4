package gg.vape.asm.matcher;

import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import java.util.List;

public class InstructionSequenceMethodMatcher extends MethodNodeMatcher {
   private static int opaqueState;
   private final InstructionPattern[] expectedPatterns;

   public static int getOpaqueState() {
      return opaqueState;
   }

   public static void setOpaqueState(int state) {
      opaqueState = state;
   }

   public static int opaquePredicate() {
      int state = getOpaqueState();
      return state == 0 ? 77 : 0;
   }

   public InstructionSequenceMethodMatcher(Class targetClass, InstructionPattern... expectedPatterns) {
      super(targetClass);
      this.expectedPatterns = expectedPatterns;
   }

   @Override
   public boolean matchesMethod(MethodNode methodNode) {
      MethodInstructionIndex methodInstructionIndex = this.getMethodInstructionIndex(methodNode);
      int matchedCount = 0;
      int searchStartIndex = 0;
      List<InstructionPattern> actualPatterns = methodInstructionIndex.getPatterns();

      for (InstructionPattern expectedPattern : this.expectedPatterns) {
         for (int index = searchStartIndex; index < actualPatterns.size(); index++) {
            searchStartIndex = index;
            InstructionPattern actualPattern = actualPatterns.get(index);
            if (expectedPattern.matches(actualPattern)) {
               matchedCount++;
               break;
            }
         }
      }

      return matchedCount == this.expectedPatterns.length;
   }

   static {
      if (opaquePredicate() != 0) {
         setOpaqueState(89);
      }
   }
}
