package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;

public class ColorPaletteRefreshClickListener implements GuiClickListener {
   final ColorChannelSliderComponent channelSlider;
   final ColorPaletteSliderComponent paletteSlider;

   @Override
   public void onPrimaryClick() {
      this.paletteSlider.resetToMiddleColor();
   }

   public ColorPaletteRefreshClickListener(ColorChannelSliderComponent channelSlider, ColorPaletteSliderComponent paletteSlider) {
      this.channelSlider = channelSlider;
      this.paletteSlider = paletteSlider;
   }
}
