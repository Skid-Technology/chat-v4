package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MGlyphInfo extends Mapping {
   private MappingField m;
   private MappingField F;
   private MappingField y;
   private MappingField A;
   private MappingField k;
   private MappingField b;
   private MappingField n;
   private MappingField h;
   private MappingField G;
   private MappingField q;
   private MappingMethod Z;

   public float K(Object object) {
      return this.G == null ? 0.0F : this.G.getFloat(object);
   }

   public MGlyphInfo() {
      super(MappedClasses.v);
      Class<Float> clazz = float.class;
      boolean bl = true;
      String string = "u0";
      this.m = this.J(string, bl, clazz);
      Class<Float> clazz2 = float.class;
      boolean bl2 = true;
      String string2 = "u1";
      this.h = this.J(string2, bl2, clazz2);
      Class<Float> clazz3 = float.class;
      boolean bl3 = true;
      String string3 = "v0";
      this.b = this.J(string3, bl3, clazz3);
      Class<Float> clazz4 = float.class;
      boolean bl4 = true;
      String string4 = "v1";
      this.n = this.J(string4, bl4, clazz4);
      Class<Float> clazz5 = float.class;
      boolean bl5 = true;
      String string5 = "left";
      this.F = this.J(string5, bl5, clazz5);
      Class<Float> clazz6 = float.class;
      boolean bl6 = true;
      String string6 = "right";
      this.A = this.J(string6, bl6, clazz6);
      if (MGlyphProvider.getGlyphSourceControlFlowState() != null) {
         Class<Float> clazz7 = float.class;
         boolean bl7 = true;
         String string7 = "up";
         this.y = this.J(string7, bl7, clazz7);
         Class<Float> clazz8 = float.class;
         boolean bl8 = true;
         String string8 = "down";
         this.G = this.J(string8, bl8, clazz8);
         Class clazz9 = MappedClasses.g;
         boolean bl9 = true;
         String string9 = "info";
         this.k = this.J(string9, bl9, clazz9);
         Class clazz10 = MappedClasses.GPU_TEXTURE_VIEW;
         boolean bl10 = true;
         String string10 = "textureView";
         this.q = this.J(string10, bl10, clazz10);
         Class[] classArray = new Class[0];
         Class clazz11 = MappedClasses.g;
         boolean bl11 = true;
         String string11 = "info";
         this.Z = this.Y(string11, bl11, clazz11, classArray);
         GuiComponent.setLegacyComponentState(new GuiComponent[3]);
      } else {
         Class<Float> clazz12 = float.class;
         boolean bl12 = true;
         String string12 = "up";
         this.y = this.J(string12, bl12, clazz12);
         Class<Float> clazz13 = float.class;
         boolean bl13 = true;
         String string13 = "down";
         this.G = this.J(string13, bl13, clazz13);
         Class clazz14 = MappedClasses.g;
         boolean bl14 = true;
         String string14 = "info";
         this.k = this.J(string14, bl14, clazz14);
         Class clazz15 = MappedClasses.GPU_TEXTURE_VIEW;
         boolean bl15 = true;
         String string15 = "textureView";
         this.q = this.J(string15, bl15, clazz15);
         Class[] classArray = new Class[0];
         Class clazz16 = MappedClasses.g;
         boolean bl16 = true;
         String string16 = "info";
         this.Z = this.Y(string16, bl16, clazz16, classArray);
      }
   }

   public float h(Object object) {
      return this.n == null ? 0.0F : this.n.getFloat(object);
   }

   public Object r$src$Ljava_lang_Object_$1r6sqxs(Object object) {
      return this.q == null ? null : this.q.getObject(object);
   }

   public float v(Object object) {
      return this.b == null ? 0.0F : this.b.getFloat(object);
   }

   public float r(Object object) {
      return this.F == null ? 0.0F : this.F.getFloat(object);
   }

   public Object H(Object object) {
      if (this.Z != null && !this.Z.hasResolutionFailed()) {
         return this.Z.invokeObject(object);
      } else {
         return this.k == null ? null : this.k.getObject(object);
      }
   }

   public float j(Object object) {
      return this.A == null ? 0.0F : this.A.getFloat(object);
   }

   public float M(Object object) {
      return this.m == null ? 0.0F : this.m.getFloat(object);
   }

   public float g(Object object) {
      return this.h == null ? 0.0F : this.h.getFloat(object);
   }

   public float N(Object object) {
      return this.y == null ? 0.0F : this.y.getFloat(object);
   }
}
