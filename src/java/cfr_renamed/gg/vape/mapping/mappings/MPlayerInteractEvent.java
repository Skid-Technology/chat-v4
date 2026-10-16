/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MSPacketEntityVelocity;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MPlayerInteractEvent
extends Mapping {
    private final MappingField var_gg_vape_mapping_MappingField_O;
    private final MappingField var_gg_vape_mapping_MappingField_f;
    private final MappingField var_gg_vape_mapping_MappingField_Z;
    private final MappingField var_gg_vape_mapping_MappingField_M;
    private final MappingField var_gg_vape_mapping_MappingField_y;
    private final MappingField var_gg_vape_mapping_MappingField_D;

    public Object X() {
        return this.var_gg_vape_mapping_MappingField_Z.getObject(null);
    }

    public Object p() {
        return this.var_gg_vape_mapping_MappingField_M.getObject(null);
    }

    public int r(Object object) {
        return this.var_gg_vape_mapping_MappingField_D.getInt(object);
    }

    public Object y() {
        return this.var_gg_vape_mapping_MappingField_y.getObject(null);
    }

    public Object l() {
        return this.var_gg_vape_mapping_MappingField_O.getObject(null);
    }

    public Object H() {
        return this.var_gg_vape_mapping_MappingField_f.getObject(null);
    }

    public MPlayerInteractEvent() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MPlayerInteractEvent(int[] nArray) {
        super(MappedClasses.VL);
        if (nArray != null) {
            MPlayerInteractEvent mPlayerInteractEvent;
            String string;
            boolean bl;
            Class clazz;
            if (ForgeVersion.MC_1_12_2.d()) {
                clazz = Integer.TYPE;
                bl = true;
                string = "bit";
                mPlayerInteractEvent = this;
                this.var_gg_vape_mapping_MappingField_D = mPlayerInteractEvent.J(string, bl, clazz);
            } else {
                clazz = Integer.TYPE;
                bl = true;
                string = "field_180058_f";
                mPlayerInteractEvent = this;
                this.var_gg_vape_mapping_MappingField_D = mPlayerInteractEvent.J(string, bl, clazz);
            }
            clazz = MappedClasses.VL;
            bl = true;
            string = "X";
            mPlayerInteractEvent = this;
            this.var_gg_vape_mapping_MappingField_Z = mPlayerInteractEvent.registerStaticField(string, bl, clazz);
            Class clazz2 = MappedClasses.VL;
            boolean bl2 = true;
            String string2 = "Y";
            MPlayerInteractEvent mPlayerInteractEvent2 = this;
            this.var_gg_vape_mapping_MappingField_f = this.registerStaticField(string2, bl2, clazz2);
            Class clazz3 = MappedClasses.VL;
            boolean bl3 = true;
            String string3 = "Z";
            MPlayerInteractEvent mPlayerInteractEvent3 = this;
            this.var_gg_vape_mapping_MappingField_O = this.registerStaticField(string3, bl3, clazz3);
            Class clazz4 = MappedClasses.VL;
            boolean bl4 = true;
            String string4 = "Y_ROT";
            MPlayerInteractEvent mPlayerInteractEvent4 = this;
            this.var_gg_vape_mapping_MappingField_M = this.registerStaticField(string4, bl4, clazz4);
            Class clazz5 = MappedClasses.VL;
            boolean bl5 = true;
            String string5 = "X_ROT";
            MPlayerInteractEvent mPlayerInteractEvent5 = this;
            this.var_gg_vape_mapping_MappingField_y = this.registerStaticField(string5, bl5, clazz5);
            if (GuiComponent.getLegacyComponentState() == null) {
                MSPacketEntityVelocity.setPacketMappingControlFlowState(new int[1]);
            }
            return;
        }
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "field_180058_f";
        MPlayerInteractEvent mPlayerInteractEvent = this;
        this.var_gg_vape_mapping_MappingField_D = mPlayerInteractEvent.J(string, bl, clazz);
        Class clazz6 = MappedClasses.VL;
        boolean bl6 = true;
        String string6 = "X";
        MPlayerInteractEvent mPlayerInteractEvent6 = this;
        this.var_gg_vape_mapping_MappingField_Z = this.registerStaticField(string6, bl6, clazz6);
        Class clazz7 = MappedClasses.VL;
        boolean bl7 = true;
        String string7 = "Y";
        MPlayerInteractEvent mPlayerInteractEvent7 = this;
        this.var_gg_vape_mapping_MappingField_f = this.registerStaticField(string7, bl7, clazz7);
        Class clazz8 = MappedClasses.VL;
        boolean bl8 = true;
        String string8 = "Z";
        MPlayerInteractEvent mPlayerInteractEvent8 = this;
        this.var_gg_vape_mapping_MappingField_O = this.registerStaticField(string8, bl8, clazz8);
        Class clazz9 = MappedClasses.VL;
        boolean bl9 = true;
        String string9 = "Y_ROT";
        MPlayerInteractEvent mPlayerInteractEvent9 = this;
        this.var_gg_vape_mapping_MappingField_M = this.registerStaticField(string9, bl9, clazz9);
        Class clazz10 = MappedClasses.VL;
        boolean bl10 = true;
        String string10 = "X_ROT";
        MPlayerInteractEvent mPlayerInteractEvent10 = this;
        this.var_gg_vape_mapping_MappingField_y = this.registerStaticField(string10, bl10, clazz10);
        if (GuiComponent.getLegacyComponentState() == null) {
            MSPacketEntityVelocity.setPacketMappingControlFlowState(new int[1]);
        }
    }
}

