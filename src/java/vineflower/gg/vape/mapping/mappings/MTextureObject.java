package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextureObject extends Mapping {
   private MappingMethod setFilterMethod;
   private MappingField textureField;
   private MappingMethod getGlTextureIdMethod;
   private MappingField glTextureIdField;

   public static void setFilter(MTextureObject mapping, Object textureObject, boolean blur, boolean mipmap) {
      mapping.setFilter(textureObject, blur, mipmap);
   }

   private void setFilter(Object textureObject, boolean blur, boolean mipmap) {
      this.setFilterMethod.invokeVoid(textureObject, blur, mipmap);
   }

   private Object getTexture(Object textureObject) {
      return this.textureField.getObject(textureObject);
   }

   public static int getGlTextureId(MTextureObject mapping, Object textureObject) {
      return mapping.getGlTextureId(textureObject);
   }

   public static Object getTexture(MTextureObject mapping, Object textureObject) {
      return mapping.getTexture(textureObject);
   }

   private int getGlTextureId(Object textureObject) {
      return ForgeVersion.MC_1_20_6.d() ? this.glTextureIdField.getInt(textureObject) : this.getGlTextureIdMethod.invokeInt(textureObject);
   }

   public MTextureObject() {
      this(MTextureManager.getInitialControlFlowState());
   }

   private MTextureObject(int n) {
      super(MappedClasses.ut);
      if (n != 0) {
         if (ForgeVersion.MC_1_21_6.d()) {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "glTextureId";
            this.glTextureIdField = this.J(string, bl, clazz);
            Class[] classArray = new Class[]{boolean.class, boolean.class};
            Class<Void> clazz2 = void.class;
            boolean bl2 = true;
            String string2 = "setFilter";
            this.setFilterMethod = this.Y(string2, bl2, clazz2, classArray);
         }

         Class[] classArray = new Class[0];
         Class<Integer> clazz = int.class;
         boolean bl = true;
         String string = "getGlTextureId";
         this.getGlTextureIdMethod = this.Y(string, bl, clazz, classArray);
      } else {
         if (ForgeVersion.MC_1_21_6.d()) {
            Class clazz = MappedClasses.GPU_TEXTURE;
            boolean bl = true;
            String string = "texture";
            this.textureField = this.J(string, bl, clazz);
         } else if (ForgeVersion.MC_1_20_6.d()) {
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "glTextureId";
            this.glTextureIdField = this.J(string, bl, clazz);
            Class[] classArray = new Class[]{boolean.class, boolean.class};
            Class<Void> clazz3 = void.class;
            boolean bl3 = true;
            String string3 = "setFilter";
            this.setFilterMethod = this.Y(string3, bl3, clazz3, classArray);
         } else {
            Class[] classArray = new Class[0];
            Class<Integer> clazz = int.class;
            boolean bl = true;
            String string = "getGlTextureId";
            this.getGlTextureIdMethod = this.Y(string, bl, clazz, classArray);
         }
      }
   }
}
