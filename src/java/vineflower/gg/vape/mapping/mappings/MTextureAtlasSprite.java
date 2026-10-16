package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextureAtlasSprite extends Mapping {
   private MappingField atlasLocationField;
   private MappingField u0Field;
   private MappingField u1Field;
   private MappingField v0Field;
   private MappingField primaryPositionField;
   private MappingField v1Field;
   private MappingField contentsOrAtlasTextureField;
   private MappingField secondaryPositionField;

   public static void setPrimaryPosition(MTextureAtlasSprite mapping, Object sprite, int position) {
      mapping.setPrimaryPosition(sprite, position);
   }

   public static void setV0(MTextureAtlasSprite mapping, Object sprite, float v0) {
      mapping.setV0(sprite, v0);
   }

   private void setPrimaryPosition(Object sprite, int position) {
      this.primaryPositionField.setInt(sprite, position);
   }

   private void setV0(Object sprite, float v0) {
      this.v0Field.setFloat(sprite, v0);
   }

   private void setSecondaryPosition(Object sprite, int position) {
      this.secondaryPositionField.setInt(sprite, position);
   }

   private Object getContentsOrAtlasTexture(Object sprite) {
      return this.contentsOrAtlasTextureField.getObject(sprite);
   }

   private void setU1(Object sprite, float u1) {
      this.u1Field.setFloat(sprite, u1);
   }

   private void setV1(Object sprite, float v1) {
      this.v1Field.setFloat(sprite, v1);
   }

   public static Object getContentsOrAtlasTexture(MTextureAtlasSprite mapping, Object sprite) {
      return mapping.getContentsOrAtlasTexture(sprite);
   }

   public MTextureAtlasSprite() {
      this(MTextureManager.getInitialControlFlowState());
   }

   private MTextureAtlasSprite(int n) {
      super(MappedClasses.Db);
      Class<Float> clazz = float.class;
      boolean bl = true;
      String string = "u0";
      this.u0Field = this.J(string, bl, clazz);
      Class<Float> clazz2 = float.class;
      boolean bl2 = true;
      String string2 = "u1";
      this.u1Field = this.J(string2, bl2, clazz2);
      if (n != 0) {
         Class<Float> clazz3 = float.class;
         boolean bl3 = true;
         String string3 = "v0";
         this.v0Field = this.J(string3, bl3, clazz3);
         Class<Float> clazz4 = float.class;
         boolean bl4 = true;
         String string4 = "v1";
         this.v1Field = this.J(string4, bl4, clazz4);
         Class clazz5 = MappedClasses.L;
         boolean bl5 = true;
         String string5 = "atlasTexture";
         this.contentsOrAtlasTextureField = this.J(string5, bl5, clazz5);
      } else {
         Class<Float> clazz6 = float.class;
         boolean bl6 = true;
         String string6 = "v0";
         this.v0Field = this.J(string6, bl6, clazz6);
         Class<Float> clazz7 = float.class;
         boolean bl7 = true;
         String string7 = "v1";
         this.v1Field = this.J(string7, bl7, clazz7);
         if (ForgeVersion.MC_1_20_6.d()) {
            Class<Integer> clazz8 = int.class;
            boolean bl8 = true;
            String string8 = "y";
            this.primaryPositionField = this.J(string8, bl8, clazz8);
            Class<Integer> clazz9 = int.class;
            boolean bl9 = true;
            String string9 = "x";
            this.secondaryPositionField = this.J(string9, bl9, clazz9);
            Class clazz10 = MappedClasses.V4;
            boolean bl10 = true;
            String string10 = "contents";
            this.contentsOrAtlasTextureField = this.J(string10, bl10, clazz10);
            Class clazz11 = MappedClasses.zC;
            boolean bl11 = true;
            String string11 = "atlasLocation";
            this.atlasLocationField = this.J(string11, bl11, clazz11);
         } else {
            Class<Integer> clazz12 = int.class;
            boolean bl12 = true;
            String string12 = "x";
            this.primaryPositionField = this.J(string12, bl12, clazz12);
            Class<Integer> clazz13 = int.class;
            boolean bl13 = true;
            String string13 = "y";
            this.secondaryPositionField = this.J(string13, bl13, clazz13);
            Class clazz14 = MappedClasses.L;
            boolean bl14 = true;
            String string14 = "atlasTexture";
            this.contentsOrAtlasTextureField = this.J(string14, bl14, clazz14);
         }
      }
   }

   private float[] getTextureCoordinates(Object sprite) {
      return new float[]{this.u0Field.getFloat(sprite), this.u1Field.getFloat(sprite), this.v0Field.getFloat(sprite), this.v1Field.getFloat(sprite)};
   }

   private Object getAtlasLocation(Object sprite) {
      return this.atlasLocationField.getObject(sprite);
   }

   public static float[] getTextureCoordinates(MTextureAtlasSprite mapping, Object sprite) {
      return mapping.getTextureCoordinates(sprite);
   }

   private void setU0(Object sprite, float u0) {
      this.u0Field.setFloat(sprite, u0);
   }

   public static Object getAtlasLocation(MTextureAtlasSprite mapping, Object sprite) {
      return mapping.getAtlasLocation(sprite);
   }

   public static void setU1(MTextureAtlasSprite mapping, Object sprite, float u1) {
      mapping.setU1(sprite, u1);
   }

   public static void setV1(MTextureAtlasSprite mapping, Object sprite, float v1) {
      mapping.setV1(sprite, v1);
   }

   public static void setU0(MTextureAtlasSprite mapping, Object sprite, float u0) {
      mapping.setU0(sprite, u0);
   }

   public static void setSecondaryPosition(MTextureAtlasSprite mapping, Object sprite, int position) {
      mapping.setSecondaryPosition(sprite, position);
   }
}
