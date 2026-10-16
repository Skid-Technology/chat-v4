package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

public class NotFileFilter extends AbstractFileFilter implements Serializable {
   private final IOFileFilter filter;

   public NotFileFilter(IOFileFilter filter) {
      if (filter == null) {
         throw new IllegalArgumentException("The filter must not be null");
      } else {
         this.filter = filter;
      }
   }

   @Override
   public boolean accept(File file) {
      return !this.filter.accept(file);
   }

   @Override
   public boolean accept(File file, String name) {
      return !this.filter.accept(file, name);
   }

   @Override
   public String toString() {
      return super.toString() + "(" + this.filter.toString() + ")";
   }
}
