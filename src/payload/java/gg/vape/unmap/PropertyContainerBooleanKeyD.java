package gg.vape.unmap;

public final class PropertyContainerBooleanKeyD extends PropertyKey<Boolean> {
   public Boolean getDefaultValue() {
      return this.getFalseDefault();
   }

   Boolean getFalseDefault() {
      return false;
   }
}
