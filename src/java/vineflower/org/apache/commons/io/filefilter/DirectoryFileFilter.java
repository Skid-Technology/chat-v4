package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

public class DirectoryFileFilter extends AbstractFileFilter implements Serializable {
   public static final IOFileFilter DIRECTORY = new DirectoryFileFilter();
   public static final IOFileFilter INSTANCE = DIRECTORY;

   protected DirectoryFileFilter() {
   }

   @Override
   public boolean accept(File file) {
      return file.isDirectory();
   }
}
