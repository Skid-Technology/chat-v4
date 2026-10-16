package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ITextComponent;
import java.util.List;
import java.util.Map;

public class MItem extends Mapping {
   private MappingMethod W;
   public final MappingMethod f;
   private MappingMethod G;
   private final MappingMethod d;
   private MappingField b;
   public final MappingMethod F;
   private MappingMethod e;
   private MappingMethod N;
   private MappingMethod Z;
   private MappingField v;
   private MappingMethod S;
   private MappingMethod c;
   private final MappingMethod h;
   private MappingField M;
   private MappingMethod Q;
   private MappingMethod w;
   public final MappingMethod p;
   private final MappingField r;

   public static Object r(MItem mItem, String string) {
      return mItem.O(string);
   }

   private Object D(Object object, Object object2) {
      return this.h.invokeObject(object, object2);
   }

   private Object W(Object object) {
      return this.c.invokeObject(object);
   }

   private int M(Object object) {
      return this.b.getInt(object);
   }

   public static Object Z(MItem mItem) {
      return mItem.D();
   }

   public static Object i(MItem mItem, Object object) {
      return mItem.W(object);
   }

   public static String X(MItem mItem, Object object) {
      return mItem.w(object);
   }

   public static Object h(MItem mItem, Object object, Object object2) {
      return mItem.D(object, object2);
   }

   public static Object K(MItem mItem, Object object) {
      return mItem.B(object);
   }

   private boolean X(Object object) {
      return this.M.getBoolean(object);
   }

   private String T(Object object, Object object2) {
      Object object3 = this.d.invokeObject(object, object2);
      if (ForgeVersion.MC_1_16_5.d()) {
         ITextComponent iTextComponent = new ITextComponent(object3);
         return iTextComponent.getFormattedText();
      } else {
         return object3.toString();
      }
   }

   public static String k(MItem mItem, Object object) {
      return mItem.W$src$Ljava_lang_String_$1nf3kj0(object);
   }

   public static Object F(MItem mItem, Object object) {
      return mItem.q(object);
   }

   private int R(Object object) {
      return this.F.invokeInt(null, object);
   }

   public static int Q(MItem mItem, Object object, Object object2) {
      return mItem.A(object, object2);
   }

   private Map d() {
      return (Map)this.v.getObject(null);
   }

   public static int l(MItem mItem, Object object) {
      return mItem.R(object);
   }

   public static Object z(MItem mItem, Object object) {
      return mItem.t(object);
   }

   public static boolean N(MItem mItem, Object object) {
      return mItem.X(object);
   }

   private Object q(Object object) {
      return this.Z.invokeObject(object);
   }

   private String w(Object object) {
      return (String)this.N.invokeObject(object);
   }

   private int A(Object object, Object object2) {
      return this.e.invokeInt(object, object2);
   }

   public static String o(MItem mItem, Object object, Object object2) {
      return mItem.T(object, object2);
   }

   public int c(Object object, Object object2, Object object3) {
      return this.e.invokeInt(object, object2, object3);
   }

   public static Object W(MItem mItem, int n) {
      return mItem.J(n);
   }

   private Object J(int n) {
      return this.f.invokeObject(null, n);
   }

   private Object D() {
      return this.r.getObject(null);
   }

   public static int d(MItem mItem, Object object) {
      return mItem.M(object);
   }

   private Object t(Object object) {
      return this.w.invokeObject(object);
   }

   public MItem() {
      this(MItemStack.M());
   }

