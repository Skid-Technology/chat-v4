package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MMatrixStackEntry extends Mapping {
   private static final String MATRIX_FIELD_NAME = "matrix";
   private final MappingField matrixField = this.J("matrix", true, MappedClasses.qr);

   private Object readMatrix(Object matrixStackEntry) {
      return this.matrixField.getObject(matrixStackEntry);
   }

   public static Object getMatrix(MMatrixStackEntry mapping, Object matrixStackEntry) {
      return mapping.readMatrix(matrixStackEntry);
   }

   public MMatrixStackEntry() {
      super(MappedClasses.G);
   }
}
