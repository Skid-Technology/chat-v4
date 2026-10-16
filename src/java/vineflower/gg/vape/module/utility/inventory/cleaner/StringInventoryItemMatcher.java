package gg.vape.module.utility.inventory.cleaner;

import gg.vape.Vape;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Map;
import java.util.Map.Entry;

public class StringInventoryItemMatcher extends AbstractInventoryItemMatcher {
   private final Map<String, StringMatchOperator> matchOperators;

   @Override
   public boolean matches(ItemStack itemStack, Item item) {
      if (this.matchOperators.isEmpty()) {
         return false;
      } else {
         ItemMappingEntry mappingEntry = Vape.INSTANCE.getItemStackResolver().resolve(itemStack);
         if (mappingEntry == null) {
            return false;
         } else {
            for (Entry<String, StringMatchOperator> entry : this.matchOperators.entrySet()) {
               String pattern = entry.getKey();
               StringMatchOperator operator = entry.getValue();
               if (operator.getPredicate().test(mappingEntry.getModernId(), pattern)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public static StringInventoryItemMatcherBuilder builderFrom(InventoryItemMatcherBuilderBase<?> baseBuilder) {
      return new StringInventoryItemMatcherBuilder(baseBuilder, null);
   }

   public StringInventoryItemMatcher(StringInventoryItemMatcherBuilder builder) {
      super(builder);
      this.matchOperators = builder.getOperatorsByPattern();
   }
}
