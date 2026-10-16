package org.lwjgl.util;

import java.io.Serializable;

public final class Dimension implements Serializable, ReadableDimension, WritableDimension {
   static final long serialVersionUID = 1L;
   private int width;
   private int height;

   public Dimension() {
   }

   public Dimension(int w, int h) {
      this.width = w;
      this.height = h;
   }

   public Dimension(ReadableDimension d) {
      this.setSize(d);
   }

   @Override
   public void setSize(int w, int h) {
      this.width = w;
      this.height = h;
   }

   @Override
   public void setSize(ReadableDimension d) {
      this.width = d.getWidth();
      this.height = d.getHeight();
   }

   @Override
   public void getSize(WritableDimension dest) {
      dest.setSize(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof ReadableDimension)) {
         return false;
      } else {
         ReadableDimension d = (ReadableDimension)obj;
         return this.width == d.getWidth() && this.height == d.getHeight();
      }
   }

   @Override
   public int hashCode() {
      int sum = this.width + this.height;
      return sum * (sum + 1) / 2 + this.width;
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "[width=" + this.width + ",height=" + this.height + "]";
   }

   @Override
   public int getHeight() {
      return this.height;
   }

   @Override
   public void setHeight(int height) {
      this.height = height;
   }

   @Override
   public int getWidth() {
      return this.width;
   }

   @Override
   public void setWidth(int width) {
      this.width = width;
   }
}
