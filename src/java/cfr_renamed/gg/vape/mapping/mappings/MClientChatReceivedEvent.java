/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MSPacketEntityVelocity;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MClientChatReceivedEvent
extends Mapping {
    private final MappingMethod var_gg_vape_mapping_MappingMethod_n;
    private final MappingField var_gg_vape_mapping_MappingField_a;

    public Object V(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_n.invokeObject(object, new Object[0]);
    }

    public MClientChatReceivedEvent() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MClientChatReceivedEvent(int[] nArray) {
        super(MappedClasses.Zu);
        int[] nArray2 = nArray;
        if (Wrapper.vapeInstance.isVanillaMinecraftPresent() || ForgeVersion.MC_1_8_9.Y()) {
            String string;
            boolean bl;
            Class clazz;
            Class[] classArray;
            if (ForgeVersion.MC_1_7_10.L()) {
                classArray = new Class[]{};
                clazz = MappedClasses.Yr;
                bl = true;
                string = "func_148915_c";
                MClientChatReceivedEvent mClientChatReceivedEvent = this;
                this.var_gg_vape_mapping_MappingMethod_n = mClientChatReceivedEvent.Y(string, bl, clazz, classArray);
            } else {
                classArray = new Class[]{};
                clazz = MappedClasses.Yr;
                bl = true;
                string = "getChatComponent";
                MClientChatReceivedEvent mClientChatReceivedEvent = this;
                this.var_gg_vape_mapping_MappingMethod_n = mClientChatReceivedEvent.Y(string, bl, clazz, classArray);
            }
            Class clazz2 = MappedClasses.Yr;
            boolean bl2 = true;
            String string2 = "chatComponent";
            MClientChatReceivedEvent mClientChatReceivedEvent = this;
            this.var_gg_vape_mapping_MappingField_a = mClientChatReceivedEvent.J(string2, bl2, clazz2);
        } else {
            Class[] classArray = new Class[]{};
            Class clazz = MappedClasses.Yr;
            boolean bl = Wrapper.isNativeAvailable;
            String string = "func_148915_c";
            MClientChatReceivedEvent mClientChatReceivedEvent = this;
            this.var_gg_vape_mapping_MappingMethod_n = mClientChatReceivedEvent.Y(string, bl, clazz, classArray);
            Class clazz2 = MappedClasses.Yr;
            boolean bl2 = Wrapper.isNativeAvailable;
            String string2 = "field_148919_a";
            MClientChatReceivedEvent mClientChatReceivedEvent2 = this;
            this.var_gg_vape_mapping_MappingField_a = this.J(string2, bl2, clazz2);
        }
    }

    public void i(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_a.setObject(object, object2);
    }
}

