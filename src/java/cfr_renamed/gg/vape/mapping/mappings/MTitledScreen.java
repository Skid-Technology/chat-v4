/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MTitledScreen
extends Mapping {
    public MappingMethod renderScoreboardMethod;
    public MappingField displayedTitleField;
    public MappingMethod renderHudMethod;
    private MappingField hudField;

    public Object getDisplayedTitle(Object titledScreenHandle) {
        if (ForgeVersion.MC_26_2.d()) {
            Object hudHandle = this.hudField.getObject(titledScreenHandle);
            return hudHandle == null ? null : this.displayedTitleField.getObject(hudHandle);
        }
        return this.displayedTitleField.getObject(titledScreenHandle);
    }

    public MTitledScreen() {
        this(ScaledResolution.q());
    }

    private MTitledScreen(int initializationState) {
        super(MappedClasses.Zj);
        MTitledScreen mTitledScreen;
        MTitledScreen mTitledScreen2;
        String string;
        boolean bl;
        Class<Void> clazz;
        Class[] classArray;
        int nextControlFlowState = initializationState;
        if (nextControlFlowState != 0) {
            MTitledScreen mTitledScreen3;
            String string2;
            boolean bl2;
            Class<Void> clazz2;
            Class[] classArray2;
            if (ForgeVersion.MC_1_8_9.d()) {
                classArray2 = new Class[]{MappedClasses.var_java_lang_Class_Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
                clazz2 = Void.TYPE;
                bl2 = true;
                string2 = "renderScoreboard";
                mTitledScreen3 = this;
                this.renderScoreboardMethod = mTitledScreen3.Y(string2, bl2, clazz2, classArray2);
            }
            classArray2 = new Class[]{MappedClasses.var_java_lang_Class_Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
            clazz2 = Void.TYPE;
            bl2 = Wrapper.isNativeAvailable;
            string2 = "func_96136_a";
            mTitledScreen3 = this;
            this.renderScoreboardMethod = mTitledScreen3.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray22 = new Class[]{MappedClasses.var_java_lang_Class_Y, MappedClasses.Zz};
            Class<Void> clazz22 = Void.TYPE;
            boolean bl22 = true;
            String string22 = "renderScoreboard";
            MTitledScreen mTitledScreen22 = this;
            this.renderScoreboardMethod = this.Y(string22, bl22, clazz22, classArray22);
            Class<String> clazz3 = String.class;
            boolean bl3 = true;
            String string3 = "displayedTitle";
            MTitledScreen mTitledScreen32 = this;
            this.displayedTitleField = this.J(string3, bl3, clazz3);
            if (GuiComponent.getLegacyComponentState() == null) {
                ScaledResolution.r(++nextControlFlowState);
            }
            return;
        }
        if (ForgeVersion.MC_1_8_9.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.d()) {
                    if (ForgeVersion.MC_1_21_0.d()) {
                        if (ForgeVersion.MC_26_2.d()) {
                            this.renderHudMethod = null;
                        } else {
                            classArray = new Class[]{MappedClasses.var_java_lang_Class_m, MappedClasses.uy};
                            clazz = Void.TYPE;
                            bl = true;
                            string = ForgeVersion.MC_26_1.d() ? "extractRenderState" : "render";
                            mTitledScreen2 = this;
                            this.renderHudMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
                        }
                    } else {
                        classArray = new Class[]{MappedClasses.var_java_lang_Class_m, Float.TYPE};
                        clazz = Void.TYPE;
                        bl = true;
                        string = "render";
                        mTitledScreen2 = this;
                        this.renderHudMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
                    }
                } else {
                    classArray = new Class[]{MappedClasses.DQ, Float.TYPE};
                    clazz = Void.TYPE;
                    bl = true;
                    string = "renderIngameGui";
                    mTitledScreen2 = this;
                    this.renderHudMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
                }
            } else {
                classArray = new Class[]{Float.TYPE};
                clazz = Void.TYPE;
                bl = true;
                string = "renderGameOverlay";
                Class clazz4 = MappedClasses.Zj;
                mTitledScreen = this;
                this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz4, string, bl, clazz, classArray);
            }
            if (this.renderHudMethod != null && !Wrapper.isNativeAvailable && !this.renderHudMethod.hasResolutionFailed() && Vape.INSTANCE.isMappingsRemapped()) {
                if (ForgeVersion.MC_1_16_5.d()) {
                    if (MappedClasses.DC != null) {
                        classArray = new Class[]{MappedClasses.DQ, Float.TYPE};
                        clazz = Void.TYPE;
                        bl = false;
                        string = this.renderHudMethod.getResolvedName();
                        Class clazz5 = MappedClasses.DC;
                        mTitledScreen = this;
                        this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz5, string, bl, clazz, classArray);
                    }
                } else {
                    classArray = new Class[]{Float.TYPE};
                    clazz = Void.TYPE;
                    bl = false;
                    string = this.renderHudMethod.getResolvedName();
                    Class clazz6 = MappedClasses.DC;
                    mTitledScreen = this;
                    this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz6, string, bl, clazz, classArray);
                }
            }
        } else {
            classArray = new Class[]{Float.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE};
            clazz = Void.TYPE;
            bl = true;
            string = "renderGameOverlay";
            Class clazz7 = MappedClasses.Zj;
            mTitledScreen = this;
            this.renderHudMethod = mTitledScreen.registerInstanceMethodForOwner(clazz7, string, bl, clazz, classArray);
            if (!Wrapper.isNativeAvailable && !this.renderHudMethod.hasResolutionFailed() && Vape.INSTANCE.isMappingsRemapped()) {
                Class[] classArray3 = new Class[]{Float.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz8 = Void.TYPE;
                boolean bl4 = false;
                String string4 = this.renderHudMethod.getResolvedName();
                Class clazz9 = MappedClasses.DC;
                MTitledScreen mTitledScreen4 = this;
                this.renderHudMethod = this.registerInstanceMethodForOwner(clazz9, string4, bl4, clazz8, classArray3);
            }
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            MTitledScreen mTitledScreen4;
            Class clazz3;
            if (ForgeVersion.MC_1_20_6.d()) {
                if (ForgeVersion.MC_26_2.d()) {
                    this.renderScoreboardMethod = null;
                } else {
                    classArray = new Class[]{MappedClasses.var_java_lang_Class_m, MappedClasses.var_java_lang_Class_Y};
                    clazz = Void.TYPE;
                    bl = true;
                    string = "displayScoreboardSidebar";
                    mTitledScreen2 = this;
                    this.renderScoreboardMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
                }
            } else {
                classArray = new Class[]{MappedClasses.DQ, MappedClasses.var_java_lang_Class_Y};
                clazz = Void.TYPE;
                bl = Wrapper.isNativeAvailable;
                string = "func_238447_a_";
                mTitledScreen2 = this;
                this.renderScoreboardMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
            }
            if (ForgeVersion.MC_26_2.d()) {
                clazz3 = MappedClasses.zK;
                boolean bl3 = true;
                String string3 = "hud";
                mTitledScreen4 = this;
                this.hudField = mTitledScreen4.J(string3, bl3, clazz3);
                Class clazz10 = MappedClasses.Yr;
                boolean bl5 = true;
                String string5 = "title";
                Class clazz11 = MappedClasses.zK;
                MTitledScreen mTitledScreen5 = this;
                this.displayedTitleField = this.registerInstanceFieldForOwner(clazz11, string5, bl5, clazz10);
            } else {
                clazz3 = MappedClasses.Yr;
                boolean bl4 = true;
                String string4 = "displayedTitle";
                mTitledScreen4 = this;
                this.displayedTitleField = mTitledScreen4.J(string4, bl4, clazz3);
            }
        } else if (ForgeVersion.MC_1_7_10.L()) {
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                classArray = new Class[]{MappedClasses.var_java_lang_Class_Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
                clazz = Void.TYPE;
                bl = true;
                string = "renderScoreboard";
                mTitledScreen2 = this;
                this.renderScoreboardMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
            } else {
                classArray = new Class[]{MappedClasses.var_java_lang_Class_Y, Integer.TYPE, Integer.TYPE, MappedClasses.uQ};
                clazz = Void.TYPE;
                bl = Wrapper.isNativeAvailable;
                string = "func_96136_a";
                mTitledScreen2 = this;
                this.renderScoreboardMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
            }
        } else {
            classArray = new Class[]{MappedClasses.var_java_lang_Class_Y, MappedClasses.Zz};
            clazz = Void.TYPE;
            bl = true;
            string = "renderScoreboard";
            mTitledScreen2 = this;
            this.renderScoreboardMethod = mTitledScreen2.Y(string, bl, clazz, classArray);
            Class<String> clazz12 = String.class;
            boolean bl6 = true;
            String string6 = "displayedTitle";
            MTitledScreen mTitledScreen6 = this;
            this.displayedTitleField = this.J(string6, bl6, clazz12);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            ScaledResolution.r(++nextControlFlowState);
        }
    }
}

