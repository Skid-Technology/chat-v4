package org.apache.commons.io.output;

import java.io.OutputStream;

public class CloseShieldOutputStream extends ProxyOutputStream {
   public CloseShieldOutputStream(OutputStream out) {
      super(out);
   }

   @Override
   public void close() {
      this.out = new ClosedOutputStream();
   }
}
