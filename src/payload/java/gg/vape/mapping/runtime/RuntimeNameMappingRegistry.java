package gg.vape.mapping.runtime;

import gg.vape.Vape;
import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class RuntimeNameMappingRegistry {
   private static MemberNameRemapTable memberNameRemapTable;
   private static final Map<String, String> registeredClassNames = new LinkedHashMap<>();
   private static final ClassNameRemapTable classNameRemapTable;

   public static void registerClassName(String sourceClassName, String runtimeClassName) {
      registeredClassNames.put(sourceClassName.replace("/", "."), runtimeClassName.replace("/", "."));
      NativeBridge.scm(sourceClassName, runtimeClassName);
   }

   @Nullable
   public static MemberLookupSignature lookupMethodMapping(Class ownerClass, String methodName) {
      return memberNameRemapTable == null ? null : memberNameRemapTable.lookupMethodMapping(ownerClass, methodName);
   }

   @Nullable
   public static String lookupRegisteredClassName(Class<?> clazz) {
      return clazz == null ? null : registeredClassNames.get(clazz.getName());
   }

   @Nullable
   public static MemberLookupSignature lookupFieldMapping(Class ownerClass, String fieldName) {
      return memberNameRemapTable == null ? null : memberNameRemapTable.lookupFieldMapping(ownerClass, fieldName);
   }

   public static void initializeRegistry() {
      int forgeVersion = ForgeVersion.c();
      switch (forgeVersion) {
         case 35:
         case 36:
            memberNameRemapTable = new MemberNameRemapTableV35V36();
            break;
         case 37:
            memberNameRemapTable = new MemberNameRemapTableV37();
            break;
         case 50:
            memberNameRemapTable = new MemberNameRemapTableV50();
            break;
         case 51:
            memberNameRemapTable = new MemberNameRemapTableV51();
            break;
         case 54:
            memberNameRemapTable = new MemberNameRemapTableV54();
            break;
         case 55:
            memberNameRemapTable = new MemberNameRemapTableV55();
            break;
         case 56:
            memberNameRemapTable = new MemberNameRemapTableV56();
            break;
         case 60:
            memberNameRemapTable = new MemberNameRemapTableV60();
            break;
         case 61:
            memberNameRemapTable = new MemberNameRemapTableV61();
            break;
         case 100:
            memberNameRemapTable = new MemberNameRemapTableV100();
            break;
         case 110:
            memberNameRemapTable = new MemberNameRemapTableV110();
      }

      if (memberNameRemapTable != null) {
         memberNameRemapTable.initializeMappings();
      }
   }

   public static String remapClassName(String sourceClassName) {
      return classNameRemapTable == null ? null : classNameRemapTable.lookupRemappedClassName(sourceClassName);
   }

   static {
      int forgeVersion = ForgeVersion.c();
      switch (forgeVersion) {
         case 23:
            classNameRemapTable = new ClassNameRemapTableV23();
            break;
         case 35:
         case 36:
            if (Vape.INSTANCE.isForgeAbsent()) {
               classNameRemapTable = new ClassNameRemapTableV35V36Direct();
            } else {
               classNameRemapTable = new ClassNameRemapTableV35V36Layered();
            }
            break;
         case 37:
            classNameRemapTable = new ClassNameRemapTableV37();
            break;
         case 50:
            classNameRemapTable = new ClassNameRemapTableV50();
            break;
         case 51:
            classNameRemapTable = new ClassNameRemapTableV51();
            break;
         case 54:
            classNameRemapTable = new ClassNameRemapTableV54();
            break;
         case 55:
            classNameRemapTable = new ClassNameRemapTableV55();
            break;
         case 56:
            classNameRemapTable = new ClassNameRemapTableV56();
            break;
         case 60:
            classNameRemapTable = new ClassNameRemapTableV60();
            break;
         case 61:
            classNameRemapTable = new ClassNameRemapTableV61();
            break;
         case 100:
            classNameRemapTable = new ClassNameRemapTableV100();
            break;
         case 110:
            classNameRemapTable = new ClassNameRemapTableV110();
            break;
         default:
            classNameRemapTable = null;
      }

      if (ForgeVersion.MC_1_16_5_ACTUAL.Y() && !Vape.INSTANCE.isForgeAbsent()) {
         ClassNameRemapTable.propagateMappingsToRuntimeRegistry = true;
         new ClassNameRemapTableV35V36Direct();
      }
   }
}
