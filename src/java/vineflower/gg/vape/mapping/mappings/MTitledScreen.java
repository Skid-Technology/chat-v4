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

public class MTitledScreen extends Mapping {
   public MappingMethod renderScoreboardMethod;
   public MappingField displayedTitleField;
   public MappingMethod renderHudMethod;
   private MappingField hudField;

   public Object getDisplayedTitle(Object titledScreenHandle) {
      if (ForgeVersion.MC_26_2.d()) {
         Object hudHandle = this.hudField.getObject(titledScreenHandle);
         return hudHandle == null ? null : this.displayedTitleField.getObject(hudHandle);
      } else {
         return this.displayedTitleField.getObject(titledScreenHandle);
      }
   }

   public MTitledScreen() {
      this(ScaledResolution.q());
   }

   private MTitledScreen(int initializationState) {
      super(MappedClasses.Zj);
      if (initializationState != 0) {
         if (ForgeVersion.MC_1_8_9.d()) {
            Class[] classArray = new Class[]{MappedClasses.Y, int.class, int.class, MappedClasses.uQ};
            Class<Void> clazz = void.class;
            boolean bl = true;
            String string = "renderScoreboard";
            this.renderScoreboardMethod = this.Y(string, bl, clazz, classArray);
         }

         Class[] classArray = new Class[]{MappedClasses.Y, int.class, int.class, MappedClasses.uQ};
         Class<Void> clazz = void.class;
         boolean bl = Wrapper.isNativeAvailable;
         String string = "func_96136_a";
         this.renderScoreboardMethod = this.Y(string, bl, clazz, classArray);
         Class[] classArray2 = new Class[]{MappedClasses.Y, MappedClasses.Zz};
         Class<Void> clazz2 = void.class;
         boolean bl2 = true;
         String string2 = "renderScoreboard";
         this.renderScoreboardMethod = this.Y(string2, bl2, clazz2, classArray2);
         Class<String> clazz3 = String.class;
         boolean bl3 = true;
         String string3 = "displayedTitle";
         this.displayedTitleField = this.J(string3, bl3, clazz3);
         if (GuiComponent.getLegacyComponentState() == null) {
            int var17 = initializationState + 1;
            ScaledResolution.r(var17);
         }
      } else {
         if (ForgeVersion.MC_1_8_9.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
               if (ForgeVersion.MC_1_20_6.d()) {
                  if (ForgeVersion.MC_1_21_0.d()) {
                     if (ForgeVersion.MC_26_2.d()) {
                        this.renderHudMethod = null;
                     } else {
                        Class[] classArray = new Class[]{MappedClasses.m, MappedClasses.uy};
                        Class<Void> clazz = void.class;
                        boolean bl = true;
                        String string = ForgeVersion.MC_26_1.d() ? "extractRenderState" : "render";
                        this.renderHudMethod = this.Y(string, bl, clazz, classArray);
                     }
                  } else {
                     Class[] classArray = new Class[]{MappedClasses.m, float.class};
                     Class<Void> clazz = void.class;
                     boolean bl = true;
                     String string = "render";
                     this.renderHudMethod = this.Y(string, bl, clazz, classArray);
                  }
               } else {
                  Class[] classArray = new Class[]{MappedClasses.DQ, float.class};
                  Class<Void> clazz = void.class;
                  boolean bl = true;
                  String string = "renderIngameGui";
                  this.renderHudMethod = this.Y(string, bl, clazz, classArray);
               }
            } else {
               Class[] classArray = new Class[]{float.class};
               Class<Void> clazz = void.class;
               boolean bl = true;
               String string = "renderGameOverlay";
               Class clazz4 = MappedClasses.Zj;
               this.renderHudMethod = this.registerInstanceMethodForOwner(clazz4, string, bl, clazz, classArray);
            }

            if (this.renderHudMethod != null && !Wrapper.isNativeAvailable && !this.renderHudMethod.hasResolutionFailed() && Vape.INSTANCE.isMappingsRemapped()
               )
             {
               if (ForgeVersion.MC_1_16_5.d()) {
                  if (MappedClasses.DC != null) {
                     Class[] classArray = new Class[]{MappedClasses.DQ, float.class};
                     Class<Void> clazz = void.class;
                     boolean bl = false;
                     String string = this.renderHudMethod.getResolvedName();
                     Class clazz5 = MappedClasses.DC;
                     this.renderHudMethod = this.registerInstanceMethodForOwner(clazz5, string, bl, clazz, classArray);
                  }
               } else {
                  Class[] classArray = new Class[]{float.class};
                  Class<Void> clazz = void.class;
                  boolean bl = false;
                  String string = this.renderHudMethod.getResolvedName();
                  Class clazz6 = MappedClasses.DC;
                  this.renderHudMethod = this.registerInstanceMethodForOwner(clazz6, string, bl, clazz, classArray);
               }
            }
         } else {
            Class[] classArray = new Class[]{float.class, boolean.class, int.class, int.class};
            Class<Void> clazz = void.class;
            boolean bl = true;
            String string = "renderGameOverlay";
            Class clazz7 = MappedClasses.Zj;
            this.renderHudMethod = this.registerInstanceMethodForOwner(clazz7, string, bl, clazz, classArray);
            if (!Wrapper.isNativeAvailable && !this.renderHudMethod.hasResolutionFailed() && Vape.INSTANCE.isMappingsRemapped()) {
               Class[] classArray3 = new Class[]{float.class, boolean.class, int.class, int.class};
               Class<Void> clazz8 = void.class;
               boolean bl4 = false;
               String string4 = this.renderHudMethod.getResolvedName();
               Class clazz9 = MappedClasses.DC;
               this.renderHudMethod = this.registerInstanceMethodForOwner(clazz9, string4, bl4, clazz8, classArray3);
            }
         }

         if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_20_6.d()) {
               if (ForgeVersion.MC_26_2.d()) {
                  this.renderScoreboardMethod = null;
               } else {
                  Class[] classArrayx = new Class[]{MappedClasses.m, MappedClasses.Y};
                  Class<Void> clazzx = void.class;
                  boolean blx = true;
                  String stringx = "displayScoreboardSidebar";
                  this.renderScoreboardMethod = this.Y(stringx, blx, clazzx, classArrayx);
               }
            } else {
               Class[] classArrayx = new Class[]{MappedClasses.DQ, MappedClasses.Y};
               Class<Void> clazzx = void.class;
               boolean blx = Wrapper.isNativeAvailable;
               String stringx = "func_238447_a_";
               this.renderScoreboardMethod = this.Y(stringx, blx, clazzx, classArrayx);
            }

