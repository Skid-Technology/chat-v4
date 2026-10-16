package org.lwjgl.util;

import java.io.Serializable;

public final class Point implements ReadablePoint, WritablePoint, Serializable {
   static final long serialVersionUID = 1L;
   private int x;
   private int y;

   public Point() {
   }

   public Point(int x, int y) {
      this.setLocation(x, y);
   }

   public Point(ReadablePoint p) {
      this.setLocation(p);
   }

   @Override
   public void setLocation(int x, int y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void setLocation(ReadablePoint p) {
      this.x = p.getX();
      this.y = p.getY();
   }

   @Override
   public void setX(int x) {
      this.x = x;
   }

   @Override
   public void setY(int y) {
      this.y = y;
   }

   public void translate(int dx, int dy) {
      this.x += dx;
      this.y += dy;
   }

   public void translate(ReadablePoint p) {
      this.x = this.x + p.getX();
      this.y = this.y + p.getY();
   }

   public void untranslate(ReadablePoint p) {
      this.x = this.x - p.getX();
      this.y = this.y - p.getY();
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Point)) {
         return super.equals(obj);
      } else {
         Point pt = (Point)obj;
         return this.x == pt.x && this.y == pt.y;
      }
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "[x=" + this.x + ",y=" + this.y + "]";
   }

   @Override
   public int hashCode() {
      int sum = this.x + this.y;
      return sum * (sum + 1) / 2 + this.x;
   }

   @Override
   public int getX() {
      return this.x;
   }

   @Override
   public int getY() {
      return this.y;
   }

   @Override
   public void getLocation(WritablePoint dest) {
      dest.setLocation(this.x, this.y);
   }
}
