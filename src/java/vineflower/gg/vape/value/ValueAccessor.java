package gg.vape.value;

public abstract class ValueAccessor<K, T extends Value<K, T>> {
   private final Value<K, T> value;

   public abstract K getValue();

   public abstract void setValue(K var1);

   public ValueAccessor(Value<K, T> value) {
      this.value = value;
   }

   public Value<K, T> getSourceValue() {
      return this.value;
   }
}
