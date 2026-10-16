/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.asm.matcher;

public abstract class InstructionPattern {
    final public static int PUTFIELD;
    private static String opaqueMarker;
    private String descriptor;
    private int opcode;
    private String owner;
    private String name;
    final public static int GETSTATIC;
    final public static int PUTSTATIC;
    final public static int INVOKESTATIC;
    final public static int INVOKEVIRTUAL;
    final public static int INVOKESPECIAL;
    final public static int GETFIELD;
    final public static int INVOKEINTERFACE;

    public static String getOpaqueMarker() {
        return opaqueMarker;
    }

    public InstructionPattern(int opcode, String owner, String name, String descriptor) {
        owner = owner.replace(".", "/");
        descriptor = descriptor.replace(".", "/");
        this.opcode = opcode;
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
    }

    public String getName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public static void setOpaqueMarker(String marker) {
        opaqueMarker = marker;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public boolean matches(InstructionPattern instructionPattern) {
        return !(this.opcode != instructionPattern.opcode || !this.owner.equals(instructionPattern.owner) && !this.owner.equals("*") || !this.name.equals(instructionPattern.name) && !this.name.equals("*") || !this.descriptor.equals(instructionPattern.descriptor) && !this.descriptor.equals("*"));
    }

    public String getOwner() {
        return this.owner;
    }

    static {
        if (InstructionPattern.getOpaqueMarker() != null) {
            InstructionPattern.setOpaqueMarker("jHaNE");
        }
        GETSTATIC = 178;
        PUTFIELD = 181;
        INVOKEINTERFACE = 185;
        INVOKEVIRTUAL = 182;
        INVOKESTATIC = 184;
        INVOKESPECIAL = 183;
        PUTSTATIC = 179;
        GETFIELD = 180;
    }
}

