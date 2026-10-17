package gg.vape.wrapper;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import java.util.HashMap;
import java.util.Map;

public class Wrapper {
   private static final Map<String, Integer> wrapperIdCache = new HashMap<>();
   public static boolean nativeDisabled = false;
   private static final String nullInstanceText = "Null instance";
   private static GuiComponent[] obfuscationState;
   public static boolean isNativeAvailable = Wrapper.vapeInstance.isForgeAbsent();
   protected Object I;
   public static Vape vapeInstance = Vape.INSTANCE;

   @Override
   public String toString() {
      if (this.I == null) {
         return nullInstanceText;
      } else {
         try {
            return this.I.toString();
         } catch (Exception var2) {
            return "";
         }
      }
   }

   public boolean isInstance(Class clazz) {
      return this.isNotNull() && clazz != null && clazz.isInstance(this.I);
   }

   public Object getObject() {
      return this.I;
   }

   public boolean isNull() {
      return this.I == null;
   }

   public Wrapper(Object object) {
      if (object instanceof Wrapper) {
         Wrapper wrapper = (Wrapper)object;
         this.I = wrapper.getObject();
      } else {
         this.I = object;
      }
   }

   public static GuiComponent[] getObfuscationState() {
      return obfuscationState;
   }

   public boolean isNotNull() {
      return !this.isNull();
   }

   public static void setObfuscationState(GuiComponent[] state) {
      obfuscationState = state;
   }

   @Override
   public boolean equals(Object object) {
      if (object == null || this.I == null) {
         return false;
      } else if (object instanceof Wrapper) {
         Wrapper wrapper = (Wrapper)object;
         return this.I.equals(wrapper.I);
      } else {
         return super.equals(object);
      }
   }

   static {
      setObfuscationState(null);
   }
}
