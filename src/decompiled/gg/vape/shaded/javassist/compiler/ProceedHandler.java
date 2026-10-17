/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler;

import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.JvstCodeGen;
import gg.vape.shaded.javassist.compiler.JvstTypeChecker;
import gg.vape.shaded.javassist.compiler.ast.ASTList;

public interface ProceedHandler {
    public void doit(JvstCodeGen var1, Bytecode var2, ASTList var3) throws CompileError;

    public void setReturnType(JvstTypeChecker var1, ASTList var2) throws CompileError;
}

