package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ForgeVersion;

public class MWorldClient extends Mapping {
   private final MappingMethod u;
   private MappingField D;
   private MappingField x;
   private final MappingMethod s;

   public MWorldClient() {
      this(MPlayerControllerMP.d());
   }

   private MWorldClient(int n) {
      super(MappedClasses.Z);
      if (n != 0) {
         if (ForgeVersion.MC_1_8_9.L()) {
            Class[] classArray = new Class[]{int.class, MappedClasses.zc};
            Class<Void> clazz = void.class;
            boolean bl = true;
            String string = "addEntityImpl";
            this.Y(string, bl, clazz, classArray);
            Class clazz2 = MappedClasses.CLIENT_WORLD_INFO;
            boolean bl2 = true;
            String string2 = "clientLevelData";
            Class clazz3 = MappedClasses.Z;
            this.D = this.registerInstanceFieldForOwner(clazz3, string2, bl2, clazz2);
         }

         Class[] classArray = new Class[]{int.class, MappedClasses.zc};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = "addEntityToWorld";
         this.s = this.Y(string, bl, clazz, classArray);
         this.u = null;
         if (GuiComponent.getLegacyComponentState() == null) {
            int var17 = n + 1;
            MPlayerControllerMP.p(var17);
         }
      } else {
         if (ForgeVersion.MC_1_8_9.L()) {
            Class clazz = MappedClasses.le;
            boolean bl = true;
            String string = "clientChunkProvider";
            Class clazz4 = MappedClasses.Z;
            this.x = this.registerInstanceFieldForOwner(clazz4, string, bl, clazz);
         }

         Class[] classArray = new Class[]{int.class};
         Class clazz = MappedClasses.zc;
         String string = "getEntityByID";
         this.u = this.methodBuilder(string, clazz, classArray)
            .setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getEntity")
            .setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.YU)
            .buildMethod();
         if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray2 = new Class[]{int.class, MappedClasses.zc};
            Class<Void> clazz5 = void.class;
            boolean bl = true;
            String string3 = "addEntityImpl";
            this.s = this.Y(string3, bl, clazz5, classArray2);
            Class clazz6 = MappedClasses.CLIENT_WORLD_INFO;
            boolean bl3 = true;
            String string4 = "clientLevelData";
            Class clazz7 = MappedClasses.Z;
            this.D = this.registerInstanceFieldForOwner(clazz7, string4, bl3, clazz6);
         } else {
            Class[] classArray3 = new Class[]{int.class, MappedClasses.zc};
            Class<Void> clazz8 = void.class;
            boolean bl = true;
            String string5 = "addEntityToWorld";
            this.s = this.Y(string5, bl, clazz8, classArray3);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            int n2 = n + 1;
            MPlayerControllerMP.p(n2);
         }
      }
   }

   public void Z(Object object, int n, Entity entity) {
      if (ForgeVersion.MC_1_20_6.d()) {
         this.s.invokeVoid(object, entity.getObject());
      } else {
         this.s.invokeVoid(object, n, entity.getObject());
      }
   }

   public Object t(Object object, int n) {
      return this.u.invokeObject(object, n);
   }

   public Object u(Object object) {
      return this.D.getObject(object);
   }

   public Object y(Object object) {
      return this.x.getObject(object);
   }
}
