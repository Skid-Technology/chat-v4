package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.List;

public class MModelBase extends Mapping {
   private MappingField M;
   private final MappingMethod u;
   private final MappingMethod E;
   private final MappingMethod a;

   private List Z(Object object) {
      return (List)this.M.getObject(object);
   }

   private void b(Object object, Object object2, float f, float f2, float f3) {
      this.u.invokeVoid(object, object2, f, f2, f3);
   }

   public MModelBase() {
      this(MModelRenderer.T());
   }

   private MModelBase(int[] nArray) {
      super(MappedClasses.M);
      if (nArray != null) {
         Class<List> clazz = List.class;
         boolean bl = true;
         String string = "boxList";
         this.M = this.J(string, bl, clazz);
         Class[] classArray = new Class[]{MappedClasses.zm, float.class, float.class, float.class};
         Class<Void> clazz2 = void.class;
         boolean bl2 = true;
         String string2 = "setLivingAnimations";
         this.u = this.Y(string2, bl2, clazz2, classArray);
         Class[] classArray2 = new Class[]{float.class, float.class, float.class, float.class, float.class, float.class, MappedClasses.zc};
         Class<Void> clazz3 = void.class;
         boolean bl3 = true;
         String string3 = "setRotationAngles";
         this.a = this.Y(string3, bl3, clazz3, classArray2);
         Class[] classArray3 = new Class[]{MappedClasses.zc, float.class, float.class, float.class, float.class, float.class, float.class};
         Class<Void> clazz4 = void.class;
         boolean bl4 = true;
         String string4 = "render";
         this.E = this.Y(string4, bl4, clazz4, classArray3);
      } else {
         Class<List> clazz = List.class;
         boolean bl = true;
         String string = "boxList";
         this.M = this.J(string, bl, clazz);
         Class[] classArray = new Class[]{MappedClasses.zm, float.class, float.class, float.class};
         Class<Void> clazz5 = void.class;
         boolean bl5 = true;
         String string5 = "setLivingAnimations";
         this.u = this.Y(string5, bl5, clazz5, classArray);
         Class[] classArray4 = new Class[]{float.class, float.class, float.class, float.class, float.class, float.class, MappedClasses.zc};
         Class<Void> clazz6 = void.class;
         boolean bl6 = true;
         String string6 = "setRotationAngles";
         this.a = this.Y(string6, bl6, clazz6, classArray4);
         Class[] classArray5 = new Class[]{MappedClasses.zc, float.class, float.class, float.class, float.class, float.class, float.class};
         Class<Void> clazz7 = void.class;
         boolean bl7 = true;
         String string7 = "render";
         this.E = this.Y(string7, bl7, clazz7, classArray5);
         GuiComponent.setLegacyComponentState(new GuiComponent[4]);
      }
   }

   private void e(Object object, float f, float f2, float f3, float f4, float f5, float f6, Object object2) {
      this.a.invokeVoid(object, f, f2, f3, f4, f5, f6, object2);
   }

   private void i(Object object, Object object2, float f, float f2, float f3, float f4, float f5, float f6) {
      this.E.invokeVoid(object, object2, f, f2, f3, f4, f5, f6);
   }
}
