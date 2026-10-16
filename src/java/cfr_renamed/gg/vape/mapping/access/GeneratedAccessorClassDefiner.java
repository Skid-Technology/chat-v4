/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.access;

import com.google.common.collect.MapMaker;
import gg.vape.mapping.access.GeneratedAccessorClassLoader;
import java.util.concurrent.ConcurrentMap;

public class GeneratedAccessorClassDefiner {
    private final ConcurrentMap<ClassLoader, GeneratedAccessorClassLoader> var_java_util_concurrent_ConcurrentMap_java_lang_ClassLoader,_gg_vape_mapping_access_GeneratedAccessorClassLoader__t = new MapMaker().weakKeys().makeMap();
    final static boolean var_boolean_G;
    private static String[] var_java_lang_String_arr_x;
    final public static GeneratedAccessorClassDefiner var_gg_vape_mapping_access_GeneratedAccessorClassDefiner_o;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> defineGeneratedAccessorClass(ClassLoader classLoader, String string, byte[] byArray) {
        Object object;
        GeneratedAccessorClassLoader generatedAccessorClassLoader = this.var_java_util_concurrent_ConcurrentMap_java_lang_ClassLoader,_gg_vape_mapping_access_GeneratedAccessorClassLoader__t.computeIfAbsent(classLoader, GeneratedAccessorClassLoader::new);
        Object object2 = object = generatedAccessorClassLoader.getClassLoadingLock(string);
        synchronized (object2) {
            Class clazz = GeneratedAccessorClassLoader.d(generatedAccessorClassLoader, string, byArray);
            if (!var_boolean_G && !clazz.getName().equals(string)) {
                throw new AssertionError();
            }
            return clazz;
        }
    }

    public static void setStringTable(String[] stringArray) {
        var_java_lang_String_arr_x = stringArray;
    }

    public static String[] getStringTable() {
        return var_java_lang_String_arr_x;
    }

    static {
        if (GeneratedAccessorClassDefiner.getStringTable() == null) {
            GeneratedAccessorClassDefiner.setStringTable(new String[2]);
        }
        var_boolean_G = !GeneratedAccessorClassDefiner.class.desiredAssertionStatus();
        var_gg_vape_mapping_access_GeneratedAccessorClassDefiner_o = new GeneratedAccessorClassDefiner();
    }
}

