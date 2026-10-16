package gg.vape.shaded.org.objectweb.asm;

public final class ClassTooLargeException extends IndexOutOfBoundsException {
   private static final long serialVersionUID = 160715609518896765L;
   private final String className;
   private final int constantPoolCount;

   public ClassTooLargeException(String className, int constantPoolCount) {
      super(stringConcat$0(className));
      this.className = className;
      this.constantPoolCount = constantPoolCount;
   }

   public String getClassName() {
      return this.className;
   }

   public int getConstantPoolCount() {
      return this.constantPoolCount;
   }
}
