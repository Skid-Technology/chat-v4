package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MGpuTextureView extends Mapping {
   private MappingMethod isClosedMethod;
   private MappingField mipLevelsField;
   private MappingMethod getHeightMethod;
   private MappingField baseMipLevelField;
   private static String[] constructorState;
   private MappingField textureField;
   private MappingMethod getWidthMethod;

   public int getWidth(Object textureView, int mipLevel) {
      return this.getWidthMethod != null && !this.getWidthMethod.hasResolutionFailed() ? this.getWidthMethod.invokeInt(textureView, mipLevel) : 0;
   }

   public boolean isClosed(Object textureView) {
      return this.isClosedMethod != null && !this.isClosedMethod.hasResolutionFailed() ? this.isClosedMethod.invokeBoolean(textureView) : false;
   }

   public static String[] getGpuTextureViewConstructorState() {
      return constructorState;
   }

   public MGpuTextureView() {
      this(getGpuTextureViewConstructorState());
   }

   private MGpuTextureView(String[] constructorState) {
      super(MappedClasses.GPU_TEXTURE_VIEW);
      Class textureType = MappedClasses.GPU_TEXTURE;
      boolean remapTextureField = true;
      String textureFieldName = "texture";
      this.textureField = this.J(textureFieldName, remapTextureField, textureType);
      Class<Integer> baseMipLevelType = int.class;
      boolean remapBaseMipLevelField = true;
      String baseMipLevelFieldName = "baseMipLevel";
      this.baseMipLevelField = this.J(baseMipLevelFieldName, remapBaseMipLevelField, baseMipLevelType);
      Class<Integer> mipLevelsType = int.class;
      boolean remapMipLevelsField = true;
      String mipLevelsFieldName = "mipLevels";
      this.mipLevelsField = this.J(mipLevelsFieldName, remapMipLevelsField, mipLevelsType);
      Class[] widthParameterTypes = new Class[]{int.class};
      Class<Integer> widthReturnType = int.class;
      boolean remapWidthMethod = true;
      String widthMethodName = "getWidth";
      this.getWidthMethod = this.Y(widthMethodName, remapWidthMethod, widthReturnType, widthParameterTypes);
      Class[] heightParameterTypes = new Class[]{int.class};
      Class<Integer> heightReturnType = int.class;
      boolean remapHeightMethod = true;
      String heightMethodName = "getHeight";
      this.getHeightMethod = this.Y(heightMethodName, remapHeightMethod, heightReturnType, heightParameterTypes);
      Class[] isClosedParameterTypes = new Class[0];
      Class<Boolean> isClosedReturnType = boolean.class;
      boolean remapIsClosedMethod = true;
      String isClosedMethodName = "isClosed";
      this.isClosedMethod = this.Y(isClosedMethodName, remapIsClosedMethod, isClosedReturnType, isClosedParameterTypes);
   }

   public int getMipLevels(Object textureView) {
      return this.mipLevelsField == null ? 1 : this.mipLevelsField.getInt(textureView);
   }

   public int getBaseMipLevel(Object textureView) {
      return this.baseMipLevelField == null ? 0 : this.baseMipLevelField.getInt(textureView);
   }

   public static void setGpuTextureViewConstructorState(String[] state) {
      constructorState = state;
   }

   public Object getTexture(Object textureView) {
      return this.textureField == null ? null : this.textureField.getObject(textureView);
   }

   public int getHeight(Object textureView, int mipLevel) {
      return this.getHeightMethod != null && !this.getHeightMethod.hasResolutionFailed() ? this.getHeightMethod.invokeInt(textureView, mipLevel) : 0;
   }

   static {
      setGpuTextureViewConstructorState(null);
   }
}
