package gg.vape.value;

public class SnapshotValueAccessor<K, T extends Value<K, T>> extends ValueAccessor {
   private final ValueSnapshot<T, K> snapshot;

   @Override
   public Object getValue() {
      return this.snapshot.getValue();
   }

   public SnapshotValueAccessor(ValueSnapshot<T, K> valueSnapshot, Value<K, T> value) {
      super(value);
      this.snapshot = valueSnapshot;
   }

   @Override
   public void setValue(Object value) {
      this.snapshot.setValue(value);
   }
}
