/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.commons.Remapper;
import gg.vape.shaded.org.objectweb.asm.signature.SignatureVisitor;
import java.util.ArrayList;

public class SignatureRemapper
extends SignatureVisitor {
    private final SignatureVisitor signatureVisitor;
    private final Remapper remapper;
    private ArrayList<String> classNames = new ArrayList();

    public SignatureRemapper(SignatureVisitor signatureVisitor, Remapper remapper) {
        this(589824, signatureVisitor, remapper);
    }

    protected SignatureRemapper(int api, SignatureVisitor signatureVisitor, Remapper remapper) {
        super(api);
        this.signatureVisitor = signatureVisitor;
        this.remapper = remapper;
    }

    public void visitClassType(String name) {
        this.classNames.add(name);
        this.signatureVisitor.visitClassType(this.remapper.mapType(name));
    }

    public void visitInnerClassType(String name) {
        String outerClassName = this.classNames.remove(this.classNames.size() - 1);
        String className = SignatureRemapper.stringConcat$0(outerClassName, name);
        this.classNames.add(className);
        String remappedOuter = SignatureRemapper.stringConcat$1(this.remapper.mapType(outerClassName));
        String remappedName = this.remapper.mapType(className);
        int index = remappedName.startsWith(remappedOuter) ? remappedOuter.length() : remappedName.lastIndexOf(36) + 1;
        this.signatureVisitor.visitInnerClassType(remappedName.substring(index));
    }

    private static /* synthetic */ String stringConcat$0(String string, String string2) {
        return string + "$" + string2;
    }

    private static /* synthetic */ String stringConcat$1(String string) {
        return string + "$";
    }

    public void visitFormalTypeParameter(String name) {
        this.signatureVisitor.visitFormalTypeParameter(name);
    }

    public void visitTypeVariable(String name) {
        this.signatureVisitor.visitTypeVariable(name);
    }

    public SignatureVisitor visitArrayType() {
        this.signatureVisitor.visitArrayType();
        return this;
    }

    public void visitBaseType(char descriptor) {
        this.signatureVisitor.visitBaseType(descriptor);
    }

    public SignatureVisitor visitClassBound() {
        this.signatureVisitor.visitClassBound();
        return this;
    }

    public SignatureVisitor visitExceptionType() {
        this.signatureVisitor.visitExceptionType();
        return this;
    }

    public SignatureVisitor visitInterface() {
        this.signatureVisitor.visitInterface();
        return this;
    }

    public SignatureVisitor visitInterfaceBound() {
        this.signatureVisitor.visitInterfaceBound();
        return this;
    }

    public SignatureVisitor visitParameterType() {
        this.signatureVisitor.visitParameterType();
        return this;
    }

    public SignatureVisitor visitReturnType() {
        this.signatureVisitor.visitReturnType();
        return this;
    }

    public SignatureVisitor visitSuperclass() {
        this.signatureVisitor.visitSuperclass();
        return this;
    }

    public void visitTypeArgument() {
        this.signatureVisitor.visitTypeArgument();
    }

    public SignatureVisitor visitTypeArgument(char wildcard) {
        this.signatureVisitor.visitTypeArgument(wildcard);
        return this;
    }

    public void visitEnd() {
        this.signatureVisitor.visitEnd();
        this.classNames.remove(this.classNames.size() - 1);
    }
}

