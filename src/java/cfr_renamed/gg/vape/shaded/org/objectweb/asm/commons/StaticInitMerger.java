/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ClassVisitor;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;

public class StaticInitMerger
extends ClassVisitor {
    private String owner;
    private final String renamedClinitMethodPrefix;
    private int numClinitMethods;
    private MethodVisitor mergedClinitVisitor;

    public StaticInitMerger(String prefix, ClassVisitor classVisitor) {
        this(589824, prefix, classVisitor);
    }

    protected StaticInitMerger(int api, String prefix, ClassVisitor classVisitor) {
        super(api, classVisitor);
        this.renamedClinitMethodPrefix = prefix;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.owner = name;
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor;
        if ("<clinit>".equals(name)) {
            int newAccess = 10;
            String newName = StaticInitMerger.stringConcat$0(this.renamedClinitMethodPrefix, this.numClinitMethods++);
            methodVisitor = super.visitMethod(newAccess, newName, descriptor, signature, exceptions);
            if (this.mergedClinitVisitor == null) {
                this.mergedClinitVisitor = super.visitMethod(newAccess, name, descriptor, null, null);
            }
            this.mergedClinitVisitor.visitMethodInsn(184, this.owner, newName, descriptor, false);
        } else {
            methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        }
        return methodVisitor;
    }

    private static String stringConcat$0(String string, int n) {
        return string + n;
    }

    public void visitEnd() {
        if (this.mergedClinitVisitor != null) {
            this.mergedClinitVisitor.visitInsn(177);
            this.mergedClinitVisitor.visitMaxs(0, 0);
        }
        super.visitEnd();
    }
}

