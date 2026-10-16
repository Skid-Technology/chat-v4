package gg.vape.ui.font;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class SmoothFontRendererStringCache extends LinkedHashMap<String, String> {
   final SmoothFontRenderer L;

   @Override
   protected boolean removeEldestEntry(Entry<String, String> entry) {
      return this.size() > 5120;
   }

   public SmoothFontRendererStringCache(SmoothFontRenderer smoothFontRenderer, int n, float f, boolean bl) {
      super(n, f, bl);
      this.L = smoothFontRenderer;
   }
}
