package gg.vape.reflect;

import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MappingRegistry {
   public static final Map<String, Set<String>> METHODS = new HashMap<>();
   public static final Map<String, Set<String>> METHODS_REVERSED = new HashMap<>();
   public static final Map<String, Set<String>> FIELDS = new HashMap<>();
   public static final Map<String, Set<String>> FIELDS_REVERSED = new HashMap<>();
   private static final String RESOURCE_DIRECTORY = getResourceDirectory();
   private static final String METHODS_RESOURCE = RESOURCE_DIRECTORY + "/methods.csv";
   private static final String FIELDS_RESOURCE = RESOURCE_DIRECTORY + "/fields.csv";

   private static int getVanillaMappingVersion() {
      return NativeBridge.isForgeAbsent() ? ForgeVersion.c() : 0;
   }

   private static boolean isFabric12111Runtime() {
      int version = getVanillaMappingVersion();
      return (version == 54 || version == 55 || version == 56 || version == 60 || version == 61) && Fabric12111Mappings.isRuntimePresent();
   }

   private MappingRegistry() {
   }

   private static String getResourceDirectory() {
      switch (ForgeVersion.c()) {
         case 13:
            return "mappings/forge1710";
         case 15:
            return "mappings/forge189";
         case 23:
            return "mappings/forge1122";
         case 35:
            return "mappings/forge1164";
         case 36:
            return "mappings/forge1165";
         case 50:
            return "mappings/forge1206";
         case 54:
         case 55:
         case 56:
         case 60:
         case 61:
         case 100:
         case 110:
            return "mappings/forge12111";
         default:
            return "mappings/forge189";
      }
   }

   public static void parseCsv(InputStream stream, Map<String, Set<String>> namesToSrg, Map<String, Set<String>> srgToNames) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

      String line;
      try {
         while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            String srgName = columns[0];
            String readableName = columns[1];
            namesToSrg.computeIfAbsent(readableName, ignored -> new HashSet<>()).add(srgName);
            srgToNames.computeIfAbsent(srgName, ignored -> new HashSet<>()).add(readableName);
         }
      } catch (Throwable var9) {
         try {
            reader.close();
         } catch (Throwable var8) {
            var9.addSuppressed(var8);
         }

         throw var9;
      }

      reader.close();
   }

   public static boolean matches(Method method, String requestedName) {
      if (matches(method.getName(), requestedName, METHODS, METHODS_REVERSED)) {
         return true;
      } else {
         String srgName = lookupVanillaMethodSrgName(method);
         return srgName != null && matches(srgName, requestedName, METHODS, METHODS_REVERSED);
      }
   }

   public static boolean matches(Field field, String requestedName) {
      if (matches(field.getName(), requestedName, FIELDS, FIELDS_REVERSED)) {
         return true;
      } else {
         String srgName = lookupVanillaFieldSrgName(field);
         return srgName != null && matches(srgName, requestedName, FIELDS, FIELDS_REVERSED);
      }
   }

   private static boolean matches(String actualName, String requestedName, Map<String, Set<String>> namesToSrg, Map<String, Set<String>> srgToNames) {
      return actualName.equals(requestedName)
         || namesToSrg.getOrDefault(requestedName, Collections.emptySet()).contains(actualName)
         || srgToNames.getOrDefault(requestedName, Collections.emptySet()).contains(actualName);
   }

   private static String lookupVanillaMethodSrgName(Method method) {
      int version = getVanillaMappingVersion();
      if (version == 13) {
         return Vanilla1710Mappings.lookupMethodSrgName(method);
      } else if (version == 15) {
         return Vanilla189Mappings.lookupMethodSrgName(method);
      } else if (version == 23) {
         return Vanilla1122Mappings.lookupMethodSrgName(method);
      } else if (version == 35 || version == 36) {
         return Vanilla1165Mappings.lookupMethodSrgName(method);
      } else if (version == 50) {
         return Vanilla1206Mappings.lookupMethodSrgName(method);
      } else if (version != 54 && version != 55 && version != 56 && version != 60 && version != 61) {
         return null;
      } else {
         return isFabric12111Runtime() ? Fabric12111Mappings.lookupMethodSrgName(method) : Vanilla12111Mappings.lookupMethodSrgName(method);
      }
   }

   private static String lookupVanillaFieldSrgName(Field field) {
      int version = getVanillaMappingVersion();
      if (version == 13) {
         return Vanilla1710Mappings.lookupFieldSrgName(field);
      } else if (version == 15) {
         return Vanilla189Mappings.lookupFieldSrgName(field);
      } else if (version == 23) {
         return Vanilla1122Mappings.lookupFieldSrgName(field);
      } else if (version == 35 || version == 36) {
         return Vanilla1165Mappings.lookupFieldSrgName(field);
      } else if (version == 50) {
         return Vanilla1206Mappings.lookupFieldSrgName(field);
      } else if (version != 54 && version != 55 && version != 56 && version != 60 && version != 61) {
         return null;
      } else {
         return isFabric12111Runtime() ? Fabric12111Mappings.lookupFieldSrgName(field) : Vanilla12111Mappings.lookupFieldSrgName(field);
      }
   }

   private static InputStream openRequiredResource(String resourcePath) throws IOException {
      InputStream stream = MappingRegistry.class.getResourceAsStream('/' + resourcePath);
      if (stream == null) {
         throw new FileNotFoundException("Missing mapping resource: " + resourcePath);
      } else {
         return stream;
      }
   }

   static {
      try {
         parseCsv(openRequiredResource(METHODS_RESOURCE), METHODS, METHODS_REVERSED);
         parseCsv(openRequiredResource(FIELDS_RESOURCE), FIELDS, FIELDS_REVERSED);
      } catch (Throwable var1) {
         var1.printStackTrace();
         throw new RuntimeException();
      }
   }
}
