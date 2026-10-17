package gg.vape.ui.click;

public class MousePosition {
   public int H;
   public int O;
   private static final String b = "[%s, %s]";

   @Override
   public String toString() {
      return String.format("[%s, %s]", this.O, this.H);
   }

   public MousePosition(int n, int n2) {
      this.O = n;
      this.H = n2;
   }
}
