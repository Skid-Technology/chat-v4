/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.commons.Remapper;
import java.util.Collections;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SimpleRemapper
extends Remapper {
    private final Map<String, String> mapping;

    public SimpleRemapper(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    public SimpleRemapper(String oldName, String newName) {
        this.mapping = Collections.singletonMap(oldName, newName);
    }

    @Override
    public String mapMethodName(String owner, String name, String descriptor) {
        String remappedName = this.map(SimpleRemapper.stringConcat$0(owner, name, descriptor));
        return remappedName == null ? name : remappedName;
    }

    private static String stringConcat$0(String string, String string2, String string3) {
        return string + "." + string2 + string3;
    }

    @Override
    public String mapInvokeDynamicMethodName(String name, String descriptor) {
        String remappedName = this.map(SimpleRemapper.stringConcat$1(name, descriptor));
        return remappedName == null ? name : remappedName;
    }

    private static String stringConcat$1(String string, String string2) {
        return "." + string + string2;
    }

    @Override
    public String mapAnnotationAttributeName(String descriptor, String name) {
        String remappedName = this.map(SimpleRemapper.stringConcat$2(descriptor, name));
        return remappedName == null ? name : remappedName;
    }

    private static String stringConcat$2(String string, String string2) {
        return string + "." + string2;
    }

    @Override
    public String mapFieldName(String owner, String name, String descriptor) {
        String remappedName = this.map(SimpleRemapper.stringConcat$3(owner, name));
        return remappedName == null ? name : remappedName;
    }

    private static String stringConcat$3(String string, String string2) {
        return string + "." + string2;
    }

    @Override
    public String map(String key) {
        return this.mapping.get(key);
    }
}

