package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public enum MembershipMode implements INamed {
   IS_IN("is in"),
   IS_NOT_IN("is not in");

   private final String label;
   public static final @UnmodifiableView List<MembershipMode> VALUES = Arrays.asList(values());

   public boolean matchesAny(String value, List<String> candidates) {
      for (String candidate : candidates) {
         if (this.matches(value, candidate)) {
            return true;
         }
      }

      return false;
   }

   public static MembershipMode fromName(String name) {
      return fromNameOrDefault(name, IS_IN);
   }

   @Override
   public String getName() {
      return this.label;
   }

   private MembershipMode(String label) {
      this.label = label;
   }

   public boolean matches(String value, String candidate) {
      value = value.toLowerCase();
      candidate = candidate.toLowerCase();
      switch (this) {
         case IS_IN:
            return value.contains(candidate);
         case IS_NOT_IN:
            return !value.contains(candidate);
         default:
            return false;
      }
   }

   public static MembershipMode fromNameOrDefault(String name, MembershipMode fallback) {
      MembershipMode mode = findByName(name);
      return mode == null ? fallback : mode;
   }

   @Nullable
   public static MembershipMode findByName(String name) {
      for (MembershipMode mode : VALUES) {
         if (mode.getName().equalsIgnoreCase(name)) {
            return mode;
         }
      }

      return null;
   }
}
