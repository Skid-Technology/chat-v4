package gg.vape.asm.helper;

import gg.vape.asm.ITramsformNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import java.util.ListIterator;

public class Local implements ITramsformNode {
   private String descriptorOverride = null;
   protected VarInsnNode storeInstruction;
   protected VarInsnNode loadInstruction;
   protected String resolvedDescriptor;
   private String localName;
   protected InsnList storeInstructions;
   private boolean ownerSet;
   private static boolean opaqueState;
   private String ownerInternalName;
   protected InsnList loadInstructions = new InsnList();
   protected LocalVariableNode localVariable;

   public Local(String localName) {
      this.storeInstructions = new InsnList();
      this.localName = localName;
   }

   public static boolean opaquePredicate() {
      boolean state = getOpaqueState();
      return false;
   }

   @Override
   public ITramsformNode setOwner(Class ownerClass) {
      this.ownerSet = true;
      this.ownerInternalName = ownerClass.getName().replace(".", "/");
      return this;
   }

   void setLocalVariableStart(LabelNode startLabel) {
      if (this.localVariable != null) {
         this.localVariable.start = startLabel;
      }
   }

   @Override
   public InsnList getLoadInstructions() {
      return this.loadInstructions;
   }

   @Override
   public String getDescriptor() {
      return this.descriptorOverride != null ? this.descriptorOverride : this.resolvedDescriptor;
   }

   @Override
   public boolean hasOwner() {
      return this.ownerSet;
   }

   public static boolean getOpaqueState() {
      return opaqueState;
   }

   public Local setDescriptorClass(Class descriptorClass) {
      this.descriptorOverride = DescUtils.getDescriptor(descriptorClass);
      return this;
   }

   @Override
   public String getOwnerInternalName() {
      return this.ownerInternalName;
   }

   public static void setOpaqueState(boolean state) {
      opaqueState = state;
   }

   @Override
   public InsnList getStoreInstructions() {
      return this.storeInstructions;
   }

   @Override
   public void prepare(ClassNode classNode, MethodNode methodNode) {
      if (this.localName.equals("this") && methodNode.localVariables.size() == 0) {
         String descriptor = "L" + classNode.name + ";";
         LabelNode startLabel = null;
         LabelNode endLabel = null;
         ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();

         label48:
         while (iterator.hasNext()) {
            AbstractInsnNode instruction = iterator.next();
            if (instruction instanceof LabelNode) {
               startLabel = (LabelNode)instruction;

               while (iterator.hasNext()) {
                  iterator.next();
                  if (!iterator.hasNext()) {
                     while (iterator.hasPrevious()) {
                        instruction = iterator.previous();
                        if (instruction instanceof LabelNode) {
                           endLabel = (LabelNode)instruction;
                           break label48;
                        }
                     }
                  }
               }
            }
         }

         LocalVariableNode localVariable = new LocalVariableNode("this", descriptor, null, startLabel, endLabel, 0);
         methodNode.localVariables.add(localVariable);
         this.localVariable = localVariable;
         this.loadInstruction = new VarInsnNode(25, 0);
         this.loadInstructions.add(this.loadInstruction);
         this.storeInstruction = new VarInsnNode(58, 0);
         this.storeInstructions.add(this.storeInstruction);
         this.resolvedDescriptor = descriptor;
      } else {
         for (LocalVariableNode candidate : methodNode.localVariables) {
            if (candidate.name.equals(this.localName)) {
               this.localVariable = candidate;
               this.loadInstruction = new VarInsnNode(EventBuilder.getLoadOpcode(candidate.desc), candidate.index);
               this.loadInstructions.add(this.loadInstruction);
               this.storeInstruction = new VarInsnNode(EventBuilder.getStoreOpcode(candidate.desc), candidate.index);
               this.storeInstructions.add(this.storeInstruction);
               this.resolvedDescriptor = this.localVariable.desc;
            }
         }
      }
   }

   static {
      setOpaqueState(true);
   }
}
