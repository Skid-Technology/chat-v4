package com.google.common.io;

import java.io.IOException;

abstract class LineBuffer {
   private StringBuilder line = new StringBuilder();
   private boolean sawReturn;

   protected void add(char[] cbuf, int off, int len) throws IOException {
      int pos = off;
      if (this.sawReturn && len > 0 && this.finishLine(cbuf[off] == '\n')) {
         pos = off + 1;
      }

      int start = pos;

      for (int end = off + len; pos < end; pos++) {
         switch (cbuf[pos]) {
            case '\n':
               this.line.append(cbuf, start, pos - start);
               this.finishLine(true);
               start = pos + 1;
               break;
            case '\r':
               this.line.append(cbuf, start, pos - start);
               this.sawReturn = true;
               if (pos + 1 < end && this.finishLine(cbuf[pos + 1] == '\n')) {
                  pos++;
               }

               start = pos + 1;
         }
      }

      this.line.append(cbuf, start, off + len - start);
   }

   private boolean finishLine(boolean sawNewline) throws IOException {
      String var10001 = this.line.toString();
      LineBuffer var10000;
      String var10002;
      if (this.sawReturn) {
         String var3 = var10001;
         if (sawNewline) {
            var10000 = this;
            var10001 = var3;
            var10002 = "\r\n";
         } else {
            var10000 = this;
            var10001 = var3;
            var10002 = "\r";
         }
      } else {
         String var5 = var10001;
         if (sawNewline) {
            var10000 = this;
            var10001 = var5;
            var10002 = "\n";
         } else {
            var10000 = this;
            var10001 = var5;
            var10002 = "";
         }
      }

      var10000.handleLine(var10001, var10002);
      this.line = new StringBuilder();
      this.sawReturn = false;
      return sawNewline;
   }

   protected void finish() throws IOException {
      if (this.sawReturn || this.line.length() > 0) {
         this.finishLine(false);
      }
   }

   protected abstract void handleLine(String var1, String var2) throws IOException;
}
