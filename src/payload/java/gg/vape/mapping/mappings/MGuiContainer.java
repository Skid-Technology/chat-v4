package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MGuiContainer extends Mapping {
   private MappingField g;
   private MappingField l;
   private MappingField L;
   private MappingMethod F;
   private MappingField D;
   private static String[] f;
   private MappingField e;
   private MappingField p;
   private MappingField r;
   private MappingField S;
   private MappingMethod I;

   public static void r(String[] stringArray) {
      f = stringArray;
   }

   public Object y(Object object) {
      return this.L.getObject(object);
   }

   private int p(Object object) {
      return this.e.getInt(object);
   }

   public MGuiContainer() {
      this(l());
   }

   private MGuiContainer(String[] stringArray) {
      super(MappedClasses.Ft);
      if (stringArray != null) {
         if (ForgeVersion.MC_1_7_10.L()) {
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
               Class clazz = MappedClasses.zC;
               boolean bl = true;
               String string = "inventoryBackground";
               this.g = this.registerStaticField(string, bl, clazz);
            } else {
               Class clazz = MappedClasses.zC;
               boolean bl = Wrapper.isNativeAvailable;
               String string = "field_147001_a";
               this.g = this.registerStaticField(string, bl, clazz);
            }
         } else {
            Class clazz = MappedClasses.zC;
            boolean bl = true;
            String string = ForgeVersion.MC_1_12_2.d() ? "INVENTORY_BACKGROUND" : "inventoryBackground";
            this.g = this.registerStaticField(string, bl, clazz);
         }

         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "guiLeft";
         this.r = this.J(string, bl, clazz);
         Class<Integer> clazz2 = int.class;
         boolean bl2 = true;
         String string2 = "guiTop";
         this.e = this.J(string2, bl2, clazz2);
         Class<Integer> clazz3 = int.class;
         boolean bl3 = true;
         String string3 = "xSize";
         this.S = this.J(string3, bl3, clazz3);
         Class<Integer> clazz4 = int.class;
         boolean bl4 = true;
         String string4 = "ySize";
         this.D = this.J(string4, bl4, clazz4);
         if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
               Class clazz5 = MappedClasses.YQ;
               boolean bl5 = true;
               String string5 = "hoveredSlot";
               this.p = this.J(string5, bl5, clazz5);
               Class clazz6 = MappedClasses.X;
               boolean bl6 = Wrapper.isNativeAvailable;
               String string6 = "field_147002_h";
               this.l = this.J(string6, bl6, clazz6);
            } else {
               Class clazz7 = MappedClasses.YQ;
               boolean bl7 = Wrapper.isNativeAvailable;
               String string7 = "field_147006_u";
               this.p = this.J(string7, bl7, clazz7);
            }

            Class[] classArray = new Class[]{MappedClasses.YQ, int.class, int.class, MappedClasses.V_};
            Class<Void> clazz8 = void.class;
            boolean bl8 = true;
            String string8 = "handleMouseClick";
            this.I = this.Y(string8, bl8, clazz8, classArray);
         } else {
            Class clazz9 = MappedClasses.YQ;
            boolean bl9 = true;
            String string9 = "theSlot";
            this.p = this.J(string9, bl9, clazz9);
            Class[] classArray = new Class[]{MappedClasses.YQ, int.class, int.class, int.class};
            Class<Void> clazz10 = void.class;
            boolean bl10 = true;
            String string10 = "handleMouseClick";
            this.I = this.Y(string10, bl10, clazz10, classArray);
         }

         if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz11 = MappedClasses.X;
            boolean bl11 = true;
            String string11 = "container";
            this.L = this.J(string11, bl11, clazz11);
            Class[] classArray = new Class[]{double.class, double.class};
            Class clazz12 = MappedClasses.YQ;
            boolean bl12 = true;
            String string12 = "getSelectedSlot";
            this.F = this.Y(string12, bl12, clazz12, classArray);
         } else {
            Class clazz13 = MappedClasses.X;
            boolean bl13 = true;
            String string13 = "inventorySlots";
            this.L = this.J(string13, bl13, clazz13);
            Class[] classArray = new Class[]{int.class, int.class};
            Class clazz14 = MappedClasses.YQ;
            boolean bl14 = true;
            String string14 = "getSlotAtPosition";
            this.F = this.Y(string14, bl14, clazz14, classArray);
         }
      } else {
         if (ForgeVersion.MC_1_7_10.L()) {
            Class clazzx = MappedClasses.zC;
            boolean blx = true;
            String stringx = "inventoryBackground";
            this.registerStaticField(stringx, blx, clazzx);
            GuiComponent.setLegacyComponentState(new GuiComponent[3]);
         }

         Class clazzx = MappedClasses.zC;
         boolean blx = Wrapper.isNativeAvailable;
         String stringx = "field_147001_a";
         this.registerStaticField(stringx, blx, clazzx);
         Class clazz15 = MappedClasses.zC;
         boolean bl15 = true;
         String string15 = ForgeVersion.MC_1_12_2.d() ? "INVENTORY_BACKGROUND" : "inventoryBackground";
         this.g = this.registerStaticField(string15, bl15, clazz15);
         Class<Integer> clazz16 = int.class;
         boolean bl16 = true;
         String string16 = "guiLeft";
         this.r = this.J(string16, bl16, clazz16);
         Class<Integer> clazz17 = int.class;
         boolean bl17 = true;
         String string17 = "guiTop";
         this.e = this.J(string17, bl17, clazz17);
         Class<Integer> clazz18 = int.class;
         boolean bl18 = true;
         String string18 = "xSize";
         this.S = this.J(string18, bl18, clazz18);
         Class<Integer> clazz19 = int.class;
         boolean bl19 = true;
         String string19 = "ySize";
         this.D = this.J(string19, bl19, clazz19);
         Class[] classArray = new Class[]{MappedClasses.YQ, int.class, int.class, int.class};
         Class<Void> clazz20 = void.class;
         boolean bl20 = true;
         String string20 = "handleMouseClick";
         this.I = this.Y(string20, bl20, clazz20, classArray);
         if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz21 = MappedClasses.X;
            boolean bl21 = true;
            String string21 = "container";
            this.J(string21, bl21, clazz21);
            Class[] classArray2 = new Class[]{double.class, double.class};
            Class clazz22 = MappedClasses.YQ;
            boolean bl22 = true;
            String string22 = "getSelectedSlot";
            this.F = this.Y(string22, bl22, clazz22, classArray2);
         }

         Class clazz23 = MappedClasses.X;
         boolean bl23 = true;
         String string23 = "inventorySlots";
         this.L = this.J(string23, bl23, clazz23);
         Class[] classArray3 = new Class[]{int.class, int.class};
         Class clazz24 = MappedClasses.YQ;
         boolean bl24 = true;
         String string24 = "getSlotAtPosition";
         this.F = this.Y(string24, bl24, clazz24, classArray3);
         this.p = null;
      }
   }

   private int q(Object object) {
      return this.r.getInt(object);
   }

   private Object D(Object object) {
      return this.p.getObject(object);
   }

   private int s(Object object) {
      return this.S.getInt(object);
   }

   private int g(Object object) {
      return this.D.getInt(object);
   }

   public static Object a(MGuiContainer mGuiContainer, Object object, int n, int n2) {
      return mGuiContainer.g(object, n, n2);
   }

   private Object g(Object object, int n, int n2) {
      return ForgeVersion.MC_1_16_5.d() ? this.F.invokeObject(object, n, n2) : this.F.invokeObject(object, n, n2);
   }

   private void B(Object object, Object object2, int n, int n2, int n3) {
      this.I.invokeVoid(object, object2, n, n2, n3);
   }

   private void q(Object object, Object object2, int n, int n2, Object object3) {
      this.I.invokeVoid(object, object2, n, n2, object3);
   }

   public Object v() {
      return this.g.getObject(null);
   }

   public Object t(Object object) {
      return this.l.getObject(object);
   }

   public static int i(MGuiContainer mGuiContainer, Object object) {
      return mGuiContainer.s(object);
   }

   public static int G(MGuiContainer mGuiContainer, Object object) {
      return mGuiContainer.p(object);
   }

   public static Object y(MGuiContainer mGuiContainer, Object object) {
      return mGuiContainer.D(object);
   }

   public static String[] l() {
      return f;
   }

   public static int e(MGuiContainer mGuiContainer, Object object) {
      return mGuiContainer.g(object);
   }

   public static int B(MGuiContainer mGuiContainer, Object object) {
      return mGuiContainer.q(object);
   }

   static {
      r(new String[2]);
   }
}
