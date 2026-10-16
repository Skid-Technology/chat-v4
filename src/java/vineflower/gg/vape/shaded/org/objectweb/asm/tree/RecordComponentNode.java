package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.AnnotationVisitor;
import gg.vape.shaded.org.objectweb.asm.Attribute;
import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.RecordComponentVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import java.util.List;

public class RecordComponentNode extends RecordComponentVisitor {
   public String name;
   public String descriptor;
   public String signature;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;

   public RecordComponentNode(String name, String descriptor, String signature) {
      this(589824, name, descriptor, signature);
      if (this.getClass() != RecordComponentNode.class) {
         throw new IllegalStateException();
      }
   }

   public RecordComponentNode(int api, String name, String descriptor, String signature) {
      super(api);
      this.name = name;
      this.descriptor = descriptor;
      this.signature = signature;
   }

   @Override
   public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      AnnotationNode annotation = new AnnotationNode(descriptor);
      if (visible) {
         this.visibleAnnotations = Util.add(this.visibleAnnotations, annotation);
      } else {
         this.invisibleAnnotations = Util.add(this.invisibleAnnotations, annotation);
      }

      return annotation;
   }

   @Override
   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
      TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
      if (visible) {
         this.visibleTypeAnnotations = Util.add(this.visibleTypeAnnotations, typeAnnotation);
      } else {
         this.invisibleTypeAnnotations = Util.add(this.invisibleTypeAnnotations, typeAnnotation);
      }

      return typeAnnotation;
   }

   @Override
   public void visitAttribute(Attribute attribute) {
      this.attrs = Util.add(this.attrs, attribute);
   }

   @Override
   public void visitEnd() {
   }

   public void check(int api) {
      if (api < 524288) {
         throw new UnsupportedClassVersionException();
      }
   }

   public void accept(ClassVisitor classVisitor) {
      RecordComponentVisitor recordComponentVisitor = classVisitor.visitRecordComponent(this.name, this.descriptor, this.signature);
      if (recordComponentVisitor != null) {
         if (this.visibleAnnotations != null) {
            int i = 0;

            for (int n = this.visibleAnnotations.size(); i < n; i++) {
               AnnotationNode annotation = this.visibleAnnotations.get(i);
               annotation.accept(recordComponentVisitor.visitAnnotation(annotation.desc, true));
            }
         }

         if (this.invisibleAnnotations != null) {
            int i = 0;

            for (int n = this.invisibleAnnotations.size(); i < n; i++) {
               AnnotationNode annotation = this.invisibleAnnotations.get(i);
               annotation.accept(recordComponentVisitor.visitAnnotation(annotation.desc, false));
            }
         }

         if (this.visibleTypeAnnotations != null) {
            int i = 0;

            for (int n = this.visibleTypeAnnotations.size(); i < n; i++) {
               TypeAnnotationNode typeAnnotation = this.visibleTypeAnnotations.get(i);
               typeAnnotation.accept(recordComponentVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
         }

         if (this.invisibleTypeAnnotations != null) {
            int i = 0;

            for (int n = this.invisibleTypeAnnotations.size(); i < n; i++) {
               TypeAnnotationNode typeAnnotation = this.invisibleTypeAnnotations.get(i);
               typeAnnotation.accept(recordComponentVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
         }

         if (this.attrs != null) {
            int i = 0;

            for (int n = this.attrs.size(); i < n; i++) {
               recordComponentVisitor.visitAttribute(this.attrs.get(i));
            }
         }

         recordComponentVisitor.visitEnd();
      }
   }
}
