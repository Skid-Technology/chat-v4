package gg.vape.shaded.javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

class FloatInfo extends ConstInfo {
   static final int tag = 4;
   float value;

   public FloatInfo(float f, int index) {
      super(index);
      this.value = f;
   }

   public FloatInfo(DataInputStream in, int index) throws IOException {
      super(index);
      this.value = in.readFloat();
   }

   @Override
   public int hashCode() {
      return Float.floatToIntBits(this.value);
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof FloatInfo && ((FloatInfo)obj).value == this.value;
   }

   @Override
   public int getTag() {
      return 4;
   }

   @Override
   public int copy(ConstPool src, ConstPool dest, Map<String, String> map) {
      return dest.addFloatInfo(this.value);
   }

   @Override
   public void write(DataOutputStream out) throws IOException {
      out.writeByte(4);
      out.writeFloat(this.value);
   }

   @Override
   public void print(PrintWriter out) {
      out.print("Float ");
      out.println(this.value);
   }
}
