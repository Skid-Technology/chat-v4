/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.access;

import gg.vape.asm.helper.DescUtils;
import gg.vape.event.GeneratedEventHandlerInvokerMarker;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.access.FieldAccessor;
import gg.vape.mapping.access.GeneratedAccessorClassDefiner;
import gg.vape.mapping.access.MethodInvoker;
import gg.vape.shaded.org.objectweb.asm.ClassWriter;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.commons.GeneratorAdapter;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratedAccessorFactory {
    final public static String var_java_lang_String_l;
    private final static String var_java_lang_String_Q;
    final public static String var_java_lang_String_s;
    private final static Map<Method, Class<? extends MethodInvoker>> var_java_util_Map_java_lang_reflect_Method,_java_lang_Class___extends_gg_vape_mapping_access_MethodInvoker___D;
    private static Method var_java_lang_reflect_Method_G;
    private static Method var_java_lang_reflect_Method_Y;
    private final static Map<Field, Class<? extends FieldAccessor>> var_java_util_Map_java_lang_reflect_Field,_java_lang_Class___extends_gg_vape_mapping_access_FieldAccessor___M;
    private final static String var_java_lang_String_x;
    private static AtomicInteger var_java_util_concurrent_atomic_AtomicInteger_p;
    private static Method var_java_lang_reflect_Method_a;
    private final static String var_java_lang_String_S;
    private static Method var_java_lang_reflect_Method_c;

    public static Class<? extends MethodInvoker> Y(Mapping mapping, MappingMethod mappingMethod) {
        return GeneratedAccessorFactory.A(mapping.getMappedClass(), mappingMethod);
    }

    public static Class<? extends FieldAccessor> U(Class<?> clazz, Field field) {
        Class<? extends FieldAccessor> clazz2 = var_java_util_Map_java_lang_reflect_Field,_java_lang_Class___extends_gg_vape_mapping_access_FieldAccessor___M.get(field);
        if (clazz2 != null) {
            return clazz2;
        }
        GeneratedAccessorFactory.s();
        String string = GeneratedAccessorFactory.x(clazz, field);
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(52, 1, string.replace('.', '/'), null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(MethodInvoker.class)});
        GeneratorAdapter generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, "<init>", "()V", null, null), 1, "<init>", "()V");
        generatorAdapter.loadThis();
        generatorAdapter.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        generatorAdapter.returnValue();
        generatorAdapter.endMethod();
        generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, var_java_lang_reflect_Method_Y.getName(), "(Ljava/lang/Object;)Ljava/lang/Object;", null, null), 1, var_java_lang_reflect_Method_Y.getName(), "(Ljava/lang/Object;)Ljava/lang/Object;");
        generatorAdapter.visitCode();
        generatorAdapter.visitVarInsn(25, 1);
        generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz));
        generatorAdapter.visitFieldInsn(Modifier.isStatic(field.getModifiers()) ? 178 : 180, Type.getInternalName(clazz), field.getName(), DescUtils.getDescriptor(field.getType()));
        if (Boolean.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
        } else if (Byte.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
        } else if (Character.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
        } else if (Short.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
        } else if (Integer.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        } else if (Long.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
        } else if (Float.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
        } else if (Double.TYPE == field.getType()) {
            generatorAdapter.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
        } else if (DescUtils.getKnownDescriptor(field.getType()) == null) {
            generatorAdapter.visitTypeInsn(192, field.getType().getName());
        }
        generatorAdapter.visitInsn(176);
        generatorAdapter.visitMaxs(7, 3);
        generatorAdapter.visitEnd();
        classWriter.visitEnd();
        byte[] byArray = classWriter.toByteArray();
        try {
            Class<FieldAccessor> clazz3 = GeneratedAccessorClassDefiner.var_gg_vape_mapping_access_GeneratedAccessorClassDefiner_o.defineGeneratedAccessorClass(GeneratedAccessorFactory.class.getClassLoader(), string, byArray).asSubclass(FieldAccessor.class);
            var_java_util_Map_java_lang_reflect_Field,_java_lang_Class___extends_gg_vape_mapping_access_FieldAccessor___M.put(field, clazz3);
            return clazz3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Class<? extends GeneratedEventHandlerInvokerMarker> N(Class<?> clazz, Method method) {
        GeneratedAccessorFactory.s();
        String string = GeneratedAccessorFactory.x(clazz, method);
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(52, 1, string.replace('.', '/'), null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(GeneratedEventHandlerInvokerMarker.class)});
        GeneratorAdapter generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, "<init>", "()V", null, null), 1, "<init>", "()V");
        generatorAdapter.loadThis();
        generatorAdapter.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        generatorAdapter.returnValue();
        generatorAdapter.endMethod();
        generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, var_java_lang_reflect_Method_G.getName(), "(Ljava/lang/Object;Ljava/lang/Object;)V", null, null), 1, var_java_lang_reflect_Method_G.getName(), "(Ljava/lang/Object;Ljava/lang/Object;)V");
        generatorAdapter.visitCode();
        generatorAdapter.visitVarInsn(25, 1);
        generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz));
        if (method.getParameters().length > 0) {
            for (int i = 0; i < method.getParameters().length; ++i) {
                Class<?> parameterType = method.getParameterTypes()[i];
                generatorAdapter.visitVarInsn(25, 2);
                if (Boolean.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Boolean");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
                    continue;
                }
                if (Byte.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Byte");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B", false);
                    continue;
                }
                if (Character.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Character");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Character", "charValue", "()C", false);
                    continue;
                }
                if (Short.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Short");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S", false);
                    continue;
                }
                if (Integer.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Integer");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I", false);
                    continue;
                }
                if (Long.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Long");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Long", "longValue", "()J", false);
                    continue;
                }
                if (Float.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Float");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F", false);
                    continue;
                }
                if (Double.TYPE == parameterType) {
                    generatorAdapter.visitTypeInsn(192, "java/lang/Double");
                    generatorAdapter.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D", false);
                    continue;
                }
                generatorAdapter.visitTypeInsn(192, Type.getInternalName(parameterType));
            }
        }
        generatorAdapter.visitMethodInsn(Modifier.isStatic(method.getModifiers()) ? 184 : (clazz.isInterface() ? 185 : 182), Type.getInternalName(clazz), method.getName(), DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()), clazz.isInterface());
        generatorAdapter.visitInsn(177);
        generatorAdapter.endMethod();
        classWriter.visitEnd();
        byte[] byArray = classWriter.toByteArray();
        try {
            return GeneratedAccessorClassDefiner.var_gg_vape_mapping_access_GeneratedAccessorClassDefiner_o.defineGeneratedAccessorClass(GeneratedAccessorFactory.class.getClassLoader(), string, byArray).asSubclass(GeneratedEventHandlerInvokerMarker.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    public static Class<? extends MethodInvoker> A(Class clazz, MappingMethod mappingMethod) {
        if (mappingMethod.hasResolutionFailed()) {
            return null;
        }
        try {
            try {
                Method method = clazz.getMethod(mappingMethod.getResolvedName(), mappingMethod.getResolvedParameterTypes());
                return GeneratedAccessorFactory.I(clazz, method);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return null;
            }
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static void i(String string, byte[] byArray) {
    }

    public static Class<? extends FieldAccessor> z(Mapping mapping, MappingField mappingField) {
        if (mappingField.hasResolutionFailed()) {
            return null;
        }
        try {
            return GeneratedAccessorFactory.U(mapping.getMappedClass(), mappingField.findReflectedField());
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    private static Method s() {
        if (var_java_lang_reflect_Method_c == null) {
            for (Method method : MethodInvoker.class.getDeclaredMethods()) {
                if (!"(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) continue;
                var_java_lang_reflect_Method_c = method;
                break;
            }
            if (var_java_lang_reflect_Method_c == null) {
                // empty if block
            }
            for (Method method : GeneratedEventHandlerInvokerMarker.class.getDeclaredMethods()) {
                if (!"(Ljava/lang/Object;Ljava/lang/Object;)V".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) continue;
                var_java_lang_reflect_Method_G = method;
                break;
            }
            if (var_java_lang_reflect_Method_G == null) {
                // empty if block
            }
        }
        if (var_java_lang_reflect_Method_Y == null || var_java_lang_reflect_Method_a == null) {
            for (Method method : FieldAccessor.class.getDeclaredMethods()) {
                if ("(Ljava/lang/Object;)Ljava/lang/Object;".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) {
                    var_java_lang_reflect_Method_Y = method;
                }
                if (!"(Ljava/lang/Object;Ljava/lang/Object;)V".equals(DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()))) continue;
                var_java_lang_reflect_Method_a = method;
            }
            if (var_java_lang_reflect_Method_Y == null) {
                // empty if block
            }
            if (var_java_lang_reflect_Method_a == null) {
                // empty if block
            }
        }
        return var_java_lang_reflect_Method_c;
    }

    public static Class<? extends MethodInvoker> I(Class<?> clazz, Method method) {
        Class<? extends MethodInvoker> clazz3 = var_java_util_Map_java_lang_reflect_Method,_java_lang_Class___extends_gg_vape_mapping_access_MethodInvoker___D.get(method);
        if (clazz3 != null) {
            return clazz3;
        }
        GeneratedAccessorFactory.s();
        String string = GeneratedAccessorFactory.x(clazz, method);
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(52, 1, string.replace('.', '/'), null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(MethodInvoker.class)});
        GeneratorAdapter generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, "<init>", "()V", null, null), 1, "<init>", "()V");
        generatorAdapter.loadThis();
        generatorAdapter.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        generatorAdapter.returnValue();
        generatorAdapter.endMethod();
        generatorAdapter = new GeneratorAdapter(classWriter.visitMethod(1, var_java_lang_reflect_Method_c.getName(), "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", null, null), 1, var_java_lang_reflect_Method_c.getName(), "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
        generatorAdapter.visitCode();
        generatorAdapter.visitVarInsn(25, 1);
        generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz));
        for (int i = 0; i < method.getParameters().length; ++i) {
            Class<?> clazz2 = method.getParameterTypes()[i];
            generatorAdapter.visitVarInsn(25, 2);
            generatorAdapter.visitIntInsn(16, i);
            generatorAdapter.visitInsn(50);
            if (Boolean.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Boolean");
                generatorAdapter.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
                continue;
            }
            if (Byte.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Byte");
                generatorAdapter.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B", false);
                continue;
            }
            if (Character.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Character");
                generatorAdapter.visitMethodInsn(182, "java/lang/Character", "charValue", "()C", false);
                continue;
            }
            if (Short.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Short");
                generatorAdapter.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S", false);
                continue;
            }
            if (Integer.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Integer");
                generatorAdapter.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I", false);
                continue;
            }
            if (Long.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Long");
                generatorAdapter.visitMethodInsn(182, "java/lang/Long", "longValue", "()J", false);
                continue;
            }
            if (Float.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Float");
                generatorAdapter.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F", false);
                continue;
            }
            if (Double.TYPE == clazz2) {
                generatorAdapter.visitTypeInsn(192, "java/lang/Double");
                generatorAdapter.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D", false);
                continue;
            }
            generatorAdapter.visitTypeInsn(192, Type.getInternalName(clazz2));
        }
        generatorAdapter.visitMethodInsn(Modifier.isStatic(method.getModifiers()) ? 184 : (clazz.isInterface() ? 185 : 182), Type.getInternalName(clazz), method.getName(), DescUtils.genMethodDesc(method.getReturnType(), method.getParameterTypes()), clazz.isInterface());
        if (method.getReturnType() == Void.TYPE) {
            generatorAdapter.visitInsn(1);
            generatorAdapter.visitInsn(176);
        } else {
            if (Boolean.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
            } else if (Byte.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
            } else if (Character.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
            } else if (Short.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
            } else if (Integer.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            } else if (Long.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
            } else if (Float.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
            } else if (Double.TYPE == method.getReturnType()) {
                generatorAdapter.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
            } else if (DescUtils.getKnownDescriptor(method.getReturnType()) == null) {
                // empty if block
            }
            generatorAdapter.visitInsn(176);
        }
        generatorAdapter.endMethod();
        classWriter.visitEnd();
        byte[] byArray = classWriter.toByteArray();
        try {
            Class<MethodInvoker> generatedClass = GeneratedAccessorClassDefiner.var_gg_vape_mapping_access_GeneratedAccessorClassDefiner_o.defineGeneratedAccessorClass(GeneratedAccessorFactory.class.getClassLoader(), string, byArray).asSubclass(MethodInvoker.class);
            var_java_util_Map_java_lang_reflect_Method,_java_lang_Class___extends_gg_vape_mapping_access_MethodInvoker___D.put(method, generatedClass);
            return generatedClass;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static String x(Class<?> clazz, Member member) {
        int n = var_java_util_concurrent_atomic_AtomicInteger_p.incrementAndGet();
        return "aaa_" + n;
    }

    static {
        var_java_lang_String_Q = "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;";
        var_java_lang_String_l = "(Ljava/lang/Object;)Ljava/lang/Object;";
        var_java_lang_String_s = "(Ljava/lang/Object;Ljava/lang/Object;)V";
        var_java_lang_String_S = "(Ljava/lang/Object;Ljava/lang/Object;)V";
        var_java_lang_String_x = "(Ljava/lang/Object;)Ljava/lang/Object;";
        var_java_util_concurrent_atomic_AtomicInteger_p = new AtomicInteger(0);
        var_java_util_Map_java_lang_reflect_Method,_java_lang_Class___extends_gg_vape_mapping_access_MethodInvoker___D = new LinkedHashMap<Method, Class<? extends MethodInvoker>>();
        var_java_util_Map_java_lang_reflect_Field,_java_lang_Class___extends_gg_vape_mapping_access_FieldAccessor___M = new LinkedHashMap<Field, Class<? extends FieldAccessor>>();
    }
}

