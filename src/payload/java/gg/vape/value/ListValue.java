package gg.vape.value;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public abstract class ListValue<C, T extends ListValue<C, T>> extends Value<List<C>, T> {
   @Nullable
   private ListValueSuggestionProvider suggestionProvider;
   private final String displayName;

   public ListValue(Object owner, String id, String displayName) {
      this(owner, id, displayName, new ArrayList<>());
   }

   public T setSuggestionProvider(ListValueSuggestionProvider suggestionProvider) {
      this.suggestionProvider = suggestionProvider;
      return (T)this;
   }

   public abstract C createEntry(String var1, int var2);

   public List<C> snapshotEntries() {
      return new ArrayList<>(this.getValue());
   }

   @Override
   public String getDisplayValue() {
      List<C> list = this.getValue();
      if (list.isEmpty()) {
         return "None";
      } else {
         return list.size() == 1 ? list.get(0).toString() : list.get(0).toString() + " +" + (list.size() - 1);
      }
   }

   public List<C> copyValue() {
      return this.snapshotEntries();
   }

   @Nullable
   public ListValueSuggestionProvider getSuggestionProvider() {
      return this.suggestionProvider;
   }

   public ListValue(Object owner, String id, String displayName, List<C> entries) {
      super(owner, id, ImmutableList.copyOf(entries));
      this.setDirectValue(new ListValueMutableBackingList<>(this, entries));
      this.displayName = displayName;
   }

   @Override
   public String getName() {
      return this.displayName;
   }
}
