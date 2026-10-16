/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.MemberResolver;
import gg.vape.shaded.javassist.compiler.ast.ASTList;
import gg.vape.shaded.javassist.compiler.ast.ASTree;
import gg.vape.shaded.javassist.compiler.ast.Expr;
import gg.vape.shaded.javassist.compiler.ast.Visitor;

public class CallExpr
extends Expr {
    private final static long serialVersionUID = 1L;
    private MemberResolver.Method method = null;

    private CallExpr(ASTree _head, ASTList _tail) {
        super(67, _head, _tail);
    }

    public void setMethod(MemberResolver.Method m) {
        this.method = m;
    }

    public MemberResolver.Method getMethod() {
        return this.method;
    }

    public static CallExpr makeCall(ASTree target, ASTree args) {
        return new CallExpr(target, new ASTList(args));
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atCallExpr(this);
    }
}

