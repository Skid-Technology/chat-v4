package gg.vape.ui.click.component;

import gg.vape.unmap.INamed;
import org.jetbrains.annotations.Nullable;

public interface OptionTextFormatter<T> {
   String format(T var1);

   static OptionTextFormatter<String> strings() {
      return strings("");
   }

   static OptionTextFormatter<String> strings(@Nullable String fallbackText) {
      return value -> formatStringOrFallback(fallbackText, value);
   }

   static <N extends INamed> OptionTextFormatter<N> namedValues(@Nullable String fallbackText) {
      return value -> formatNamedOrFallback(fallbackText, value);
   }

   static String formatStringOrFallback(String fallbackText, String value) {
      return value != null ? value : fallbackText;
   }

   static <N extends INamed> OptionTextFormatter<N> namedValues() {
      return namedValues("");
   }

   static String formatNamedOrFallback(String fallbackText, INamed value) {
      return value != null ? value.getName() : fallbackText;
   }
}
