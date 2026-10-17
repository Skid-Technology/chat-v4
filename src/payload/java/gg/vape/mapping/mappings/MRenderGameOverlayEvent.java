package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderGameOverlayEvent extends Mapping {
   private MappingMethod p;
   private final MappingField E;
   private MappingField C;
   private final MappingField m;

   public float o(Object object, float f) {
      return this.p.invokeFloat(object, f);
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
      if (nArray != null) {
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "numPlayersUsing";
         this.m = this.J(string, bl, clazz);
         if (ForgeVersion.MC_1_12_2.d()) {
            Class clazz2 = MappedClasses.q1;
            boolean bl2 = true;
            String string2 = "cachedChestType";
            this.C = this.J(string2, bl2, clazz2);
            Class<Integer> clazz3 = int.class;
            boolean bl3 = true;
            String string3 = "cachedChestType";
            this.C = this.J(string3, bl3, clazz3);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            MTileEntityMobSpawner.setMobSpawnerControlFlowState(new int[1]);
         }

         this.E = null;
      } else {
         if (ForgeVersion.MC_1_17.d()) {
            Class<Float> clazzx = float.class;
            boolean blx = true;
            String stringx = "openness";
            Class clazz4 = MappedClasses.lQ;
            this.E = this.registerInstanceFieldForOwner(clazz4, stringx, blx, clazzx);
            Class<Integer> clazz5 = int.class;
            boolean bl4 = true;
            String string4 = "openCount";
            Class clazz6 = MappedClasses.zn;
            this.m = this.registerInstanceFieldForOwner(clazz6, string4, bl4, clazz5);
            Class[] classArray = new Class[]{float.class};
            Class<Float> clazz7 = float.class;
            String string5 = "getOpenNess";
            this.p = this.methodBuilder(string5, clazz7, classArray).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Fs).buildMethod();
         } else {
            Class<Float> clazzx = float.class;
            boolean blx = true;
            String stringx = "lidAngle";
            this.E = this.J(stringx, blx, clazzx);
            Class<Integer> clazz8 = int.class;
            boolean bl5 = true;
            String string6 = "numPlayersUsing";
            this.m = this.J(string6, bl5, clazz8);
         }

         if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.v()) {
               Class clazzx = MappedClasses.q1;
               boolean blx = true;
               String stringx = "cachedChestType";
               this.C = this.J(stringx, blx, clazzx);
            }
         } else {
            Class<Integer> clazzx = int.class;
            boolean blx = true;
            String stringx = "cachedChestType";
            this.C = this.J(stringx, blx, clazzx);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            MTileEntityMobSpawner.setMobSpawnerControlFlowState(new int[1]);
         }
      }
   }

   public int Z(Object object) {
      return this.m.getInt(object);
   }
}
