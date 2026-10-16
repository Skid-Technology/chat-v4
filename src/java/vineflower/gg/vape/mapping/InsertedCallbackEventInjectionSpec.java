package gg.vape.mapping;

public class InsertedCallbackEventInjectionSpec extends EventInjectionSpec {
   private static final String h;

   @Override
   public String buildInjectionCode() {
      return this.getEventClass().getName() + h;
   }

   public InsertedCallbackEventInjectionSpec(MappingMethod mappingMethod, Class<? extends InsertedCallbackMarker> clazz) {
      super(mappingMethod, clazz);
   }

   static {
      try {
         h = "#call();";
      } catch (Exception var1) {
         throw new ExceptionInInitializerError(var1);
      }
   }
}
