/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.mapped;

import org.lwjgl.util.mapped.MappedObject;

public class MappedSet3 {
    private final MappedObject var_org_lwjgl_util_mapped_MappedObject_a;
    private final MappedObject var_org_lwjgl_util_mapped_MappedObject_b;
    private final MappedObject var_org_lwjgl_util_mapped_MappedObject_c;
    public int view;

    MappedSet3(MappedObject a, MappedObject b, MappedObject c) {
        this.var_org_lwjgl_util_mapped_MappedObject_a = a;
        this.var_org_lwjgl_util_mapped_MappedObject_b = b;
        this.var_org_lwjgl_util_mapped_MappedObject_c = c;
    }

    void view(int view) {
        this.var_org_lwjgl_util_mapped_MappedObject_a.setViewAddress(this.var_org_lwjgl_util_mapped_MappedObject_a.getViewAddress(view));
        this.var_org_lwjgl_util_mapped_MappedObject_b.setViewAddress(this.var_org_lwjgl_util_mapped_MappedObject_b.getViewAddress(view));
        this.var_org_lwjgl_util_mapped_MappedObject_c.setViewAddress(this.var_org_lwjgl_util_mapped_MappedObject_c.getViewAddress(view));
    }

    public void next() {
        this.var_org_lwjgl_util_mapped_MappedObject_a.next();
        this.var_org_lwjgl_util_mapped_MappedObject_b.next();
        this.var_org_lwjgl_util_mapped_MappedObject_c.next();
    }
}

