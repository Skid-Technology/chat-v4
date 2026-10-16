/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.ClassMap;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.CtConstructor;
import gg.vape.shaded.javassist.CtMember;
import gg.vape.shaded.javassist.CtMethod;
import gg.vape.shaded.javassist.CtNewWrappedConstructor;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.Javac;

public class CtNewConstructor {
    final public static int PASS_NONE = 0;
    final public static int PASS_ARRAY = 1;
    final public static int PASS_PARAMS = 2;

    public static CtConstructor make(String src, CtClass declaring) throws CannotCompileException {
        Javac compiler = new Javac(declaring);
        try {
            CtMember obj = compiler.compile(src);
            if (obj instanceof CtConstructor) {
                return (CtConstructor)obj;
            }
        }
        catch (CompileError e) {
            throw new CannotCompileException(e);
        }
        throw new CannotCompileException("not a constructor");
    }

    public static CtConstructor make(CtClass[] parameters, CtClass[] exceptions, String body, CtClass declaring) throws CannotCompileException {
        try {
            CtConstructor cc = new CtConstructor(parameters, declaring);
            cc.setExceptionTypes(exceptions);
            cc.setBody(body);
            return cc;
        }
        catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static CtConstructor copy(CtConstructor c, CtClass declaring, ClassMap map) throws CannotCompileException {
        return new CtConstructor(c, declaring, map);
    }

    public static CtConstructor defaultConstructor(CtClass declaring) throws CannotCompileException {
        CtConstructor cons = new CtConstructor((CtClass[])null, declaring);
        ConstPool cp = declaring.getClassFile2().getConstPool();
        Bytecode code = new Bytecode(cp, 1, 1);
        code.addAload(0);
        try {
            code.addInvokespecial(declaring.getSuperclass(), "<init>", "()V");
        }
        catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
        code.add(177);
        cons.getMethodInfo2().setCodeAttribute(code.toCodeAttribute());
        return cons;
    }

    public static CtConstructor skeleton(CtClass[] parameters, CtClass[] exceptions, CtClass declaring) throws CannotCompileException {
        return CtNewConstructor.make(parameters, exceptions, 0, null, null, declaring);
    }

    public static CtConstructor make(CtClass[] parameters, CtClass[] exceptions, CtClass declaring) throws CannotCompileException {
        return CtNewConstructor.make(parameters, exceptions, 2, null, null, declaring);
    }

    public static CtConstructor make(CtClass[] parameters, CtClass[] exceptions, int howto, CtMethod body, CtMethod.ConstParameter cparam, CtClass declaring) throws CannotCompileException {
        return CtNewWrappedConstructor.wrapped(parameters, exceptions, howto, body, cparam, declaring);
    }
}

