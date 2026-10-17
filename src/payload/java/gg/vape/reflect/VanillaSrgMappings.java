package gg.vape.reflect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

final class VanillaSrgMappings {
   private final String minecraftVersion;
   private final String resource;
   private final String runtimeMinecraftClass;
   private final String runtimeMinecraftGetter;
   private final String runtimeMinecraftInstanceField;
   private final String[] runtimeAnchors;
   private final Map<String, String> aliasClassNames = new LinkedHashMap<>();
   private volatile VanillaSrgMappings.MappingData data;

   VanillaSrgMappings(
      String minecraftVersion,
      String resource,
      String runtimeMinecraftClass,
      String runtimeMinecraftGetter,
      String runtimeMinecraftInstanceField,
      String... runtimeAnchors
   ) {
      this.minecraftVersion = minecraftVersion;
      this.resource = resource;
      this.runtimeMinecraftClass = runtimeMinecraftClass;
      this.runtimeMinecraftGetter = runtimeMinecraftGetter;
      this.runtimeMinecraftInstanceField = runtimeMinecraftInstanceField;
      this.runtimeAnchors = (String[])runtimeAnchors.clone();
   }

   void addAlias(String alias, String target) {
      String normalizedAlias = normalizeInternalName(alias);
      String normalizedTarget = normalizeInternalName(target);
      if (normalizedAlias != null && normalizedTarget != null && !normalizedAlias.equals(normalizedTarget)) {
         this.aliasClassNames.put(normalizedAlias, normalizedTarget);
      }
   }

   String remapClassName(String sourceClassName) {
      String normalized = normalizeInternalName(sourceClassName);
      if (normalized == null) {
         return null;
      } else {
         String remapped = this.getData().classNames.get(normalized);
         if (remapped != null) {
            return remapped;
         } else {
            String alias = this.aliasClassNames.get(normalized);
            if (alias != null) {
               remapped = this.getData().classNames.get(alias);
               return remapped != null ? remapped : alias;
            } else {
               return null;
            }
         }
      }
   }

