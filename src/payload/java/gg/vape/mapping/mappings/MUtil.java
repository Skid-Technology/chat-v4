package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MUtil extends Mapping {
   private final MappingMethod glfwPollEventsMethod;
   private final MappingMethod glfwGetCurrentContextMethod = this.registerStaticMethod("glfwGetCurrentContext", false, long.class);

   public long glfwGetCurrentContext() {
      return this.glfwGetCurrentContextMethod.invokeLong(null);
   }

   public void glfwPollEvents() {
      this.glfwPollEventsMethod.invokeVoidNoArgs(null);
   }

   public MUtil() {
      super(MappedClasses.zb);
      this.glfwPollEventsMethod = this.registerStaticMethod("glfwPollEvents", false, void.class);
   }
}
