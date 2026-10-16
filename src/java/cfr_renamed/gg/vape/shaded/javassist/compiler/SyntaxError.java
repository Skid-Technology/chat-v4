/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.compiler;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.Lex;

public class SyntaxError
extends CompileError {
    private final static long serialVersionUID = 1L;

    public SyntaxError(Lex lexer) {
        super("syntax error near \"" + lexer.getTextAround() + "\"", lexer);
    }
}

