package gg.vape.mapping.runtime;

import gg.vape.Vape;
import gg.vape.wrapper.impl.ForgeVersion;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class MemberNameRemapTable {
   private final HashMap<Class<?>, Map<String, MemberLookupSignature>> fieldMappings = new HashMap<>();
   private static String[] controlFlowState;
   private final HashMap<Class<?>, Map<String, MemberLookupSignature>> methodMappings = new HashMap<>();
   private static final String initializerMethodName = "initialize";

   public static String[] getControlFlowState() {
      return controlFlowState;
   }

   public void G(Class<?> ownerClass, String sourceName, String runtimeName, Class<?> resolvedType) {
      this.registerFieldMapping(ownerClass, sourceName, runtimeName, null, resolvedType);
   }

   public void f(Class<?> ownerClass, String sourceName, String runtimeName, Class<?> returnType, Class<?>... parameterTypes) {
      this.registerMethodMapping(ownerClass, sourceName, runtimeName, null, returnType, parameterTypes);
   }

   public void registerFieldMapping(Class<?> ownerClass, String sourceName, String runtimeName, Boolean mappedMemberOverride, Class<?> resolvedType) {
      String resolvedRuntimeName = resolveRuntimeName(sourceName, runtimeName);
      this.fieldMappings
         .compute(
            ownerClass,
            (ignoredOwner, existingMappings) -> lambda$setFieldMapping$0(
               sourceName, resolvedRuntimeName, mappedMemberOverride, resolvedType, ignoredOwner, existingMappings
            )
         );
   }

   public void f(Class<?> ownerClass, String sourceName, String runtimeName, boolean mappedMemberOverride) {
      this.registerFieldMapping(ownerClass, sourceName, runtimeName, mappedMemberOverride, null);
   }

   public boolean isVanillaMinecraftAbsent() {
      return !Vape.INSTANCE.isVanillaMinecraftPresent();
   }

   private static Exception identityException(Exception exception) {
      return exception;
   }

   public void registerMethodMapping(
      Class<?> ownerClass, String sourceName, String runtimeName, Boolean mappedMemberOverride, Class<?> returnType, Class<?>... parameterTypes
   ) {
      String resolvedRuntimeName = resolveRuntimeName(sourceName, runtimeName);
      this.methodMappings
         .compute(
            ownerClass,
            (ignoredOwner, existingMappings) -> lambda$setMethodMapping$1(
               sourceName, resolvedRuntimeName, mappedMemberOverride, returnType, parameterTypes, ignoredOwner, existingMappings
            )
         );
   }

   private static String resolveRuntimeName(String sourceName, String runtimeName) {
      int minorVersion = ForgeVersion.c();
      return (minorVersion == 35 || minorVersion == 36) && !runtimeName.startsWith("func_") && !runtimeName.startsWith("field_") ? sourceName : runtimeName;
   }

   @Nullable
   public MemberLookupSignature lookupFieldMapping(Class<?> ownerClass, String fieldName) {
      Map<String, MemberLookupSignature> mappings = this.fieldMappings.get(ownerClass);
      return mappings == null ? null : mappings.getOrDefault(fieldName, null);
   }

   public void B(Class<?> ownerClass, String sourceName, String runtimeName) {
      this.registerFieldMapping(ownerClass, sourceName, runtimeName, null, null);
   }

   public void initializeMappings() {
      ArrayList<Class<?>> tableHierarchy = new ArrayList<>();

      for (Class<?> tableClass = this.getClass();
         tableClass != null && MemberNameRemapTable.class.isAssignableFrom(tableClass) && tableClass != MemberNameRemapTable.class;
         tableClass = tableClass.getSuperclass()
      ) {
         tableHierarchy.add(tableClass);
      }

      Collections.reverse(tableHierarchy);

      for (Class<?> tableClass : tableHierarchy) {
         for (Method method : tableClass.getDeclaredMethods()) {
            if (method.getParameterCount() == 0 && !initializerMethodName.equals(method.getName())) {
               method.setAccessible(true);

               try {
                  method.invoke(this);
               } catch (Exception var10) {
                  Vape.logThrowable(var10);
               }
            }
         }
      }
   }

   @Nullable
   public MemberLookupSignature lookupMethodMapping(Class<?> ownerClass, String methodName) {
      Map<String, MemberLookupSignature> mappings = this.methodMappings.get(ownerClass);
      return mappings == null ? null : mappings.getOrDefault(methodName, null);
   }

   public static void setControlFlowState(String[] state) {
      controlFlowState = state;
   }

   private static Map lambda$setMethodMapping$1(
      String sourceName, String runtimeName, Boolean mappedMemberOverride, Class returnType, Class[] parameterTypes, Class ignoredOwner, Map mappings
   ) {
      if (mappings == null) {
         mappings = new HashMap();
      }

      mappings.put(sourceName, new MemberLookupSignature(runtimeName, mappedMemberOverride, returnType, parameterTypes));
      return mappings;
   }

   public void t(Class<?> ownerClass, String sourceName, String runtimeName) {
      this.f(ownerClass, sourceName, runtimeName, null);
   }

   public void b(Class<?> ownerClass, String sourceName, String runtimeName, boolean mappedMemberOverride) {
      this.registerMethodMapping(ownerClass, sourceName, runtimeName, mappedMemberOverride, null);
   }

   private static Map lambda$setFieldMapping$0(
      String sourceName, String runtimeName, Boolean mappedMemberOverride, Class resolvedType, Class ignoredOwner, Map mappings
   ) {
      if (mappings == null) {
         mappings = new HashMap();
      }

      mappings.put(sourceName, new MemberLookupSignature(runtimeName, mappedMemberOverride, resolvedType));
      return mappings;
   }

   static {
      setControlFlowState(null);
   }
}
