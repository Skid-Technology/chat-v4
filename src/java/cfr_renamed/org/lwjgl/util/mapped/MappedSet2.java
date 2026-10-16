/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.mapped;

import org.lwjgl.util.mapped.MappedObject;

public class MappedSet2 {
    private final MappedObject var_org_lwjgl_util_mapped_MappedObject_a;
    private final MappedObject var_org_lwjgl_util_mapped_MappedObject_b;
    public int view;

    MappedSet2(MappedObject a, MappedObject b) {
        this.var_org_lwjgl_util_mapped_MappedObject_a = a;
        this.var_org_lwjgl_util_mapped_MappedObject_b = b;
    }

    void view(int view) {
        this.var_org_lwjgl_util_mapped_MappedObject_a.setViewAddress(this.var_org_lwjgl_util_mapped_MappedObject_a.getViewAddress(view));
        this.var_org_lwjgl_util_mapped_MappedObject_b.setViewAddress(this.var_org_lwjgl_util_mapped_MappedObject_b.getViewAddress(view));
    }

    public void next() {
        this.var_org_lwjgl_util_mapped_MappedObject_a.next();
        this.var_org_lwjgl_util_mapped_MappedObject_b.next();
    }
}

