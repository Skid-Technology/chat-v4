/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public enum PrimitiveTopology {
    LINES(2, 2, "line"),
    LINES_LOOP(2, 2, "line"),
    QUADS(6, 4, "quad"),
    TRIANGLES(3, 3, "triangle");

    final public String name;
    final public int verticesCount;
    private final static PrimitiveTopology[] DECLARED_VALUES;
    final public int indicesCount;

    private PrimitiveTopology(int indicesCount, int verticesCount, String name) {
        this.indicesCount = indicesCount;
        this.verticesCount = verticesCount;
        this.name = name;
    }

    public int getOpenGlMode() {
        switch (this) {
            case LINES: {
                return 1;
            }
            case QUADS: 
            case TRIANGLES: {
                return 4;
            }
            case LINES_LOOP: {
                return 2;
            }
        }
        return -1;
    }

    static {
        String[] legacyNames = new String[]{"quad", "line", "line", "QUADS", "TRIANGLES", "LINES", "triangle", "LINES_LOOP"};
        DECLARED_VALUES = new PrimitiveTopology[]{LINES, LINES_LOOP, QUADS, TRIANGLES};
    }
}

