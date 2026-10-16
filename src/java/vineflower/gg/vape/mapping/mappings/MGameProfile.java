package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.UUID;

public class MGameProfile extends Mapping {
   private final MappingField A;
   private static String[] W;
   private MappingMethod u;
   private final MappingMethod e;
   private MappingField U;

   public static String[] I() {
      return W;
   }

   private Object W(UUID uUID, String string) {
      return this.e.newInstance(uUID, string);
   }

   public static void n(String[] stringArray) {
      W = stringArray;
   }

   private UUID j(Object object) {
      return ForgeVersion.MC_1_21_10.d() ? (UUID)this.U.getObject(object) : (UUID)this.u.invokeObject(object);
   }

   private String K(Object object) {
      return (String)this.A.getObject(object);
   }

   public static String P(MGameProfile mGameProfile, Object object) {
      return mGameProfile.K(object);
   }

   public static Object N(MGameProfile mGameProfile, UUID uUID, String string) {
      return mGameProfile.W(uUID, string);
   }

   public static UUID h(MGameProfile mGameProfile, Object object) {
      return mGameProfile.j(object);
   }

   public MGameProfile() {
      this(I());
   }

   private MGameProfile(String[] stringArray) {
      super(MappedClasses.VD);
      if (ForgeVersion.MC_1_21_10.d()) {
         Class<UUID> clazz = UUID.class;
         boolean bl = false;
         String string = "id";
         this.U = this.J(string, bl, clazz);
      } else {
         Class[] classArray = new Class[0];
         Class<UUID> clazz = UUID.class;
         boolean bl = false;
         String string = "getId";
         this.u = this.Y(string, bl, clazz, classArray);
      }

      Class<String> clazz = String.class;
      boolean bl = false;
      String string = "name";
      this.A = this.J(string, bl, clazz);
      Class[] classArray = new Class[]{UUID.class, String.class};
      Class<Void> clazz2 = void.class;
      boolean bl2 = false;
      String string2 = "<init>";
      this.e = this.Y(string2, bl2, clazz2, classArray);
   }

   public static MappingField z(MGameProfile mGameProfile) {
      return mGameProfile.A;
   }

   static {
      n(new String[3]);
   }
}
