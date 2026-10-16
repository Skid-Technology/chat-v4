/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Value;

public class AnalyzerException
extends Exception {
    private final static long serialVersionUID = 3154190448018943333L;
    final transient public AbstractInsnNode node;

    public AnalyzerException(AbstractInsnNode insn, String message) {
        super(message);
        this.node = insn;
    }

    public AnalyzerException(AbstractInsnNode insn, String message, Throwable cause) {
        super(message, cause);
        this.node = insn;
    }

    public AnalyzerException(AbstractInsnNode insn, String message, Object expected, Value actual) {
        super(AnalyzerException.stringConcat$1(message == null ? "Expected " : AnalyzerException.stringConcat$0(message), expected, actual));
        this.node = insn;
    }

    private static String stringConcat$0(String string) {
        return string + ": expected ";
    }

    private static String stringConcat$1(String string, Object object, Value value) {
        return string + object + ", but found " + value;
    }
}

