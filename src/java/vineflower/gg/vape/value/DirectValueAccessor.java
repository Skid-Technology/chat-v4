package gg.vape.value;

public class DirectValueAccessor<K, T extends Value<K, T>> extends ValueAccessor {
   @Override
   public void setValue(Object value) {
      this.getSourceValue().setDirectValue((K)value);
   }

   @Override
   public Object getValue() {
      return this.getSourceValue().getDirectValue();
   }

   public DirectValueAccessor(Value<K, T> value) {
      super(value);
   }
}
