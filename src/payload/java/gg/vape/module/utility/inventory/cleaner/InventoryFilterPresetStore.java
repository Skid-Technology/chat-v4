package gg.vape.module.utility.inventory.cleaner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class InventoryFilterPresetStore {
   private final List<SharedInventoryFilterPreset> presets = new ArrayList<>();

   @Nullable
   public SharedInventoryFilterPreset getById(UUID uUID) {
      for (SharedInventoryFilterPreset preset : this.presets) {
         if (preset.getId().equals(uUID)) {
            return preset;
         }
      }

      return null;
   }

   public void remove(SharedInventoryFilterPreset preset) {
      this.presets.remove(preset);
   }

   public @UnmodifiableView List<SharedInventoryFilterPreset> getAll() {
      return this.presets;
   }

   public void replace(@Nullable SharedInventoryFilterPreset oldPreset, SharedInventoryFilterPreset newPreset) {
      if (oldPreset != null) {
         this.remove(oldPreset);
      }

      this.add(newPreset);
   }

   public void add(SharedInventoryFilterPreset preset) {
      this.presets.add(preset);
   }
}
