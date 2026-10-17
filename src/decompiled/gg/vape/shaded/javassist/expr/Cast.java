/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.expr;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.Javac;
import gg.vape.shaded.javassist.compiler.JvstCodeGen;
import gg.vape.shaded.javassist.compiler.JvstTypeChecker;
import gg.vape.shaded.javassist.compiler.ProceedHandler;
import gg.vape.shaded.javassist.compiler.ast.ASTList;
import gg.vape.shaded.javassist.expr.Expr;

public class Cast
extends Expr {
    protected Cast(int pos, CodeIterator i, CtClass declaring, MethodInfo m) {
        super(pos, i, declaring, m);
    }

    @Override
    public CtBehavior where() {
        return super.where();
    }

    @Override
    public int getLineNumber() {
        return super.getLineNumber();
    }

    @Override
    public String getFileName() {
        return super.getFileName();
    }

    public CtClass getType() throws NotFoundException {
        ConstPool cp = this.getConstPool();
        int pos = this.currentPos;
        int index = this.iterator.u16bitAt(pos + 1);
        String name = cp.getClassInfo(index);
        return this.thisClass.getClassPool().getCtClass(name);
    }

    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    @Override
    public void replace(String statement) throws CannotCompileException {
        this.thisClass.getClassFile();
        ConstPool constPool = this.getConstPool();
        int pos = this.currentPos;
        int index = this.iterator.u16bitAt(pos + 1);
        Javac jc = new Javac(this.thisClass);
        ClassPool cp = this.thisClass.getClassPool();
        CodeAttribute ca = this.iterator.get();
        try {
            CtClass[] params = new CtClass[]{cp.get("java.lang.Object")};
            CtClass retType = this.getType();
            int paramVar = ca.getMaxLocals();
            jc.recordParams("java.lang.Object", params, true, paramVar, this.withinStatic());
            int retVar = jc.recordReturnType(retType, true);
            jc.recordProceed(new ProceedForCast(index, retType));
            Cast.checkResultValue(retType, statement);
            Bytecode bytecode = jc.getBytecode();
            Cast.storeStack(params, true, paramVar, bytecode);
            jc.recordLocalVariables(ca, pos);
            bytecode.addConstZero(retType);
            bytecode.addStore(retVar, retType);
            jc.compileStmnt(statement);
            bytecode.addLoad(retVar, retType);
            this.replace0(pos, bytecode, 3);
        }
        catch (CompileError e) {
            throw new CannotCompileException(e);
        }
        catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
        catch (BadBytecode e) {
            throw new CannotCompileException("broken method");
        }
    }

    static class ProceedForCast
    implements ProceedHandler {
        int index;
        CtClass retType;

        ProceedForCast(int i, CtClass t) {
            this.index = i;
            this.retType = t;
        }

        @Override
        public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args) throws CompileError {
            if (gen.getMethodArgsLength(args) != 1) {
                throw new CompileError("$proceed() cannot take more than one parameter for cast");
            }
            gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
            bytecode.addOpcode(192);
            bytecode.addIndex(this.index);
            gen.setType(this.retType);
        }

        @Override
        public void setReturnType(JvstTypeChecker c, ASTList args) throws CompileError {
            c.atMethodArgs(args, new int[1], new int[1], new String[1]);
            c.setType(this.retType);
        }
    }
}

