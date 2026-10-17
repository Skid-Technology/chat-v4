package gg.vape.ui.click.frame;

public interface CollapsibleFrame {
   void w();

   boolean q();

   default void void_w() {
      this.w();
   }

   default boolean boolean_q() {
      return this.q();
   }
}
