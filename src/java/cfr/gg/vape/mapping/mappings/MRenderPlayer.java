/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MRenderManager;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderPlayer
extends Mapping {
    private MappingField modelBipedMainField;
    public MappingMethod shouldRenderPassMethod;
    private MappingField entityModelField;
    public MappingMethod renderEquippedItemsMethod;
    public MappingMethod renderMethod;
    private MappingMethod getMainModelMethod;

    public MRenderPlayer() {
        this(MRenderManager.O());
    }

    private MRenderPlayer(String[] renderManagerControlFlowState) {
        super(MappedClasses.D0);
        if (ForgeVersion.MC_1_7_10.Y()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                this.renderMethod = ForgeVersion.MC_1_21_10.d() ? null : (ForgeVersion.MC_1_21_4.d() ? this.registerInstanceMethodForOwner(MappedClasses.Fq, "render", true, Void.TYPE, MappedClasses.uo, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE) : this.Y("render", true, Void.TYPE, MappedClasses.zt, Float.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE));
                this.entityModelField = this.registerInstanceFieldForOwner(MappedClasses.Fq, "entityModel", true, MappedClasses.V6);
            } else {
                this.renderMethod = this.Y("doRender", true, Void.TYPE, MappedClasses.zt, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
                this.getMainModelMethod = this.Y("getMainModel", true, MappedClasses.ud, new Class[0]);
            }
        } else {
            this.renderMethod = this.Y("doRender", true, Void.TYPE, MappedClasses.zt, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
            this.modelBipedMainField = this.J("modelBipedMain", true, MappedClasses.zV);
            this.renderEquippedItemsMethod = Wrapper.isNativeAvailable ? this.Y("renderEquippedItems", true, Void.TYPE, MappedClasses.zm, Float.TYPE) : this.Y("renderEquippedItems", true, Void.TYPE, MappedClasses.zt, Float.TYPE);
            this.shouldRenderPassMethod = this.Y("shouldRenderPass", true, Integer.TYPE, MappedClasses.zt, Integer.TYPE, Float.TYPE);
        }
    }

    public Object getMainModel(Object renderPlayerHandle) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.entityModelField.getObject(renderPlayerHandle);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            return this.getMainModelMethod.invokeObject(renderPlayerHandle, new Object[0]);
        }
        return this.modelBipedMainField.getObject(renderPlayerHandle);
    }
}

