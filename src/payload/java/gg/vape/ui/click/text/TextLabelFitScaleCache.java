package gg.vape.ui.click.text;

import gg.vape.Vape;
import gg.vape.ui.font.SmoothFontRenderer;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class TextLabelFitScaleCache {
   LinkedHashMap<String, Double> scaleBySpecKey = new LinkedHashMap<>();
   public static TextLabelFitScaleCache INSTANCE = new TextLabelFitScaleCache();

   public double getFittedScale(TextLabelFitSpec fitSpec) {
      if (this.scaleBySpecKey.containsKey(fitSpec.toString())) {
         return this.scaleBySpecKey.get(fitSpec.toString());
      } else {
         double fittedScale;
         for (fittedScale = fitSpec.getMaxScale();
            fittedScale >= fitSpec.getMinScale() && fittedScale <= fitSpec.getMaxScale();
            fittedScale = new BigDecimal(fittedScale).subtract(BigDecimal.valueOf(fitSpec.getScaleIncrement())).setScale(1, 4).doubleValue()
         ) {
            SmoothFontRenderer fontRenderer = fitSpec.isBold()
               ? Vape.INSTANCE.getFontManager().W(fittedScale, true)
               : Vape.INSTANCE.getFontManager().E(fittedScale, false);
            if (!(fontRenderer.N(fitSpec.getText()) > fitSpec.getMaxWidth())) {
               break;
            }
         }

         this.scaleBySpecKey.put(fitSpec.toString(), fittedScale);
         return fittedScale;
      }
   }
}
