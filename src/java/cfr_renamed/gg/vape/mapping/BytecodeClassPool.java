/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.mapping.MappingClassBytecodeResolver;
import gg.vape.shaded.javassist.ByteArrayClassPath;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

public class BytecodeClassPool
extends ClassPool {
    private final MappingClassBytecodeResolver var_gg_vape_mapping_MappingClassBytecodeResolver_W;
    private final Set<String> runtimeClassPaths = new HashSet<String>();

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public synchronized CtClass get(String string) throws NotFoundException {
        this.ensureRuntimeClassPath(string);
        try {
            return super.get(string);
        }
        catch (NotFoundException notFoundException) {
            if (this.N(string)) {
                return super.get(string);
            }
            throw notFoundException;
        }
        catch (RuntimeException runtimeException) {
            if (this.N(string)) {
                try {
                    return super.get(string);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            try {
                CtClass existing = super.getOrNull(string);
                if (existing != null) {
                    return existing;
                }
                return this.makeClass(BytecodeClassPool.normalizeClassName(string));
            }
            catch (Throwable throwable) {
                throw runtimeException;
            }
        }
    }

    public BytecodeClassPool(MappingClassBytecodeResolver mappingClassBytecodeResolver) {
        super(true);
        this.var_gg_vape_mapping_MappingClassBytecodeResolver_W = mappingClassBytecodeResolver;
    }

    @Override
    public synchronized CtClass getOrNull(String string) {
        this.ensureRuntimeClassPath(string);
        try {
            CtClass ctClass = super.getOrNull(string);
            if (ctClass == null && this.N(string)) {
                ctClass = super.getOrNull(string);
            }
            return ctClass;
        }
        catch (RuntimeException runtimeException) {
            if (this.N(string)) {
                try {
                    return super.getOrNull(string);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            try {
                return this.makeClass(BytecodeClassPool.normalizeClassName(string));
            }
            catch (Throwable ignored) {
                return null;
            }
        }
    }

    private void ensureRuntimeClassPath(String requestedName) {
        String className = BytecodeClassPool.normalizeClassName(requestedName);
        if (className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("sun.") || className.startsWith("jdk.")) {
            return;
        }
        if (!this.runtimeClassPaths.add(className)) {
            return;
        }
        byte[] bytecode = this.var_gg_vape_mapping_MappingClassBytecodeResolver_W.y(className);
        if (bytecode == null) {
            this.runtimeClassPaths.remove(className);
            return;
        }
        this.removeCached(className);
        try {
            CtClass cc = this.makeClass(new ByteArrayInputStream(bytecode));
            if (!cc.getName().equals(className)) {
                cc.setName(className);
            }
        }
        catch (Exception e) {
            this.insertClassPath(new ByteArrayClassPath(className, bytecode));
        }
    }

    private boolean N(String string) {
        String className = BytecodeClassPool.normalizeClassName(string);
        byte[] byArray = this.var_gg_vape_mapping_MappingClassBytecodeResolver_W.y(className);
        if (byArray != null) {
            try {
                CtClass cc = this.makeClass(new ByteArrayInputStream(byArray));
                if (!cc.getName().equals(className)) {
                    cc.setName(className);
                }
            }
            catch (Exception e) {
                this.insertClassPath(new ByteArrayClassPath(className, byArray));
            }
            return true;
        }
        return false;
    }

    private static String normalizeClassName(String requestedName) {
        String className = requestedName;
        if (className.startsWith("[")) {
            className = Descriptor.toClassName(className);
        }
        while (className.endsWith("[]")) {
            className = className.substring(0, className.length() - 2);
        }
        return className;
    }
}

