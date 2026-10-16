package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Collection;

public class MNetHandlerPlayClientImpl extends Mapping {
   private MappingField p;
   private final MappingMethod F;
   private MappingMethod r;
   private final MappingField u;
   private MappingMethod h;

   public static boolean X(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
      return mNetHandlerPlayClientImpl.i(object);
   }

   private Object h(Object object) {
      return this.u.getObject(object);
   }

   private void Q(Object object, Object object2) {
      this.F.invokeVoid(object, object2);
   }

   public MNetHandlerPlayClientImpl() {
      this(MNetworkManager.Q());
   }

   private MNetHandlerPlayClientImpl(String[] stringArray) {
      super(MappedClasses.F1);
      if (ForgeVersion.MC_1_20_6.v()) {
         Class<Boolean> clazz = boolean.class;
         boolean bl = true;
         String string = "doneLoadingTerrain";
         this.p = this.J(string, bl, clazz);
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         Class clazz = MappedClasses.FO;
         boolean bl = true;
         String string = "connection";
         Class clazz2 = MappedClasses.Yt;
         this.u = this.registerInstanceFieldForOwner(clazz2, string, bl, clazz);
      } else {
         Class clazz = MappedClasses.FO;
         boolean bl = true;
         String string = "netManager";
         this.u = this.J(string, bl, clazz);
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] classArray = new Class[]{MappedClasses.Fm};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "send";
         Class clazz3 = MappedClasses.Yt;
         this.F = this.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
      } else if (ForgeVersion.MC_1_12_2.d()) {
         Class[] classArray = new Class[]{MappedClasses.Fm};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "sendPacket";
         this.F = this.Y(string, bl, clazz, classArray);
      } else {
         Class[] classArray = new Class[]{MappedClasses.Fm};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "addToSendQueue";
         this.F = this.Y(string, bl, clazz, classArray);
      }

      if (ForgeVersion.MC_1_7_10.Y()) {
         Class[] classArray = new Class[0];
         Class<Collection> clazz = Collection.class;
         boolean bl = true;
         String string = "getPlayerInfoMap";
         this.h = this.Y(string, bl, clazz, classArray);
      }

      if (ForgeVersion.MC_1_21_11.d()) {
         Class[] classArray = new Class[0];
         Class<Boolean> clazz = boolean.class;
         String string = "hasClientLoaded";
         this.r = this.methodBuilder(string, clazz, classArray).buildMethod();
      }

      if (GuiComponent.getLegacyComponentState() == null) {
         MNetworkManager.Z(new String[2]);
      }
   }

   public static Collection H(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
      return mNetHandlerPlayClientImpl.N(object);
   }

   public static boolean d(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
      return mNetHandlerPlayClientImpl.K(object);
   }

   public static void I(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object, Object object2) {
      mNetHandlerPlayClientImpl.Q(object, object2);
   }

   private Collection N(Object object) {
      return (Collection)this.h.invokeObject(object);
   }

   private boolean i(Object object) {
      return this.p.getBoolean(object);
   }

   private boolean K(Object object) {
      return this.r.invokeBoolean(object);
   }

   public static Object z(MNetHandlerPlayClientImpl mNetHandlerPlayClientImpl, Object object) {
      return mNetHandlerPlayClientImpl.h(object);
   }
}
