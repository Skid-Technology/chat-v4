package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.TimerUtil;
import gg.vape.wrapper.impl.ForgeVersion;

public class MI18n extends Mapping {
   private final MappingField languageField;
   private final MappingMethod formatMethod;

   public MI18n() {
      this(TimerUtil.p());
   }

   private MI18n(String timerState) {
      super(MappedClasses.Vm);
      if (ForgeVersion.MC_26_2.d()) {
         this.languageField = null;
      } else if (ForgeVersion.MC_1_16_5.d()) {
         this.languageField = this.registerStaticField("field_239501_a_", true, MappedClasses.Fn);
      } else {
         this.languageField = this.registerStaticField("i18nLocale", true, MappedClasses.Fn);
      }

      this.formatMethod = this.registerStaticMethod(ForgeVersion.MC_26_2.d() ? "get" : "format", true, String.class, String.class, Object[].class);
   }

   public String format(String translationKey, Object... arguments) {
      return (String)this.formatMethod.invokeObject(null, translationKey, arguments);
   }

   public Object getLanguage() {
      return this.languageField == null ? null : this.languageField.getObject(null);
   }
}
