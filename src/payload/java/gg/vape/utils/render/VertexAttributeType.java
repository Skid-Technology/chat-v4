package gg.vape.utils.render;

public enum VertexAttributeType {
   Float(1, false, 5126),
   Vec2(2, false, 5126),
   Vec3(3, false, 5126),
   Vec4(4, true, 5126);

   private static final VertexAttributeType[] DECLARED_VALUES = new VertexAttributeType[]{Float, Vec2, Vec3, Vec4};
   public final int count;
   public final int type;
   public final boolean normalized;

   private VertexAttributeType(int componentCount, boolean normalized, int openGlType) {
      this.count = componentCount;
      this.normalized = normalized;
      this.type = openGlType;
   }

   static {
      String[] var10000 = new String[]{"Vec3", "Vec4", "Vec2", "Float"};
   }
}
