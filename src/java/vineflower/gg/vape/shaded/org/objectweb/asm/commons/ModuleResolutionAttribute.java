package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ByteVector;
import gg.vape.shaded.org.objectweb.asm.ClassReader;
import gg.vape.shaded.org.objectweb.asm.ClassWriter;
import gg.vape.shaded.org.objectweb.asm.Label;

public final class ModuleResolutionAttribute extends Attribute {
   public static final int RESOLUTION_DO_NOT_RESOLVE_BY_DEFAULT = 1;
   public static final int RESOLUTION_WARN_DEPRECATED = 2;
   public static final int RESOLUTION_WARN_DEPRECATED_FOR_REMOVAL = 4;
   public static final int RESOLUTION_WARN_INCUBATING = 8;
   public int resolution;

   public ModuleResolutionAttribute(int resolution) {
      super("ModuleResolution");
      this.resolution = resolution;
   }

   public ModuleResolutionAttribute() {
      this(0);
   }

   @Override
   protected Attribute read(ClassReader classReader, int offset, int length, char[] charBuffer, int codeOffset, Label[] labels) {
      return new ModuleResolutionAttribute(classReader.readUnsignedShort(offset));
   }

   @Override
   protected ByteVector write(ClassWriter classWriter, byte[] code, int codeLength, int maxStack, int maxLocals) {
      ByteVector byteVector = new ByteVector();
      byteVector.putShort(this.resolution);
      return byteVector;
   }
}
