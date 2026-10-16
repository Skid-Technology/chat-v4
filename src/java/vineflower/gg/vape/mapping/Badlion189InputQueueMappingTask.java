package gg.vape.mapping;

import gg.vape.asm.transform.ClassTransformer;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;

public final class Badlion189InputQueueMappingTask extends ClassTransformer {
   private static final String INPUT_METHOD = "runInputTick";
   private static final String CALLBACK_OWNER = "gg/vape/input/BadlionKeyBindingEventQueue";

   public Badlion189InputQueueMappingTask() {
      super(MappedClasses.uP);
   }

   @Override
   public void transform() {
      int callbackCount = installCallback(this.classNode);
      if (callbackCount != 1) {
         throw new IllegalStateException("Expected one Badlion input queue callback, found " + callbackCount);
      }
   }

   static int installCallback(ClassNode minecraftClass) {
      MethodNode inputMethod = null;

      for (MethodNode method : minecraftClass.methods) {
         if ("runInputTick".equals(method.name) && "()V".equals(method.desc)) {
            if (inputMethod != null) {
               throw new IllegalStateException("Multiple Badlion runInputTick methods are present");
            }

            inputMethod = method;
         }
      }

      if (inputMethod != null && inputMethod.instructions.getFirst() != null) {
         int callbackCount = countCallbacks(inputMethod);
         if (callbackCount == 0) {
            inputMethod.instructions
               .insertBefore(inputMethod.instructions.getFirst(), new MethodInsnNode(184, "gg/vape/input/BadlionKeyBindingEventQueue", "drain", "()V", false));
            callbackCount = countCallbacks(inputMethod);
         }

         return callbackCount;
      } else {
         throw new IllegalStateException("Badlion Minecraft.runInputTick() is unavailable");
      }
   }

   private static int countCallbacks(MethodNode method) {
      int count = 0;

      for (AbstractInsnNode instruction = method.instructions.getFirst(); instruction != null; instruction = instruction.getNext()) {
         if (instruction instanceof MethodInsnNode) {
            MethodInsnNode call = (MethodInsnNode)instruction;
            if (call.getOpcode() == 184
               && "gg/vape/input/BadlionKeyBindingEventQueue".equals(call.owner)
               && "drain".equals(call.name)
               && "()V".equals(call.desc)) {
               count++;
            }
         }
      }

      return count;
   }
}
