package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MEffectRenderer extends Mapping {
   private MappingField v;
   private final MappingField F;

   public MEffectRenderer() {
      this(MSoundAwareEntityFX.t());
   }

   private MEffectRenderer(String[] stringArray) {
      super(MappedClasses.qv);
      if (stringArray != null) {
         if (ForgeVersion.MC_1_7_10.Y()) {
            Class<List> clazz = List.class;
            boolean bl = true;
            String string = "particleEmitters";
            this.v = this.J(string, bl, clazz);
            Class<List> clazz2 = List.class;
            String string2 = "fxLayers";
            this.F = this.fieldBuilder(string2, clazz2).setArrayDimensions(2).buildField();
         } else {
            Class<List> clazz = List.class;
            String string = "fxLayers";
            this.F = this.fieldBuilder(string, clazz).setArrayDimensions(1).buildField();
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            MSoundAwareEntityFX.Z(new String[5]);
         }
      } else {
         Class<List> clazz = List.class;
         String string = "fxLayers";
         this.F = this.fieldBuilder(string, clazz).setArrayDimensions(1).buildField();
         if (GuiComponent.getLegacyComponentState() == null) {
            MSoundAwareEntityFX.Z(new String[5]);
         }
      }
   }

   private List[] V(Object object) {
      return (List[])this.F.getObjectArray(object);
   }

   public static List[] z(MEffectRenderer mEffectRenderer, Object object) {
      return mEffectRenderer.V(object);
   }

   private List[][] s(Object object) {
      return object != null && this.F != null && !this.F.hasResolutionFailed() ? (List[][])this.F.getObjectArray(object) : new List[0][];
   }

   public List A(Object object) {
      return (List)this.v.getObject(object);
   }

   public static List[][] N(MEffectRenderer mEffectRenderer, Object object) {
      return mEffectRenderer.s(object);
   }
}
