/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

public interface MappingTask {
    public void transform();

    public void rollback();

    public Class getTargetClass();

    public void prepare();

    public boolean isApplied();

    public int commit();

    public void serialize();
}

