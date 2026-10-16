package gg.vape.asm.transform;

import gg.vape.Vape;
import gg.vape.asm.ITramsformNode;
import gg.vape.asm.helper.EventBuilder;
import gg.vape.asm.helper.MethodInfo;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingTask;
import gg.vape.runtime.ClassBytecodeCache;
import gg.vape.shaded.org.objectweb.asm.ClassReader;
import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.LaunchClassLoader;
import java.io.File;
import java.io.FileOutputStream;

public abstract class ClassTransformer implements MappingTask {
   protected byte[] originalBytecode;
   private static int transformerState;
   protected Class targetClass;
   private boolean commitBlocked;
   protected ClassNode classNode;
   private boolean applied;
   protected byte[] transformedBytecode;

   public static int opaquePredicate() {
      int state = getTransformerState();
      return state == 0 ? 11 : 0;
   }

   public static void setTransformerState(int state) {
      transformerState = state;
   }

   public void injectEventAtEntry(MappingMethod mappingMethod, Class eventClass, ITramsformNode... transformNodes) {
      EventBuilder eventBuilder = new EventBuilder(
         0,
         eventClass,
         this.classNode,
         new MethodInfo(mappingMethod.getResolvedName(), mappingMethod.getDescriptor()),
         mappingMethod.isStaticMethod(),
         transformNodes
      );
      eventBuilder.buildEventDispatchInstructions();
      eventBuilder.inject();
   }

   @Override
   public abstract void transform();

   public void writeBytesToFile(String filePath, byte[] bytecode) {
      int opaqueBranch = opaquePredicate();

      try {
         File file = new File(filePath);
         boolean parentExists = file.getParentFile().exists();
         if (opaqueBranch != 0 && !parentExists) {
            parentExists = file.getParentFile().mkdirs();
         }

         FileOutputStream output = new FileOutputStream(file);
         output.write(bytecode);
         output.close();
      } catch (Exception var7) {
         Vape.logThrowable(var7);
      }
   }

   @Override
   public void serialize() {
   }

   @Override
   public Class getTargetClass() {
      return this.targetClass;
   }

   public ClassTransformer(Class targetClass) {
      if (!Vape.INSTANCE.isForgeAbsent()) {
         LaunchClassLoader launchClassLoader = LaunchClassLoader.getLaunchClassLoader();
         if (launchClassLoader.supportsLegacyClassCache()) {
            launchClassLoader.cachedClasses().put(targetClass.getName(), targetClass);
         }
      }

      this.targetClass = targetClass;
   }

   public static int getTransformerState() {
      return transformerState;
   }

   private static Exception preserveException(Exception error) {
      return error;
   }

   @Override
   public boolean isApplied() {
      return this.applied;
   }

   @Override
   public int commit() {
      if (this.commitBlocked) {
         return -3;
      } else {
         this.serializeClassNode();
         int result = ClassBytecodeCache.setClassBytecode(this.targetClass, this.transformedBytecode);
         if (result == 0) {
            this.applied = true;
         }

         return result;
      }
   }

   public void injectEventAtExit(MappingMethod mappingMethod, Class eventClass, ITramsformNode... transformNodes) {
      EventBuilder eventBuilder = new EventBuilder(
         -1,
         eventClass,
         this.classNode,
         new MethodInfo(mappingMethod.getResolvedName(), mappingMethod.getDescriptor()),
         mappingMethod.isStaticMethod(),
         transformNodes
      );
      eventBuilder.buildEventDispatchInstructions();
      eventBuilder.inject();
   }

   @Override
   public void prepare() {
      this.originalBytecode = ClassBytecodeCache.getClassBytecode(this.targetClass, true);
      this.transformedBytecode = new byte[this.originalBytecode.length];
      System.arraycopy(this.originalBytecode, 0, this.transformedBytecode, 0, this.originalBytecode.length);
      ClassReader classReader = new ClassReader(this.originalBytecode);
      this.classNode = new ClassNode();
      classReader.accept(this.classNode, 0);
   }

   @Override
   public void rollback() {
      ClassBytecodeCache.setClassBytecode(this.targetClass, this.originalBytecode);
   }

   public void serializeClassNode() {
      int writerFlags = 3;
      if (ForgeVersion.MC_1_12_2.d()) {
         writerFlags = 2;
      }

      TransformClassWriter classWriter = new TransformClassWriter(writerFlags);
      this.classNode.accept(classWriter);
      this.transformedBytecode = classWriter.toByteArray();
   }

   static {
      if (opaquePredicate() == 0) {
         setTransformerState(92);
      }
   }
}
