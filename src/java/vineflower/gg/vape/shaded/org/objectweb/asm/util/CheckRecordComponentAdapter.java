package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.RecordComponentVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.TypeReference;

public class CheckRecordComponentAdapter extends RecordComponentVisitor {
   private boolean visitEndCalled;

   public CheckRecordComponentAdapter(RecordComponentVisitor recordComponentVisitor) {
      this(589824, recordComponentVisitor);
      if (this.getClass() != CheckRecordComponentAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckRecordComponentAdapter(int api, RecordComponentVisitor recordComponentVisitor) {
      super(api, recordComponentVisitor);
   }

   @Override
   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      this.checkVisitEndNotCalled();
      CheckMethodAdapter.checkDescriptor(49, descriptor, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(descriptor, visible));
   }

   @Override
   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      this.checkVisitEndNotCalled();
      int sort = new TypeReference(typeRef).getSort();
      if (sort != 19) {
         throw new IllegalArgumentException(stringConcat$0(Integer.toHexString(sort)));
      } else {
         CheckClassAdapter.checkTypeRef(typeRef);
         CheckMethodAdapter.checkDescriptor(49, descriptor, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, descriptor, visible));
      }
   }

   @Override
   public void visitAttribute(Attribute attribute) {
      this.checkVisitEndNotCalled();
      if (attribute == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(attribute);
      }
   }

   @Override
   public void visitEnd() {
      this.checkVisitEndNotCalled();
      this.visitEndCalled = true;
      super.visitEnd();
   }

   private void checkVisitEndNotCalled() {
      if (this.visitEndCalled) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }
}
