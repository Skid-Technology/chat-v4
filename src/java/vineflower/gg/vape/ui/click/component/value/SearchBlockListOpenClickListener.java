package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiClickListener;

public class SearchBlockListOpenClickListener implements GuiClickListener {
   final SearchBlockListComponent component;

   @Override
   public void onPrimaryClick() {
      SearchBlockListComponent.openEditorCompat(this.component);
   }

   public SearchBlockListOpenClickListener(SearchBlockListComponent component) {
      this.component = component;
   }
}
