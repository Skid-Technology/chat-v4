package gg.vape.mapping;

public class MappingFieldBuilder extends MappingMemberBuilder<MappingFieldBuilder, MappingField> {
   private int arrayDimensions = 0;

   public MappingField buildField() {
      return MappingField.fromBuilder(this);
   }

   public MappingField build() {
      return this.buildField();
   }

   public int getArrayDimensions() {
      return this.arrayDimensions;
   }

   public MappingFieldBuilder setArrayDimensions(int arrayDimensions) {
      this.arrayDimensions = arrayDimensions;
      return this;
   }
}
