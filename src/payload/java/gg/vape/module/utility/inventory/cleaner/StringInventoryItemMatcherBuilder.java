package gg.vape.module.utility.inventory.cleaner;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.UnmodifiableView;

public class StringInventoryItemMatcherBuilder extends InventoryItemMatcherBuilderBase<StringInventoryItemMatcherBuilder> {
   private final Map<String, StringMatchOperator> operatorsByPattern = new LinkedHashMap<>();

   private StringInventoryItemMatcherBuilder(InventoryItemMatcherBuilderBase<?> baseBuilder) {
      super(baseBuilder);
   }

   public @UnmodifiableView Map<String, StringMatchOperator> getOperatorsByPattern() {
      return this.operatorsByPattern;
   }

   public StringInventoryItemMatcherBuilder addPattern(String pattern, StringMatchOperator operator) {
      this.operatorsByPattern.put(pattern, operator);
      return this;
   }

   public StringInventoryItemMatcherBuilder putPatterns(StringMatchOperator operator, String... patterns) {
      for (String pattern : patterns) {
         this.operatorsByPattern.put(pattern, operator);
      }

      return this;
   }

   StringInventoryItemMatcherBuilder(InventoryItemMatcherBuilderBase baseBuilder, StringInventoryItemMatcherBuilderConstructorMarker constructorMarker) {
      this(baseBuilder);
   }

   public StringInventoryItemMatcher build() {
      return new StringInventoryItemMatcher(this);
   }
}
