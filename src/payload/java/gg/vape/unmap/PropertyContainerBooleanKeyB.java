package gg.vape.unmap;

public final class PropertyContainerBooleanKeyB extends PropertyKey<Boolean> {
   public Boolean getDefaultValue() {
      return this.getFalseDefault();
   }

   Boolean getFalseDefault() {
      return false;
   }
}
