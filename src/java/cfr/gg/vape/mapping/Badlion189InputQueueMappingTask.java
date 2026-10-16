/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.asm.transform.ClassTransformer;
import gg.vape.mapping.MappedClasses;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;

public final class Badlion189InputQueueMappingTask
extends ClassTransformer {
    private static final String INPUT_METHOD = "runInputTick";
    private static final String CALLBACK_OWNER = "gg/vape/input/BadlionKeyBindingEventQueue";

    public Badlion189InputQueueMappingTask() {
        super(MappedClasses.uP);
    }

    @Override
    public void transform() {
        int callbackCount = Badlion189InputQueueMappingTask.installCallback(this.classNode);
        if (callbackCount != 1) {
            throw new IllegalStateException("Expected one Badlion input queue callback, found " + callbackCount);
        }
    }

    static int installCallback(ClassNode minecraftClass) {
        MethodNode inputMethod = null;
        for (MethodNode method : minecraftClass.methods) {
            if (!INPUT_METHOD.equals(method.name) || !"()V".equals(method.desc)) continue;
            if (inputMethod != null) {
                throw new IllegalStateException("Multiple Badlion runInputTick methods are present");
            }
            inputMethod = method;
        }
        if (inputMethod == null || inputMethod.instructions.getFirst() == null) {
            throw new IllegalStateException("Badlion Minecraft.runInputTick() is unavailable");
        }
        int callbackCount = Badlion189InputQueueMappingTask.countCallbacks(inputMethod);
        if (callbackCount == 0) {
            inputMethod.instructions.insertBefore(inputMethod.instructions.getFirst(), new MethodInsnNode(184, CALLBACK_OWNER, "drain", "()V", false));
            callbackCount = Badlion189InputQueueMappingTask.countCallbacks(inputMethod);
        }
        return callbackCount;
    }

    private static int countCallbacks(MethodNode method) {
        int count = 0;
        for (AbstractInsnNode instruction = method.instructions.getFirst(); instruction != null; instruction = instruction.getNext()) {
            MethodInsnNode call;
            if (!(instruction instanceof MethodInsnNode) || (call = (MethodInsnNode)instruction).getOpcode() != 184 || !CALLBACK_OWNER.equals(call.owner) || !"drain".equals(call.name) || !"()V".equals(call.desc)) continue;
            ++count;
        }
        return count;
    }
}

