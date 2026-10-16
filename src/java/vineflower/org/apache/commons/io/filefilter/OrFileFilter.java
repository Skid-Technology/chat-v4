package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrFileFilter extends AbstractFileFilter implements ConditionalFileFilter, Serializable {
   private final List<IOFileFilter> fileFilters;

   public OrFileFilter() {
      this.fileFilters = new ArrayList<>();
   }

   public OrFileFilter(List<IOFileFilter> fileFilters) {
      if (fileFilters == null) {
         this.fileFilters = new ArrayList<>();
      } else {
         this.fileFilters = new ArrayList<>(fileFilters);
      }
   }

   public OrFileFilter(IOFileFilter filter1, IOFileFilter filter2) {
      if (filter1 != null && filter2 != null) {
         this.fileFilters = new ArrayList<>(2);
         this.addFileFilter(filter1);
         this.addFileFilter(filter2);
      } else {
         throw new IllegalArgumentException("The filters must not be null");
      }
   }

   @Override
   public void addFileFilter(IOFileFilter ioFileFilter) {
      this.fileFilters.add(ioFileFilter);
   }

   @Override
   public List<IOFileFilter> getFileFilters() {
      return Collections.unmodifiableList(this.fileFilters);
   }

   @Override
   public boolean removeFileFilter(IOFileFilter ioFileFilter) {
      return this.fileFilters.remove(ioFileFilter);
   }

   @Override
   public void setFileFilters(List<IOFileFilter> fileFilters) {
      this.fileFilters.clear();
      this.fileFilters.addAll(fileFilters);
   }

   @Override
   public boolean accept(File file) {
      for (IOFileFilter fileFilter : this.fileFilters) {
         if (fileFilter.accept(file)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean accept(File file, String name) {
      for (IOFileFilter fileFilter : this.fileFilters) {
         if (fileFilter.accept(file, name)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public String toString() {
      StringBuilder buffer = new StringBuilder();
      buffer.append(super.toString());
      buffer.append("(");
      if (this.fileFilters != null) {
         for (int i = 0; i < this.fileFilters.size(); i++) {
            if (i > 0) {
               buffer.append(",");
            }

            Object filter = this.fileFilters.get(i);
            buffer.append(filter == null ? "null" : filter.toString());
         }
      }

      buffer.append(")");
      return buffer.toString();
   }
}
