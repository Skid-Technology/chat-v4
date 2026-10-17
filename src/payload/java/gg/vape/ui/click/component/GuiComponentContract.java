package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.theme.ThemeColors;

public interface GuiComponentContract {
   double t = 110.0;
   ThemeColors J = ThemeColors.J;
   double p = 20.0;

   default void F() {
   }

   default double x() {
      return 0.0;
   }

   default void onEnable() {
   }

   default void H() {
   }

   void K(double var1);

   double G$src$D$1b2f02a();

   double n();

   boolean V$src$Z$1xhop3l();

   double L();

   default double C() {
      return 0.0;
   }

   default void g(GuiMouseEvent guiMouseEvent) {
   }

   double A();

   default void I() {
   }

   void S(double var1);

   default void u() {
   }

   void Y(double var1);

   void o(double var1);

   default void U(GuiMouseEvent guiMouseEvent) {
   }
}
