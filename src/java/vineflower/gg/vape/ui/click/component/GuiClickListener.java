package gg.vape.ui.click.component;

public interface GuiClickListener {
   void onPrimaryClick();

   default void onSecondaryClick() {
   }
}
