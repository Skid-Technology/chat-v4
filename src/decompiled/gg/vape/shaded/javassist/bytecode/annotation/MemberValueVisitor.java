/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.bytecode.annotation.AnnotationMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.ArrayMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.BooleanMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.ByteMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.CharMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.ClassMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.DoubleMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.EnumMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.FloatMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.IntegerMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.LongMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.ShortMemberValue;
import gg.vape.shaded.javassist.bytecode.annotation.StringMemberValue;

public interface MemberValueVisitor {
    public void visitAnnotationMemberValue(AnnotationMemberValue var1);

    public void visitArrayMemberValue(ArrayMemberValue var1);

    public void visitBooleanMemberValue(BooleanMemberValue var1);

    public void visitByteMemberValue(ByteMemberValue var1);

    public void visitCharMemberValue(CharMemberValue var1);

    public void visitDoubleMemberValue(DoubleMemberValue var1);

    public void visitEnumMemberValue(EnumMemberValue var1);

    public void visitFloatMemberValue(FloatMemberValue var1);

    public void visitIntegerMemberValue(IntegerMemberValue var1);

    public void visitLongMemberValue(LongMemberValue var1);

    public void visitShortMemberValue(ShortMemberValue var1);

    public void visitStringMemberValue(StringMemberValue var1);

    public void visitClassMemberValue(ClassMemberValue var1);
}

