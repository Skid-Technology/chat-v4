package gg.vape.mapping;

import gg.vape.asm.transform.ClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class Badlion189Render2DMappingTask extends ClassTransformer {
   private static final String DISPLAY_CLASS = "org.lwjgl.opengl.Display";
   private static final String DISPLAY_OWNER = "org/lwjgl/opengl/Display";
   private static final String CALLBACK_OWNER = "gg/vape/event/impl/EventRender2D";

   public Badlion189Render2DMappingTask() {
      super(resolveDisplayClass());
   }

   @Override
   public void transform() {
      int callbackCount = installCallback(this.classNode);
      if (callbackCount != 1) {
         throw new IllegalStateException("Expected one Badlion display callback, found " + callbackCount);
      }
   }

   private static int installCallback(ClassNode displayClass) {
      MethodNode updateMethod = null;

      for (MethodNode method : displayClass.methods) {
         if ("update".equals(method.name) && "(Z)V".equals(method.desc)) {
            updateMethod = method;
            break;
         }
      }

      if (updateMethod == null) {
         throw new IllegalStateException("LWJGL Display.update(boolean) is unavailable");
      } else {
         int callbackCount = countCallbacks(updateMethod);
         if (callbackCount != 0) {
            return callbackCount;
         } else {
            for (AbstractInsnNode instruction = updateMethod.instructions.getFirst(); instruction != null; instruction = instruction.getNext()) {
               if (instruction instanceof MethodInsnNode) {
                  MethodInsnNode call = (MethodInsnNode)instruction;
                  if (call.getOpcode() == 184 && "org/lwjgl/opengl/Display".equals(call.owner) && "swapBuffers".equals(call.name) && "()V".equals(call.desc)) {
                     updateMethod.instructions.insertBefore(call, new MethodInsnNode(184, "gg/vape/event/impl/EventRender2D", "create", "()V", false));
                     return countCallbacks(updateMethod);
                  }
               }
            }

            throw new IllegalStateException("LWJGL Display.update(boolean) does not call swapBuffers()");
         }
      }
   }

   private static int countCallbacks(MethodNode method) {
      int count = 0;

      for (AbstractInsnNode instruction = method.instructions.getFirst(); instruction != null; instruction = instruction.getNext()) {
         if (instruction instanceof MethodInsnNode) {
            MethodInsnNode call = (MethodInsnNode)instruction;
            if (call.getOpcode() == 184 && "gg/vape/event/impl/EventRender2D".equals(call.owner) && "create".equals(call.name) && "()V".equals(call.desc)) {
               count++;
            }
         }
      }

      return count;
   }

   private static Class<?> resolveDisplayClass() {
      try {
         return Class.forName("org.lwjgl.opengl.Display", false, Badlion189Render2DMappingTask.class.getClassLoader());
      } catch (LinkageError | ClassNotFoundException var1) {
         throw new IllegalStateException("Unable to resolve Badlion's runtime LWJGL Display class", var1);
      }
   }
}
