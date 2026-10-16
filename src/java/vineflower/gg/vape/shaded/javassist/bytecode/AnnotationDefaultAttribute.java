package gg.vape.shaded.javassist.bytecode;

import gg.vape.shaded.javassist.bytecode.annotation.AnnotationsWriter;
import gg.vape.shaded.javassist.bytecode.annotation.MemberValue;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class AnnotationDefaultAttribute extends AttributeInfo {
   public static final String tag = "AnnotationDefault";

   public AnnotationDefaultAttribute(ConstPool cp, byte[] info) {
      super(cp, "AnnotationDefault", info);
   }

   public AnnotationDefaultAttribute(ConstPool cp) {
      this(cp, new byte[]{0, 0});
   }

   AnnotationDefaultAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
      super(cp, n, in);
   }

   @Override
   public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
      AnnotationsAttribute.Copier copier = new AnnotationsAttribute.Copier(this.info, this.constPool, newCp, classnames);

      try {
         copier.memberValue(0);
         return new AnnotationDefaultAttribute(newCp, copier.close());
      } catch (Exception var5) {
         throw new RuntimeException(var5.toString());
      }
   }

   @Override
   void renameClass(String oldname, String newname) {
      try {
         MemberValue defaultValue = this.getDefaultValue();
         defaultValue.renameClass(oldname, newname);
         this.setDefaultValue(defaultValue);
      } catch (Exception var4) {
      }
   }

   @Override
   void renameClass(Map<String, String> classnames) {
      try {
         MemberValue defaultValue = this.getDefaultValue();
         defaultValue.renameClass(classnames);
         this.setDefaultValue(defaultValue);
      } catch (Exception var3) {
      }
   }

   public MemberValue getDefaultValue() {
      try {
         return new AnnotationsAttribute.Parser(this.info, this.constPool).parseMemberValue();
      } catch (Exception var2) {
         throw new RuntimeException(var2.toString());
      }
   }

   public void setDefaultValue(MemberValue value) {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      AnnotationsWriter writer = new AnnotationsWriter(output, this.constPool);

      try {
         value.write(writer);
         writer.close();
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      this.set(output.toByteArray());
   }

   @Override
   public String toString() {
      return this.getDefaultValue().toString();
   }
}
