package gg.vape.module.world.cheststeal;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MTextComponentTranslation;
import gg.vape.unmap.TextComponentBase;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.TextComponent;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class ChestStealInventoryState extends TextComponentBase {
   private static final String UNSUPPORTED_MESSAGE = "This method is available on 1.20.6 and later.";

   public static ChestStealInventoryState createTranslation(String translationKey, Object... formatArguments) {
      return new ChestStealInventoryState(MTextComponentTranslation.B(vapeInstance.getMappings().D_, translationKey, formatArguments));
   }

   public String getTranslationKey() {
      if (ForgeVersion.MC_1_16_5.v()) {
         Vape.notifyNativeStackTrace();
      }

      return MTextComponentTranslation.C(vapeInstance.getMappings().D_, this.getObject());
   }

   public String getFallback() {
      if (ForgeVersion.MC_1_20_6.v()) {
         Vape.notifyNativeStackTrace();
      }

      return MTextComponentTranslation.a(vapeInstance.getMappings().D_, this.getObject());
   }

   public static ChestStealInventoryState createTranslationWithFallback(String translationKey, @Nullable String fallback, Object[] formatArguments) {
      if (ForgeVersion.MC_1_20_6.v()) {
         Vape.notifyNativeStackTrace();
         throw new UnsupportedOperationException("This method is available on 1.20.6 and later.");
      } else {
         return new ChestStealInventoryState(MTextComponentTranslation.k(vapeInstance.getMappings().D_, translationKey, fallback, formatArguments));
      }
   }

   @Override
   public String getFormattedText() {
      if (ForgeVersion.MC_1_20_6.d()) {
         TextComponent textComponent = TextComponent.p(this.I);
         return textComponent.getFormattedText();
      } else {
         return super.getFormattedText();
      }
   }

   public Object[] getFormatArguments() {
      if (ForgeVersion.MC_1_16_5.v()) {
         Vape.notifyNativeStackTrace();
      }

      return MTextComponentTranslation.x(vapeInstance.getMappings().D_, this.getObject());
   }

   public ChestStealInventoryState(Object component) {
      super(component);
   }

   public List<?> getSiblings() {
      return vapeInstance.getMappings().D_.k(this.getObject());
   }
}