   private MItem(int n) {
      super(MappedClasses.lb);
      if (n != 0) {
         if (ForgeVersion.MC_1_21_0.d()) {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "maxDamage";
            this.b = this.J(string, bl, clazz);
         }

         Class[] classArray = new Class[0];
         Class clazz = MappedClasses.VK;
         boolean bl = true;
         String string = "getDefaultInstance";
         this.w = this.Y(string, bl, clazz, classArray);
         Class[] classArray2 = new Class[]{MappedClasses.lb};
         Class<Integer> clazz2 = int.class;
         boolean bl2 = true;
         String string2 = "getIdFromItem";
         this.F = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
         Class[] classArray3 = new Class[]{int.class};
         Class clazz3 = MappedClasses.lb;
         boolean bl3 = true;
         String string3 = "getItemById";
         this.f = this.registerStaticMethod(string3, bl3, clazz3, classArray3);
         if (ForgeVersion.MC_1_7_10.L()) {
            Class clazz4 = MappedClasses.lz;
            boolean bl4 = true;
            String string4 = "ITEM";
            Class clazz5 = MappedClasses.R;
            this.registerStaticFieldForOwner(clazz5, string4, bl4, clazz4);
         }

         Class clazz6 = MappedClasses.lz;
         boolean bl5 = true;
         String string5 = "ITEM";
         Class clazz7 = MappedClasses.Fk;
         this.r = this.registerStaticFieldForOwner(clazz7, string5, bl5, clazz6);
         Class[] classArray4 = new Class[]{MappedClasses.zX, MappedClasses.Vd};
         Class<Void> clazz8 = void.class;
         boolean bl6 = true;
         String string6 = "fillItemGroup";
         this.Q = this.Y(string6, bl6, clazz8, classArray4);
         if (ForgeVersion.MC_1_21_0.v()) {
            Class[] classArray5 = new Class[]{MappedClasses.VK};
            Class clazz9 = MappedClasses.Yr;
            boolean bl7 = true;
            String string7 = "getDisplayName";
            this.Y(string7, bl7, clazz9, classArray5);
            Class[] classArray6 = new Class[]{MappedClasses.VK};
            Class clazz10 = MappedClasses.Yr;
            boolean bl8 = true;
            String string8 = "getDisplayName";
            this.Y(string8, bl8, clazz10, classArray6);
         }

         Class[] classArray7 = new Class[]{MappedClasses.VK};
         Class<String> clazz11 = String.class;
         boolean bl9 = true;
         String string9 = "getItemStackDisplayName";
         this.d = this.Y(string9, bl9, clazz11, classArray7);
         Class[] classArray8 = new Class[]{MappedClasses.VK};
         Class<String> clazz12 = String.class;
         boolean bl10 = true;
         String string10 = "getUnlocalizedNameInefficiently";
         this.h = this.Y(string10, bl10, clazz12, classArray8);
         if (ForgeVersion.MC_1_8_9.d()) {
            Class[] classArray9 = new Class[]{MappedClasses.YU, MappedClasses.Yl, boolean.class};
            Class clazz13 = MappedClasses.DT;
            boolean bl11 = true;
            String string11 = "rayTrace";
            this.Y(string11, bl11, clazz13, classArray9);
         }

         Class[] classArray10 = new Class[]{MappedClasses.YU, MappedClasses.Yl, boolean.class};
         Class clazz14 = MappedClasses.DT;
         boolean bl12 = true;
         String string12 = "getMovingObjectPositionFromPlayer";
         this.p = this.Y(string12, bl12, clazz14, classArray10);
         Class[] classArray11 = new Class[]{MappedClasses.VK};
         Class<Integer> clazz15 = int.class;
         String string13 = "getMaxItemUseDuration";
         this.e = this.methodBuilder(string13, clazz15, classArray11)
            .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getUseDuration")
            .setParameterTypesForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.VK, MappedClasses.zm)
            .buildMethod();
         if (GuiComponent.getLegacyComponentState() == null) {
            int var47 = n + 1;
            MItemStack.E(var47);
         }
      } else {
         if (ForgeVersion.MC_1_21_0.d()) {
            Class[] classArrayx = new Class[0];
            Class<String> clazzx = String.class;
            boolean blx = true;
            String stringx = "getDescriptionId";
            this.N = this.Y(stringx, blx, clazzx, classArrayx);
         }

         if (ForgeVersion.MC_1_7_10.L() && Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            Class<Integer> clazzx = int.class;
            boolean blx = true;
            String stringx = "maxDurability";
            this.b = this.J(stringx, blx, clazzx);
         } else if (ForgeVersion.MC_1_20_6.v()) {
            Class<Integer> clazzx = int.class;
            boolean blx = true;
            String stringx = "maxDamage";
            this.b = this.J(stringx, blx, clazzx);
         } else {
            Class[] classArrayx = new Class[0];
            Class clazzx = MappedClasses.VK;
            boolean blx = true;
            String stringx = "getDefaultInstance";
            this.w = this.Y(stringx, blx, clazzx, classArrayx);
         }

         Class[] classArrayx = new Class[]{MappedClasses.lb};
         Class<Integer> clazzx = int.class;
         boolean blx = true;
         String stringx = "getIdFromItem";
         this.F = this.registerStaticMethod(stringx, blx, clazzx, classArrayx);
         Class[] classArray12 = new Class[]{int.class};
         Class clazz16 = MappedClasses.lb;
         boolean bl13 = true;
         String string14 = "getItemById";
         this.f = this.registerStaticMethod(string14, bl13, clazz16, classArray12);
         if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray13 = new Class[0];
            Class<String> clazz17 = String.class;
            boolean bl14 = true;
            String string15 = "getIconString";
            this.S = this.Y(string15, bl14, clazz17, classArray13);
         } else if (ForgeVersion.MC_1_16_5.v()) {
            Class[] classArray14 = new Class[]{String.class};
            Class clazz18 = MappedClasses.lb;
            boolean bl15 = true;
            String string16 = "getByNameOrId";
            this.W = this.registerStaticMethod(string16, bl15, clazz18, classArray14);
         }

