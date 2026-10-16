/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.datas;

import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.datas.HSBData;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchResultData
extends HSBData {
    private SearchBlock var_gg_vape_ui_unmap_SearchBlock_j;
    private int var_int_z;

    public int L() {
        return this.var_int_z;
    }

    public void W(int n) {
        this.var_int_z = n;
    }

    public boolean n() {
        return this.var_gg_vape_ui_unmap_SearchBlock_j.W();
    }

    public Color O() {
        return this.var_gg_vape_ui_unmap_SearchBlock_j.B();
    }

    public void t(int n, int n2, int n3, int n4, SearchBlock searchBlock, AtomicBoolean atomicBoolean, int n5) {
        this.F = n;
        this.O = n2;
        this.D = n3;
        this.N = n4;
        this.var_gg_vape_ui_unmap_SearchBlock_j = searchBlock;
        this.Q = atomicBoolean;
        this.var_int_z = n5;
    }

    public SearchResultData(int n, int n2, int n3, int n4, SearchBlock searchBlock, AtomicBoolean atomicBoolean, int n5) {
        super(n, n2, n3, n4, searchBlock.B(), atomicBoolean);
        this.var_gg_vape_ui_unmap_SearchBlock_j = searchBlock;
        this.var_int_z = n5;
    }
}

