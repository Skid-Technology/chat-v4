package gg.vape.ui.click.component;

import gg.vape.unmap.ModeSelection;

public class ModeEntryTextFormatter<T extends ModeSelection> implements OptionTextFormatter<T> {
   public static final OptionTextFormatter<ModeSelection> DEFAULT = new ModeEntryTextFormatter<>();

   public String formatValue(T value) {
      return value != null ? value.toString() : "";
   }

   public String format(T value) {
      return this.formatValue(value);
   }
}
