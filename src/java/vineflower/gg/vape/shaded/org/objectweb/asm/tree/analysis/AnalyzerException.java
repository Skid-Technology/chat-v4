package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;

public class AnalyzerException extends Exception {
   private static final long serialVersionUID = 3154190448018943333L;
   public final transient AbstractInsnNode node;

   public AnalyzerException(AbstractInsnNode insn, String message) {
      super(message);
      this.node = insn;
   }

   public AnalyzerException(AbstractInsnNode insn, String message, Throwable cause) {
      super(message, cause);
      this.node = insn;
   }

   public AnalyzerException(AbstractInsnNode insn, String message, Object expected, Value actual) {
      super(stringConcat$1(message == null ? "Expected " : stringConcat$0(message), expected, actual));
      this.node = insn;
   }
}
