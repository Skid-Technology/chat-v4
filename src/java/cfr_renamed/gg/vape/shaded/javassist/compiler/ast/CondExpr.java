/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.ast.ASTList;
import gg.vape.shaded.javassist.compiler.ast.ASTree;
import gg.vape.shaded.javassist.compiler.ast.Visitor;

public class CondExpr
extends ASTList {
    private final static long serialVersionUID = 1L;

    public CondExpr(ASTree cond, ASTree thenp, ASTree elsep) {
        super(cond, new ASTList(thenp, new ASTList(elsep)));
    }

    public ASTree condExpr() {
        return this.head();
    }

    public void setCond(ASTree t) {
        this.setHead(t);
    }

    public ASTree thenExpr() {
        return this.tail().head();
    }

    public void setThen(ASTree t) {
        this.tail().setHead(t);
    }

    public ASTree elseExpr() {
        return this.tail().tail().head();
    }

    public void setElse(ASTree t) {
        this.tail().tail().setHead(t);
    }

    @Override
    public String getTag() {
        return "?:";
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atCondExpr(this);
    }
}

