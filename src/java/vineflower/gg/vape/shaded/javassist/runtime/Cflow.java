package gg.vape.shaded.javassist.runtime;

public class Cflow extends ThreadLocal<Cflow.Depth> {
   protected synchronized Cflow.Depth initialValue() {
      return new Cflow.Depth();
   }

   public void enter() {
      this.get().inc();
   }

   public void exit() {
      this.get().dec();
   }

   public int value() {
      return this.get().value();
   }

   protected static class Depth {
      private int depth = 0;

      Depth() {
      }

      int value() {
         return this.depth;
      }

      void inc() {
         this.depth++;
      }

      void dec() {
         this.depth--;
      }
   }
}
