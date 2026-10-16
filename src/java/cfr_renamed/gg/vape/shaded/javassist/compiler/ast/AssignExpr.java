/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.ast.ASTList;
import gg.vape.shaded.javassist.compiler.ast.ASTree;
import gg.vape.shaded.javassist.compiler.ast.Expr;
import gg.vape.shaded.javassist.compiler.ast.Visitor;

public class AssignExpr
extends Expr {
    private final static long serialVersionUID = 1L;

    private AssignExpr(int op, ASTree _head, ASTList _tail) {
        super(op, _head, _tail);
    }

    public static AssignExpr makeAssign(int op, ASTree oprand1, ASTree oprand2) {
        return new AssignExpr(op, oprand1, new ASTList(oprand2));
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atAssignExpr(this);
    }
}