   Class<?> resolveClass(String sourceClassName, ClassLoader... preferredLoaders) {
      String normalizedName = normalizeInternalName(sourceClassName);
      if (normalizedName != null && !normalizedName.isEmpty()) {
         Set<String> candidateNames = new LinkedHashSet<>();
         String remappedName = this.getData().classNames.get(normalizedName);
         if (remappedName != null) {
            candidateNames.add(remappedName);
         }

         String reverseMappedName = this.getData().reverseClassNames.get(normalizedName);
         if (reverseMappedName != null) {
            candidateNames.add(reverseMappedName);
         }

         String alias = this.aliasClassNames.get(normalizedName);
         if (alias != null) {
            String aliasRemapped = this.getData().classNames.get(alias);
            if (aliasRemapped != null) {
               candidateNames.add(aliasRemapped);
            }

            String aliasReverse = this.getData().reverseClassNames.get(alias);
            if (aliasReverse != null) {
               candidateNames.add(aliasReverse);
            }

            candidateNames.add(alias);
         }

         candidateNames.add(normalizedName);
         Set<ClassLoader> loaders = candidateLoaders(preferredLoaders);

         for (String candidateName : candidateNames) {
            String binaryName = candidateName.replace('/', '.');

            for (ClassLoader loader : loaders) {
               try {
                  return Class.forName(binaryName, false, loader);
               } catch (ClassNotFoundException var15) {
               } catch (LinkageError var16) {
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   boolean isRuntimePresent(ClassLoader... preferredLoaders) {
      for (ClassLoader loader : candidateLoaders(preferredLoaders)) {
         Class<?> minecraftClass = resolveRuntimeClass(this.runtimeMinecraftClass, loader);
         if (minecraftClass != null) {
            ClassLoader definingLoader = minecraftClass.getClassLoader();
            boolean anchorsMatch = true;
            if (this.runtimeAnchors.length > 0) {
               int matched = 0;

               for (String runtimeName : this.runtimeAnchors) {
                  Class<?> anchorClass = resolveRuntimeClass(runtimeName, loader);
                  if (anchorClass != null && (definingLoader == null || anchorClass.getClassLoader() == definingLoader)) {
                     matched++;
                  }
               }

               if (matched == 0) {
                  anchorsMatch = false;
               }
            }

            if (anchorsMatch && this.matchesMinecraftStructure(minecraftClass)) {
               return true;
            }
         }
      }

      return false;
   }

   String lookupFieldSrgName(Field field) {
      if (field == null) {
         return null;
      } else {
         String name = this.getData().fieldNames.get(memberKey(field.getDeclaringClass().getName(), field.getName()));
         if (name != null) {
            return name;
         } else {
            String declaringClassName = normalizeInternalName(field.getDeclaringClass().getName());
            String alias = this.aliasClassNames.get(declaringClassName);
            if (alias != null) {
               name = this.getData().fieldNames.get(memberKey(alias, field.getName()));
               if (name != null) {
                  return name;
               }
            }

            return null;
         }
      }
   }

   String lookupMethodSrgName(Method method) {
      if (method == null) {
         return null;
      } else {
         String ownerAndName = memberKey(method.getDeclaringClass().getName(), method.getName());
         String descriptor = ParameterResolver.methodDescriptor(method.getParameterTypes(), method.getReturnType());
         String name = this.getData().methodNames.get(ownerAndName + ' ' + descriptor);
         if (name != null) {
            return name;
         } else {
            String declaringClassName = normalizeInternalName(method.getDeclaringClass().getName());
            String alias = this.aliasClassNames.get(declaringClassName);
            if (alias != null) {
               name = this.getData().methodNames.get(memberKey(alias, method.getName()) + ' ' + descriptor);
               if (name != null) {
                  return name;
               }
            }

            return null;
         }
      }
   }

   int getClassMappingCount() {
      return this.getData().classNames.size();
   }

   int getFieldMappingCount() {
      return this.getData().fieldNames.size();
   }

   int getMethodMappingCount() {
      return this.getData().methodNames.size();
   }

   private VanillaSrgMappings.MappingData getData() {
      VanillaSrgMappings.MappingData result = this.data;
      if (result != null) {
         return result;
      } else {
         synchronized (this) {
            result = this.data;
            if (result == null) {
               result = this.loadMappings();
               this.data = result;
            }

            return result;
         }
      }
   }

   private VanillaSrgMappings.MappingData loadMappings() {
      Map<String, String> classNames = new LinkedHashMap<>();
      Map<String, String> reverseClassNames = new LinkedHashMap<>();
      Map<String, String> fieldNames = new LinkedHashMap<>();
      Map<String, String> methodNames = new LinkedHashMap<>();

      try {
         InputStream stream = VanillaSrgMappings.class.getResourceAsStream(this.resource);

         try {
            if (stream == null) {
               throw new FileNotFoundException("Missing mapping resource: " + this.resource);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

            String line;
            try {
               while ((line = reader.readLine()) != null) {
                  if (line.startsWith("CL: ")) {
                     String[] columns = line.split("\\s+");
                     if (columns.length == 3) {
                        classNames.put(columns[2], columns[1]);
                        reverseClassNames.put(columns[1], columns[2]);
                     }
                  } else if (line.startsWith("FD: ")) {
                     String[] columns = line.split("\\s+");
                     if (columns.length == 3) {
                        fieldNames.put(columns[1], simpleMemberName(columns[2]));
                        fieldNames.put(columns[2], simpleMemberName(columns[2]));
                     }
                  } else if (line.startsWith("MD: ")) {
                     String[] columns = line.split("\\s+");
                     if (columns.length == 5) {
                        methodNames.put(columns[1] + ' ' + columns[2], simpleMemberName(columns[3]));
                        methodNames.put(columns[3] + ' ' + columns[4], simpleMemberName(columns[3]));
                     }
                  }
               }
            } catch (Throwable var11) {
               try {
                  reader.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
               }

               throw var11;
            }

            reader.close();
         } catch (Throwable var12) {
            if (stream != null) {
               try {
                  stream.close();
               } catch (Throwable var9) {
                  var12.addSuppressed(var9);
               }
            }

            throw var12;
         }

         stream.close();
      } catch (IOException var13) {
         throw new IllegalStateException("Unable to load " + this.minecraftVersion + " mappings", var13);
      }

      if (!classNames.isEmpty() && !fieldNames.isEmpty() && !methodNames.isEmpty()) {
         return new VanillaSrgMappings.MappingData(classNames, reverseClassNames, fieldNames, methodNames);
      } else {
         throw new IllegalStateException("Incomplete " + this.minecraftVersion + " mappings in " + this.resource);
      }
   }

   private boolean matchesMinecraftStructure(Class<?> minecraftClass) {
      try {
         boolean hasGetter = false;

         try {
            Method getter = minecraftClass.getDeclaredMethod(this.runtimeMinecraftGetter);
            hasGetter = Modifier.isStatic(getter.getModifiers()) && getter.getReturnType() == minecraftClass;
         } catch (Throwable var9) {
            for (Method method : minecraftClass.getDeclaredMethods()) {
               if (Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 0 && method.getReturnType() == minecraftClass) {
                  hasGetter = true;
                  break;
               }
            }
         }

         if (!hasGetter) {
            return false;
         } else {
            try {
               Field instance = minecraftClass.getDeclaredField(this.runtimeMinecraftInstanceField);
               if (Modifier.isStatic(instance.getModifiers()) && instance.getType() == minecraftClass) {
                  return true;
               }
            } catch (Throwable var8) {
            }

            for (Field field : minecraftClass.getDeclaredFields()) {
               if (Modifier.isStatic(field.getModifiers()) && field.getType() == minecraftClass) {
                  return true;
               }
            }

            return false;
         }
      } catch (Throwable var10) {
         return false;
      }
   }

   private static Class<?> resolveRuntimeClass(String runtimeInternalName, ClassLoader loader) {
      String binaryName = runtimeInternalName.replace('/', '.');

      try {
         return Class.forName(binaryName, false, loader);
      } catch (ClassNotFoundException var4) {
         return null;
      } catch (LinkageError var5) {
         return null;
      }
   }

   private static Set<ClassLoader> candidateLoaders(ClassLoader... preferredLoaders) {
      Set<ClassLoader> loaders = new LinkedHashSet<>();
      if (preferredLoaders != null) {
         for (ClassLoader loader : preferredLoaders) {
            if (loader != null) {
               loaders.add(loader);
            }
         }
      }

      ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
      if (contextLoader != null) {
         loaders.add(contextLoader);
      }

      ClassLoader mappingLoader = VanillaSrgMappings.class.getClassLoader();
      if (mappingLoader != null) {
         loaders.add(mappingLoader);
      }

      try {
         ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
         if (systemLoader != null) {
            loaders.add(systemLoader);
         }
      } catch (SecurityException var6) {
      }

      return loaders;
   }

   private static String normalizeInternalName(String className) {
      if (className == null) {
         return null;
      } else {
         String normalized = className.trim().replace('.', '/');
         if (normalized.length() > 2 && normalized.charAt(0) == 'L' && normalized.charAt(normalized.length() - 1) == ';') {
            normalized = normalized.substring(1, normalized.length() - 1);
         }

         return normalized;
      }
   }

   private static String memberKey(String ownerName, String memberName) {
      return ownerName.replace('.', '/') + '/' + memberName;
   }

   private static String simpleMemberName(String qualifiedName) {
      int separator = qualifiedName.lastIndexOf(47);
      return separator < 0 ? qualifiedName : qualifiedName.substring(separator + 1);
   }

   private static final class MappingData {
      final Map<String, String> classNames;
      final Map<String, String> reverseClassNames;
      final Map<String, String> fieldNames;
      final Map<String, String> methodNames;

      MappingData(Map<String, String> classNames, Map<String, String> reverseClassNames, Map<String, String> fieldNames, Map<String, String> methodNames) {
         this.classNames = Collections.unmodifiableMap(classNames);
         this.reverseClassNames = Collections.unmodifiableMap(reverseClassNames);
         this.fieldNames = Collections.unmodifiableMap(fieldNames);
         this.methodNames = Collections.unmodifiableMap(methodNames);
      }
   }
}
