package gg.vape.value;

import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public interface ListValueSuggestionProvider {
   void setComparator(@Nullable Comparator<String> var1);

   @UnmodifiableView List<String> getValues();

   @UnmodifiableView List<String> getSuggestions();

   @Nullable
   Comparator<String> getComparator();

   void updateFilter(String var1);

   void setIncludeAllWhenEmpty(boolean var1);

   boolean isIncludeAllWhenEmpty();
}
