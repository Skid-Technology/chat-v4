package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public enum DurabilityValueMode implements INamed, DescribedOption {
   PERCENTAGE("Percentage", "The durability of the item as a percentage"),
   VALUE("Value", "The durability of the item as the direct value");

   private final String description;
   public static final @UnmodifiableView List<DurabilityValueMode> VALUES = Arrays.asList(values());
   private final String name;

   @Override
   public String getName() {
      return this.name;
   }

   private DurabilityValueMode(String name, String description) {
      this.name = name;
      this.description = description;
   }

   @Override
   public String getDescription() {
      return this.description;
   }

   public static DurabilityValueMode fromNameOrDefault(String name, DurabilityValueMode fallback) {
      DurabilityValueMode mode = findByName(name);
      return mode == null ? fallback : mode;
   }

   @Nullable
   public static DurabilityValueMode findByName(String name) {
      for (DurabilityValueMode mode : VALUES) {
         if (mode.getName().equalsIgnoreCase(name)) {
            return mode;
         }
      }

      return null;
   }

   public static DurabilityValueMode fromName(String name) {
      return fromNameOrDefault(name, PERCENTAGE);
   }
}
