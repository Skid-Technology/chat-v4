package gg.vape.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ConditionalValue<K, T extends ConditionalValue<K, T>> extends Value<K, T> {
   private final List<Value> dependentValues = new ArrayList<>();
   private static String[] legacyState;

   public ValueCondition whenNotEqualTo(K value) {
      return new ValueCondition().require(this, candidate -> isNotEqual(value, candidate));
   }

   public static void setLegacyState(String[] state) {
      legacyState = state;
   }

   public abstract boolean hasActiveDependentBranch();

   public List<Value> getDependentValues() {
      return this.dependentValues;
   }

   public static String[] getConditionalLegacyState() {
      return legacyState;
   }

   public ConditionalValue(Object object, String string, K k) {
      super(object, string, k);
   }

   public T addDependentValues(Value<?, ?>... values) {
      for (Value<?, ?> value : values) {
         value.setParent(this);
         value.addConditionalValue(this);
      }

      this.dependentValues.addAll(Arrays.asList(values));
      return (T)this;
   }

   public ValueCondition whenEqualTo(K value) {
      return new ValueCondition().requireEqual(this, value);
   }

   private static boolean isNotEqual(Object expected, Object candidate) {
      return !candidate.equals(expected);
   }

   public abstract boolean isDependentValueActive(Value var1);

   public Value getTerminalDependentValue() {
      for (Value value : this.getDependentValues()) {
         if (value.equals(this.getDependentValues().get(this.getDependentValues().size() - 1))) {
            if (value instanceof ConditionalValue) {
               ConditionalValue<?, ?> conditionalValue = (ConditionalValue<?, ?>)value;
               if (!conditionalValue.getDependentValues().isEmpty() && conditionalValue.hasActiveDependentBranch()) {
                  Value terminalValue = conditionalValue.getDependentValues().get(conditionalValue.getDependentValues().size() - 1);
                  if (!conditionalValue.getDependentValues().isEmpty()) {
                     return conditionalValue.getTerminalDependentValue();
                  }

                  return terminalValue;
               }

               return value;
            }

            return value;
         }
      }

      return null;
   }

   static {
      if (getConditionalLegacyState() != null) {
         setLegacyState(new String[3]);
      }
   }
}
