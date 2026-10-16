package gg.vape.shaded.javassist.tools.web;

public class BadHttpRequest extends Exception {
   private static final long serialVersionUID = 1L;
   private Exception e;

   public BadHttpRequest() {
      this.e = null;
   }

   public BadHttpRequest(Exception _e) {
      this.e = _e;
   }

   @Override
   public String toString() {
      return this.e == null ? super.toString() : this.e.toString();
   }
}
