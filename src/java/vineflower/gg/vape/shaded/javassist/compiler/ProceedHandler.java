package gg.vape.shaded.javassist.compiler;

import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.compiler.ast.ASTList;

public interface ProceedHandler {
   void doit(JvstCodeGen var1, Bytecode var2, ASTList var3) throws CompileError;

   void setReturnType(JvstTypeChecker var1, ASTList var2) throws CompileError;
}
