/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.runtime;

import gg.vape.mapping.runtime.RuntimeNameMappingRegistry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClassNameRemapTable {
    public static boolean propagateMappingsToRuntimeRegistry = false;
    private final HashMap<String, String> classNameMappings = new HashMap();
    private static int[] controlFlowState;

    public static int[] getControlFlowState() {
        return controlFlowState;
    }

    public void Q(String sourceClassName, String targetClassName) {
        String previousTarget = this.classNameMappings.get(sourceClassName);
        this.classNameMappings.put(sourceClassName, targetClassName);
        if (propagateMappingsToRuntimeRegistry) {
            String remappedSourceName = RuntimeNameMappingRegistry.remapClassName(sourceClassName);
            String runtimeSourceName = remappedSourceName != null ? remappedSourceName : sourceClassName;
            RuntimeNameMappingRegistry.registerClassName(runtimeSourceName, targetClassName);
        }
    }

    public static void setControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public String lookupRemappedClassName(String sourceClassName) {
        return this.classNameMappings.getOrDefault(sourceClassName, null);
    }

    public Map<String, String> getMappings() {
        return Collections.unmodifiableMap(this.classNameMappings);
    }

    static {
        ClassNameRemapTable.setControlFlowState(new int[3]);
    }
}

