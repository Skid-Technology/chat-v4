package gg.vape.value;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class ValueCondition {
   private static int[] legacyState;
   private final Map<ConditionalValue, Predicate<Object>> predicatesByValue = new HashMap<>();

   public ValueCondition requireFalse(ConditionalValue conditionalValue) {
      this.predicatesByValue.put(conditionalValue, Boolean.FALSE::equals);
      return this;
   }

   private static boolean isNotEqual(Object expected, Object candidate) {
      return !expected.equals(candidate);
   }

   public Map<ConditionalValue, Predicate<Object>> getPredicatesByValue() {
      return this.predicatesByValue;
   }

   public static void setLegacyState(int[] state) {
      legacyState = state;
   }

   public ValueCondition requireNotEqual(ConditionalValue conditionalValue, Object expectedValue) {
      this.predicatesByValue.put(conditionalValue, candidate -> isNotEqual(expectedValue, candidate));
      return this;
   }

   public ValueCondition requireEqual(ConditionalValue conditionalValue, Object expectedValue) {
      this.predicatesByValue.put(conditionalValue, expectedValue::equals);
      return this;
   }

   public void applyTo(Value<?, ?>... values) {
      for (Entry<ConditionalValue, Predicate<Object>> entry : this.predicatesByValue.entrySet()) {
         ConditionalValue conditionalValue = entry.getKey();
         conditionalValue.addDependentValues(values);

         for (Value<?, ?> value : values) {
            value.addCondition(this);
         }
      }
   }

   public ValueCondition and(ValueCondition other) {
      ValueCondition combined = new ValueCondition();
      combined.predicatesByValue.putAll(this.predicatesByValue);
      combined.predicatesByValue.putAll(other.predicatesByValue);
      return combined;
   }

   public ValueCondition require(ConditionalValue conditionalValue, Predicate<Object> predicate) {
      this.predicatesByValue.put(conditionalValue, predicate);
      return this;
   }

   public static int[] getLegacyState() {
      return legacyState;
   }

   public ValueCondition requireTrue(ConditionalValue conditionalValue) {
      this.predicatesByValue.put(conditionalValue, Boolean.TRUE::equals);
      return this;
   }

   public boolean isSatisfied() {
      for (Entry<ConditionalValue, Predicate<Object>> entry : this.predicatesByValue.entrySet()) {
         if (!entry.getValue().test(entry.getKey().getValue())) {
            return false;
         }
      }

      return true;
   }

   static {
      if (getLegacyState() != null) {
         setLegacyState(new int[1]);
      }
   }
}