         if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
               if (ForgeVersion.MC_1_20_6.d()) {
                  Class clazz19 = MappedClasses.lz;
                  boolean bl16 = true;
                  String string17 = "ITEM";
                  Class clazz20 = MappedClasses.R;
                  this.r = this.registerStaticFieldForOwner(clazz20, string17, bl16, clazz19);
               } else {
                  Class clazz21 = MappedClasses.lz;
                  boolean bl17 = true;
                  String string18 = "ITEM";
                  Class clazz22 = MappedClasses.Fk;
                  this.r = this.registerStaticFieldForOwner(clazz22, string18, bl17, clazz21);
                  Class[] classArray15 = new Class[]{MappedClasses.zX, MappedClasses.Vd};
                  Class<Void> clazz23 = void.class;
                  boolean bl18 = true;
                  String string19 = "fillItemGroup";
                  this.Q = this.Y(string19, bl18, clazz23, classArray15);
               }

               if (ForgeVersion.MC_1_21_0.v()) {
                  Class[] classArray16 = new Class[0];
                  Class clazz24 = MappedClasses.Yr;
                  boolean bl19 = true;
                  String string20 = "getName";
                  this.c = this.Y(string20, bl19, clazz24, classArray16);
               }
            } else {
               Class clazz25 = MappedClasses.zz;
               boolean bl20 = true;
               String string21 = "REGISTRY";
               this.r = this.registerStaticField(string21, bl20, clazz25);
               Class[] classArray17 = new Class[]{MappedClasses.zX, MappedClasses.Vd};
               Class<Void> clazz26 = void.class;
               boolean bl21 = true;
               String string22 = "getSubItems";
               this.Q = this.Y(string22, bl21, clazz26, classArray17);
            }
         } else {
            Class clazz27 = MappedClasses.zz;
            boolean bl22 = true;
            String string23 = "itemRegistry";
            this.r = this.registerStaticField(string23, bl22, clazz27);
            Class[] classArray18 = new Class[]{MappedClasses.lb, MappedClasses.zX, List.class};
            Class<Void> clazz28 = void.class;
            boolean bl23 = true;
            String string24 = "getSubItems";
            this.Q = this.Y(string24, bl23, clazz28, classArray18);
         }

         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray19 = new Class[]{MappedClasses.VK};
            Class clazz29 = MappedClasses.Yr;
            boolean bl24 = true;
            String string25 = "getDisplayName";
            this.d = this.Y(string25, bl24, clazz29, classArray19);
            Class[] classArray20 = new Class[]{MappedClasses.VK};
            Class clazz30 = MappedClasses.Yr;
            boolean bl25 = true;
            String string26 = "getDisplayName";
            this.h = this.Y(string26, bl25, clazz30, classArray20);
         } else {
            Class[] classArray21 = new Class[]{MappedClasses.VK};
            Class<String> clazz31 = String.class;
            boolean bl26 = true;
            String string27 = "getItemStackDisplayName";
            this.d = this.Y(string27, bl26, clazz31, classArray21);
            Class[] classArray22 = new Class[]{MappedClasses.VK};
            Class<String> clazz32 = String.class;
            boolean bl27 = true;
            String string28 = "getUnlocalizedNameInefficiently";
            this.h = this.Y(string28, bl27, clazz32, classArray22);
         }

         if (ForgeVersion.MC_1_8_9.d()) {
            Class<Map> clazz33 = Map.class;
            boolean bl28 = true;
            String string29 = "BLOCK_TO_ITEM";
            this.v = this.registerStaticField(string29, bl28, clazz33);
         }

         if (ForgeVersion.MC_1_16_5.v()) {
            Class<Boolean> clazz34 = boolean.class;
            boolean bl29 = true;
            String string30 = "hasSubtypes";
            this.M = this.J(string30, bl29, clazz34);
         }

         if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray23 = new Class[0];
            Class clazz35 = MappedClasses.zD;
            boolean bl30 = true;
            String string31 = "components";
            this.G = this.Y(string31, bl30, clazz35, classArray23);
         }

         if (ForgeVersion.MC_26_1.d()) {
            Class[] classArray24 = new Class[0];
            Class clazz36 = MappedClasses.Vo;
            boolean bl31 = true;
            String string32 = "builtInRegistryHolder";
            this.Z = this.Y(string32, bl31, clazz36, classArray24);
         }

         if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray25 = new Class[]{MappedClasses.YU, MappedClasses.Yl, MappedClasses.Y9};
            Class clazz37 = MappedClasses.qF;
            boolean bl32 = true;
            String string33 = "getPlayerPOVHitResult";
            this.p = this.registerStaticMethod(string33, bl32, clazz37, classArray25);
         } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray26 = new Class[]{MappedClasses.YU, MappedClasses.Yl, MappedClasses.Dm};
            Class clazz38 = MappedClasses.qF;
            boolean bl33 = true;
            String string34 = "getPlayerPOVHitResult";
            this.p = this.registerStaticMethod(string34, bl33, clazz38, classArray26);
         } else if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray27 = new Class[]{MappedClasses.YU, MappedClasses.Yl, boolean.class};
            Class clazz39 = MappedClasses.DT;
            boolean bl34 = true;
            String string35 = "rayTrace";
            this.p = this.Y(string35, bl34, clazz39, classArray27);
         } else {
            Class[] classArray28 = new Class[]{MappedClasses.YU, MappedClasses.Yl, boolean.class};
            Class clazz40 = MappedClasses.DT;
            boolean bl35 = true;
            String string36 = "getMovingObjectPositionFromPlayer";
            this.p = this.Y(string36, bl35, clazz40, classArray28);
         }

         Class[] classArray29 = new Class[]{MappedClasses.VK};
         Class<Integer> clazz41 = int.class;
         String string37 = "getMaxItemUseDuration";
         this.e = this.methodBuilder(string37, clazz41, classArray29)
            .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getUseDuration")
            .setParameterTypesForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.VK, MappedClasses.zm)
            .buildMethod();
         if (GuiComponent.getLegacyComponentState() == null) {
            int n2 = n + 1;
            MItemStack.E(n2);
         }
      }
   }

   public static Map U(MItem mItem) {
      return mItem.d();
   }

   private String W$src$Ljava_lang_String_$1nf3kj0(Object object) {
      return this.S.invokeObject(object).toString();
   }

   private Object O(String string) {
      return this.W.invokeObject(null, string);
   }

   public static void V(MItem mItem, Object object, Object object2, Object object3, List list) {
      mItem.h(object, object2, object3, list);
   }

   private void h(Object object, Object object2, Object object3, List list) {
      if (ForgeVersion.MC_1_12_2.d()) {
         this.Q.invokeVoid(object, object3, list);
      } else {
         this.Q.invokeVoid(object, object2, object3, list);
      }
   }

   private Object B(Object object) {
      return this.G.invokeObject(object);
   }
}
