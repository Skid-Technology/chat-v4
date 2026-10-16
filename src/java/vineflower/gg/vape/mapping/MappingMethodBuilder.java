package gg.vape.mapping;

import gg.vape.module.MinecraftVersionConstraint;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MappingMethodBuilder extends MappingMemberBuilder<MappingMethodBuilder, MappingMethod> {
   private boolean skipAccessorGeneration;
   private Class<?>[] defaultParameterTypes;
   private final Map<MinecraftVersionConstraint, Class[]> versionedParameterTypes = new HashMap<>();

   public MappingMethodBuilder setSkipAccessorGeneration(boolean skipAccessorGeneration) {
      this.skipAccessorGeneration = skipAccessorGeneration;
      return this;
   }

   public boolean isSkipAccessorGeneration() {
      return this.skipAccessorGeneration;
   }

   public Class<?>[] getParameterTypes() {
      if (!this.versionedParameterTypes.isEmpty()) {
         for (Entry<MinecraftVersionConstraint, Class[]> entry : this.versionedParameterTypes.entrySet()) {
            if (entry.getKey().y()) {
               return entry.getValue();
            }
         }
      }

      return this.defaultParameterTypes;
   }

   public MappingMethodBuilder setParameterTypesForVersion(MinecraftVersionConstraint versionConstraint, Class... parameterTypes) {
      this.versionedParameterTypes.put(versionConstraint, parameterTypes);
      return this;
   }

   public MappingMethod buildMethod() {
      return this.shouldSkipBuild() ? null : MappingMethod.fromBuilder(this);
   }

   public MappingMethod build() {
      return this.buildMethod();
   }

   public MappingMethodBuilder setParameterTypes(Class<?>[] parameterTypes) {
      this.defaultParameterTypes = parameterTypes;
      return this;
   }
}
