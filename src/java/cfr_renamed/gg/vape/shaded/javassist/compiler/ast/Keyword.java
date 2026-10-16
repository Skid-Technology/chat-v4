/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.ast.ASTree;
import gg.vape.shaded.javassist.compiler.ast.Visitor;

public class Keyword
extends ASTree {
    private final static long serialVersionUID = 1L;
    protected int tokenId;

    public Keyword(int token) {
        this.tokenId = token;
    }

    public int get() {
        return this.tokenId;
    }

    @Override
    public String toString() {
        return "id:" + this.tokenId;
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atKeyword(this);
    }
}

