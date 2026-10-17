package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MTickEventPhase extends Mapping {
   private MappingField h;
   private static GuiComponent[] N;
   private MappingField y;

   public static void m(GuiComponent[] guiComponentArray) {
      N = guiComponentArray;
   }

   public static Object B(MTickEventPhase mTickEventPhase) {
      return mTickEventPhase.h();
   }

   private Object p() {
      return this.y.getObject(null);
   }

   public static Object G(MTickEventPhase mTickEventPhase) {
      return mTickEventPhase.p();
   }

   public static GuiComponent[] P() {
      return N;
   }

   public MTickEventPhase() {
      this(P());
   }

   private MTickEventPhase(GuiComponent[] guiComponentArray) {
      super(MappedClasses.VT);
      Class clazz = MappedClasses.VT;
      boolean bl = true;
      String string = "DISTANCE_TO_ORIGIN";
      this.y = this.registerStaticField(string, bl, clazz);
      Class clazz2 = MappedClasses.VT;
      boolean bl2 = true;
      String string2 = "ORTHOGRAPHIC_Z";
      this.h = this.registerStaticField(string2, bl2, clazz2);
      if (GuiComponent.getLegacyComponentState() == null) {
         m(new GuiComponent[2]);
      }
   }

   private Object h() {
      return this.h.getObject(null);
   }

   static {
      m(null);
   }
}
