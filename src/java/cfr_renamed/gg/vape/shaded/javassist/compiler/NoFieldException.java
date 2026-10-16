/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.ast.ASTree;

public class NoFieldException
extends CompileError {
    private final static long serialVersionUID = 1L;
    private String fieldName;
    private ASTree expr;

    public NoFieldException(String name, ASTree e) {
        super("no such field: " + name);
        this.fieldName = name;
        this.expr = e;
    }

    public String getField() {
        return this.fieldName;
    }

    public ASTree getExpr() {
        return this.expr;
    }
}

