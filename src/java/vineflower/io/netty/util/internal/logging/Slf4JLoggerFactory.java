package io.netty.util.internal.logging;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

public class Slf4JLoggerFactory extends InternalLoggerFactory {
   public Slf4JLoggerFactory() {
   }

   Slf4JLoggerFactory(boolean failIfNOP) {
      assert failIfNOP;

      final StringBuffer buf = new StringBuffer();
      PrintStream err = System.err;

      try {
         System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
               buf.append((char)b);
            }
         }, true, "US-ASCII"));
      } catch (UnsupportedEncodingException var8) {
         throw new Error(var8);
      }

      try {
         if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
            throw new NoClassDefFoundError(buf.toString());
         }

         err.print(buf);
         err.flush();
      } finally {
         System.setErr(err);
      }
   }

   @Override
   public InternalLogger newInstance(String name) {
      return new Slf4JLogger(LoggerFactory.getLogger(name));
   }
}
