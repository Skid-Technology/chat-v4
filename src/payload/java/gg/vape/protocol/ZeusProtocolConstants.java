package gg.vape.protocol;

import io.netty.util.AttributeKey;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

public class ZeusProtocolConstants {
   public static final int z;
   public static final Pattern y;
   public static final int e;
   public static AttributeKey<ZeusProtocolState> Q;
   public static final int C;
   public static final int s;
   public static final int U;
   public static final int a;

   private static AttributeKey<ZeusProtocolState> E(String string) {
      try {
         Constructor constructor = AttributeKey.class.getDeclaredConstructor(String.class);
         return (AttributeKey<ZeusProtocolState>)constructor.newInstance(string);
      } catch (NoSuchMethodException var2) {
         return AttributeKey.valueOf(string);
      } catch (InstantiationException | InvocationTargetException | IllegalAccessException var3) {
         throw new RuntimeException(var3);
      }
   }

   static {
      String[] stringArray = new String[]{"^[a-zA-Z\\d_.-]*$", "connection_state"};
      long[] lArray = new long[]{-1775853824527826689L, 5551149997770145808L, 5142080514882011146L, -1319690179857874936L, -694266774610247552L};
      C = (int)lArray[3];
      a = (int)lArray[2];
      z = (int)lArray[1];
      s = (int)lArray[4];
      e = (int)lArray[0];
      U = (int)lArray[3];
      y = Pattern.compile(stringArray[0]);
      Q = E(stringArray[1]);
   }
}
