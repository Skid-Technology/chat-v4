/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MSlot;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MContainer
extends Mapping {
    private MappingMethod getCarriedMethod;
    private final MappingField windowIdField;
    private final MappingMethod getSlotMethod;
    private final MappingField slotsField;

    private Object getCarried(Object container) {
        return this.getCarriedMethod.invokeObject(container, new Object[0]);
    }

    public static Object getCarried(MContainer mapping, Object container) {
        return mapping.getCarried(container);
    }

    public List getSlots(Object container) {
        return (List)this.slotsField.getObject(container);
    }

    private Object getSlot(Object container, int slotIndex) {
        return this.getSlotMethod.invokeObject(container, slotIndex);
    }

    public int getWindowId(Object container) {
        return this.windowIdField.getInt(container);
    }

    public static Object getSlot(MContainer mapping, Object container, int slotIndex) {
        return mapping.getSlot(container, slotIndex);
    }

    public MContainer() {
        this(MSlot.getSlotControlFlowState());
    }

    private MContainer(int[] nArray) {
        super(MappedClasses.var_java_lang_Class_X);
        Class[] classArray;
        MContainer mContainer;
        String string;
        boolean bl;
        Class clazz;
        if (nArray != null) {
            Class<List> clazz2 = List.class;
            boolean bl2 = true;
            String string2 = "inventorySlots";
            MContainer mContainer2 = this;
            this.slotsField = mContainer2.J(string2, bl2, clazz2);
            Class<Integer> clazz22 = Integer.TYPE;
            boolean bl22 = true;
            String string22 = "windowId";
            MContainer mContainer22 = this;
            this.windowIdField = this.J(string22, bl22, clazz22);
            Class[] classArray2 = new Class[]{Integer.TYPE};
            Class clazz3 = MappedClasses.YQ;
            boolean bl3 = true;
            String string3 = "getSlot";
            MContainer mContainer3 = this;
            this.getSlotMethod = this.Y(string3, bl3, clazz3, classArray2);
            return;
        }
        if (ForgeVersion.MC_1_17.d()) {
            clazz = MappedClasses.Vd;
            bl = true;
            string = "slots";
            mContainer = this;
            this.slotsField = mContainer.J(string, bl, clazz);
            classArray = new Class[]{};
            Class clazz4 = MappedClasses.VK;
            boolean bl4 = true;
            String string4 = "getCarried";
            MContainer mContainer4 = this;
            this.getCarriedMethod = this.Y(string4, bl4, clazz4, classArray);
        } else {
            clazz = List.class;
            bl = true;
            string = "inventorySlots";
            mContainer = this;
            this.slotsField = mContainer.J(string, bl, clazz);
        }
        clazz = Integer.TYPE;
        bl = true;
        string = "windowId";
        mContainer = this;
        this.windowIdField = mContainer.J(string, bl, clazz);
        classArray = new Class[]{Integer.TYPE};
        Class clazz5 = MappedClasses.YQ;
        boolean bl5 = true;
        String string5 = "getSlot";
        MContainer mContainer5 = this;
        this.getSlotMethod = this.Y(string5, bl5, clazz5, classArray);
    }
}

