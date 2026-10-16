package gg.vape.asm.helper;

import gg.vape.Vape;
import gg.vape.asm.ITramsformNode;
import gg.vape.event.EventBus;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.InsnList;
import gg.vape.shaded.org.objectweb.asm.tree.InsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.JumpInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.LabelNode;
import gg.vape.shaded.org.objectweb.asm.tree.LineNumberNode;
import gg.vape.shaded.org.objectweb.asm.tree.LocalVariableNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import gg.vape.shaded.org.objectweb.asm.tree.TypeInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.VarInsnNode;
import gg.vape.wrapper.impl.LaunchClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class EventBuilder {
   private int injectionPoint;
   private LabelNode eventStartLabel;
   private List<ITramsformNode> transformNodes = new ArrayList<>();
   private InsnList injectionInstructions;
   private MethodNode targetMethod;
   private Class eventClass;
   private boolean staticMethod;

   public static int getStoreOpcode(String descriptor) {
      switch (descriptor) {
         case "D":
            return 57;
         case "I":
            return 21;
         case "F":
            return 56;
         case "Z":
            return 54;
         case "J":
            return 55;
         default:
            return 58;
      }
   }

   public EventBuilder inject() {
      if (this.injectionPoint == 0) {
         this.targetMethod.instructions.insert(this.injectionInstructions);
         return this;
      } else {
         if (this.injectionPoint == -1) {
            ListIterator<AbstractInsnNode> iterator = this.targetMethod.instructions.iterator();

            while (iterator.hasNext()) {
               iterator.next();
               if (!iterator.hasNext()) {
                  while (iterator.hasPrevious()) {
                     AbstractInsnNode instruction = iterator.previous();
                     if (instruction instanceof LineNumberNode) {
                        this.targetMethod.instructions.insert(instruction, this.injectionInstructions);
                        return this;
                     }
                  }
               }
            }
         } else {
            for (AbstractInsnNode instruction : this.targetMethod.instructions) {
               if (instruction instanceof LineNumberNode) {
                  LineNumberNode lineNumber = (LineNumberNode)instruction;
                  if (lineNumber.line == this.injectionPoint) {
                     this.targetMethod.instructions.insert(lineNumber, this.injectionInstructions);
                     return this;
                  }
               }
            }
         }

         return this;
      }
   }

   public static List<String> parseArgumentDescriptors(String methodDescriptor) {
      ArrayList<String> descriptors = new ArrayList<>();
      char[] characters = methodDescriptor.toCharArray();

      for (int index = 0; index < characters.length; index++) {
         char descriptorChar = characters[index];
         if (descriptorChar != '(') {
            if (descriptorChar == ')') {
               break;
            }

            if (descriptorChar == 'L') {
               String objectDescriptor = "";

               while ((descriptorChar = characters[index++]) != ';') {
                  objectDescriptor = objectDescriptor + descriptorChar;
               }

               index--;
               descriptors.add(objectDescriptor);
            } else {
               descriptors.add(Character.toString(descriptorChar));
            }
         }
      }

      return descriptors;
   }

   public static int getLoadOpcode(String descriptor) {
      switch (descriptor) {
         case "D":
            return 24;
         case "I":
            return 21;
         case "F":
            return 23;
         case "Z":
            return 21;
         case "J":
            return 22;
         default:
            return 25;
      }
   }

   public static int getReturnOpcode(String methodDescriptor) {
      int returnTypeIndex = methodDescriptor.indexOf(")") + 1;
      String var2 = methodDescriptor.substring(returnTypeIndex, returnTypeIndex + 1);
      switch (var2) {
         case "I":
         case "Z":
            return 172;
         case "F":
            return 174;
         case "D":
            return 175;
         case "V":
            return 177;
         case "J":
            return 173;
         default:
            return 176;
      }
   }

   public static String getReturnDescriptor(String methodDescriptor) {
      int returnTypeIndex = methodDescriptor.indexOf(")") + 1;
      return methodDescriptor.substring(returnTypeIndex, returnTypeIndex + 1);
   }

   public void buildEventDispatchInstructions() {
      String eventInternalName = this.eventClass.getName().replace('.', '/');
      LabelNode continueLabel = new LabelNode(new Label());
      int eventLocalIndex = this.targetMethod.maxLocals++;
      LocalVariableNode eventLocal = new LocalVariableNode("event", "L" + eventInternalName + ";", null, this.eventStartLabel, continueLabel, eventLocalIndex);
      this.targetMethod.localVariables.add(eventLocal);
      InsnList instructions = new InsnList();
      instructions.add(this.eventStartLabel);
      if (!this.staticMethod) {
         for (LocalVariableNode localVariable : this.targetMethod.localVariables) {
            if (localVariable.index == 0) {
               localVariable.start = this.eventStartLabel;
            }
         }
      }

      instructions.add(new TypeInsnNode(187, eventInternalName));
      instructions.add(new InsnNode(89));
      StringBuilder constructorDescriptor = new StringBuilder("(");

      for (ITramsformNode transformNode : this.transformNodes) {
         instructions.add(transformNode.getLoadInstructions());
         constructorDescriptor.append(transformNode.getDescriptor());
      }

      constructorDescriptor.append(")V");
      instructions.add(new MethodInsnNode(183, eventInternalName, "<init>", constructorDescriptor.toString(), false));
      instructions.add(new VarInsnNode(58, eventLocalIndex));
      instructions.add(new VarInsnNode(25, eventLocalIndex));
      instructions.add(new MethodInsnNode(182, eventInternalName, EventBus.getFireMethod(this.eventClass).getName(), "()Z", false));
      instructions.add(new JumpInsnNode(153, continueLabel));
      instructions.add(new InsnNode(getReturnOpcode(this.targetMethod.desc)));
      instructions.add(continueLabel);
      this.injectionInstructions = instructions;
   }

   public EventBuilder(int injectionPoint, Class eventClass, ClassNode classNode, MethodInfo methodInfo, boolean staticMethod, ITramsformNode... transformNodes) {
      this.eventStartLabel = new LabelNode(new Label());
      if (!Vape.INSTANCE.isForgeAbsent()) {
         LaunchClassLoader launchClassLoader = LaunchClassLoader.getLaunchClassLoader();
         if (launchClassLoader.supportsLegacyClassCache()) {
            launchClassLoader.cachedClasses().put(eventClass.getName(), eventClass);
         }
      }

      for (MethodNode candidate : classNode.methods) {
         if (methodInfo.matches(candidate.name, candidate.desc)) {
            this.targetMethod = candidate;
         }
      }

      if (this.targetMethod == null) {
         Vape.debugLog("Couldnt find method node");
      }

      this.injectionPoint = injectionPoint;
      this.eventClass = eventClass;

      for (ITramsformNode transformNode : transformNodes) {
         transformNode.prepare(classNode, this.targetMethod);
         this.transformNodes.add(transformNode);
      }

      this.staticMethod = staticMethod;
   }
}
