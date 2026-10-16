package gg.vape.shaded.org.objectweb.asm;

final class CurrentFrame extends Frame {
   CurrentFrame(Label owner) {
      super(owner);
   }

   @Override
   void execute(int opcode, int arg, Symbol symbolArg, SymbolTable symbolTable) {
      super.execute(opcode, arg, symbolArg, symbolTable);
      Frame successor = new Frame(null);
      this.merge(symbolTable, successor, 0);
      this.copyFrom(successor);
   }
}
