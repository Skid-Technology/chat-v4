package gg.vape.config;

import com.google.gson.JsonObject;
import gg.vape.input.BindSet;
import gg.vape.value.BindValue;

public class PublicProfileSettingsBindValue extends BindValue {
   final PublicProfileSettings settings;

   @Override
   public boolean loadJson(JsonObject object) {
      return super.loadJson(object);
   }

   @Override
   public boolean isDefault() {
      return false;
   }

   public PublicProfileSettingsBindValue(PublicProfileSettings settings, Object owner, String name, BindSet bindSet) {
      super(owner, name, bindSet);
      this.settings = settings;
   }
}
