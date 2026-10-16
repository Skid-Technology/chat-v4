package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

public class FalseFileFilter implements IOFileFilter, Serializable {
   public static final IOFileFilter FALSE = new FalseFileFilter();
   public static final IOFileFilter INSTANCE = FALSE;

   protected FalseFileFilter() {
   }

   @Override
   public boolean accept(File file) {
      return false;
   }

   @Override
   public boolean accept(File dir, String name) {
      return false;
   }
}
