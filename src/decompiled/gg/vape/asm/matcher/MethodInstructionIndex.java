/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.asm.matcher;

import gg.vape.asm.matcher.FieldInsnPattern;
import gg.vape.asm.matcher.InstructionPattern;
import gg.vape.asm.matcher.MethodInsnPattern;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.FieldInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.InsnList;
import gg.vape.shaded.org.objectweb.asm.tree.MethodInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import java.util.ArrayList;
import java.util.List;

public class MethodInstructionIndex {
    private List<InstructionPattern> patterns = new ArrayList<InstructionPattern>();
    private MethodNode methodNode;
    private static String[] opaqueStringSlots;

    public void buildIndex() {
        InsnList instructions = this.methodNode.instructions;
        for (AbstractInsnNode instruction : instructions) {
            this.addInstruction(instruction);
        }
    }

    public static String[] getOpaqueStringSlots() {
        return opaqueStringSlots;
    }

    public List<InstructionPattern> getPatterns() {
        return this.patterns;
    }

    public static void setOpaqueStringSlots(String[] slots) {
        opaqueStringSlots = slots;
    }

    public MethodInstructionIndex(MethodNode methodNode) {
        this.methodNode = methodNode;
    }

    private void addInstruction(AbstractInsnNode instruction) {
        if (instruction instanceof MethodInsnNode) {
            this.patterns.add(MethodInsnPattern.fromNode((MethodInsnNode)instruction));
        }
        if (instruction instanceof FieldInsnNode) {
            this.patterns.add(FieldInsnPattern.fromNode((FieldInsnNode)instruction));
        }
    }

    static {
        if (MethodInstructionIndex.getOpaqueStringSlots() == null) {
            MethodInstructionIndex.setOpaqueStringSlots(new String[2]);
        }
    }
}

