package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MGlStateManager_TextureState extends Mapping {
   public final MappingField textureNameField = this.fieldBuilder("textureName", int.class)
      .setNameForVersion(ForgeVersion.MC_1_20_6.n(), "binding")
      .buildField();

   public MGlStateManager_TextureState() {
      super(MappedClasses.Zn);
   }

   public int getTextureName(Object textureStateHandle) {
      return this.textureNameField.getInt(textureStateHandle);
   }

   public void setTextureName(Object textureStateHandle, int textureName) {
      this.textureNameField.setInt(textureStateHandle, textureName);
   }
}
