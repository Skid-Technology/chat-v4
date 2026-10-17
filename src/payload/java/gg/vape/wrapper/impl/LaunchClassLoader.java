package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MLaunchClassLoader;
import gg.vape.wrapper.Wrapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LaunchClassLoader extends Wrapper {
   public static LaunchClassLoader getLaunchClassLoader() {
      return new LaunchClassLoader(vapeInstance.getMappingsMapperCompat().C4.getInstance());
   }

   public LaunchClassLoader(Object object) {
      super(object);
   }

   public Set getClassLoaderExceptions() {
      return (Set)(ForgeVersion.MC_1_16_5.d() ? new HashSet() : MLaunchClassLoader.l(vapeInstance.getMappingsMapperCompat().C4, this.I));
   }

   public Map cachedClasses() {
      return (Map)(ForgeVersion.MC_1_16_5.d() ? new HashMap() : vapeInstance.getMappingsMapperCompat().C4.cachedClasses(this.I));
   }

   public boolean supportsLegacyClassCache() {
      return vapeInstance.getMappingsMapperCompat().C4.supportsLegacyClassCache();
   }
}
