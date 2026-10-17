package gg.vape.value;

public class StringValue extends Value<String, StringValue> {
   private String definitionName;

   @Override
   public String toString() {
      return this.getValue();
   }

   public StringValue(Object owner, String name, String defaultValue) {
      super(owner, name, defaultValue);
   }

   @Override
   public void parse(String serializedValue) {
      this.setValue(serializedValue);
   }

   public static StringValue create(Object owner, String name, String defaultValue) {
      StringValue stringValue = new StringValue(owner, name, defaultValue);
      stringValue.setDefinitionName(name);
      return stringValue;
   }

   public StringValue copyDefinition() {
      return create(null, this.getDefinitionName(), this.getValue());
   }

   public StringValue copyValueDefinition() {
      return this.copyDefinition();
   }

   public String getDefinitionName() {
      return this.definitionName;
   }

   private void setDefinitionName(String definitionName) {
      this.definitionName = definitionName;
   }
}
