/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.tools.rmi;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.CtConstructor;
import gg.vape.shaded.javassist.CtField;
import gg.vape.shaded.javassist.CtMethod;
import gg.vape.shaded.javassist.CtNewConstructor;
import gg.vape.shaded.javassist.CtNewMethod;
import gg.vape.shaded.javassist.Modifier;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.Translator;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

public class StubGenerator
implements Translator {
    private final static String fieldImporter = "importer";
    private final static String fieldObjectId = "objectId";
    private final static String accessorObjectId = "_getObjectId";
    private final static String sampleClass = "gg.vape.shaded.javassist.tools.rmi.Sample";
    private ClassPool classPool;
    private Map<String, CtClass> proxyClasses = new Hashtable<String, CtClass>();
    private CtMethod forwardMethod;
    private CtMethod forwardStaticMethod;
    private CtClass[] proxyConstructorParamTypes;
    private CtClass[] interfacesForProxy;
    private CtClass[] exceptionForProxy;

    @Override
    public void start(ClassPool pool) throws NotFoundException {
        this.classPool = pool;
        CtClass c = pool.get(sampleClass);
        this.forwardMethod = c.getDeclaredMethod("forward");
        this.forwardStaticMethod = c.getDeclaredMethod("forwardStatic");
        this.proxyConstructorParamTypes = pool.get(new String[]{"gg.vape.shaded.javassist.tools.rmi.ObjectImporter", "int"});
        this.interfacesForProxy = pool.get(new String[]{"java.io.Serializable", "gg.vape.shaded.javassist.tools.rmi.Proxy"});
        this.exceptionForProxy = new CtClass[]{pool.get("gg.vape.shaded.javassist.tools.rmi.RemoteException")};
    }

    @Override
    public void onLoad(ClassPool pool, String classname) {
    }

    public boolean isProxyClass(String name) {
        return this.proxyClasses.get(name) != null;
    }

    public synchronized boolean makeProxyClass(Class<?> clazz) throws CannotCompileException, NotFoundException {
        String classname = clazz.getName();
        if (this.proxyClasses.get(classname) != null) {
            return false;
        }
        CtClass ctclazz = this.produceProxyClass(this.classPool.get(classname), clazz);
        this.proxyClasses.put(classname, ctclazz);
        this.modifySuperclass(ctclazz);
        return true;
    }

    private CtClass produceProxyClass(CtClass orgclass, Class<?> orgRtClass) throws CannotCompileException, NotFoundException {
        int modify = orgclass.getModifiers();
        if (Modifier.isAbstract(modify) || Modifier.isNative(modify) || !Modifier.isPublic(modify)) {
            throw new CannotCompileException(orgclass.getName() + " must be public, non-native, and non-abstract.");
        }
        CtClass proxy = this.classPool.makeClass(orgclass.getName(), orgclass.getSuperclass());
        proxy.setInterfaces(this.interfacesForProxy);
        CtField f = new CtField(this.classPool.get("gg.vape.shaded.javassist.tools.rmi.ObjectImporter"), fieldImporter, proxy);
        f.setModifiers(2);
        proxy.addField(f, CtField.Initializer.byParameter(0));
        f = new CtField(CtClass.intType, fieldObjectId, proxy);
        f.setModifiers(2);
        proxy.addField(f, CtField.Initializer.byParameter(1));
        proxy.addMethod(CtNewMethod.getter(accessorObjectId, f));
        proxy.addConstructor(CtNewConstructor.defaultConstructor(proxy));
        CtConstructor cons = CtNewConstructor.skeleton(this.proxyConstructorParamTypes, null, proxy);
        proxy.addConstructor(cons);
        try {
            this.addMethods(proxy, orgRtClass.getMethods());
            return proxy;
        }
        catch (SecurityException e) {
            throw new CannotCompileException(e);
        }
    }

    private CtClass toCtClass(Class<?> rtclass) throws NotFoundException {
        String name;
        if (!rtclass.isArray()) {
            name = rtclass.getName();
        } else {
            StringBuilder sbuf = new StringBuilder();
            do {
                sbuf.append("[]");
            } while ((rtclass = rtclass.getComponentType()).isArray());
            sbuf.insert(0, rtclass.getName());
            name = sbuf.toString();
        }
        return this.classPool.get(name);
    }

    private CtClass[] toCtClass(Class<?>[] rtclasses) throws NotFoundException {
        int n = rtclasses.length;
        CtClass[] ctclasses = new CtClass[n];
        for (int i = 0; i < n; ++i) {
            ctclasses[i] = this.toCtClass(rtclasses[i]);
        }
        return ctclasses;
    }

    private void addMethods(CtClass proxy, Method[] ms) throws CannotCompileException, NotFoundException {
        for (int i = 0; i < ms.length; ++i) {
            Method m = ms[i];
            int mod = m.getModifiers();
            if (m.getDeclaringClass() == Object.class || Modifier.isFinal(mod)) continue;
            if (Modifier.isPublic(mod)) {
                CtMethod body = Modifier.isStatic(mod) ? this.forwardStaticMethod : this.forwardMethod;
                CtMethod wmethod = CtNewMethod.wrapped(this.toCtClass(m.getReturnType()), m.getName(), this.toCtClass(m.getParameterTypes()), this.exceptionForProxy, body, CtMethod.ConstParameter.integer(i), proxy);
                wmethod.setModifiers(mod);
                proxy.addMethod(wmethod);
                continue;
            }
            if (Modifier.isProtected(mod) || Modifier.isPrivate(mod)) continue;
            throw new CannotCompileException("the methods must be public, protected, or private.");
        }
    }

    private void modifySuperclass(CtClass orgclass) throws CannotCompileException, NotFoundException {
        CtClass superclazz;
        while ((superclazz = orgclass.getSuperclass()) != null) {
            try {
                superclazz.getDeclaredConstructor(null);
                break;
            }
            catch (NotFoundException notFoundException) {
                superclazz.addConstructor(CtNewConstructor.defaultConstructor(superclazz));
                orgclass = superclazz;
            }
        }
    }
}

