package gg.vape.ui.click.frame.impl;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public final class ThemeComponentGroupKey {
   @Nullable
   private final String h;
   private final String Z;

   @Nullable
   public String u() {
      return this.h;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         ThemeComponentGroupKey he_12 = (ThemeComponentGroupKey)object;
         return Objects.equals(this.Z, he_12.Z) && Objects.equals(this.h, he_12.h);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.Z, this.h);
   }

   public String h() {
      return this.Z;
   }

   public ThemeComponentGroupKey(String string, @Nullable String string2) {
      this.Z = string;
      this.h = string2;
   }
}
