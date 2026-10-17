package gg.vape.value;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public class PlayerNameSuggestionProvider extends AbstractListValueSuggestionProvider {
   @Override
   public @UnmodifiableView List<String> getValues() {
      ArrayList<String> playerNames = new ArrayList<>();
      WorldClient world = Minecraft.theWorld();
      if (world.isNull()) {
         return playerNames;
      } else {
         for (Object entity : world.X()) {
            if (MappedClasses.Yl.isInstance(entity)) {
               EntityPlayer player = new EntityPlayer(entity);
               playerNames.add(player.getName());
            }
         }

         return playerNames;
      }
   }
}
