package io.netty.util.internal.logging;

import org.apache.log4j.Logger;

public class Log4JLoggerFactory extends InternalLoggerFactory {
   @Override
   public InternalLogger newInstance(String name) {
      return new Log4JLogger(Logger.getLogger(name));
   }
}
