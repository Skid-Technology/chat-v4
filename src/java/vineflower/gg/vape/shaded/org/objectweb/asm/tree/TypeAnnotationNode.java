package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.TypePath;

public class TypeAnnotationNode extends AnnotationNode {
   public int typeRef;
   public TypePath typePath;

   public TypeAnnotationNode(int typeRef, TypePath typePath, String descriptor) {
      this(589824, typeRef, typePath, descriptor);
      if (this.getClass() != TypeAnnotationNode.class) {
         throw new IllegalStateException();
      }
   }

   public TypeAnnotationNode(int api, int typeRef, TypePath typePath, String descriptor) {
      super(api, descriptor);
      this.typeRef = typeRef;
      this.typePath = typePath;
   }
}
