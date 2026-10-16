package org.apache.commons.io.filefilter;

import java.io.File;

public abstract class AbstractFileFilter implements IOFileFilter {
   @Override
   public boolean accept(File file) {
      return this.accept(file.getParentFile(), file.getName());
   }

   @Override
   public boolean accept(File dir, String name) {
      return this.accept(new File(dir, name));
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }
}
