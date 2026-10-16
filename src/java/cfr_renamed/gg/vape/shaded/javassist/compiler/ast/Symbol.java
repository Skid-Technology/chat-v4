/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.ast.ASTree;
import gg.vape.shaded.javassist.compiler.ast.Visitor;

public class Symbol
extends ASTree {
    private final static long serialVersionUID = 1L;
    protected String identifier;

    public Symbol(String sym) {
        this.identifier = sym;
    }

    public String get() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return this.identifier;
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atSymbol(this);
    }
}

