/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MVisGraph;
import gg.vape.ui.click.component.GuiComponent;

public class MSetVisibility
extends Mapping {
    final public MappingMethod setAllVisibleMethod;
    final public MappingMethod constructor;

    public void setAllVisible(Object visibilitySet, boolean visible) {
        this.setAllVisibleMethod.invokeVoid(visibilitySet, visible);
    }

    public MSetVisibility() {
        this(MVisGraph.getVisGraphControlFlowMarker());
    }

    private MSetVisibility(String controlFlowMarker) {
        super(MappedClasses.SET_VISIBILITY);
        Class[] constructorParameterTypes = new Class[]{};
        Class<Void> constructorReturnType = Void.TYPE;
        boolean remapConstructor = false;
        String constructorName = "<init>";
        MSetVisibility mappings = this;
        this.constructor = mappings.Y(constructorName, remapConstructor, constructorReturnType, constructorParameterTypes);
        if (controlFlowMarker != null) {
            Class[] parameterTypes = new Class[]{Boolean.TYPE};
            Class<Void> returnType = Void.TYPE;
            boolean remap = true;
            String methodName = "setAllVisible";
            this.setAllVisibleMethod = this.Y(methodName, remap, returnType, parameterTypes);
            return;
        }
        Class[] parameterTypes = new Class[]{Boolean.TYPE};
        Class<Void> returnType = Void.TYPE;
        boolean remap = true;
        String methodName = "setAllVisible";
        this.setAllVisibleMethod = this.Y(methodName, remap, returnType, parameterTypes);
        GuiComponent.setLegacyComponentState(new GuiComponent[2]);
    }
}

