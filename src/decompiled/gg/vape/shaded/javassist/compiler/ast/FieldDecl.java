/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.ast.ASTList;
import gg.vape.shaded.javassist.compiler.ast.ASTree;
import gg.vape.shaded.javassist.compiler.ast.Declarator;
import gg.vape.shaded.javassist.compiler.ast.Visitor;

public class FieldDecl
extends ASTList {
    private static final long serialVersionUID = 1L;

    public FieldDecl(ASTree _head, ASTList _tail) {
        super(_head, _tail);
    }

    public ASTList getModifiers() {
        return (ASTList)this.getLeft();
    }

    public Declarator getDeclarator() {
        return (Declarator)this.tail().head();
    }

    public ASTree getInit() {
        return this.sublist(2).head();
    }

    @Override
    public void accept(Visitor v) throws CompileError {
        v.atFieldDecl(this);
    }
}

