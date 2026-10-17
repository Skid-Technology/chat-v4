package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGameSettingsValue;
import gg.vape.wrapper.Wrapper;

public class GameSettingsValue extends Wrapper {
   public GameSettingsValue(Object object) {
      super(object);
   }

   public void a(Object object) {
      if (object instanceof Float) {
         object = ((Float)object).doubleValue();
      }

      MGameSettingsValue.S(vapeInstance.getMappingsMapperCompat().DM, this.I, object);
   }

   public void f(Object object) {
      if (object instanceof Float) {
         object = ((Float)object).doubleValue();
      }

      vapeInstance.getMappingsMapperCompat().DM.b(this.I, object);
   }

   public Object i() {
      return MGameSettingsValue.U(vapeInstance.getMappingsMapperCompat().DM, this.I);
   }
}
