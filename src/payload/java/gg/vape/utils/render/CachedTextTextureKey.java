package gg.vape.utils.render;

import java.util.Objects;

class CachedTextTextureKey {
   String text;
   int color;

   public String getText() {
      return this.text;
   }

   public CachedTextTextureKey(String text, int color) {
      this.text = text;
      this.color = color;
   }

   public int getColor() {
      return this.color;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof CachedTextTextureKey)) {
         return false;
      } else {
         CachedTextTextureKey other = (CachedTextTextureKey)object;
         return !other.getText().equals(this.getText()) ? false : other.getColor() == this.getColor();
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getText(), this.getColor());
   }
}
