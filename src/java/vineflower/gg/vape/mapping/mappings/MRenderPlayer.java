package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderPlayer extends Mapping {
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
            if (ForgeVersion.MC_1_21_10.d()) {
               this.renderMethod = null;
            } else if (ForgeVersion.MC_1_21_4.d()) {
               this.renderMethod = this.registerInstanceMethodForOwner(
                  MappedClasses.Fq, "render", true, void.class, MappedClasses.uo, MappedClasses.DQ, MappedClasses.ZK, int.class
               );
            } else {
               this.renderMethod = this.Y("render", true, void.class, MappedClasses.zt, float.class, float.class, MappedClasses.DQ, MappedClasses.ZK, int.class);
            }

            this.entityModelField = this.registerInstanceFieldForOwner(MappedClasses.Fq, "entityModel", true, MappedClasses.V6);
         } else {
            this.renderMethod = this.Y("doRender", true, void.class, MappedClasses.zt, double.class, double.class, double.class, float.class, float.class);
            this.getMainModelMethod = this.Y("getMainModel", true, MappedClasses.ud);
         }
      } else {
         this.renderMethod = this.Y("doRender", true, void.class, MappedClasses.zt, double.class, double.class, double.class, float.class, float.class);
         this.modelBipedMainField = this.J("modelBipedMain", true, MappedClasses.zV);
         if (Wrapper.isNativeAvailable) {
            this.renderEquippedItemsMethod = this.Y("renderEquippedItems", true, void.class, MappedClasses.zm, float.class);
         } else {
            this.renderEquippedItemsMethod = this.Y("renderEquippedItems", true, void.class, MappedClasses.zt, float.class);
         }

         this.shouldRenderPassMethod = this.Y("shouldRenderPass", true, int.class, MappedClasses.zt, int.class, float.class);
      }
   }

   public Object getMainModel(Object renderPlayerHandle) {
      if (ForgeVersion.MC_1_16_5.d()) {
         return this.entityModelField.getObject(renderPlayerHandle);
      } else {
         return ForgeVersion.MC_1_7_10.Y() ? this.getMainModelMethod.invokeObject(renderPlayerHandle) : this.modelBipedMainField.getObject(renderPlayerHandle);
      }
   }
}
