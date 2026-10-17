/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.mappings.MTileEntityMobSpawner;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderGameOverlayEvent
extends Mapping {
    private MappingMethod p;
    private final MappingField E;
    private MappingField C;
    private final MappingField m;

    public float o(Object object, float f) {
        return this.p.invokeFloat(object, Float.valueOf(f));
    }

    public Object b(Object object) {
        return this.C.getObject(object);
    }

    public int s(Object object) {
        return this.C.getInt(object);
    }

    public float G(Object object) {
        return this.E.getFloat(object);
    }

    public MRenderGameOverlayEvent() {
        this(MTileEntityMobSpawner.getMobSpawnerControlFlowState());
    }

    private MRenderGameOverlayEvent(int[] nArray) {
        super(MappedClasses.DZ);
        MRenderGameOverlayEvent mRenderGameOverlayEvent;
        String string;
        boolean bl;
        Class clazz;
        if (nArray != null) {
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "numPlayersUsing";
            MRenderGameOverlayEvent mRenderGameOverlayEvent2 = this;
            this.m = mRenderGameOverlayEvent2.J(string2, bl2, clazz2);
            if (ForgeVersion.MC_1_12_2.d()) {
                Class clazz22 = MappedClasses.q1;
                boolean bl22 = true;
                String string22 = "cachedChestType";
                MRenderGameOverlayEvent mRenderGameOverlayEvent22 = this;
                this.C = this.J(string22, bl22, clazz22);
                Class<Integer> clazz3 = Integer.TYPE;
                boolean bl3 = true;
                String string3 = "cachedChestType";
                MRenderGameOverlayEvent mRenderGameOverlayEvent3 = this;
                this.C = this.J(string3, bl3, clazz3);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MTileEntityMobSpawner.setMobSpawnerControlFlowState(new int[1]);
            }
            this.E = null;
            return;
        }
        if (ForgeVersion.MC_1_17.d()) {
            clazz = Float.TYPE;
            bl = true;
            string = "openness";
            Class clazz4 = MappedClasses.lQ;
            MRenderGameOverlayEvent mRenderGameOverlayEvent3 = this;
            this.E = mRenderGameOverlayEvent3.registerInstanceFieldForOwner(clazz4, string, bl, clazz);
            Class<Integer> clazz5 = Integer.TYPE;
            boolean bl4 = true;
            String string4 = "openCount";
            Class clazz6 = MappedClasses.zn;
            MRenderGameOverlayEvent mRenderGameOverlayEvent4 = this;
            this.m = this.registerInstanceFieldForOwner(clazz6, string4, bl4, clazz5);
            Class[] classArray = new Class[]{Float.TYPE};
            Class<Float> clazz7 = Float.TYPE;
            String string5 = "getOpenNess";
            MRenderGameOverlayEvent mRenderGameOverlayEvent5 = this;
            this.p = ((MappingMethodBuilder)this.methodBuilder(string5, clazz7, classArray).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Fs)).buildMethod();
        } else {
            clazz = Float.TYPE;
            bl = true;
            string = "lidAngle";
            mRenderGameOverlayEvent = this;
            this.E = mRenderGameOverlayEvent.J(string, bl, clazz);
            Class<Integer> clazz8 = Integer.TYPE;
            boolean bl5 = true;
            String string6 = "numPlayersUsing";
            MRenderGameOverlayEvent mRenderGameOverlayEvent6 = this;
            this.m = this.J(string6, bl5, clazz8);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.v()) {
                clazz = MappedClasses.q1;
                bl = true;
                string = "cachedChestType";
                mRenderGameOverlayEvent = this;
                this.C = mRenderGameOverlayEvent.J(string, bl, clazz);
            }
        } else {
            clazz = Integer.TYPE;
            bl = true;
            string = "cachedChestType";
            mRenderGameOverlayEvent = this;
            this.C = mRenderGameOverlayEvent.J(string, bl, clazz);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MTileEntityMobSpawner.setMobSpawnerControlFlowState(new int[1]);
        }
    }

    public int Z(Object object) {
        return this.m.getInt(object);
    }
}

