package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.access.GeneratedAccessorFactory;
import gg.vape.mapping.access.MethodInvoker;
import gg.vape.mapping.runtime.MemberLookupSignature;
import gg.vape.mapping.runtime.RuntimeNameMappingRegistry;
import gg.vape.wrapper.impl.ForgeVersion;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MappingMethod {
   private final String mappedDescriptor;
   private final Class<?> ownerClass;
   private static int nextMethodId = 0;
   private Constructor<?> reflectedConstructor;
   private final boolean mappedMember;
   private Class<?> resolvedReturnType;
   private Class<?> declaredReturnType;
   private final int methodId;
   private boolean resolutionFailed;
   private boolean skipAccessorGeneration;
   private final String descriptor;
   private final Class<?>[] declaredParameterTypes;
   private final String ownerInternalName;
   private Class<?>[] resolvedParameterTypes;
   private MethodInvoker generatedInvoker;
   private final boolean staticMethod;
   private Method reflectedMethod;
   private final String originalName;
   private boolean secondaryMember;
   private final String runtimeName;
   private static String controlFlowMarker;
   private final Mapping mappingOwner;

   public char[] invokeCharArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (char[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (char[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeCharArray(this.methodId, object, objectArray);
      }
   }

   public void initializeAccessor(Class<?> owner) {
      if ((Vape.INSTANCE.isMappingsRemapped() || Vape.INSTANCE.isForgeRemapInactive()) && !this.resolutionFailed) {
         if (this.runtimeName.equals("<init>") || this.runtimeName.equals("<clinit>")) {
            this.resolveConstructor(owner);
            return;
         }

         try {
            Class<? extends MethodInvoker> clazz2 = GeneratedAccessorFactory.A(owner, this);
            if (clazz2 == null) {
               return;
            }

            this.generatedInvoker = clazz2.newInstance();
         } catch (Throwable var3) {
         }
      }
   }

   public Object[] invokeObjectArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Object[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Object[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeObjectArray(this.methodId, object, objectArray);
      }
   }

   public void invokeVoid(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         this.generatedInvoker.invoke(object, objectArray);
      } else if (this.reflectedMethod != null) {
         this.invokeReflected(object, objectArray);
      } else {
         NativeMappedMemberInvoker.invokeVoid(this.methodId, object, objectArray);
      }
   }

   public static String getControlFlowMarker() {
      return controlFlowMarker;
   }

   public String getOriginalName() {
      return this.originalName;
   }

   public String getResolvedName() {
      return Vape.INSTANCE.isForgeRemapInactive() ? this.runtimeName : NativeMappedMemberInvoker.getMethodName(this.methodId);
   }

   public Class<?>[] getDeclaredParameterTypes() {
      return this.declaredParameterTypes;
   }

   public boolean[] invokeBooleanArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (boolean[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (boolean[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeBooleanArray(this.methodId, object, objectArray);
      }
   }

   public long[] invokeLongArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (long[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (long[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeLongArray(this.methodId, object, objectArray);
      }
   }

   public short invokeShort(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Short)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Short)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeShort(this.methodId, object, objectArray);
      }
   }

   public MappingMethod register() {
      this.resolutionFailed = false;

      try {
         if (Vape.INSTANCE.isForgeRemapInactive()) {
            this.resolveReflectionMember();
         } else if (this.mappedMember) {
            String string2 = this.runtimeName;
            if (!Vape.INSTANCE.isVanillaMinecraftPresent()) {
               string2 = string2 + ":" + this.descriptor;
            }

            NativeMappedMemberInvoker.registerMethodWithMetadata(
               this.methodId, this.ownerClass, this.ownerInternalName, this.runtimeName, this.descriptor, this.mappedDescriptor, this.staticMethod
            );
         } else {
            NativeMappedMemberInvoker.registerMethod(this.methodId, this.ownerClass, this.runtimeName, this.descriptor, this.staticMethod);
         }

         if (!this.skipAccessorGeneration && !Vape.INSTANCE.isForgeRemapInactive()) {
            this.initializeAccessor(this.ownerClass);
         }
      } catch (Throwable var2) {
         if (this.secondaryMember) {
            MappingProfileSnapshotRegistry.O();
         } else {
            MappingProfileSnapshotRegistry.X(this);
         }

         this.resolutionFailed = true;
      }

      return this;
   }

   public float[] invokeFloatArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (float[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (float[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeFloatArray(this.methodId, object, objectArray);
      }
   }

   public Class<?> getOwnerClass() {
      return this.ownerClass;
   }

   private void resolveReflectionMember() throws Throwable {
      if (!this.runtimeName.equals("<init>") && !this.runtimeName.equals("<clinit>")) {
         this.initializeAccessor(this.ownerClass);
         if (this.generatedInvoker == null) {
            this.reflectedMethod = this.findMethodInHierarchy(this.ownerClass, this.runtimeName, this.resolvedParameterTypes);
            if (this.reflectedMethod == null) {
               throw new NoSuchMethodException(this.ownerClass.getName() + "#" + this.runtimeName + this.descriptor);
            } else {
               this.reflectedMethod.setAccessible(true);
            }
         }
      } else {
         this.resolveConstructor(this.ownerClass);
         if (this.reflectedConstructor == null) {
            throw new NoSuchMethodException(this.ownerClass.getName() + "#" + this.runtimeName + this.descriptor);
         }
      }
   }

   public Mapping getMappingOwner() {
      return this.mappingOwner;
   }

   public Object newInstance(Object... objectArray) {
      if (this.reflectedConstructor != null) {
         try {
            return this.reflectedConstructor.newInstance(objectArray);
         } catch (Throwable var4) {
            Vape.logThrowable(var4);
         }
      }

      try {
         return NativeMappedMemberInvoker.invokeConstructor(this.methodId, this.ownerClass, objectArray);
      } catch (Exception var3) {
         return sneakyThrow(var3);
      }
   }

   public Class<?>[] getResolvedParameterTypes() {
      return this.resolvedParameterTypes;
   }

   public double[] invokeDoubleArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (double[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (double[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeDoubleArray(this.methodId, object, objectArray);
      }
   }

   public short[] invokeShortArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (short[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (short[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeShortArray(this.methodId, object, objectArray);
      }
   }

   public Object invokeNativeBridge(Object object, Object... objectArray) {
      return NativeMappedMemberInvoker.invokeNativeBridge(this.methodId, object, objectArray);
   }

   private static <E extends Throwable, T> T sneakyThrow(Throwable throwable) throws E {
      throw throwable;
   }

   private Object invokeReflected(Object instance, Object... arguments) {
      try {
         return this.reflectedMethod.invoke(instance, arguments);
      } catch (Throwable var4) {
         Vape.logThrowable(var4);
         return null;
      }
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public MappingMethod(
      Mapping mappingOwner,
      Class<?> ownerClass,
      String methodName,
      boolean mappedMember,
      boolean staticMethod,
      boolean secondaryMember,
      Class<?> returnType,
      Class<?>... parameterTypes
   ) {
      if (ForgeVersion.MC_26_1.d()) {
         mappedMember = false;
      }

      this.mappingOwner = mappingOwner;
      this.ownerClass = ownerClass;
      this.ownerInternalName = MappedClasses.b(ownerClass).replace(".", "/");
      this.originalName = methodName;
      this.declaredReturnType = returnType;
      this.declaredParameterTypes = parameterTypes;
      MemberLookupSignature memberLookupSignature = RuntimeNameMappingRegistry.lookupMethodMapping(ownerClass, methodName);
      if (memberLookupSignature != null) {
         this.runtimeName = memberLookupSignature.runtimeName;
         this.resolvedReturnType = memberLookupSignature.resolvedType != null ? memberLookupSignature.resolvedType : returnType;
         this.resolvedParameterTypes = memberLookupSignature.parameterTypes.length > 0 ? memberLookupSignature.parameterTypes : parameterTypes;
         this.mappedMember = memberLookupSignature.getMappedMemberOverride() != null ? memberLookupSignature.getMappedMemberOverride() : mappedMember;
      } else {
         this.runtimeName = methodName;
         this.resolvedReturnType = returnType;
         this.resolvedParameterTypes = parameterTypes;
         this.mappedMember = mappedMember;
      }

      this.staticMethod = staticMethod;
      this.secondaryMember = secondaryMember;
      this.methodId = ++nextMethodId;
      this.descriptor = DescUtils.getMethodDescriptor(false, this.resolvedReturnType, this.resolvedParameterTypes);
      this.mappedDescriptor = DescUtils.getMethodDescriptor(true, this.resolvedReturnType, this.resolvedParameterTypes);
   }

   public Class<?> getResolvedReturnType() {
      return this.resolvedReturnType;
   }

   public void resolveConstructor(Class<?> owner) {
      try {
         this.reflectedConstructor = owner.getDeclaredConstructor(this.getResolvedParameterTypes());
         this.reflectedConstructor.setAccessible(true);
      } catch (Throwable var3) {
         Vape.logThrowable(var3);
      }
   }

   public int getMethodId() {
      return this.methodId;
   }

   public Class<?> getDeclaredReturnType() {
      return this.declaredReturnType;
   }

   public byte invokeByte(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Byte)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Byte)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeByte(this.methodId, object, objectArray);
      }
   }

   public long invokeLong(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Long)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Long)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeLong(this.methodId, object, objectArray);
      }
   }

   public char invokeChar(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Character)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Character)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeChar(this.methodId, object, objectArray);
      }
   }

   public boolean isMappedMember() {
      return this.mappedMember;
   }

   public Object invokeObject(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeObject(this.methodId, object, objectArray);
      }
   }

   public boolean isStaticMethod() {
      return this.staticMethod;
   }

   public boolean invokeBoolean(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Boolean)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Boolean)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeBoolean(this.methodId, object, objectArray);
      }
   }

   public void invokeVoidNative(Object object, Object... objectArray) {
      NativeMappedMemberInvoker.invokeVoid(this.methodId, object, objectArray);
   }

   public static MappingMethod fromBuilder(MappingMethodBuilder mappingMethodBuilder) {
      MemberLookupSignature memberLookupSignature = RuntimeNameMappingRegistry.lookupMethodMapping(
         mappingMethodBuilder.getOwnerClass(), mappingMethodBuilder.getMemberName()
      );
      if (memberLookupSignature != null) {
         mappingMethodBuilder.setMemberName(memberLookupSignature.runtimeName);
         if (memberLookupSignature.resolvedType != null) {
            mappingMethodBuilder.setType(memberLookupSignature.resolvedType);
         }

         if (memberLookupSignature.parameterTypes.length > 0) {
            mappingMethodBuilder.setParameterTypes(memberLookupSignature.parameterTypes);
         }

         if (memberLookupSignature.getMappedMemberOverride() != null) {
            mappingMethodBuilder.setMappedMember(memberLookupSignature.getMappedMemberOverride());
         }
      }

      MappingMethod mappingMethod = new MappingMethod(
         mappingMethodBuilder.getMappingOwner(),
         mappingMethodBuilder.getOwnerClass(),
         mappingMethodBuilder.getMemberName(),
         mappingMethodBuilder.isMappedMember(),
         mappingMethodBuilder.isStaticMember(),
         mappingMethodBuilder.isSecondaryMember(),
         mappingMethodBuilder.getType(),
         mappingMethodBuilder.getParameterTypes()
      );
      if (mappingMethodBuilder.isSkipAccessorGeneration()) {
         mappingMethod.skipAccessorGeneration = true;
      }

      return mappingMethod.register();
   }

   public void invokeVoidNoArgs(Object object) {
      if (this.generatedInvoker != null) {
         this.generatedInvoker.invoke(object);
      } else if (this.reflectedMethod != null) {
         this.invokeReflected(object);
      } else {
         NativeMappedMemberInvoker.invokeVoid(this.methodId, object);
      }
   }

   public double invokeDouble(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Double)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Double)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeDouble(this.methodId, object, objectArray);
      }
   }

   public int invokeInt(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Integer)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Integer)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeInt(this.methodId, object, objectArray);
      }
   }

   public boolean hasResolutionFailed() {
      return this.resolutionFailed;
   }

   private Method findMethodInHierarchy(Class<?> owner, String name, Class<?>[] parameterTypes) {
      try {
         return owner.getMethod(name, parameterTypes);
      } catch (NoSuchMethodException var8) {
         for (Class<?> currentClass = owner; currentClass != null; currentClass = currentClass.getSuperclass()) {
            try {
               return currentClass.getDeclaredMethod(name, parameterTypes);
            } catch (NoSuchMethodException var7) {
            }
         }

         return null;
      }
   }

   public String getRuntimeName() {
      return this.runtimeName;
   }

   public static void setControlFlowMarker(String marker) {
      controlFlowMarker = marker;
   }

   public float invokeFloat(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (Float)this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (Float)this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeFloat(this.methodId, object, objectArray);
      }
   }

   public int[] invokeIntArray(Object object, Object... objectArray) {
      if (this.generatedInvoker != null) {
         return (int[])this.generatedInvoker.invoke(object, objectArray);
      } else {
         return this.reflectedMethod != null
            ? (int[])this.invokeReflected(object, objectArray)
            : NativeMappedMemberInvoker.invokeIntArray(this.methodId, object, objectArray);
      }
   }

   static {
      setControlFlowMarker("YeDWk");
   }
}
