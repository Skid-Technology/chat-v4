package gg.vape.unmap;

public class ModeOption extends ModeSelection {
   public ModeOption(String name) {
      this(name, 1.0);
   }

   public boolean isSelected() {
      return this.getMode() == null ? false : this.getMode().getValue().equals(this);
   }

   public ModeOption(String name, double displayScale) {
      super(name);
   }
}
