package gg.vape.ui.click.component;

public interface GuiRefreshListener {
   default void onRefresh() {
   }

   default void onIndexedRefresh(String text, int index) {
   }
}
