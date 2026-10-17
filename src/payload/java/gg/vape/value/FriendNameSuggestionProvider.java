package gg.vape.value;

import gg.vape.Vape;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class FriendNameSuggestionProvider extends AbstractListValueSuggestionProvider {
   @Override
   public @UnmodifiableView List<String> getValues() {
      return new ArrayList<>(Vape.INSTANCE.getPublicProfileManager().getTags());
   }
}
