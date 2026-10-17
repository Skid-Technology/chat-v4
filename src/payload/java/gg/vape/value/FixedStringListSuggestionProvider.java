package gg.vape.value;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class FixedStringListSuggestionProvider extends AbstractListValueSuggestionProvider {
   private List<String> values = new ArrayList<>();

   public FixedStringListSuggestionProvider() {
      this.updateFilter("");
   }

   public void setValues(@NotNull List<String> values) {
      this.values = values;
      this.updateFilter("");
   }

   public FixedStringListSuggestionProvider(List<String> values) {
      this.values = values;
      this.updateFilter("");
   }

   @Override
   public List<String> getValues() {
      return this.values;
   }
}
