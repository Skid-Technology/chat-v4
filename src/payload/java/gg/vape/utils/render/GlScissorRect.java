package gg.vape.utils.render;

public class GlScissorRect {
   public int width;
   public int y;
   public int height;
   public int x;

   public GlScissorRect(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         GlScissorRect glScissorRect = (GlScissorRect)object;
         return this.x == glScissorRect.x && this.y == glScissorRect.y && this.width == glScissorRect.width && this.height == glScissorRect.height;
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "ScissorCoords{x=" + this.x + ", y=" + this.y + ", w=" + this.width + ", h=" + this.height + '}';
   }
}
