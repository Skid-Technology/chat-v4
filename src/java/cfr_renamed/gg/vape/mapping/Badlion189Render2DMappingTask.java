/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.asm.transform.ClassTransformer;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;

public final class Badlion189Render2DMappingTask
extends ClassTransformer {
    private final static String DISPLAY_CLASS = "org.lwjgl.opengl.Display";
    private final static String DISPLAY_OWNER = "org/lwjgl/opengl/Display";
    private final static String CALLBACK_OWNER = "gg/vape/event/impl/EventRender2D";

    public Badlion189Render2DMappingTask() {
        super(Badlion189Render2DMappingTask.resolveDisplayClass());
    }

    @Override
    public void transform() {
        int callbackCount = Badlion189Render2DMappingTask.installCallback(this.classNode);
        if (callbackCount != 1) {
            throw new IllegalStateException("Expected one Badlion display callback, found " + callbackCount);
        }
    }

    private static int installCallback(ClassNode displayClass) {
        MethodNode updateMethod = null;
        for (MethodNode method : displayClass.methods) {
            if (!"update".equals(method.name) || !"(Z)V".equals(method.desc)) continue;
            updateMethod = method;
            break;
        }
        if (updateMethod == null) {
            throw new IllegalStateException("LWJGL Display.update(boolean) is unavailable");
        }
        int callbackCount = Badlion189Render2DMappingTask.countCallbacks(updateMethod);
        if (callbackCount != 0) {
            return callbackCount;
        }
        for (AbstractInsnNode instruction = updateMethod.instructions.getFirst(); instruction != null; instruction = instruction.getNext()) {
            MethodInsnNode call;
            if (!(instruction instanceof MethodInsnNode) || (call = (MethodInsnNode)instruction).getOpcode() != 184 || !DISPLAY_OWNER.equals(call.owner) || !"swapBuffers".equals(call.name) || !"()V".equals(call.desc)) continue;
            updateMethod.instructions.insertBefore((AbstractInsnNode)call, new MethodInsnNode(184, CALLBACK_OWNER, "create", "()V", false));
            return Badlion189Render2DMappingTask.countCallbacks(updateMethod);
        }
        throw new IllegalStateException("LWJGL Display.update(boolean) does not call swapBuffers()");
    }

    private static int countCallbacks(MethodNode method) {
        int count = 0;
        for (AbstractInsnNode instruction = method.instructions.getFirst(); instruction != null; instruction = instruction.getNext()) {
            MethodInsnNode call;
            if (!(instruction instanceof MethodInsnNode) || (call = (MethodInsnNode)instruction).getOpcode() != 184 || !CALLBACK_OWNER.equals(call.owner) || !"create".equals(call.name) || !"()V".equals(call.desc)) continue;
            ++count;
        }
        return count;
    }

    private static Class<?> resolveDisplayClass() {
        try {
            return Class.forName(DISPLAY_CLASS, false, Badlion189Render2DMappingTask.class.getClassLoader());
        }
        catch (ClassNotFoundException | LinkageError error) {
            throw new IllegalStateException("Unable to resolve Badlion's runtime LWJGL Display class", error);
        }
    }
}

