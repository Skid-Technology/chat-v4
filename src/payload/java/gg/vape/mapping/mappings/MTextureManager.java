package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextureManager extends Mapping {
   private static int textureManagerControlFlowState;
   public MappingMethod bindTextureOrFallbackGetTextureMethod;
   private final MappingMethod getTextureMethod;

   private void bindTexture(Object textureManager, Object location) {
      this.bindTextureOrFallbackGetTextureMethod.invokeVoid(textureManager, location);
   }

   public static int getTextureManagerControlFlowState() {
      return textureManagerControlFlowState;
   }

   public static void bindTexture(MTextureManager mapping, Object textureManager, Object location) {
      mapping.bindTexture(textureManager, location);
   }

   public MTextureManager() {
      this(getInitialControlFlowState());
   }

   private MTextureManager(int n) {
      super(MappedClasses.Dt);
      if (n != 0) {
         Class[] classArray = new Class[]{MappedClasses.zC};
         Class clazz = MappedClasses.ut;
         boolean bl = true;
         String string = "getTexture";
         this.bindTextureOrFallbackGetTextureMethod = this.Y(string, bl, clazz, classArray);
         this.getTextureMethod = null;
      } else {
         Class[] classArray = new Class[]{MappedClasses.zC};
         Class clazz = MappedClasses.ut;
         boolean bl = true;
         String string = "getTexture";
         this.getTextureMethod = this.Y(string, bl, clazz, classArray);
         if (ForgeVersion.MC_1_21_0.v()) {
            Class[] classArray2 = new Class[]{MappedClasses.zC};
            Class<Void> clazz2 = void.class;
            boolean bl2 = true;
            String string2 = "bindTexture";
            this.bindTextureOrFallbackGetTextureMethod = this.Y(string2, bl2, clazz2, classArray2);
         }
      }
   }

   public static int getInitialControlFlowState() {
      int n = getTextureManagerControlFlowState();
      return n == 0 ? 90 : 0;
   }

   private Object getTexture(Object textureManager, Object location) {
      return this.getTextureMethod.invokeObject(textureManager, location);
   }

   public static void setTextureManagerControlFlowState(int state) {
      textureManagerControlFlowState = state;
   }

   public static Object getTexture(MTextureManager mapping, Object textureManager, Object location) {
      return mapping.getTexture(textureManager, location);
   }

   static {
      setTextureManagerControlFlowState(69);
   }
}