            if (ForgeVersion.MC_26_2.d()) {
               Class clazzx = MappedClasses.zK;
               boolean blx = true;
               String stringx = "hud";
               this.hudField = this.J(stringx, blx, clazzx);
               Class clazz10 = MappedClasses.Yr;
               boolean bl5 = true;
               String string5 = "title";
               Class clazz11 = MappedClasses.zK;
               this.displayedTitleField = this.registerInstanceFieldForOwner(clazz11, string5, bl5, clazz10);
            } else {
               Class clazzx = MappedClasses.Yr;
               boolean blx = true;
               String stringx = "displayedTitle";
               this.displayedTitleField = this.J(stringx, blx, clazzx);
            }
         } else if (ForgeVersion.MC_1_7_10.L()) {
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
               Class[] classArrayx = new Class[]{MappedClasses.Y, int.class, int.class, MappedClasses.uQ};
               Class<Void> clazzx = void.class;
               boolean blx = true;
               String stringx = "renderScoreboard";
               this.renderScoreboardMethod = this.Y(stringx, blx, clazzx, classArrayx);
            } else {
               Class[] classArrayx = new Class[]{MappedClasses.Y, int.class, int.class, MappedClasses.uQ};
               Class<Void> clazzx = void.class;
               boolean blx = Wrapper.isNativeAvailable;
               String stringx = "func_96136_a";
               this.renderScoreboardMethod = this.Y(stringx, blx, clazzx, classArrayx);
            }
         } else {
            Class[] classArrayx = new Class[]{MappedClasses.Y, MappedClasses.Zz};
            Class<Void> clazzx = void.class;
            boolean blx = true;
            String stringx = "renderScoreboard";
            this.renderScoreboardMethod = this.Y(stringx, blx, clazzx, classArrayx);
            Class<String> clazz12 = String.class;
            boolean bl6 = true;
            String string6 = "displayedTitle";
            this.displayedTitleField = this.J(string6, bl6, clazz12);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            int nextControlFlowState = initializationState + 1;
            ScaledResolution.r(nextControlFlowState);
         }
      }
   }
}
