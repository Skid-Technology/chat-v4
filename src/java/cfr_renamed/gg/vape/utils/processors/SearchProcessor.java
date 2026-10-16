/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.processors;

import gg.vape.Vape;
import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.datas.SearchResultData;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.WorldClient;
import java.util.Arrays;
import java.util.List;

public class SearchProcessor
implements Runnable {
    private boolean var_boolean_P;
    private final List<SearchResultData> var_java_util_List_gg_vape_utils_datas_SearchResultData__c;
    private final List<Integer> var_java_util_List_java_lang_Integer__y;
    private final List<SearchBlock> var_java_util_List_gg_vape_ui_unmap_SearchBlock__H;
    private final int[][] var_int_arr_arr_X = new int[][]{{0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}};
    private boolean var_boolean_o;
    private final NumberValue var_gg_vape_value_NumberValue_j;

    public boolean h(int n, int n2, int n3) {
        WorldClient worldClient = Minecraft.theWorld();
        for (int[] nArray : this.var_int_arr_arr_X) {
            Block block = worldClient.getBlockByPos(n + nArray[0], n2 + nArray[1], n3 + nArray[2]);
            int n4 = Block.R(block);
            if (!this.var_java_util_List_java_lang_Integer__y.contains(n4)) continue;
            return true;
        }
        return false;
    }

    public SearchResultData X(int n, int n2, int n3) {
        for (SearchResultData searchResultData : this.var_java_util_List_gg_vape_utils_datas_SearchResultData__c) {
            if (searchResultData.F != n || searchResultData.O != n2 || searchResultData.D != n3) continue;
            return searchResultData;
        }
        return null;
    }

    public void w() {
        this.var_boolean_o = false;
    }

    public void n() {
        this.var_boolean_o = true;
        new Thread(this).start();
    }

    public NumberValue F() {
        return this.var_gg_vape_value_NumberValue_j;
    }

    public SearchBlock S(int n, int n2) {
        for (SearchBlock searchBlock : this.var_java_util_List_gg_vape_ui_unmap_SearchBlock__H) {
            if (!searchBlock.b(n, n2)) continue;
            return searchBlock;
        }
        return null;
    }

    public SearchProcessor(List<SearchResultData> list, List<SearchBlock> list2, NumberValue numberValue) {
        this.var_java_util_List_java_lang_Integer__y = Arrays.asList(0, 8, 9, 30);
        this.var_java_util_List_gg_vape_utils_datas_SearchResultData__c = list;
        this.var_java_util_List_gg_vape_ui_unmap_SearchBlock__H = list2;
        this.var_gg_vape_value_NumberValue_j = numberValue;
    }

    public List<SearchResultData> b() {
        return this.var_java_util_List_gg_vape_utils_datas_SearchResultData__c;
    }

    public boolean p() {
        return this.var_boolean_o;
    }

    public void X(boolean bl) {
        this.var_boolean_P = bl;
    }

    public boolean u() {
        return this.var_boolean_P;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public void run() {
        block11: {
            try {
                block4: while (this.var_boolean_o) {
                    try {
                        Thread.sleep(50L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    if (Minecraft.theWorld().isNull() || Minecraft.thePlayer().isNull()) continue;
                    EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
                    WorldClient worldClient = Minecraft.theWorld();
                    int n = this.var_gg_vape_value_NumberValue_j.getValue().intValue();
                    this.h(0, 0, 0);
                    for (int i = -n; i < n; ++i) {
                        for (int j = -n; j < n; ++j) {
                            for (int k = -n; k < n; ++k) {
                                if (this.var_boolean_o) {
                                    int n2;
                                    int n3;
                                    if (Minecraft.theWorld().isNull() || !Minecraft.theWorld().equals(worldClient) || Minecraft.thePlayer().isNull() || !Minecraft.thePlayer().equals(entityPlayerSP)) {
                                        this.var_java_util_List_gg_vape_utils_datas_SearchResultData__c.clear();
                                        continue block4;
                                    }
                                    int n4 = (int)entityPlayerSP.z() + i;
                                    Block block = worldClient.getBlockByPos(n4, n3 = (int)entityPlayerSP.N() + k, n2 = (int)entityPlayerSP.h() + j);
                                    if (block.U() == null) continue;
                                    int n5 = Block.R(block);
                                    int n6 = ForgeVersion.MC_1_16_5.d() ? -1 : block.d(n4, n3, n2);
                                    SearchResultData searchResultData = this.X(n4, n3, n2);
                                    boolean bl = searchResultData != null;
                                    boolean bl2 = bl;
                                    if (!bl) {
                                        SearchBlock searchBlock = this.S(n5, n6);
                                        if (searchBlock == null || this.var_boolean_P && !this.h(n4, n3, n2)) continue;
                                        this.var_java_util_List_gg_vape_utils_datas_SearchResultData__c.add(new SearchResultData(n4, n3, n2, n5, searchBlock, searchBlock.I$src$Ljava_util_concurrent_atomic_AtomicBoolean_$10pq3bz(), 0));
                                        continue;
                                    }
                                    if (n5 == searchResultData.N && !(RotationUtil.p(entityPlayerSP, n4, n3, n2) > 200.0)) continue;
                                    this.var_java_util_List_gg_vape_utils_datas_SearchResultData__c.remove(searchResultData);
                                    continue;
                                }
                                break block11;
                            }
                        }
                    }
                }
            }
            catch (Exception exception) {
                Vape.logThrowable(exception);
            }
        }
    }
}

