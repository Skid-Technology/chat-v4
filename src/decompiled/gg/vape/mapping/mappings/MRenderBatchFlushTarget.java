/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderBatchFlushTarget
extends Mapping {
    private static int glCommandEncoderControlFlowState;
    private static final String DRAW_FROM_BUFFERS_METHOD_NAME;
    public MappingMethod drawFromBuffersMethod;

    public static int getInitialControlFlowState() {
        int n = MRenderBatchFlushTarget.getGlCommandEncoderControlFlowState();
        if (n == 0) {
            return 64;
        }
        return 0;
    }

    public static void setGlCommandEncoderControlFlowState(int state) {
        glCommandEncoderControlFlowState = state;
    }

    public MRenderBatchFlushTarget() {
        this(MRenderBatchFlushTarget.getInitialControlFlowState());
    }

    private MRenderBatchFlushTarget(int n) {
        super(MappedClasses.zg);
        Class[] classArray;
        if (n != 0) {
            Class[] classArray2;
            if (ForgeVersion.MC_26_2.d()) {
                Class[] classArray3 = new Class[8];
                classArray3[0] = MappedClasses.uq;
                classArray3[1] = Integer.TYPE;
                classArray3[2] = Integer.TYPE;
                classArray3[3] = Integer.TYPE;
                classArray3[4] = MappedClasses.Yz;
                classArray3[5] = MappedClasses.FA;
                classArray3[6] = Integer.TYPE;
                classArray2 = classArray3;
                classArray3[7] = Integer.TYPE;
            } else {
                Class[] classArray4 = new Class[7];
                classArray4[0] = MappedClasses.uq;
                classArray4[1] = Integer.TYPE;
                classArray4[2] = Integer.TYPE;
                classArray4[3] = Integer.TYPE;
                classArray4[4] = MappedClasses.Yz;
                classArray4[5] = MappedClasses.FA;
                classArray2 = classArray4;
                classArray4[6] = Integer.TYPE;
            }
            Class[] classArray5 = classArray2;
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = DRAW_FROM_BUFFERS_METHOD_NAME;
            MRenderBatchFlushTarget mRenderBatchFlushTarget = this;
            this.drawFromBuffersMethod = mRenderBatchFlushTarget.Y(string, bl, clazz, classArray5);
            return;
        }
        if (ForgeVersion.MC_26_2.d()) {
            Class[] classArray6 = new Class[8];
            classArray6[0] = MappedClasses.uq;
            classArray6[1] = Integer.TYPE;
            classArray6[2] = Integer.TYPE;
            classArray6[3] = Integer.TYPE;
            classArray6[4] = MappedClasses.Yz;
            classArray6[5] = MappedClasses.FA;
            classArray6[6] = Integer.TYPE;
            classArray = classArray6;
            classArray6[7] = Integer.TYPE;
        } else {
            Class[] classArray7 = new Class[7];
            classArray7[0] = MappedClasses.uq;
            classArray7[1] = Integer.TYPE;
            classArray7[2] = Integer.TYPE;
            classArray7[3] = Integer.TYPE;
            classArray7[4] = MappedClasses.Yz;
            classArray7[5] = MappedClasses.FA;
            classArray = classArray7;
            classArray7[6] = Integer.TYPE;
        }
        Class[] classArray8 = classArray;
        Class<Void> clazz = Void.TYPE;
        boolean bl = true;
        String string = DRAW_FROM_BUFFERS_METHOD_NAME;
        MRenderBatchFlushTarget mRenderBatchFlushTarget = this;
        this.drawFromBuffersMethod = mRenderBatchFlushTarget.Y(string, bl, clazz, classArray8);
        GuiComponent.setLegacyComponentState(new GuiComponent[3]);
    }

    public static int getGlCommandEncoderControlFlowState() {
        return glCommandEncoderControlFlowState;
    }

    static {
        MRenderBatchFlushTarget.setGlCommandEncoderControlFlowState(0);
        DRAW_FROM_BUFFERS_METHOD_NAME = "drawFromBuffers";
    }
}

